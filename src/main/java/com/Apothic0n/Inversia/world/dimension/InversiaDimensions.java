package com.Apothic0n.Inversia.world.dimension;

import com.Apothic0n.Inversia.Inversia;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

public class InversiaDimensions {
    public static ResourceKey<Level> InversiaDim = ResourceKey.create(Registry.DIMENSION_REGISTRY,
            new ResourceLocation(Inversia.MODID, "inversiadim"));
}
