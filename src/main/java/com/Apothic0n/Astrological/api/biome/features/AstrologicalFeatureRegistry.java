package com.Apothic0n.Astrological.api.biome.features;

import com.Apothic0n.Astrological.Astrological;
import com.Apothic0n.Astrological.api.biome.features.configurations.AnvilRockConfiguration;
import com.Apothic0n.Astrological.api.biome.features.types.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.GeodeConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.PointedDripstoneConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public abstract class AstrologicalFeatureRegistry {
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(Registries.FEATURE, Astrological.MODID);

    public static final DeferredHolder<Feature<?>, ?> ASTEROID_FEATURE = FEATURES.register("asteroid", () ->
            new AsteroidFeature(GeodeConfiguration.CODEC));
    public static final DeferredHolder<Feature<?>, ?> ANVIL_ROCK_FEATURE = FEATURES.register("anvil_rock", () ->
            new AnvilRockFeature(AnvilRockConfiguration.CODEC));

    public static final DeferredHolder<Feature<?>, ?> CRYSTAL_FEATURE = FEATURES.register("crystal", () ->
            new CrystalFeature(SimpleBlockConfiguration.CODEC));

    public static final DeferredHolder<Feature<?>, ?> JADE_CRYSTAL_FEATURE = FEATURES.register("jade_crystal", () ->
            new JadeCrystalFeature(SimpleBlockConfiguration.CODEC));

    public static final DeferredHolder<Feature<?>, ?> TENDRIL_FEATURE = FEATURES.register("tendril", () ->
            new TendrilFeature(PointedDripstoneConfiguration.CODEC));

    public static final DeferredHolder<Feature<?>, ?> CRYING_DUCT_FEATURE = FEATURES.register("crying_duct", () ->
            new CryingDuctFeature(PointedDripstoneConfiguration.CODEC));

    public static final DeferredHolder<Feature<?>, ?> BASIC_3X2X3_CUBE_FEATURE = FEATURES.register("basic_3x2x3_cube", () ->
            new Basic3x2x3CubeFeature(SimpleBlockConfiguration.CODEC));
    public static void register(IEventBus eventBus) {
        FEATURES.register(eventBus);
    }
}
