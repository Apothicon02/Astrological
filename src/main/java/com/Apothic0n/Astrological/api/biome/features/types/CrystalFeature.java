package com.Apothic0n.Astrological.api.biome.features.types;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;

import java.util.ArrayList;
import java.util.List;

import static net.minecraft.world.level.block.Block.UPDATE_ALL;

public class CrystalFeature extends Feature<SimpleBlockConfiguration> {
    public CrystalFeature(Codec<SimpleBlockConfiguration> pContext) {
        super(pContext);
    }

    public static List<List<Block>> lootBlocks = List.of(
            List.of(Blocks.COAL_ORE, Blocks.DEEPSLATE_COAL_ORE, Blocks.DEEPSLATE),
            List.of(Blocks.IRON_ORE, Blocks.DEEPSLATE_IRON_ORE, Blocks.RAW_IRON_BLOCK, Blocks.STONE, Blocks.ANDESITE, Blocks.TUFF),
            List.of(Blocks.COPPER_ORE, Blocks.DEEPSLATE_COPPER_ORE, Blocks.RAW_COPPER_BLOCK, Blocks.STONE, Blocks.GRANITE, Blocks.DRIPSTONE_BLOCK),
            List.of(Blocks.GOLD_ORE, Blocks.DEEPSLATE_GOLD_ORE, Blocks.RAW_GOLD_BLOCK, Blocks.TERRACOTTA, Blocks.YELLOW_TERRACOTTA, Blocks.ORANGE_TERRACOTTA, Blocks.RED_TERRACOTTA),
            List.of(Blocks.LAPIS_ORE, Blocks.DEEPSLATE_LAPIS_ORE, Blocks.WATER),
            List.of(Blocks.EMERALD_ORE, Blocks.DEEPSLATE_EMERALD_ORE, Blocks.SLIME_BLOCK),
            List.of(Blocks.DIAMOND_ORE, Blocks.DEEPSLATE_DIAMOND_ORE, Blocks.PRISMARINE, Blocks.PRISMARINE_BRICKS, Blocks.DARK_PRISMARINE, Blocks.SEA_LANTERN, Blocks.WATER),
            List.of(Blocks.REDSTONE_ORE, Blocks.DEEPSLATE_REDSTONE_ORE, Blocks.REDSTONE_BLOCK, Blocks.RED_SANDSTONE, Blocks.GRANITE, Blocks.LAVA),
            List.of(Blocks.NETHER_QUARTZ_ORE, Blocks.NETHER_GOLD_ORE, Blocks.NETHERRACK, Blocks.NETHER_BRICKS, Blocks.LAVA),
            List.of(Blocks.GLOWSTONE, Blocks.OCHRE_FROGLIGHT, Blocks.SHROOMLIGHT),
            List.of(Blocks.RED_MUSHROOM_BLOCK, Blocks.MUSHROOM_STEM, Blocks.SHROOMLIGHT),
            List.of(Blocks.BROWN_MUSHROOM_BLOCK, Blocks.MUSHROOM_STEM, Blocks.SHROOMLIGHT),
            List.of(Blocks.HONEY_BLOCK, Blocks.HONEYCOMB_BLOCK, Blocks.BEE_NEST),
            List.of(Blocks.ICE, Blocks.PACKED_ICE, Blocks.BLUE_ICE, Blocks.SNOW_BLOCK),
            List.of(Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.ROOTED_DIRT, Blocks.COARSE_DIRT, Blocks.PODZOL, Blocks.MYCELIUM, Blocks.MOSS_BLOCK, Blocks.MUD, Blocks.PACKED_MUD),
            List.of(Blocks.OAK_LOG, Blocks.OAK_PLANKS, Blocks.OAK_LEAVES),
            List.of(Blocks.BIRCH_LOG, Blocks.BIRCH_PLANKS, Blocks.BIRCH_LEAVES),
            List.of(Blocks.DARK_OAK_LOG, Blocks.DARK_OAK_PLANKS, Blocks.DARK_OAK_LEAVES),
            List.of(Blocks.SPRUCE_LOG, Blocks.SPRUCE_PLANKS, Blocks.SPRUCE_LEAVES),
            List.of(Blocks.ACACIA_LOG, Blocks.ACACIA_PLANKS, Blocks.ACACIA_LEAVES),
            List.of(Blocks.JUNGLE_LOG, Blocks.JUNGLE_PLANKS, Blocks.JUNGLE_LEAVES),
            List.of(Blocks.MANGROVE_LOG, Blocks.MANGROVE_PLANKS, Blocks.MANGROVE_ROOTS, Blocks.MANGROVE_LEAVES),
            List.of(Blocks.CHERRY_LOG, Blocks.CHERRY_PLANKS, Blocks.CHERRY_LEAVES),
            List.of(Blocks.WARPED_STEM, Blocks.WARPED_PLANKS, Blocks.WARPED_WART_BLOCK, Blocks.SHROOMLIGHT),
            List.of(Blocks.CRIMSON_STEM, Blocks.CRIMSON_PLANKS, Blocks.NETHER_WART_BLOCK, Blocks.SHROOMLIGHT),
            List.of(Blocks.SAND, Blocks.SANDSTONE, Blocks.RED_SAND, Blocks.RED_SANDSTONE),
            List.of(Blocks.GRAVEL, Blocks.STONE, Blocks.DEEPSLATE, Blocks.TUFF),
            List.of(Blocks.CALCITE, Blocks.QUARTZ_BLOCK, Blocks.PURPUR_PILLAR, Blocks.CHISELED_QUARTZ_BLOCK, Blocks.SMOOTH_QUARTZ),
            List.of(Blocks.PURPUR_BLOCK, Blocks.PURPUR_PILLAR, Blocks.OBSIDIAN, Blocks.CRYING_OBSIDIAN, Blocks.END_STONE),
            List.of(Blocks.DIORITE, Blocks.ANDESITE, Blocks.CALCITE),
            List.of(Blocks.GRANITE, Blocks.DRIPSTONE_BLOCK, Blocks.WATER),
            List.of(Blocks.BASALT, Blocks.SMOOTH_BASALT, Blocks.BLACKSTONE, Blocks.GILDED_BLACKSTONE, Blocks.LAVA),
            List.of(Blocks.DARK_PRISMARINE, Blocks.PRISMARINE, Blocks.PRISMARINE_BRICKS, Blocks.SEA_LANTERN, Blocks.WATER),
            List.of(Blocks.WARPED_NYLIUM, Blocks.CRIMSON_NYLIUM, Blocks.NETHERRACK, Blocks.LAVA),
            List.of(Blocks.OCHRE_FROGLIGHT, Blocks.VERDANT_FROGLIGHT, Blocks.PEARLESCENT_FROGLIGHT),
            List.of(Blocks.OCHRE_FROGLIGHT),
            List.of(Blocks.VERDANT_FROGLIGHT),
            List.of(Blocks.PEARLESCENT_FROGLIGHT));

