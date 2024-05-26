package com.Apothic0n.Astrological.api.biome.features.types;

import com.Apothic0n.Astrological.core.objects.AstrologicalBlocks;
import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.LegacyRandomSource;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.synth.PerlinSimplexNoise;

public class Basic3x2x3CubeFeature extends Feature<SimpleBlockConfiguration> {
    public Basic3x2x3CubeFeature(Codec<SimpleBlockConfiguration> pContext) {
        super(pContext);
    }
    public static final PerlinSimplexNoise SATURATION_NOISE = new PerlinSimplexNoise(new WorldgenRandom(new LegacyRandomSource(2345L)), ImmutableList.of(0));
    public static final PerlinSimplexNoise BRIGHTNESS_NOISE = new PerlinSimplexNoise(new WorldgenRandom(new LegacyRandomSource(5432L)), ImmutableList.of(0));

    public boolean place(FeaturePlaceContext<SimpleBlockConfiguration> pContext) {
        WorldGenLevel worldGenLevel = pContext.level();
        BlockPos blockPos = pContext.origin();
        RandomSource random = pContext.random();
        BlockState material = pContext.config().toPlace().getState(random, blockPos);
        if (worldGenLevel.isEmptyBlock(blockPos.offset(-1, -1, 0)) || worldGenLevel.isEmptyBlock(blockPos.offset(1, -1, 0)) ||
                worldGenLevel.isEmptyBlock(blockPos.offset(0, -1, -1)) || worldGenLevel.isEmptyBlock(blockPos.offset(0, -1, 1))) {
            return false;
        } else {
            generateCube(worldGenLevel, blockPos, material);
            if ((int)(random.nextFloat()*(3)+1) < 2) {
                generateCube(worldGenLevel, blockPos.above(2), material);
                if ((int)(random.nextFloat()*(3)+1) < 2) {
                    generateCube(worldGenLevel, blockPos.above(4), material);
                    if ((int)(random.nextFloat()*(3)+1) < 2) {
                        generateCube(worldGenLevel, blockPos.above(6), material);
                        if ((int)(random.nextFloat()*(4)+1) < 2) {
                            generateCube(worldGenLevel, blockPos.above(8), material);
                            if ((int)(random.nextFloat()*(4)+1) < 2) {
                                generateCube(worldGenLevel, blockPos.above(10), material);
                            }
                        }
                    }
                }
            }
            return true;
        }
    }
    
    private static void generateCube(WorldGenLevel worldGenLevel, BlockPos blockPos, BlockState material) {
        generateLayer(worldGenLevel, blockPos, material);
        generateLayer(worldGenLevel, blockPos.above(), material);
    }

    private static void generateLayer(WorldGenLevel worldGenLevel, BlockPos blockPos, BlockState material) {
        setBlock(worldGenLevel, blockPos.offset(0, 0, 0), material);
        setBlock(worldGenLevel, blockPos.offset(1, 0, 0), material);
        setBlock(worldGenLevel, blockPos.offset(0, 0, 1), material);
        setBlock(worldGenLevel, blockPos.offset(-1, 0, 0), material);
        setBlock(worldGenLevel, blockPos.offset(0, 0, -1), material);
        setBlock(worldGenLevel, blockPos.offset(-1, 0, -1), material);
        setBlock(worldGenLevel, blockPos.offset(1, 0, 1), material);
        setBlock(worldGenLevel, blockPos.offset(1, 0, -1), material);
        setBlock(worldGenLevel, blockPos.offset(-1, 0, 1), material);
    }

    private static void setBlock(WorldGenLevel worldGenLevel, BlockPos blockPos, BlockState material) {
        if (material.is(AstrologicalBlocks.JADE.get())) {
            int x = blockPos.getX();
            int z = blockPos.getZ();
            double brightness = (BRIGHTNESS_NOISE.getValue(x * 0.02, z * 0.02, false));
            double saturation = (SATURATION_NOISE.getValue(x * 0.02, z * 0.02, false));
            if ((brightness < 0.4 && brightness > 0) || (saturation < 0 && saturation > -0.4)) {
                material = AstrologicalBlocks.LIGHT_JADE.get().defaultBlockState();
            }
        }
        worldGenLevel.setBlock(blockPos, material, 2);
    }
}
