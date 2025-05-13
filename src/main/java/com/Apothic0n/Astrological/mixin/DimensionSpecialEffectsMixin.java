package com.Apothic0n.Astrological.mixin;

import com.Apothic0n.Astrological.api.AstrologicalJsonReader;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;
import net.neoforged.neoforge.client.extensions.IDimensionSpecialEffectsExtension;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.awt.*;

@Mixin(value = DimensionSpecialEffects.class, priority = 69420)
public class DimensionSpecialEffectsMixin implements IDimensionSpecialEffectsExtension {

    /**
     * @author Apothicon
     * @reason Removes hardcoded ambient lighting from the end.
     */
    @Overwrite
    public boolean constantAmbientLight() {
        return false;
    }

    @Override
    public void adjustLightmapColors(ClientLevel level, float partialTicks, float skyDarken, float blockLightRedFlicker, float skyLight, int pixelX, int pixelY, Vector3f colors) {
        if (AstrologicalJsonReader.customEndLighting) {
            //float light = Math.min(10, pixelX);
            //IForgeDimensionSpecialEffects.super.adjustLightmapColors(level, partialTicks, skyDarken, blockLightRedFlicker, skyLight, pixelX, pixelY, colors.add(new Vector3f(0.07F*light, -0.07F*light, 0.01F*light)).min(new Vector3f(1, 1, 1)).max(new Vector3f(0.01f, 0.01f, 0.02f)));
            if (level.dimension().equals(Level.END)) {
                skyLight = Math.max(1F, skyLight);
                float blockFactor = 0;
                if (pixelX <= 4) {
                    blockFactor = 0.25f - (pixelX / 16f);
                }
                IDimensionSpecialEffectsExtension.super.adjustLightmapColors(level, partialTicks, skyDarken, blockLightRedFlicker, skyLight, pixelX, pixelY, colors.add(new Vector3f((-0.016f + (blockFactor / 1.9F)) - (blockFactor / 3), (-0.166f + (blockFactor / 1.2f)) - (blockFactor / 1.7f), -(blockFactor / 3))));
            }
        }
    }
}
