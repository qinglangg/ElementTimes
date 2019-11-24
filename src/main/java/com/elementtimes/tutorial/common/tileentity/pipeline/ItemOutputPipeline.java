package com.elementtimes.tutorial.common.tileentity.pipeline;

import com.elementtimes.elementcore.api.common.ECUtils;
import com.elementtimes.tutorial.common.block.pipeline.BaseElement;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;

/**
 * 物品输出管道
 * 用于将 ItemStack 从管道中输出
 * @author luqin2007
 */
public class ItemOutputPipeline extends BaseItemPipeline {

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
                return te.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, direction.getOpposite());
            }
        }
        return false;
    }

    @Override
    public boolean canOutput(BlockPos pos, BaseElement element) {
        if (world != null && element.element instanceof ItemStack) {
            EnumFacing facing = ECUtils.block.getPosFacing(this.pos, pos);
            TileEntity te = world.getTileEntity(pos);
            if (te != null) {
                IItemHandler handler = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, facing);
                if (handler != null) {
                    ItemStack stack = ItemHandlerHelper.insertItem(handler, (ItemStack) element.element, true);
                    return stack.getCount() < ((ItemStack) element.element).getCount();
                }
            }
        }
        return false;
    }

    @Override
    public BaseElement output(BlockPos pos, BaseElement element) {
        if (world != null && canOutput(pos, element)) {
            element.remove(pos);
            EnumFacing facing = ECUtils.block.getPosFacing(this.pos, pos);
            ItemStack stack = (ItemStack) element.element;
            TileEntity te = world.getTileEntity(pos);
            if (te != null) {
                IItemHandler handler = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, facing);
                if (handler != null) {
                    ItemStack back = ItemHandlerHelper.insertItem(handler, stack, false);
                    if (!back.isEmpty()) {
                        BaseElement backElement = element.copy();
                        backElement.element = back;
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
