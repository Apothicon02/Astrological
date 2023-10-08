package com.Apothic0n.Inversia.core.objects;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class JadeBlock extends Block {

    public JadeBlock(Properties p_49795_) {
        super(p_49795_);
    }

    public void onProjectileHit(Level p_152001_, BlockState p_152002_, BlockHitResult p_152003_, Projectile p_152004_) {
        if (!p_152001_.isClientSide) {
            BlockPos blockpos = p_152003_.getBlockPos();
            p_152001_.playSound((Player)null, blockpos, SoundEvents.AMETHYST_BLOCK_HIT, SoundSource.BLOCKS, 2.0F, 2.03F + p_152001_.random.nextFloat() * 0.02F);
            p_152001_.playSound((Player)null, blockpos, SoundEvents.AMETHYST_BLOCK_CHIME, SoundSource.BLOCKS, 2.0F, 2.03F + p_152001_.random.nextFloat() * 0.02F);
        }

    }
}
