package com.Apothic0n.Astrological.core.events;

import com.Apothic0n.Astrological.Astrological;
import com.Apothic0n.Astrological.api.AstrologicalJsonReader;
import com.Apothic0n.Astrological.api.AstrologicalMath;
import com.Apothic0n.Astrological.core.objects.AstrologicalBlocks;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import org.joml.Matrix4f;

import static net.minecraft.client.renderer.blockentity.TheEndPortalRenderer.END_PORTAL_LOCATION;

@EventBusSubscriber(modid = Astrological.MODID, bus = EventBusSubscriber.Bus.GAME, value = Dist.CLIENT)
public class ClientEvents {
    private static ResourceLocation END_SUN_LOCATION = ResourceLocation.tryBuild("astrological", "textures/environment/end_sun.png");
    private static ResourceLocation SATURN_PHASES_LOCATION = ResourceLocation.tryBuild("astrological", "textures/environment/saturn_phases.png");

    private static int sleepSoundDelay = 19;
    private static int soundDelay = 40;

    @SubscribeEvent
    public static void renderLevelStageEvent(RenderLevelStageEvent event) {
        ClientLevel level = Minecraft.getInstance().level;
        if (level != null && level.dimension().equals(Level.END) && event.getStage().equals(RenderLevelStageEvent.Stage.AFTER_SKY)) {
            RenderSystem.enableBlend();
            RenderSystem.depthMask(false);
            PoseStack poseStack = event.getPoseStack();
            Matrix4f matrix4f1 = poseStack.last().pose();
            Tesselator tesselator = Tesselator.getInstance();
            if (AstrologicalJsonReader.customEndSky) {
                RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
                RenderSystem.setShaderTexture(0, END_PORTAL_LOCATION);
                for (int i = 0; i < 6; ++i) {
                    poseStack.pushPose();
                    if (i == 1) {
                        poseStack.mulPose(Axis.XP.rotationDegrees(90.0F));
                    }

                    if (i == 2) {
                        poseStack.mulPose(Axis.XP.rotationDegrees(-90.0F));
                    }

                    if (i == 3) {
                        poseStack.mulPose(Axis.XP.rotationDegrees(180.0F));
                    }

                    if (i == 4) {
                        poseStack.mulPose(Axis.ZP.rotationDegrees(90.0F));
                    }

                    if (i == 5) {
                        poseStack.mulPose(Axis.ZP.rotationDegrees(-90.0F));
                    }

                    Matrix4f matrix4f = poseStack.last().pose();
                    BufferBuilder bufferbuilder = tesselator.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);
                    bufferbuilder.addVertex(matrix4f, -110.0F, -110.0F, -110.0F).setUv(0.0F, 0.0F).setColor(255, 255, 255, 100);
                    bufferbuilder.addVertex(matrix4f, -110.0F, -110.0F, 110.0F).setUv(0.0F, 4).setColor(255, 255, 255, 100);
                    bufferbuilder.addVertex(matrix4f, 110.0F, -110.0F, 110.0F).setUv(4, 4).setColor(255, 255, 255, 100);
                    bufferbuilder.addVertex(matrix4f, 110.0F, -110.0F, -110.0F).setUv(4, 0.0F).setColor(255, 255, 255, 100);
                    BufferUploader.drawWithShader(bufferbuilder.buildOrThrow());
                    poseStack.popPose();
                }
            }
//
//            float time = level.getDayTime();
//            if (time > 24000) {
//                time = (float) (time - (Math.floor(time/24000)*24000));
//            }
//            float finalTime = time;
//            Block sleep = AstrologicalBlocks.SLEEP.get();
//            soundDelay -= 1;
//            level.players().forEach(player -> {
//                if (soundDelay <= 0 &&((finalTime >= 22700 && finalTime <= 23750) || (finalTime >= 12500 && finalTime <= 13000))) {
//                    soundDelay = 40;
//                    level.playSound(player, player.blockPosition(), SoundEvents.BEACON_AMBIENT, SoundSource.WEATHER, 0.3F, 1.69F);
//                    if (finalTime == 22700 || finalTime == 12500) {
//                        level.playSound(player, player.blockPosition(), SoundEvents.BEACON_ACTIVATE, SoundSource.AMBIENT, 2.0F, 1.69F);
//                    } else if (finalTime == 23750 || finalTime == 13000) {
//                        level.playSound(player, player.blockPosition(), SoundEvents.BEACON_DEACTIVATE, SoundSource.AMBIENT, 2.0F, 1.69F);
//                    }
//                }
//                double speed = Math.max(player.getDeltaMovement().x(), player.getDeltaMovement().z())+player.getDeltaMovement().y();
//                if ((level.getBlockState(player.blockPosition().below()).is(sleep) || level.getBlockState(player.blockPosition()).is(sleep) || level.getBlockState(player.blockPosition().above()).is(sleep)) && !Minecraft.getInstance().isPaused() && sleepSoundDelay < 0) {
//                    sleepSoundDelay = 20;
//                    level.playSound(player, player.blockPosition(), SoundEvents.WOOL_STEP, SoundSource.PLAYERS, Math.max((float) ((speed*4)+0.1), 1), Math.max((float) ((speed*8)+0.1), 1));
//                }
//                sleepSoundDelay = sleepSoundDelay - ((int) (speed*5) + 1);
//            });
//
//            float f12 = 30.0F;
//            RenderSystem.setShader(GameRenderer::getPositionTexShader);
//            RenderSystem.setShaderTexture(0, END_SUN_LOCATION);
//            BufferBuilder bufferbuilder = tesselator.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
//            bufferbuilder.addVertex(matrix4f1, -f12, 110.0F, -f12).setUv(0.0F, 0.0F);
//            bufferbuilder.addVertex(matrix4f1, f12, 110.0F, -f12).setUv(1.0F, 0.0F);
//            bufferbuilder.addVertex(matrix4f1, f12, 110.0F, f12).setUv(1.0F, 1.0F);
//            bufferbuilder.addVertex(matrix4f1, -f12, 110.0F, f12).setUv(0.0F, 1.0F);
//            BufferUploader.drawWithShader(bufferbuilder.buildOrThrow());
//            if (time >= 22000 || time <= 500) {
//                f12 = 20.0F;
//                RenderSystem.setShader(GameRenderer::getPositionTexShader);
//                RenderSystem.setShaderTexture(0, SATURN_PHASES_LOCATION);
//                int k = level.getMoonPhase();
//                int l = k % 4;
//                int i1 = k / 4 % 2;
//                float f13 = (float) (l) / 4.0F;
//                float f14 = (float) (i1 ) / 2.0F;
//                float f15 = (float) (l + 1) / 4.0F;
//                float f16 = (float) (i1 + 1) / 2.0F;
//                int min = 22000;
//                int max = 24500;
//                float extraTime = time;
//                if (extraTime <= 500) {
//                    extraTime = 24000 + time;
//                }
//                int mid = ((max - min) / 2) + min;
//                float o4;
//                float o8;
//                float o12;
//                float o16;
//                if (extraTime < mid) {
//                    max = mid;
//                    o4 = AstrologicalMath.invLerp(extraTime, 4f, min, max);
//                    o8 = AstrologicalMath.invLerp(extraTime, 8f, min, max);
//                    o12 = AstrologicalMath.invLerp(extraTime, 12f, min, max);
//                    o16 = AstrologicalMath.invLerp(extraTime, 16f, min, max);
//                } else {
//                    min = mid;
//                    o4 = AstrologicalMath.invLerp(extraTime, 4f, max, min);
//                    o8 = AstrologicalMath.invLerp(extraTime, 8f, max, min);
//                    o12 = AstrologicalMath.invLerp(extraTime, 12f, max, min);
//                    o16 = AstrologicalMath.invLerp(extraTime, 16f, max, min);
//                }
//                bufferbuilder = tesselator.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
//                bufferbuilder.addVertex(matrix4f1, -f12 + o8, 100.0F, -f12 + o16).setUv(f15, f16);
//                bufferbuilder.addVertex(matrix4f1, f12 + o4, 100.0F, -f12 + o12).setUv(f13, f16);
//                bufferbuilder.addVertex(matrix4f1, f12 + o8, 100.0F, f12 + o8).setUv(f13, f14);
//                bufferbuilder.addVertex(matrix4f1, -f12 + o12, 100.0F, f12 + o12).setUv(f15, f14);
//                BufferUploader.drawWithShader(bufferbuilder.buildOrThrow());
//            } else if (time >= 12000 && time <= 13500) {
//                f12 = 20.0F;
//                RenderSystem.setShader(GameRenderer::getPositionTexShader);
//                RenderSystem.setShaderTexture(0, SATURN_PHASES_LOCATION);
//                int k = level.getMoonPhase();
//                int l = k % 4;
//                int i1 = k / 4 % 2;
//                float f13 = (float) (l ) / 4.0F;
//                float f14 = (float) (i1 ) / 2.0F;
//                float f15 = (float) (l + 1) / 4.0F;
//                float f16 = (float) (i1 + 1) / 2.0F;
//                int min = 12000;
//                int max = 13500;
//                int mid = ((max - min) / 2) + min;
//                float o4;
//                float o8;
//                float o12;
//                float o16;
//                if (time < mid) {
//                    max = mid;
//                    o4 = AstrologicalMath.invLerp(time, 4f, min, max);
//                    o8 = AstrologicalMath.invLerp(time, 8f, min, max);
//                    o12 = AstrologicalMath.invLerp(time, 12f, min, max);
//                    o16 = AstrologicalMath.invLerp(time, 16f, min, max);
//                } else {
//                    min = mid;
//                    o4 = AstrologicalMath.invLerp(time, 4f, max, min);
//                    o8 = AstrologicalMath.invLerp(time, 8f, max, min);
//                    o12 = AstrologicalMath.invLerp(time, 12f, max, min);
//                    o16 = AstrologicalMath.invLerp(time, 16f, max, min);
//                }
//                bufferbuilder = tesselator.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
//                bufferbuilder.addVertex(matrix4f1, -f12 - o8, 100.0F, -f12 - o16).setUv(f15, f16);
//                bufferbuilder.addVertex(matrix4f1, f12 - o4, 100.0F, -f12 - o12).setUv(f13, f16);
//                bufferbuilder.addVertex(matrix4f1, f12 - o8, 100.0F, f12 - o8).setUv(f13, f14);
//                bufferbuilder.addVertex(matrix4f1, -f12 - o12, 100.0F, f12 - o12).setUv(f15, f14);
//                BufferUploader.drawWithShader(bufferbuilder.buildOrThrow());
//            } else {
//                f12 = 20.0F;
//                RenderSystem.setShader(GameRenderer::getPositionTexShader);
//                RenderSystem.setShaderTexture(0, SATURN_PHASES_LOCATION);
//                int k = level.getMoonPhase();
//                int l = k % 4;
//                int i1 = k / 4 % 2;
//                float f13 = (float) (l) / 4.0F;
//                float f14 = (float) (i1) / 2.0F;
//                float f15 = (float) (l + 1) / 4.0F;
//                float f16 = (float) (i1 + 1) / 2.0F;
//                bufferbuilder = tesselator.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
//                bufferbuilder.addVertex(matrix4f1, -f12, 100.0F, -f12).setUv(f15, f16);
//                bufferbuilder.addVertex(matrix4f1, f12, 100.0F, -f12).setUv(f13, f16);
//                bufferbuilder.addVertex(matrix4f1, f12, 100.0F, f12).setUv(f13, f14);
//                bufferbuilder.addVertex(matrix4f1, -f12, 100.0F, f12).setUv(f15, f14);
//                BufferUploader.drawWithShader(bufferbuilder.buildOrThrow());
//            }

            RenderSystem.depthMask(true);
            RenderSystem.disableBlend();
        }
    }
}
