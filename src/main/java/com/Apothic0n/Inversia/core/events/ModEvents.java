package com.Apothic0n.Inversia.core.events;

import com.Apothic0n.Inversia.Inversia;
import com.Apothic0n.Inversia.api.MaskingSource;
import com.Apothic0n.Inversia.core.objects.InversiaBlocks;
import com.Apothic0n.Inversia.core.objects.InversiaItems;
import com.google.common.collect.ImmutableList;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.util.FastColor;
import net.minecraft.util.Mth;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
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
@Mod.EventBusSubscriber(modid = Inversia.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModEvents {

    @SubscribeEvent
    public static void registerMask(RegisterClientReloadListenersEvent event) {
        MaskingSource.register();
    }

    @SubscribeEvent
    public static void addItemsToTabs(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey().equals(CreativeModeTabs.NATURAL_BLOCKS)) {
            event.accept(InversiaItems.SLEEP.get(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.accept(InversiaItems.TUMOR.get(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.accept(InversiaItems.CYST.get(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.accept(InversiaItems.CRYING_DUCT.get(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.accept(InversiaItems.INSOMNIA_VENT.get(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.accept(InversiaItems.OCHRE_SELENITE.get(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.accept(InversiaItems.VERDANT_SELENITE.get(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.accept(InversiaItems.PEARLESCENT_SELENITE.get(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
        }
    }

    private static final PerlinSimplexNoise SATURATION_NOISE = new PerlinSimplexNoise(new WorldgenRandom(new LegacyRandomSource(2345L)), ImmutableList.of(0));
    private static final PerlinSimplexNoise BRIGHTNESS_NOISE = new PerlinSimplexNoise(new WorldgenRandom(new LegacyRandomSource(5432L)), ImmutableList.of(0));
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
                        float red = (float) Mth.clamp(FastColor.ABGR32.red(color), 1, 255)/255;
                        float green = (float) Mth.clamp(FastColor.ABGR32.green(color), 1, 255)/255;
                        float blue = (float) Mth.clamp(FastColor.ABGR32.blue(color), 1, 255)/255;
                        float gray = (float) ((red + green + blue) / (3 + brighten));
                        return FastColor.ABGR32.color(FastColor.ABGR32.alpha(color),
                                (int) (Mth.clamp(((red + (gray - red)) * saturate) + temperature, 0, 1) * 255),
                                (int) (Mth.clamp(((green + (gray - green)) * saturate) + temperature, 0, 1) * 255),
                                (int) (Mth.clamp(((blue + (gray - blue)) * saturate) - temperature, 0, 1) * 255));
                    } else {
                        return -328966;
                    }
                },
                Blocks.END_STONE, Blocks.END_STONE_BRICKS, Blocks.END_STONE_BRICK_STAIRS, Blocks.END_STONE_BRICK_SLAB, Blocks.END_STONE_BRICK_WALL,
                InversiaBlocks.INSOMNIA_VENT.get());

        event.register((blockState, blockAndTintGetter, blockPos, tint) -> {
                    if (blockPos != null && Minecraft.getInstance().level != null) {
                        int x = blockPos.getX();
                        int z = blockPos.getZ();
                        int color = -328966;
                        double saturate = Mth.clamp(SATURATION_NOISE.getValue(x * 0.1, z * 0.1, false) * 0.33, -0.03, 0.03)+1.1;
                        double brighten = Mth.clamp(BRIGHTNESS_NOISE.getValue(x * 0.025, z * 0.025, false) * 0.3, -0.33, 0.33);
                        float red = (float) Mth.clamp(FastColor.ABGR32.red(color), 1, 255)/255;
                        float green = (float) Mth.clamp(FastColor.ABGR32.green(color), 1, 255)/255;
                        float blue = (float) Mth.clamp(FastColor.ABGR32.blue(color), 1, 255)/255;
                        float gray = (float) ((red + green + blue) / (3 + brighten));
                        return FastColor.ABGR32.color(FastColor.ABGR32.alpha(color),
                                (int) (Mth.clamp(((red + (gray - red)) * saturate), 0, 1) * 255),
                                (int) (Mth.clamp(((green + (gray - green)) * saturate), 0, 1) * 255),
                                (int) (Mth.clamp(((blue + (gray - blue)) * saturate), 0, 1) * 255));
                    } else {
                        return -328966;
                    }
                },
                InversiaBlocks.OCHRE_SELENITE.get(), InversiaBlocks.VERDANT_SELENITE.get(), InversiaBlocks.PEARLESCENT_SELENITE.get());
    }
}