    public boolean place(FeaturePlaceContext<SimpleBlockConfiguration> pContext) {
        WorldGenLevel worldGenLevel = pContext.level();
        BlockPos blockPos = pContext.origin();
        BlockPos origin = blockPos;
        RandomSource random = pContext.random();
        BlockState material = pContext.config().toPlace().getState(random, blockPos);
        List<BlockPos> positions = new ArrayList<>(List.of());

        positions.add(checkBlockPos(worldGenLevel, blockPos));
        positions.add(checkBlockPos(worldGenLevel, blockPos.above()));
        positions.addAll(generateSmallCircle(worldGenLevel, blockPos.above(2)));
        positions.addAll(generateSmallCircle(worldGenLevel, blockPos.above(3)));
        positions.addAll(generateMediumCircle(worldGenLevel, blockPos.above(4)));
        positions.addAll(generateSmallCircle(worldGenLevel, blockPos.above(5)));
        positions.addAll(generateSmallCircle(worldGenLevel, blockPos.above(6)));
        positions.add(checkBlockPos(worldGenLevel, blockPos.above(7)));
        positions.add(checkBlockPos(worldGenLevel, blockPos.above(8)));

        int randomNumber = (int)(random.nextFloat()*(3)+1);
        if (randomNumber < 2) { //33% chance to generate big crystal above small one
            blockPos = blockPos.above(15);
            origin = blockPos.above(6);
        } else if (randomNumber < 3) { //33% chance to generate big crystal below small one
            blockPos = blockPos.below(15);
            origin = blockPos.above(6);
        } else {
            origin = blockPos.above(22);
        }

        if (randomNumber < 3) { //66% chance to generate big crystal
            positions.add(checkBlockPos(worldGenLevel, blockPos));
            positions.add(checkBlockPos(worldGenLevel, blockPos.above()));
            positions.addAll(generateSmallCircle(worldGenLevel, blockPos.above(2)));
            positions.addAll(generateSmallCircle(worldGenLevel, blockPos.above(3)));
            positions.addAll(generateMediumCircle(worldGenLevel, blockPos.above(4)));
            positions.addAll(generateMediumCircle(worldGenLevel, blockPos.above(5)));
            positions.addAll(generateMediumCircle(worldGenLevel, blockPos.above(6)));
            positions.addAll(generateMediumCircle(worldGenLevel, blockPos.above(7)));
            positions.addAll(generateMediumCircle(worldGenLevel, blockPos.above(8)));
            positions.addAll(generateSmallCircle(worldGenLevel, blockPos.above(9)));
            positions.addAll(generateSmallCircle(worldGenLevel, blockPos.above(10)));
            positions.add(checkBlockPos(worldGenLevel, blockPos.above(11)));
            positions.add(checkBlockPos(worldGenLevel, blockPos.above(12)));
        }

        List<Block> insideMaterial = lootBlocks.get((int)(random.nextFloat()*(lootBlocks.size())));

        List<BlockPos> validPositions = new ArrayList<>(List.of());
        for (int i = 0; i < positions.size(); i++) {
            if (positions.get(i).getX() == 0) {
                return false;
            } else {
                validPositions.add(positions.get(i));
            }
        }
        for (int i = 0; i < validPositions.size(); i++) {
            BlockState block = material;
            BlockPos pos = validPositions.get(i);
            if (pos.getX() - origin.getX() < 2 && pos.getY() - origin.getY() < 2 && pos.getZ() - origin.getZ() < 2 &&
                    pos.getX() - origin.getX() > -2 && pos.getY() - origin.getY() > -2 && pos.getZ() - origin.getZ() > -2) {
                block = insideMaterial.get((int)(random.nextFloat()*(insideMaterial.size()))).defaultBlockState();
            }
            worldGenLevel.setBlock(pos, block, UPDATE_ALL);
        }
        return true;
    }

