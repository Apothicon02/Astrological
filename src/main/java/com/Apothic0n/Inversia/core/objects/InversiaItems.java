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
}