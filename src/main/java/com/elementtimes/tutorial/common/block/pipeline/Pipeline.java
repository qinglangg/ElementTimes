package com.elementtimes.tutorial.common.block.pipeline;

import com.elementtimes.tutorial.common.tileentity.pipeline.BaseTilePipeline;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Supplier;

/**
 * 管道类
 * @author luqin2007
 */
@SuppressWarnings("deprecation")
public class Pipeline extends Block implements ITileEntityProvider {

    /**
     * 管道连接方向
     */
    public static PropertyBool[] CONNECTED_PROPERTIES = new PropertyBool[] {
            PropertyBool.create("connected_down"),
            PropertyBool.create("connected_up"),
            PropertyBool.create("connected_north"),
            PropertyBool.create("connected_south"),
            PropertyBool.create("connected_west"),
            PropertyBool.create("connected_east")
    };

    public final Supplier<? extends BaseTilePipeline> te;

    public Pipeline(Supplier<? extends BaseTilePipeline> te) {
        super(Material.ROCK);
        this.te = te;
        IBlockState state = getDefaultState();
        for (PropertyBool property : CONNECTED_PROPERTIES) {
            state.withProperty(property, false);
        }
        setDefaultState(state);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return 0;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, CONNECTED_PROPERTIES);
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return te.get();
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        IBlockState blockState = super.getActualState(state, worldIn, pos);
        TileEntity te = worldIn.getTileEntity(pos);
        if (te instanceof BaseTilePipeline) {
            for (EnumFacing value : EnumFacing.values()) {
                blockState = blockState.withProperty(CONNECTED_PROPERTIES[value.getIndex()], ((BaseTilePipeline) te).isConnected(value));
            }
        }
        return blockState;
    }

    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
        super.neighborChanged(state, worldIn, pos, blockIn, fromPos);
        if (!worldIn.isRemote) {
            TileEntity te = worldIn.getTileEntity(pos);
            if (te instanceof BaseTilePipeline) {
                ((BaseTilePipeline) te).onNeighborChanged(fromPos);
            }
        }
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
        TileEntity te = worldIn.getTileEntity(pos);
        if (te instanceof BaseTilePipeline) {
            ((BaseTilePipeline) te).onPlace(stack, placer);
        }
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        if (!worldIn.isRemote) {
            final TileEntity te = worldIn.getTileEntity(pos);
            if (te instanceof BaseTilePipeline) {
                ((BaseTilePipeline) te).onRemoved(state);
            }
        }
        super.breakBlock(worldIn, pos, state);
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean isActualState) {
        if (!isActualState) {
            state = getActualState(state, worldIn, pos);
        }
        collidingBoxes.add(new AxisAlignedBB(0.375, 0.375, 0.375, 0.625, 0.625, 0.625));
        for (EnumFacing facing : EnumFacing.values()) {
            if (state.getValue(CONNECTED_PROPERTIES[facing.getIndex()])) {
                Vec3i vec = facing.getDirectionVec();
                collidingBoxes.add(new AxisAlignedBB(getVec1(vec.getX()), getVec1(vec.getY()), getVec1(vec.getZ()), getVec2(vec.getX()), getVec2(vec.getY()), getVec2(vec.getZ())));
            }
        }
    }

    private float getVec1(int i) {
        switch (i) {
            case 0: return 0.375f;
            case 1: return 0.625f;
            default: return 0;
        }
    }

    private float getVec2(int i) {
        switch (i) {
            case 0: return 0.625f;
            case 1: return 1;
            default: return 0.375f;
        }
    }
}
