package com.Apothic0n.Inversia.api;

import com.Apothic0n.Inversia.Inversia;
import com.google.common.base.Preconditions;
import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.renderer.texture.SpriteContents;
import net.minecraft.client.renderer.texture.atlas.*;
import net.minecraft.client.renderer.texture.atlas.sources.LazyLoadedImage;
import net.minecraft.client.resources.metadata.animation.AnimationMetadataSection;
import net.minecraft.client.resources.metadata.animation.FrameSize;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import org.slf4j.Logger;

import java.util.*;

/**
 * Example atlas config:
 * {
 *   "sources": [
 *     {
 *       "type": "yourmodid:mask",
 *       "source": "minecraft:block/lava_still",
 *       "mask": "minecraft:block/glass",
 *       "sprite": "minecraft:block/white_stained_glass"
 *     }
 *   ]
 * }
 */
public final class MaskingSource implements SpriteSource
{
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final ResourceLocation ID = new ResourceLocation(Inversia.MODID, "mask");
    private static SpriteSourceType TYPE = null;
    private static final Codec<MaskingSource> CODEC = RecordCodecBuilder.create(inst -> inst.group(
            ResourceLocation.CODEC.fieldOf("source").forGetter(s -> s.source),
            ResourceLocation.CODEC.fieldOf("mask").forGetter(s -> s.mask),
            ResourceLocation.CODEC.fieldOf("sprite").forGetter(s -> s.outLoc)
    ).apply(inst, MaskingSource::new));

    private final ResourceLocation source;
    private final ResourceLocation mask;
    private final ResourceLocation outLoc;

    public MaskingSource(ResourceLocation source, ResourceLocation mask, ResourceLocation outLoc)
    {
        this.source = source;
        this.mask = mask;
        this.outLoc = outLoc;
    }

    @Override
    public void run(ResourceManager mgr, Output out)
    {
        ResourceLocation sourcePath = TEXTURE_ID_CONVERTER.idToFile(source);
        Optional<Resource> optSource = mgr.getResource(sourcePath);
        if (optSource.isEmpty())
        {
            LOGGER.warn("Missing source texture: {}", sourcePath);
            return;
        }

        ResourceLocation maskPath = TEXTURE_ID_CONVERTER.idToFile(mask);
        Optional<Resource> optMask = mgr.getResource(maskPath);
        if (optMask.isEmpty())
        {
            LOGGER.warn("Missing mask texture: {}", maskPath);
            return;
        }

        Resource sourceRes = optSource.get();
        Resource maskRes = optMask.get();
        LazyLoadedImage sourceImg = new LazyLoadedImage(sourcePath, sourceRes, 1);
        LazyLoadedImage maskImg = new LazyLoadedImage(maskPath, maskRes, 1);
        out.add(outLoc, new MaskingSpriteSupplier(sourcePath, maskPath, sourceRes, maskRes, sourceImg, maskImg, outLoc));
    }

    @Override
    public SpriteSourceType type()
    {
        Preconditions.checkNotNull(TYPE, "SpriteSourceType not registered");
        return TYPE;
    }



