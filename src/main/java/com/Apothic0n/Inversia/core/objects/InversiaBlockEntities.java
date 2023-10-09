package com.Apothic0n.Inversia.core.objects;

import com.Apothic0n.Inversia.Inversia;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class InversiaBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Inversia.MODID);

    public static final RegistryObject<BlockEntityType<?>> PRISMATIC_SELENITE = BLOCK_ENTITIES.register("prismatic_selenite", () ->
            BlockEntityType.Builder.of(PrismaticBlockEntity::new, InversiaBlocks.PRISMATIC_SELENITE.get()).build(null));
    public static final RegistryObject<BlockEntityType<?>> SELENITE_WALL = BLOCK_ENTITIES.register("selenite_wall", () ->
            BlockEntityType.Builder.of(PrismaticWallBlockEntity::new, InversiaBlocks.SELENITE_WALL.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
