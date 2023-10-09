package com.Apothic0n.Inversia.core.objects;

import com.Apothic0n.Inversia.Inversia;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class InversiaItems extends Items {
    private InversiaItems() {}

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Inversia.MODID);

    public static final RegistryObject<Item> SLEEP = ITEMS.register("sleep", () ->
            new BlockItem(InversiaBlocks.SLEEP.get(), new Item.Properties()));
    public static final RegistryObject<Item> TUMOR = ITEMS.register("tumor", () ->
            new BlockItem(InversiaBlocks.TENDRILS.get(), new Item.Properties()));
    public static final RegistryObject<Item> CYST = ITEMS.register("cyst", () ->
            new BlockItem(InversiaBlocks.TUMOR.get(), new Item.Properties()));
    public static final RegistryObject<Item> CRYING_DUCT = ITEMS.register("crying_duct", () ->
            new BlockItem(InversiaBlocks.CRYING_DUCT.get(), new Item.Properties()));
    public static final RegistryObject<Item> INSOMNIA_VENT = ITEMS.register("insomnia_vent", () ->
            new BlockItem(InversiaBlocks.INSOMNIA_VENT.get(), new Item.Properties()));
    public static final RegistryObject<Item> OCHRE_SELENITE = ITEMS.register("ochre_selenite", () ->
            new OchreSeleniteBlockItem(InversiaBlocks.OCHRE_SELENITE.get(), new Item.Properties()));
    public static final RegistryObject<Item> VERDANT_SELENITE = ITEMS.register("verdant_selenite", () ->
            new VerdantSeleniteBlockItem(InversiaBlocks.VERDANT_SELENITE.get(), new Item.Properties()));
    public static final RegistryObject<Item> PEARLESCENT_SELENITE = ITEMS.register("pearlescent_selenite", () ->
            new PearlescentSeleniteBlockItem(InversiaBlocks.PEARLESCENT_SELENITE.get(), new Item.Properties()));
    public static final RegistryObject<Item> PURPURITE = ITEMS.register("purpurite", () ->
            new BlockItem(InversiaBlocks.PURPURITE.get(), new Item.Properties()));
    public static final RegistryObject<Item> TRIPHYLITE = ITEMS.register("triphylite", () ->
            new BlockItem(InversiaBlocks.TRIPHYLITE.get(), new Item.Properties()));
    public static final RegistryObject<Item> JADE = ITEMS.register("jade", () ->
            new BlockItem(InversiaBlocks.JADE.get(), new Item.Properties()));
    public static final RegistryObject<Item> REINFORCED_JADE = ITEMS.register("reinforced_jade", () ->
            new BlockItem(InversiaBlocks.REINFORCED_JADE.get(), new Item.Properties()));
    public static final RegistryObject<Item> JADE_BRICKS = ITEMS.register("jade_bricks", () ->
            new BlockItem(InversiaBlocks.JADE_BRICKS.get(), new Item.Properties()));
    public static final RegistryObject<Item> POLISHED_JADE = ITEMS.register("polished_jade", () ->
            new BlockItem(InversiaBlocks.POLISHED_JADE.get(), new Item.Properties()));
    public static final RegistryObject<Item> MARBLED_JADE_SLAB = ITEMS.register("marbled_jade_slab", () ->
            new BlockItem(InversiaBlocks.MARBLED_JADE_SLAB.get(), new Item.Properties()));
    public static final RegistryObject<Item> LIGHT_JADE = ITEMS.register("light_jade", () ->
            new BlockItem(InversiaBlocks.LIGHT_JADE.get(), new Item.Properties()));
    public static final RegistryObject<Item> SELENITE_WALL = ITEMS.register("selenite_wall", () ->
            new BlockItem(InversiaBlocks.SELENITE_WALL.get(), new Item.Properties()));
    public static final RegistryObject<Item> PRISMATIC_SELENITE = ITEMS.register("prismatic_selenite", () ->
            new BlockItem(InversiaBlocks.PRISMATIC_SELENITE.get(), new Item.Properties()));
    public static final RegistryObject<Item> LIGHT_JADE_TILES = ITEMS.register("light_jade_tiles", () ->
            new BlockItem(InversiaBlocks.LIGHT_JADE_TILES.get(), new Item.Properties()));
    public static final RegistryObject<Item> CRACKED_LIGHT_JADE_TILES = ITEMS.register("cracked_light_jade_tiles", () ->
            new BlockItem(InversiaBlocks.CRACKED_LIGHT_JADE_TILES.get(), new Item.Properties()));
    public static final RegistryObject<Item> PURPURITE_TILES = ITEMS.register("purpurite_tiles", () ->
            new BlockItem(InversiaBlocks.PURPURITE_TILES.get(), new Item.Properties()));
}