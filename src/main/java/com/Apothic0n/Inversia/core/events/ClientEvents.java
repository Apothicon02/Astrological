package com.Apothic0n.Inversia.core.events;

import com.Apothic0n.Inversia.Inversia;
import com.Apothic0n.Inversia.api.InversiaMath;
import com.Apothic0n.Inversia.core.objects.InversiaBlocks;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.joml.Matrix4f;

@Mod.EventBusSubscriber(modid = Inversia.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ClientEvents {
    private static ResourceLocation END_SUN_LOCATION = new ResourceLocation("inversia", "textures/environment/end_sun.png");
    private static ResourceLocation SATURN_PHASES_LOCATION = new ResourceLocation("inversia", "textures/environment/saturn_phases.png");

    @SubscribeEvent
    public static void renderLevelStageEvent(RenderLevelStageEvent event) {
        Level level = Minecraft.getInstance().level;
        if (level != null && level.dimension().equals(Level.END) && event.getStage().equals(RenderLevelStageEvent.Stage.AFTER_SKY)) {
            RenderSystem.enableBlend();
            RenderSystem.depthMask(false);
            float time = level.getDayTime();
            if (time > 24000) {
                time = (float) (time - (Math.floor(time/24000)*24000));
            }
            float finalTime = time;
            Block sleep = InversiaBlocks.SLEEP.get();
            level.players().forEach(player -> {
                if ((finalTime >= 22700 && finalTime <= 23750) || (finalTime >= 12500 && finalTime <= 13000)) {
                    level.playSound(player, player.blockPosition(), SoundEvents.BEACON_AMBIENT, SoundSource.WEATHER, 0.3F, 1.69F);
                    if (finalTime == 22700 || finalTime == 12500) {
                        level.playSound(player, player.blockPosition(), SoundEvents.BEACON_ACTIVATE, SoundSource.AMBIENT, 2.0F, 1.69F);
                    } else if (finalTime == 23750 || finalTime == 13000) {
                        level.playSound(player, player.blockPosition(), SoundEvents.BEACON_DEACTIVATE, SoundSource.AMBIENT, 2.0F, 1.69F);
                    }
                }
                if ((player.getBlockStateOn().is(sleep) || level.getBlockState(player.blockPosition()).is(sleep) || level.getBlockState(player.blockPosition().above()).is(sleep)) && (Math.random()*(40)+1) < 2) {
                    level.playSound(player, player.blockPosition(), SoundEvents.WOOL_STEP, SoundSource.PLAYERS, 1F, 1.69F);
                }
            });
            Tesselator tesselator = Tesselator.getInstance();
            BufferBuilder bufferbuilder = tesselator.getBuilder();
            PoseStack poseStack = event.getPoseStack();
            Matrix4f matrix4f1 = poseStack.last().pose();
            float f12 = 30.0F;
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderTexture(0, END_SUN_LOCATION);
            bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
            bufferbuilder.vertex(matrix4f1, -f12, -110.0F, f12).uv(0.0F, 0.0F).endVertex();
            bufferbuilder.vertex(matrix4f1, f12, -110.0F, f12).uv(1.0F, 0.0F).endVertex();
            bufferbuilder.vertex(matrix4f1, f12, -110.0F, -f12).uv(1.0F, 1.0F).endVertex();
            bufferbuilder.vertex(matrix4f1, -f12, -110.0F, -f12).uv(0.0F, 1.0F).endVertex();
            BufferUploader.drawWithShader(bufferbuilder.end());
            if (time >= 22000 || time <= 500) {
                f12 = 20.0F;
                RenderSystem.setShader(GameRenderer::getPositionTexShader);
                RenderSystem.setShaderTexture(0, SATURN_PHASES_LOCATION);
                int k = level.getMoonPhase();
                int l = k % 4;
                int i1 = k / 4 % 2;
                float f13 = (float) (l) / 4.0F;
                float f14 = (float) (i1 ) / 2.0F;
                float f15 = (float) (l + 1) / 4.0F;
                float f16 = (float) (i1 + 1) / 2.0F;
                int min = 22000;
                int max = 24500;
                float extraTime = time;
                if (extraTime <= 500) {
                    extraTime = 24000 + time;
                }
                int mid = ((max - min) / 2) + min;
                float o4;
                float o8;
                float o12;
                float o16;
                if (extraTime < mid) {
                    max = mid;
                    o4 = InversiaMath.invLerp(extraTime, 4f, min, max);
                    o8 = InversiaMath.invLerp(extraTime, 8f, min, max);
                    o12 = InversiaMath.invLerp(extraTime, 12f, min, max);
                    o16 = InversiaMath.invLerp(extraTime, 16f, min, max);
                } else {
                    min = mid;
                    o4 = InversiaMath.invLerp(extraTime, 4f, max, min);
                    o8 = InversiaMath.invLerp(extraTime, 8f, max, min);
                    o12 = InversiaMath.invLerp(extraTime, 12f, max, min);
                    o16 = InversiaMath.invLerp(extraTime, 16f, max, min);
                }
                bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
                bufferbuilder.vertex(matrix4f1, -f12 + o12, -100.0F, f12 + o12).uv(f15, f16).endVertex();
                bufferbuilder.vertex(matrix4f1, f12 + o8, -100.0F, f12 + o8).uv(f13, f16).endVertex();
                bufferbuilder.vertex(matrix4f1, f12 + o4, -100.0F, -f12 + o12).uv(f13, f14).endVertex();
                bufferbuilder.vertex(matrix4f1, -f12 + o8, -100.0F, -f12 + o16).uv(f15, f14).endVertex();
                BufferUploader.drawWithShader(bufferbuilder.end());
            } else if (time >= 12000 && time <= 13500) {
                f12 = 20.0F;
                RenderSystem.setShader(GameRenderer::getPositionTexShader);
                RenderSystem.setShaderTexture(0, SATURN_PHASES_LOCATION);
                int k = level.getMoonPhase();
                int l = k % 4;
                int i1 = k / 4 % 2;
                float f13 = (float) (l ) / 4.0F;
                float f14 = (float) (i1 ) / 2.0F;
                float f15 = (float) (l + 1) / 4.0F;
                float f16 = (float) (i1 + 1) / 2.0F;
                int min = 12000;
                int max = 13500;
                float extraTime = time;
                int mid = ((max - min) / 2) + min;
                float o4;
                float o8;
                float o12;
                float o16;
                if (extraTime < mid) {
                    max = mid;
                    o4 = InversiaMath.invLerp(extraTime, 4f, min, max);
                    o8 = InversiaMath.invLerp(extraTime, 8f, min, max);
                    o12 = InversiaMath.invLerp(extraTime, 12f, min, max);
                    o16 = InversiaMath.invLerp(extraTime, 16f, min, max);
                } else {
                    min = mid;
                    o4 = InversiaMath.invLerp(extraTime, 4f, max, min);
                    o8 = InversiaMath.invLerp(extraTime, 8f, max, min);
                    o12 = InversiaMath.invLerp(extraTime, 12f, max, min);
                    o16 = InversiaMath.invLerp(extraTime, 16f, max, min);
                }
                bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
                bufferbuilder.vertex(matrix4f1, -f12 - o12, -100.0F, f12 - o12).uv(f15, f16).endVertex();
                bufferbuilder.vertex(matrix4f1, f12 - o8, -100.0F, f12 - o8).uv(f13, f16).endVertex();
                bufferbuilder.vertex(matrix4f1, f12 - o4, -100.0F, -f12 - o12).uv(f13, f14).endVertex();
                bufferbuilder.vertex(matrix4f1, -f12 - o8, -100.0F, -f12 - o16).uv(f15, f14).endVertex();
                BufferUploader.drawWithShader(bufferbuilder.end());
            } else {
                f12 = 20.0F;
                RenderSystem.setShader(GameRenderer::getPositionTexShader);
                RenderSystem.setShaderTexture(0, SATURN_PHASES_LOCATION);
                int k = level.getMoonPhase();
                int l = k % 4;
                int i1 = k / 4 % 2;
                float f13 = (float) (l + 0) / 4.0F;
                float f14 = (float) (i1 + 0) / 2.0F;
                float f15 = (float) (l + 1) / 4.0F;
                float f16 = (float) (i1 + 1) / 2.0F;
                bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
                bufferbuilder.vertex(matrix4f1, -f12, -100.0F, f12).uv(f15, f16).endVertex();
                bufferbuilder.vertex(matrix4f1, f12, -100.0F, f12).uv(f13, f16).endVertex();
                bufferbuilder.vertex(matrix4f1, f12, -100.0F, -f12).uv(f13, f14).endVertex();
                bufferbuilder.vertex(matrix4f1, -f12, -100.0F, -f12).uv(f15, f14).endVertex();
                BufferUploader.drawWithShader(bufferbuilder.end());
            }

            RenderSystem.depthMask(true);
            RenderSystem.disableBlend();
        }
    }
}
