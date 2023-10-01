package com.Apothic0n.Inversia.mixin;

import com.Apothic0n.Inversia.core.objects.InversiaBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
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

    @Shadow @Final private ChorusPlantBlock plant;

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
        if (!blockstate.is(this.plant) && !blockstate.is(Blocks.END_STONE) && !blockstate.is(InversiaBlocks.PURPURITE.get())) {
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
    public void randomTick(BlockState p_220980_, ServerLevel p_220981_, BlockPos p_220982_, RandomSource p_220983_) {
        BlockPos blockpos = p_220982_.above();
        if (p_220981_.isEmptyBlock(blockpos) && blockpos.getY() < p_220981_.getMaxBuildHeight()) {
            int i = p_220980_.getValue(AGE);
            if (i < 5 && net.minecraftforge.common.ForgeHooks.onCropsGrowPre(p_220981_, blockpos, p_220980_, true)) {
                boolean flag = false;
                boolean flag1 = false;
                BlockState blockstate = p_220981_.getBlockState(p_220982_.below());
                if (blockstate.is(Blocks.END_STONE) || blockstate.is(InversiaBlocks.PURPURITE.get())) {
                    flag = true;
                } else if (blockstate.is(this.plant)) {
                    int j = 1;

                    for(int k = 0; k < 4; ++k) {
                        BlockState blockstate1 = p_220981_.getBlockState(p_220982_.below(j + 1));
                        if (!blockstate1.is(this.plant)) {
                            if (blockstate1.is(Blocks.END_STONE) || blockstate1.is(InversiaBlocks.PURPURITE.get())) {
                                flag1 = true;
                            }
                            break;
                        }

                        ++j;
                    }

                    if (j < 2 || j <= p_220983_.nextInt(flag1 ? 5 : 4)) {
                        flag = true;
                    }
                } else if (blockstate.isAir()) {
                    flag = true;
                }

                if (flag && allNeighborsEmpty(p_220981_, blockpos, (Direction)null) && p_220981_.isEmptyBlock(p_220982_.above(2))) {
                    p_220981_.setBlock(p_220982_, this.plant.getStateForPlacement(p_220981_, p_220982_), 2);
                    this.placeGrownFlower(p_220981_, blockpos, i);
                } else if (i < 4) {
                    int l = p_220983_.nextInt(4);
                    if (flag1) {
                        ++l;
                    }

                    boolean flag2 = false;

                    for(int i1 = 0; i1 < l; ++i1) {
                        Direction direction = Direction.Plane.HORIZONTAL.getRandomDirection(p_220983_);
                        BlockPos blockpos1 = p_220982_.relative(direction);
                        if (p_220981_.isEmptyBlock(blockpos1) && p_220981_.isEmptyBlock(blockpos1.below()) && allNeighborsEmpty(p_220981_, blockpos1, direction.getOpposite())) {
                            this.placeGrownFlower(p_220981_, blockpos1, i + 1);
                            flag2 = true;
                        }
                    }

                    if (flag2) {
                        p_220981_.setBlock(p_220982_, this.plant.getStateForPlacement(p_220981_, p_220982_), 2);
                    } else {
                        this.placeDeadFlower(p_220981_, p_220982_);
                    }
                } else {
                    this.placeDeadFlower(p_220981_, p_220982_);
                }
                net.minecraftforge.common.ForgeHooks.onCropsGrowPost(p_220981_, p_220982_, p_220980_);
            }
        }
    }
}
