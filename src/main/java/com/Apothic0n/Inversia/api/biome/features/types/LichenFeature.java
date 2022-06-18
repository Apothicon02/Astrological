package com.Apothic0n.Inversia.api.biome.features.types;

import com.Apothic0n.Inversia.api.biome.features.configuartions.LichenConfiguration;
import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.GlowLichenBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class LichenFeature extends Feature<LichenConfiguration> {
    public LichenFeature(Codec<LichenConfiguration> pContext) {
        super(pContext);
    }

    public boolean place(FeaturePlaceContext<LichenConfiguration> pContext) {
        WorldGenLevel worldgenlevel = pContext.level();
        BlockPos blockpos = pContext.origin();
        RandomSource random = pContext.random();
        LichenConfiguration lichenconfiguration = pContext.config();
        if (!isAirOrWater(worldgenlevel.getBlockState(blockpos))) {
            return false;
        } else {
            List<Direction> list = getShuffledDirections(lichenconfiguration, random);
            if (placeLichenIfPossible(worldgenlevel, blockpos, worldgenlevel.getBlockState(blockpos), lichenconfiguration, random, list)) {
                return true;
            } else {
                BlockPos.MutableBlockPos blockpos$mutableblockpos = blockpos.mutable();

                for(Direction direction : list) {
                    blockpos$mutableblockpos.set(blockpos);
                    List<Direction> list1 = getShuffledDirectionsExcept(lichenconfiguration, random, direction.getOpposite());

                    for(int i = 0; i < lichenconfiguration.searchRange; ++i) {
                        blockpos$mutableblockpos.setWithOffset(blockpos, direction);
                        BlockState blockstate = worldgenlevel.getBlockState(blockpos$mutableblockpos);
                        if (placeLichenIfPossible(worldgenlevel, blockpos$mutableblockpos, blockstate, lichenconfiguration, random, list1)) {
                            return true;
                        }
                    }
                }

                return false;
            }
        }
    }

    public static boolean placeLichenIfPossible(WorldGenLevel pLevel, BlockPos pPos, BlockState pState, LichenConfiguration pConfig, RandomSource pRandom, List<Direction> pDirections) {
        BlockPos.MutableBlockPos blockpos$mutableblockpos = pPos.mutable();

        for(Direction direction : pDirections) {
            BlockState blockstate = pLevel.getBlockState(blockpos$mutableblockpos.setWithOffset(pPos, direction));
            if (pConfig.canBePlacedOn.contains(blockstate.getBlock())) {
                GlowLichenBlock lichenblock = (GlowLichenBlock)pConfig.material.getBlock();
                BlockState blockstate1 = lichenblock.getStateForPlacement(pState, pLevel, pPos, direction);
                if (blockstate1 == null) {
                    return false;
                }

                pLevel.setBlock(pPos, blockstate1, 3);
                pLevel.getChunk(pPos).markPosForPostprocessing(pPos);
                if (pRandom.nextFloat() < pConfig.chanceOfSpreading) {
                    lichenblock.getSpreader().spreadFromFaceTowardRandomDirection(blockstate1, pLevel, pPos, direction, pRandom, true);
                }

                return true;
            }
        }

        return false;
    }

    public static List<Direction> getShuffledDirections(LichenConfiguration pConfig, RandomSource pRandom) {
        List<Direction> list = Lists.newArrayList(pConfig.validDirections);
        Collections.shuffle(list, (Random) pRandom);
        return list;
    }

    public static List<Direction> getShuffledDirectionsExcept(LichenConfiguration pConfig, RandomSource pRandom, Direction pExcludedDirection) {
        List<Direction> list = pConfig.validDirections.stream().filter((p_159857_) -> {
            return p_159857_ != pExcludedDirection;
        }).collect(Collectors.toList());
        Collections.shuffle(list, (Random) pRandom);
        return list;
    }

    private static boolean isAirOrWater(BlockState pState) {
        return pState.isAir() || pState.is(Blocks.WATER);
    }
}