    private record MaskingSpriteSupplier(
            ResourceLocation sourceLoc,
            ResourceLocation maskLoc,
            Resource sourceRes,
            Resource maskRes,
            LazyLoadedImage sourceImg,
            LazyLoadedImage maskImg,
            ResourceLocation outLoc
    ) implements SpriteSupplier
    {
        @Override
        public SpriteContents get()
        {
            try
            {
                // Make sure the mask texture has no animation
                AnimationMetadataSection maskAnim = maskRes.metadata()
                        .getSection(AnimationMetadataSection.SERIALIZER)
                        .orElse(AnimationMetadataSection.EMPTY);
                if (maskAnim != AnimationMetadataSection.EMPTY)
                {
                    throw new IllegalArgumentException("Mask texture must not be animated");
                }

                // Get texture contents
                NativeImage source = sourceImg.get();
                NativeImage mask = maskImg.get();

                // Get animation of the source texture, if available
                AnimationMetadataSection sourceAnim = sourceRes.metadata()
                        .getSection(AnimationMetadataSection.SERIALIZER)
                        .orElse(AnimationMetadataSection.EMPTY);
                // Calculate sprite size, taking a possible animation into account
                FrameSize srcSize = sourceAnim.calculateFrameSize(source.getWidth(), source.getHeight());
                int srcWidth = srcSize.width();
                int srcHeight = srcSize.height();
                int maskWidth = mask.getWidth();
                int maskHeight = mask.getHeight();
                // Make sure the two textures have the same aspect ratio
                if (!checkAspectRatio(srcWidth, srcHeight, maskWidth, maskHeight))
                {
                    throw new IllegalArgumentException("Aspect ratio of source texture and mask texture don't match. Texture sizes: (%d, %d) and (%d, %d)".formatted(
                            srcWidth, srcHeight, maskWidth, maskHeight
                    ));
                }

                // Calculate scale factors needed to scale textures to the same size
                int srcScale = maskWidth > srcWidth ? (maskWidth / srcWidth) : 1;
                int maskScale = srcWidth > maskWidth ? (srcWidth / maskWidth) : 1;
                // Make sure the textures can be cleanly scaled to match
                if ((srcScale > 1 && srcWidth * srcScale != maskWidth) || (maskScale > 1 && maskWidth * maskScale != srcWidth))
                {
                    throw new IllegalArgumentException("Source texture and mask texture cannot be scaled to match. Texture sizes: (%d, %d) and (%d, %d)".formatted(
                            srcWidth, srcHeight, maskWidth, maskHeight
                    ));
                }
                // Scale the textures
                source = scaleImage(source, srcScale);
                mask = scaleImage(mask, maskScale);

                // Create the output image
                FrameSize resultSize = new FrameSize(Math.max(srcWidth, maskWidth), Math.max(srcHeight, maskHeight));
                NativeImage imageOut = new NativeImage(NativeImage.Format.RGBA, source.getWidth(), source.getHeight(), false);
                // Collect the positions of the animation frames
                List<FrameInfo> frames = collectFrames(source, srcSize, sourceAnim);
                // Copy pixel data to the output image
                buildOutputImage(frames, source, mask, imageOut, resultSize);
                return new SpriteContents(outLoc, resultSize, imageOut, sourceAnim, null);
            }
            catch (Exception e)
            {
                LOGGER.error("Failed to apply mask '{}' to texture '{}'", maskLoc, sourceLoc, e);
            }
            finally
            {
                sourceImg.release();
                maskImg.release();
            }
            return null;
        }

        @Override
        public void discard()
        {
            sourceImg.release();
            maskImg.release();
        }

        private static boolean checkAspectRatio(int bgWidth, int bgHeight, int fgWidth, int fgHeight)
        {
            int widthFactor = Math.max(bgWidth, fgWidth) / Math.min(bgWidth, fgWidth);
            int heightFactor = Math.max(bgHeight, fgHeight) / Math.min(bgHeight, fgHeight);
            return widthFactor == heightFactor;
        }

        private static NativeImage scaleImage(NativeImage source, int scale)
        {
            if (scale > 1)
            {
                NativeImage scaled = new NativeImage(source.format(), source.getWidth() * scale, source.getHeight() * scale, false);
                scaled.resizeSubRectTo(0, 0, source.getWidth(), source.getHeight(), source);
                source.close();
                return scaled;
            }
            return source;
        }

        private static List<FrameInfo> collectFrames(NativeImage image, FrameSize size, AnimationMetadataSection animation)
        {
            List<FrameInfo> frames = new ArrayList<>();
            int rowCount = image.getWidth() / size.width();
            // Collect explicitly specified frames
            animation.forEachFrame((idx, time) ->
            {
                int frameX = (idx % rowCount) * size.width();
                int frameY = (idx / rowCount) * size.height();
                frames.add(new FrameInfo(idx, frameX, frameY));
            });
            // Collect implicit frames if no explicit ones are specified in the animation or no animation is present
            if (frames.isEmpty())
            {
                int frameCount = rowCount * (image.getHeight() / size.height());
                for (int idx = 0; idx < frameCount; idx++)
                {
                    int frameX = (idx % rowCount) * size.width();
                    int frameY = (idx / rowCount) * size.height();
                    frames.add(new FrameInfo(idx, frameX, frameY));
                }
            }
            return frames;
        }

        private static void buildOutputImage(
                List<FrameInfo> frames, NativeImage source, NativeImage mask, NativeImage imageOut, FrameSize resultSize
        )
        {
            // For each frame, copy the pixels of the frame in the source texture to the output texture if the
            // respective pixel in the mask texture is not fully transparent
            frames.forEach(frame ->
            {
                int fx = frame.x();
                int fy = frame.y();

                for (int y = 0; y < resultSize.height(); y++)
                {
                    for (int x = 0; x < resultSize.width(); x++)
                    {
                        int absX = fx + x;
                        int absY = fy + y;
                        int color = 0;
                        if (mask.getLuminanceOrAlpha(x, y) != 0)
                        {
                            color = source.getPixelRGBA(absX, absY);
                        }
                        imageOut.setPixelRGBA(absX, absY, color);
                    }
                }
            });
        }
    }

    private record FrameInfo(int idx, int x, int y) { }



    public static void register()
    {
        TYPE = SpriteSources.register(ID.toString(), CODEC);
    }
}