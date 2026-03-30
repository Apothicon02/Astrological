package com.apothicon.astrological.core.block_tint_sources;

import net.minecraft.client.Minecraft;
import net.minecraft.client.color.block.BlockTintSource;
import net.minecraft.client.renderer.block.BlockAndTintGetter;
import net.minecraft.core.BlockPos;
import net.minecraft.util.ARGB;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.state.BlockState;

import static com.apothicon.astrological.core.events.ModEvents.*;

public class EndStoneBlockTintSource implements BlockTintSource {
    @Override
    public int color(BlockState blockState) {
        return 0;
    }

    @Override
    public int colorInWorld(BlockState state, BlockAndTintGetter level, BlockPos pos) {
        if (pos != null && Minecraft.getInstance().level != null) {
            int x = pos.getX();
            int z = pos.getZ();
            int color = -328966;
            double saturate = Mth.clamp(SATURATION_NOISE.getValue(x * 0.1, z * 0.1, false) * 0.33, -0.03, 0.03)+1.1;
            double brighten = Mth.clamp(BRIGHTNESS_NOISE.getValue(x * 0.025, z * 0.025, false) * 0.3, -0.33, 0.33);
            float red = (float) ARGB.red(color)/255;
            float green = (float) ARGB.green(color)/255;
            float blue = (float) ARGB.blue(color)/255;
            float gray = (float) ((red + green + blue) / (3 + brighten));
            return ARGB.color(ARGB.alpha(color),
                    (int) (Mth.clamp(((blue + (gray - blue)) * saturate), 0, 1) * 255),
                    (int) (Mth.clamp(((green + (gray - green)) * saturate), 0, 1) * 255),
                    (int) (Mth.clamp(((red + (gray - red)) * saturate), 0, 1) * 255));
        } else {
            return -328966;
        }
    }
}
