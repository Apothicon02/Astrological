package com.Apothic0n.Inversia.api.biome.features;

import com.Apothic0n.Inversia.Inversia;
import com.Apothic0n.Inversia.api.biome.features.configurations.AnvilRockConfiguration;
import com.Apothic0n.Inversia.api.biome.features.types.AnvilRockFeature;
import com.Apothic0n.Inversia.api.biome.features.types.CryingDuctFeature;
import com.Apothic0n.Inversia.api.biome.features.types.CrystalFeature;
import com.Apothic0n.Inversia.api.biome.features.types.TendrilFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.PointedDripstoneConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public abstract class InversiaFeatureRegistry {
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, Inversia.MODID);

    public static final RegistryObject<Feature<AnvilRockConfiguration>> ANVIL_ROCK_FEATURE = FEATURES.register("anvil_rock", () ->
            new AnvilRockFeature(AnvilRockConfiguration.CODEC));

    public static final RegistryObject<Feature<SimpleBlockConfiguration>> CRYSTAL_FEATURE = FEATURES.register("crystal", () ->
            new CrystalFeature(SimpleBlockConfiguration.CODEC));

    public static final RegistryObject<Feature<PointedDripstoneConfiguration>> TENDRIL_FEATURE = FEATURES.register("tendril", () ->
            new TendrilFeature(PointedDripstoneConfiguration.CODEC));

    public static final RegistryObject<Feature<PointedDripstoneConfiguration>> CRYING_DUCT_FEATURE = FEATURES.register("crying_duct", () ->
            new CryingDuctFeature(PointedDripstoneConfiguration.CODEC));
    public static void register(IEventBus eventBus) {
        FEATURES.register(eventBus);
    }
}
