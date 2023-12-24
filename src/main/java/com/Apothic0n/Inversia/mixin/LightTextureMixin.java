package com.Apothic0n.Inversia.mixin;

import com.Apothic0n.Inversia.api.InversiaMath;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = LightTexture.class, priority = 69420)
public class LightTextureMixin {

    /**
     * @author Apothicon
     * @reason Increases ambient lighting when there is not an eclipse.
     */
    @Inject(at = @At("RETURN"), method = "getBrightness", cancellable = true)
    private static void getBrightness(DimensionType dimensionType, int integer, CallbackInfoReturnable<Float> cir) {
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
                    ambientLight = InversiaMath.invLerp(time, 0.2f, min, max);
                } else {
                    min = mid;
                    ambientLight = InversiaMath.invLerp(time, 0.2f, max, min);
                }
            } else if (time >= 12500 && time <= 13000) {
                int min = 12500;
                int max = 13000;
                int mid = ((max - min) / 2) + min;
                if (time < mid) {
                    max = mid;
                    ambientLight = InversiaMath.invLerp(time, 0.2f, min, max);
                } else {
                    min = mid;
                    ambientLight = InversiaMath.invLerp(time, 0.2f, max, min);
                }
            } else {
                ambientLight = 0;
            }
            float f = (float)integer / 15.0F;
            float f1 = f / (4.0F - 3.0F * f);
            cir.setReturnValue(Mth.lerp(ambientLight, f1, 1.0F));
        }
    }
}
