package com.Apothic0n.Avoid.core.objects;

import com.Apothic0n.Avoid.Avoid;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class AvoidBlocks {
    private AvoidBlocks() {}

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Avoid.MODID);

    public static final RegistryObject<Block> BLAZING_LICHEN = BLOCKS.register("blazing_lichen", () ->
            new GlowLichenBlock(BlockBehaviour.Properties.of(Material.REPLACEABLE_FIREPROOF_PLANT, MaterialColor.NETHER)
                    .noCollission().strength(0.2F).sound(SoundType.GLOW_LICHEN).lightLevel(GlowLichenBlock.emission(7))));

    public static final RegistryObject<Block> VOID_VINES = BLOCKS.register("void_vines", () ->
            new VoidVinesBlock(BlockBehaviour.Properties.of(Material.PLANT, MaterialColor.COLOR_PURPLE)
                    .randomTicks().noCollission().lightLevel(CaveVines.emission(14)).instabreak().sound(SoundType.CAVE_VINES)));

    public static final RegistryObject<Block> VOID_VINES_PLANT = BLOCKS.register("void_vines_plant", () ->
            new VoidVinesBlock(BlockBehaviour.Properties.of(Material.PLANT, MaterialColor.COLOR_PURPLE)
                    .noCollission().lightLevel(CaveVines.emission(14)).instabreak().sound(SoundType.CAVE_VINES)));

    public static void fixBlockRenderLayers() {
        ItemBlockRenderTypes.setRenderLayer(BLAZING_LICHEN.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(VOID_VINES.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(VOID_VINES_PLANT.get(), RenderType.cutout());
    }
}
