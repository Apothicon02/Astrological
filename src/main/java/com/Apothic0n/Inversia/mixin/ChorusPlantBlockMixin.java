package com.Apothic0n.Inversia.mixin;

import com.Apothic0n.Inversia.core.objects.InversiaBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
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
    public BlockState getStateForPlacement(BlockGetter p_51711_, BlockPos p_51712_) {
        BlockState blockstate = p_51711_.getBlockState(p_51712_.below());
        BlockState blockstate1 = p_51711_.getBlockState(p_51712_.above());
        BlockState blockstate2 = p_51711_.getBlockState(p_51712_.north());
        BlockState blockstate3 = p_51711_.getBlockState(p_51712_.east());
        BlockState blockstate4 = p_51711_.getBlockState(p_51712_.south());
        BlockState blockstate5 = p_51711_.getBlockState(p_51712_.west());
        return this.defaultBlockState().setValue(DOWN, Boolean.valueOf(blockstate.is(this) || blockstate.is(Blocks.CHORUS_FLOWER) || blockstate.is(Blocks.END_STONE) || blockstate.is(InversiaBlocks.PURPURITE.get()))).setValue(UP, Boolean.valueOf(blockstate1.is(this) || blockstate1.is(Blocks.CHORUS_FLOWER))).setValue(NORTH, Boolean.valueOf(blockstate2.is(this) || blockstate2.is(Blocks.CHORUS_FLOWER))).setValue(EAST, Boolean.valueOf(blockstate3.is(this) || blockstate3.is(Blocks.CHORUS_FLOWER))).setValue(SOUTH, Boolean.valueOf(blockstate4.is(this) || blockstate4.is(Blocks.CHORUS_FLOWER))).setValue(WEST, Boolean.valueOf(blockstate5.is(this) || blockstate5.is(Blocks.CHORUS_FLOWER)));
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
            boolean flag = p_51730_.is(this) || p_51730_.is(Blocks.CHORUS_FLOWER) || p_51729_ == Direction.DOWN && p_51730_.is(Blocks.END_STONE) || p_51729_ == Direction.DOWN && p_51730_.is(InversiaBlocks.PURPURITE.get());
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
                if (blockstate2.is(this) || blockstate2.is(Blocks.END_STONE) || blockstate2.is(InversiaBlocks.PURPURITE.get())) {
                    return true;
                }
            }
        }

        return blockstate.is(this) || blockstate.is(Blocks.END_STONE) || blockstate.is(InversiaBlocks.PURPURITE.get());
    }
}
