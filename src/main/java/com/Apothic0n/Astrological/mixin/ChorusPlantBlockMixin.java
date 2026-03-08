package com.Apothic0n.Astrological.mixin;

import com.Apothic0n.Astrological.core.objects.AstrologicalBlocks;
import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ChorusPlantBlock;
import net.minecraft.world.level.block.PipeBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ChorusPlantBlock.class)
public abstract class ChorusPlantBlockMixin extends PipeBlock {

    public ChorusPlantBlockMixin(float p_55159_, Properties p_55160_) {
        super(p_55159_, p_55160_);
    }

    @Definition(id = "blockstate", local = @Local(type = BlockState.class, ordinal = 0))
    @Definition(id = "blockstate1", local = @Local(type = BlockState.class, ordinal = 1))
    @Definition(id = "blockstate2", local = @Local(type = BlockState.class, ordinal = 2))
    @Definition(id = "blockstate3", local = @Local(type = BlockState.class, ordinal = 3))
    @Definition(id = "blockstate4", local = @Local(type = BlockState.class, ordinal = 4))
    @Definition(id = "blockstate5", local = @Local(type = BlockState.class, ordinal = 5))
    @Definition(id = "is", method = "Lnet/minecraft/world/level/block/state/BlockState;is(Lnet/minecraft/world/level/block/Block;)Z")
    @Definition(id = "CHORUS_FLOWER", field = "Lnet/minecraft/world/level/block/Blocks;CHORUS_FLOWER:Lnet/minecraft/world/level/block/Block;")
    @Expression(value = "blockstate.is(CHORUS_FLOWER)", id = "state1")
    @Expression(value = "blockstate1.is(CHORUS_FLOWER)", id = "state2")
    @Expression(value = "blockstate2.is(CHORUS_FLOWER)", id = "state3")
    @Expression(value = "blockstate3.is(CHORUS_FLOWER)", id = "state4")
    @Expression(value = "blockstate4.is(CHORUS_FLOWER)", id = "state5")
    @Expression(value = "blockstate5.is(CHORUS_FLOWER)", id = "state6")
    @WrapOperation(method = "getStateForPlacement(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/block/state/BlockState;", at = {
            @At(value = "MIXINEXTRAS:EXPRESSION", id = "state1"),
            @At(value = "MIXINEXTRAS:EXPRESSION", id = "state2"),
            @At(value = "MIXINEXTRAS:EXPRESSION", id = "state3"),
            @At(value = "MIXINEXTRAS:EXPRESSION", id = "state4"),
            @At(value = "MIXINEXTRAS:EXPRESSION", id = "state5"),
            @At(value = "MIXINEXTRAS:EXPRESSION", id = "state6"),
    })
    private static boolean allowChorusPlantConnectToPurpurite(BlockState instance, Block block, Operation<Boolean> original) {
        return original.call(instance, block) || instance.is(AstrologicalBlocks.PURPURITE.get());
    }

    @Definition(id = "state", local = @Local(type = BlockState.class, ordinal = 1, argsOnly = true))
    @Definition(id = "is", method = "Lnet/minecraft/world/level/block/state/BlockState;is(Lnet/minecraft/world/level/block/Block;)Z")
    @Definition(id = "END_STONE", field = "Lnet/minecraft/world/level/block/Blocks;END_STONE:Lnet/minecraft/world/level/block/Block;")
    @Expression("state.is(END_STONE)")
    @WrapOperation(method = "updateShape", at = @At("MIXINEXTRAS:EXPRESSION"))
    public boolean allowChorusPlantUpdateShapeConnectToPurpurite(BlockState instance, Block block, Operation<Boolean> original) {
        return original.call(instance, block) || instance.is(AstrologicalBlocks.PURPURITE.get());
    }

    @Definition(id = "blockstate", local = @Local(type = BlockState.class, ordinal = 1))
    @Definition(id = "blockstate2", local = @Local(type = BlockState.class, ordinal = 3))
    @Definition(id = "is", method = "Lnet/minecraft/world/level/block/state/BlockState;is(Lnet/minecraft/world/level/block/Block;)Z")
    @Definition(id = "END_STONE", field = "Lnet/minecraft/world/level/block/Blocks;END_STONE:Lnet/minecraft/world/level/block/Block;")
    @Expression(value = "blockstate2.is(END_STONE)", id = "state1")
    @Expression(value = "blockstate.is(END_STONE)", id = "state2")
    @WrapOperation(method = "canSurvive", at = {
            @At(value = "MIXINEXTRAS:EXPRESSION", id = "state1"),
            @At(value = "MIXINEXTRAS:EXPRESSION", id = "state2")
    })
    public boolean allowChorusPlantSurviveOnPurpurite(BlockState instance, Block block, Operation<Boolean> original) {
        return original.call(instance, block) || instance.is(AstrologicalBlocks.PURPURITE.get());
    }
}
