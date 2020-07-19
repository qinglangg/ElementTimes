package com.elementtimes.elementtimes.common.pipeline;

import com.elementtimes.elementcore.api.utils.BlockUtils;
import com.elementtimes.elementcore.api.utils.path.EndResult;
import com.elementtimes.elementcore.api.utils.path.Path;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;



public class FluidPathHelper extends BasePipelinePathHelper<FluidStack> {

    private final TileEntity mTe;

    public FluidPathHelper(TileEntity te) {
        super(FluidStack.EMPTY);
        mTe = te;
    }

    @Override
    public int getElementCount(BaseElement<FluidStack> element) {
        return element.get(FluidStack.class).map(FluidStack::getAmount).orElse(0);
    }

    @Override
    public int getElementCount(FluidStack element) {
        return element.getAmount();
    }

    @Nullable
    @Override
    public IPipeline getPipeline(BlockPos node) {
        TileEntity tileEntity = mTe.getWorld().getTileEntity(node);
        return tileEntity instanceof IPipeline ? (IPipeline) tileEntity : null;
    }

    @Override
    public ElementList<FluidStack> getPathResult(BaseElement<FluidStack> element, List<Path<BlockPos, NodeInfo, PathInfo<FluidStack>>> allPaths) {
        FluidStack stack = element.get();
        allPaths.sort(ElementList.byTick());
        List<Path<BlockPos, NodeInfo, PathInfo<FluidStack>>> paths = new ArrayList<>();
        for (Path<BlockPos, NodeInfo, PathInfo<FluidStack>> path : allPaths) {
            FluidStack send = path.extra.stack;
            int count = send.getAmount();
            if (stack.getAmount() >= count) {
                stack.shrink(count);
                paths.add(path);
            }
            if (stack.isEmpty()) {
                break;
            }
        }
        return new ElementList<>(paths, element.copy(stack));
    }

    @Override
    public EndResult<PathInfo<FluidStack>> testAsEnd(BlockPos blockPos, BaseElement<FluidStack> element, BlockPos prevNode, @Nullable NodeInfo prevInfo) {
        TileEntity tileEntity = mTe.getWorld().getTileEntity(blockPos);
        if (!element.isEmpty() && tileEntity != null) {
            FluidStack stack = element.get(FluidStack.class).orElse(FluidStack.EMPTY);
            Direction direction = BlockUtils.getPosFacing(prevNode, blockPos);
            LazyOptional<IFluidHandler> optional = tileEntity.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, direction);
            if (optional.isPresent()) {
                int fill = optional.orElseThrow(RuntimeException::new).fill(stack, IFluidHandler.FluidAction.SIMULATE);
                int totalTick = prevInfo == null ? 0 : prevInfo.totalTick;
                FluidStack fluidStack = fill == 0 ? FluidStack.EMPTY : new FluidStack(stack, fill);
                return new EndResult<>(fill > 0, new PathInfo<>(totalTick, fluidStack));
            }
        }
        return new EndResult<>(false, new PathInfo<>(0, FluidStack.EMPTY));
    }
}