    private BlockPos checkBlockPos(WorldGenLevel worldGenLevel, BlockPos blockPos) {
        if (worldGenLevel.getBlockState(blockPos).getBlock().equals(Blocks.AIR)) {
            return blockPos;
        }
        return new BlockPos(0, 0, 0);
    }


    private List<BlockPos> generateSmallCircle(WorldGenLevel worldGenLevel, BlockPos blockPos) {
        List<BlockPos> positions = new ArrayList<>(List.of());

        positions.add(checkBlockPos(worldGenLevel, blockPos));
        positions.add(checkBlockPos(worldGenLevel, blockPos.north()));
        positions.add(checkBlockPos(worldGenLevel, blockPos).east());
        positions.add(checkBlockPos(worldGenLevel, blockPos).south());
        positions.add(checkBlockPos(worldGenLevel, blockPos).west());
        positions.add(checkBlockPos(worldGenLevel, blockPos).north().east());
        positions.add(checkBlockPos(worldGenLevel, blockPos).north().west());
        positions.add(checkBlockPos(worldGenLevel, blockPos).south().east());
        positions.add(checkBlockPos(worldGenLevel, blockPos).south().west());

        return positions;
    }
    private List<BlockPos> generateMediumCircle(WorldGenLevel worldGenLevel, BlockPos blockPos) {
        List<BlockPos> positions = generateSmallCircle(worldGenLevel, blockPos);

        positions.add(checkBlockPos(worldGenLevel, blockPos.north(2)));
        positions.add(checkBlockPos(worldGenLevel, blockPos).east(2));
        positions.add(checkBlockPos(worldGenLevel, blockPos).south(2));
        positions.add(checkBlockPos(worldGenLevel, blockPos).west(2));
        positions.add(checkBlockPos(worldGenLevel, blockPos).north(2).east());
        positions.add(checkBlockPos(worldGenLevel, blockPos).north(2).west());
        positions.add(checkBlockPos(worldGenLevel, blockPos).south(2).east());
        positions.add(checkBlockPos(worldGenLevel, blockPos).south(2).west());
        positions.add(checkBlockPos(worldGenLevel, blockPos).north().east(2));
        positions.add(checkBlockPos(worldGenLevel, blockPos).north().west(2));
        positions.add(checkBlockPos(worldGenLevel, blockPos).south().east(2));
        positions.add(checkBlockPos(worldGenLevel, blockPos).south().west(2));
        positions.add(checkBlockPos(worldGenLevel, blockPos).north(2).east(2));
        positions.add(checkBlockPos(worldGenLevel, blockPos).north(2).west(2));
        positions.add(checkBlockPos(worldGenLevel, blockPos).south(2).east(2));
        positions.add(checkBlockPos(worldGenLevel, blockPos).south(2).west(2));
        
        return positions;
    }

