package com.Apothic0n.Astrological.api.effect;

import com.Apothic0n.Astrological.Astrological;
import com.Apothic0n.Astrological.api.effect.types.AstrologicalMobEffect;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class AstrologicalMobEffects {
    private AstrologicalMobEffects() {}

    public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, Astrological.MODID);

    public static final RegistryObject<MobEffect> ENDFECTED = MOB_EFFECTS.register("endfected",() -> {return new AstrologicalMobEffect(MobEffectCategory.HARMFUL, 330066);});
}
