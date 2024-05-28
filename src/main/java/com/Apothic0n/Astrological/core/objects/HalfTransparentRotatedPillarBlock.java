package com.Apothic0n.Astrological.core.objects;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;

public class HalfTransparentRotatedPillarBlock extends RotatedPillarBlock {
    public HalfTransparentRotatedPillarBlock(Properties p_55926_) {
        super(p_55926_);
    }

    public boolean skipRendering(BlockState p_53972_, BlockState p_53973_, Direction p_53974_) {
        return p_53973_.is(this) || super.skipRendering(p_53972_, p_53973_, p_53974_);
    }
}
