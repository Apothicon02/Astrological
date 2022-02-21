package com.Apothic0n.Inversia.core.objects;

import com.Apothic0n.Inversia.Inversia;
import net.minecraft.world.food.Foods;
import net.minecraft.world.item.*;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class InversiaItems extends Items {
    private InversiaItems() {}

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Inversia.MODID);

    //Block Items
    public static final RegistryObject<Item> BLAZING_LICHEN_ITEM = ITEMS.register("blazing_lichen", () ->
            new BlockItem(InversiaBlocks.BLAZING_LICHEN.get(),
                    new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS)));

    public static final RegistryObject<Item> VOID_BERRIES = ITEMS.register("void_berries", () ->
            new ItemNameBlockItem(InversiaBlocks.VOID_VINES.get(),
                    new Item.Properties().food(Foods.GLOW_BERRIES).tab(CreativeModeTab.TAB_FOOD)));
}
