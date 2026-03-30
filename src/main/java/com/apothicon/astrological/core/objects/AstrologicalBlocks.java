package com.apothicon.astrological.core.objects;

import com.apothicon.astrological.Astrological;
import com.apothicon.astrological.core.sounds.AstrologicalSoundTypes;
import com.mojang.datafixers.util.Pair;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.List;
import java.util.function.ToIntFunction;

import static com.apothicon.astrological.core.objects.PrismaticBlockEntity.PRISMATIC_POWER;

public final class AstrologicalBlocks {
    private AstrologicalBlocks() {}

    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Astrological.MODID);

    public static final DeferredBlock<Block> SLEEP = BLOCKS.registerBlock("sleep", props ->
            new MangroveRootsBlock(props.mapColor(MapColor.COLOR_BLACK).instrument(NoteBlockInstrument.BASS).strength(0.4F).randomTicks().sound(SoundType.WOOL).noOcclusion().noCollision()));
    public static final DeferredBlock<Block> TENDRILS = BLOCKS.registerBlock("tendrils", props ->
            new TendrilsBlock(props.mapColor(MapColor.TERRACOTTA_BLUE).forceSolidOn().instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().noOcclusion().noCollision().sound(SoundType.MUD).randomTicks().strength(0.66F, 1.5F).dynamicShape().offsetType(BlockBehaviour.OffsetType.XZ).pushReaction(PushReaction.DESTROY)));
    public static final DeferredBlock<Block> TUMOR = BLOCKS.registerBlock("tumor", props ->
            new Block(props.mapColor(MapColor.TERRACOTTA_GREEN).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().sound(SoundType.MUD).strength(0.66F, 1.5F)));
    public static final DeferredBlock<Block> CRYING_DUCT = BLOCKS.registerBlock("crying_duct", props ->
            new CryingDuctBlock(props.mapColor(MapColor.COLOR_BLACK).forceSolidOn().instrument(NoteBlockInstrument.HAT).requiresCorrectToolForDrops().noOcclusion().sound(AstrologicalSoundTypes.GLASSY_OBSIDIAN).randomTicks().strength(1.5F, 3.0F).dynamicShape().offsetType(BlockBehaviour.OffsetType.XZ).pushReaction(PushReaction.DESTROY)));
    public static final DeferredBlock<Block> INSOMNIA_VENT = BLOCKS.registerBlock("insomnia_vent", props ->
            new InsomniaVentBlock(props.mapColor(MapColor.COLOR_YELLOW).randomTicks().instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().sound(SoundType.STONE).strength(1.5F, 3.0F)));
    public static final DeferredBlock<Block> CRYO_FIRE = BLOCKS.registerBlock("cryo_fire", props ->
            new CryoFireBlock(props.mapColor(MapColor.COLOR_PURPLE).replaceable().noCollision().instabreak().lightLevel((p_152607_) -> {return 7;}).requiresCorrectToolForDrops().sound(SoundType.WOOL).pushReaction(PushReaction.DESTROY)));
    public static final DeferredBlock<Block> OCHRE_SELENITE = BLOCKS.registerBlock("ochre_selenite", props ->
            new HalfTransparentRotatedPillarBlock(props.mapColor(MapColor.SAND).friction(0.989F).instrument(NoteBlockInstrument.HAT).lightLevel((p_152607_) -> {return 15;}).requiresCorrectToolForDrops().strength(1.25F, 4.2F).sound(AstrologicalSoundTypes.SELENITE).noOcclusion()));
    public static final DeferredBlock<Block> VERDANT_SELENITE = BLOCKS.registerBlock("verdant_selenite", props ->
            new HalfTransparentRotatedPillarBlock(props.mapColor(MapColor.GLOW_LICHEN).friction(0.989F).instrument(NoteBlockInstrument.HAT).lightLevel((p_152607_) -> {return 15;}).requiresCorrectToolForDrops().strength(1.25F, 4.2F).sound(AstrologicalSoundTypes.SELENITE).noOcclusion()));
    public static final DeferredBlock<Block> PEARLESCENT_SELENITE = BLOCKS.registerBlock("pearlescent_selenite", props ->
            new HalfTransparentRotatedPillarBlock(props.mapColor(MapColor.COLOR_PINK).friction(0.989F).instrument(NoteBlockInstrument.HAT).lightLevel((p_152607_) -> {return 15;}).requiresCorrectToolForDrops().strength(1.25F, 4.2F).sound(AstrologicalSoundTypes.SELENITE).noOcclusion()));
    public static final DeferredBlock<Block> PURPURITE = BLOCKS.registerBlock("purpurite", props ->
            new Block(props.mapColor(MapColor.COLOR_PURPLE).strength(0.4F).sound(SoundType.WOOD).requiresCorrectToolForDrops()));
    public static final DeferredBlock<Block> SELENITE_WALL = BLOCKS.registerBlock("selenite_wall", props ->
            new PrismaticWallBlock(props.mapColor(MapColor.SNOW).friction(0.989F).instrument(NoteBlockInstrument.HAT).lightLevel(prismaticEmission(15)).requiresCorrectToolForDrops().strength(1.25F, 4.2F).sound(AstrologicalSoundTypes.SELENITE)));
    public static final DeferredBlock<Block> PRISMATIC_SELENITE = BLOCKS.registerBlock("prismatic_selenite", props ->
            new PrismaticBlock(props.mapColor(MapColor.SNOW).friction(0.989F).instrument(NoteBlockInstrument.HAT).lightLevel(prismaticEmission(15)).requiresCorrectToolForDrops().strength(1.25F, 4.2F).sound(AstrologicalSoundTypes.SELENITE)));
    public static final DeferredBlock<Block> TRIPHYLITE = BLOCKS.registerBlock("triphylite", props ->
            new TriphyliteBlock(props.mapColor(MapColor.COLOR_ORANGE).strength(1.5F).sound(AstrologicalSoundTypes.TRIPHYLITE).requiresCorrectToolForDrops()));
    public static final DeferredBlock<Block> JADE = BLOCKS.registerBlock("jade", props ->
            new JadeBlock(props.mapColor(MapColor.COLOR_GREEN).strength(1.5F).sound(AstrologicalSoundTypes.JADE).lightLevel((p_152607_) -> 1).postProcess((state, getter, pos) -> pos).emissiveRendering((state, getter, pos) -> true).requiresCorrectToolForDrops()));
    public static final DeferredBlock<Block> REINFORCED_JADE = BLOCKS.registerBlock("reinforced_jade", props ->
            new RotatedPillarBlock(props.mapColor(MapColor.COLOR_GREEN).friction(1.1F).requiresCorrectToolForDrops().strength(1.25F, 4.2F).sound(AstrologicalSoundTypes.JADE)));
    public static final DeferredBlock<Block> JADE_BRICKS = BLOCKS.registerBlock("jade_bricks", props ->
            new JadeBlock(props.mapColor(MapColor.COLOR_GREEN).strength(1.5F).sound(AstrologicalSoundTypes.JADE).requiresCorrectToolForDrops()));
    public static final DeferredBlock<Block> POLISHED_JADE = BLOCKS.registerBlock("polished_jade", props ->
            new JadeBlock(props.mapColor(MapColor.COLOR_GREEN).strength(1.5F).sound(AstrologicalSoundTypes.JADE).requiresCorrectToolForDrops()));
    public static final DeferredBlock<Block> MARBLED_JADE_SLAB = BLOCKS.registerBlock("marbled_jade_slab", props ->
            new SlabBlock(props.mapColor(MapColor.COLOR_GREEN).strength(1.5F).sound(AstrologicalSoundTypes.JADE).requiresCorrectToolForDrops()));
    public static final DeferredBlock<Block> LIGHT_JADE = BLOCKS.registerBlock("light_jade", props ->
            new JadeBlock(props.mapColor(MapColor.COLOR_LIGHT_GREEN).strength(1.5F).sound(AstrologicalSoundTypes.JADE).lightLevel((p_152607_) -> 1).postProcess((state, getter, pos) -> pos).emissiveRendering((state, getter, pos) -> true).requiresCorrectToolForDrops()));
    public static final DeferredBlock<Block> LIGHT_JADE_TILES = BLOCKS.registerBlock("light_jade_tiles", props ->
            new JadeBlock(props.mapColor(MapColor.COLOR_GREEN).strength(1.5F).sound(AstrologicalSoundTypes.JADE).requiresCorrectToolForDrops()));
    public static final DeferredBlock<Block> CRACKED_LIGHT_JADE_TILES = BLOCKS.registerBlock("cracked_light_jade_tiles", props ->
            new JadeBlock(props.mapColor(MapColor.COLOR_GREEN).strength(1.5F).sound(AstrologicalSoundTypes.JADE).requiresCorrectToolForDrops()));
    public static final DeferredBlock<Block> PURPURITE_TILES = BLOCKS.registerBlock("purpurite_tiles", props ->
            new Block(props.mapColor(MapColor.COLOR_PURPLE).strength(0.4F).sound(SoundType.WOOD).requiresCorrectToolForDrops()));

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
    }

    public static List<Pair<String, DeferredBlock<Block>>> blocksWithStairsSlabsAndWalls = List.of(
            Pair.of("purpurite", PURPURITE), Pair.of("purpurite_tiles", PURPURITE_TILES),
            Pair.of("triphylite", TRIPHYLITE),
            Pair.of("jade", JADE), Pair.of("jade_bricks", JADE_BRICKS), Pair.of("polished_jade", POLISHED_JADE), Pair.of("reinforced_jade", REINFORCED_JADE),
                    Pair.of("light_jade", LIGHT_JADE), Pair.of("light_jade_tiles", LIGHT_JADE_TILES), Pair.of("cracked_light_jade_tiles", CRACKED_LIGHT_JADE_TILES)
    );

    public static final List<Pair<String, DeferredBlock<Block>>> wallBlocks = new ArrayList<>(List.of());
    public static final List<Pair<String, DeferredBlock<Block>>> stairBlocks = new ArrayList<>(List.of());
    public static final List<Pair<String, DeferredBlock<Block>>> slabBlocks = new ArrayList<>(List.of());

    public static void generateStairsSlabsWalls() {
        for (int i = 0; i < blocksWithStairsSlabsAndWalls.size(); i++) {
            Pair<String, DeferredBlock<Block>> baseBlock = blocksWithStairsSlabsAndWalls.get(i);
            wallBlocks.add(createWallBlocks(baseBlock));
            stairBlocks.add(createStairBlocks(baseBlock));
            slabBlocks.add(createSlabBlocks(baseBlock));
        }
    }

    public static Pair<String, DeferredBlock<Block>> createWallBlocks(Pair<String, DeferredBlock<Block>> baseBlock) {
        String name = baseBlock.getFirst() + "_wall";
        return Pair.of(name, BLOCKS.registerBlock(name, WallBlock::new, props -> BlockBehaviour.Properties.ofFullCopy(baseBlock.getSecond().get())));
    }

    public static Pair<String, DeferredBlock<Block>> createStairBlocks(Pair<String, DeferredBlock<Block>> baseBlock) {
        String name = baseBlock.getFirst() + "_stairs";
        return Pair.of(name, BLOCKS.registerBlock(name, props -> new StairBlock(baseBlock.getSecond().get().defaultBlockState(), props), props -> BlockBehaviour.Properties.ofFullCopy(baseBlock.getSecond().get())));
    }

    public static Pair<String, DeferredBlock<Block>> createSlabBlocks(Pair<String, DeferredBlock<Block>> baseBlock) {
        String name = baseBlock.getFirst() + "_slab";
        return Pair.of(name, BLOCKS.registerBlock(name, SlabBlock::new, props -> BlockBehaviour.Properties.ofFullCopy(baseBlock.getSecond().get())));
    }
}
