package com.Apothic0n.Astrological.core.objects;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DripstoneThickness;

import java.util.function.Consumer;

public class TendrilsUtils {
    public static double getDripstoneHeight(double p_159624_, double p_159625_, double p_159626_, double p_159627_) {
        if (p_159624_ < p_159627_) {
            p_159624_ = p_159627_;
        }

        double d0 = 0.384D;
        double d1 = p_159624_ / p_159625_ * 0.384D;
        double d2 = 0.75D * Math.pow(d1, 1.3333333333333333D);
        double d3 = Math.pow(d1, 0.6666666666666666D);
        double d4 = 0.3333333333333333D * Math.log(d1);
        double d5 = p_159626_ * (d2 - d3 - d4);
        d5 = Math.max(d5, 0.0D);
        return d5 / 0.384D * p_159625_;
    }

    public static boolean isCircleMostlyEmbeddedInStone(WorldGenLevel p_159640_, BlockPos p_159641_, int p_159642_) {
        if (isEmptyOrWaterOrLava(p_159640_, p_159641_)) {
            return false;
        } else {
            float f = 6.0F;
            float f1 = 6.0F / (float)p_159642_;

            for(float f2 = 0.0F; f2 < ((float)Math.PI * 2F); f2 += f1) {
                int i = (int)(Mth.cos(f2) * (float)p_159642_);
                int j = (int)(Mth.sin(f2) * (float)p_159642_);
                if (isEmptyOrWaterOrLava(p_159640_, p_159641_.offset(i, 0, j))) {
                    return false;
                }
            }

            return true;
        }
    }

    protected static boolean isEmptyOrWater(LevelAccessor p_159629_, BlockPos p_159630_) {
        return p_159629_.isStateAtPosition(p_159630_, net.minecraft.world.level.levelgen.feature.DripstoneUtils::isEmptyOrWater);
    }

    public static boolean isEmptyOrWaterOrLava(LevelAccessor p_159660_, BlockPos p_159661_) {
        return p_159660_.isStateAtPosition(p_159661_, net.minecraft.world.level.levelgen.feature.DripstoneUtils::isEmptyOrWaterOrLava);
    }

    public static void buildBaseToTipColumn(Direction p_159652_, int p_159653_, boolean p_159654_, Consumer<BlockState> p_159655_) {
        if (p_159653_ >= 3) {
            p_159655_.accept(createPointedDripstone(p_159652_, DripstoneThickness.BASE));

            for(int i = 0; i < p_159653_ - 3; ++i) {
                p_159655_.accept(createPointedDripstone(p_159652_, DripstoneThickness.MIDDLE));
            }
        }

        if (p_159653_ >= 2) {
            p_159655_.accept(createPointedDripstone(p_159652_, DripstoneThickness.FRUSTUM));
        }

        if (p_159653_ >= 1) {
            p_159655_.accept(createPointedDripstone(p_159652_, p_159654_ ? DripstoneThickness.TIP_MERGE : DripstoneThickness.TIP));
        }

    }

    public static void growPointedDripstone(LevelAccessor p_190848_, BlockPos p_190849_, Direction p_190850_, int p_190851_, boolean p_190852_) {
        if (isDripstoneBase(p_190848_.getBlockState(p_190849_.relative(p_190850_.getOpposite())))) {
            BlockPos.MutableBlockPos blockpos$mutableblockpos = p_190849_.mutable();
            buildBaseToTipColumn(p_190850_, p_190851_, p_190852_, (p_277326_) -> {
                if (p_277326_.is(AstrologicalBlocks.TENDRILS.get())) {
                    p_277326_ = p_277326_.setValue(TendrilsBlock.WATERLOGGED, Boolean.valueOf(p_190848_.isWaterAt(blockpos$mutableblockpos)));
                }

                p_190848_.setBlock(blockpos$mutableblockpos, p_277326_, 2);
                blockpos$mutableblockpos.move(p_190850_);
            });
        }
    }

    public static boolean placeDripstoneBlockIfPossible(LevelAccessor p_190854_, BlockPos p_190855_) {
        BlockState blockstate = p_190854_.getBlockState(p_190855_);
        if (blockstate.is(AstrologicalBlocks.TUMOR.get())) {
            p_190854_.setBlock(p_190855_, AstrologicalBlocks.TUMOR.get().defaultBlockState(), 2);
            return true;
        } else {
            return false;
        }
    }

    public static BlockState createPointedDripstone(Direction p_159657_, DripstoneThickness p_159658_) {
        return AstrologicalBlocks.TENDRILS.get().defaultBlockState().setValue(TendrilsBlock.TIP_DIRECTION, p_159657_).setValue(TendrilsBlock.THICKNESS, p_159658_);
    }

    public static boolean isDripstoneBaseOrLava(BlockState p_159650_) {
        return isDripstoneBase(p_159650_) || p_159650_.is(Blocks.LAVA);
    }

    public static boolean isDripstoneBase(BlockState p_159663_) {
        return p_159663_.is(AstrologicalBlocks.TUMOR.get());
    }

    public static boolean isEmptyOrWater(BlockState p_159665_) {
        return p_159665_.isAir() || p_159665_.is(Blocks.WATER);
    }

    public static boolean isNeitherEmptyNorWater(BlockState p_203131_) {
        return !p_203131_.isAir() && !p_203131_.is(Blocks.WATER);
    }

    public static boolean isEmptyOrWaterOrLava(BlockState p_159667_) {
        return p_159667_.isAir() || p_159667_.is(Blocks.WATER) || p_159667_.is(Blocks.LAVA);
    }
}