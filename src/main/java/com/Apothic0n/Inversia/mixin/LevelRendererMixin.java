package com.Apothic0n.Inversia.mixin;

import com.Apothic0n.Inversia.api.InversiaMath;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Axis;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.resources.ResourceLocation;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import javax.annotation.Nullable;

@Mixin(value = LevelRenderer.class, priority = 69420)
public class LevelRendererMixin {

    @Shadow @Nullable private ClientLevel level;
    @Shadow @Final private static ResourceLocation END_SKY_LOCATION;
    private static ResourceLocation END_SUN_LOCATION = new ResourceLocation("inversia", "textures/environment/end_sun.png");

    private static ResourceLocation SATURN_PHASES_LOCATION = new ResourceLocation("inversia", "textures/environment/saturn_phases.png");

    /**
     * @author Apothicon
     * @reason Adds astral objects to the sky.
     */
    @Overwrite
    private void renderEndSky(PoseStack p_109781_) {
        RenderSystem.enableBlend();
        RenderSystem.depthMask(false);
        float time = this.level.getDayTime();
        RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
        RenderSystem.setShaderTexture(0, END_SKY_LOCATION);
        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder bufferbuilder = tesselator.getBuilder();
        for(int i = 0; i < 6; ++i) {
            p_109781_.pushPose();
            if (i == 1) {
                p_109781_.mulPose(Axis.XP.rotationDegrees(90.0F));
            }

            if (i == 2) {
                p_109781_.mulPose(Axis.XP.rotationDegrees(-90.0F));
            }

            if (i == 3) {
                p_109781_.mulPose(Axis.XP.rotationDegrees(180.0F));
            }

            if (i == 4) {
                p_109781_.mulPose(Axis.ZP.rotationDegrees(90.0F));
            }

            if (i == 5) {
                p_109781_.mulPose(Axis.ZP.rotationDegrees(-90.0F));
            }
            Matrix4f matrix4f = p_109781_.last().pose();
            bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);
            bufferbuilder.vertex(matrix4f, -100.0F, -100.0F, -100.0F).uv(0.0F, 0.0F).color(40, 40, 40, 255).endVertex();
            bufferbuilder.vertex(matrix4f, -100.0F, -100.0F, 100.0F).uv(0.0F, 16.0F).color(40, 40, 40, 255).endVertex();
            bufferbuilder.vertex(matrix4f, 100.0F, -100.0F, 100.0F).uv(16.0F, 16.0F).color(40, 40, 40, 255).endVertex();
            bufferbuilder.vertex(matrix4f, 100.0F, -100.0F, -100.0F).uv(16.0F, 0.0F).color(40, 40, 40, 255).endVertex();
            tesselator.end();
            p_109781_.popPose();
        }
        Matrix4f matrix4f1 = p_109781_.last().pose();
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
            int k = this.level.getMoonPhase();
            int l = k % 4;
            int i1 = k / 4 % 2;
            float f13 = (float)(l + 0) / 4.0F;
            float f14 = (float)(i1 + 0) / 2.0F;
            float f15 = (float)(l + 1) / 4.0F;
            float f16 = (float)(i1 + 1) / 2.0F;
            int min = 22000;
            int max = 24500;
            float extraTime = time;
            if (extraTime <= 500) {
                extraTime = 24000+time;
            }
            int mid = ((max-min)/2)+min;
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
            int k = this.level.getMoonPhase();
            int l = k % 4;
            int i1 = k / 4 % 2;
            float f13 = (float)(l + 0) / 4.0F;
            float f14 = (float)(i1 + 0) / 2.0F;
            float f15 = (float)(l + 1) / 4.0F;
            float f16 = (float)(i1 + 1) / 2.0F;
            int min = 12000;
            int max = 13500;
            float extraTime = time;
            int mid = ((max-min)/2)+min;
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
            int k = this.level.getMoonPhase();
            int l = k % 4;
            int i1 = k / 4 % 2;
            float f13 = (float)(l + 0) / 4.0F;
            float f14 = (float)(i1 + 0) / 2.0F;
            float f15 = (float)(l + 1) / 4.0F;
            float f16 = (float)(i1 + 1) / 2.0F;
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
