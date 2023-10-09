package com.Apothic0n.Inversia.core.objects;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;

import javax.annotation.Nullable;

import java.util.Map;

import static com.Apothic0n.Inversia.core.objects.PrismaticBlockEntity.*;

public class PrismaticWallBlock extends WallBlock implements EntityBlock {

    public PrismaticWallBlock(Properties properties) {
        super(properties);
        registerDefaultState(defaultBlockState().setValue(PRISMATIC_POWER, 0).setValue(USELESS_TOGGLE, false));
        fixShapeMaps();
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(PRISMATIC_POWER, USELESS_TOGGLE);
    }

    public void onProjectileHit(Level level, BlockState blockState, BlockHitResult blockHitResult, Projectile projectile) {
        if (!level.isClientSide) {
            BlockPos blockpos = blockHitResult.getBlockPos();
            level.playSound((Player) null, blockpos, SoundEvents.AMETHYST_BLOCK_HIT, SoundSource.BLOCKS, 2.0F, 2.03F + level.random.nextFloat() * 0.02F);
            level.playSound((Player) null, blockpos, SoundEvents.AMETHYST_BLOCK_CHIME, SoundSource.BLOCKS, 2.0F, 2.03F + level.random.nextFloat() * 0.02F);
        }

    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> blockEntityType) {
        return PrismaticWallBlockEntity::tick;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return InversiaBlockEntities.SELENITE_WALL.get().create(blockPos, blockState);
    }

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

    private void fixShapeMaps() {
        Map<BlockState, VoxelShape> shapeByIndex = ObfuscationReflectionHelper.getPrivateValue(WallBlock.class, this, "f_57955_");
        shapeByIndex = fixShapeMap(shapeByIndex);
        ObfuscationReflectionHelper.setPrivateValue(WallBlock.class, this, shapeByIndex, "f_57955_");

        Map<BlockState, VoxelShape> collisionShapeByIndex = ObfuscationReflectionHelper.getPrivateValue(WallBlock.class, this, "f_57956_");
        collisionShapeByIndex = fixShapeMap(collisionShapeByIndex);
        ObfuscationReflectionHelper.setPrivateValue(WallBlock.class, this, collisionShapeByIndex, "f_57956_");
    }

    private static Map<BlockState, VoxelShape> fixShapeMap(Map<BlockState, VoxelShape> map) {
        Preconditions.checkNotNull(map, "Got a null map?!");

        ImmutableMap.Builder<BlockState, VoxelShape> builder = ImmutableMap.builder();

        for (BlockState state : map.keySet()) {
            VoxelShape shape = map.get(state);
            for (int prismaticPower : PRISMATIC_POWER.getPossibleValues()) {
                state = state.setValue(PRISMATIC_POWER, prismaticPower);
                builder.put(state, shape);
                builder.put(state.cycle(USELESS_TOGGLE), shape);
            }
        }

        return builder.build();
    }
}