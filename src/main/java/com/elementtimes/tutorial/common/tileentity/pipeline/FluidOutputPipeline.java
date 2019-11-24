package com.elementtimes.tutorial.common.tileentity.pipeline;

import com.elementtimes.elementcore.api.common.ECUtils;
import com.elementtimes.tutorial.common.block.pipeline.BaseElement;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;

/**
 * 物品输出管道
 * 用于将 ItemStack 从管道中输出
 * @author luqin2007
 */
public class FluidOutputPipeline extends BaseFluidPipeline {

    @Override
    public boolean canConnectTo(EnumFacing direction) {
        if (world == null) {
            return false;
        }
        if (isInterrupted(direction)) {
            return false;
        }
        TileEntity te = world.getTileEntity(pos.offset(direction));
        if (te != null) {
            if (te instanceof BaseTilePipeline) {
                return ((BaseTilePipeline) te).canConnectBy(pos, direction.getOpposite());
            } else {
                return te.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, direction.getOpposite());
            }
        }
        return false;
    }

    @Override
    public boolean canOutput(BlockPos pos, BaseElement element) {
        if (world != null && element.element instanceof FluidStack) {
            TileEntity te = world.getTileEntity(pos);
            EnumFacing facing = ECUtils.block.getPosFacing(this.pos, pos);
            if (te != null) {
                IFluidHandler handler = te.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, facing);
                if (handler != null) {
                    int fill = handler.fill((FluidStack) element.element, false);
                    return fill > 0;
                }
            }
        }
        return false;
    }

    @Override
    public BaseElement output(BlockPos pos, BaseElement element) {
        if (world != null && canOutput(pos, element)) {
            element.remove(pos);
            FluidStack stack = (FluidStack) element.element;
            TileEntity te = world.getTileEntity(pos);
            EnumFacing facing = ECUtils.block.getPosFacing(this.pos, pos);
            if (te != null) {
                IFluidHandler handler = te.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, facing);
                if (handler != null) {
                    int fill = handler.fill(stack, true);
                    if (fill < stack.amount) {
                        BaseElement<FluidStack> backElement = element.copy();
                        backElement.element.amount -= fill;
                        return backElement;
                    } else {
                        return null;
                    }
                }
            }
        }
        return element;
    }

    @Override
    public int getKeepTime(BaseElement element) {
        return 10;
    }
}
