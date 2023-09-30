package com.Apothic0n.Inversia.core.sounds;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.SoundType;

public class InversiaSoundTypes {
    public InversiaSoundTypes(float volume, float pitch, SoundEvent breakSound, SoundEvent stepSound, SoundEvent placeSound, SoundEvent hitSound, SoundEvent fallSound) {
        this.volume = volume;
        this.pitch = pitch;
        this.breakSound = breakSound;
        this.stepSound = stepSound;
        this.placeSound = placeSound;
        this.hitSound = hitSound;
        this.fallSound = fallSound;
    }

    public static final SoundType GLASSY_OBSIDIAN = new SoundType(1.0F, 0.33F, SoundEvents.GLASS_BREAK, SoundEvents.GLASS_STEP, SoundEvents.GLASS_PLACE, SoundEvents.GLASS_HIT, SoundEvents.GLASS_FALL);

    public static final SoundType SELENITE = new SoundType(0.66F, 1.33F, SoundEvents.GLASS_BREAK, SoundEvents.GLASS_STEP, SoundEvents.GLASS_PLACE, SoundEvents.GLASS_HIT, SoundEvents.GLASS_FALL);

    public final float volume;
    public final float pitch;
    private final SoundEvent breakSound;
    private final SoundEvent stepSound;
    private final SoundEvent placeSound;
    private final SoundEvent hitSound;
    private final SoundEvent fallSound;

    public float getVolume() {
        return this.volume;
    }

    public float getPitch() {
        return this.pitch;
    }

    public SoundEvent getBreakSound() {
        return this.breakSound;
    }

    public SoundEvent getStepSound() {
        return this.stepSound;
    }

    public SoundEvent getPlaceSound() {
        return this.placeSound;
    }

    public SoundEvent getHitSound() {
        return this.hitSound;
    }

    public SoundEvent getFallSound() {
        return this.fallSound;
    }
}