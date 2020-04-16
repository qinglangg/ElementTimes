package com.elementtimes.tutorial.common.tileentity.pipeline.item;

import com.elementtimes.tutorial.common.pipeline.BaseElement;
import com.elementtimes.tutorial.common.tileentity.pipeline.BaseTilePipeline;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

/**
 * 物品连接管道
 * 仅用于物品管道间的连接
 * @author luqin2007
 */
public class ItemConnectPipeline extends BaseTilePipeline implements IItemPipeline {

    @Override
    public boolean canConnectBy(BlockPos pos, EnumFacing direction) {
        if (world != null && !isInterrupted(pos.offset(direction), direction)) {
            return world.getTileEntity(pos) instanceof IItemPipeline;
        }
        return false;
    }

    @Override
    public boolean canReceive(BlockPos pos, BaseElement element) {
        return element.element instanceof ItemStack;
    }

    @Override
    public int getKeepTime(BaseElement element) {
        return 10;
    }
}
