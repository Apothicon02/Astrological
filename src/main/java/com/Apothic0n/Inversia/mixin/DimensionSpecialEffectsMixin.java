package com.Apothic0n.Inversia.mixin;

import net.minecraft.client.renderer.DimensionSpecialEffects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(value = DimensionSpecialEffects.class, priority = 69420)
public class DimensionSpecialEffectsMixin {

    /**
     * @author Apothicon
     * @reason Removes hardcoded ambient lighting from the end.
     */
    @Overwrite
    public boolean constantAmbientLight() {
        return false;
    }
}
