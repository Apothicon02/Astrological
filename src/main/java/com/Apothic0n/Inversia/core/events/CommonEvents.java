package com.Apothic0n.Inversia.core.events;

import com.Apothic0n.Inversia.Inversia;
import com.Apothic0n.Inversia.api.effect.InversiaMobEffects;
import com.Apothic0n.Inversia.api.effect.types.InversiaMobEffect;
import com.Apothic0n.Inversia.core.objects.InversiaBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

import static net.minecraft.world.level.block.Block.UPDATE_ALL;

@Mod.EventBusSubscriber(modid = Inversia.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CommonEvents {
    @SubscribeEvent
    public static void playerTick(TickEvent.PlayerTickEvent event) {
        Player player = event.player;
        Level level = player.level();
        if (!level.isClientSide && !player.isSpectator()) {
            boolean overVoid = false;
            BlockPos blockPos = player.blockPosition();
            if (level.getBlockState(blockPos).is(InversiaBlocks.SLEEP.get())) {
                overVoid = true;
            }
            List<BlockPos> neighboring = List.of(
                    blockPos.below(), blockPos, blockPos.above(), 
                    blockPos.north(), blockPos.above().north(),
                    blockPos.east(), blockPos.above().east(),
                    blockPos.south(), blockPos.above().south(),
                    blockPos.west(), blockPos.above().west());
            for (int i = 0; i < neighboring.size(); i++) {
                BlockState blockState = level.getBlockState(neighboring.get(i));
                if ((blockState.is(InversiaBlocks.TUMOR.get()) || blockState.is(InversiaBlocks.CYST.get())) && !player.hasEffect(MobEffects.CONFUSION)) {
                    player.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 120, 1));
                    player.addEffect(new MobEffectInstance(MobEffects.POISON, 120, 1));
                    player.addEffect(new MobEffectInstance(InversiaMobEffects.ENDFECTED.get(), 80, 1));
                }
            }
            if (level.dimension().equals(Level.END)) {
                float time = level.getDayTime();
                time = (float) (time - (Math.floor(time / 24000) * 24000));
                if ((time >= 22750 && time <= 23750) || (time >= 12500 && time <= 13000)) {
                    if (level.getHeight(Heightmap.Types.WORLD_SURFACE, blockPos.getX(), blockPos.getZ()) <= level.getMinBuildHeight()) {
                        overVoid = true;
                    } else {
                        for (int y = player.blockPosition().getY(); y > level.getMinBuildHeight(); y--) {
                            if (!level.getBlockState(new BlockPos(player.blockPosition().getX(), y, player.blockPosition().getZ())).isAir()) {
                                overVoid = true;
                            }
                        }
                    }
                }
            }
            if (overVoid) {
                player.addEffect(new MobEffectInstance(MobEffects.LEVITATION, 20, 5));
            }
            if (player.hasEffect(InversiaMobEffects.ENDFECTED.get())) {
                if (((int)(Math.random()*(240)+1) < 2)  || (player.getEffect(InversiaMobEffects.ENDFECTED.get()).endsWithin(3)) && ((int)(Math.random()*(20)+1) < 2)) {
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
                        net.minecraftforge.event.entity.EntityTeleportEvent.ChorusFruit tpEvent = net.minecraftforge.event.ForgeEventFactory.onChorusFruitTeleport(player, d3, d4, d5);
                        if (player.randomTeleport(tpEvent.getTargetX(), tpEvent.getTargetY(), tpEvent.getTargetZ(), true)) {
                            SoundEvent soundevent = SoundEvents.CHORUS_FRUIT_TELEPORT;
                            level.playSound((Player)null, d0, d1, d2, soundevent, SoundSource.PLAYERS, 1.0F, 0.75F);
                            player.playSound(soundevent, 1.0F, 0.75F);
                            break;
                        }
                    }
                } else {
                    spreadEndfection(level, player.getOnPos(), false);
                    spreadEndfection(level, player.getOnPos().north(), true);
                    spreadEndfection(level, player.getOnPos().east(), true);
                    spreadEndfection(level, player.getOnPos().south(), true);
                    spreadEndfection(level, player.getOnPos().west(), true);
                }
            }
        }
    }

    private static void spreadEndfection(Level level, BlockPos blockPos, boolean hasTumor) {
        if ((int)(Math.random()*(4)+1) < 2) {
            replaceEndstone(level, blockPos, InversiaBlocks.CYST.get().defaultBlockState());
            if (hasTumor && (int)(Math.random()*(5)+1) < 2) {
                placeOnCyst(level, blockPos.above(), InversiaBlocks.TUMOR.get().defaultBlockState());
                if ((int)(Math.random()*(5)+1) < 2) {
                    placeOnCyst(level, blockPos.above(2), InversiaBlocks.TUMOR.get().defaultBlockState());
                    if ((int)(Math.random()*(5)+1) < 2) {
                        placeOnCyst(level, blockPos.above(3), InversiaBlocks.TUMOR.get().defaultBlockState());
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
        if ((level.getBlockState(blockPos.below()).is(InversiaBlocks.CYST.get()) || level.getBlockState(blockPos.below()).is(InversiaBlocks.TUMOR.get())) && level.getBlockState(blockPos).isAir()) {
            level.setBlock(blockPos, blockState, UPDATE_ALL);
        }
    }
}