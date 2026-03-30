package com.apothicon.astrological.api.biome.features.configurations;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.IntProviders;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public record AnvilRockConfiguration(BlockStateProvider material, IntProvider radius, IntProvider height, IntProvider stretch) implements FeatureConfiguration {
    public static final Codec<AnvilRockConfiguration> CODEC = RecordCodecBuilder.create((fields) -> {
        return fields.group(BlockStateProvider.CODEC.fieldOf("material").forGetter(AnvilRockConfiguration::material),
                IntProviders.CODEC.fieldOf("radius").forGetter((v) -> {return v.radius;}),
                IntProviders.CODEC.fieldOf("height").forGetter((v) -> {return v.height;}),
                IntProviders.CODEC.fieldOf("stretch").forGetter((v) -> {return v.stretch;})
        ).apply(fields, AnvilRockConfiguration::new);
    });
}