    private List<BlockPos> generateLargeCircle(WorldGenLevel worldGenLevel, BlockPos blockPos) {
        List<BlockPos> positions = generateMediumCircle(worldGenLevel, blockPos);

        positions.add(checkBlockPos(worldGenLevel, blockPos.north(3)));
        positions.add(checkBlockPos(worldGenLevel, blockPos).east(3));
        positions.add(checkBlockPos(worldGenLevel, blockPos).south(3));
        positions.add(checkBlockPos(worldGenLevel, blockPos).west(3));
        positions.add(checkBlockPos(worldGenLevel, blockPos).north(3).east());
        positions.add(checkBlockPos(worldGenLevel, blockPos).north(3).west());
        positions.add(checkBlockPos(worldGenLevel, blockPos).south(3).east());
        positions.add(checkBlockPos(worldGenLevel, blockPos).south(3).west());
        positions.add(checkBlockPos(worldGenLevel, blockPos).north().east(3));
        positions.add(checkBlockPos(worldGenLevel, blockPos).north().west(3));
        positions.add(checkBlockPos(worldGenLevel, blockPos).south().east(3));
        positions.add(checkBlockPos(worldGenLevel, blockPos).south().west(3));
        positions.add(checkBlockPos(worldGenLevel, blockPos).north(3).east(2));
        positions.add(checkBlockPos(worldGenLevel, blockPos).north(3).west(2));
        positions.add(checkBlockPos(worldGenLevel, blockPos).south(3).east(2));
        positions.add(checkBlockPos(worldGenLevel, blockPos).south(3).west(2));
        positions.add(checkBlockPos(worldGenLevel, blockPos).north(2).east(3));
        positions.add(checkBlockPos(worldGenLevel, blockPos).north(2).west(3));
        positions.add(checkBlockPos(worldGenLevel, blockPos).south(2).east(3));
        positions.add(checkBlockPos(worldGenLevel, blockPos).south(2).west(3));
        positions.add(checkBlockPos(worldGenLevel, blockPos).north(3).east(3));
        positions.add(checkBlockPos(worldGenLevel, blockPos).north(3).west(3));
        positions.add(checkBlockPos(worldGenLevel, blockPos).south(3).east(3));
        positions.add(checkBlockPos(worldGenLevel, blockPos).south(3).west(3));

        return positions;
    }
}