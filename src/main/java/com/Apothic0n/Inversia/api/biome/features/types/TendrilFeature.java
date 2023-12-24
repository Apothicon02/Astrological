package com.Apothic0n.Inversia.api.biome.features.types;

import com.Apothic0n.Inversia.core.objects.TendrilsUtils;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.PointedDripstoneConfiguration;

import java.util.Optional;

public class TendrilFeature extends Feature<PointedDripstoneConfiguration> {
    public TendrilFeature(Codec<PointedDripstoneConfiguration> pContext) {
        super(pContext);
    }

    public boolean place(FeaturePlaceContext<PointedDripstoneConfiguration> pContext) {
        LevelAccessor levelaccessor = pContext.level();
        BlockPos blockpos = pContext.origin();
        RandomSource random = pContext.random();
        PointedDripstoneConfiguration pointeddripstoneconfiguration = pContext.config();
        Optional<Direction> optional = getTipDirection(levelaccessor, blockpos, random);
        if (optional.isEmpty()) {
            return false;
        } else {
            BlockPos blockpos1 = blockpos.relative(optional.get().getOpposite());
            createPatchOfDripstoneBlocks(levelaccessor, random, blockpos1, pointeddripstoneconfiguration);
            int i = (int) (pointeddripstoneconfiguration.chanceOfTallerDripstone*10);
            int randomNumber = (int)(random.nextFloat()*(i)+1);
            TendrilsUtils.growPointedDripstone(levelaccessor, blockpos, optional.get(), randomNumber, false);
            return true;
        }
    }

    private static Optional<Direction> getTipDirection(LevelAccessor p_225199_, BlockPos p_225200_, RandomSource p_225201_) {
        boolean flag = TendrilsUtils.isDripstoneBase(p_225199_.getBlockState(p_225200_.above()));
        boolean flag1 = TendrilsUtils.isDripstoneBase(p_225199_.getBlockState(p_225200_.below()));
        if (flag && flag1) {
            return Optional.of(p_225201_.nextBoolean() ? Direction.DOWN : Direction.UP);
        } else if (flag) {
            return Optional.of(Direction.DOWN);
        } else {
            return flag1 ? Optional.of(Direction.UP) : Optional.empty();
        }
    }

    private static void createPatchOfDripstoneBlocks(LevelAccessor p_225194_, RandomSource p_225195_, BlockPos p_225196_, PointedDripstoneConfiguration p_225197_) {
        TendrilsUtils.placeDripstoneBlockIfPossible(p_225194_, p_225196_);

        for(Direction direction : Direction.Plane.HORIZONTAL) {
            if (!(p_225195_.nextFloat() > p_225197_.chanceOfDirectionalSpread)) {
                BlockPos blockpos = p_225196_.relative(direction);
                TendrilsUtils.placeDripstoneBlockIfPossible(p_225194_, blockpos);
                if (!(p_225195_.nextFloat() > p_225197_.chanceOfSpreadRadius2)) {
                    BlockPos blockpos1 = blockpos.relative(Direction.getRandom(p_225195_));
                    TendrilsUtils.placeDripstoneBlockIfPossible(p_225194_, blockpos1);
                    if (!(p_225195_.nextFloat() > p_225197_.chanceOfSpreadRadius3)) {
                        BlockPos blockpos2 = blockpos1.relative(Direction.getRandom(p_225195_));
                        TendrilsUtils.placeDripstoneBlockIfPossible(p_225194_, blockpos2);
                    }
                }
            }
        }

    }
}