package com.elementtimes.tutorial.common.block.tree;

import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import org.apache.commons.lang3.ArrayUtils;

import static net.minecraft.util.EnumFacing.Axis.Y;

/**
 * @author luqin2007
 */
public class EssenceLog extends BlockRotatedPillar {

    public EssenceLog() {
        super(Material.WOOD);
        this.setHardness(2.0F);
        this.setSoundType(SoundType.WOOD);
        this.setDefaultState(getDefaultState().withProperty(AXIS, Y));
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
        int a = ArrayUtils.indexOf(EnumFacing.Axis.values(), axis);
        return a;
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        int a = meta & 0b0011;
        EnumFacing.Axis axis = EnumFacing.Axis.values()[a];
        return super.getStateFromMeta(meta).withProperty(AXIS, axis);
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
