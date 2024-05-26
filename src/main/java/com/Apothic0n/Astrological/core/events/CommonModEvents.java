package com.Apothic0n.Astrological.core.events;

import com.Apothic0n.Astrological.Astrological;
import com.Apothic0n.Astrological.core.objects.AstrologicalBlocks;
import com.mojang.serialization.JsonOps;
import commoble.databuddy.datagen.BlockStateFile;
import commoble.databuddy.datagen.SimpleModel;
import net.minecraft.Util;
import net.minecraft.client.resources.model.BlockModelRotation;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.Apothic0n.Astrological.core.objects.AstrologicalBlocks.blocksWithStairsSlabsAndWalls;

@Mod.EventBusSubscriber(modid= Astrological.MODID, bus= Mod.EventBusSubscriber.Bus.MOD)
public class CommonModEvents {
    @SubscribeEvent
    public static void onGatherData(GatherDataEvent event) {
        // models
        SimpleModel.addDataProvider(event, Astrological.MODID, JsonOps.INSTANCE, Util.make(new HashMap<>(), map ->
        {
            for (int i = 0; i < blocksWithStairsSlabsAndWalls.size(); i++) {
                RegistryObject<Block> baseBlockBlock = blocksWithStairsSlabsAndWalls.get(i);
                ResourceLocation baseBlock = new ResourceLocation(Astrological.MODID, "block/" +  baseBlockBlock.getId().toString().substring(13));

                //Walls
                ResourceLocation tempWallBlock = new ResourceLocation("block/failure");
                ResourceLocation tempWallBlockSide = new ResourceLocation("block/failure_side");
                ResourceLocation tempWallBlockSideTall = new ResourceLocation("block/failure_side_tall");
                ResourceLocation tempWallBlockItem = new ResourceLocation("block/failure_block_item");
                for (int o = 0; o < AstrologicalBlocks.wallBlocks.size(); o++) {
                    Map<RegistryObject<Block>, RegistryObject<Block>> wallBlockMap = AstrologicalBlocks.wallBlocks.get(o);
                    if (wallBlockMap.containsKey(baseBlockBlock)) {
                        tempWallBlock = new ResourceLocation(Astrological.MODID, "block/" + wallBlockMap.get(baseBlockBlock).getId().toString().substring(13) + "_post");
                        tempWallBlockSide = new ResourceLocation(Astrological.MODID, "block/" + wallBlockMap.get(baseBlockBlock).getId().toString().substring(13) + "_side");
                        tempWallBlockSideTall = new ResourceLocation(Astrological.MODID, "block/" + wallBlockMap.get(baseBlockBlock).getId().toString().substring(13) + "_side_tall");
                        tempWallBlockItem = new ResourceLocation(Astrological.MODID, "item/" + wallBlockMap.get(baseBlockBlock).getId().toString().substring(13));
                    }
                }
                ResourceLocation wallBlock = tempWallBlock;
                ResourceLocation wallBlockSide = tempWallBlockSide;
                ResourceLocation wallBlockSideTall = tempWallBlockSideTall;
                ResourceLocation wallBlockItem = tempWallBlockItem;
                if (baseBlockBlock.equals(AstrologicalBlocks.REINFORCED_JADE)) {
                    map.put(wallBlock,
                            SimpleModel.create(new ResourceLocation("block/template_wall_post"))
                                    .addTexture("wall", new ResourceLocation(Astrological.MODID, "block/reinforced_jade_end")));
                    map.put(wallBlockSide,
                            SimpleModel.create(new ResourceLocation("block/template_wall_side"))
                                    .addTexture("wall", new ResourceLocation(Astrological.MODID, "block/reinforced_jade_side")));
                    map.put(wallBlockSideTall,
                            SimpleModel.create(new ResourceLocation("block/template_wall_side_tall"))
                                    .addTexture("wall", new ResourceLocation(Astrological.MODID, "block/reinforced_jade_side")));
                    map.put(wallBlockItem,
                            SimpleModel.create(new ResourceLocation("block/wall_inventory"))
                                    .addTexture("wall", new ResourceLocation(Astrological.MODID, "block/reinforced_jade_end")));
                } else {
                    map.put(wallBlock,
                            SimpleModel.create(new ResourceLocation("block/template_wall_post"))
                                    .addTexture("wall", baseBlock));
                    map.put(wallBlockSide,
                            SimpleModel.create(new ResourceLocation("block/template_wall_side"))
                                    .addTexture("wall", baseBlock));
                    map.put(wallBlockSideTall,
                            SimpleModel.create(new ResourceLocation("block/template_wall_side_tall"))
                                    .addTexture("wall", baseBlock));
                    map.put(wallBlockItem,
                            SimpleModel.create(new ResourceLocation("block/wall_inventory"))
                                    .addTexture("wall", baseBlock));
                }

                //Stairs
                ResourceLocation tempStairsBlock = new ResourceLocation("block/failure");
                ResourceLocation tempStairsBlockInner = new ResourceLocation("block/failure_inner");
                ResourceLocation tempStairsBlockOuter = new ResourceLocation("block/failure_outer");
                ResourceLocation tempStairsBlockItem = new ResourceLocation("block/failure_block_item");
                for (int o = 0; o < AstrologicalBlocks.stairBlocks.size(); o++) {
                    Map<RegistryObject<Block>, RegistryObject<Block>> stairBlockMap = AstrologicalBlocks.stairBlocks.get(o);
                    if (stairBlockMap.containsKey(baseBlockBlock)) {
                        tempStairsBlock = new ResourceLocation(Astrological.MODID, "block/" + stairBlockMap.get(baseBlockBlock).getId().toString().substring(13));
                        tempStairsBlockInner = new ResourceLocation(Astrological.MODID, "block/" + stairBlockMap.get(baseBlockBlock).getId().toString().substring(13) + "_inner");
                        tempStairsBlockOuter = new ResourceLocation(Astrological.MODID, "block/" + stairBlockMap.get(baseBlockBlock).getId().toString().substring(13) + "_outer");
                        tempStairsBlockItem = new ResourceLocation(Astrological.MODID, "item/" + stairBlockMap.get(baseBlockBlock).getId().toString().substring(13));
                    }
                }
                ResourceLocation stairsBlock = tempStairsBlock;
                ResourceLocation stairsBlockInner = tempStairsBlockInner;
                ResourceLocation stairsBlockOuter = tempStairsBlockOuter;
                ResourceLocation stairsBlockItem = tempStairsBlockItem;
                if (baseBlockBlock.equals(AstrologicalBlocks.REINFORCED_JADE)) {
                    map.put(stairsBlock,
                            SimpleModel.create(new ResourceLocation("block/stairs"))
                                    .addTexture("bottom", new ResourceLocation(Astrological.MODID, "block/reinforced_jade_end"))
                                    .addTexture("side", new ResourceLocation(Astrological.MODID, "block/reinforced_jade_side"))
                                    .addTexture("top", new ResourceLocation(Astrological.MODID, "block/reinforced_jade_end")));
                    map.put(stairsBlockInner,
                            SimpleModel.create(new ResourceLocation("block/inner_stairs"))
                                    .addTexture("bottom", new ResourceLocation(Astrological.MODID, "block/reinforced_jade_end"))
                                    .addTexture("side", new ResourceLocation(Astrological.MODID, "block/reinforced_jade_side"))
                                    .addTexture("top", new ResourceLocation(Astrological.MODID, "block/reinforced_jade_end")));
                    map.put(stairsBlockOuter,
                            SimpleModel.create(new ResourceLocation("block/outer_stairs"))
                                    .addTexture("bottom", new ResourceLocation(Astrological.MODID, "block/reinforced_jade_end"))
                                    .addTexture("side", new ResourceLocation(Astrological.MODID, "block/reinforced_jade_side"))
                                    .addTexture("top", new ResourceLocation(Astrological.MODID, "block/reinforced_jade_end")));
                    map.put(stairsBlockItem,
                            SimpleModel.create(stairsBlock));
                } else {
                    map.put(stairsBlock,
                            SimpleModel.create(new ResourceLocation("block/stairs"))
                                    .addTexture("bottom", baseBlock)
                                    .addTexture("side", baseBlock)
                                    .addTexture("top", baseBlock));
                    map.put(stairsBlockInner,
                            SimpleModel.create(new ResourceLocation("block/inner_stairs"))
                                    .addTexture("bottom", baseBlock)
                                    .addTexture("side", baseBlock)
                                    .addTexture("top", baseBlock));
                    map.put(stairsBlockOuter,
                            SimpleModel.create(new ResourceLocation("block/outer_stairs"))
                                    .addTexture("bottom", baseBlock)
                                    .addTexture("side", baseBlock)
                                    .addTexture("top", baseBlock));
                    map.put(stairsBlockItem,
                            SimpleModel.create(stairsBlock));
                }

                //Slabs
                ResourceLocation tempSlabBlock = new ResourceLocation("block/failure");
                ResourceLocation tempSlabBlockTop = new ResourceLocation("block/failure_top");
                ResourceLocation tempSlabBlockItem = new ResourceLocation("block/failure_block_item");
                for (int o = 0; o < AstrologicalBlocks.slabBlocks.size(); o++) {
                    Map<RegistryObject<Block>, RegistryObject<Block>> slabBlockMap = AstrologicalBlocks.slabBlocks.get(o);
                    if (slabBlockMap.containsKey(baseBlockBlock)) {
                        tempSlabBlock = new ResourceLocation(Astrological.MODID, "block/" + slabBlockMap.get(baseBlockBlock).getId().toString().substring(13));
                        tempSlabBlockTop = new ResourceLocation(Astrological.MODID, "block/" + slabBlockMap.get(baseBlockBlock).getId().toString().substring(13) + "_top");
                        tempSlabBlockItem = new ResourceLocation(Astrological.MODID, "item/" + slabBlockMap.get(baseBlockBlock).getId().toString().substring(13));
                    }
                }
                ResourceLocation slabBlock = tempSlabBlock;
                ResourceLocation slabBlockTop = tempSlabBlockTop;
                ResourceLocation slabBlockItem = tempSlabBlockItem;
                if (baseBlockBlock.equals(AstrologicalBlocks.REINFORCED_JADE)) {
                    map.put(slabBlock,
                            SimpleModel.create(new ResourceLocation("block/slab"))
                                    .addTexture("bottom", new ResourceLocation(Astrological.MODID, "block/reinforced_jade_end"))
                                    .addTexture("side", new ResourceLocation(Astrological.MODID, "block/reinforced_jade_side"))
                                    .addTexture("top", new ResourceLocation(Astrological.MODID, "block/reinforced_jade_end")));
                    map.put(slabBlockTop,
                            SimpleModel.create(new ResourceLocation("block/slab_top"))
                                    .addTexture("bottom", new ResourceLocation(Astrological.MODID, "block/reinforced_jade_end"))
                                    .addTexture("side", new ResourceLocation(Astrological.MODID, "block/reinforced_jade_side"))
                                    .addTexture("top", new ResourceLocation(Astrological.MODID, "block/reinforced_jade_end")));
                    map.put(slabBlockItem,
                            SimpleModel.create(slabBlock));
                } else {
                    map.put(slabBlock,
                            SimpleModel.create(new ResourceLocation("block/slab"))
                                    .addTexture("bottom", baseBlock)
                                    .addTexture("side", baseBlock)
                                    .addTexture("top", baseBlock));
                    map.put(slabBlockTop,
                            SimpleModel.create(new ResourceLocation("block/slab_top"))
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
                RegistryObject<Block> baseBlockBlock = blocksWithStairsSlabsAndWalls.get(i);
                ResourceLocation baseBlock = new ResourceLocation(Astrological.MODID, "block/" +  baseBlockBlock.getId().toString().substring(13));

                //Walls
                ResourceLocation tempWallState = new ResourceLocation("failure");
                ResourceLocation tempWallBlock = new ResourceLocation("block/failure");
                ResourceLocation tempWallBlockSide = new ResourceLocation("block/failure_side");
                ResourceLocation tempWallBlockSideTall = new ResourceLocation("block/failure_side_tall");
                for (int o = 0; o < AstrologicalBlocks.wallBlocks.size(); o++) {
                    Map<RegistryObject<Block>, RegistryObject<Block>> wallBlockMap = AstrologicalBlocks.wallBlocks.get(o);
                    if (wallBlockMap.containsKey(baseBlockBlock)) {
                        tempWallState = new ResourceLocation(Astrological.MODID, wallBlockMap.get(baseBlockBlock).getId().toString().substring(13));
                        tempWallBlock = new ResourceLocation(Astrological.MODID, "block/" + wallBlockMap.get(baseBlockBlock).getId().toString().substring(13) + "_post");
                        tempWallBlockSide = new ResourceLocation(Astrological.MODID, "block/" + wallBlockMap.get(baseBlockBlock).getId().toString().substring(13) + "_side");
                        tempWallBlockSideTall = new ResourceLocation(Astrological.MODID, "block/" + wallBlockMap.get(baseBlockBlock).getId().toString().substring(13) + "_side_tall");
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
                ResourceLocation tempStairState = new ResourceLocation("failure");
                ResourceLocation tempStairBlock = new ResourceLocation("block/failure");
                ResourceLocation tempStairBlockInner = new ResourceLocation("block/failure_inner");
                ResourceLocation tempStairBlockOuter = new ResourceLocation("block/failure_outer");
                for (int o = 0; o < AstrologicalBlocks.stairBlocks.size(); o++) {
                    Map<RegistryObject<Block>, RegistryObject<Block>> stairBlockMap = AstrologicalBlocks.stairBlocks.get(o);
                    if (stairBlockMap.containsKey(baseBlockBlock)) {
                        tempStairState = new ResourceLocation(Astrological.MODID, stairBlockMap.get(baseBlockBlock).getId().toString().substring(13));
                        tempStairBlock = new ResourceLocation(Astrological.MODID, "block/" + stairBlockMap.get(baseBlockBlock).getId().toString().substring(13));
                        tempStairBlockInner = new ResourceLocation(Astrological.MODID, "block/" + stairBlockMap.get(baseBlockBlock).getId().toString().substring(13) + "_inner");
                        tempStairBlockOuter = new ResourceLocation(Astrological.MODID, "block/" + stairBlockMap.get(baseBlockBlock).getId().toString().substring(13) + "_outer");
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
                ResourceLocation tempSlabState = new ResourceLocation("failure");
                ResourceLocation tempSlabBlock = new ResourceLocation("block/failure");
                ResourceLocation tempSlabBlockTop = new ResourceLocation("block/failure_top");
                for (int o = 0; o < AstrologicalBlocks.slabBlocks.size(); o++) {
                    Map<RegistryObject<Block>, RegistryObject<Block>> slabBlockMap = AstrologicalBlocks.slabBlocks.get(o);
                    if (slabBlockMap.containsKey(baseBlockBlock)) {
                        tempSlabState = new ResourceLocation(Astrological.MODID, slabBlockMap.get(baseBlockBlock).getId().toString().substring(13));
                        tempSlabBlock = new ResourceLocation(Astrological.MODID, "block/" + slabBlockMap.get(baseBlockBlock).getId().toString().substring(13));
                        tempSlabBlockTop = new ResourceLocation(Astrological.MODID, "block/" + slabBlockMap.get(baseBlockBlock).getId().toString().substring(13) + "_top");
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
