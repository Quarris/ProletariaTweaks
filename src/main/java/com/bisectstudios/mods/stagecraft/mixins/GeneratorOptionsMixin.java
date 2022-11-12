package com.bisectstudios.mods.stagecraft.mixins;

import mod.beethoven92.betterendforge.BetterEnd;
import mod.beethoven92.betterendforge.common.util.WorldDataAPI;
import mod.beethoven92.betterendforge.common.world.generator.GeneratorOptions;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = GeneratorOptions.class, remap = false)
public class GeneratorOptionsMixin {

    @Inject(method = "init", at = @At("TAIL"))
    private static void addPortalPosConfigOption(CallbackInfo ci) {
        BlockPos portalPos = NBTUtil.readBlockPos(WorldDataAPI.getCompoundTag(BetterEnd.MOD_ID, "portalPos"));
        GeneratorOptions.setPortalPos(portalPos);
    }
}
