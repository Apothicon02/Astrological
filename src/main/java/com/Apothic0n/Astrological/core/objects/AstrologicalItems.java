package com.Apothic0n.Astrological.core.objects;

import com.Apothic0n.Astrological.Astrological;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.*;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.Apothic0n.Astrological.core.objects.AstrologicalBlocks.blocksWithStairsSlabsAndWalls;

public final class AstrologicalItems extends Items {
    private AstrologicalItems() {}

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, Astrological.MODID);

    public static final DeferredHolder<Item, Item> SLEEP = ITEMS.register("sleep", () ->
            new BlockItem(AstrologicalBlocks.SLEEP.get(), new Item.Properties()));
    public static final DeferredHolder<Item, Item> TUMOR = ITEMS.register("tumor", () ->
            new BlockItem(AstrologicalBlocks.TENDRILS.get(), new Item.Properties()));
    public static final DeferredHolder<Item, Item> CYST = ITEMS.register("cyst", () ->
            new BlockItem(AstrologicalBlocks.TUMOR.get(), new Item.Properties()));
    public static final DeferredHolder<Item, Item> CRYING_DUCT = ITEMS.register("crying_duct", () ->
            new BlockItem(AstrologicalBlocks.CRYING_DUCT.get(), new Item.Properties()));
    public static final DeferredHolder<Item, Item> INSOMNIA_VENT = ITEMS.register("insomnia_vent", () ->
            new BlockItem(AstrologicalBlocks.INSOMNIA_VENT.get(), new Item.Properties()));
    public static final DeferredHolder<Item, Item> OCHRE_SELENITE = ITEMS.register("ochre_selenite", () ->
            new OchreSeleniteBlockItem(AstrologicalBlocks.OCHRE_SELENITE.get(), new Item.Properties()));
    public static final DeferredHolder<Item, Item> VERDANT_SELENITE = ITEMS.register("verdant_selenite", () ->
            new VerdantSeleniteBlockItem(AstrologicalBlocks.VERDANT_SELENITE.get(), new Item.Properties()));
    public static final DeferredHolder<Item, Item> PEARLESCENT_SELENITE = ITEMS.register("pearlescent_selenite", () ->
            new PearlescentSeleniteBlockItem(AstrologicalBlocks.PEARLESCENT_SELENITE.get(), new Item.Properties()));
    public static final DeferredHolder<Item, Item> PURPURITE = ITEMS.register("purpurite", () ->
            new BlockItem(AstrologicalBlocks.PURPURITE.get(), new Item.Properties()));
    public static final DeferredHolder<Item, Item> TRIPHYLITE = ITEMS.register("triphylite", () ->
            new BlockItem(AstrologicalBlocks.TRIPHYLITE.get(), new Item.Properties()));
    public static final DeferredHolder<Item, Item> JADE = ITEMS.register("jade", () ->
            new BlockItem(AstrologicalBlocks.JADE.get(), new Item.Properties()));
    public static final DeferredHolder<Item, Item> REINFORCED_JADE = ITEMS.register("reinforced_jade", () ->
            new BlockItem(AstrologicalBlocks.REINFORCED_JADE.get(), new Item.Properties()));
    public static final DeferredHolder<Item, Item> JADE_BRICKS = ITEMS.register("jade_bricks", () ->
            new BlockItem(AstrologicalBlocks.JADE_BRICKS.get(), new Item.Properties()));
    public static final DeferredHolder<Item, Item> POLISHED_JADE = ITEMS.register("polished_jade", () ->
            new BlockItem(AstrologicalBlocks.POLISHED_JADE.get(), new Item.Properties()));
    public static final DeferredHolder<Item, Item> MARBLED_JADE_SLAB = ITEMS.register("marbled_jade_slab", () ->
            new BlockItem(AstrologicalBlocks.MARBLED_JADE_SLAB.get(), new Item.Properties()));
    public static final DeferredHolder<Item, Item> LIGHT_JADE = ITEMS.register("light_jade", () ->
            new BlockItem(AstrologicalBlocks.LIGHT_JADE.get(), new Item.Properties()));
    public static final DeferredHolder<Item, Item> SELENITE_WALL = ITEMS.register("selenite_wall", () ->
            new BlockItem(AstrologicalBlocks.SELENITE_WALL.get(), new Item.Properties()));
    public static final DeferredHolder<Item, Item> PRISMATIC_SELENITE = ITEMS.register("prismatic_selenite", () ->
            new BlockItem(AstrologicalBlocks.PRISMATIC_SELENITE.get(), new Item.Properties()));
    public static final DeferredHolder<Item, Item> LIGHT_JADE_TILES = ITEMS.register("light_jade_tiles", () ->
            new BlockItem(AstrologicalBlocks.LIGHT_JADE_TILES.get(), new Item.Properties()));
    public static final DeferredHolder<Item, Item> CRACKED_LIGHT_JADE_TILES = ITEMS.register("cracked_light_jade_tiles", () ->
            new BlockItem(AstrologicalBlocks.CRACKED_LIGHT_JADE_TILES.get(), new Item.Properties()));
    public static final DeferredHolder<Item, Item> PURPURITE_TILES = ITEMS.register("purpurite_tiles", () ->
            new BlockItem(AstrologicalBlocks.PURPURITE_TILES.get(), new Item.Properties()));

    public static final DeferredHolder<Item, Item> PURPURITE_BLOB = ITEMS.register("purpurite_blob", () ->
            new Item(new Item.Properties()));
    public static final DeferredHolder<Item, Item> POPPED_PURPURITE = ITEMS.register("popped_purpurite", () ->
            new Item(new Item.Properties()));

    public static final List<DeferredHolder<Item, Item>> wallItems = new ArrayList<>(List.of());
    public static final List<DeferredHolder<Item, Item>> stairItems = new ArrayList<>(List.of());
    public static final List<DeferredHolder<Item, Item>> slabItems = new ArrayList<>(List.of());

    public static void generateStairsSlabsWalls() {
        for (int i = 0; i < blocksWithStairsSlabsAndWalls.size(); i++) {
            Pair<String, DeferredHolder<Block, Block>> baseBlock = blocksWithStairsSlabsAndWalls.get(i);
            wallItems.add(createWallItems(baseBlock));
            stairItems.add(createStairItems(baseBlock));
            slabItems.add(createSlabItems(baseBlock));
        }
    }

    public static DeferredHolder<Item, Item> createWallItems(Pair<String, DeferredHolder<Block, Block>> baseBlock) {
        DeferredHolder<Block, Block> block = getBlock(baseBlock.getFirst()+"_wall", AstrologicalBlocks.wallBlocks);
        return ITEMS.register(block.getId().toString().substring(13), () ->
                        new BlockItem(block.get(), new Item.Properties())
        );
    }

    public static DeferredHolder<Item, Item> createStairItems(Pair<String, DeferredHolder<Block, Block>> baseBlock) {
        DeferredHolder<Block, Block> block = getBlock(baseBlock.getFirst()+"_stairs", AstrologicalBlocks.stairBlocks);
        return ITEMS.register(block.getId().toString().substring(13), () ->
                        new BlockItem(block.get(), new Item.Properties())
        );
    }

    public static DeferredHolder<Item, Item> createSlabItems(Pair<String, DeferredHolder<Block, Block>> baseBlock) {
        DeferredHolder<Block, Block> block = getBlock(baseBlock.getFirst()+"_slab", AstrologicalBlocks.slabBlocks);
        return ITEMS.register(block.getId().toString().substring(13), () ->
                        new BlockItem(block.get(), new Item.Properties())
        );
    }

    public static DeferredHolder<Block, Block> getBlock(String name, List<Pair<String, DeferredHolder<Block, Block>>> blockList) {
        for (int i = 0; i < blockList.size(); i++) {
            if (blockList.get(i).getFirst().equals(name)) {
                return blockList.get(i).getSecond();
            }
        }
        return AstrologicalBlocks.PRISMATIC_SELENITE; //this means it messed up
    }
}