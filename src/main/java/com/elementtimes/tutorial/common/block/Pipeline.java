package com.elementtimes.tutorial.common.block;

import com.elementtimes.tutorial.common.init.ElementtimesBlocks;
import com.elementtimes.tutorial.common.tileentity.TilePipeline;
import com.elementtimes.tutorial.other.pipeline.PLInfo;
import com.elementtimes.tutorial.other.pipeline.PLNetworkManager;
import com.elementtimes.tutorial.other.pipeline.PLType;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
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
@SuppressWarnings("deprecation")
public class Pipeline extends Block implements ITileEntityProvider {

    /**
     * 管道类型
     */
    public static PropertyEnum<PLType> PL_TYPE = PropertyEnum.create("type", PLType.class);
    /**
     * 管道连接方向
     */
    public static PropertyBool PL_CONNECTED_UP = PropertyBool.create("connected_up");
    public static PropertyBool PL_CONNECTED_DOWN = PropertyBool.create("connected_down");
    public static PropertyBool PL_CONNECTED_EAST = PropertyBool.create("connected_east");
    public static PropertyBool PL_CONNECTED_WEST = PropertyBool.create("connected_west");
    public static PropertyBool PL_CONNECTED_NORTH = PropertyBool.create("connected_north");
    public static PropertyBool PL_CONNECTED_SOUTH = PropertyBool.create("connected_south");

    private static final String NBT_BIND_PIPELINE_TICK = "_pipeline_tick_";

    public Pipeline() {
        super(Material.CIRCUITS);
        setDefaultState(getDefaultState()
                .withProperty(PL_TYPE, PLType.Item)
                .withProperty(PL_CONNECTED_UP, false)
                .withProperty(PL_CONNECTED_DOWN, false)
                .withProperty(PL_CONNECTED_EAST, false)
                .withProperty(PL_CONNECTED_WEST, false)
                .withProperty(PL_CONNECTED_NORTH, false)
                .withProperty(PL_CONNECTED_SOUTH, false));
    }

    @Nonnull
    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this,
                PL_TYPE, PL_CONNECTED_UP, PL_CONNECTED_DOWN, PL_CONNECTED_EAST,
                PL_CONNECTED_WEST, PL_CONNECTED_NORTH, PL_CONNECTED_SOUTH);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(PL_TYPE).toInt();
    }

    @Nonnull
    @Override
    public IBlockState getStateFromMeta(int meta) {
        PLType type = PLType.get(meta);
        assert type != null;
        return super.getStateFromMeta(meta).withProperty(PL_TYPE, type);
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(@Nonnull World worldIn, int meta) {
        return new TilePipeline();
    }

    @Override
    public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
        super.getSubBlocks(itemIn, items);
        boolean removed = items.removeIf(is -> Block.getBlockFromItem(is.getItem()) == this);
        if (removed) {
            items.add(create(PLType.Item, 20));
            items.add(create(PLType.ItemIn, 20));
            items.add(create(PLType.ItemOut, 20));
            items.add(create(PLType.Fluid, 20));
            items.add(create(PLType.FluidIn, 20));
            items.add(create(PLType.FluidOut, 20));
        }
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
        if (!worldIn.isRemote) {
            TilePipeline tp = (TilePipeline) worldIn.getTileEntity(pos);
            assert tp != null;
            // nbt
            NBTTagCompound tagCompound = stack.getTagCompound();
            int tick;
            if (tagCompound != null && tagCompound.hasKey(NBT_BIND_PIPELINE_TICK)) {
                tick = tagCompound.getInteger(NBT_BIND_PIPELINE_TICK);
            } else {
                tick = 20;
            }
            tp.init(tick);
            PLInfo info = tp.getInfo();
            for (EnumFacing facing : EnumFacing.values()) {
                tp.tryConnect(facing);
            }

            PLNetworkManager.addPipeline(placer, worldIn, info);
        }
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        TilePipeline tp = (TilePipeline) worldIn.getTileEntity(pos);
        assert tp != null;
        PLInfo info = tp.getInfo();
        info.getNetwork().remove(worldIn, info);
        super.breakBlock(worldIn, pos, state);
    }

    @Nonnull
    @Override
    public IBlockState getActualState(@Nonnull IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        TileEntity te = worldIn.getTileEntity(pos);
        IBlockState actualState = super.getActualState(state, worldIn, pos);
        if (te instanceof TilePipeline) {
            return ((TilePipeline) te).bindActualState(actualState);
        }
        return actualState;
    }

    public static ItemStack create(PLType type, int keepTick) {
        ItemStack itemStack = new ItemStack(ElementtimesBlocks.pipeline, 1, type.toInt());
        itemStack.setTagInfo("_pipeline_tick_", new NBTTagInt(keepTick));
        return itemStack;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }
}
