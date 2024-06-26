package com.Apothic0n.Astrological.core.objects;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.BlockHitResult;

import javax.annotation.Nullable;

import static com.Apothic0n.Astrological.core.objects.PrismaticBlockEntity.*;

public class PrismaticBlock extends Block implements EntityBlock {

    public PrismaticBlock(Properties properties) {
        super(properties);
        registerDefaultState(defaultBlockState().setValue(PRISMATIC_POWER, 0).setValue(USELESS_TOGGLE, false));
    }
    public void onProjectileHit(Level p_152001_, BlockState p_152002_, BlockHitResult p_152003_, Projectile p_152004_) {
        if (!p_152001_.isClientSide) {
            BlockPos blockpos = p_152003_.getBlockPos();
            p_152001_.playSound((Player) null, blockpos, SoundEvents.AMETHYST_BLOCK_HIT, SoundSource.BLOCKS, 2.0F, 2.03F + p_152001_.random.nextFloat() * 0.02F);
            p_152001_.playSound((Player) null, blockpos, SoundEvents.AMETHYST_BLOCK_CHIME, SoundSource.BLOCKS, 2.0F, 2.03F + p_152001_.random.nextFloat() * 0.02F);
        }

    }
    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> blockEntityType) {
        return PrismaticBlockEntity::tick;
    }
    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return AstrologicalBlockEntities.PRISMATIC_SELENITE.get().create(blockPos, blockState);
    }
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> blockBlockStateBuilder) {
        blockBlockStateBuilder.add(PRISMATIC_POWER).add(USELESS_TOGGLE);
    }
}