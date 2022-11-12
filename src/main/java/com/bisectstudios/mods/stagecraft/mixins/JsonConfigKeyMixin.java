package com.bisectstudios.mods.stagecraft.mixins;

import mod.beethoven92.betterendforge.config.jsons.JsonConfigKey;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Arrays;

@Mixin(value = JsonConfigKey.class, remap = false)
public class JsonConfigKeyMixin {

    @Shadow @Final private String[] path;

    @Shadow @Final private String entry;

    @Inject(method = "hashCode", at = @At("HEAD"), cancellable = true)
    private void properHashCode(CallbackInfoReturnable<Integer> cir) {
        int prime = 31;
        int result = 1;
        result = prime * result + Arrays.hashCode(this.path);
        result = prime * result + this.entry.hashCode();
        cir.setReturnValue(result);
    }
}
