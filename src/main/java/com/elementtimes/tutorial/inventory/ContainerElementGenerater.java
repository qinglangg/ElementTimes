package com.elementtimes.tutorial.inventory;

import com.elementtimes.tutorial.common.tileentity.TileElementGenerater;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * @author KSGFK create in 2019/2/17
 */
public class ContainerElementGenerater extends Container {
    private Slot inputSlot;
    private TileElementGenerater generater;

    public ContainerElementGenerater(TileEntity tileEntity, EntityPlayer player) {
        super();
        this.generater = (TileElementGenerater) tileEntity;
        inputSlot = generater.getInputSlot();
        /*机器*/
        this.addSlotToContainer(inputSlot);
        /*玩家背包*/
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlotToContainer(new Slot(player.inventory, j + i * 9 + 9, 8 + j * 18, 74 + i * 18));
            }
        }
        for (int i = 0; i < 9; ++i) {
            this.addSlotToContainer(new Slot(player.inventory, i, 8 + i * 18, 132));
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return playerIn.getDistanceSq(this.generater.getPos()) <= 64;
    }

    @Nonnull
    @Override
    public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2) {
        ItemStack var3 = ItemStack.EMPTY;
        Slot var4 = this.inventorySlots.get(par2);
        if (var4 != null && var4.getHasStack()) {
            ItemStack var5 = var4.getStack();
            var3 = var5.copy();
            // 点击到Slot的ID为0的时候，将物品送回玩家的背包中
            if (par2 == 0) {
                if (!this.mergeItemStack(var5, 1, 28, false)) {
                    return ItemStack.EMPTY;
                }
                var4.onSlotChange(var5, var3);
            }
            // 点击到玩家的背包的时候将物品送到玩家的快捷栏中
            else if (par2 > 0 && par2 < 28) {
                if (!this.mergeItemStack(var5, 28, 37, false)) {
                    return ItemStack.EMPTY;
                }
            }
            // 点击到玩家的快捷栏的时候将物品送到背包中
            else if (par2 >= 28 && par2 < 37) {
                if (!this.mergeItemStack(var5, 1, 28, false)) {
                    return ItemStack.EMPTY;
                }
            }
            if (var5.getMaxStackSize() == 0) {
                var4.putStack(ItemStack.EMPTY);
            } else {
                var4.onSlotChanged();
            }
            if (var5.getMaxStackSize() == var3.getMaxStackSize()) {
                return ItemStack.EMPTY;
            }
            var4.onTake(par1EntityPlayer, var5);
        }
        return var3;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void setAll(List<ItemStack> stacks) {
        for (int i = 0; i < stacks.size(); ++i) {
            putStackInSlot(i, stacks.get(i));
        }
    }

    @Override
    public void onContainerClosed(EntityPlayer playerIn) {
        generater.setOpenGui(false);
        generater.setInputSlot(inputSlot);
    }

    public TileElementGenerater getGenerater() {
        return generater;
    }
}
