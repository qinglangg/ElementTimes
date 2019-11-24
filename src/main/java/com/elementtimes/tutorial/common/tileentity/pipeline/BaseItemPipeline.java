package com.elementtimes.tutorial.common.tileentity.pipeline;

import com.elementtimes.tutorial.common.block.pipeline.BaseElement;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

/**
 * 物品提取管道，用于将物品输入到管道网络
 * @author luqin2007
 */
public abstract class BaseItemPipeline extends BaseTilePipeline {

    @Override
    protected boolean canConnectBy(BlockPos pos, EnumFacing direction) {
        if (world != null) {
            if (!isInterrupted(direction)) {
                return world.getTileEntity(pos) instanceof BaseItemPipeline;
            }
        }
        return false;
    }

    @Override
    public boolean canReceive(BlockPos pos, BaseElement element) {
        return element.element instanceof ItemStack;
    }
}
