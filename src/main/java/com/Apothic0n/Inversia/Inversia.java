package com.Apothic0n.Inversia;

import com.Apothic0n.Inversia.api.InversiaDensityFunctions;
import com.Apothic0n.Inversia.api.effect.InversiaMobEffects;
import com.Apothic0n.Inversia.api.biome.features.InversiaFeatureRegistry;
import com.Apothic0n.Inversia.core.objects.InversiaBlockEntities;
import com.Apothic0n.Inversia.core.objects.InversiaBlocks;
import com.Apothic0n.Inversia.core.objects.InversiaItems;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Inversia.MODID)
public class Inversia {
    public static final String MODID = "inversia";

    public Inversia() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);

        InversiaDensityFunctions.register(eventBus);
        InversiaBlocks.BLOCKS.register(eventBus);
        InversiaBlockEntities.BLOCK_ENTITIES.register(eventBus);
        InversiaBlocks.generateStairsSlabsWalls();
        InversiaItems.ITEMS.register(eventBus);
        InversiaItems.generateStairsSlabsWalls();
        InversiaFeatureRegistry.FEATURES.register(eventBus);
        InversiaMobEffects.MOB_EFFECTS.register(eventBus);
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        InversiaBlocks.fixBlockRenderLayers();
    }
}
