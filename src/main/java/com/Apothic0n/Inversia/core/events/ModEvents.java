package com.Apothic0n.Inversia.core.events;

import com.Apothic0n.Inversia.Inversia;
import com.Apothic0n.Inversia.api.MaskingSource;
import com.Apothic0n.Inversia.core.objects.InversiaBlocks;
import com.Apothic0n.Inversia.core.objects.InversiaItems;
import com.google.common.collect.ImmutableList;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.FastColor;
import net.minecraft.util.Mth;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.LegacyRandomSource;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.synth.PerlinSimplexNoise;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterClientReloadListenersEvent;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;
import java.util.Map;

@Mod.EventBusSubscriber(modid = Inversia.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModEvents {

    @SubscribeEvent
    public static void registerMask(RegisterClientReloadListenersEvent event) {
        MaskingSource.register();
    }

    @SubscribeEvent
    public static void addItemsToTabs(BuildCreativeModeTabContentsEvent event) {
        ResourceKey<CreativeModeTab> tab = event.getTabKey();
        if (tab.equals(CreativeModeTabs.NATURAL_BLOCKS)) {
            event.accept(InversiaItems.SLEEP.get(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.accept(InversiaItems.TUMOR.get(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.accept(InversiaItems.CYST.get(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.accept(InversiaItems.CRYING_DUCT.get(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.accept(InversiaItems.INSOMNIA_VENT.get(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.accept(InversiaItems.OCHRE_SELENITE.get(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.accept(InversiaItems.VERDANT_SELENITE.get(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.accept(InversiaItems.PEARLESCENT_SELENITE.get(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.accept(InversiaItems.PURPURITE.get(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.accept(InversiaItems.TRIPHYLITE.get(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.accept(InversiaItems.JADE.get(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.accept(InversiaItems.LIGHT_JADE.get(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
        } else if (tab.equals(CreativeModeTabs.BUILDING_BLOCKS)) {
            event.accept(InversiaItems.PRISMATIC_SELENITE.get(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.accept(InversiaItems.SELENITE_WALL.get(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.accept(InversiaItems.REINFORCED_JADE.get(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.accept(InversiaItems.JADE_BRICKS.get(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.accept(InversiaItems.POLISHED_JADE.get(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.accept(InversiaItems.MARBLED_JADE_SLAB.get(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.accept(InversiaItems.LIGHT_JADE_TILES.get(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.accept(InversiaItems.CRACKED_LIGHT_JADE_TILES.get(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.accept(InversiaItems.PURPURITE_TILES.get(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            List<List<RegistryObject<Item>>> buildingBlockItems = List.of(InversiaItems.wallItems, InversiaItems.stairItems, InversiaItems.slabItems);
            for (int i = 0; i < buildingBlockItems.size(); i++) {
                List<RegistryObject<Item>> blockItemTypeList = buildingBlockItems.get(i);
                for (int o = 0; o < blockItemTypeList.size(); o++) {
                    event.accept(blockItemTypeList.get(o).get(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
                }
            }
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
                        int maxHeight = Minecraft.getInstance().level.getMaxBuildHeight();
                        int midHeight = maxHeight/2;
                        int minHeight = Minecraft.getInstance().level.getMinBuildHeight();
                        int offset = 0;
                        if (minHeight < 0) {
                            offset = -(minHeight);
                            maxHeight = maxHeight + offset;
                            midHeight = (maxHeight/2);
                        }
                        BlockPos offsetPos = blockPos.above(offset);
                        double temperature = 0;
                        if (offsetPos.getY() < midHeight) {
                            temperature = (offsetPos.getY()-midHeight) * 0.005;
                        } else {
                            temperature = Mth.clamp(offsetPos.getY()-midHeight * 0.005, -1, 0.05);
                        }
                        double saturate = Mth.clamp(SATURATION_NOISE.getValue(x * 0.1, z * 0.1, false) * 0.33, -0.03, 0.03)+1.1;
                        double brighten = Mth.clamp(BRIGHTNESS_NOISE.getValue(x * 0.025, z * 0.025, false) * 0.3, -0.33, 0.33);
                        float red = (float) FastColor.ABGR32.red(color)/255;
                        float green = (float) FastColor.ABGR32.green(color)/255;
                        float blue = (float) FastColor.ABGR32.blue(color)/255;
                        float gray = (float) ((red + green + blue) / (3 + brighten));
                        return FastColor.ABGR32.color(FastColor.ABGR32.alpha(color),
                                (int) (Mth.clamp(((blue + (gray - blue)) * saturate) + temperature, 0, 1) * 255),
                                (int) (Mth.clamp(((green + (gray - green)) * saturate) + temperature, 0, 1) * 255),
                                (int) (Mth.clamp(((red + (gray - red)) * saturate) - temperature, 0, 1) * 255));
                    } else {
                        return -328966;
                    }
                },
                Blocks.END_STONE, Blocks.END_STONE_BRICKS, Blocks.END_STONE_BRICK_STAIRS, Blocks.END_STONE_BRICK_SLAB, Blocks.END_STONE_BRICK_WALL);

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
                InversiaBlocks.OCHRE_SELENITE.get(), InversiaBlocks.VERDANT_SELENITE.get(), InversiaBlocks.PEARLESCENT_SELENITE.get(),
                InversiaBlocks.JADE.get());

        event.register((blockState, blockAndTintGetter, blockPos, tint) -> {
                    Level level = Minecraft.getInstance().level;
                    if (level != null) {
                        int color = -19457; //night
                        float time = level.getDayTime();
                        if (time > 24000) {
                            time = (float) (time - (Math.floor(time / 24000) * 24000));
                        }
                        if ((time >= 22000 || time <= 500) || (time >= 12000 && time <= 13500)) { //dawn & dusk
                            color = -3670093;
                        } else if (time <= 12000) { //day
                            color = -9549;
                        }
                        return color;
                    } else {
                        return -328966;
                    }
                },
                InversiaBlocks.PRISMATIC_SELENITE.get(), InversiaBlocks.SELENITE_WALL.get());
    }
}