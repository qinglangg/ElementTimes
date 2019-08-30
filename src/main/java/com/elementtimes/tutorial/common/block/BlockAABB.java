package com.elementtimes.tutorial.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

/**
 * 蒸发皿
 *
 * @author KSGFK create in 2019/6/12
 */
public class BlockAABB extends Block {
    private AxisAlignedBB aabb;

    public BlockAABB(AxisAlignedBB aabb) {
        super(Material.GLASS);
        this.aabb = aabb;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return aabb;
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean isFullCube(IBlockState state) {
        return false;
    }
}
