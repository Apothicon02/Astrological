package com.Apothic0n.Avoid.world.dimension;

import com.Apothic0n.Avoid.Avoid;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

public class AvoidDimensions {
    public static ResourceKey<Level> AvoidDim = ResourceKey.create(Registry.DIMENSION_REGISTRY,
            new ResourceLocation(Avoid.MODID, "avoiddim"));
}
