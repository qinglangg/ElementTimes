package com.elementtimes.tutorial.common.block;

import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.AxisAlignedBB;

public class Crucible extends BlockAABB {

    public static PropertyBool RUNNING = PropertyBool.create("running");

    public Crucible() {
        super(new AxisAlignedBB(1, 1, 1, 1, 1, 1));
        setDefaultState(getDefaultState().withProperty(RUNNING, false));
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        boolean isRunning = (meta & 0b0001) == 0b0001;
        return super.getStateFromMeta(meta).withProperty(RUNNING, isRunning);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(RUNNING) ? 0b0001 : 0b0000;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, RUNNING);
    }
}
