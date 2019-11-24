package com.elementtimes.tutorial.common.tileentity.pipeline;

import com.elementtimes.tutorial.common.block.pipeline.BaseElement;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.FluidStack;

/**
 * @author 流体管道 TileEntity 基类
 */
public abstract class BaseFluidPipeline extends BaseTilePipeline {

    @Override
    protected boolean canConnectBy(BlockPos pos, EnumFacing direction) {
        if (world != null) {
            if (!isInterrupted(direction)) {
                return world.getTileEntity(pos) instanceof BaseTilePipeline;
            }
        }
        return false;
    }

    @Override
    public boolean canReceive(BlockPos pos, BaseElement element) {
        return element.element instanceof FluidStack;
    }
}
