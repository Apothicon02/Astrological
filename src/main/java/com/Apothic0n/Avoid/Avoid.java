package com.Apothic0n.Avoid;

import com.Apothic0n.Avoid.api.biome.features.AvoidFeatureRegistry;
import com.Apothic0n.Avoid.core.objects.AvoidBlocks;
import com.Apothic0n.Avoid.core.objects.AvoidItems;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Avoid.MODID)
public class Avoid {
    public static final String MODID = "avoid";

    public Avoid() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::commonSetup);

        AvoidFeatureRegistry.register(eventBus);
        AvoidBlocks.BLOCKS.register(eventBus);
        AvoidItems.ITEMS.register(eventBus);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        AvoidBlocks.fixBlockRenderLayers();
    }
}
