package com.Apothic0n.Astrological.core.events;

import com.Apothic0n.Astrological.Astrological;
import com.Apothic0n.Astrological.api.AstrologicalJsonReader;
import com.Apothic0n.Astrological.api.effect.AstrologicalMobEffects;
import com.Apothic0n.Astrological.core.objects.AstrologicalBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;
import java.util.Objects;

import static net.minecraft.world.level.block.Block.UPDATE_ALL;

@Mod.EventBusSubscriber(modid = Astrological.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CommonEvents {
    @SubscribeEvent
    public static void playerTick(TickEvent.PlayerTickEvent event) {
        Player player = event.player;
        Level level = player.level();
        if (!level.isClientSide && !player.isSpectator() && !player.touchingUnloadedChunk()) {
            boolean underVoid = false;
            BlockPos blockPos = player.blockPosition();
            BlockState standingOn = player.getBlockStateOn();
            
            //Sleep
            Block sleep = AstrologicalBlocks.SLEEP.get();
            if (standingOn.is(sleep) || level.getBlockState(blockPos).is(sleep)) {
                player.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 60, 0));
                player.addEffect(new MobEffectInstance(MobEffects.LEVITATION, 2, 0));
            }
            if (level.getBlockState(blockPos.atY((int) player.getEyePosition().y)).is(sleep)) {
                player.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 2, 1));
            }
            
            //Selenite
            if (standingOn.is(AstrologicalBlocks.OCHRE_SELENITE.get())) {
                player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 80, 31));
            } else if (standingOn.is(AstrologicalBlocks.VERDANT_SELENITE.get())) {
                player.addEffect(new MobEffectInstance(MobEffects.JUMP, 5, 9));
            } else if (standingOn.is(AstrologicalBlocks.PEARLESCENT_SELENITE.get())) {
                player.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 150, 0));
            } else if (standingOn.is(AstrologicalBlocks.PRISMATIC_SELENITE.get()) || standingOn.is(AstrologicalBlocks.SELENITE_WALL.get())) {
                float time = level.getDayTime();
                if (time > 24000) {
                    time = (float) (time - (Math.floor(time / 24000) * 24000));
                }
                if ((time >= 22000 || time <= 500) || (time >= 12000 && time <= 13500)) { //dawn & dusk
                    player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 80, 31));
                } else if (time <= 12000) { //day
                    player.addEffect(new MobEffectInstance(MobEffects.JUMP, 5, 9));
                } else { //night
                    player.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 150, 0));
                }
            }

            //Tumors & Tendrils            
            List<BlockPos> neighboring = List.of(
                    blockPos.below(), blockPos, blockPos.above(), 
                    blockPos.north(), blockPos.above().north(),
                    blockPos.east(), blockPos.above().east(),
                    blockPos.south(), blockPos.above().south(),
                    blockPos.west(), blockPos.above().west());
            for (int i = 0; i < neighboring.size(); i++) {
                BlockState blockState = level.getBlockState(neighboring.get(i));
                if ((blockState.is(AstrologicalBlocks.TENDRILS.get()) || blockState.is(AstrologicalBlocks.TUMOR.get())) && !player.hasEffect(MobEffects.CONFUSION)) {
                    player.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 120, 1));
                    player.addEffect(new MobEffectInstance(MobEffects.POISON, 120, 1));
                    player.addEffect(new MobEffectInstance(AstrologicalMobEffects.ENDFECTED.get(), 80, 1));
                    teleportPlayer(level, player);
                }
            }
            if (player.hasEffect(AstrologicalMobEffects.ENDFECTED.get())) {
                if (((int)(Math.random()*(240)+1) < 2)  || (player.getEffect(AstrologicalMobEffects.ENDFECTED.get()).endsWithin(3)) && ((int)(Math.random()*(20)+1) < 2)) {
                    teleportPlayer(level, player);
                } else {
                    spreadEndfection(level, player.getOnPos(), false);
                    spreadEndfection(level, player.getOnPos().north(), true);
                    spreadEndfection(level, player.getOnPos().east(), true);
                    spreadEndfection(level, player.getOnPos().south(), true);
                    spreadEndfection(level, player.getOnPos().west(), true);
                }
            }

            //Anti-eclipse
            if (level.dimension().equals(Level.END)) {
                float time = level.getDayTime();
                time = (float) (time - (Math.floor(time / 24000) * 24000));
                if ((time >= 22750 && time <= 23750) || (time >= 12500 && time <= 13000)) {
                    int playerY = player.blockPosition().getY();
                    BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos(player.blockPosition().getX(), playerY, player.blockPosition().getZ());
                    int maxHeight = level.getMaxBuildHeight();
                    boolean underVoidYet = true;
                    for (int y = playerY; y < maxHeight; y++) {
                        if (!level.getBlockState(mutableBlockPos.setY(y)).isAir()) {
                            underVoidYet = false;
                            break;
                        }
                    }
                    underVoid = underVoidYet;
                }
            }
            if (underVoid && AstrologicalJsonReader.weatherBasedLevitationEffects) {
                player.addEffect(new MobEffectInstance(MobEffects.LEVITATION, 20, 5));
                if (player.isPassenger()) {
                    Objects.requireNonNull(player.getVehicle()).addDeltaMovement(new Vec3(0, 0.2, 0));
                }
            }
        }
    }

    private static void teleportPlayer(Level level, Player player) {
        double d0 = player.getX();
        double d1 = player.getY();
        double d2 = player.getZ();

        for(int i = 0; i < 16; ++i) {
            double d3 = player.getX() + (player.getRandom().nextDouble() - 0.5D) * 16.0D;
            double d4 = Mth.clamp(player.getY() + (double)(player.getRandom().nextInt(16) - 8), (double)level.getMinBuildHeight(), (double)(level.getMinBuildHeight() + ((ServerLevel)level).getLogicalHeight() - 1));
            double d5 = player.getZ() + (player.getRandom().nextDouble() - 0.5D) * 16.0D;
            if (player.isPassenger()) {
                player.stopRiding();
            }

            Vec3 vec3 = player.position();
            level.gameEvent(GameEvent.TELEPORT, vec3, GameEvent.Context.of(player));
            if (player.randomTeleport(player.getX()+((Math.random()*32)-16), player.getY()+16, player.getZ()+((Math.random()*32)-16), true)) {
                SoundEvent soundevent = SoundEvents.CHORUS_FRUIT_TELEPORT;
                level.playSound(null, d0, d1, d2, soundevent, SoundSource.PLAYERS, 1.0F, 0.75F);
                player.playSound(soundevent, 1.0F, 0.75F);
                break;
            }
        }
    }

    private static void spreadEndfection(Level level, BlockPos blockPos, boolean hasTumor) {
        if ((int)(Math.random()*(4)+1) < 2) {
            replaceEndstone(level, blockPos, AstrologicalBlocks.TUMOR.get().defaultBlockState());
            if ((Math.random()*(10)+1) < 2) {
                SoundEvent soundevent = SoundEvents.SCULK_BLOCK_SPREAD;
                level.playSound((Player) null, blockPos.getX(), blockPos.getY(), blockPos.getZ(), soundevent, SoundSource.PLAYERS, 10.0F, 0.75F);
            }
            if (hasTumor && (int)(Math.random()*(5)+1) < 2) {
                placeOnCyst(level, blockPos.above(), AstrologicalBlocks.TENDRILS.get().defaultBlockState());
                if ((int)(Math.random()*(5)+1) < 2) {
                    placeOnCyst(level, blockPos.above(2), AstrologicalBlocks.TENDRILS.get().defaultBlockState());
                    if ((int)(Math.random()*(5)+1) < 2) {
                        placeOnCyst(level, blockPos.above(3), AstrologicalBlocks.TENDRILS.get().defaultBlockState());
                    }
                }
            }
        }
    }

    private static void replaceEndstone(Level level, BlockPos blockPos, BlockState blockState) {
        if (level.getBlockState(blockPos).is(Blocks.END_STONE)) {
            level.setBlock(blockPos, blockState, UPDATE_ALL);
        }
    }

    private static void placeOnCyst(Level level, BlockPos blockPos, BlockState blockState) {
        if ((level.getBlockState(blockPos.below()).is(AstrologicalBlocks.TUMOR.get()) || level.getBlockState(blockPos.below()).is(AstrologicalBlocks.TENDRILS.get())) && level.getBlockState(blockPos).isAir()) {
            level.setBlock(blockPos, blockState, UPDATE_ALL);
        }
    }
}