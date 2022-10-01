package com.bisectstudios.mods.stagecraft.mixins;

import mod.beethoven92.betterendforge.BetterEnd;
import mod.beethoven92.betterendforge.common.util.StructureHelper;
import mod.beethoven92.betterendforge.common.util.WorldDataAPI;
import mod.beethoven92.betterendforge.common.world.generator.GeneratorOptions;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.chunk.ChunkStatus;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.EndPodiumFeature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraftforge.fml.loading.FMLLoader;
import net.minecraftforge.fml.server.ServerLifecycleHooks;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(value = EndPodiumFeature.class, priority = 900)
public class EndPodiumFeatureMixin {

    @Shadow @Final private boolean activePortal;

    @Inject(
        method = "generate(Lnet/minecraft/world/ISeedReader;Lnet/minecraft/world/gen/ChunkGenerator;Ljava/util/Random;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/world/gen/feature/NoFeatureConfig;)Z",
        at = @At("HEAD"),
        cancellable = true
    )
    public void getEndPortalTemplate(ISeedReader level, ChunkGenerator chunkGenerator, Random random, BlockPos origin, NoFeatureConfig config, CallbackInfoReturnable<Boolean> info) {
        if (!GeneratorOptions.hasPortal()) {
            info.setReturnValue(false);
            info.cancel();
        } else if (GeneratorOptions.replacePortal() && FMLLoader.getLoadingModList().getModFileById("endergetic") == null) {
            BlockPos blockPos = this.be_updatePos(origin, level);
            ResourceLocation structureId = BetterEnd.makeID(this.activePortal ? "portal/end_portal_active" : "portal/end_portal_inactive");
            Template structure;
            MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
            if (server == null) {
                structure = StructureHelper.readStructure(structureId);
            } else {
                structure = server.getTemplateManager().getTemplateDefaulted(structureId);
            }
            Vector3i size = structure.getSize();
            blockPos = blockPos.add(-(size.getX() >> 1), -1, -(size.getZ() >> 1));
            structure.func_237146_a_(level, blockPos, blockPos, new PlacementSettings(), random, 2);
            info.setReturnValue(true);
            info.cancel();
        }
    }

    private BlockPos be_updatePos(BlockPos blockPos, ISeedReader world) {
        if (GeneratorOptions.useNewGenerator()) {
            BlockPos pos = GeneratorOptions.getPortalPos();
            if (pos.equals(BlockPos.ZERO)) {
                int y = world.getChunk(0, 0, ChunkStatus.FULL).getTopBlockY(Heightmap.Type.WORLD_SURFACE, blockPos.getX(), blockPos.getZ());
                if (y < 1) {
                    y = 65;
                }

                pos = new BlockPos(pos.getX(), y, pos.getZ());
                GeneratorOptions.setPortalPos(pos);
                WorldDataAPI.getRootTag("betterendforge").put("portal", NBTUtil.writeBlockPos(pos));
            }

            return pos;
        } else {
            return blockPos;
        }
    }
}
