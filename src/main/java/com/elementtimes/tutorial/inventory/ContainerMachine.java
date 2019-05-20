package com.elementtimes.tutorial.inventory;

import com.elementtimes.tutorial.common.tileentity.TileMachine;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

/**
 * @author KSGFK create in 2019/3/9
 */
public abstract class ContainerMachine<T extends TileMachine> extends Container {
    public final T tileEntity;

    @SuppressWarnings("unchecked")
    public ContainerMachine(TileEntity tileEntity) {
        this.tileEntity = (T) tileEntity;
    }

    ContainerMachine(T tileEntity, EntityPlayer player) {
        this.tileEntity = (T) tileEntity;
        for (int i = 0; i < 3; ++i)
            for (int j = 0; j < 9; ++j)
                this.addSlotToContainer(new Slot(player.inventory, j + i * 9 + 9, 8 + j * 18, 74 + i * 18));
        for (int i = 0; i < 9; ++i)
            this.addSlotToContainer(new Slot(player.inventory, i, 8 + i * 18, 132));
    }

    ContainerMachine(T tileEntity, EntityPlayer player, int xOffsetA, int yOffsetA, int xOffsetB, int yOffsetB) {
        this.tileEntity = (T) tileEntity;
        for (int i = 0; i < 3; ++i)
            for (int j = 0; j < 9; ++j)
                this.addSlotToContainer(new Slot(player.inventory, j + i * 9 + 9, xOffsetA + j * 18, yOffsetA + i * 18));
        for (int i = 0; i < 9; ++i)
            this.addSlotToContainer(new Slot(player.inventory, i, xOffsetB + i * 18, yOffsetB));
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return playerIn.getDistanceSq(tileEntity.getPos()) <= 64;
    }

    @Override//来源于https://github.com/Yaossg/SausageCore的开源代码
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
        Slot slot = inventorySlots.get(index);
        if (slot == null || !slot.getHasStack()) {
            return ItemStack.EMPTY;
        }
        ItemStack newStack = slot.getStack(), oldStack = newStack.copy();
        boolean isMerged;
//        int length = inventorySlots.size() - 36;
//        if (index < length) {
//            isMerged = mergeItemStack(newStack, length, 36 + length, true);
//        } else if (index < 27 + length) {
//            isMerged = mergeItemStack(newStack, 0, length, false) || mergeItemStack(newStack, 27 + length, 36 + length, false);
//        } else {
//            isMerged = mergeItemStack(newStack, 0, length, false) || mergeItemStack(newStack, length, 27 + length, false);
//        }
        int total = inventorySlots.size();
        // fix: Shift 转移问题
        // 按执行顺序先添加的是 player 的物品槽，Yaossg 用的应该是 4z 的那套方法，显然在这里判断槽位对应错了
        if (index < 27) { // 0-26 背包
            isMerged = mergeItemStack(newStack, 36, total, false)
                    || mergeItemStack(newStack, 27, 36, false);
        } else if (index < 36) { // 27-35 物品栏
            isMerged = mergeItemStack(newStack, 36, total, false)
                    || mergeItemStack(newStack, 0, 27, false);
        } else { // 36-total 机器
            isMerged = mergeItemStack(newStack, 0, 36, true);
        }
        if (!isMerged) {
            return ItemStack.EMPTY;
        }
        if (newStack.isEmpty()) {
            slot.putStack(ItemStack.EMPTY);
        } else {
            slot.onSlotChanged();
        }
        return oldStack;
    }

    @Override//从原版复制
    protected boolean mergeItemStack(ItemStack stack, int startIndex, int endIndex, boolean reverseDirection) {
        boolean changed = false;
        int i = reverseDirection ? endIndex - 1 : startIndex;
        if(stack.isStackable()) {
            while (!stack.isEmpty()) {
                if(reverseDirection && i < startIndex) break;
                else if(i >= endIndex) break;
                Slot slot = this.inventorySlots.get(i);
                ItemStack itemstack = slot.getStack();
                if(slot.isItemValid(itemstack)
                        && !itemstack.isEmpty()
                        && itemstack.getItem() == stack.getItem()
                        && (!stack.getHasSubtypes() || stack.getMetadata() == itemstack.getMetadata())
                        && ItemStack.areItemStackTagsEqual(stack, itemstack)) {
                    int j = itemstack.getCount() + stack.getCount();
                    int maxSize = Math.min(slot.getSlotStackLimit(), stack.getMaxStackSize());
                    if(j <= maxSize) {
                        stack.setCount(0);
                        itemstack.setCount(j);
                        slot.onSlotChanged();
                        changed = true;
                    } else if(itemstack.getCount() < maxSize) {
                        stack.shrink(maxSize - itemstack.getCount());
                        itemstack.setCount(maxSize);
                        slot.onSlotChanged();
                        changed = true;
                    }
                }
                i += reverseDirection ? -1 : 1;
            }
        }
        if(!stack.isEmpty()) {
            i = reverseDirection ? endIndex - 1 : startIndex;
            while (true) {
                if(reverseDirection && i < startIndex) break;
                else if(i >= endIndex) break;
                Slot slot = this.inventorySlots.get(i);
                ItemStack itemstack = slot.getStack();
                if(itemstack.isEmpty() && slot.isItemValid(stack)) {
                    if(stack.getCount() > slot.getSlotStackLimit())
                        slot.putStack(stack.splitStack(slot.getItemStackLimit(stack)));
                    else slot.putStack(stack.splitStack(stack.getCount()));
                    slot.onSlotChanged();
                    changed = true;
                    break;
                }
                i += reverseDirection ? -1 : 1;
            }
        }
        return changed;
    }

    @Override
    public void onContainerClosed(EntityPlayer playerIn) {
        tileEntity.setOpenGui(false);
    }

    public T getTileEntity() {
        return tileEntity;
    }
}
