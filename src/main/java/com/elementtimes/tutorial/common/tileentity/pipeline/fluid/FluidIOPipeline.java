package com.elementtimes.tutorial.common.tileentity.pipeline.fluid;

import com.elementtimes.tutorial.common.block.pipeline.PipelineIO;
import com.elementtimes.tutorial.interfaces.ITilePipelineIO;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;

import javax.annotation.Nonnull;

import static com.elementtimes.tutorial.common.block.pipeline.PipelineIO.CONNECTED_PROPERTIES;

public class FluidIOPipeline extends FluidConnectPipeline implements ITilePipelineIO {

    @Override
    public void onPlace(ItemStack stack, EntityLivingBase placer) {
        for (EnumFacing facing : EnumFacing.VALUES) {
            BlockPos pos = this.pos.offset(facing);
            connect(pos, facing);
            if (canConnectIO(pos, facing)) {
                connectIO(pos, facing);
            }
        }
    }

    @Override
    public void onNeighborChanged(BlockPos neighbor) {
        super.onNeighborChanged(neighbor);
        if (world != null && !world.isRemote) {
            for (EnumFacing facing : EnumFacing.VALUES) {
                BlockPos offset = pos.offset(facing);
                if (canConnectIO(offset, facing) && !isConnectedIO(offset, facing)) {
                    connectIO(offset, facing);
                } else if (!canConnectIO(offset, facing) && isConnectedIO(offset, facing)) {
                    disconnectIO(offset, facing);
                }
            }
        }
    }

    @Override
    public boolean connect(BlockPos pos, EnumFacing direction) {
        if (world != null && !world.isRemote && !isConnected(pos, direction) && canConnectTo(pos, direction)) {
            if (writeByteValue(direction.getIndex())) {
                setBlockState(state -> state.withProperty(CONNECTED_PROPERTIES[direction.getIndex()], PipelineIO.SideType.NORMAL));
                return true;
            }
        }
        return false;
    }

    @Override
    public void disconnect(BlockPos pos, EnumFacing direction) {
        if (world != null && direction != null && isConnected(pos, direction)) {
            if (clearByteValue(direction.getIndex())) {
                setBlockState(state -> state.withProperty(CONNECTED_PROPERTIES[direction.getIndex()], PipelineIO.SideType.NONE));
            }
        }
    }

    @Override
    public boolean canConnectIO(BlockPos pos, EnumFacing direction) {
        if (world != null) {
            TileEntity te = world.getTileEntity(pos);
            if (te != null) {
                IFluidHandler capability = te.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, direction.getOpposite());
                return capability != null;
            }
        }
        return false;
    }

    @Override
    public boolean isConnectedIO(BlockPos pos, EnumFacing direction) {
        return direction != null && direction.getIndex() == readByteValue(12, 3);
    }

    @Override
    public boolean isConnectedIO() {
        int i = readByteValue(12, 3);
        return i >= 0 && i <= 5;
    }

    @Override
    public void connectIO(BlockPos pos, EnumFacing direction) {
        if (direction != null) {
            int index = direction.getIndex();
            if (writeByteValue(12, 3, index)) {
                setBlockState(state -> state.withProperty(CONNECTED_PROPERTIES[index], PipelineIO.SideType.IO));
            }
        }
    }

    @Override
    public void disconnectIO(BlockPos pos, EnumFacing direction) {
        if (direction != null && direction.getIndex() == readByteValue(12, 3)) {
            if (writeByteValue(12, 3, 6)) {
                setBlockState(state -> state.withProperty(CONNECTED_PROPERTIES[direction.getIndex()], PipelineIO.SideType.NONE));
            }
        }
    }

    @Nonnull
    @Override
    public IBlockState onBindActualState(@Nonnull IBlockState state, BlockPos pos) {
        int side = readByteValue(12, 3);
        for (EnumFacing value : EnumFacing.VALUES) {
            PipelineIO.SideType type = side == value.getIndex() ? PipelineIO.SideType.IO : isConnected(pos.offset(value), value) ? PipelineIO.SideType.NORMAL : PipelineIO.SideType.NONE;
            state = state.withProperty(CONNECTED_PROPERTIES[value.getIndex()], type);
        }
        return state;
    }
}
