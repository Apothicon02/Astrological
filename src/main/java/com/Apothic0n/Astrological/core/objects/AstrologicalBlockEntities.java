package com.Apothic0n.Astrological.core.objects;

import com.Apothic0n.Astrological.Astrological;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class AstrologicalBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Astrological.MODID);

    public static final RegistryObject<BlockEntityType<?>> PRISMATIC_SELENITE = BLOCK_ENTITIES.register("prismatic_selenite", () ->
            BlockEntityType.Builder.of(PrismaticBlockEntity::new, AstrologicalBlocks.PRISMATIC_SELENITE.get()).build(null));
    public static final RegistryObject<BlockEntityType<?>> SELENITE_WALL = BLOCK_ENTITIES.register("selenite_wall", () ->
            BlockEntityType.Builder.of(PrismaticWallBlockEntity::new, AstrologicalBlocks.SELENITE_WALL.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
