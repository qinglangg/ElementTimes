package com.elementtimes.elementtimes.common.block.pipeline;

import com.elementtimes.elementcore.api.annotation.ModTileEntity;
import com.elementtimes.elementcore.api.annotation.part.Getter;
import com.elementtimes.elementcore.api.utils.BlockUtils;
import com.elementtimes.elementtimes.common.init.blocks.Industry;
import com.elementtimes.elementtimes.common.pipeline.*;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;

import java.util.Optional;



@ModTileEntity(blocks = @Getter(value = Industry.class, name = "pipelineItemConnect"))
public class TileItemConnect extends BaseTilePipeline implements SymbolItemPipeline {

    protected ItemPathHelper mHelper = new ItemPathHelper(this);

    public TileItemConnect(TileEntityType<? extends TileItemConnect> tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }

    @Override
    public boolean connectedBy(IPipeline from, IConnectType type, boolean simulate) {
        if (from instanceof SymbolItemPipeline) {
            return super.connectedBy(from, type, simulate);
        }
        return false;
    }

    @Override
    public <T> BaseElement<T> receiveElement(IPipeline from, BaseElement<T> element, boolean simulate) {
        Optional<BaseElement<ItemStack>> optional = element.as(ItemStack.class).filter(elem -> !elem.isEmpty());
        if (optional.isPresent()) {
            if (simulate) {
                BaseElement<ItemStack> elem = optional.get();
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
        if (!element.is(ItemStack.class) || element.isEmpty() || world == null || world.isRemote) {
            return element;
        }
        TileEntity te = world.getTileEntity(target);
        if (te == null) {
            return element;
        }
        Direction direction = BlockUtils.getPosFacing(pos, target);
        LazyOptional<IItemHandler> optional = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, direction);
        if (!optional.isPresent()) {
            return element;
        }
        IItemHandler handler = optional.orElseThrow(RuntimeException::new);
        BaseElement<ItemStack> itemElement = element.as(ItemStack.class).orElse(ItemElement.EMPTY);
        ItemStack insertItem = ItemHandlerHelper.insertItem(handler, itemElement.get(), false);
        return itemElement.copy(insertItem).cast();
    }

    @Override
    public <T> BaseElement<T> sendElement(BaseElement<T> element, BlockPos container, boolean simulate) {
        return element.as(ItemStack.class)
                .filter(BaseElement::isNotEmpty)
                .map(elem -> sendElement(elem, container, simulate, mHelper))
                .orElseGet(element::cast)
                .cast();
    }

    @Override
    public void backElement(BaseElement<?> element) {
        element.as(ItemStack.class).ifPresent(elem -> sendElement(elem, pos, false, mHelper).drop(world, pos));
    }
}
