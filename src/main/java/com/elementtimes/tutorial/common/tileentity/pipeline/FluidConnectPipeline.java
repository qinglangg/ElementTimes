package com.elementtimes.tutorial.common.tileentity.pipeline;

import com.elementtimes.tutorial.common.block.pipeline.BaseElement;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

/**
 * 物品连接管道
 * 仅用于物品管道间的连接
 * @author luqin2007
 */
public class FluidConnectPipeline extends BaseFluidPipeline {

    @Override
    public boolean canConnectTo(EnumFacing direction) {
        if (world != null) {
            if (isInterrupted(direction)) {
                return false;
            }
            TileEntity te = world.getTileEntity(pos.offset(direction));
            if (te != null) {
                if (te instanceof BaseTilePipeline) {
                    return ((BaseTilePipeline) te).canConnectBy(pos, direction.getOpposite());
                }
            }
        }
        return false;
    }

    @Override
    public boolean canOutput(BlockPos pos, BaseElement element) {
        return false;
    }

    @Override
    public BaseElement output(BlockPos pos, BaseElement element) {
        return element;
    }

    @Override
    public int getKeepTime(BaseElement element) {
        return 10;
    }
}
