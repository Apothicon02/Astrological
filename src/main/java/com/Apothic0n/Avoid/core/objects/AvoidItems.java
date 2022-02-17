package com.Apothic0n.Avoid.core.objects;

import com.Apothic0n.Avoid.Avoid;
import net.minecraft.world.food.Foods;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class AvoidItems {
    private AvoidItems() {}

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Avoid.MODID);

    //Block Items
    public static final RegistryObject<Item> BLAZING_LICHEN_ITEM = ITEMS.register("blazing_lichen", () ->
            new BlockItem(AvoidBlocks.BLAZING_LICHEN.get(),
                    new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS)));

    public static final RegistryObject<Item> VOID_BERRIES = ITEMS.register("void_berries", () ->
            new ItemNameBlockItem(AvoidBlocks.VOID_VINES.get(),
                    new Item.Properties().food(Foods.GLOW_BERRIES).tab(CreativeModeTab.TAB_FOOD)));

}
