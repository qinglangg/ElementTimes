package com.elementtimes.tutorial.common.block.pipeline;

import com.elementtimes.elementcore.api.template.block.interfaces.IDismantleBlock;
import com.elementtimes.tutorial.ElementTimes;
import com.elementtimes.tutorial.common.pipeline.ITilePipeline;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Supplier;

/**
 * 管道类
 * @author luqin2007
 */
@SuppressWarnings("deprecation")
public class Pipeline extends Block implements ITileEntityProvider, IDismantleBlock {

    protected static final AxisAlignedBB AABB_PIPELINE_CENTER = new AxisAlignedBB(0.375, 0.375, 0.375, 0.625, 0.625, 0.625);
    protected static final AxisAlignedBB AABB_PIPELINE_UP     = new AxisAlignedBB(0.375, 0.625, 0.375, 0.625, 1, 0.625);
    protected static final AxisAlignedBB AABB_PIPELINE_DOWN   = new AxisAlignedBB(0.375, 0, 0.375, 0.625, 0.375, 0.625);
    protected static final AxisAlignedBB AABB_PIPELINE_WEST   = new AxisAlignedBB(0, 0.375, 0.375, 0.375, 0.625, 0.625);
    protected static final AxisAlignedBB AABB_PIPELINE_EAST   = new AxisAlignedBB(0.625, 0.375, 0.375, 1, 0.625, 0.625);
    protected static final AxisAlignedBB AABB_PIPELINE_NORTH  = new AxisAlignedBB(0.375, 0.375, 0, 0.625, 0.625, 0.375);
    protected static final AxisAlignedBB AABB_PIPELINE_SOUTH  = new AxisAlignedBB(0.375, 0.375, 0.625, 0.625, 0.625, 1);

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

    public final Supplier<? extends ITilePipeline> te;

    public Pipeline(Supplier<? extends ITilePipeline> te) {
        super(Material.ROCK);
        this.te = te;
        IBlockState state = getDefaultState();
        for (IProperty property : getProperties()) {
            state = state.withProperty(property, getDefaultPropertyValue(property));
        }
        setDefaultState(state);
    }

    public IProperty[] getProperties() {
        return CONNECTED_PROPERTIES;
    }

    public Comparable getDefaultPropertyValue(IProperty property) {
        return false;
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return 0;
    }

    @Nonnull
    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, getProperties());
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(@Nonnull World worldIn, int meta) {
        return (TileEntity) te.get();
    }

    @Nonnull
    @Override
    public IBlockState getActualState(@Nonnull IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        IBlockState blockState = super.getActualState(state, worldIn, pos);
        TileEntity te = worldIn.getTileEntity(pos);
        if (te instanceof ITilePipeline) {
            return ((ITilePipeline) te).onBindActualState(blockState, pos);
        }
        return blockState;
    }

    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
        super.neighborChanged(state, worldIn, pos, blockIn, fromPos);
        if (!worldIn.isRemote) {
            TileEntity te = worldIn.getTileEntity(pos);
            if (te instanceof ITilePipeline) {
                ((ITilePipeline) te).onNeighborChanged(fromPos);
            }
        }
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
        TileEntity te = worldIn.getTileEntity(pos);
        if (te instanceof ITilePipeline) {
            ((ITilePipeline) te).onPlace(stack, placer);
        }
    }

    @Nonnull
    @Override
    public ItemStack getDismantleItem(World world, BlockPos pos) {
        return new ItemStack(this);
    }

    @Override
    public void breakBlock(World worldIn, @Nonnull BlockPos pos, @Nonnull IBlockState state) {
        if (!worldIn.isRemote) {
            final TileEntity te = worldIn.getTileEntity(pos);
            if (te instanceof ITilePipeline) {
                ((ITilePipeline) te).onRemoved(state);
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
    public boolean isFullBlock(IBlockState state) {
        return false;
    }

    @Nonnull
    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
        return BlockFaceShape.UNDEFINED;
    }

    @Nonnull
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        AxisAlignedBB aabb = AABB_PIPELINE_CENTER;
        TileEntity te = source.getTileEntity(pos);
        if (te instanceof ITilePipeline) {
            if (((ITilePipeline) te).isConnected(pos.offset(EnumFacing.UP), EnumFacing.UP)) {
                aabb = aabb.union(AABB_PIPELINE_UP);
            }
            if (((ITilePipeline) te).isConnected(pos.offset(EnumFacing.DOWN), EnumFacing.DOWN)) {
                aabb = aabb.union(AABB_PIPELINE_DOWN);
            }
            if (((ITilePipeline) te).isConnected(pos.offset(EnumFacing.EAST), EnumFacing.EAST)) {
                aabb = aabb.union(AABB_PIPELINE_EAST);
            }
            if (((ITilePipeline) te).isConnected(pos.offset(EnumFacing.WEST), EnumFacing.WEST)) {
                aabb = aabb.union(AABB_PIPELINE_WEST);
            }
            if (((ITilePipeline) te).isConnected(pos.offset(EnumFacing.NORTH), EnumFacing.NORTH)) {
                aabb = aabb.union(AABB_PIPELINE_NORTH);
            }
            if (((ITilePipeline) te).isConnected(pos.offset(EnumFacing.SOUTH), EnumFacing.SOUTH)) {
                aabb = aabb.union(AABB_PIPELINE_SOUTH);
            }
        }
        return aabb;
    }

    @Override
    public void addCollisionBoxToList(IBlockState state, @Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull AxisAlignedBB entityBox, @Nonnull List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean isActualState) {
        super.addCollisionBoxToList(state, worldIn, pos, entityBox, collidingBoxes, entityIn, isActualState);
        TileEntity te = worldIn.getTileEntity(pos);
        if (te instanceof ITilePipeline) {
            if (((ITilePipeline) te).isConnected(pos.offset(EnumFacing.UP), EnumFacing.UP)) {
                addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_PIPELINE_UP);
            }
            if (((ITilePipeline) te).isConnected(pos.offset(EnumFacing.DOWN), EnumFacing.DOWN)) {
                addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_PIPELINE_DOWN);
            }
            if (((ITilePipeline) te).isConnected(pos.offset(EnumFacing.EAST), EnumFacing.EAST)) {
                addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_PIPELINE_EAST);
            }
            if (((ITilePipeline) te).isConnected(pos.offset(EnumFacing.WEST), EnumFacing.WEST)) {
                addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_PIPELINE_WEST);
            }
            if (((ITilePipeline) te).isConnected(pos.offset(EnumFacing.NORTH), EnumFacing.NORTH)) {
                addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_PIPELINE_NORTH);
            }
            if (((ITilePipeline) te).isConnected(pos.offset(EnumFacing.SOUTH), EnumFacing.SOUTH)) {
                addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_PIPELINE_SOUTH);
            }
        }
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote) {
            playerIn.openGui(ElementTimes.instance, 0, worldIn, pos.getX(), pos.getY(), pos.getZ());
        }
        return true;
    }
}
