package com.elementtimes.tutorial.common.tileentity.pipeline.multiply;

import com.elementtimes.tutorial.common.pipeline.BaseElement;
import com.elementtimes.tutorial.common.tileentity.pipeline.BaseTilePipeline;
import com.elementtimes.tutorial.common.tileentity.pipeline.fluid.IFluidPipeline;
import com.elementtimes.tutorial.common.tileentity.pipeline.item.IItemPipeline;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.FluidStack;

/**
 * 物品连接管道
 * 仅用于物品管道间的连接
 * @author luqin2007
 */
public class MultiplyConnectPipeline extends BaseTilePipeline implements IItemPipeline, IFluidPipeline {

    @Override
    public boolean canConnectBy(BlockPos pos, EnumFacing direction) {
        if (world != null && !isInterrupted(pos.offset(direction), direction)) {
            TileEntity te = world.getTileEntity(pos);
            return te instanceof IItemPipeline || te instanceof IFluidPipeline;
        }
        return false;
    }

    @Override
    public boolean canReceive(BlockPos pos, BaseElement element) {
        return element.element instanceof ItemStack || element.element instanceof FluidStack;
    }

    @Override
    public int getKeepTime(BaseElement element) {
        return 10;
    }
}
