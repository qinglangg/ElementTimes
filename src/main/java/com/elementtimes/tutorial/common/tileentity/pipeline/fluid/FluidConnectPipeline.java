package com.elementtimes.tutorial.common.tileentity.pipeline.fluid;

import com.elementtimes.tutorial.common.block.pipeline.Pipeline;
import com.elementtimes.tutorial.common.pipeline.BaseElement;
import com.elementtimes.tutorial.common.tileentity.pipeline.BaseTilePipeline;
import com.elementtimes.tutorial.common.pipeline.ITilePipeline;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nonnull;

import static com.elementtimes.tutorial.common.block.pipeline.Pipeline.CONNECTED_PROPERTIES;

/**
 * 物品连接管道
 * 仅用于物品管道间的连接
 * @author luqin2007
 */
public class FluidConnectPipeline extends BaseTilePipeline {

    @Override
    public boolean canConnectTo(BlockPos pos, EnumFacing direction) {
        if (world != null) {
            if (isInterrupted(pos, direction)) {
                return false;
            }
            TileEntity te = world.getTileEntity(pos);
            if (te instanceof ITilePipeline) {
                return ((ITilePipeline) te).canConnectBy(this.pos, direction.getOpposite());
            }
        }
        return false;
    }

    @Override
    public boolean canConnectBy(BlockPos pos, EnumFacing direction) {
        if (world != null && !isInterrupted(pos.offset(direction), direction)) {
            return world.getTileEntity(pos) instanceof FluidConnectPipeline;
        }
        return false;
    }

    @Override
    public boolean connect(BlockPos pos, EnumFacing direction) {
        if (world != null && !world.isRemote && !isConnected(pos, direction) && canConnectTo(pos, direction)) {
            if (writeByteValue(direction.getIndex())) {
                setBlockState(state -> state.withProperty(CONNECTED_PROPERTIES[direction.getIndex()], true));
                return true;
            }
        }
        return false;
    }

    @Override
    public void disconnect(BlockPos pos, EnumFacing direction) {
        if (world != null && direction != null && isConnected(pos, direction)) {
            if (clearByteValue(direction.getIndex())) {
                setBlockState(state -> state.withProperty(CONNECTED_PROPERTIES[direction.getIndex()], false));
            }
        }
    }

    @Override
    public boolean canReceive(BlockPos pos, BaseElement element) {
        return element.element instanceof FluidStack;
    }

    @Override
    public int getKeepTime(BaseElement element) {
        return 10;
    }

    @Nonnull
    @Override
    public IBlockState onBindActualState(@Nonnull IBlockState state, BlockPos pos) {
        for (EnumFacing value : EnumFacing.VALUES) {
            state = state.withProperty(Pipeline.CONNECTED_PROPERTIES[value.getIndex()], isConnected(pos.offset(value), value));
        }
        return state;
    }
}
