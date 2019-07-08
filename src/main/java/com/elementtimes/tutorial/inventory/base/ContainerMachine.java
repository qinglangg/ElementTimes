package com.elementtimes.tutorial.inventory.base;

import com.elementtimes.tutorial.common.capability.impl.RfEnergy;
import com.elementtimes.tutorial.common.init.ElementtimesGUI;
import com.elementtimes.tutorial.common.tileentity.BaseMachine;
import com.elementtimes.tutorial.other.SideHandlerType;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.lang3.tuple.ImmutablePair;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

/**
 * 一个机器的 Container
 * @author KSGFK create in 2019/3/9
 */
public class ContainerMachine extends Container {
    private BaseMachine machine;
    private int width, height;

    public static Map<SideHandlerType, Int2ObjectMap<ImmutablePair<FluidStack, Integer>>> FLUIDS = new HashMap<>();
    public static ElementtimesGUI.Machines MACHINE = null;

    public static ContainerMachine cm176_156_74(BaseMachine tileEntity, EntityPlayer player) {
        return new ContainerMachine(tileEntity, player, 176, 156, 74);
    }

    public static ContainerMachine cm176_166_84(BaseMachine tileEntity, EntityPlayer player) {
        return new ContainerMachine(tileEntity, player, 176, 166, 84);
    }

    public static ContainerMachine cm176_204_122(BaseMachine tileEntity, EntityPlayer player) {
        return new ContainerMachine(tileEntity, player, 176, 204, 122);
    }

    public ContainerMachine(BaseMachine tileEntity, EntityPlayer player, int width, int height, int offsetY) {
        this(tileEntity, player, width, height, 8, offsetY);
    }

    public ContainerMachine(BaseMachine tileEntity, EntityPlayer player, int width, int height, int offsetX, int offsetY) {
        machine = tileEntity;
        this.width = width;
        this.height = height;

        int line = 3, slotCount = 9;

        for (int i = 0; i < line; ++i) {
            for (int j = 0; j < slotCount; ++j) {
                this.addSlotToContainer(new Slot(player.inventory, j + i * 9 + 9, offsetX + j * 18, offsetY + i * 18));
            }
        }
        for (int i = 0; i < slotCount; ++i) {
            this.addSlotToContainer(new Slot(player.inventory, i, offsetX + i * 18, offsetY + 58));
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
    @Nonnull
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
        float totalEnergy = Math.abs(energyProxy.getMaxEnergyStored());
        int stored = totalEnergy == 0 ? 0 : (int) (Short.MAX_VALUE * (energyProxy.getEnergyStored() / totalEnergy));
        float totalProcessed = Math.abs(machine.getEnergyUnprocessed() + machine.getEnergyProcessed());
        int processed = totalProcessed == 0 ? 0 : (int) (Short.MAX_VALUE * (machine.getEnergyProcessed() / totalProcessed));

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

    /**
     * 获取已处理的能量（比例缩放），范围为 0-Short.MAX_VALUE，仅用于确定进度
     * @return 已处理能量（按比例缩放）
     */
    public short getEnergyProcessed() {
        return mEnergyProcessed;
    }

    /**
     * 获取能量缓存（比例缩放），范围为 0-Short.MAX_VALUE，仅用于缓存充满程度
     * @return 能量缓存（按比例缩放）
     */
    public short getEnergyStored() {
        return mEnergyStored;
    }

    /**
     * 获取该 GUI 标题，默认为所使用机器名
     * @return 该 GUI 标题，服务器获得则为 getLocalizedName 值
     */
    public String getName() {
        String localizedName = machine.getBlockType().getLocalizedName();
        if (machine.getWorld().isRemote) {
            return I18n.format(localizedName);
        }
		return localizedName;
    }

    public Map<SideHandlerType, Int2ObjectMap<int[]>> getFluidPositions() {
        return machine.getFluids();
    }

    @Override
    public void onContainerClosed(EntityPlayer playerIn) {
        super.onContainerClosed(playerIn);
        if (playerIn instanceof EntityPlayerMP) {
            machine.getOpenedPlayers().remove(playerIn);
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
