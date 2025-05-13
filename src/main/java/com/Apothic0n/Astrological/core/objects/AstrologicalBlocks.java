package com.Apothic0n.Astrological.core.objects;

import com.Apothic0n.Astrological.Astrological;
import com.Apothic0n.Astrological.core.sounds.AstrologicalSoundTypes;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.ToIntFunction;

import static com.Apothic0n.Astrological.core.objects.PrismaticBlockEntity.PRISMATIC_POWER;
import static net.minecraft.world.level.block.Blocks.RED_MUSHROOM_BLOCK;

public final class AstrologicalBlocks {
    private AstrologicalBlocks() {}

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(Registries.BLOCK, Astrological.MODID);

    public static final DeferredHolder<Block, Block> SLEEP = BLOCKS.register("sleep", () ->
            new MangroveRootsBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).instrument(NoteBlockInstrument.BASS).strength(0.4F).randomTicks().sound(SoundType.WOOL).noOcclusion().noCollission()));
    public static final DeferredHolder<Block, Block> TENDRILS = BLOCKS.register("tendrils", () ->
            new TendrilsBlock(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_BLUE).forceSolidOn().instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().noOcclusion().noCollission().sound(SoundType.MUD).randomTicks().strength(0.66F, 1.5F).dynamicShape().offsetType(BlockBehaviour.OffsetType.XZ).pushReaction(PushReaction.DESTROY)));
    public static final DeferredHolder<Block, Block> TUMOR = BLOCKS.register("tumor", () ->
            new Block(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_GREEN).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().sound(SoundType.MUD).strength(0.66F, 1.5F)));
    public static final DeferredHolder<Block, Block> CRYING_DUCT = BLOCKS.register("crying_duct", () ->
            new CryingDuctBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).forceSolidOn().instrument(NoteBlockInstrument.HAT).requiresCorrectToolForDrops().noOcclusion().sound(AstrologicalSoundTypes.GLASSY_OBSIDIAN).randomTicks().strength(1.5F, 3.0F).dynamicShape().offsetType(BlockBehaviour.OffsetType.XZ).pushReaction(PushReaction.DESTROY)));
    public static final DeferredHolder<Block, Block> INSOMNIA_VENT = BLOCKS.register("insomnia_vent", () ->
            new InsomniaVentBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_YELLOW).randomTicks().instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().sound(SoundType.STONE).strength(1.5F, 3.0F)));
    public static final DeferredHolder<Block, Block> CRYO_FIRE = BLOCKS.register("cryo_fire", () ->
            new CryoFireBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PURPLE).replaceable().noCollission().instabreak().lightLevel((p_152607_) -> {return 7;}).requiresCorrectToolForDrops().sound(SoundType.WOOL).pushReaction(PushReaction.DESTROY)));
    public static final DeferredHolder<Block, Block> OCHRE_SELENITE = BLOCKS.register("ochre_selenite", () ->
            new HalfTransparentRotatedPillarBlock(BlockBehaviour.Properties.of().mapColor(MapColor.SAND).friction(0.989F).instrument(NoteBlockInstrument.HAT).lightLevel((p_152607_) -> {return 15;}).requiresCorrectToolForDrops().strength(1.25F, 4.2F).sound(AstrologicalSoundTypes.SELENITE).noOcclusion()));
    public static final DeferredHolder<Block, Block> VERDANT_SELENITE = BLOCKS.register("verdant_selenite", () ->
            new HalfTransparentRotatedPillarBlock(BlockBehaviour.Properties.of().mapColor(MapColor.GLOW_LICHEN).friction(0.989F).instrument(NoteBlockInstrument.HAT).lightLevel((p_152607_) -> {return 15;}).requiresCorrectToolForDrops().strength(1.25F, 4.2F).sound(AstrologicalSoundTypes.SELENITE).noOcclusion()));
    public static final DeferredHolder<Block, Block> PEARLESCENT_SELENITE = BLOCKS.register("pearlescent_selenite", () ->
            new HalfTransparentRotatedPillarBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).friction(0.989F).instrument(NoteBlockInstrument.HAT).lightLevel((p_152607_) -> {return 15;}).requiresCorrectToolForDrops().strength(1.25F, 4.2F).sound(AstrologicalSoundTypes.SELENITE).noOcclusion()));
    public static final DeferredHolder<Block, Block> PURPURITE = BLOCKS.register("purpurite", () ->
            new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PURPLE).strength(0.4F).sound(SoundType.WOOD).requiresCorrectToolForDrops()));
    public static final DeferredHolder<Block, Block> SELENITE_WALL = BLOCKS.register("selenite_wall", () ->
            new PrismaticWallBlock(BlockBehaviour.Properties.of().mapColor(MapColor.SNOW).friction(0.989F).instrument(NoteBlockInstrument.HAT).lightLevel(prismaticEmission(15)).requiresCorrectToolForDrops().strength(1.25F, 4.2F).sound(AstrologicalSoundTypes.SELENITE)));
    public static final DeferredHolder<Block, Block> PRISMATIC_SELENITE = BLOCKS.register("prismatic_selenite", () ->
            new PrismaticBlock(BlockBehaviour.Properties.of().mapColor(MapColor.SNOW).friction(0.989F).instrument(NoteBlockInstrument.HAT).lightLevel(prismaticEmission(15)).requiresCorrectToolForDrops().strength(1.25F, 4.2F).sound(AstrologicalSoundTypes.SELENITE)));
    public static final DeferredHolder<Block, Block> TRIPHYLITE = BLOCKS.register("triphylite", () ->
            new TriphyliteBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_ORANGE).strength(1.5F).sound(AstrologicalSoundTypes.TRIPHYLITE).requiresCorrectToolForDrops()));
    public static final DeferredHolder<Block, Block> JADE = BLOCKS.register("jade", () ->
            new JadeBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GREEN).strength(1.5F).sound(AstrologicalSoundTypes.JADE).lightLevel((p_152607_) -> 1).hasPostProcess((state, getter, pos) -> true).emissiveRendering((state, getter, pos) -> true).requiresCorrectToolForDrops()));
    public static final DeferredHolder<Block, Block> REINFORCED_JADE = BLOCKS.register("reinforced_jade", () ->
            new RotatedPillarBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GREEN).friction(1.1F).requiresCorrectToolForDrops().strength(1.25F, 4.2F).sound(AstrologicalSoundTypes.JADE)));
    public static final DeferredHolder<Block, Block> JADE_BRICKS = BLOCKS.register("jade_bricks", () ->
            new JadeBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GREEN).strength(1.5F).sound(AstrologicalSoundTypes.JADE).requiresCorrectToolForDrops()));
    public static final DeferredHolder<Block, Block> POLISHED_JADE = BLOCKS.register("polished_jade", () ->
            new JadeBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GREEN).strength(1.5F).sound(AstrologicalSoundTypes.JADE).requiresCorrectToolForDrops()));
    public static final DeferredHolder<Block, Block> MARBLED_JADE_SLAB = BLOCKS.register("marbled_jade_slab", () ->
            new SlabBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GREEN).strength(1.5F).sound(AstrologicalSoundTypes.JADE).requiresCorrectToolForDrops()));
    public static final DeferredHolder<Block, Block> LIGHT_JADE = BLOCKS.register("light_jade", () ->
            new JadeBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_GREEN).strength(1.5F).sound(AstrologicalSoundTypes.JADE).lightLevel((p_152607_) -> 1).hasPostProcess((state, getter, pos) -> true).emissiveRendering((state, getter, pos) -> true).requiresCorrectToolForDrops()));
    public static final DeferredHolder<Block, Block> LIGHT_JADE_TILES = BLOCKS.register("light_jade_tiles", () ->
            new JadeBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GREEN).strength(1.5F).sound(AstrologicalSoundTypes.JADE).requiresCorrectToolForDrops()));
    public static final DeferredHolder<Block, Block> CRACKED_LIGHT_JADE_TILES = BLOCKS.register("cracked_light_jade_tiles", () ->
            new JadeBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GREEN).strength(1.5F).sound(AstrologicalSoundTypes.JADE).requiresCorrectToolForDrops()));
    public static final DeferredHolder<Block, Block> PURPURITE_TILES = BLOCKS.register("purpurite_tiles", () ->
            new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PURPLE).strength(0.4F).sound(SoundType.WOOD).requiresCorrectToolForDrops()));

    private static ToIntFunction<BlockState> prismaticEmission(int max) {
        return (blockState) -> {
            int power = blockState.getValue(PRISMATIC_POWER);
            if (power > 0) {
                power = power/2;
            }
            if (power > max) {
                power = max;
            }
            return power;
        };
    }

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

    public static List<Pair<String, DeferredHolder<Block, Block>>> blocksWithStairsSlabsAndWalls = List.of(
            Pair.of("purpurite", PURPURITE), Pair.of("purpurite_tiles", PURPURITE_TILES),
            Pair.of("triphylite", TRIPHYLITE),
            Pair.of("jade", JADE), Pair.of("jade_bricks", JADE_BRICKS), Pair.of("polished_jade", POLISHED_JADE), Pair.of("reinforced_jade", REINFORCED_JADE),
                    Pair.of("light_jade", LIGHT_JADE), Pair.of("light_jade_tiles", LIGHT_JADE_TILES), Pair.of("cracked_light_jade_tiles", CRACKED_LIGHT_JADE_TILES)
    );

    public static final List<Pair<String, DeferredHolder<Block, Block>>> wallBlocks = new ArrayList<>(List.of());
    public static final List<Pair<String, DeferredHolder<Block, Block>>> stairBlocks = new ArrayList<>(List.of());
    public static final List<Pair<String, DeferredHolder<Block, Block>>> slabBlocks = new ArrayList<>(List.of());

    public static void generateStairsSlabsWalls() {
        for (int i = 0; i < blocksWithStairsSlabsAndWalls.size(); i++) {
            Pair<String, DeferredHolder<Block, Block>> baseBlock = blocksWithStairsSlabsAndWalls.get(i);
            wallBlocks.add(createWallBlocks(baseBlock));
            stairBlocks.add(createStairBlocks(baseBlock));
            slabBlocks.add(createSlabBlocks(baseBlock));
        }
    }

    public static Pair<String, DeferredHolder<Block, Block>> createWallBlocks(Pair<String, DeferredHolder<Block, Block>> baseBlock) {
        String name = baseBlock.getFirst() + "_wall";
        return Pair.of(name, BLOCKS.register(name, () ->
                        new WallBlock(BlockBehaviour.Properties.ofFullCopy(baseBlock.getSecond().get()))));
    }

    public static Pair<String, DeferredHolder<Block, Block>> createStairBlocks(Pair<String, DeferredHolder<Block, Block>> baseBlock) {
        String name = baseBlock.getFirst() + "_stairs";
        return Pair.of(name, BLOCKS.register(name, () ->
                        new StairBlock(baseBlock.getSecond().get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(baseBlock.getSecond().get()))));
    }

    public static Pair<String, DeferredHolder<Block, Block>> createSlabBlocks(Pair<String, DeferredHolder<Block, Block>> baseBlock) {
        String name = baseBlock.getFirst() + "_slab";
        return Pair.of(name, BLOCKS.register(name, () ->
                        new SlabBlock(BlockBehaviour.Properties.ofFullCopy(baseBlock.getSecond().get()))));
    }
}
