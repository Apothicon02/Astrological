package com.Apothic0n.Astrological.core.objects;

import com.Apothic0n.Astrological.Astrological;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class AstrologicalBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, Astrological.MODID);

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<?>> PRISMATIC_SELENITE = BLOCK_ENTITIES.register("prismatic_selenite", () ->
            BlockEntityType.Builder.of(PrismaticBlockEntity::new, AstrologicalBlocks.PRISMATIC_SELENITE.get()).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<?>> SELENITE_WALL = BLOCK_ENTITIES.register("selenite_wall", () ->
            BlockEntityType.Builder.of(PrismaticWallBlockEntity::new, AstrologicalBlocks.SELENITE_WALL.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
