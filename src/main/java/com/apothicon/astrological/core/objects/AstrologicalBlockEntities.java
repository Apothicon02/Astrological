package com.apothicon.astrological.core.objects;

import com.apothicon.astrological.Astrological;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class AstrologicalBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, Astrological.MODID);

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<?>> PRISMATIC_SELENITE = BLOCK_ENTITIES.register("prismatic_selenite", () ->
            new BlockEntityType(PrismaticBlockEntity::new, AstrologicalBlocks.PRISMATIC_SELENITE.get()));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<?>> SELENITE_WALL = BLOCK_ENTITIES.register("selenite_wall", () ->
            new BlockEntityType(PrismaticWallBlockEntity::new, AstrologicalBlocks.SELENITE_WALL.get()));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
