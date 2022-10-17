package com.bisectstudios.mods.stagecraft.mixins;

import blusunrize.immersiveengineering.common.EventHandler;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = EventHandler.class, remap = false)
public class IEEventHandlerMixin {

    @Inject(method = "onLivingDrops", at = @At("HEAD"), cancellable = true)
    private void cancelShaderBagDrops(LivingDropsEvent event, CallbackInfo ci) {
        ci.cancel();
    }

}
