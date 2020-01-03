package com.elementtimes.tutorial.common.tileentity.pipeline.item;

import com.elementtimes.elementcore.api.common.ECUtils;
import com.elementtimes.tutorial.common.pipeline.BaseElement;
import com.elementtimes.tutorial.common.pipeline.test.PLTestNetwork;
import com.elementtimes.tutorial.interfaces.ITilePipelineOutput;
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
public class ItemOutputPipeline extends ItemIOPipeline implements ITilePipelineOutput {

    @Override
    public BaseElement output(BaseElement element) {
        if (world != null && !world.isRemote && isConnectedIO()) {
            EnumFacing ioSide = EnumFacing.VALUES[readByteValue(12, 3)];
            BlockPos pos = this.pos.offset(ioSide);
            EnumFacing facing = ECUtils.block.getPosFacing(this.pos, pos);
            ItemStack stack = (ItemStack) element.element;
            TileEntity te = world.getTileEntity(pos);
            if (te != null) {
                IItemHandler handler = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, facing);
                if (handler != null) {
                    PLTestNetwork.sendMessage(PLTestNetwork.TestElementType.output, element, pos);
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
            return element;
        }
        return null;
    }

    @Override
    public boolean canOutput(BaseElement element) {
        if (world != null && !world.isRemote && isConnectedIO()) {
            EnumFacing ioSide = EnumFacing.VALUES[readByteValue(12, 3)];
            TileEntity te = world.getTileEntity(pos.offset(ioSide));
            if (te != null) {
                IItemHandler capability = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, ioSide.getOpposite());
                ItemStack stack = ItemHandlerHelper.insertItem(capability, element.get(ItemStack.class), false);
                return stack.isEmpty() || stack.getCount() < element.get(ItemStack.class).getCount();
            }
        }
        return false;
    }
}
