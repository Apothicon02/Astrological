package com.Apothic0n.Astrological.core.events;

import com.Apothic0n.Astrological.Astrological;
import com.Apothic0n.Astrological.core.objects.AstrologicalBlocks;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.JsonOps;
import net.commoble.databuddy.datagen.BlockStateFile;
import net.commoble.databuddy.datagen.SimpleModel;
import net.minecraft.Util;
import net.minecraft.client.resources.model.BlockModelRotation;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.properties.*;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.Apothic0n.Astrological.core.objects.AstrologicalBlocks.blocksWithStairsSlabsAndWalls;

@EventBusSubscriber(modid = Astrological.MODID, bus = EventBusSubscriber.Bus.MOD)
public class CommonModEvents {
    @SubscribeEvent
    public static void onGatherData(GatherDataEvent event) {
        // models
        SimpleModel.addDataProvider(event, Astrological.MODID, JsonOps.INSTANCE, Util.make(new HashMap<>(), map ->
        {
            for (int i = 0; i < blocksWithStairsSlabsAndWalls.size(); i++) {
                Pair<String, DeferredHolder<Block, Block>> baseBlockBlock = blocksWithStairsSlabsAndWalls.get(i);
                ResourceLocation baseBlock = ResourceLocation.tryBuild(Astrological.MODID, "block/" +  baseBlockBlock.getFirst());

                //Walls
                ResourceLocation tempWallBlock = ResourceLocation.parse("block/failure");
                ResourceLocation tempWallBlockSide = ResourceLocation.parse("block/failure_side");
                ResourceLocation tempWallBlockSideTall = ResourceLocation.parse("block/failure_side_tall");
                ResourceLocation tempWallBlockItem = ResourceLocation.parse("block/failure_block_item");
                for (int o = 0; o < AstrologicalBlocks.wallBlocks.size(); o++) {
                    Pair<String, DeferredHolder<Block, Block>> wallBlockMap = AstrologicalBlocks.wallBlocks.get(o);
                    if (wallBlockMap.getFirst().equals(baseBlockBlock.getFirst())) {
                        tempWallBlock = ResourceLocation.tryBuild(Astrological.MODID, "block/" + wallBlockMap.getFirst() + "_post");
                        tempWallBlockSide = ResourceLocation.tryBuild(Astrological.MODID, "block/" + wallBlockMap.getFirst() + "_side");
                        tempWallBlockSideTall = ResourceLocation.tryBuild(Astrological.MODID, "block/" + wallBlockMap.getFirst() + "_side_tall");
                        tempWallBlockItem = ResourceLocation.tryBuild(Astrological.MODID, "item/" + wallBlockMap.getFirst());
                    }
                }
                ResourceLocation wallBlock = tempWallBlock;
                ResourceLocation wallBlockSide = tempWallBlockSide;
                ResourceLocation wallBlockSideTall = tempWallBlockSideTall;
                ResourceLocation wallBlockItem = tempWallBlockItem;
                if (baseBlockBlock.equals(AstrologicalBlocks.REINFORCED_JADE)) {
                    map.put(wallBlock,
                            SimpleModel.create(ResourceLocation.parse("block/template_wall_post"))
                                    .addTexture("wall", ResourceLocation.tryBuild(Astrological.MODID, "block/reinforced_jade_end")));
                    map.put(wallBlockSide,
                            SimpleModel.create(ResourceLocation.parse("block/template_wall_side"))
                                    .addTexture("wall", ResourceLocation.tryBuild(Astrological.MODID, "block/reinforced_jade_side")));
                    map.put(wallBlockSideTall,
                            SimpleModel.create(ResourceLocation.parse("block/template_wall_side_tall"))
                                    .addTexture("wall", ResourceLocation.tryBuild(Astrological.MODID, "block/reinforced_jade_side")));
                    map.put(wallBlockItem,
                            SimpleModel.create(ResourceLocation.parse("block/wall_inventory"))
                                    .addTexture("wall", ResourceLocation.tryBuild(Astrological.MODID, "block/reinforced_jade_end")));
                } else {
                    map.put(wallBlock,
                            SimpleModel.create(ResourceLocation.parse("block/template_wall_post"))
                                    .addTexture("wall", baseBlock));
                    map.put(wallBlockSide,
                            SimpleModel.create(ResourceLocation.parse("block/template_wall_side"))
                                    .addTexture("wall", baseBlock));
                    map.put(wallBlockSideTall,
                            SimpleModel.create(ResourceLocation.parse("block/template_wall_side_tall"))
                                    .addTexture("wall", baseBlock));
                    map.put(wallBlockItem,
                            SimpleModel.create(ResourceLocation.parse("block/wall_inventory"))
                                    .addTexture("wall", baseBlock));
                }

                //Stairs
                ResourceLocation tempStairsBlock = ResourceLocation.parse("block/failure");
                ResourceLocation tempStairsBlockInner = ResourceLocation.parse("block/failure_inner");
                ResourceLocation tempStairsBlockOuter = ResourceLocation.parse("block/failure_outer");
                ResourceLocation tempStairsBlockItem = ResourceLocation.parse("block/failure_block_item");
                for (int o = 0; o < AstrologicalBlocks.stairBlocks.size(); o++) {
                    Pair<String, DeferredHolder<Block, Block>> stairBlockMap = AstrologicalBlocks.stairBlocks.get(o);
                    if (stairBlockMap.getFirst().equals(baseBlockBlock.getFirst())) {
                        tempStairsBlock = ResourceLocation.tryBuild(Astrological.MODID, "block/" + stairBlockMap.getFirst());
                        tempStairsBlockInner = ResourceLocation.tryBuild(Astrological.MODID, "block/" + stairBlockMap.getFirst() + "_inner");
                        tempStairsBlockOuter = ResourceLocation.tryBuild(Astrological.MODID, "block/" + stairBlockMap.getFirst() + "_outer");
                        tempStairsBlockItem = ResourceLocation.tryBuild(Astrological.MODID, "item/" + stairBlockMap.getFirst());
                    }
                }
                ResourceLocation stairsBlock = tempStairsBlock;
                ResourceLocation stairsBlockInner = tempStairsBlockInner;
                ResourceLocation stairsBlockOuter = tempStairsBlockOuter;
                ResourceLocation stairsBlockItem = tempStairsBlockItem;
                if (baseBlockBlock.equals(AstrologicalBlocks.REINFORCED_JADE)) {
                    map.put(stairsBlock,
                            SimpleModel.create(ResourceLocation.parse("block/stairs"))
                                    .addTexture("bottom", ResourceLocation.tryBuild(Astrological.MODID, "block/reinforced_jade_end"))
                                    .addTexture("side", ResourceLocation.tryBuild(Astrological.MODID, "block/reinforced_jade_side"))
                                    .addTexture("top", ResourceLocation.tryBuild(Astrological.MODID, "block/reinforced_jade_end")));
                    map.put(stairsBlockInner,
                            SimpleModel.create(ResourceLocation.parse("block/inner_stairs"))
                                    .addTexture("bottom", ResourceLocation.tryBuild(Astrological.MODID, "block/reinforced_jade_end"))
                                    .addTexture("side", ResourceLocation.tryBuild(Astrological.MODID, "block/reinforced_jade_side"))
                                    .addTexture("top", ResourceLocation.tryBuild(Astrological.MODID, "block/reinforced_jade_end")));
                    map.put(stairsBlockOuter,
                            SimpleModel.create(ResourceLocation.parse("block/outer_stairs"))
                                    .addTexture("bottom", ResourceLocation.tryBuild(Astrological.MODID, "block/reinforced_jade_end"))
                                    .addTexture("side", ResourceLocation.tryBuild(Astrological.MODID, "block/reinforced_jade_side"))
                                    .addTexture("top", ResourceLocation.tryBuild(Astrological.MODID, "block/reinforced_jade_end")));
                    map.put(stairsBlockItem,
                            SimpleModel.create(stairsBlock));
                } else {
                    map.put(stairsBlock,
                            SimpleModel.create(ResourceLocation.parse("block/stairs"))
                                    .addTexture("bottom", baseBlock)
                                    .addTexture("side", baseBlock)
                                    .addTexture("top", baseBlock));
                    map.put(stairsBlockInner,
                            SimpleModel.create(ResourceLocation.parse("block/inner_stairs"))
                                    .addTexture("bottom", baseBlock)
                                    .addTexture("side", baseBlock)
                                    .addTexture("top", baseBlock));
                    map.put(stairsBlockOuter,
                            SimpleModel.create(ResourceLocation.parse("block/outer_stairs"))
                                    .addTexture("bottom", baseBlock)
                                    .addTexture("side", baseBlock)
                                    .addTexture("top", baseBlock));
                    map.put(stairsBlockItem,
                            SimpleModel.create(stairsBlock));
                }

                //Slabs
                ResourceLocation tempSlabBlock = ResourceLocation.parse("block/failure");
                ResourceLocation tempSlabBlockTop = ResourceLocation.parse("block/failure_top");
                ResourceLocation tempSlabBlockItem = ResourceLocation.parse("block/failure_block_item");
                for (int o = 0; o < AstrologicalBlocks.slabBlocks.size(); o++) {
                    Pair<String, DeferredHolder<Block, Block>> slabBlockMap = AstrologicalBlocks.slabBlocks.get(o);
                    if (slabBlockMap.getFirst().equals(baseBlockBlock.getFirst())) {
                        tempSlabBlock = ResourceLocation.tryBuild(Astrological.MODID, "block/" + slabBlockMap.getFirst());
                        tempSlabBlockTop = ResourceLocation.tryBuild(Astrological.MODID, "block/" + slabBlockMap.getFirst() + "_top");
                        tempSlabBlockItem = ResourceLocation.tryBuild(Astrological.MODID, "item/" + slabBlockMap.getFirst());
                    }
                }
                ResourceLocation slabBlock = tempSlabBlock;
                ResourceLocation slabBlockTop = tempSlabBlockTop;
                ResourceLocation slabBlockItem = tempSlabBlockItem;
                if (baseBlockBlock.equals(AstrologicalBlocks.REINFORCED_JADE)) {
                    map.put(slabBlock,
                            SimpleModel.create(ResourceLocation.parse("block/slab"))
                                    .addTexture("bottom", ResourceLocation.tryBuild(Astrological.MODID, "block/reinforced_jade_end"))
                                    .addTexture("side", ResourceLocation.tryBuild(Astrological.MODID, "block/reinforced_jade_side"))
                                    .addTexture("top", ResourceLocation.tryBuild(Astrological.MODID, "block/reinforced_jade_end")));
                    map.put(slabBlockTop,
                            SimpleModel.create(ResourceLocation.parse("block/slab_top"))
                                    .addTexture("bottom", ResourceLocation.tryBuild(Astrological.MODID, "block/reinforced_jade_end"))
                                    .addTexture("side", ResourceLocation.tryBuild(Astrological.MODID, "block/reinforced_jade_side"))
                                    .addTexture("top", ResourceLocation.tryBuild(Astrological.MODID, "block/reinforced_jade_end")));
                    map.put(slabBlockItem,
                            SimpleModel.create(slabBlock));
                } else {
                    map.put(slabBlock,
                            SimpleModel.create(ResourceLocation.parse("block/slab"))
                                    .addTexture("bottom", baseBlock)
                                    .addTexture("side", baseBlock)
                                    .addTexture("top", baseBlock));
                    map.put(slabBlockTop,
                            SimpleModel.create(ResourceLocation.parse("block/slab_top"))
                                    .addTexture("bottom", baseBlock)
                                    .addTexture("side", baseBlock)
                                    .addTexture("top", baseBlock));
                    map.put(slabBlockItem,
                            SimpleModel.create(slabBlock));
                }
            }
        }));
        // blockstates
        BlockStateFile.addDataProvider(event, Astrological.MODID, JsonOps.INSTANCE, Util.make(new HashMap<>(), map ->
        {
            for (int i = 0; i < blocksWithStairsSlabsAndWalls.size(); i++) {
                Pair<String, DeferredHolder<Block, Block>> baseBlockBlock = blocksWithStairsSlabsAndWalls.get(i);
                ResourceLocation baseBlock = ResourceLocation.tryBuild(Astrological.MODID, "block/" +  baseBlockBlock.toString().substring(13));

                //Walls
                ResourceLocation tempWallState = ResourceLocation.parse("failure");
                ResourceLocation tempWallBlock = ResourceLocation.parse("block/failure");
                ResourceLocation tempWallBlockSide = ResourceLocation.parse("block/failure_side");
                ResourceLocation tempWallBlockSideTall = ResourceLocation.parse("block/failure_side_tall");
                for (int o = 0; o < AstrologicalBlocks.wallBlocks.size(); o++) {
                    Pair<String, DeferredHolder<Block, Block>> wallBlockMap = AstrologicalBlocks.wallBlocks.get(o);
                    if (wallBlockMap.getFirst().equals(baseBlockBlock.getFirst())) {
                        tempWallState = ResourceLocation.tryBuild(Astrological.MODID, wallBlockMap.getFirst());
                        tempWallBlock = ResourceLocation.tryBuild(Astrological.MODID, "block/" + wallBlockMap.getFirst() + "_post");
                        tempWallBlockSide = ResourceLocation.tryBuild(Astrological.MODID, "block/" + wallBlockMap.getFirst() + "_side");
                        tempWallBlockSideTall = ResourceLocation.tryBuild(Astrological.MODID, "block/" + wallBlockMap.getFirst() + "_side_tall");
                    }
                }
                ResourceLocation wallState = tempWallState;
                ResourceLocation wallBlock = tempWallBlock;
                ResourceLocation wallBlockSide = tempWallBlockSide;
                ResourceLocation wallBlockSideTall = tempWallBlockSideTall;
                map.put(wallState,
                        BlockStateFile.multipart(BlockStateFile.Multipart.builder()
                                .addWhenApply(BlockStateFile.WhenApply.when(
                                        BlockStateFile.Case.create(WallBlock.UP, true),
                                        BlockStateFile.Model.create(wallBlock)
                                )).addWhenApply(BlockStateFile.WhenApply.when(
                                        BlockStateFile.Case.create(BlockStateProperties.NORTH_WALL, WallSide.LOW),
                                        BlockStateFile.Model.create(wallBlockSide)
                                )).addWhenApply(BlockStateFile.WhenApply.when(
                                        BlockStateFile.Case.create(BlockStateProperties.EAST_WALL, WallSide.LOW),
                                        BlockStateFile.Model.create(wallBlockSide, BlockModelRotation.X0_Y90)
                                )).addWhenApply(BlockStateFile.WhenApply.when(
                                        BlockStateFile.Case.create(BlockStateProperties.SOUTH_WALL, WallSide.LOW),
                                        BlockStateFile.Model.create(wallBlockSide, BlockModelRotation.X0_Y180)
                                )).addWhenApply(BlockStateFile.WhenApply.when(
                                        BlockStateFile.Case.create(BlockStateProperties.WEST_WALL, WallSide.LOW),
                                        BlockStateFile.Model.create(wallBlockSide, BlockModelRotation.X0_Y270)
                                )).addWhenApply(BlockStateFile.WhenApply.when(
                                        BlockStateFile.Case.create(BlockStateProperties.NORTH_WALL, WallSide.TALL),
                                        BlockStateFile.Model.create(wallBlockSideTall)
                                )).addWhenApply(BlockStateFile.WhenApply.when(
                                        BlockStateFile.Case.create(BlockStateProperties.EAST_WALL, WallSide.TALL),
                                        BlockStateFile.Model.create(wallBlockSideTall, BlockModelRotation.X0_Y90)
                                )).addWhenApply(BlockStateFile.WhenApply.when(
                                        BlockStateFile.Case.create(BlockStateProperties.SOUTH_WALL, WallSide.TALL),
                                        BlockStateFile.Model.create(wallBlockSideTall, BlockModelRotation.X0_Y180)
                                )).addWhenApply(BlockStateFile.WhenApply.when(
                                        BlockStateFile.Case.create(BlockStateProperties.WEST_WALL, WallSide.TALL),
                                        BlockStateFile.Model.create(wallBlockSideTall, BlockModelRotation.X0_Y270)
                                ))));

                //Stairs
                ResourceLocation tempStairState = ResourceLocation.parse("failure");
                ResourceLocation tempStairBlock = ResourceLocation.parse("block/failure");
                ResourceLocation tempStairBlockInner = ResourceLocation.parse("block/failure_inner");
                ResourceLocation tempStairBlockOuter = ResourceLocation.parse("block/failure_outer");
                for (int o = 0; o < AstrologicalBlocks.stairBlocks.size(); o++) {
                    Pair<String, DeferredHolder<Block, Block>> stairBlockMap = AstrologicalBlocks.stairBlocks.get(o);
                    if (stairBlockMap.getFirst().equals(baseBlockBlock.getFirst())) {
                        tempStairState = ResourceLocation.tryBuild(Astrological.MODID, stairBlockMap.getFirst());
                        tempStairBlock = ResourceLocation.tryBuild(Astrological.MODID, "block/" + stairBlockMap.getFirst());
                        tempStairBlockInner = ResourceLocation.tryBuild(Astrological.MODID, "block/" + stairBlockMap.getFirst() + "_inner");
                        tempStairBlockOuter = ResourceLocation.tryBuild(Astrological.MODID, "block/" + stairBlockMap.getFirst() + "_outer");
                    }
                }
                ResourceLocation stairState = tempStairState;
                ResourceLocation stairBlock = tempStairBlock;
                ResourceLocation stairBlockInner = tempStairBlockInner;
                ResourceLocation stairBlockOuter = tempStairBlockOuter;
                BlockStateFile.Variants variants = BlockStateFile.Variants.builder();
                for (Direction facing : StairBlock.FACING.getPossibleValues()) {
                    for (Half half : StairBlock.HALF.getPossibleValues()) {
                        for (StairsShape shape : StairBlock.SHAPE.getPossibleValues()) {
                            ResourceLocation model =
                                    shape == StairsShape.INNER_LEFT || shape == StairsShape.INNER_RIGHT ? stairBlockInner
                                            : shape == StairsShape.OUTER_LEFT || shape == StairsShape.OUTER_RIGHT ? stairBlockOuter
                                            : stairBlock;
                            int x = half == Half.TOP ? 180 : 0;
                            int y = ((int) facing.toYRot() + 90
                            + (shape == StairsShape.INNER_LEFT || shape == StairsShape.OUTER_LEFT ? 270 : 0)
                            + (half == Half.TOP && shape != StairsShape.STRAIGHT ? 90 : 0))
                                    % 360;
                            boolean uvlock = x != 0 || y != 0;
                            variants.addVariant(List.of(BlockStateFile.PropertyValue.create(StairBlock.FACING, facing), BlockStateFile.PropertyValue.create(StairBlock.HALF, half), BlockStateFile.PropertyValue.create(StairBlock.SHAPE, shape)),
                                    BlockStateFile.Model.create(model, BlockModelRotation.by(x, y), uvlock, 1));
                        }
                    }
                }
                map.put(stairState, BlockStateFile.variants(variants));

                //Slabs
                ResourceLocation tempSlabState = ResourceLocation.parse("failure");
                ResourceLocation tempSlabBlock = ResourceLocation.parse("block/failure");
                ResourceLocation tempSlabBlockTop = ResourceLocation.parse("block/failure_top");
                for (int o = 0; o < AstrologicalBlocks.slabBlocks.size(); o++) {
                    Pair<String, DeferredHolder<Block, Block>> slabBlockMap = AstrologicalBlocks.slabBlocks.get(o);
                    if (slabBlockMap.getFirst().equals(baseBlockBlock.getFirst())) {
                        tempSlabState = ResourceLocation.tryBuild(Astrological.MODID, slabBlockMap.getFirst());
                        tempSlabBlock = ResourceLocation.tryBuild(Astrological.MODID, "block/" + slabBlockMap.getFirst());
                        tempSlabBlockTop = ResourceLocation.tryBuild(Astrological.MODID, "block/" + slabBlockMap.getFirst() + "_top");
                    }
                }
                ResourceLocation slabState = tempSlabState;
                ResourceLocation slabBlock = tempSlabBlock;
                ResourceLocation slabBlockTop = tempSlabBlockTop;
                map.put(slabState,
                        BlockStateFile.variants(BlockStateFile.Variants.builder()
                                .addVariant(
                                        BlockStateFile.PropertyValue.create(SlabBlock.TYPE, SlabType.BOTTOM),
                                        BlockStateFile.Model.create(slabBlock))
                                .addVariant(
                                        BlockStateFile.PropertyValue.create(SlabBlock.TYPE, SlabType.DOUBLE),
                                        BlockStateFile.Model.create(baseBlock))
                                .addVariant(
                                        BlockStateFile.PropertyValue.create(SlabBlock.TYPE, SlabType.TOP),
                                        BlockStateFile.Model.create(slabBlockTop))));
            }
        }));
    }
}
