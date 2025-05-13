package com.Apothic0n.Astrological.mixin;

import com.Apothic0n.Astrological.core.objects.AstrologicalBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ChorusFlowerBlock;
import net.minecraft.world.level.block.ChorusPlantBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = ChorusFlowerBlock.class, priority = 69420)
public abstract class ChorusFlowerBlockMixin {

    @Shadow @Final private Block plant;

    @Shadow @Final public static IntegerProperty AGE;

    @Shadow
    private static boolean allNeighborsEmpty(LevelReader p_51698_, BlockPos p_51699_, @Nullable Direction p_51700_) {
        return false;
    }

    @Shadow protected abstract void placeGrownFlower(Level p_51662_, BlockPos p_51663_, int p_51664_);

    @Shadow protected abstract void placeDeadFlower(Level p_51659_, BlockPos p_51660_);

    /**
     * @author Apothicon
     * @reason Allows chorus flowers to survive on purpurite.
     */
    @Overwrite
    public boolean canSurvive(BlockState p_51683_, LevelReader p_51684_, BlockPos p_51685_) {
        BlockState blockstate = p_51684_.getBlockState(p_51685_.below());
        if (!blockstate.is(this.plant) && !blockstate.is(Blocks.END_STONE) && !blockstate.is(AstrologicalBlocks.PURPURITE.get())) {
            if (!blockstate.isAir()) {
                return false;
            } else {
                boolean flag = false;

                for(Direction direction : Direction.Plane.HORIZONTAL) {
                    BlockState blockstate1 = p_51684_.getBlockState(p_51685_.relative(direction));
                    if (blockstate1.is(this.plant)) {
                        if (flag) {
                            return false;
                        }

                        flag = true;
                    } else if (!blockstate1.isAir()) {
                        return false;
                    }
                }

                return flag;
            }
        } else {
            return true;
        }
    }

    /**
     * @author Apothicon
     * @reason Allows chorus flowers to connect to purpurite.
     */
    @Overwrite
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        BlockPos blockpos = pos.above();
        if (level.isEmptyBlock(blockpos) && blockpos.getY() < level.getMaxBuildHeight()) {
            int i = state.getValue(AGE);
            if (i < 5 && net.neoforged.neoforge.common.CommonHooks.canCropGrow(level, blockpos, state, true)) {
                boolean flag = false;
                boolean flag1 = false;
                BlockState blockstate = level.getBlockState(pos.below());
                net.neoforged.neoforge.common.util.TriState soilDecision = blockstate.canSustainPlant(level, pos.below(), Direction.UP, state);
                if (!soilDecision.isDefault()) flag = soilDecision.isTrue();
                else
                if (blockstate.is(Blocks.END_STONE) || blockstate.is(AstrologicalBlocks.PURPURITE)) {
                    flag = true;
                } else if (blockstate.is(this.plant)) {
                    int j = 1;

                    for (int k = 0; k < 4; k++) {
                        BlockState blockstate1 = level.getBlockState(pos.below(j + 1));
                        if (!blockstate1.is(this.plant)) {
                            net.neoforged.neoforge.common.util.TriState soilDecision2 = blockstate1.canSustainPlant(level, pos.below(j + 1), Direction.UP, state);
                            if (!soilDecision2.isDefault()) flag1 = soilDecision2.isTrue();
                            if (blockstate1.is(Blocks.END_STONE) || blockstate1.is(AstrologicalBlocks.PURPURITE)) {
                                flag1 = true;
                            }
                            break;
                        }

                        j++;
                    }

                    if (j < 2 || j <= random.nextInt(flag1 ? 5 : 4)) {
                        flag = true;
                    }
                } else if (blockstate.isAir()) {
                    flag = true;
                }

                if (flag && allNeighborsEmpty(level, blockpos, null) && level.isEmptyBlock(pos.above(2))) {
                    level.setBlock(pos, ChorusPlantBlock.getStateWithConnections(level, pos, this.plant.defaultBlockState()), 2);
                    this.placeGrownFlower(level, blockpos, i);
                } else if (i < 4) {
                    int l = random.nextInt(4);
                    if (flag1) {
                        l++;
                    }

                    boolean flag2 = false;

                    for (int i1 = 0; i1 < l; i1++) {
                        Direction direction = Direction.Plane.HORIZONTAL.getRandomDirection(random);
                        BlockPos blockpos1 = pos.relative(direction);
                        if (level.isEmptyBlock(blockpos1)
                                && level.isEmptyBlock(blockpos1.below())
                                && allNeighborsEmpty(level, blockpos1, direction.getOpposite())) {
                            this.placeGrownFlower(level, blockpos1, i + 1);
                            flag2 = true;
                        }
                    }

                    if (flag2) {
                        level.setBlock(pos, ChorusPlantBlock.getStateWithConnections(level, pos, this.plant.defaultBlockState()), 2);
                    } else {
                        this.placeDeadFlower(level, pos);
                    }
                } else {
                    this.placeDeadFlower(level, pos);
                }
                net.neoforged.neoforge.common.CommonHooks.fireCropGrowPost(level, pos, state);
            }
        }
    }
}
