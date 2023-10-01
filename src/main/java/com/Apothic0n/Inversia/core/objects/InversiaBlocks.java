package com.Apothic0n.Inversia.core.objects;

import com.Apothic0n.Inversia.Inversia;
import com.Apothic0n.Inversia.core.sounds.InversiaSoundTypes;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class InversiaBlocks {
    private InversiaBlocks() {}

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Inversia.MODID);

    public static final RegistryObject<Block> SLEEP = BLOCKS.register("sleep", () ->
            new MangroveRootsBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).instrument(NoteBlockInstrument.BASS).strength(0.7F).randomTicks().sound(SoundType.WOOL).noOcclusion().noCollission()));

    public static final RegistryObject<Block> TENDRILS = BLOCKS.register("tendrils", () ->
            new TendrilsBlock(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_BLUE).forceSolidOn().instrument(NoteBlockInstrument.BASEDRUM).noOcclusion().noCollission().sound(SoundType.MUD).randomTicks().strength(1.5F, 3.0F).dynamicShape().offsetType(BlockBehaviour.OffsetType.XZ).pushReaction(PushReaction.DESTROY)));

    public static final RegistryObject<Block> TUMOR = BLOCKS.register("tumor", () ->
            new Block(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_GREEN).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.MUD).strength(1.5F, 3.0F)));

    public static final RegistryObject<Block> CRYING_DUCT = BLOCKS.register("crying_duct", () ->
            new CryingDuctBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).forceSolidOn().instrument(NoteBlockInstrument.HAT).noOcclusion().sound(InversiaSoundTypes.GLASSY_OBSIDIAN).randomTicks().strength(1.5F, 3.0F).dynamicShape().offsetType(BlockBehaviour.OffsetType.XZ).pushReaction(PushReaction.DESTROY)));

    public static final RegistryObject<Block> INSOMNIA_VENT = BLOCKS.register("insomnia_vent", () ->
            new InsomniaVentBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_YELLOW).randomTicks().instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).strength(1.5F, 3.0F)));

    public static final RegistryObject<Block> CRYO_FIRE = BLOCKS.register("cryo_fire", () ->
            new CryoFireBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PURPLE).replaceable().noCollission().instabreak().lightLevel((p_152607_) -> {return 7;}).sound(SoundType.WOOL).pushReaction(PushReaction.DESTROY)));

    public static final RegistryObject<Block> OCHRE_SELENITE = BLOCKS.register("ochre_selenite", () ->
            new RotatedPillarBlock(BlockBehaviour.Properties.of().mapColor(MapColor.SAND).friction(0.2F).instrument(NoteBlockInstrument.HAT).lightLevel((p_152607_) -> {return 15;}).requiresCorrectToolForDrops().strength(1.25F, 4.2F).sound(InversiaSoundTypes.SELENITE)));

    public static final RegistryObject<Block> VERDANT_SELENITE = BLOCKS.register("verdant_selenite", () ->
            new RotatedPillarBlock(BlockBehaviour.Properties.of().mapColor(MapColor.GLOW_LICHEN).friction(1.15F).instrument(NoteBlockInstrument.HAT).lightLevel((p_152607_) -> {return 15;}).requiresCorrectToolForDrops().strength(1.25F, 4.2F).sound(InversiaSoundTypes.SELENITE)));

    public static final RegistryObject<Block> PEARLESCENT_SELENITE = BLOCKS.register("pearlescent_selenite", () ->
            new RotatedPillarBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).friction(1.1F).instrument(NoteBlockInstrument.HAT).lightLevel((p_152607_) -> {return 15;}).requiresCorrectToolForDrops().strength(1.25F, 4.2F).sound(InversiaSoundTypes.SELENITE)));

    public static final RegistryObject<Block> PURPURITE = BLOCKS.register("purpurite", () ->
            new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PURPLE).forceSolidOff().strength(0.4F).sound(SoundType.WOOD)));

    public static final RegistryObject<Block> TRIPHYLITE = BLOCKS.register("triphylite", () ->
            new TriphyliteBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PURPLE).strength(1.5F).sound(InversiaSoundTypes.TRIPHYLITE).requiresCorrectToolForDrops()));

    public static void fixBlockRenderLayers() {
        CryoFireBlock.bootStrap();
        ItemBlockRenderTypes.setRenderLayer(SLEEP.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(OCHRE_SELENITE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(VERDANT_SELENITE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(PEARLESCENT_SELENITE.get(), RenderType.translucent());

        ItemBlockRenderTypes.setRenderLayer(TENDRILS.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(CRYING_DUCT.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(CRYO_FIRE.get(), RenderType.cutout());
    }
}
