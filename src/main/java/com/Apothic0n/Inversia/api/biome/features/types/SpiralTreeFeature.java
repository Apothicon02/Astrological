package com.Apothic0n.Inversia.api.biome.features.types;

import com.Apothic0n.Inversia.api.biome.features.configuartions.SpiralTreeConfiguration;
import com.Apothic0n.Inversia.api.biome.features.configuartions.SpiralTreeConfiguration;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.material.Material;

import java.util.Random;
import java.util.Set;

public class SpiralTreeFeature extends Feature<SpiralTreeConfiguration> {
    public SpiralTreeFeature(Codec<SpiralTreeConfiguration> pContext) {
        super(pContext);
    }

    public boolean place(FeaturePlaceContext<SpiralTreeConfiguration> pContext) {
        WorldGenLevel worldgenlevel = pContext.level();
        BlockPos blockpos = pContext.origin();
        Random random = pContext.random();
        SpiralTreeConfiguration config = pContext.config();
        Set<Block> validBlocks = config.validBlocks;
        Block stemMaterial = config.stemMaterial.getBlock();
        Block leafMaterial = config.leafMaterial.getBlock();
        Integer blobMass = config.getBlobMass().sample(random);
        Integer blobWidth = config.getBlobWidth().sample(random);
        Integer blobHeight = config.getBlobHeight().sample(random);
        Integer capSize = config.getCapSize().sample(random);
        if (!worldgenlevel.isEmptyBlock(blockpos) && validBlocks.contains(worldgenlevel.getBlockState(blockpos.above()).getBlock())) {
            worldgenlevel.setBlock(blockpos, stemMaterial.defaultBlockState(), 2);
            BlockPos blockpos1 = blockpos;
            boolean northNegative = false;//x
            boolean eastNegative = false;//z
            int randomNumber = (int)(Math.random()*(4-1+1)+1);
            if (randomNumber >= 4) {
                northNegative = true;
                eastNegative = true;
            } else if (randomNumber >= 3) {
                northNegative = true;
            } else if (randomNumber >= 2) {
                eastNegative = true;
            }
            int xFactor = 1;
            int zFactor = 1;
            if (northNegative) {xFactor = -1;}
            if (eastNegative) {zFactor = -1;}
            int spiralStep = 1;

            for (int i = 0; i < blobMass*4;) {
                int randomNumber2 = (int)(Math.random()*(4)+1);
                if (randomNumber2 >= 4/blobHeight) { //25% chance per number up to 4.
                    blockpos1 = new BlockPos(blockpos1.getX() + xFactor, blockpos1.getY() - 1, blockpos1.getZ() + zFactor);
                } else {
                    blockpos1 = new BlockPos(blockpos1.getX(), blockpos1.getY() - 1, blockpos1.getZ());
                }
                worldgenlevel.setBlock(blockpos1, stemMaterial.defaultBlockState(), 2);
                worldgenlevel.setBlock(blockpos1.below(), stemMaterial.defaultBlockState(), 2);
                worldgenlevel.setBlock(blockpos1.above(), stemMaterial.defaultBlockState(), 2);
                if (spiralStep == 1) {
                    worldgenlevel.setBlock(blockpos1.north(), stemMaterial.defaultBlockState(), 2);
                    worldgenlevel.setBlock(blockpos1.east(), stemMaterial.defaultBlockState(), 2);
                } else if (spiralStep == 2) {
                    worldgenlevel.setBlock(blockpos1.east(), stemMaterial.defaultBlockState(), 2);
                    worldgenlevel.setBlock(blockpos1.south(), stemMaterial.defaultBlockState(), 2);
                } else if (spiralStep == 3) {
                    worldgenlevel.setBlock(blockpos1.south(), stemMaterial.defaultBlockState(), 2);
                    worldgenlevel.setBlock(blockpos1.west(), stemMaterial.defaultBlockState(), 2);
                } else if (spiralStep == 4) {
                    worldgenlevel.setBlock(blockpos1.west(), stemMaterial.defaultBlockState(), 2);
                    worldgenlevel.setBlock(blockpos1.north(), stemMaterial.defaultBlockState(), 2);
                    spiralStep = 0;
                }
                int xDistance = blockpos1.getX() - blockpos.getX();
                int zDistance = blockpos1.getZ() - blockpos.getZ();
                if (xDistance >= blobWidth || zDistance >= blobWidth) {
                    BlockPos blockpos2 = blockpos1.below(2);
                    if (capSize <= 1) {
                        worldgenlevel.setBlock(blockpos2, Blocks.SHROOMLIGHT.defaultBlockState(), 2);
                        worldgenlevel.setBlock(blockpos2, leafMaterial.defaultBlockState(), 2);
                        worldgenlevel.setBlock(blockpos2.north(), leafMaterial.defaultBlockState(), 2);
                        worldgenlevel.setBlock(blockpos2.east(), leafMaterial.defaultBlockState(), 2);
                        if (Math.random()*(4)+1 <= 1) { //25% chance to be missing the south block
                            worldgenlevel.setBlock(blockpos2.south(), leafMaterial.defaultBlockState(), 2);
                        }
                        if (Math.random()*(5)+1 <= 1) { //20% chance to be missing the west and southwest blocks
                            worldgenlevel.setBlock(blockpos2.west(), leafMaterial.defaultBlockState(), 2);
                            worldgenlevel.setBlock(blockpos2.south().west(), leafMaterial.defaultBlockState(), 2);
                        }
                        worldgenlevel.setBlock(blockpos2.south().east(), leafMaterial.defaultBlockState(), 2);
                        worldgenlevel.setBlock(blockpos2.north().west(), leafMaterial.defaultBlockState(), 2);
                        worldgenlevel.setBlock(blockpos2.north().east(), leafMaterial.defaultBlockState(), 2);

                        worldgenlevel.setBlock(blockpos2.below().south().west(), leafMaterial.defaultBlockState(), 2);
                        worldgenlevel.setBlock(blockpos2.below().north().west(), leafMaterial.defaultBlockState(), 2);
                        if (Math.random()*(9)+1 <= 4) { //44% chance to be missing the south and southeast blocks
                            worldgenlevel.setBlock(blockpos2.below().south().east(), leafMaterial.defaultBlockState(), 2);
                            worldgenlevel.setBlock(blockpos2.below().south(), leafMaterial.defaultBlockState(), 2);
                        }
                        if (Math.random()*(9)+1 <= 4) { //44% chance to be missing the north and northeast blocks
                            worldgenlevel.setBlock(blockpos2.below().north().east(), leafMaterial.defaultBlockState(), 2);
                            worldgenlevel.setBlock(blockpos2.below().north(), leafMaterial.defaultBlockState(), 2);
                        } else if (Math.random()*(3)+1 <= 1) { //33% chance to add an additional block below the north and northeast blocks, if they're not missing
                            worldgenlevel.setBlock(blockpos2.below(2).north().east(), leafMaterial.defaultBlockState(), 2);
                            worldgenlevel.setBlock(blockpos2.below(2).north(), leafMaterial.defaultBlockState(), 2);
                        }
                        worldgenlevel.setBlock(blockpos2.below().east(), leafMaterial.defaultBlockState(), 2);
                        worldgenlevel.setBlock(blockpos2.below().west(), leafMaterial.defaultBlockState(), 2);
                    } else if (capSize <= 2) {
                        worldgenlevel.setBlock(blockpos2, Blocks.SHROOMLIGHT.defaultBlockState(), 2);
                        worldgenlevel.setBlock(blockpos2, leafMaterial.defaultBlockState(), 2);
                        worldgenlevel.setBlock(blockpos2.north(), leafMaterial.defaultBlockState(), 2);
                        worldgenlevel.setBlock(blockpos2.east(), leafMaterial.defaultBlockState(), 2);
                        worldgenlevel.setBlock(blockpos2.south(), leafMaterial.defaultBlockState(), 2);
                        if (Math.random()*(3)+1 <= 1) { //33% chance to have an extra block above the south block
                            worldgenlevel.setBlock(blockpos2.above().south(), leafMaterial.defaultBlockState(), 2);
                        }
                        if (Math.random()*(3)+1 <= 1) { //33% chance to have extra blocks above the west and southwest blocks
                            worldgenlevel.setBlock(blockpos2.above().west(), leafMaterial.defaultBlockState(), 2);
                            worldgenlevel.setBlock(blockpos2.above().south().west(), leafMaterial.defaultBlockState(), 2);
                        }
                        worldgenlevel.setBlock(blockpos2.west(), leafMaterial.defaultBlockState(), 2);
                        worldgenlevel.setBlock(blockpos2.south().west(), leafMaterial.defaultBlockState(), 2);
                        worldgenlevel.setBlock(blockpos2.south().east(), leafMaterial.defaultBlockState(), 2);
                        worldgenlevel.setBlock(blockpos2.north().west(), leafMaterial.defaultBlockState(), 2);
                        worldgenlevel.setBlock(blockpos2.north().east(), leafMaterial.defaultBlockState(), 2);

                        worldgenlevel.setBlock(blockpos2.north(2), leafMaterial.defaultBlockState(), 2);
                        worldgenlevel.setBlock(blockpos2.east(2), leafMaterial.defaultBlockState(), 2);
                        worldgenlevel.setBlock(blockpos2.south(2), leafMaterial.defaultBlockState(), 2);
                        worldgenlevel.setBlock(blockpos2.west(2), leafMaterial.defaultBlockState(), 2);
                        worldgenlevel.setBlock(blockpos2.north(2).east(), leafMaterial.defaultBlockState(), 2);
                        worldgenlevel.setBlock(blockpos2.north(2).west(), leafMaterial.defaultBlockState(), 2);
                        worldgenlevel.setBlock(blockpos2.south(2).east(), leafMaterial.defaultBlockState(), 2);
                        worldgenlevel.setBlock(blockpos2.south(2).west(), leafMaterial.defaultBlockState(), 2);
                        worldgenlevel.setBlock(blockpos2.north().east(2), leafMaterial.defaultBlockState(), 2);
                        worldgenlevel.setBlock(blockpos2.north().west(2), leafMaterial.defaultBlockState(), 2);
                        worldgenlevel.setBlock(blockpos2.south().east(2), leafMaterial.defaultBlockState(), 2);
                        worldgenlevel.setBlock(blockpos2.south().west(2), leafMaterial.defaultBlockState(), 2);
                        if (Math.random()*(2)+1 <= 1) { //50% chance to be missing the far corner blocks
                            worldgenlevel.setBlock(blockpos2.north(2).east(2), leafMaterial.defaultBlockState(), 2);
                            worldgenlevel.setBlock(blockpos2.north(2).west(2), leafMaterial.defaultBlockState(), 2);
                            worldgenlevel.setBlock(blockpos2.south(2).east(2), leafMaterial.defaultBlockState(), 2);
                            worldgenlevel.setBlock(blockpos2.south(2).west(2), leafMaterial.defaultBlockState(), 2);
                        }
                        worldgenlevel.setBlock(blockpos2.below().south(2).west(2), leafMaterial.defaultBlockState(), 2);
                        worldgenlevel.setBlock(blockpos2.below().north(2).west(2), leafMaterial.defaultBlockState(), 2);
                        worldgenlevel.setBlock(blockpos2.below().south(2).west(), leafMaterial.defaultBlockState(), 2);
                        worldgenlevel.setBlock(blockpos2.below().north(2).west(), leafMaterial.defaultBlockState(), 2);
                        worldgenlevel.setBlock(blockpos2.below().south().west(2), leafMaterial.defaultBlockState(), 2);
                        worldgenlevel.setBlock(blockpos2.below().north().west(2), leafMaterial.defaultBlockState(), 2);
                        if (Math.random()*(9)+1 <= 4) { //44% chance to be missing the south and southeast blocks
                            worldgenlevel.setBlock(blockpos2.below().south(2).east(), leafMaterial.defaultBlockState(), 2);
                            worldgenlevel.setBlock(blockpos2.below().south().east(2), leafMaterial.defaultBlockState(), 2);
                            worldgenlevel.setBlock(blockpos2.below().south(2).east(2), leafMaterial.defaultBlockState(), 2);
                            worldgenlevel.setBlock(blockpos2.below().south(2), leafMaterial.defaultBlockState(), 2);
                        }
                        if (Math.random()*(9)+1 <= 4) { //44% chance to be missing the north and northeast blocks
                            worldgenlevel.setBlock(blockpos2.below().north(2).east(), leafMaterial.defaultBlockState(), 2);
                            worldgenlevel.setBlock(blockpos2.below().north().east(2), leafMaterial.defaultBlockState(), 2);
                            worldgenlevel.setBlock(blockpos2.below().north(2).east(2), leafMaterial.defaultBlockState(), 2);
                            worldgenlevel.setBlock(blockpos2.below().north(2), leafMaterial.defaultBlockState(), 2);
                        } else if (Math.random()*(3)+1 <= 1) { //33% chance to add an additional block below the north and northeast blocks, if they're not missing
                            worldgenlevel.setBlock(blockpos2.below(2).north(2).east(), leafMaterial.defaultBlockState(), 2);
                            worldgenlevel.setBlock(blockpos2.below(2).north().east(2), leafMaterial.defaultBlockState(), 2);
                            worldgenlevel.setBlock(blockpos2.below(2).north(2).east(2), leafMaterial.defaultBlockState(), 2);
                            worldgenlevel.setBlock(blockpos2.below(2).north(2), leafMaterial.defaultBlockState(), 2);
                        }
                        worldgenlevel.setBlock(blockpos2.below().east(2), leafMaterial.defaultBlockState(), 2);
                        worldgenlevel.setBlock(blockpos2.below().west(2), leafMaterial.defaultBlockState(), 2);
                    } else if (capSize <= 3) {
                        worldgenlevel.setBlock(blockpos2, Blocks.SHROOMLIGHT.defaultBlockState(), 2);
                        worldgenlevel.setBlock(blockpos2, leafMaterial.defaultBlockState(), 2);
                        worldgenlevel.setBlock(blockpos2.north(), leafMaterial.defaultBlockState(), 2);
                        worldgenlevel.setBlock(blockpos2.east(), leafMaterial.defaultBlockState(), 2);
                        worldgenlevel.setBlock(blockpos2.south(), leafMaterial.defaultBlockState(), 2);
                        if (Math.random()*(3)+1 <= 1) { //33% chance to have an extra block above the south block
                            worldgenlevel.setBlock(blockpos2.above().south(), leafMaterial.defaultBlockState(), 2);
                        }
                        if (Math.random()*(3)+1 <= 1) { //33% chance to have extra blocks above the west and southwest blocks
                            worldgenlevel.setBlock(blockpos2.above().west(), leafMaterial.defaultBlockState(), 2);
                            worldgenlevel.setBlock(blockpos2.above().south().west(), leafMaterial.defaultBlockState(), 2);
                        }
                        worldgenlevel.setBlock(blockpos2.west(), leafMaterial.defaultBlockState(), 2);
                        worldgenlevel.setBlock(blockpos2.south().west(), leafMaterial.defaultBlockState(), 2);
                        worldgenlevel.setBlock(blockpos2.south().east(), leafMaterial.defaultBlockState(), 2);
                        worldgenlevel.setBlock(blockpos2.north().west(), leafMaterial.defaultBlockState(), 2);
                        worldgenlevel.setBlock(blockpos2.north().east(), leafMaterial.defaultBlockState(), 2);

                        worldgenlevel.setBlock(blockpos2.north(2), leafMaterial.defaultBlockState(), 2);
                        worldgenlevel.setBlock(blockpos2.east(2), leafMaterial.defaultBlockState(), 2);
                        worldgenlevel.setBlock(blockpos2.south(2), leafMaterial.defaultBlockState(), 2);
                        worldgenlevel.setBlock(blockpos2.west(2), leafMaterial.defaultBlockState(), 2);
                        worldgenlevel.setBlock(blockpos2.north(2).east(), leafMaterial.defaultBlockState(), 2);
                        worldgenlevel.setBlock(blockpos2.north(2).west(), leafMaterial.defaultBlockState(), 2);
                        worldgenlevel.setBlock(blockpos2.south(2).east(), leafMaterial.defaultBlockState(), 2);
                        worldgenlevel.setBlock(blockpos2.south(2).west(), leafMaterial.defaultBlockState(), 2);
                        worldgenlevel.setBlock(blockpos2.north().east(2), leafMaterial.defaultBlockState(), 2);
                        worldgenlevel.setBlock(blockpos2.north().west(2), leafMaterial.defaultBlockState(), 2);
                        worldgenlevel.setBlock(blockpos2.south().east(2), leafMaterial.defaultBlockState(), 2);
                        worldgenlevel.setBlock(blockpos2.south().west(2), leafMaterial.defaultBlockState(), 2);
                        if (Math.random()*(8)+1 <= 1) { //12.5% chance to be missing the far corner blocks
                            worldgenlevel.setBlock(blockpos2.north(2).east(2), leafMaterial.defaultBlockState(), 2);
                            worldgenlevel.setBlock(blockpos2.north(2).west(2), leafMaterial.defaultBlockState(), 2);
                            worldgenlevel.setBlock(blockpos2.south(2).east(2), leafMaterial.defaultBlockState(), 2);
                            worldgenlevel.setBlock(blockpos2.south(2).west(2), leafMaterial.defaultBlockState(), 2);
                        }
                        worldgenlevel.setBlock(blockpos2.below().south(2).west(2), leafMaterial.defaultBlockState(), 2);
                        worldgenlevel.setBlock(blockpos2.below().north(2).west(2), leafMaterial.defaultBlockState(), 2);
                        worldgenlevel.setBlock(blockpos2.below().south(2).west(), leafMaterial.defaultBlockState(), 2);
                        worldgenlevel.setBlock(blockpos2.below().north(2).west(), leafMaterial.defaultBlockState(), 2);
                        worldgenlevel.setBlock(blockpos2.below().south().west(2), leafMaterial.defaultBlockState(), 2);
                        worldgenlevel.setBlock(blockpos2.below().north().west(2), leafMaterial.defaultBlockState(), 2);
                        if (Math.random()*(15)+1 <= 1) { //6.6% chance to be missing the south and southeast blocks
                            worldgenlevel.setBlock(blockpos2.below().south(2).east(), leafMaterial.defaultBlockState(), 2);
                            worldgenlevel.setBlock(blockpos2.below().south().east(2), leafMaterial.defaultBlockState(), 2);
                            worldgenlevel.setBlock(blockpos2.below().south(2).east(2), leafMaterial.defaultBlockState(), 2);
                            worldgenlevel.setBlock(blockpos2.below().south(2), leafMaterial.defaultBlockState(), 2);
                        }
                        if (Math.random()*(15)+1 <= 1) { //6.6% chance to be missing the north and northeast blocks
                            worldgenlevel.setBlock(blockpos2.below().north(2).east(), leafMaterial.defaultBlockState(), 2);
                            worldgenlevel.setBlock(blockpos2.below().north().east(2), leafMaterial.defaultBlockState(), 2);
                            worldgenlevel.setBlock(blockpos2.below().north(2).east(2), leafMaterial.defaultBlockState(), 2);
                            worldgenlevel.setBlock(blockpos2.below().north(2), leafMaterial.defaultBlockState(), 2);
                        }
                        worldgenlevel.setBlock(blockpos2.below().east(2), leafMaterial.defaultBlockState(), 2);
                        worldgenlevel.setBlock(blockpos2.below().west(2), leafMaterial.defaultBlockState(), 2);



                        worldgenlevel.setBlock(blockpos2.north(3), leafMaterial.defaultBlockState(), 2);
                        worldgenlevel.setBlock(blockpos2.east(3), leafMaterial.defaultBlockState(), 2);
                        worldgenlevel.setBlock(blockpos2.south(3), leafMaterial.defaultBlockState(), 2);
                        worldgenlevel.setBlock(blockpos2.west(3), leafMaterial.defaultBlockState(), 2);
                        worldgenlevel.setBlock(blockpos2.north(3).east(), leafMaterial.defaultBlockState(), 2);
                        worldgenlevel.setBlock(blockpos2.north(3).west(), leafMaterial.defaultBlockState(), 2);
                        worldgenlevel.setBlock(blockpos2.south(3).east(), leafMaterial.defaultBlockState(), 2);
                        worldgenlevel.setBlock(blockpos2.south(3).west(), leafMaterial.defaultBlockState(), 2);
                        worldgenlevel.setBlock(blockpos2.north().east(3), leafMaterial.defaultBlockState(), 2);
                        worldgenlevel.setBlock(blockpos2.north().west(3), leafMaterial.defaultBlockState(), 2);
                        worldgenlevel.setBlock(blockpos2.south().east(3), leafMaterial.defaultBlockState(), 2);
                        worldgenlevel.setBlock(blockpos2.south().west(3), leafMaterial.defaultBlockState(), 2);
                        worldgenlevel.setBlock(blockpos2.north(3).east(2), leafMaterial.defaultBlockState(), 2);
                        worldgenlevel.setBlock(blockpos2.north(3).west(2), leafMaterial.defaultBlockState(), 2);
                        worldgenlevel.setBlock(blockpos2.south(3).east(2), leafMaterial.defaultBlockState(), 2);
                        worldgenlevel.setBlock(blockpos2.south(3).west(2), leafMaterial.defaultBlockState(), 2);
                        worldgenlevel.setBlock(blockpos2.north(2).east(3), leafMaterial.defaultBlockState(), 2);
                        worldgenlevel.setBlock(blockpos2.north(2).west(3), leafMaterial.defaultBlockState(), 2);
                        worldgenlevel.setBlock(blockpos2.south(2).east(3), leafMaterial.defaultBlockState(), 2);
                        worldgenlevel.setBlock(blockpos2.south(2).west(3), leafMaterial.defaultBlockState(), 2);
                        if (Math.random()*(5)+1 <= 1) { //20% chance to be missing the far corner blocks
                            worldgenlevel.setBlock(blockpos2.north(3).east(3), leafMaterial.defaultBlockState(), 2);
                            worldgenlevel.setBlock(blockpos2.north(3).west(3), leafMaterial.defaultBlockState(), 2);
                            worldgenlevel.setBlock(blockpos2.south(3).east(3), leafMaterial.defaultBlockState(), 2);
                            worldgenlevel.setBlock(blockpos2.south(3).west(3), leafMaterial.defaultBlockState(), 2);
                        }
                        worldgenlevel.setBlock(blockpos2.below().south(3).west(3), leafMaterial.defaultBlockState(), 2);
                        worldgenlevel.setBlock(blockpos2.below().north(3).west(3), leafMaterial.defaultBlockState(), 2);
                        worldgenlevel.setBlock(blockpos2.below().south(3).west(), leafMaterial.defaultBlockState(), 2);
                        worldgenlevel.setBlock(blockpos2.below().north(3).west(), leafMaterial.defaultBlockState(), 2);
                        worldgenlevel.setBlock(blockpos2.below().south().west(3), leafMaterial.defaultBlockState(), 2);
                        worldgenlevel.setBlock(blockpos2.below().north().west(3), leafMaterial.defaultBlockState(), 2);
                        if (Math.random()*(5)+1 <= 1) { //20% chance to be missing the south and southeast blocks
                            worldgenlevel.setBlock(blockpos2.below().south(3).east(), leafMaterial.defaultBlockState(), 2);
                            worldgenlevel.setBlock(blockpos2.below().south().east(3), leafMaterial.defaultBlockState(), 2);
                            worldgenlevel.setBlock(blockpos2.below().south(3).east(3), leafMaterial.defaultBlockState(), 2);
                            worldgenlevel.setBlock(blockpos2.below().south(3), leafMaterial.defaultBlockState(), 2);
                        }
                        if (Math.random()*(5)+1 <= 1) { //20% chance to be missing the north and northeast blocks
                            worldgenlevel.setBlock(blockpos2.below().north(3).east(), leafMaterial.defaultBlockState(), 2);
                            worldgenlevel.setBlock(blockpos2.below().north().east(3), leafMaterial.defaultBlockState(), 2);
                            worldgenlevel.setBlock(blockpos2.below().north(3).east(3), leafMaterial.defaultBlockState(), 2);
                            worldgenlevel.setBlock(blockpos2.below().north(3), leafMaterial.defaultBlockState(), 2);
                        }
                        worldgenlevel.setBlock(blockpos2.below().east(3), leafMaterial.defaultBlockState(), 2);
                        worldgenlevel.setBlock(blockpos2.below().west(3), leafMaterial.defaultBlockState(), 2);



                        worldgenlevel.setBlock(blockpos2.below(2).north(3), leafMaterial.defaultBlockState(), 2);
                        worldgenlevel.setBlock(blockpos2.below(2).east(3), leafMaterial.defaultBlockState(), 2);
                        worldgenlevel.setBlock(blockpos2.below(2).south(3), leafMaterial.defaultBlockState(), 2);
                        worldgenlevel.setBlock(blockpos2.below(2).west(3), leafMaterial.defaultBlockState(), 2);
                        worldgenlevel.setBlock(blockpos2.below(2).north(3).east(), leafMaterial.defaultBlockState(), 2);
                        worldgenlevel.setBlock(blockpos2.below(2).north(3).west(), leafMaterial.defaultBlockState(), 2);
                        worldgenlevel.setBlock(blockpos2.below(2).south(3).east(), leafMaterial.defaultBlockState(), 2);
                        worldgenlevel.setBlock(blockpos2.below(2).south(3).west(), leafMaterial.defaultBlockState(), 2);
                        worldgenlevel.setBlock(blockpos2.below(2).north().east(3), leafMaterial.defaultBlockState(), 2);
                        worldgenlevel.setBlock(blockpos2.below(2).north().west(3), leafMaterial.defaultBlockState(), 2);
                        worldgenlevel.setBlock(blockpos2.below(2).south().east(3), leafMaterial.defaultBlockState(), 2);
                        worldgenlevel.setBlock(blockpos2.below(2).south().west(3), leafMaterial.defaultBlockState(), 2);
                        worldgenlevel.setBlock(blockpos2.below(2).north(3).east(2), leafMaterial.defaultBlockState(), 2);
                        worldgenlevel.setBlock(blockpos2.below(2).north(3).west(2), leafMaterial.defaultBlockState(), 2);
                        worldgenlevel.setBlock(blockpos2.below(2).south(3).east(2), leafMaterial.defaultBlockState(), 2);
                        worldgenlevel.setBlock(blockpos2.below(2).south(3).west(2), leafMaterial.defaultBlockState(), 2);
                        worldgenlevel.setBlock(blockpos2.below(2).north(2).east(3), leafMaterial.defaultBlockState(), 2);
                        worldgenlevel.setBlock(blockpos2.below(2).north(2).west(3), leafMaterial.defaultBlockState(), 2);
                        worldgenlevel.setBlock(blockpos2.below(2).south(2).east(3), leafMaterial.defaultBlockState(), 2);
                        worldgenlevel.setBlock(blockpos2.below(2).south(2).west(3), leafMaterial.defaultBlockState(), 2);
                        if (Math.random()*(2)+1 <= 1) { //50% chance to be missing the far corner blocks
                            worldgenlevel.setBlock(blockpos2.below(2).north(3).east(3), leafMaterial.defaultBlockState(), 2);
                            worldgenlevel.setBlock(blockpos2.below(2).north(3).west(3), leafMaterial.defaultBlockState(), 2);
                            worldgenlevel.setBlock(blockpos2.below(2).south(3).east(3), leafMaterial.defaultBlockState(), 2);
                            worldgenlevel.setBlock(blockpos2.below(2).south(3).west(3), leafMaterial.defaultBlockState(), 2);
                        }
                        worldgenlevel.setBlock(blockpos2.below(3).south(3).west(3), leafMaterial.defaultBlockState(), 2);
                        worldgenlevel.setBlock(blockpos2.below(3).north(3).west(3), leafMaterial.defaultBlockState(), 2);
                        worldgenlevel.setBlock(blockpos2.below(3).south(3).west(), leafMaterial.defaultBlockState(), 2);
                        worldgenlevel.setBlock(blockpos2.below(3).north(3).west(), leafMaterial.defaultBlockState(), 2);
                        worldgenlevel.setBlock(blockpos2.below(3).south().west(3), leafMaterial.defaultBlockState(), 2);
                        worldgenlevel.setBlock(blockpos2.below(3).north().west(3), leafMaterial.defaultBlockState(), 2);
                        if (Math.random()*(9)+1 <= 4) { //44% chance to be missing the south and southeast blocks
                            worldgenlevel.setBlock(blockpos2.below(3).south(3).east(), leafMaterial.defaultBlockState(), 2);
                            worldgenlevel.setBlock(blockpos2.below(3).south().east(3), leafMaterial.defaultBlockState(), 2);
                            worldgenlevel.setBlock(blockpos2.below(3).south(3).east(3), leafMaterial.defaultBlockState(), 2);
                            worldgenlevel.setBlock(blockpos2.below(3).south(3), leafMaterial.defaultBlockState(), 2);
                        }
                        if (Math.random()*(9)+1 <= 4) { //44% chance to be missing the north and northeast blocks
                            worldgenlevel.setBlock(blockpos2.below(3).north(3).east(), leafMaterial.defaultBlockState(), 2);
                            worldgenlevel.setBlock(blockpos2.below(3).north().east(3), leafMaterial.defaultBlockState(), 2);
                            worldgenlevel.setBlock(blockpos2.below(3).north(3).east(3), leafMaterial.defaultBlockState(), 2);
                            worldgenlevel.setBlock(blockpos2.below(3).north(3), leafMaterial.defaultBlockState(), 2);
                        } else if (Math.random()*(9)+1 <= 4) { //44% chance to add an additional block below the north and northeast blocks, if they're not missing
                            worldgenlevel.setBlock(blockpos2.below(4).north(3).east(), leafMaterial.defaultBlockState(), 2);
                            worldgenlevel.setBlock(blockpos2.below(4).north().east(3), leafMaterial.defaultBlockState(), 2);
                            worldgenlevel.setBlock(blockpos2.below(4).north(3).east(3), leafMaterial.defaultBlockState(), 2);
                            worldgenlevel.setBlock(blockpos2.below(4).north(3), leafMaterial.defaultBlockState(), 2);
                            if (Math.random()*(2)+1 <= 1) { //50% chance to add an additional block below those, if they exist
                                worldgenlevel.setBlock(blockpos2.below(5).north(3).east(), leafMaterial.defaultBlockState(), 2);
                                worldgenlevel.setBlock(blockpos2.below(5).north().east(3), leafMaterial.defaultBlockState(), 2);
                                worldgenlevel.setBlock(blockpos2.below(5).north(3).east(3), leafMaterial.defaultBlockState(), 2);
                                worldgenlevel.setBlock(blockpos2.below(5).north(3), leafMaterial.defaultBlockState(), 2);
                            }
                        }
                        worldgenlevel.setBlock(blockpos2.below().east(3), leafMaterial.defaultBlockState(), 2);
                        worldgenlevel.setBlock(blockpos2.below().west(3), leafMaterial.defaultBlockState(), 2);
                    }
                    break;
                } else if (randomNumber2 >= 4/blobHeight && !(xDistance >= blobWidth - 4) && !(zDistance >= blobWidth - 4) && !leafMaterial.defaultBlockState().getMaterial().equals(Material.AIR)) {
                    for (int b = 1; b <= 4;) {
                        int randomNumber3 = (int)(Math.random()*(2));
                        if (randomNumber3 >= 1) {
                            placeBranch(worldgenlevel, blockpos1.below(b).north(randomNumber3).east(randomNumber3 - 1), leafMaterial.defaultBlockState());
                            b++;
                        } else {
                            b = 5;
                            int randomNumber4 = (int)(Math.random()*(8));
                            if (randomNumber4 >= 7 && Blocks.WARPED_WART_BLOCK.equals(leafMaterial)) {
                                worldgenlevel.setBlock(blockpos1.below(b), Blocks.SHROOMLIGHT.defaultBlockState(), 2);
                            }
                        }
                    }
                }
                spiralStep++;
                i += 3;
            }
            return true;
        }
        return false;
    }

    private void placeBranch(WorldGenLevel level, BlockPos blockPos, BlockState material) {
        placeLeafBlock(level, blockPos.north(), material);
        placeLeafBlock(level, blockPos.east(), material);
        placeLeafBlock(level, blockPos.south(), material);
        placeLeafBlock(level, blockPos.west(), material);
        placeLeafBlock(level, blockPos.north().east(), material);
        placeLeafBlock(level, blockPos.south().east(), material);
        placeLeafBlock(level, blockPos.north().west(), material);
        placeLeafBlock(level, blockPos.south().west(), material);
        blockPos = blockPos.below();
        placeLeafBlock(level, blockPos.north().east(), material);
        placeLeafBlock(level, blockPos.south().east(), material);
        placeLeafBlock(level, blockPos.north().west(), material);
        placeLeafBlock(level, blockPos.south().west(), material);
    }

    private void placeLeafBlock(WorldGenLevel level, BlockPos blockPos, BlockState material) {
        if (level.getBlockState(blockPos).getMaterial() == Material.AIR) {
            level.setBlock(blockPos, material, 2);
        }
    }
}
