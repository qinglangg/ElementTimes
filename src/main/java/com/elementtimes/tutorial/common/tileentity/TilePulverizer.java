package com.elementtimes.tutorial.common.tileentity;

import com.elementtimes.tutorial.Elementtimes;
import com.elementtimes.tutorial.config.ElementtimesConfig;
import com.elementtimes.tutorial.network.PulMsg;
import com.elementtimes.tutorial.util.PowderDictionary;
import com.elementtimes.tutorial.util.RedStoneEnergy;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;
import java.util.Map;

/**
 * @author KSGFK create in 2019/5/6
 */
public class TilePulverizer extends TileMachine implements ISidedInventory {
    private SlotItemHandler inputSlot = new SlotItemHandler(items, 0, 56, 30) {
        @Override
        public boolean isItemValid(@Nonnull ItemStack stack) {
            return canInItemMap.containsKey(stack.getItem()) && super.isItemValid(stack);
        }
    };
    private SlotItemHandler outputSlot = new SlotItemHandler(items, 1, 110, 30) {
        @Override
        public boolean isItemValid(@Nonnull ItemStack stack) {
            return false;
        }
    };
    /**
     * key是放进来的物品,value是处理完后的物品
     */
    private Map<Item, Item> canInItemMap;
    /**
     * key是处理完后的,value是可能放进来的东西表
     */
    private Map<Item, Map<Item, String>> powderLinkOre;

    /**
     * 是否在处理一个矿石
     */
    private boolean isProc = false;
    /**
     * 处理进度
     */
    private int schedule = 0;
    /**
     * 每个矿石的处理时间
     * TODO:我猜卿岚还要求每个矿石处理时间不一样:(
     */
    private int perTime = ElementtimesConfig.pul.pulPowderEnergy;
    /**
     * 标记正在处理的是啥矿石
     */
    private ItemStack procItem = ItemStack.EMPTY;

    public TilePulverizer() {
        super(new RedStoneEnergy(ElementtimesConfig.pul.pulMaxEnergy, ElementtimesConfig.pul.pulMaxReceive, ElementtimesConfig.pul.pulMaxExtract), new ItemStackHandler(2));

        canInItemMap = PowderDictionary.getInstance().getPulCanInItemMap();
        powderLinkOre = PowderDictionary.getInstance().getPulPowderLinkOre();
    }

    @Override
    public void update() {
        if (!world.isRemote) {
            int itemCount = inputSlot.getStack().getCount();
            if (isProc) {
                if (schedule <= perTime) {//没有处理完，先扣电
                    if (storage.canExtract()) {
                        int test = storage.extractEnergy(storage.getMaxExtract(), true);
                        if (test <= perTime) {
                            storage.extractEnergy(test, false);
                            schedule += test;
                        }
                    }
                } else {//必须处理完才能替换out
                    if (outputSlot.getHasStack()) {//out里有东西了
                        Item out = outputSlot.getStack().getItem();
                        if (powderLinkOre.containsKey(out) && powderLinkOre.get(out).containsKey(procItem.getItem())) {
                            //out里的东西和粉对应的矿石的表一致
                            int now = outputSlot.getStack().getCount();
                            if (now + ElementtimesConfig.pul.pulPowderCount <= outputSlot.getSlotStackLimit()) {
                                outputSlot.getStack().setCount(now + ElementtimesConfig.pul.pulPowderCount);
                                isProc = false;
                                procItem = ItemStack.EMPTY;
                            }
                        }
                    } else {
                        if (canInItemMap.containsKey(procItem.getItem())) {
                            outputSlot.putStack(new ItemStack(canInItemMap.get(procItem.getItem()), ElementtimesConfig.pul.pulPowderCount));
                            isProc = false;
                            procItem = ItemStack.EMPTY;
                        }
                    }
                }
            } else {
                if (inputSlot.getHasStack()) {
                    if (itemCount > 0) {
                        procItem = inputSlot.getStack().copy();
                        inputSlot.getStack().setCount(itemCount - 1);
                        isProc = true;
                        schedule = 0;
                    }
                }
            }
        }

        if (isOpenGui) {
            Elementtimes.getNetwork().sendTo(new PulMsg(storage.getEnergyStored(), storage.getMaxEnergyStored(), schedule), player);
        }
    }

    public SlotItemHandler getInputSlot() {
        return inputSlot;
    }

    public SlotItemHandler getOutputSlot() {
        return outputSlot;
    }

    public int getPerTime() {
        return perTime;
    }

    //实现ISideInventory
    private int[] temp = new int[]{0, 1};

    @Override
    public int[] getSlotsForFace(EnumFacing side) {
        return this.temp;
    }

    @Override
    public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction) {
        return this.inputSlot.isItemValid(itemStackIn);
    }

    @Override
    public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
        return this.outputSlot.isItemValid(stack);
    }

    @Override
    public int getSizeInventory() {
        return this.items.getSlots();
    }

    @Override
    public boolean isEmpty() {
        return this.inputSlot.inventory.isEmpty();
    }

    @Override
    public ItemStack getStackInSlot(int index) {
        return items.getStackInSlot(index);
    }

    @Override
    public ItemStack decrStackSize(int index, int count) {
        ItemStack i;

        switch (index) {
            case 0:
                i = inputSlot.inventory.decrStackSize(index, count);
                break;
            case 1:
                i = outputSlot.inventory.decrStackSize(index, count);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + index);
        }

        return i;
    }

    @Override
    public ItemStack removeStackFromSlot(int index) {
        ItemStack i = this.items.getStackInSlot(index);
        this.items.setStackInSlot(index, ItemStack.EMPTY);
        return i;
    }

    @Override
    public void setInventorySlotContents(int index, @Nonnull ItemStack stack) {
        this.items.setStackInSlot(index, stack);
    }

    @Override
    public int getInventoryStackLimit() {
        return this.inputSlot.inventory.getInventoryStackLimit();
    }

    @Override
    public boolean isUsableByPlayer(EntityPlayer player) {
        return this.inputSlot.inventory.isUsableByPlayer(player);
    }

    @Override
    public void openInventory(EntityPlayer player) {

    }

    @Override
    public void closeInventory(EntityPlayer player) {

    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        boolean b;

        switch (index) {
            case 0:
                b = this.inputSlot.isItemValid(stack);
                break;
            case 1:
                b = this.outputSlot.isItemValid(stack);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + index);
        }

        return b;
    }

    @Override
    public int getField(int id) {
        return 0;
    }

    @Override
    public void setField(int id, int value) {

    }

    @Override
    public int getFieldCount() {
        return 0;
    }

    @Override
    public void clear() {
        this.inputSlot.inventory.clear();
        this.outputSlot.inventory.clear();
    }

    @Override
    public String getName() {
        return "233";
    }

    @Override
    public boolean hasCustomName() {
        return false;
    }
}
