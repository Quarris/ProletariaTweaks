package com.bisectstudios.mods.proletaria.mixins;

import com.bisectstudios.mods.proletaria.ModRef;
import com.bisectstudios.mods.proletaria.configs.ModConfigs;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;
import net.minecraft.world.gen.feature.structure.OceanMonumentPieces;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = OceanMonumentPieces.MonumentCoreRoom.class)
public abstract class OceanMonumentPiecesMonumentCoreMixin extends OceanMonumentPieces.Piece {

    public OceanMonumentPiecesMonumentCoreMixin(IStructurePieceType p_i50647_1_, int p_i50647_2_) {
        super(p_i50647_1_, p_i50647_2_);
    }

    @Redirect(method = "func_230383_a_",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/world/gen/feature/structure/OceanMonumentPieces$MonumentCoreRoom;fillWithBlocks(Lnet/minecraft/world/ISeedReader;Lnet/minecraft/util/math/MutableBoundingBox;IIIIIILnet/minecraft/block/BlockState;Lnet/minecraft/block/BlockState;Z)V",
                    ordinal = 12))
    public void remakeTrophyRoom(OceanMonumentPieces.MonumentCoreRoom instance, ISeedReader iSeedReader, MutableBoundingBox mutableBoundingBox, int i1, int i2, int i3, int i4, int i5, int i6, BlockState blockState1, BlockState blockState2, boolean b) {
        Block replacement = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(ModConfigs.oceanMonumentTrophyReplacement.get()));
        if (replacement == null) {
            ModRef.LOGGER.error("Cannot find block {}. Defaulting.", ModConfigs.oceanMonumentTrophyReplacement.get());
            replacement = Blocks.GOLD_BLOCK;
        }
        this.fillWithBlocks(iSeedReader, mutableBoundingBox, 7, 4, 7, 8, 5, 8, replacement.getDefaultState(), replacement.getDefaultState(), false);
    }
}
