package com.Apothic0n.Inversia.mixin;

import com.Apothic0n.Inversia.api.InversiaMath;
import net.minecraft.client.Minecraft;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(value = DimensionType.class, priority = 69420)
public class DimensionTypeMixin {

    /**
     * @author Apothicon
     * @reason Increases ambient lighting when there is not an eclipse.
     */
    @Overwrite
    public float ambientLight() {
        Level level = Minecraft.getInstance().level;
        if (level != null && level.dimension().equals(Level.END)) {
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
                    return InversiaMath.invLerp(time, 0.2f, min, max);
                } else {
                    min = mid;
                    return InversiaMath.invLerp(time, 0.2f, max, min);
                }
            } else if (time >= 12500 && time <= 13000) {
                int min = 12500;
                int max = 13000;
                int mid = ((max - min) / 2) + min;
                if (time < mid) {
                    max = mid;
                    return InversiaMath.invLerp(time, 0.2f, min, max);
                } else {
                    min = mid;
                    return InversiaMath.invLerp(time, 0.2f, max, min);
                }
            }
        }
        return 0;
    }
}
