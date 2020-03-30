package com.elementtimes.tutorial.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;

import javax.annotation.Nullable;
import java.util.Random;

public class Bamboo extends Block implements IPlantable, IGrowable {

    private static final AxisAlignedBB BAMBOO_AABB = new AxisAlignedBB(0.3, 0, 0.3, 0.7, 1, 0.7);

    public Bamboo() {
        super(Material.PLANTS);
        setTickRandomly(true);
    }

    @Override
    public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {
        return !worldIn.isRemote && worldIn.rand.nextFloat() <= .3f && nextYCheck(worldIn, pos);
    }

    @Override
    public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state) {
        return true || canGrow(worldIn, pos, state, worldIn.isRemote);
    }

    @Override
    public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state) {
        if (canGrow(worldIn, pos, state, worldIn.isRemote)) {
            BlockPos upPos = pos.up();
            if (!worldIn.isOutsideBuildHeight(upPos)) {
                worldIn.setBlockState(upPos, getDefaultState(), 2);
            }
        }
    }

    @Override
    public EnumPlantType getPlantType(IBlockAccess world, BlockPos pos) {
        return EnumPlantType.Plains;
    }

    @Override
    public IBlockState getPlant(IBlockAccess world, BlockPos pos) {
        return getDefaultState();
    }

    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        super.updateTick(worldIn, pos, state, rand);
        if (!worldIn.isRemote && drop(worldIn, state, pos) && canGrow(worldIn, pos, state, false)) {
            grow(worldIn, rand, pos, state);
        }
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return BAMBOO_AABB;
    }

    @Nullable
    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
        return NULL_AABB;
    }

    @Override
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
        Block block = worldIn.getBlockState(pos.down()).getBlock();
        return block == Blocks.GRASS || block == Blocks.DIRT || block == this;
    }

    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
        super.neighborChanged(state, worldIn, pos, blockIn, fromPos);
        drop(worldIn, worldIn.getBlockState(fromPos), fromPos);
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullBlock(IBlockState state) {
        return false;
    }

    @Override
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
        return BlockFaceShape.UNDEFINED;
    }

    private boolean drop(World world, IBlockState state, BlockPos pos) {
        if (!canPlaceBlockAt(world, pos.down())) {
            dropBlockAsItem(world, pos, state, 0);
            world.setBlockToAir(pos);
            return false;
        }
        return true;
    }

    public int getMaxHeight() {
        return 10;
    }

    public boolean nextYCheck(World world, BlockPos pos) {
        if (world.isOutsideBuildHeight(pos.up())) {
            return false;
        }
        BlockPos currentPos = pos;
        int height = 0;
        while (world.getBlockState(pos).getBlock() == this) {
            height++;
            currentPos = currentPos.down();
        }
        return height < getMaxHeight();
    }
}
