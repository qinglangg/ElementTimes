package com.elementtimes.elementtimes.common.block.pipeline;

import com.elementtimes.elementcore.api.block.BlockTileBase;
import com.elementtimes.elementtimes.common.init.blocks.Industry;
import com.elementtimes.elementtimes.common.pipeline.BaseTilePipeline;
import com.elementtimes.elementtimes.common.pipeline.ConnectType;
import com.elementtimes.elementtimes.common.pipeline.PipelinePropertyMap;
import com.elementtimes.elementtimes.common.pipeline.PipelineSideProperty;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;
import java.util.function.Supplier;

/**
 * 管道类

 */
@SuppressWarnings("deprecation")
public class Pipeline extends BlockTileBase {

    protected static final VoxelShape SHAPE_PIPELINE_CENTER = VoxelShapes.create(0.375, 0.375, 0.375, 0.625, 0.625, 0.625);
    protected static final VoxelShape SHAPE_PIPELINE_UP     = VoxelShapes.create(0.375, 0.625, 0.375, 0.625, 1, 0.625);
    protected static final VoxelShape SHAPE_PIPELINE_DOWN   = VoxelShapes.create(0.375, 0, 0.375, 0.625, 0.375, 0.625);
    protected static final VoxelShape SHAPE_PIPELINE_WEST   = VoxelShapes.create(0, 0.375, 0.375, 0.375, 0.625, 0.625);
    protected static final VoxelShape SHAPE_PIPELINE_EAST   = VoxelShapes.create(0.625, 0.375, 0.375, 1, 0.625, 0.625);
    protected static final VoxelShape SHAPE_PIPELINE_NORTH  = VoxelShapes.create(0.375, 0.375, 0, 0.625, 0.625, 0.375);
    protected static final VoxelShape SHAPE_PIPELINE_SOUTH  = VoxelShapes.create(0.375, 0.375, 0.625, 0.625, 0.625, 1);

    public static Map<Direction, PipelineSideProperty> PROPERTIES = new PipelinePropertyMap();

    public Pipeline(Supplier<? extends BaseTilePipeline> te) {
        super(Properties.create(Material.ROCK), te);
        BlockState state = getDefaultState();
        for (PipelineSideProperty property : PROPERTIES.values()) {
            state = state.with(property, ConnectType.NONE);
        }
        setDefaultState(state);
    }

    public Pipeline(Class<? extends BaseTilePipeline> teClass) {
        this(Industry.te(teClass));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        super.fillStateContainer(builder);
        builder.add(PROPERTIES.values().toArray(new PipelineSideProperty[0]));
    }

    @Override
    public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
        super.neighborChanged(state, worldIn, pos, blockIn, fromPos, isMoving);
        if (!worldIn.isRemote) {
            TileEntity te = worldIn.getTileEntity(pos);
            if (te instanceof BaseTilePipeline) {
                ((BaseTilePipeline) te).onNeighborChanged(fromPos);
            }
        }
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
        TileEntity te = worldIn.getTileEntity(pos);
        if (te instanceof BaseTilePipeline) {
            ((BaseTilePipeline) te).onPlace(stack, placer);
        }
    }

    @Nonnull
    @Override
    public ItemStack getDismantleItem(World world, BlockPos pos) {
        return new ItemStack(this);
    }

    @Override
    public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        if (!worldIn.isRemote && newState.getBlock() != this) {
            final TileEntity te = worldIn.getTileEntity(pos);
            if (te instanceof BaseTilePipeline) {
                ((BaseTilePipeline) te).onRemoved(state);
            }
        }
        super.onReplaced(state, worldIn, pos, newState, isMoving);

    }

    @Override
    public boolean isNormalCube(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return false;
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        VoxelShape shape = SHAPE_PIPELINE_CENTER;
        if (state.get(PROPERTIES.get(Direction.UP)).isConnected()) {
            shape = VoxelShapes.or(shape, SHAPE_PIPELINE_UP);
        }
        if (state.get(PROPERTIES.get(Direction.DOWN)).isConnected()) {
            shape = VoxelShapes.or(shape, SHAPE_PIPELINE_DOWN);
        }
        if (state.get(PROPERTIES.get(Direction.EAST)).isConnected()) {
            shape = VoxelShapes.or(shape, SHAPE_PIPELINE_EAST);
        }
        if (state.get(PROPERTIES.get(Direction.WEST)).isConnected()) {
            shape = VoxelShapes.or(shape, SHAPE_PIPELINE_WEST);
        }
        if (state.get(PROPERTIES.get(Direction.NORTH)).isConnected()) {
            shape = VoxelShapes.or(shape, SHAPE_PIPELINE_NORTH);
        }
        if (state.get(PROPERTIES.get(Direction.SOUTH)).isConnected()) {
            shape = VoxelShapes.or(shape, SHAPE_PIPELINE_SOUTH);
        }
        return shape;
    }

    @Override
    public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity playerIn, Hand hand, BlockRayTraceResult hit) {
        return false;
    }
}
