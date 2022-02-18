package com.Apothic0n.Avoid.api.biome.features;

import com.Apothic0n.Avoid.Avoid;
import com.Apothic0n.Avoid.api.biome.features.configuartions.CatchingFallConfiguration;
import com.Apothic0n.Avoid.api.biome.features.configuartions.FloatingBlobConfiguration;
import com.Apothic0n.Avoid.api.biome.features.configuartions.LichenConfiguration;
import com.Apothic0n.Avoid.api.biome.features.configuartions.VerticalBlobConfiguration;
import com.Apothic0n.Avoid.api.biome.features.types.*;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.LargeDripstoneConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SpringConfiguration;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public abstract class AvoidFeatureRegistry {

    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, Avoid.MODID);

    public static final RegistryObject<Feature<VerticalBlobConfiguration>> ADDITIVE_BLOB = FEATURES.register("additive_blob", () ->
            new AdditiveBlobFeature(VerticalBlobConfiguration.CODEC));

    public static final RegistryObject<Feature<VerticalBlobConfiguration>> ADDITIVE_GROUND_BLOB = FEATURES.register("additive_ground_blob", () ->
            new AdditiveGroundBlobFeature(VerticalBlobConfiguration.CODEC));

    public static final RegistryObject<Feature<FloatingBlobConfiguration>> FLOATING_BLOB = FEATURES.register("floating_blob", () ->
            new FloatingBlobFeature(FloatingBlobConfiguration.CODEC));

    public static final RegistryObject<Feature<CatchingFallConfiguration>> CATCHING_FALL = FEATURES.register("catching_fall", () ->
            new CatchingFallFeature(CatchingFallConfiguration.CODEC));

    public static final RegistryObject<Feature<LargeDripstoneConfiguration>> LARGE_BASALT_PILLAR = FEATURES.register("large_basalt_pillar", () ->
            new LargeBasaltPillarFeature(LargeDripstoneConfiguration.CODEC));

    public static final RegistryObject<Feature<LargeDripstoneConfiguration>> THIN_BLACKSTONE_PILLAR = FEATURES.register("thin_blackstone_pillar", () ->
            new LargeBlackstonePillarFeature(LargeDripstoneConfiguration.CODEC));

    public static final RegistryObject<Feature<VerticalBlobConfiguration>> CRYSTAL_SPIKE = FEATURES.register("crystal_spike", () ->
            new CrystalSpikeFeature(VerticalBlobConfiguration.CODEC));

    public static final RegistryObject<Feature<VerticalBlobConfiguration>> SPIRAL = FEATURES.register("spiral", () ->
            new SpiralFeature(VerticalBlobConfiguration.CODEC));

    public static final RegistryObject<Feature<LichenConfiguration>> LICHEN = FEATURES.register("lichen", () ->
            new LichenFeature(LichenConfiguration.CODEC));

    public static void register(IEventBus eventBus) {
        FEATURES.register(eventBus);
    }
}
