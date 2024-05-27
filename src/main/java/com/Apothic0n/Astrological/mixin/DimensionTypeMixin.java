package com.Apothic0n.Astrological.mixin;

import com.Apothic0n.Astrological.api.AstrologicalMath;
import net.minecraft.client.Minecraft;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DimensionType.class)
public abstract class DimensionTypeMixin {

    /**
     * @author Apothicon
     * @reason Remove ambient light at night.
     */
    @Inject(method = "ambientLight", at = @At("HEAD"), cancellable = true)
    public void ambientLight(CallbackInfoReturnable<Float> ci) {
        Level level = Minecraft.getInstance().level;
        if (level != null && level.dimension().equals(Level.END)) {
            float ambientLight;
            float time = level.getDayTime();
            if (time > 24000) {
                time = (float) (time - (Math.floor(time / 24000) * 24000));
            }
            if (time >= 22750 && time <= 23750) {
                int min = 22750;
                int max = 23750;
                int mid = ((max - min) / 2) + min;
                if (time < mid) {
                    max = mid;
                    ambientLight = AstrologicalMath.invLerp(time, 0.2f, min, max);
                } else {
                    min = mid;
                    ambientLight = AstrologicalMath.invLerp(time, 0.2f, max, min);
                }
            } else if (time >= 12500 && time <= 13000) {
                int min = 12500;
                int max = 13000;
                int mid = ((max - min) / 2) + min;
                if (time < mid) {
                    max = mid;
                    ambientLight = AstrologicalMath.invLerp(time, 0.2f, min, max);
                } else {
                    min = mid;
                    ambientLight = AstrologicalMath.invLerp(time, 0.2f, max, min);
                }
            } else {
                ambientLight = 0;
            }
            ci.setReturnValue(ambientLight-0.266F);
        }
    }
}
