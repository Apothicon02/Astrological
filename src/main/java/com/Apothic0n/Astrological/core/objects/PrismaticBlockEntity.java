package com.Apothic0n.Astrological.core.objects;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RedstoneTorchBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

import java.util.List;

import static com.Apothic0n.Astrological.core.objects.AstrologicalBlockEntities.PRISMATIC_SELENITE;

public class PrismaticBlockEntity extends BlockEntity {
    public static final BooleanProperty USELESS_TOGGLE = BooleanProperty.create("useless_toggle");
    public static final IntegerProperty POWER = BlockStateProperties.POWER;
    public static final IntegerProperty PRISMATIC_POWER = IntegerProperty.create("prismatic_power", 0, 30);
    public PrismaticBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(PRISMATIC_SELENITE.get(), blockPos, blockState);
    }

    public static <T extends BlockEntity> void tick(Level level, BlockPos blockPos, BlockState blockState, T t) {
        if (!level.isClientSide) {
            List<Integer> all = List.of(
                    power(level, blockPos.below()),
                    power(level, blockPos.north()),
                    power(level, blockPos.east()),
                    power(level, blockPos.south()),
                    power(level, blockPos.west()),
                    power(level, blockPos.above())
            );
            int maxPower = 0;
            for (int i = 0; i < all.size(); i++) {
                int otherPower = all.get(i);
                if (otherPower > 0) {
                    if (maxPower < otherPower) {
                        maxPower = otherPower;
                    }
                }
            }
            if (maxPower == 0) {
                level.setBlock(blockPos, blockState.setValue(PRISMATIC_POWER, 0).cycle(USELESS_TOGGLE), 2);
            } else {
                level.setBlock(blockPos, blockState.setValue(PRISMATIC_POWER, maxPower).cycle(USELESS_TOGGLE), 2);
            }
        }
    }

    private static int power(Level serverLevel, BlockPos blockPos) {
        BlockState otherBlockState = serverLevel.getBlockState(blockPos);
        if ((otherBlockState.hasProperty(PRISMATIC_POWER) && otherBlockState.getValue(PRISMATIC_POWER) > 0)) {
            return (serverLevel.getBlockState(blockPos).getValue(PRISMATIC_POWER)) - 1;
        } else if ((otherBlockState.hasProperty(POWER) && otherBlockState.getValue(POWER) > 0)) {
            return (serverLevel.getBlockState(blockPos).getValue(POWER)) - 1;
        } else if (otherBlockState.is(Blocks.REDSTONE_BLOCK) || (otherBlockState.is(Blocks.REDSTONE_TORCH) && otherBlockState.getValue(RedstoneTorchBlock.LIT).equals(true)) ||
                (otherBlockState.hasProperty(BlockStateProperties.POWERED) && otherBlockState.getValue(BlockStateProperties.POWERED).equals(true))) {
            return 30;
        }
        return 0;
    }
}