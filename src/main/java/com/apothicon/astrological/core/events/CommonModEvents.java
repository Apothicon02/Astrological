package com.apothicon.astrological.core.events;

import com.apothicon.astrological.Astrological;
import com.apothicon.astrological.core.objects.AstrologicalBlocks;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.JsonOps;
import net.commoble.databuddy.datagen.BlockStateFile;
import net.commoble.databuddy.datagen.SimpleModel;
import net.minecraft.client.renderer.block.dispatch.BlockModelRotation;
import net.minecraft.util.Util;
import net.minecraft.core.Direction;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.properties.*;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.registries.DeferredBlock;

import java.util.HashMap;
import java.util.List;

import static com.apothicon.astrological.core.objects.AstrologicalBlocks.blocksWithStairsSlabsAndWalls;

@EventBusSubscriber(modid = Astrological.MODID, bus = EventBusSubscriber.Bus.MOD)
public class CommonModEvents {
    @SubscribeEvent
    public static void onGatherData(GatherDataEvent event) {
        // models
        SimpleModel.addDataProvider(event, Astrological.MODID, JsonOps.INSTANCE, Util.make(new HashMap<>(), map ->
        {
            for (int i = 0; i < blocksWithStairsSlabsAndWalls.size(); i++) {
                Pair<String, DeferredBlock<Block>> baseBlockBlock = blocksWithStairsSlabsAndWalls.get(i);
                Identifier baseBlock = Identifier.tryBuild(Astrological.MODID, "block/" +  baseBlockBlock.getFirst());

                //Walls
                Identifier tempWallBlock = Identifier.parse("block/failure");
                Identifier tempWallBlockSide = Identifier.parse("block/failure_side");
                Identifier tempWallBlockSideTall = Identifier.parse("block/failure_side_tall");
                Identifier tempWallBlockItem = Identifier.parse("block/failure_block_item");
                for (int o = 0; o < AstrologicalBlocks.wallBlocks.size(); o++) {
                    Pair<String, DeferredBlock<Block>> wallBlockMap = AstrologicalBlocks.wallBlocks.get(o);
                    if (wallBlockMap.getFirst().equals(baseBlockBlock.getFirst())) {
                        tempWallBlock = Identifier.tryBuild(Astrological.MODID, "block/" + wallBlockMap.getFirst() + "_post");
                        tempWallBlockSide = Identifier.tryBuild(Astrological.MODID, "block/" + wallBlockMap.getFirst() + "_side");
                        tempWallBlockSideTall = Identifier.tryBuild(Astrological.MODID, "block/" + wallBlockMap.getFirst() + "_side_tall");
                        tempWallBlockItem = Identifier.tryBuild(Astrological.MODID, "item/" + wallBlockMap.getFirst());
                    }
                }
                Identifier wallBlock = tempWallBlock;
                Identifier wallBlockSide = tempWallBlockSide;
                Identifier wallBlockSideTall = tempWallBlockSideTall;
                Identifier wallBlockItem = tempWallBlockItem;
                if (baseBlockBlock.equals(AstrologicalBlocks.REINFORCED_JADE)) {
                    map.put(wallBlock,
                            SimpleModel.create(Identifier.parse("block/template_wall_post"))
                                    .addTexture("wall", Identifier.tryBuild(Astrological.MODID, "block/reinforced_jade_end")));
                    map.put(wallBlockSide,
                            SimpleModel.create(Identifier.parse("block/template_wall_side"))
                                    .addTexture("wall", Identifier.tryBuild(Astrological.MODID, "block/reinforced_jade_side")));
                    map.put(wallBlockSideTall,
                            SimpleModel.create(Identifier.parse("block/template_wall_side_tall"))
                                    .addTexture("wall", Identifier.tryBuild(Astrological.MODID, "block/reinforced_jade_side")));
                    map.put(wallBlockItem,
                            SimpleModel.create(Identifier.parse("block/wall_inventory"))
                                    .addTexture("wall", Identifier.tryBuild(Astrological.MODID, "block/reinforced_jade_end")));
                } else {
                    map.put(wallBlock,
                            SimpleModel.create(Identifier.parse("block/template_wall_post"))
                                    .addTexture("wall", baseBlock));
                    map.put(wallBlockSide,
                            SimpleModel.create(Identifier.parse("block/template_wall_side"))
                                    .addTexture("wall", baseBlock));
                    map.put(wallBlockSideTall,
                            SimpleModel.create(Identifier.parse("block/template_wall_side_tall"))
                                    .addTexture("wall", baseBlock));
                    map.put(wallBlockItem,
                            SimpleModel.create(Identifier.parse("block/wall_inventory"))
                                    .addTexture("wall", baseBlock));
                }

                //Stairs
                Identifier tempStairsBlock = Identifier.parse("block/failure");
                Identifier tempStairsBlockInner = Identifier.parse("block/failure_inner");
                Identifier tempStairsBlockOuter = Identifier.parse("block/failure_outer");
                Identifier tempStairsBlockItem = Identifier.parse("block/failure_block_item");
                for (int o = 0; o < AstrologicalBlocks.stairBlocks.size(); o++) {
                    Pair<String, DeferredBlock<Block>> stairBlockMap = AstrologicalBlocks.stairBlocks.get(o);
                    if (stairBlockMap.getFirst().equals(baseBlockBlock.getFirst())) {
                        tempStairsBlock = Identifier.tryBuild(Astrological.MODID, "block/" + stairBlockMap.getFirst());
                        tempStairsBlockInner = Identifier.tryBuild(Astrological.MODID, "block/" + stairBlockMap.getFirst() + "_inner");
                        tempStairsBlockOuter = Identifier.tryBuild(Astrological.MODID, "block/" + stairBlockMap.getFirst() + "_outer");
                        tempStairsBlockItem = Identifier.tryBuild(Astrological.MODID, "item/" + stairBlockMap.getFirst());
                    }
                }
                Identifier stairsBlock = tempStairsBlock;
                Identifier stairsBlockInner = tempStairsBlockInner;
                Identifier stairsBlockOuter = tempStairsBlockOuter;
                Identifier stairsBlockItem = tempStairsBlockItem;
                if (baseBlockBlock.equals(AstrologicalBlocks.REINFORCED_JADE)) {
                    map.put(stairsBlock,
                            SimpleModel.create(Identifier.parse("block/stairs"))
                                    .addTexture("bottom", Identifier.tryBuild(Astrological.MODID, "block/reinforced_jade_end"))
                                    .addTexture("side", Identifier.tryBuild(Astrological.MODID, "block/reinforced_jade_side"))
                                    .addTexture("top", Identifier.tryBuild(Astrological.MODID, "block/reinforced_jade_end")));
                    map.put(stairsBlockInner,
                            SimpleModel.create(Identifier.parse("block/inner_stairs"))
                                    .addTexture("bottom", Identifier.tryBuild(Astrological.MODID, "block/reinforced_jade_end"))
                                    .addTexture("side", Identifier.tryBuild(Astrological.MODID, "block/reinforced_jade_side"))
                                    .addTexture("top", Identifier.tryBuild(Astrological.MODID, "block/reinforced_jade_end")));
                    map.put(stairsBlockOuter,
                            SimpleModel.create(Identifier.parse("block/outer_stairs"))
                                    .addTexture("bottom", Identifier.tryBuild(Astrological.MODID, "block/reinforced_jade_end"))
                                    .addTexture("side", Identifier.tryBuild(Astrological.MODID, "block/reinforced_jade_side"))
                                    .addTexture("top", Identifier.tryBuild(Astrological.MODID, "block/reinforced_jade_end")));
                    map.put(stairsBlockItem,
                            SimpleModel.create(stairsBlock));
                } else {
                    map.put(stairsBlock,
                            SimpleModel.create(Identifier.parse("block/stairs"))
                                    .addTexture("bottom", baseBlock)
                                    .addTexture("side", baseBlock)
                                    .addTexture("top", baseBlock));
                    map.put(stairsBlockInner,
                            SimpleModel.create(Identifier.parse("block/inner_stairs"))
                                    .addTexture("bottom", baseBlock)
                                    .addTexture("side", baseBlock)
                                    .addTexture("top", baseBlock));
                    map.put(stairsBlockOuter,
                            SimpleModel.create(Identifier.parse("block/outer_stairs"))
                                    .addTexture("bottom", baseBlock)
                                    .addTexture("side", baseBlock)
                                    .addTexture("top", baseBlock));
                    map.put(stairsBlockItem,
                            SimpleModel.create(stairsBlock));
                }

                //Slabs
                Identifier tempSlabBlock = Identifier.parse("block/failure");
                Identifier tempSlabBlockTop = Identifier.parse("block/failure_top");
                Identifier tempSlabBlockItem = Identifier.parse("block/failure_block_item");
                for (int o = 0; o < AstrologicalBlocks.slabBlocks.size(); o++) {
                    Pair<String, DeferredBlock<Block>> slabBlockMap = AstrologicalBlocks.slabBlocks.get(o);
                    if (slabBlockMap.getFirst().equals(baseBlockBlock.getFirst())) {
                        tempSlabBlock = Identifier.tryBuild(Astrological.MODID, "block/" + slabBlockMap.getFirst());
                        tempSlabBlockTop = Identifier.tryBuild(Astrological.MODID, "block/" + slabBlockMap.getFirst() + "_top");
                        tempSlabBlockItem = Identifier.tryBuild(Astrological.MODID, "item/" + slabBlockMap.getFirst());
                    }
                }
                Identifier slabBlock = tempSlabBlock;
                Identifier slabBlockTop = tempSlabBlockTop;
                Identifier slabBlockItem = tempSlabBlockItem;
                if (baseBlockBlock.equals(AstrologicalBlocks.REINFORCED_JADE)) {
                    map.put(slabBlock,
                            SimpleModel.create(Identifier.parse("block/slab"))
                                    .addTexture("bottom", Identifier.tryBuild(Astrological.MODID, "block/reinforced_jade_end"))
                                    .addTexture("side", Identifier.tryBuild(Astrological.MODID, "block/reinforced_jade_side"))
                                    .addTexture("top", Identifier.tryBuild(Astrological.MODID, "block/reinforced_jade_end")));
                    map.put(slabBlockTop,
                            SimpleModel.create(Identifier.parse("block/slab_top"))
                                    .addTexture("bottom", Identifier.tryBuild(Astrological.MODID, "block/reinforced_jade_end"))
                                    .addTexture("side", Identifier.tryBuild(Astrological.MODID, "block/reinforced_jade_side"))
                                    .addTexture("top", Identifier.tryBuild(Astrological.MODID, "block/reinforced_jade_end")));
                    map.put(slabBlockItem,
                            SimpleModel.create(slabBlock));
                } else {
                    map.put(slabBlock,
                            SimpleModel.create(Identifier.parse("block/slab"))
                                    .addTexture("bottom", baseBlock)
                                    .addTexture("side", baseBlock)
                                    .addTexture("top", baseBlock));
                    map.put(slabBlockTop,
                            SimpleModel.create(Identifier.parse("block/slab_top"))
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
                Pair<String, DeferredBlock<Block>> baseBlockBlock = blocksWithStairsSlabsAndWalls.get(i);
                Identifier baseBlock = Identifier.tryBuild(Astrological.MODID, "block/" +  baseBlockBlock.toString().substring(13));

                //Walls
                Identifier tempWallState = Identifier.parse("failure");
                Identifier tempWallBlock = Identifier.parse("block/failure");
                Identifier tempWallBlockSide = Identifier.parse("block/failure_side");
                Identifier tempWallBlockSideTall = Identifier.parse("block/failure_side_tall");
                for (int o = 0; o < AstrologicalBlocks.wallBlocks.size(); o++) {
                    Pair<String, DeferredBlock<Block>> wallBlockMap = AstrologicalBlocks.wallBlocks.get(o);
                    if (wallBlockMap.getFirst().equals(baseBlockBlock.getFirst())) {
                        tempWallState = Identifier.tryBuild(Astrological.MODID, wallBlockMap.getFirst());
                        tempWallBlock = Identifier.tryBuild(Astrological.MODID, "block/" + wallBlockMap.getFirst() + "_post");
                        tempWallBlockSide = Identifier.tryBuild(Astrological.MODID, "block/" + wallBlockMap.getFirst() + "_side");
                        tempWallBlockSideTall = Identifier.tryBuild(Astrological.MODID, "block/" + wallBlockMap.getFirst() + "_side_tall");
                    }
                }
                Identifier wallState = tempWallState;
                Identifier wallBlock = tempWallBlock;
                Identifier wallBlockSide = tempWallBlockSide;
                Identifier wallBlockSideTall = tempWallBlockSideTall;
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
                Identifier tempStairState = Identifier.parse("failure");
                Identifier tempStairBlock = Identifier.parse("block/failure");
                Identifier tempStairBlockInner = Identifier.parse("block/failure_inner");
                Identifier tempStairBlockOuter = Identifier.parse("block/failure_outer");
                for (int o = 0; o < AstrologicalBlocks.stairBlocks.size(); o++) {
                    Pair<String, DeferredBlock<Block>> stairBlockMap = AstrologicalBlocks.stairBlocks.get(o);
                    if (stairBlockMap.getFirst().equals(baseBlockBlock.getFirst())) {
                        tempStairState = Identifier.tryBuild(Astrological.MODID, stairBlockMap.getFirst());
                        tempStairBlock = Identifier.tryBuild(Astrological.MODID, "block/" + stairBlockMap.getFirst());
                        tempStairBlockInner = Identifier.tryBuild(Astrological.MODID, "block/" + stairBlockMap.getFirst() + "_inner");
                        tempStairBlockOuter = Identifier.tryBuild(Astrological.MODID, "block/" + stairBlockMap.getFirst() + "_outer");
                    }
                }
                Identifier stairState = tempStairState;
                Identifier stairBlock = tempStairBlock;
                Identifier stairBlockInner = tempStairBlockInner;
                Identifier stairBlockOuter = tempStairBlockOuter;
                BlockStateFile.Variants variants = BlockStateFile.Variants.builder();
                for (Direction facing : StairBlock.FACING.getPossibleValues()) {
                    for (Half half : StairBlock.HALF.getPossibleValues()) {
                        for (StairsShape shape : StairBlock.SHAPE.getPossibleValues()) {
                            Identifier model =
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
                Identifier tempSlabState = Identifier.parse("failure");
                Identifier tempSlabBlock = Identifier.parse("block/failure");
                Identifier tempSlabBlockTop = Identifier.parse("block/failure_top");
                for (int o = 0; o < AstrologicalBlocks.slabBlocks.size(); o++) {
                    Pair<String, DeferredBlock<Block>> slabBlockMap = AstrologicalBlocks.slabBlocks.get(o);
                    if (slabBlockMap.getFirst().equals(baseBlockBlock.getFirst())) {
                        tempSlabState = Identifier.tryBuild(Astrological.MODID, slabBlockMap.getFirst());
                        tempSlabBlock = Identifier.tryBuild(Astrological.MODID, "block/" + slabBlockMap.getFirst());
                        tempSlabBlockTop = Identifier.tryBuild(Astrological.MODID, "block/" + slabBlockMap.getFirst() + "_top");
                    }
                }
                Identifier slabState = tempSlabState;
                Identifier slabBlock = tempSlabBlock;
                Identifier slabBlockTop = tempSlabBlockTop;
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
