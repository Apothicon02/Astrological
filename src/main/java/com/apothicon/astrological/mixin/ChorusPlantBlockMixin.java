package com.apothicon.astrological.mixin;

import com.apothicon.astrological.core.objects.AstrologicalBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.util.TriState;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ChorusPlantBlock;
import net.minecraft.world.level.block.PipeBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
@Mixin(value = ChorusPlantBlock.class, priority = 69420)
public abstract class ChorusPlantBlockMixin extends PipeBlock {

    public ChorusPlantBlockMixin(float p_55159_, Properties p_55160_) {
        super(p_55159_, p_55160_);
    }

    /**
     * @author Apothicon
     * @reason Allows chorus plants to connect to purpurite.
     */
    @Overwrite
    public static BlockState getStateWithConnections(BlockGetter level, BlockPos pos, BlockState defaultState) {
        BlockState blockstate = level.getBlockState(pos.below());
        BlockState blockstate1 = level.getBlockState(pos.above());
        BlockState blockstate2 = level.getBlockState(pos.north());
        BlockState blockstate3 = level.getBlockState(pos.east());
        BlockState blockstate4 = level.getBlockState(pos.south());
        BlockState blockstate5 = level.getBlockState(pos.west());
        Block block = defaultState.getBlock();
        TriState soilDecision = blockstate.canSustainPlant(level, pos.below(), Direction.UP, defaultState);
        return defaultState.trySetValue(DOWN, Boolean.valueOf(blockstate.is(block) || blockstate.is(Blocks.CHORUS_FLOWER) || blockstate.is(Blocks.END_STONE) || blockstate.is(AstrologicalBlocks.PURPURITE.get()) || soilDecision.isTrue()))
                .trySetValue(UP, Boolean.valueOf(blockstate1.is(block) || blockstate1.is(Blocks.CHORUS_FLOWER) || blockstate.is(AstrologicalBlocks.PURPURITE.get())))
                .trySetValue(NORTH, Boolean.valueOf(blockstate2.is(block) || blockstate2.is(Blocks.CHORUS_FLOWER) || blockstate.is(AstrologicalBlocks.PURPURITE.get())))
                .trySetValue(EAST, Boolean.valueOf(blockstate3.is(block) || blockstate3.is(Blocks.CHORUS_FLOWER) || blockstate.is(AstrologicalBlocks.PURPURITE.get())))
                .trySetValue(SOUTH, Boolean.valueOf(blockstate4.is(block) || blockstate4.is(Blocks.CHORUS_FLOWER) || blockstate.is(AstrologicalBlocks.PURPURITE.get())))
                .trySetValue(WEST, Boolean.valueOf(blockstate5.is(block) || blockstate5.is(Blocks.CHORUS_FLOWER) || blockstate.is(AstrologicalBlocks.PURPURITE.get())));
    }

    /**
     * @author Apothicon
     * @reason Allows chorus plants to survive on purpurite.
     */
    @Overwrite
    public boolean canSurvive(BlockState p_51724_, LevelReader p_51725_, BlockPos p_51726_) {
        BlockState blockstate = p_51725_.getBlockState(p_51726_.below());
        boolean flag = !p_51725_.getBlockState(p_51726_.above()).isAir() && !blockstate.isAir();

        for(Direction direction : Direction.Plane.HORIZONTAL) {
            BlockPos blockpos = p_51726_.relative(direction);
            BlockState blockstate1 = p_51725_.getBlockState(blockpos);
            if (blockstate1.is(this)) {
                if (flag) {
                    return false;
                }

                BlockState blockstate2 = p_51725_.getBlockState(blockpos.below());
                if (blockstate2.is(this) || blockstate2.is(Blocks.END_STONE) || blockstate2.is(AstrologicalBlocks.PURPURITE.get())) {
                    return true;
                }
            }
        }

        return blockstate.is(this) || blockstate.is(Blocks.END_STONE) || blockstate.is(AstrologicalBlocks.PURPURITE.get());
    }
}
