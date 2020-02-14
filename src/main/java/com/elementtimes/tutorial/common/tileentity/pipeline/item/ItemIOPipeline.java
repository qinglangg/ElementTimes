package com.elementtimes.tutorial.common.tileentity.pipeline.item;

import com.elementtimes.elementcore.api.common.ECUtils;
import com.elementtimes.elementcore.api.template.tileentity.SideHandlerType;
import com.elementtimes.elementcore.api.template.tileentity.interfaces.ITileItemHandler;
import com.elementtimes.tutorial.common.block.pipeline.PipelineIO;
import com.elementtimes.tutorial.common.pipeline.BaseElement;
import com.elementtimes.tutorial.common.pipeline.IPipelineOutput;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;

import javax.annotation.Nonnull;

import static com.elementtimes.tutorial.common.block.pipeline.PipelineIO.CONNECTED_PROPERTIES;

/**
 * @author luqin2007
 */
public class ItemIOPipeline extends ItemConnectPipeline implements IPipelineOutput {

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
                IItemHandler capability = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, direction.getOpposite());
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

    @Override
    public BaseElement output(BaseElement element) {
        if (world != null && !world.isRemote && isConnectedIO()) {
            EnumFacing ioSide = EnumFacing.VALUES[readByteValue(12, 3)];
            BlockPos pos = this.pos.offset(ioSide);
            EnumFacing facing = ECUtils.block.getPosFacing(this.pos, pos);
            TileEntity te = world.getTileEntity(pos);
            IItemHandler handler;
            if (te != null) {
                if (te instanceof ITileItemHandler) {
                    handler = ((ITileItemHandler) te).getItemHandler(SideHandlerType.INPUT);
                } else {
                    handler = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, facing);
                }
                if (handler != null) {
                    ItemStack back = ItemHandlerHelper.insertItem(handler, (ItemStack) element.element, false);
                    return back.isEmpty() ? null : element.copyBack(back);
                }
            }
            return element.back();
        }
        return null;
    }

    @Override
    public boolean canOutput(BaseElement element) {
        if (world != null && !world.isRemote && isConnectedIO() && isElementAllowOutput(element)) {
            EnumFacing ioSide = EnumFacing.VALUES[readByteValue(12, 3)];
            TileEntity te = world.getTileEntity(pos.offset(ioSide));
            IItemHandler capability;
            if (te != null) {
                if (te instanceof ITileItemHandler) {
                    capability = ((ITileItemHandler) te).getItemHandler(SideHandlerType.INPUT);
                } else {
                    capability = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, ioSide.getOpposite());
                }
                ItemStack stack = ItemHandlerHelper.insertItem(capability, element.get(ItemStack.class), true);
                return stack.isEmpty() || stack.getCount() < element.get(ItemStack.class).getCount();
            }
        }
        return false;
    }

    protected boolean isElementAllowOutput(BaseElement element) {
        return element.back;
    }
}
