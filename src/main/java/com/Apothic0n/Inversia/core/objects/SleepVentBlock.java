package com.Apothic0n.Inversia.core.objects;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class SleepVentBlock extends Block {
    public SleepVentBlock(Properties properties) {
        super(properties);
    }

    public void animateTick(BlockState blockState, Level level, BlockPos blockPos, RandomSource randomSource) {
        int randomNumber = (int)(Math.random()*(69)+1);
        if (randomNumber < 2) {
            for(int j = 0; j < 1080; ++j) {
                double d3 = (blockPos.getX() + (Math.random() * (500) - 250)*0.001);
                double d8 = (blockPos.getY() + randomSource.nextDouble() * 0.069);
                double d13 = (blockPos.getZ() + (Math.random() * (500) - 250)*0.001);
                int randomNumber2 = (int)(Math.random()*(50)+1);
                if (randomNumber2 < 2) {
                    level.addParticle(ParticleTypes.LARGE_SMOKE, d3, d8 + 0.69, d13, (Math.random() * (500) - 250) * 0.01, (Math.random() * (500) - 250) * 0.000042, (Math.random() * (500) - 250) * 0.01);
                }
                level.addParticle(ParticleTypes.DRAGON_BREATH, d3, d8, d13, (Math.random() * (50) - 25)*0.01, (Math.random() * (500) - 250)*0.0142, (Math.random() * (50) - 25)*0.01);
            }
            level.playLocalSound((double)blockPos.getX() + 0.5D, (double)blockPos.getY() + 0.5D, (double)blockPos.getZ() + 0.5D, SoundEvents.ALLAY_DEATH, SoundSource.BLOCKS, 9F + (randomSource.nextFloat()*3), randomSource.nextFloat() * 0.25F + 0.4F, false);
        }
    }
}
