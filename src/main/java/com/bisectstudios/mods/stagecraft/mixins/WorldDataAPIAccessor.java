package com.bisectstudios.mods.stagecraft.mixins;

import mod.beethoven92.betterendforge.common.util.WorldDataAPI;
import net.minecraft.nbt.CompoundNBT;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(WorldDataAPI.class)
public interface WorldDataAPIAccessor {

    @Accessor
    Map<String, CompoundNBT> getTAGS();

}
