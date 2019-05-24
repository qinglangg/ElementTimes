package com.elementtimes.tutorial.common.block.machine;

import com.elementtimes.tutorial.common.block.base.BlockTileBase;
import com.elementtimes.tutorial.common.init.ElementtimesGUI;
import com.elementtimes.tutorial.common.tileentity.TileFurnace;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class Furnace extends BlockTileBase<TileFurnace> {

    public static IProperty<EnumFacing> FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
    public static IProperty<Boolean> BURNING = PropertyBool.create("burning");

    public Furnace() {
        super(ElementtimesGUI.Furnace, TileFurnace.class, true);

        setDefaultState(getDefaultState().withProperty(FACING, EnumFacing.NORTH).withProperty(BURNING, false));
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING, BURNING);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        int facing = state.getValue(FACING).getHorizontalIndex() & 0b0011;
        int burning = state.getValue(BURNING) ? 0b0100 : 0b0000;
        return facing | burning;
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        EnumFacing facing = EnumFacing.getHorizontal(meta & 0b0011);
        boolean burning = (meta & 0b0100) == 0b0100;
        return super.getStateFromMeta(meta).withProperty(FACING, facing).withProperty(BURNING, burning);
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
        IBlockState s = worldIn.getBlockState(pos);
        IBlockState s2 = s.withProperty(FACING, placer.getHorizontalFacing());
        worldIn.setBlockState(pos, s2);
    }
}
