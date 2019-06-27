package com.elementtimes.tutorial.common.block;

import com.elementtimes.tutorial.common.tileentity.TilePipeline;
import com.elementtimes.tutorial.other.pipeline.PLType;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * 管道类
 * @author luqin2007
 */
public class Pipeline extends Block implements ITileEntityProvider {

    /**
     * 管道类型
     */
    public static PropertyEnum<PLType> PL_TYPE = PropertyEnum.create("type", PLType.class);

    /**
     * 标记 管道中是否有物品/流体
     */
    public static PropertyBool PL_HAS_ELEM = PropertyBool.create("has_elem");

    /**
     * 使用 6 位 int 代表 6 个方向是否有其他管道相连接，顺序 D-U-N-S-W-E（即 EnumFacing.getIndex 顺序）
     */
    public static PropertyInteger PL_CONNECTED = PropertyInteger.create("conn", 0b000000, 0b111111);

    /**
     * 使用 6 位 int 代表 6 个方向是否被强制截断（现在还没有加入，以后可能会考虑使用扳手或者其他方法），顺序与 PL_CONNECTED 相同
     */
    public static PropertyInteger PL_DISCONNECTED = PropertyInteger.create("disconn", 0b000000, 0b111111);

    public Pipeline() {
        super(Material.CIRCUITS);
        setDefaultState(getDefaultState()
                .withProperty(PL_TYPE, PLType.Item)
                .withProperty(PL_HAS_ELEM, false)
                .withProperty(PL_CONNECTED, 0b000000)
                .withProperty(PL_DISCONNECTED, 0b000000));
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, PL_TYPE, PL_HAS_ELEM, PL_CONNECTED, PL_DISCONNECTED);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(PL_TYPE).toInt();
    }

    @Nonnull
    @Override
    public IBlockState getStateFromMeta(int meta) {
        return super.getStateFromMeta(meta).withProperty(PL_TYPE, PLType.get(meta));
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TilePipeline();
    }

    @Override
    public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
        super.getSubBlocks(itemIn, items);
        boolean removed = items.removeIf(is -> Block.getBlockFromItem(is.getItem()) == this);
        if (removed) {
            items.add(new ItemStack(this, 1, 0b0000));
            items.add(new ItemStack(this, 1, 0b0001));
            items.add(new ItemStack(this, 1, 0b0010));
            items.add(new ItemStack(this, 1, 0b0100));
            items.add(new ItemStack(this, 1, 0b0101));
            items.add(new ItemStack(this, 1, 0b0110));
        }
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
        if (!worldIn.isRemote) {
            TilePipeline.notifyUpdate(worldIn.getTileEntity(pos));
            TilePipeline.notifyUpdate(worldIn.getTileEntity(pos.up()));
            TilePipeline.notifyUpdate(worldIn.getTileEntity(pos.down()));
            TilePipeline.notifyUpdate(worldIn.getTileEntity(pos.east()));
            TilePipeline.notifyUpdate(worldIn.getTileEntity(pos.west()));
            TilePipeline.notifyUpdate(worldIn.getTileEntity(pos.south()));
            TilePipeline.notifyUpdate(worldIn.getTileEntity(pos.north()));
        }
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        TileEntity te = worldIn.getTileEntity(pos);
        if (te instanceof TilePipeline) {
            return ((TilePipeline) te).getActualState(super.getActualState(state, worldIn, pos));
        }
        return super.getActualState(state, worldIn, pos);
    }

    @Override
    public void observedNeighborChange(IBlockState observerState, World world, BlockPos observerPos, Block changedBlock, BlockPos changedBlockPos) {
        super.observedNeighborChange(observerState, world, observerPos, changedBlock, changedBlockPos);
    }

    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
        super.neighborChanged(state, worldIn, pos, blockIn, fromPos);
        update(worldIn, pos);
    }

    public void update(World worldIn, BlockPos pos) {
        if (!worldIn.isRemote) {
            TileEntity te = worldIn.getTileEntity(pos);
            if (te instanceof TilePipeline) {
                TilePipeline tp = (TilePipeline) te;
                PLType type = tp.getType();
                if (type.in()) {

                }
            }
        }
    }
}
