package com.Apothic0n.Astrological.mixin;

import com.Apothic0n.Astrological.core.objects.AstrologicalBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ChorusPlantBlock;
import net.minecraft.world.level.block.PipeBlock;
import net.minecraft.world.level.block.state.BlockState;
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
    public static BlockState getStateWithConnections(BlockGetter level, BlockPos pos, BlockState state) {
        BlockState blockstate = level.getBlockState(pos.below());
        BlockState blockstate1 = level.getBlockState(pos.above());
        BlockState blockstate2 = level.getBlockState(pos.north());
        BlockState blockstate3 = level.getBlockState(pos.east());
        BlockState blockstate4 = level.getBlockState(pos.south());
        BlockState blockstate5 = level.getBlockState(pos.west());
        Block block = state.getBlock();
        net.neoforged.neoforge.common.util.TriState soilDecision = blockstate.canSustainPlant(level, pos.below(), Direction.UP, state);
        return state.trySetValue(DOWN, Boolean.valueOf(blockstate.is(block) || blockstate.is(Blocks.CHORUS_FLOWER) || blockstate.is(Blocks.END_STONE) || blockstate.is(AstrologicalBlocks.PURPURITE.get()) || soilDecision.isTrue()))
                .trySetValue(UP, Boolean.valueOf(blockstate1.is(block) || blockstate1.is(Blocks.CHORUS_FLOWER) || blockstate.is(AstrologicalBlocks.PURPURITE.get())))
                .trySetValue(NORTH, Boolean.valueOf(blockstate2.is(block) || blockstate2.is(Blocks.CHORUS_FLOWER) || blockstate.is(AstrologicalBlocks.PURPURITE.get())))
                .trySetValue(EAST, Boolean.valueOf(blockstate3.is(block) || blockstate3.is(Blocks.CHORUS_FLOWER) || blockstate.is(AstrologicalBlocks.PURPURITE.get())))
                .trySetValue(SOUTH, Boolean.valueOf(blockstate4.is(block) || blockstate4.is(Blocks.CHORUS_FLOWER) || blockstate.is(AstrologicalBlocks.PURPURITE.get())))
                .trySetValue(WEST, Boolean.valueOf(blockstate5.is(block) || blockstate5.is(Blocks.CHORUS_FLOWER) || blockstate.is(AstrologicalBlocks.PURPURITE.get())));
    }

    /**
     * @author Apothicon
     * @reason Allows chorus plants to connect to purpurite.
     */
    @Overwrite
    public BlockState updateShape(BlockState p_51728_, Direction p_51729_, BlockState p_51730_, LevelAccessor p_51731_, BlockPos p_51732_, BlockPos p_51733_) {
        if (!p_51728_.canSurvive(p_51731_, p_51732_)) {
            p_51731_.scheduleTick(p_51732_, this, 1);
            return super.updateShape(p_51728_, p_51729_, p_51730_, p_51731_, p_51732_, p_51733_);
        } else {
            boolean flag = p_51730_.is(this) || p_51730_.is(Blocks.CHORUS_FLOWER) || p_51729_ == Direction.DOWN && p_51730_.is(Blocks.END_STONE) || p_51729_ == Direction.DOWN && p_51730_.is(AstrologicalBlocks.PURPURITE.get());
            return p_51728_.setValue(PROPERTY_BY_DIRECTION.get(p_51729_), Boolean.valueOf(flag));
        }
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
