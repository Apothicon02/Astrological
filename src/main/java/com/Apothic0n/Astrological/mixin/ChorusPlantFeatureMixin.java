package com.Apothic0n.Astrological.mixin;

import com.Apothic0n.Astrological.core.objects.AstrologicalBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.ChorusFlowerBlock;
import net.minecraft.world.level.levelgen.feature.ChorusPlantFeature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = ChorusPlantFeature.class, priority = 69420)
public class ChorusPlantFeatureMixin {

    /**
     * @author Apothicon
     * @reason Allows chorus plants to generate on purpurite.
     */
    @Inject(at = @At("RETURN"), method = "place", cancellable = true)
    private void placeOnPurpuriteToo(FeaturePlaceContext<NoneFeatureConfiguration> p_159521_, CallbackInfoReturnable<Boolean> cir) {
        WorldGenLevel worldgenlevel = p_159521_.level();
        BlockPos blockpos = p_159521_.origin();
        RandomSource randomsource = p_159521_.random();
        if (worldgenlevel.isEmptyBlock(blockpos) && worldgenlevel.getBlockState(blockpos.below()).is(AstrologicalBlocks.PURPURITE.get())) {
            ChorusFlowerBlock.generatePlant(worldgenlevel, blockpos, randomsource, 8);
            cir.setReturnValue(true);
        }
    }
}
