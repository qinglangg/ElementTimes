package com.elementtimes.tutorial.common.block;

import com.elementtimes.tutorial.common.block.base.BlockTileBase;
import com.elementtimes.tutorial.common.init.ElementtimesGUI;
import com.elementtimes.tutorial.common.tileentity.TileFurnace;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;

public class Furnace extends BlockTileBase<TileFurnace> {
    public Furnace() {
        super("furnace", "furnace", ElementtimesGUI.Furnace, TileFurnace.class, true);

        setDefaultState(getDefaultState().withProperty(FACING, EnumFacing.EAST).withProperty(IS_BURNING, false));
    }

    public static IProperty<Boolean> IS_BURNING = PropertyBool.create("burning");
    public static IProperty<EnumFacing> FACING = PropertyDirection.create("facing");

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING, IS_BURNING);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        int facing = state.getValue(FACING).getHorizontalIndex();
        int burning = state.getValue(IS_BURNING) ? 1 : 0;
        return (burning << 2) + facing;
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        EnumFacing facing = EnumFacing.getHorizontal(meta & 0b0011);
        boolean burning = ((meta >> 2) & 0b0001) == 1;
        return super.getStateFromMeta(meta).withProperty(FACING, facing).withProperty(IS_BURNING, burning);
    }
}
