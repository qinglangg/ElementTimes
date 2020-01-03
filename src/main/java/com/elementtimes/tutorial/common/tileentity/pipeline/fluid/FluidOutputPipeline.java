package com.elementtimes.tutorial.common.tileentity.pipeline.fluid;

import com.elementtimes.tutorial.common.pipeline.BaseElement;
import com.elementtimes.tutorial.interfaces.ITilePipelineOutput;
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
public class FluidOutputPipeline extends FluidIOPipeline implements ITilePipelineOutput {

    @Override
    public BaseElement output(BaseElement element) {
        if (isConnectedIO()) {
            EnumFacing ioSide = EnumFacing.VALUES[readByteValue(12, 3)];
            BlockPos target = pos.offset(ioSide);
            TileEntity te = world.getTileEntity(target);
            if (te != null) {
                IFluidHandler handler = te.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, ioSide.getOpposite());
                if (handler != null) {
                    FluidStack stack = element.get(FluidStack.class);
                    int fillCount = handler.fill(stack, true);
                    if (stack != null && stack.amount > fillCount) {
                        BaseElement copy = element.copy();
                        copy.get(FluidStack.class).amount = stack.amount - fillCount;
                        return copy;
                    }
                }
            }
        }
        return element;
    }

    @Override
    public boolean canOutput(BaseElement element) {
        if (world != null && element.get(FluidStack.class) != null) {
            int i = readByteValue(12, 3);
            if (i >= 0 && i <= 5) {
                EnumFacing facing = EnumFacing.VALUES[i];
                BlockPos target = pos.offset(facing);
                TileEntity te = world.getTileEntity(target);
                if (te != null) {
                    IFluidHandler capability = te.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, facing.getOpposite());
                    if (capability != null) {
                        return capability.fill(element.get(FluidStack.class), false) > 0;
                    }
                }
            }
        }
        return false;
    }
}
