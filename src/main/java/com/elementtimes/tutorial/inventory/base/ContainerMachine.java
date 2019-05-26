package com.elementtimes.tutorial.inventory.base;

import com.elementtimes.tutorial.common.tileentity.base.TileMachine;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;

/**
 * @author KSGFK create in 2019/3/9
 */
public class ContainerMachine<T extends TileMachine> extends Container {
    private BlockPos pos;

    public ContainerMachine(T tileEntity, EntityPlayer player) {
        this(tileEntity, player, 8, 74, 8, 132);
    }

    private ContainerMachine(T tileEntity, EntityPlayer player, int xOffsetA, int yOffsetA, int xOffsetB, int yOffsetB) {
        this.pos = tileEntity.getPos();
        for (int i = 0; i < 3; ++i)
            for (int j = 0; j < 9; ++j)
                this.addSlotToContainer(new Slot(player.inventory, j + i * 9 + 9, xOffsetA + j * 18, yOffsetA + i * 18));
        for (int i = 0; i < 9; ++i)
            this.addSlotToContainer(new Slot(player.inventory, i, xOffsetB + i * 18, yOffsetB));
        if (tileEntity != null) {
            Slot[] slots = tileEntity.getSlots();
            for (Slot slot : slots) {
                this.addSlotToContainer(slot);
            }
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return playerIn.getDistanceSq(pos) <= 64;
    }

    @Override//来源于https://github.com/Yaossg/SausageCore的开源代码
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
        Slot slot = inventorySlots.get(index);
        if (slot == null || !slot.getHasStack()) {
            return ItemStack.EMPTY;
        }
        ItemStack newStack = slot.getStack(), oldStack = newStack.copy();
        boolean isMerged;
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

    public BlockPos getPos() {
        return pos;
    }
}
