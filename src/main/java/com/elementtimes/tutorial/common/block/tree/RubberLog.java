package com.elementtimes.tutorial.common.block.tree;

import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import org.apache.commons.lang3.ArrayUtils;

import static net.minecraft.util.EnumFacing.Axis.Y;

/**
 * @author KSGFK create in 2019/6/4
 */
public class RubberLog extends BlockRotatedPillar {
    public static IProperty<Boolean> HAS_RUBBER = PropertyBool.create("rubber");

    public RubberLog() {
        super(Material.WOOD);
        this.setHardness(2.0F);
        this.setSoundType(SoundType.WOOD);
        this.setDefaultState(getDefaultState().withProperty(AXIS, Y).withProperty(HAS_RUBBER, false));
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, AXIS, HAS_RUBBER);
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        int i = 4;
        int j = 5;
        if (worldIn.isAreaLoaded(pos.add(-j, -j, -j), pos.add(j, j, j))) {
            for (BlockPos blockpos : BlockPos.getAllInBox(pos.add(-i, -i, -i), pos.add(i, i, i))) {
                IBlockState iblockstate = worldIn.getBlockState(blockpos);

                if (iblockstate.getBlock().isLeaves(iblockstate, worldIn, blockpos)) {
                    iblockstate.getBlock().beginLeavesDecay(iblockstate, worldIn, blockpos);
                }
            }
        }
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
        int b = meta & 0b1000;
        EnumFacing.Axis axis = EnumFacing.Axis.values()[a];
        boolean hasRubber = b == 0b1000;
        return super.getStateFromMeta(meta).withProperty(AXIS, axis).withProperty(HAS_RUBBER, hasRubber);
    }

    @Override
    public boolean canSustainLeaves(IBlockState state, IBlockAccess world, BlockPos pos) {
        return true;
    }

    @Override
    public boolean isWood(IBlockAccess world, BlockPos pos) {
        return true;
    }
}
