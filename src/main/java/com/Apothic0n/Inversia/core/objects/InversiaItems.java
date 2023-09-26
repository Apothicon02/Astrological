package com.Apothic0n.Inversia.core.objects;

import com.Apothic0n.Inversia.Inversia;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class InversiaItems extends Items {
    private InversiaItems() {}

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Inversia.MODID);

    public static final RegistryObject<Item> SLEEP = ITEMS.register("sleep", () ->
            new BlockItem(InversiaBlocks.SLEEP.get(), new Item.Properties()));
}