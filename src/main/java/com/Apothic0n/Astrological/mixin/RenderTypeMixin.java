package com.Apothic0n.Astrological.mixin;

import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(value = RenderType.class, priority = 69420)
public class RenderTypeMixin {

    /**
     * @author Apothicon
     * @reason Fixes sleep not rendering past like 50 blocks.
     */
    @ModifyArg(
            method = "translucentState",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/RenderType$CompositeState$CompositeStateBuilder;setTextureState(Lnet/minecraft/client/renderer/RenderStateShard$EmptyTextureStateShard;)Lnet/minecraft/client/renderer/RenderType$CompositeState$CompositeStateBuilder;"
            )
    )
    private static RenderStateShard.EmptyTextureStateShard removeMipping(RenderStateShard.EmptyTextureStateShard emptyTextureStateShard) {
        return RenderType.BLOCK_SHEET;
    }
}
