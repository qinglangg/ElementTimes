package com.elementtimes.tutorial.common.block;

import net.minecraft.block.BlockLog;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import org.apache.commons.lang3.ArrayUtils;

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
        int a = ArrayUtils.indexOf(EnumFacing.Axis.values(), axis);
        int b = isRub ? 0b1000 : 0b0000;
        return a | b;
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        int a = meta & 0b0011;
        EnumFacing.Axis axis = EnumFacing.Axis.values()[a];
        boolean has = (meta & 0b1000) == 0b1000;
        return super.getStateFromMeta(meta).withProperty(HAS_RUBBER, has).withProperty(AXIS, axis);
    }
}
