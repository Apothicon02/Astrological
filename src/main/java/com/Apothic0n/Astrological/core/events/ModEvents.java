package com.Apothic0n.Astrological.core.events;

import com.Apothic0n.Astrological.Astrological;
import com.Apothic0n.Astrological.core.objects.AstrologicalBlocks;
import com.Apothic0n.Astrological.core.objects.AstrologicalItems;
import com.google.common.collect.ImmutableList;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.FastColor;
import net.minecraft.util.Mth;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.LegacyRandomSource;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.synth.PerlinSimplexNoise;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.List;

@EventBusSubscriber(modid = Astrological.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModEvents {

//    @SubscribeEvent
//    public static void registerMask(RegisterClientReloadListenersEvent event) {
//        MaskingSource.register();
//    }

    @SubscribeEvent
    public static void addItemsToTabs(BuildCreativeModeTabContentsEvent event) {
        ResourceKey<CreativeModeTab> tab = event.getTabKey();
        if (tab.equals(CreativeModeTabs.NATURAL_BLOCKS)) {
            event.accept(AstrologicalItems.SLEEP.get(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.accept(AstrologicalItems.TUMOR.get(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.accept(AstrologicalItems.CYST.get(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.accept(AstrologicalItems.CRYING_DUCT.get(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.accept(AstrologicalItems.INSOMNIA_VENT.get(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.accept(AstrologicalItems.OCHRE_SELENITE.get(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.accept(AstrologicalItems.VERDANT_SELENITE.get(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.accept(AstrologicalItems.PEARLESCENT_SELENITE.get(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.accept(AstrologicalItems.PURPURITE.get(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.accept(AstrologicalItems.TRIPHYLITE.get(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.accept(AstrologicalItems.JADE.get(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.accept(AstrologicalItems.LIGHT_JADE.get(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
        } else if (tab.equals(CreativeModeTabs.BUILDING_BLOCKS)) {
            event.accept(AstrologicalItems.PRISMATIC_SELENITE.get(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.accept(AstrologicalItems.SELENITE_WALL.get(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.accept(AstrologicalItems.REINFORCED_JADE.get(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.accept(AstrologicalItems.JADE_BRICKS.get(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.accept(AstrologicalItems.POLISHED_JADE.get(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.accept(AstrologicalItems.MARBLED_JADE_SLAB.get(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.accept(AstrologicalItems.LIGHT_JADE_TILES.get(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.accept(AstrologicalItems.CRACKED_LIGHT_JADE_TILES.get(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.accept(AstrologicalItems.PURPURITE_TILES.get(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            List<List<DeferredHolder<Item, Item>>> buildingBlockItems = List.of(AstrologicalItems.wallItems, AstrologicalItems.stairItems, AstrologicalItems.slabItems);
            for (int i = 0; i < buildingBlockItems.size(); i++) {
                List<DeferredHolder<Item, Item>> blockItemTypeList = buildingBlockItems.get(i);
                for (int o = 0; o < blockItemTypeList.size(); o++) {
                    event.accept(blockItemTypeList.get(o).get(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
                }
            }
        } else if (tab.equals(CreativeModeTabs.INGREDIENTS)) {
            event.accept(AstrologicalItems.PURPURITE_BLOB.get(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.accept(AstrologicalItems.POPPED_PURPURITE.get(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
        }
    }

    public static final PerlinSimplexNoise SATURATION_NOISE = new PerlinSimplexNoise(new WorldgenRandom(new LegacyRandomSource(2345L)), ImmutableList.of(0));
    public static final PerlinSimplexNoise BRIGHTNESS_NOISE = new PerlinSimplexNoise(new WorldgenRandom(new LegacyRandomSource(5432L)), ImmutableList.of(0));
    @SubscribeEvent
    public static void onBlockColors(RegisterColorHandlersEvent.Block event) {
        event.register((blockState, blockAndTintGetter, blockPos, tint) -> {
                    if (blockPos != null && Minecraft.getInstance().level != null) {
                        int x = blockPos.getX();
                        int z = blockPos.getZ();
                        int color = -328966;
                        double saturate = Mth.clamp(SATURATION_NOISE.getValue(x * 0.1, z * 0.1, false) * 0.33, -0.03, 0.03)+1.1;
                        double brighten = Mth.clamp(BRIGHTNESS_NOISE.getValue(x * 0.025, z * 0.025, false) * 0.3, -0.33, 0.33);
                        float red = (float) FastColor.ABGR32.red(color)/255;
                        float green = (float) FastColor.ABGR32.green(color)/255;
                        float blue = (float) FastColor.ABGR32.blue(color)/255;
                        float gray = (float) ((red + green + blue) / (3 + brighten));
                        return FastColor.ABGR32.color(FastColor.ABGR32.alpha(color),
                                (int) (Mth.clamp(((blue + (gray - blue)) * saturate), 0, 1) * 255),
                                (int) (Mth.clamp(((green + (gray - green)) * saturate), 0, 1) * 255),
                                (int) (Mth.clamp(((red + (gray - red)) * saturate), 0, 1) * 255));
                    } else {
                        return -328966;
                    }
                },
                AstrologicalBlocks.OCHRE_SELENITE.get(), AstrologicalBlocks.VERDANT_SELENITE.get(), AstrologicalBlocks.PEARLESCENT_SELENITE.get(),
                AstrologicalBlocks.JADE.get(),
                Blocks.END_STONE, Blocks.END_STONE_BRICKS, Blocks.END_STONE_BRICK_STAIRS, Blocks.END_STONE_BRICK_SLAB, Blocks.END_STONE_BRICK_WALL);

        event.register((blockState, blockAndTintGetter, blockPos, tint) -> {
                    Level level = Minecraft.getInstance().level;
                    if (level != null) {
                        int color = -19457; //night
                        float time = level.getDayTime();
                        if (time > 24000) {
                            time = (float) (time - (Math.floor(time / 24000) * 24000));
                        }
                        if ((time >= 22000 || time <= 500) || (time >= 12000 && time <= 13500)) { //dawn & dusk
                            color = -9549;
                        } else if (time <= 12000) { //day
                            color = -3670093;
                        }
                        return color;
                    } else {
                        return -328966;
                    }
                },
                AstrologicalBlocks.PRISMATIC_SELENITE.get(), AstrologicalBlocks.SELENITE_WALL.get());
    }
    @SubscribeEvent
    public static void onItemColors(RegisterColorHandlersEvent.Item event) {
        event.register((blockAndTintGetter, tint) -> {
                    Level level = Minecraft.getInstance().level;
                    if (level != null) {
                        int color = -19457; //night
                        float time = level.getDayTime();
                        if (time > 24000) {
                            time = (float) (time - (Math.floor(time / 24000) * 24000));
                        }
                        if ((time >= 22000 || time <= 500) || (time >= 12000 && time <= 13500)) { //dawn & dusk
                            color = -9549;
                        } else if (time <= 12000) { //day
                            color = -3670093;
                        }
                        return color;
                    } else {
                        return -328966;
                    }
                },
                AstrologicalBlocks.PRISMATIC_SELENITE.get(), AstrologicalBlocks.SELENITE_WALL.get());
    }
}