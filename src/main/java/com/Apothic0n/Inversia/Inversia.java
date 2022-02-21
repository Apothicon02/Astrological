package com.Apothic0n.Inversia;

import com.Apothic0n.Inversia.api.biome.features.InversiaFeatureRegistry;
import com.Apothic0n.Inversia.core.objects.InversiaBlocks;
import com.Apothic0n.Inversia.core.objects.InversiaItems;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Inversia.MODID)
public class Inversia {
    public static final String MODID = "inversia";

    public Inversia() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::commonSetup);

        InversiaFeatureRegistry.register(eventBus);
        InversiaBlocks.BLOCKS.register(eventBus);
        InversiaItems.ITEMS.register(eventBus);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        InversiaBlocks.fixBlockRenderLayers();
    }
}
