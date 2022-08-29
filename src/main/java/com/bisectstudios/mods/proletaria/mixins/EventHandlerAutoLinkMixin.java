package com.bisectstudios.mods.proletaria.mixins;

import com.bisectstudios.mods.proletaria.ModContent;
import hellfirepvp.astralsorcery.common.event.handler.EventHandlerAutoLink;
import hellfirepvp.astralsorcery.common.starlight.WorldNetworkHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(value = EventHandlerAutoLink.class, remap = false)
public class EventHandlerAutoLinkMixin {

    @Inject(method = "onChange",
            at = @At(
                    value = "INVOKE",
                    target = "Lhellfirepvp/astralsorcery/common/starlight/WorldNetworkHandler;informBlockChange(Lnet/minecraft/util/math/BlockPos;)V",
                    shift = At.Shift.AFTER
            ),
            cancellable = true,
            locals = LocalCapture.CAPTURE_FAILEXCEPTION)
    public void handleAutoLinkBlockTag(World world, Chunk chunk, BlockPos pos, BlockState oldState, BlockState newState, CallbackInfo ci, Block oldB, Block newB, WorldNetworkHandler handle) {
        if (newState.isIn(ModContent.ASTRAL_AUTO_LINK_TAG)) {
            handle.attemptAutoLinkTo(pos);
        } else if (oldState.isIn(ModContent.ASTRAL_AUTO_LINK_TAG)) {
            handle.removeAutoLinkTo(pos);
        }
        ci.cancel();
    }
}
