package com.Apothic0n.Inversia.core.objects;

import net.minecraft.client.Minecraft;
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
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

import static com.Apothic0n.Inversia.core.objects.PrismaticBlockEntity.*;

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
        return InversiaBlockEntities.PRISMATIC_SELENITE.get().create(blockPos, blockState);
    }

    @OnlyIn(Dist.CLIENT)
    public float getFriction() {
        Level level = Minecraft.getInstance().level;
        float newFriction = this.friction; //night
        if (level != null) {
            float time = level.getDayTime();
            if (time > 24000) {
                time = (float) (time - (Math.floor(time / 24000) * 24000));
            }
            if ((time >= 22000 || time <= 500) || (time >= 12000 && time <= 13500)) { //dawn & dusk
                newFriction = 1.15f;
            } else if (time <= 12000) { //day
                newFriction = 0.2f;
            }
        }
        return newFriction;
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> blockBlockStateBuilder) {
        blockBlockStateBuilder.add(PRISMATIC_POWER).add(USELESS_TOGGLE);
    }
}