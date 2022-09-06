package com.bisectstudios.mods.proletaria.mixins;

import net.minecraft.block.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import wayoftime.bloodmagic.api.IBloodMagicAPI;
import wayoftime.bloodmagic.impl.BloodMagicCorePlugin;

@Mixin(value = BloodMagicCorePlugin.class, remap = false)
public class BloodMagicCorePluginMixin {

    @Redirect(method = "register", at = @At(value = "INVOKE", target = "Lwayoftime/bloodmagic/api/IBloodMagicAPI;registerAltarComponent(Lnet/minecraft/block/BlockState;Ljava/lang/String;)V"))
    public void cancelAltarComponentRegisters(IBloodMagicAPI instance, BlockState state, String componentType) {

    }

}
