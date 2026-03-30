package com.apothicon.astrological.core.block_tint_sources;

import net.minecraft.client.Minecraft;
import net.minecraft.client.color.block.BlockTintSource;
import net.minecraft.client.renderer.block.BlockAndTintGetter;
import net.minecraft.core.BlockPos;
import net.minecraft.util.ARGB;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import static com.apothicon.astrological.core.events.ModEvents.BRIGHTNESS_NOISE;
import static com.apothicon.astrological.core.events.ModEvents.SATURATION_NOISE;

public class PrismaticSeleniteBlockTintSource implements BlockTintSource {
    @Override
    public int color(BlockState blockState) {
        return 0;
    }

    @Override
    public int colorInWorld(BlockState state, BlockAndTintGetter level, BlockPos pos) {
        if (level instanceof Level actualLevel) {
            int color = -19457; //night
            float time = actualLevel.getOverworldClockTime();
            if (time > 24000) {
                time = (float) (time - (Math.floor(time / 24000) * 24000));
            }
            if ((time >= 22000 || time <= 500) || (time >= 12000 && time <= 13500)) { //dawn & dusk
                color = -9549;
            } else if (time <= 12000) { //day
                color = -3670093;
            }
            return color;
        } else {
            return -328966;
        }
    }
}
