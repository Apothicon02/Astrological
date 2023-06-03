package com.Apothic0n.Inversia.core.events;

import com.Apothic0n.Inversia.Inversia;
import com.Apothic0n.Inversia.config.CommonConfig;
import com.Apothic0n.Inversia.core.objects.InversiaBlocks;
import com.Apothic0n.Inversia.world.dimension.InversiaDimensions;
import com.Apothic0n.Inversia.world.dimension.InversiaITeleporter;
import com.Apothic0n.Inversia.world.dimension.InversiaTeleporter;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityTeleportEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;


@Mod.EventBusSubscriber(modid = Inversia.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CommonModEvents {
    static final ResourceKey depthsKey = ResourceKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation("ecod", "the_depths"));
    @SubscribeEvent
    public static void chorusTeleport(EntityTeleportEvent.ChorusFruit event) {
        LivingEntity Player = event.getEntityLiving();
        if (!Player.level.isClientSide()) {
            MinecraftServer server = Player.getServer();
            if (server != null) {
                ServerLevel overWorld = server.getLevel(Level.OVERWORLD);
                ServerLevel inversiaDim = server.getLevel(InversiaDimensions.InversiaDim);
                ServerLevel theDepths = server.getLevel(depthsKey);
                BlockState standingOn = Player.level.getBlockState(Player.blockPosition().below());
                BlockState standingUnder = Player.level.getBlockState(Player.blockPosition().above(2));
                if (standingUnder != null || standingOn != null) {
                    if (ModList.get().isLoaded("ecod") && theDepths != null) {
                        BlockState bedrock = Blocks.BEDROCK.defaultBlockState();
                        if (Player.level.dimension() == InversiaDimensions.InversiaDim && standingUnder == bedrock) {
                            Player.dismountTo(Player.getX(), Player.getY(), Player.getZ());
                            Player.changeDimension(theDepths, new InversiaTeleporter(Player.blockPosition(), true));
                        } else if(Player.level.dimension().location().getNamespace() == "ecod" && standingOn == bedrock) {
                            Player.dismountTo(Player.getX(), Player.getY(), Player.getZ());
                            Player.changeDimension(inversiaDim, new InversiaTeleporter(Player.blockPosition(), true));
                            Player.addEffect(new MobEffectInstance(MobEffects.LEVITATION, 1800), Player);
                        }
                    } else {
                        BlockState bedrock = Blocks.BEDROCK.defaultBlockState();
                        if (Player.level.dimension() == Level.OVERWORLD && standingOn == bedrock) {
                            Player.dismountTo(Player.getX(), Player.getY(), Player.getZ());
                            Player.changeDimension(inversiaDim, new InversiaTeleporter(Player.blockPosition(), false));
                            Player.addEffect(new MobEffectInstance(MobEffects.LEVITATION, 1800), Player);
                        } else if (Player.level.dimension() == InversiaDimensions.InversiaDim && standingUnder == bedrock) {
                            Player.dismountTo(Player.getX(), Player.getY(), Player.getZ());
                            Player.changeDimension(overWorld, new InversiaTeleporter(Player.blockPosition(), true));
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void playerTick(TickEvent.PlayerTickEvent event) {
        Player player = event.player;
        MinecraftServer minecraftServer = player.getServer();
        Level level = player.level;
        if (level.isClientSide) { //Do something to the player from the client every tick
            if (CommonConfig.teleporting.get()) { //Do something only if the teleportation config is set to true.
                if (!player.getCooldowns().isOnCooldown(Items.AIR)) { //Use a shared 50s delay. DO NOT USE WITH TWO THINGS THAT CAN HAPPEN AT THE SAME TIME
                    if (level.getBlockState(player.blockPosition().below()) == Blocks.BEDROCK.defaultBlockState() && level.dimension().equals(Level.OVERWORLD)) {
                        player.displayClientMessage(new TranslatableComponent("block.minecraft.bedrock.under"), true);
                        player.playSound(SoundEvents.PORTAL_AMBIENT, 0.3f, 0.8f);
                        player.getCooldowns().addCooldown(Items.AIR, 1000);
                    } else if (level.getBlockState(player.blockPosition().above(2)) == Blocks.BEDROCK.defaultBlockState() && level.dimension().equals(InversiaDimensions.InversiaDim)) {
                        player.displayClientMessage(new TranslatableComponent("block.minecraft.bedrock.above"), true);
                        player.playSound(SoundEvents.PORTAL_AMBIENT, 0.3f, 0.8f);
                        player.getCooldowns().addCooldown(Items.AIR, 1000);
                    }
                }
            }
        } else if (minecraftServer != null) { //Do something to the player from the server every tick
            ServerPlayer serverPlayer = ((ServerPlayer) event.player);
            if (CommonConfig.teleporting.get()) { //Do something only if the teleportation config is set to true.
                if (InversiaDimensions.InversiaDim.equals(level.dimension())) { //Do something to the player from the server as long as they are in the Inversia dimension
                    if (player.blockPosition().getY() <= -64) {
                        ArrayList teleporterData = InversiaITeleporter.fallToNetherRoof(serverPlayer);
                        ServerPlayer newServerPlayer = (ServerPlayer) teleporterData.get(0);
                        BlockPos newPlayerPosition = (BlockPos) teleporterData.get(1);
                        player.dismountTo(player.getX(), player.getY(), player.getZ());
                        newServerPlayer.teleportTo(minecraftServer.getLevel(Level.NETHER), newPlayerPosition.getX(), newPlayerPosition.getY(), newPlayerPosition.getZ(), 0, 0);
                        player.getCooldowns().addCooldown(Items.INK_SAC, 200);
                    }
                } else if (Level.NETHER.equals(level.dimension())) { //Do something to the player from the server as long as they are in the nether dimension
                    if (player.blockPosition().getY() >= 320) {
                        ArrayList teleporterData = InversiaITeleporter.acsendFromNetherRoof(serverPlayer);
                        ServerPlayer newServerPlayer = (ServerPlayer) teleporterData.get(0);
                        BlockPos newPlayerPosition = (BlockPos) teleporterData.get(1);
                        player.dismountTo(player.getX(), player.getY(), player.getZ());
                        newServerPlayer.teleportTo(minecraftServer.getLevel(InversiaDimensions.InversiaDim), newPlayerPosition.getX(), newPlayerPosition.getY(), newPlayerPosition.getZ(), 0, 0);
                        player.addEffect(new MobEffectInstance(MobEffects.LEVITATION, 600), player);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void playerFall(LivingFallEvent event) {
        LivingEntity livingEntity = event.getEntityLiving();
        if (livingEntity instanceof ServerPlayer serverPlayer) {
            if (serverPlayer.getCooldowns().isOnCooldown(Items.INK_SAC)) {
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public static void itemUsed(PlayerInteractEvent.RightClickBlock event) {
        Level pLevel = event.getWorld();
        BlockPos pPos =  event.getHitVec().getBlockPos();
        BlockState pBlock = pLevel.getBlockState(pPos);
        ItemStack pStack = event.getItemStack();
        Player player = event.getPlayer();
        if (!pLevel.isClientSide) { //Runs stuff on the server every time a player right clicks a block
            if (pStack.getItem() == Items.GLOW_INK_SAC && pBlock.getBlock() == Blocks.AMETHYST_CLUSTER) {
                pLevel.setBlock(pPos, InversiaBlocks.GLOWING_AMETHYST.get().withPropertiesOf(pBlock), 2);
                float f = Mth.randomBetween(pLevel.random, 0.8F, 1.2F);
                pLevel.playSound((Player)null, pPos, SoundEvents.DOLPHIN_EAT, SoundSource.BLOCKS, 1.0F, f);
                player.swing(event.getHand(), true);
                if (!player.isCreative()) {
                    pStack.setCount(pStack.getCount() - 1);
                }
            }
        }
    }
}
