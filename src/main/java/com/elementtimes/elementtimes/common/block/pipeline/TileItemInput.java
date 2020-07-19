package com.elementtimes.elementtimes.common.block.pipeline;

import com.elementtimes.elementcore.api.annotation.ModTileEntity;
import com.elementtimes.elementcore.api.annotation.part.Getter;
import com.elementtimes.elementtimes.common.init.blocks.Industry;
import com.elementtimes.elementtimes.common.pipeline.BaseElement;
import com.elementtimes.elementtimes.common.pipeline.ConnectType;
import com.elementtimes.elementtimes.common.pipeline.ItemElement;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.items.CapabilityItemHandler;

import java.util.Optional;



@ModTileEntity(blocks = @Getter(value = Industry.class, name = "pipelineItemInput"))
public class TileItemInput extends TileItemConnect {

    public TileItemInput(TileEntityType<TileItemInput> tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }

    @Override
    public boolean canInputElementFrom(BlockPos from) {
        return hasHandler(from, CapabilityItemHandler.ITEM_HANDLER_CAPABILITY);
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
            te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(handler -> {
                for (int i = 0; i < handler.getSlots(); i++) {
                    ItemStack extractItem = handler.extractItem(i, Integer.MAX_VALUE, true);
                    BaseElement<ItemStack> element = ItemElement.TYPE.newInstance(extractItem.copy());
                    BaseElement<ItemStack> send = sendElement(element, blockPos, false, mHelper);
                    Optional<ItemStack> stackOptional = send.get(ItemStack.class);
                    if (stackOptional.isPresent()) {
                        int count = extractItem.getCount() - stackOptional.get().getCount();
                        if (count > 0) {
                            handler.extractItem(i, count, false);
                            break;
                        }
                    }
                }
            });
        }
    }
}
