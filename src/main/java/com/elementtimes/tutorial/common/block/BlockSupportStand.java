package com.elementtimes.tutorial.common.block;

import com.elementtimes.tutorial.common.block.base.BlockTileBase;
import com.elementtimes.tutorial.common.tileentity.TileSupportStand;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

/**
 * @author KSGFK create in 2019/6/12
 */
public class BlockSupportStand extends BlockTileBase<TileSupportStand> {
    private AxisAlignedBB aabb = new AxisAlignedBB(0.1D, 0D, 0.1D, 0.9D, 0.88D, 0.9D);

    public BlockSupportStand() {
        super(7, TileSupportStand.class, false);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return aabb;
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }
}
