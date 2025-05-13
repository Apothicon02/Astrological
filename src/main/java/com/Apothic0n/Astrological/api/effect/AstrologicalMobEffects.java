package com.Apothic0n.Astrological.api.effect;

import com.Apothic0n.Astrological.Astrological;
import com.Apothic0n.Astrological.api.effect.types.AstrologicalMobEffect;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class AstrologicalMobEffects {
    private AstrologicalMobEffects() {}

    public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(Registries.MOB_EFFECT, Astrological.MODID);

    public static final DeferredHolder<MobEffect, MobEffect> ENDFECTED = MOB_EFFECTS.register("endfected",() -> {return new AstrologicalMobEffect(MobEffectCategory.HARMFUL, 330066);});
}
