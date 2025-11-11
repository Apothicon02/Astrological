package com.Apothic0n.Astrological.mixin;

import com.Apothic0n.Astrological.api.AstrologicalJsonReader;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.TheEndGatewayBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.EndGatewayFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.EndGatewayConfiguration;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EndGatewayFeature.class)
public abstract class EndGatewayFeatureMixin extends Feature<EndGatewayConfiguration> {
    public EndGatewayFeatureMixin(Codec<EndGatewayConfiguration> config) {
        super(config);
    }

    @Inject(method = "place", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/levelgen/feature/EndGatewayFeature;setBlock(Lnet/minecraft/world/level/LevelWriter;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)V", ordinal = 0, shift = At.Shift.AFTER))
    private void generateSquarePlatformBelowGateway(FeaturePlaceContext<EndGatewayConfiguration> context, CallbackInfoReturnable<Boolean> cir, @Local(ordinal = 2) BlockPos pos, @Local WorldGenLevel level) {
        makeSquare(level, pos.below(3), Blocks.AIR.defaultBlockState());
        makeSquare(level, pos.below(4), Blocks.AIR.defaultBlockState());
        if (AstrologicalJsonReader.endChestGeneratesBeneathGateways) {
            this.setBlock(level, pos.below(4), Blocks.ENDER_CHEST.defaultBlockState());
        }
        makeSquare(level, pos.below(5), Blocks.OBSIDIAN.defaultBlockState());
    }

    @Inject(method = "lambda$place$0", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/entity/TheEndGatewayBlockEntity;setExitPosition(Lnet/minecraft/core/BlockPos;Z)V", shift = At.Shift.AFTER))
    private static void markBlockEntityAsChanged(WorldGenLevel worldgenlevel, BlockPos blockpos2, EndGatewayConfiguration endgatewayconfiguration, BlockPos p_352890_, CallbackInfo ci, @Local TheEndGatewayBlockEntity blockEntity) {
        blockEntity.setChanged();
    }

    @Unique
    private void makeSquare(WorldGenLevel worldgenlevel, BlockPos blockpos2, BlockState blockState) {
        this.setBlock(worldgenlevel, blockpos2, blockState);
        this.setBlock(worldgenlevel, blockpos2.north(), blockState);
        this.setBlock(worldgenlevel, blockpos2.east(), blockState);
        this.setBlock(worldgenlevel, blockpos2.south(), blockState);
        this.setBlock(worldgenlevel, blockpos2.west(), blockState);
        this.setBlock(worldgenlevel, blockpos2.north().east(), blockState);
        this.setBlock(worldgenlevel, blockpos2.south().east(), blockState);
        this.setBlock(worldgenlevel, blockpos2.south().west(), blockState);
        this.setBlock(worldgenlevel, blockpos2.north().west(), blockState);
    }
}
