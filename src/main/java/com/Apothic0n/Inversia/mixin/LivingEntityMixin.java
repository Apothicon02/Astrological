package com.Apothic0n.Inversia.mixin;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.extensions.IForgeLivingEntity;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin implements IForgeLivingEntity {
    @Override
    public boolean moveInFluid(FluidState state, Vec3 movementVector, double gravity)
    {
        return state.move(self(), movementVector, gravity*-1);
    }
}
