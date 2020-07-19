package com.elementtimes.elementtimes.common.pipeline;

import com.elementtimes.elementcore.api.utils.BlockUtils;
import com.elementtimes.elementcore.api.utils.path.EndResult;
import com.elementtimes.elementcore.api.utils.path.Path;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;



public class ItemPathHelper extends BasePipelinePathHelper<ItemStack> {

    private final TileEntity mTe;

    public ItemPathHelper(TileEntity te) {
        super(ItemStack.EMPTY);
        mTe = te;
    }

    @Override
    public int getElementCount(BaseElement<ItemStack> element) {
        return element.get(ItemStack.class).map(ItemStack::getCount).orElse(0);
    }

    @Override
    public int getElementCount(ItemStack element) {
        return element.getCount();
    }

    @Nullable
    @Override
    public IPipeline getPipeline(BlockPos node) {
        TileEntity tileEntity = mTe.getWorld().getTileEntity(node);
        return tileEntity instanceof IPipeline ? (IPipeline) tileEntity : null;
    }

    @Override
    public ElementList<ItemStack> getPathResult(BaseElement<ItemStack> element, List<Path<BlockPos, NodeInfo, PathInfo<ItemStack>>> allPaths) {
        ItemStack stack = element.get();
        allPaths.sort(ElementList.byTick());
        List<Path<BlockPos, NodeInfo, PathInfo<ItemStack>>> paths = new ArrayList<>();
        for (Path<BlockPos, NodeInfo, PathInfo<ItemStack>> path : allPaths) {
            ItemStack send = path.extra.stack;
            int count = send.getCount();
            if (stack.getCount() >= count) {
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
    public EndResult<PathInfo<ItemStack>> testAsEnd(BlockPos blockPos, BaseElement<ItemStack> element, BlockPos prevNode, @Nullable NodeInfo prevInfo) {
        TileEntity tileEntity = mTe.getWorld().getTileEntity(blockPos);
        if (!element.isEmpty() && tileEntity != null) {
            ItemStack stack = element.get(ItemStack.class).orElse(ItemStack.EMPTY);
            Direction direction = BlockUtils.getPosFacing(prevNode, blockPos);
            LazyOptional<IItemHandler> optional = tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, direction);
            if (optional.isPresent()) {
                ItemStack insertItem = ItemHandlerHelper.insertItem(optional.orElseThrow(RuntimeException::new), stack, true);
                boolean isInsert = insertItem.getCount() < stack.getCount();
                return new EndResult<>(isInsert, new PathInfo<ItemStack>(prevInfo == null ? 0 : prevInfo.totalTick, insertItem));
            }
        }
        return new EndResult<>(false, new PathInfo<>(0, ItemStack.EMPTY));
    }
}
