package com.elementtimes.elementtimes.common.block.pipeline;

import com.elementtimes.elementcore.api.annotation.ModTileEntity;
import com.elementtimes.elementcore.api.annotation.part.Getter;
import com.elementtimes.elementtimes.common.init.blocks.Industry;
import com.elementtimes.elementtimes.common.pipeline.BaseElement;
import com.elementtimes.elementtimes.common.pipeline.ConnectType;
import com.elementtimes.elementtimes.common.pipeline.FluidElement;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;



@ModTileEntity(blocks = @Getter(value = Industry.class, name = "pipelineFluidInput"))
public class TileFluidInput extends TileFluidConnect {

    public TileFluidInput(TileEntityType<TileFluidInput> tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }

    @Override
    public boolean canInputElementFrom(BlockPos from) {
        return hasHandler(from, CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY);
    }

    @Override
    public void extractElements(boolean simulate) {
        if (world == null || world.isRemote) {
            return;
        }
        for (BlockPos blockPos : allReachableInputs()) {
            TileEntity te = world.getTileEntity(pos);
            if (te == null) {
                setConnectState(blockPos, ConnectType.NONE);
                continue;
            }
            te.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY).ifPresent(handler -> {
                FluidStack drain = handler.drain(Integer.MAX_VALUE, IFluidHandler.FluidAction.SIMULATE);
                BaseElement<FluidStack> element = FluidElement.TYPE.newInstance(drain.copy());
                BaseElement<FluidStack> send = sendElement(element, blockPos, false);
                FluidStack stack = send.get(FluidStack.class).orElse(FluidStack.EMPTY);
                int count = drain.getAmount() - stack.getAmount();
                if (count > 0) {
                    handler.drain(count, IFluidHandler.FluidAction.EXECUTE);
                }
            });
        }
    }
}
