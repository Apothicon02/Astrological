package com.apothicon.astrological.core.objects;

import com.apothicon.astrological.Astrological;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.*;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.List;

import static com.apothicon.astrological.core.objects.AstrologicalBlocks.blocksWithStairsSlabsAndWalls;

public final class AstrologicalItems extends Items {
    private AstrologicalItems() {}

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Astrological.MODID);

    public static final DeferredItem<Item> SLEEP = ITEMS.registerItem("sleep", props ->
            new BlockItem(AstrologicalBlocks.SLEEP.get(), props));
    public static final DeferredItem<Item> TUMOR = ITEMS.registerItem("tumor", props ->
            new BlockItem(AstrologicalBlocks.TENDRILS.get(), props));
    public static final DeferredItem<Item> CYST = ITEMS.registerItem("cyst", props ->
            new BlockItem(AstrologicalBlocks.TUMOR.get(), props));
    public static final DeferredItem<Item> CRYING_DUCT = ITEMS.registerItem("crying_duct", props ->
            new BlockItem(AstrologicalBlocks.CRYING_DUCT.get(), props));
    public static final DeferredItem<Item> INSOMNIA_VENT = ITEMS.registerItem("insomnia_vent", props ->
            new BlockItem(AstrologicalBlocks.INSOMNIA_VENT.get(), props));
    public static final DeferredItem<Item> OCHRE_SELENITE = ITEMS.registerItem("ochre_selenite", props ->
            new OchreSeleniteBlockItem(AstrologicalBlocks.OCHRE_SELENITE.get(), props));
    public static final DeferredItem<Item> VERDANT_SELENITE = ITEMS.registerItem("verdant_selenite", props ->
            new VerdantSeleniteBlockItem(AstrologicalBlocks.VERDANT_SELENITE.get(), props));
    public static final DeferredItem<Item> PEARLESCENT_SELENITE = ITEMS.registerItem("pearlescent_selenite", props ->
            new PearlescentSeleniteBlockItem(AstrologicalBlocks.PEARLESCENT_SELENITE.get(), props));
    public static final DeferredItem<Item> PURPURITE = ITEMS.registerItem("purpurite", props ->
            new BlockItem(AstrologicalBlocks.PURPURITE.get(), props));
    public static final DeferredItem<Item> TRIPHYLITE = ITEMS.registerItem("triphylite", props ->
            new BlockItem(AstrologicalBlocks.TRIPHYLITE.get(), props));
    public static final DeferredItem<Item> JADE = ITEMS.registerItem("jade", props ->
            new BlockItem(AstrologicalBlocks.JADE.get(), props));
    public static final DeferredItem<Item> REINFORCED_JADE = ITEMS.registerItem("reinforced_jade", props ->
            new BlockItem(AstrologicalBlocks.REINFORCED_JADE.get(), props));
    public static final DeferredItem<Item> JADE_BRICKS = ITEMS.registerItem("jade_bricks", props ->
            new BlockItem(AstrologicalBlocks.JADE_BRICKS.get(), props));
    public static final DeferredItem<Item> POLISHED_JADE = ITEMS.registerItem("polished_jade", props ->
            new BlockItem(AstrologicalBlocks.POLISHED_JADE.get(), props));
    public static final DeferredItem<Item> MARBLED_JADE_SLAB = ITEMS.registerItem("marbled_jade_slab", props ->
            new BlockItem(AstrologicalBlocks.MARBLED_JADE_SLAB.get(), props));
    public static final DeferredItem<Item> LIGHT_JADE = ITEMS.registerItem("light_jade", props ->
            new BlockItem(AstrologicalBlocks.LIGHT_JADE.get(), props));
    public static final DeferredItem<Item> SELENITE_WALL = ITEMS.registerItem("selenite_wall", props ->
            new BlockItem(AstrologicalBlocks.SELENITE_WALL.get(), props));
    public static final DeferredItem<Item> PRISMATIC_SELENITE = ITEMS.registerItem("prismatic_selenite", props ->
            new BlockItem(AstrologicalBlocks.PRISMATIC_SELENITE.get(), props));
    public static final DeferredItem<Item> LIGHT_JADE_TILES = ITEMS.registerItem("light_jade_tiles", props ->
            new BlockItem(AstrologicalBlocks.LIGHT_JADE_TILES.get(), props));
    public static final DeferredItem<Item> CRACKED_LIGHT_JADE_TILES = ITEMS.registerItem("cracked_light_jade_tiles", props ->
            new BlockItem(AstrologicalBlocks.CRACKED_LIGHT_JADE_TILES.get(), props));
    public static final DeferredItem<Item> PURPURITE_TILES = ITEMS.registerItem("purpurite_tiles", props ->
            new BlockItem(AstrologicalBlocks.PURPURITE_TILES.get(), props));

    public static final DeferredItem<Item> PURPURITE_BLOB = ITEMS.registerItem("purpurite_blob", props ->
            new Item(props));
    public static final DeferredItem<Item> POPPED_PURPURITE = ITEMS.registerItem("popped_purpurite", props ->
            new Item(props));

    public static final List<DeferredItem<Item>> wallItems = new ArrayList<>(List.of());
    public static final List<DeferredItem<Item>> stairItems = new ArrayList<>(List.of());
    public static final List<DeferredItem<Item>> slabItems = new ArrayList<>(List.of());

    public static void generateStairsSlabsWalls() {
        for (int i = 0; i < blocksWithStairsSlabsAndWalls.size(); i++) {
            Pair<String, DeferredBlock<Block>> baseBlock = blocksWithStairsSlabsAndWalls.get(i);
            wallItems.add(createWallItems(baseBlock));
            stairItems.add(createStairItems(baseBlock));
            slabItems.add(createSlabItems(baseBlock));
        }
    }

    public static DeferredItem<Item> createWallItems(Pair<String, DeferredBlock<Block>> baseBlock) {
        DeferredBlock<Block> block = getBlock(baseBlock.getFirst()+"_wall", AstrologicalBlocks.wallBlocks);
        return ITEMS.registerItem(block.getId().toString().substring(13), props ->
                        new BlockItem(block.get(), props)
        );
    }

    public static DeferredItem<Item> createStairItems(Pair<String, DeferredBlock<Block>> baseBlock) {
        DeferredBlock<Block> block = getBlock(baseBlock.getFirst()+"_stairs", AstrologicalBlocks.stairBlocks);
        return ITEMS.registerItem(block.getId().toString().substring(13), props ->
                        new BlockItem(block.get(), props)
        );
    }

    public static DeferredItem<Item> createSlabItems(Pair<String, DeferredBlock<Block>> baseBlock) {
        DeferredBlock<Block> block = getBlock(baseBlock.getFirst()+"_slab", AstrologicalBlocks.slabBlocks);
        return ITEMS.registerItem(block.getId().toString().substring(13), props ->
                        new BlockItem(block.get(), props)
        );
    }

    public static DeferredBlock<Block> getBlock(String name, List<Pair<String, DeferredBlock<Block>>> blockList) {
        for (int i = 0; i < blockList.size(); i++) {
            if (blockList.get(i).getFirst().equals(name)) {
                return blockList.get(i).getSecond();
            }
        }
        return AstrologicalBlocks.PRISMATIC_SELENITE; //this means it messed up
    }
}