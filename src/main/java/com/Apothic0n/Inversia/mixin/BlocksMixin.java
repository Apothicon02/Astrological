package com.Apothic0n.Inversia.mixin;

import com.Apothic0n.Inversia.core.sounds.InversiaSoundTypes;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(value = Blocks.class, priority = 69420)
public class BlocksMixin {

    /**
     * @author Apothicon
     * @reason Parity with the crying duct sound.
     */
    @ModifyExpressionValue(
            method = "<clinit>",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;of()Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;"),
            slice = @Slice(from = @At(value = "FIELD", target = "Lnet/minecraft/world/level/block/Blocks;MOSSY_COBBLESTONE:Lnet/minecraft/world/level/block/Block;", opcode = Opcodes.PUTSTATIC),
                    to = @At(value = "FIELD", target = "Lnet/minecraft/world/level/block/Blocks;OBSIDIAN:Lnet/minecraft/world/level/block/Block;", opcode = Opcodes.PUTSTATIC)),
            require = 1
    )
    private static BlockBehaviour.Properties setObsidianSound(BlockBehaviour.Properties properties) {
        return properties.sound(InversiaSoundTypes.GLASSY_OBSIDIAN);
    }

    @ModifyExpressionValue(
            method = "<clinit>",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;of()Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;"),
            slice = @Slice(from = @At(value = "FIELD", target = "Lnet/minecraft/world/level/block/Blocks;ANCIENT_DEBRIS:Lnet/minecraft/world/level/block/Block;", opcode = Opcodes.PUTSTATIC),
                    to = @At(value = "FIELD", target = "Lnet/minecraft/world/level/block/Blocks;CRYING_OBSIDIAN:Lnet/minecraft/world/level/block/Block;", opcode = Opcodes.PUTSTATIC)),
            require = 1
    )
    private static BlockBehaviour.Properties setCryingObsidianSound(BlockBehaviour.Properties properties) {
        return properties.sound(InversiaSoundTypes.GLASSY_OBSIDIAN);
    }
}
