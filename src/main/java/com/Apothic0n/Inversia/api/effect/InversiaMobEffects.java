package com.Apothic0n.Inversia.api.effect;

import com.Apothic0n.Inversia.Inversia;
import com.Apothic0n.Inversia.api.effect.types.InversiaMobEffect;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class InversiaMobEffects {
    private InversiaMobEffects() {}

    public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, Inversia.MODID);

    public static final RegistryObject<MobEffect> ENDFECTED = MOB_EFFECTS.register("endfected",() -> {return new InversiaMobEffect(MobEffectCategory.HARMFUL, 330066);});
}
