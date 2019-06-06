package com.elementtimes.tutorial.inventory.base;

import com.elementtimes.tutorial.common.capability.impl.RfEnergy;
import com.elementtimes.tutorial.common.tileentity.BaseMachine;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * @author KSGFK create in 2019/3/9
 */
public class ContainerMachine<T extends BaseMachine> extends Container {
    private T machine;

    public ContainerMachine(T tileEntity, EntityPlayer player) {
        this(tileEntity, player, 8, 74, 8, 132);
    }

    private ContainerMachine(T tileEntity, EntityPlayer player, int xOffsetA, int yOffsetA, int xOffsetB, int yOffsetB) {
        this.machine = tileEntity;
        int line = 3, slotCount = 9;

        for (int i = 0; i < line; ++i) {
            for (int j = 0; j < slotCount; ++j) {
                this.addSlotToContainer(new Slot(player.inventory, j + i * 9 + 9, xOffsetA + j * 18, yOffsetA + i * 18));
            }
        }
        for (int i = 0; i < slotCount; ++i) {
            this.addSlotToContainer(new Slot(player.inventory, i, xOffsetB + i * 18, yOffsetB));
        }

        Slot[] slots = tileEntity.getSlots();
        for (Slot slot : slots) {
            this.addSlotToContainer(slot);
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return playerIn.getDistanceSq(machine.getPos()) <= 64;
    }

    /**
     * 来源于 https://github.com/Yaossg/SausageCore
     */
    @Override
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
        int bagIndex = 27, inventoryIndex = 36;
        if (index < bagIndex) {
            // 背包
            isMerged = mergeItemStack(newStack, 36, total, false)
                    || mergeItemStack(newStack, 27, 36, false);
        } else if (index < inventoryIndex) {
            // 物品栏
            isMerged = mergeItemStack(newStack, 36, total, false)
                    || mergeItemStack(newStack, 0, 27, false);
        } else {
            // 36-total 机器
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

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();

        RfEnergy.EnergyProxy energyProxy = machine.getReadonlyEnergyProxy();
        int total = energyProxy.getMaxEnergyStored();
        short stored = total == 0 ? 0 : (short) (Short.MAX_VALUE * energyProxy.getEnergyStored() / total);
        total = machine.getEnergyUnprocessed() + machine.getEnergyProcessed();
        short processed = total == 0 ? 0 : (short) (Short.MAX_VALUE * machine.getEnergyProcessed() / total);

        listeners.forEach(listener -> {
            listener.sendWindowProperty(this, 0, stored);
            listener.sendWindowProperty(this, 1, processed);
        });
    }

    private short mEnergyStored = 0;
    private short mEnergyProcessed = 0;

    @SideOnly(Side.CLIENT)
    @Override
    public void updateProgressBar(int id, int data) {
        super.updateProgressBar(id, data);
        if (id == 0) {
            mEnergyStored = (short) data;
        } else if (id == 1) {
            mEnergyProcessed = (short) data;
        }
    }

    public short getEnergyProcessed() {
        return mEnergyProcessed;
    }

    public short getEnergyStored() {
        return mEnergyStored;
    }

    public GuiButton[] getButtons() {
        return machine.getButtons();
    }

    public String getName() {
        String localizedName = machine.getBlockType().getLocalizedName();
        if (machine.getWorld().isRemote) {
            return I18n.format(localizedName);
        } else {
            return localizedName;
        }
    }
}
