package com.elementtimes.tutorial.common.block;

import net.minecraft.block.BlockLog;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;

/**
 * @author KSGFK create in 2019/6/4
 */
public class RubberLog extends BlockLog {
    public static IProperty<Boolean> HAS_RUBBER = PropertyBool.create("rubber");

    public RubberLog() {
        this.setDefaultState(this.blockState.getBaseState().withProperty(AXIS, EnumFacing.Axis.Y).withProperty(HAS_RUBBER, false));
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, AXIS, HAS_RUBBER);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        EnumFacing.Axis axis = state.getValue(AXIS);
        Boolean isRub = state.getValue(HAS_RUBBER);
        int a = 0;
        switch (axis) {
            case X:
                a |= 4;
                break;
            case Y:
                a |= 8;
                break;
        }
        int b = isRub ? 0b0100 : 0b0000;
        return a | b;
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        EnumFacing.Axis axis = EnumFacing.Axis.Y;
        int a = meta & 12;
        if (a == 4) {
            axis = EnumFacing.Axis.X;
        } else if (a == 8) {
            axis = EnumFacing.Axis.Z;
        }
        boolean has = (meta & 0b0100) == 0b0100;
        return super.getStateFromMeta(meta).withProperty(HAS_RUBBER, has).withProperty(AXIS, axis);
    }
}
