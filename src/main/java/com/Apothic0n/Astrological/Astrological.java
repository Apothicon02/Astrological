package com.Apothic0n.Astrological;

import com.Apothic0n.Astrological.api.AstrologicalDensityFunctions;
import com.Apothic0n.Astrological.api.AstrologicalJsonReader;
import com.Apothic0n.Astrological.api.effect.AstrologicalMobEffects;
import com.Apothic0n.Astrological.api.biome.features.AstrologicalFeatureRegistry;
import com.Apothic0n.Astrological.core.objects.AstrologicalBlockEntities;
import com.Apothic0n.Astrological.core.objects.AstrologicalBlocks;
import com.Apothic0n.Astrological.core.objects.AstrologicalItems;
import com.google.common.collect.ImmutableList;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod(Astrological.MODID)
public class Astrological {
    public static final String MODID = "astrological";

    public Astrological(IEventBus eventBus, ModContainer container) throws Exception {
        eventBus.addListener(this::clientSetup);
        eventBus.addListener(this::commonSetup);

        AstrologicalJsonReader.main();
        AstrologicalDensityFunctions.register(eventBus);
        AstrologicalBlocks.BLOCKS.register(eventBus);
        AstrologicalBlockEntities.BLOCK_ENTITIES.register(eventBus);
        AstrologicalBlocks.generateStairsSlabsWalls();
        AstrologicalItems.ITEMS.register(eventBus);
        AstrologicalItems.generateStairsSlabsWalls();
        AstrologicalFeatureRegistry.FEATURES.register(eventBus);
        AstrologicalMobEffects.MOB_EFFECTS.register(eventBus);
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        AstrologicalBlocks.fixBlockRenderLayers();
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        if (AstrologicalJsonReader.spawnInOuterEnd) {
            ServerLevel.END_SPAWN_POINT = new BlockPos(2500, 0, 0);
        }
        event.enqueueWork(() -> {
            addLight(Blocks.CHORUS_FLOWER.getStateDefinition().getPossibleStates(), 9);
        });
    }

    private void addLight(ImmutableList<BlockState> blockStates, int light) {
        for (int i = 0; i < blockStates.size(); i++) {
            blockStates.get(i).lightEmission = light;
        }
    }
}
