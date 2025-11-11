package com.Apothic0n.Astrological.mixin;

import com.Apothic0n.Astrological.core.objects.AstrologicalBlocks;
import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ChorusFlowerBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ChorusFlowerBlock.class)
public abstract class ChorusFlowerBlockMixin {
    @Definition(id = "blockstate", local = @Local(type = BlockState.class, ordinal = 1))
    @Definition(id = "is", method = "Lnet/minecraft/world/level/block/state/BlockState;is(Lnet/minecraft/world/level/block/Block;)Z")
    @Definition(id = "END_STONE", field = "Lnet/minecraft/world/level/block/Blocks;END_STONE:Lnet/minecraft/world/level/block/Block;")
    @Expression("blockstate.is(END_STONE)")
    @WrapOperation(method = "canSurvive", at = @At("MIXINEXTRAS:EXPRESSION"))
    public boolean allowChorusFlowerOnPurpurite(BlockState instance, Block block, Operation<Boolean> original) {
        return original.call(instance, block) || instance.is(AstrologicalBlocks.PURPURITE.get());
    }

    @Definition(id = "blockstate", local = @Local(type = BlockState.class, ordinal = 1))
    @Definition(id = "is", method = "Lnet/minecraft/world/level/block/state/BlockState;is(Lnet/minecraft/world/level/block/Block;)Z")
    @Definition(id = "END_STONE", field = "Lnet/minecraft/world/level/block/Blocks;END_STONE:Lnet/minecraft/world/level/block/Block;")
    @Definition(id = "blockstate1", local = @Local(type = BlockState.class, ordinal = 2))
    @Expression(value = "blockstate.is(END_STONE)", id = "state1")
    @Expression(value = "blockstate1.is(END_STONE)", id = "state2")
    @WrapOperation(method = "randomTick", at = {
        @At(value = "MIXINEXTRAS:EXPRESSION", id = "state1"),
        @At(value = "MIXINEXTRAS:EXPRESSION", id = "state2")
    })
    public boolean allowChorusFlowerConnectToPurpurite(BlockState instance, Block block, Operation<Boolean> original) {
        return original.call(instance, block) || instance.is(AstrologicalBlocks.PURPURITE.get());
    }
}
