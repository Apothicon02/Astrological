package com.Apothic0n.Astrological.core.objects;

import com.Apothic0n.Astrological.Astrological;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.*;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.Apothic0n.Astrological.core.objects.AstrologicalBlocks.blocksWithStairsSlabsAndWalls;

public final class AstrologicalItems extends Items {
    private AstrologicalItems() {}

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Astrological.MODID);

    public static final RegistryObject<Item> SLEEP = ITEMS.register("sleep", () ->
            new BlockItem(AstrologicalBlocks.SLEEP.get(), new Item.Properties()));
    public static final RegistryObject<Item> TUMOR = ITEMS.register("tumor", () ->
            new BlockItem(AstrologicalBlocks.TENDRILS.get(), new Item.Properties()));
    public static final RegistryObject<Item> CYST = ITEMS.register("cyst", () ->
            new BlockItem(AstrologicalBlocks.TUMOR.get(), new Item.Properties()));
    public static final RegistryObject<Item> CRYING_DUCT = ITEMS.register("crying_duct", () ->
            new BlockItem(AstrologicalBlocks.CRYING_DUCT.get(), new Item.Properties()));
    public static final RegistryObject<Item> INSOMNIA_VENT = ITEMS.register("insomnia_vent", () ->
            new BlockItem(AstrologicalBlocks.INSOMNIA_VENT.get(), new Item.Properties()));
    public static final RegistryObject<Item> OCHRE_SELENITE = ITEMS.register("ochre_selenite", () ->
            new OchreSeleniteBlockItem(AstrologicalBlocks.OCHRE_SELENITE.get(), new Item.Properties()));
    public static final RegistryObject<Item> VERDANT_SELENITE = ITEMS.register("verdant_selenite", () ->
            new VerdantSeleniteBlockItem(AstrologicalBlocks.VERDANT_SELENITE.get(), new Item.Properties()));
    public static final RegistryObject<Item> PEARLESCENT_SELENITE = ITEMS.register("pearlescent_selenite", () ->
            new PearlescentSeleniteBlockItem(AstrologicalBlocks.PEARLESCENT_SELENITE.get(), new Item.Properties()));
    public static final RegistryObject<Item> PURPURITE = ITEMS.register("purpurite", () ->
            new BlockItem(AstrologicalBlocks.PURPURITE.get(), new Item.Properties()));
    public static final RegistryObject<Item> TRIPHYLITE = ITEMS.register("triphylite", () ->
            new BlockItem(AstrologicalBlocks.TRIPHYLITE.get(), new Item.Properties()));
    public static final RegistryObject<Item> JADE = ITEMS.register("jade", () ->
            new BlockItem(AstrologicalBlocks.JADE.get(), new Item.Properties()));
    public static final RegistryObject<Item> REINFORCED_JADE = ITEMS.register("reinforced_jade", () ->
            new BlockItem(AstrologicalBlocks.REINFORCED_JADE.get(), new Item.Properties()));
    public static final RegistryObject<Item> JADE_BRICKS = ITEMS.register("jade_bricks", () ->
            new BlockItem(AstrologicalBlocks.JADE_BRICKS.get(), new Item.Properties()));
    public static final RegistryObject<Item> POLISHED_JADE = ITEMS.register("polished_jade", () ->
            new BlockItem(AstrologicalBlocks.POLISHED_JADE.get(), new Item.Properties()));
    public static final RegistryObject<Item> MARBLED_JADE_SLAB = ITEMS.register("marbled_jade_slab", () ->
            new BlockItem(AstrologicalBlocks.MARBLED_JADE_SLAB.get(), new Item.Properties()));
    public static final RegistryObject<Item> LIGHT_JADE = ITEMS.register("light_jade", () ->
            new BlockItem(AstrologicalBlocks.LIGHT_JADE.get(), new Item.Properties()));
    public static final RegistryObject<Item> SELENITE_WALL = ITEMS.register("selenite_wall", () ->
            new BlockItem(AstrologicalBlocks.SELENITE_WALL.get(), new Item.Properties()));
    public static final RegistryObject<Item> PRISMATIC_SELENITE = ITEMS.register("prismatic_selenite", () ->
            new BlockItem(AstrologicalBlocks.PRISMATIC_SELENITE.get(), new Item.Properties()));
    public static final RegistryObject<Item> LIGHT_JADE_TILES = ITEMS.register("light_jade_tiles", () ->
            new BlockItem(AstrologicalBlocks.LIGHT_JADE_TILES.get(), new Item.Properties()));
    public static final RegistryObject<Item> CRACKED_LIGHT_JADE_TILES = ITEMS.register("cracked_light_jade_tiles", () ->
            new BlockItem(AstrologicalBlocks.CRACKED_LIGHT_JADE_TILES.get(), new Item.Properties()));
    public static final RegistryObject<Item> PURPURITE_TILES = ITEMS.register("purpurite_tiles", () ->
            new BlockItem(AstrologicalBlocks.PURPURITE_TILES.get(), new Item.Properties()));

    public static final RegistryObject<Item> PURPURITE_BLOB = ITEMS.register("purpurite_blob", () ->
            new Item(new Item.Properties()));
    public static final RegistryObject<Item> POPPED_PURPURITE = ITEMS.register("popped_purpurite", () ->
            new Item(new Item.Properties()));

    public static final List<RegistryObject<Item>> wallItems = new ArrayList<>(List.of());
    public static final List<RegistryObject<Item>> stairItems = new ArrayList<>(List.of());
    public static final List<RegistryObject<Item>> slabItems = new ArrayList<>(List.of());

    public static void generateStairsSlabsWalls() {
        for (int i = 0; i < blocksWithStairsSlabsAndWalls.size(); i++) {
            RegistryObject<Block> baseBlock = blocksWithStairsSlabsAndWalls.get(i);
            wallItems.add(createWallItems(baseBlock));
            stairItems.add(createStairItems(baseBlock));
            slabItems.add(createSlabItems(baseBlock));
        }
    }

    public static RegistryObject<Item> createWallItems(RegistryObject<Block> baseBlock) {
        RegistryObject<Block> block = getBlock(baseBlock, AstrologicalBlocks.wallBlocks);
        return ITEMS.register(block.getId().toString().substring(13), () ->
                        new BlockItem(block.get(), new Item.Properties())
        );
    }

    public static RegistryObject<Item> createStairItems(RegistryObject<Block> baseBlock) {
        RegistryObject<Block> block = getBlock(baseBlock, AstrologicalBlocks.stairBlocks);
        return ITEMS.register(block.getId().toString().substring(13), () ->
                        new BlockItem(block.get(), new Item.Properties())
        );
    }

    public static RegistryObject<Item> createSlabItems(RegistryObject<Block> baseBlock) {
        RegistryObject<Block> block = getBlock(baseBlock, AstrologicalBlocks.slabBlocks);
        return ITEMS.register(block.getId().toString().substring(13), () ->
                        new BlockItem(block.get(), new Item.Properties())
        );
    }

    public static RegistryObject<Block> getBlock(RegistryObject<Block> block, List<Map<RegistryObject<Block>, RegistryObject<Block>>> blockList) {
        for (int i = 0; i < blockList.size(); i++) {
            if (blockList.get(i).containsKey(block)) {
                return blockList.get(i).get(block);
            }
        }
        return AstrologicalBlocks.PRISMATIC_SELENITE; //this means it messed up
    }
}