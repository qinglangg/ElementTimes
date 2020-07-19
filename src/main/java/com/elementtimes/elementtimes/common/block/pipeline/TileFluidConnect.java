package com.elementtimes.elementtimes.common.block.pipeline;

import com.elementtimes.elementcore.api.annotation.ModTileEntity;
import com.elementtimes.elementcore.api.annotation.part.Getter;
import com.elementtimes.elementcore.api.utils.BlockUtils;
import com.elementtimes.elementtimes.common.init.blocks.Industry;
import com.elementtimes.elementtimes.common.pipeline.*;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;

import java.util.Optional;



@ModTileEntity(blocks = @Getter(value = Industry.class, name = "pipelineFluidConnect"))
public class TileFluidConnect extends BaseTilePipeline implements SymbolFluidPipeline {

    private final FluidPathHelper mHelper = new FluidPathHelper(this);

    public TileFluidConnect(TileEntityType<? extends TileFluidConnect> tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }

    @Override
    public boolean connectedBy(IPipeline from, IConnectType type, boolean simulate) {
        if (from instanceof SymbolFluidPipeline) {
            return super.connectedBy(from, type, simulate);
        }
        return false;
    }

    @Override
    public <T> BaseElement<T> sendElement(BaseElement<T> element, BlockPos container, boolean simulate) {
        return element.as(FluidStack.class)
                .filter(BaseElement::isNotEmpty)
                .map(elem -> sendElement(elem, container, simulate, mHelper))
                .orElseGet(element::cast)
                .cast();
    }

    @Override
    public void backElement(BaseElement<?> element) {
        element.as(FluidStack.class).ifPresent(elem -> sendElement(elem, pos, false, mHelper).drop(world, pos));
    }

    @Override
    public <T> BaseElement<T> receiveElement(IPipeline from, BaseElement<T> element, boolean simulate) {
        Optional<BaseElement<FluidStack>> optional = element.as(FluidStack.class).filter(elem -> !elem.isEmpty());
        if (optional.isPresent()) {
            if (simulate) {
                BaseElement<FluidStack> elem = optional.get();
                elem.moveToNextPos();
                addElement(elem);
            }
            return null;
        }
        return element;
    }

    @Override
    public int getKeepTime(BaseElement<?> element) {
        return 20;
    }

    @Override
    public <T> BaseElement<T> outputElement(BlockPos target, BaseElement<T> element, boolean simulate) {
        Optional<BaseElement<FluidStack>> fluidElementOpt = element.as(FluidStack.class);
        if (!fluidElementOpt.isPresent() || element.isEmpty() || world == null || world.isRemote) {
            return element;
        }
        BaseElement<FluidStack> fluidElement = fluidElementOpt.get();
        TileEntity te = world.getTileEntity(target);
        if (te == null) {
            return element;
        }
        Direction direction = BlockUtils.getPosFacing(pos, target);
        LazyOptional<IFluidHandler> optional = te.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, direction);
        if (!optional.isPresent()) {
            return element;
        }
        IFluidHandler handler = optional.orElseThrow(RuntimeException::new);
        FluidStack stack = fluidElement.get();
        int fill = handler.fill(stack, IFluidHandler.FluidAction.EXECUTE);
        return fluidElement.copy(new FluidStack(stack, stack.getAmount() - fill)).cast();
    }

}
