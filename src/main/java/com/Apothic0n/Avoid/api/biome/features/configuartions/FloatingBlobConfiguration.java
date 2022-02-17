package com.Apothic0n.Avoid.api.biome.features.configuartions;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public class FloatingBlobConfiguration implements FeatureConfiguration {
    public static final Codec<FloatingBlobConfiguration> CODEC = RecordCodecBuilder.create((fields) -> {
        return fields.group(BlockState.CODEC.fieldOf("blobMaterial").forGetter((v) -> {
            return v.blobMaterial;
        }), IntProvider.codec(1, 512).fieldOf("blobMass").forGetter((v) -> {
            return v.blobMass;
        }), IntProvider.codec(1, 32).fieldOf("blobWidth").forGetter((v) -> {
            return v.blobWidth;
        }), IntProvider.codec(1, 128).fieldOf("blobHeight").forGetter((v) -> {
            return v.blobHeight;
        })).apply(fields, FloatingBlobConfiguration::new);
    });
    public final BlockState blobMaterial;
    private final IntProvider blobMass;
    private final IntProvider blobWidth;
    private final IntProvider blobHeight;

    public FloatingBlobConfiguration(BlockState blobMaterial, IntProvider blobMass, IntProvider blobWidth, IntProvider blobHeight) {
        this.blobMaterial = blobMaterial;
        this.blobMass = blobMass;
        this.blobWidth = blobWidth;
        this.blobHeight = blobHeight;
    }

    public IntProvider getBlobMass() {return this.blobMass;}
    public IntProvider getBlobWidth() {return this.blobWidth;}
    public IntProvider getBlobHeight() {return this.blobHeight;}

}
