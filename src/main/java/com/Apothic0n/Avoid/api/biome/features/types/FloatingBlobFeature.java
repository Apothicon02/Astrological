package com.Apothic0n.Avoid.api.biome.features.types;

import com.Apothic0n.Avoid.api.biome.features.configuartions.FloatingBlobConfiguration;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

import java.util.Random;

public class FloatingBlobFeature extends Feature<FloatingBlobConfiguration> {
    public FloatingBlobFeature(Codec<FloatingBlobConfiguration> pContext) {
        super(pContext);
    }

    public boolean place(FeaturePlaceContext<FloatingBlobConfiguration> pContext) {
        WorldGenLevel worldgenlevel = pContext.level();
        BlockPos blockpos = pContext.origin();
        Random random = pContext.random();
        FloatingBlobConfiguration config = pContext.config();
        blockpos = new BlockPos(blockpos.getX(), ((int)(Math.random()*40))+60, blockpos.getZ());
        Block blobMaterial = config.blobMaterial.getBlock();
        Integer blobMass = config.getBlobMass().sample(random);
        Integer blobWidth = config.getBlobWidth().sample(random);
        Integer blobHeight = config.getBlobHeight().sample(random);
        worldgenlevel.setBlock(blockpos, blobMaterial.defaultBlockState(), 2);
        for(int i = 0; i < blobMass; ++i) {
            BlockPos blockpos1 = blockpos.offset(random.nextInt(blobWidth) - random.nextInt(blobWidth), -random.nextInt(blobHeight), random.nextInt(blobWidth) - random.nextInt(blobWidth));
            BlockState blockAbove = worldgenlevel.getBlockState(blockpos1.above());
            BlockState blockNorth = worldgenlevel.getBlockState(blockpos1.above().north());
            BlockState blockEast = worldgenlevel.getBlockState(blockpos1.above().east());
            BlockState blockSouth = worldgenlevel.getBlockState(blockpos1.above().south());
            BlockState blockWest = worldgenlevel.getBlockState(blockpos1.above().west());
            if (i <= 1 || blockAbove.is(blobMaterial) || blockNorth.is(blobMaterial) || blockEast.is(blobMaterial) || blockWest.is(blobMaterial) || blockSouth.is(blobMaterial)) {
                worldgenlevel.setBlock(blockpos1, blobMaterial.defaultBlockState(), 2);
            }
        }
        return true;
    }
}
