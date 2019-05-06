package com.elementtimes.tutorial.common.tileentity;

import com.elementtimes.tutorial.Elementtimes;
import com.elementtimes.tutorial.network.PulMsg;
import com.elementtimes.tutorial.util.PowderDictionary;
import com.elementtimes.tutorial.util.RedStoneEnergy;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;
import java.util.Map;

/**
 * @author KSGFK create in 2019/5/6
 */
public class TilePulverizer extends TileMachine {
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
    private int perTime = 200;
    /**
     * 标记正在处理的是啥矿石
     */
    private ItemStack procItem = ItemStack.EMPTY;

    public TilePulverizer() {
        super(new RedStoneEnergy(320000, 20, 20), new ItemStackHandler(2));

        canInItemMap = PowderDictionary.getInstance().getPulCanInItemMap();
        powderLinkOre = PowderDictionary.getInstance().getPulPowderLinkOre();
    }

    @Override
    public void update() {
        if (!world.isRemote) {
            int itemCount = inputSlot.getStack().getCount();
            if (isProc) {
                if (schedule > 0) {//没有处理完，先扣电
                    if (storage.canExtract()) {
                        int test = storage.extractEnergy(storage.getMaxExtract(), true);
                        if (test > 0) {
                            storage.extractEnergy(test, false);
                            schedule -= test;
                        }
                    }
                } else {//必须处理完才能替换out
                    //Elementtimes.getLogger().info(outputSlot.getHasStack());
                    if (outputSlot.getHasStack()) {//out里有东西了
                        Item out = outputSlot.getStack().getItem();
                        if (powderLinkOre.containsKey(out) && powderLinkOre.get(out).containsKey(procItem.getItem())) {
                            //out里的东西和粉对应的矿石的表一致
                            int now = outputSlot.getStack().getCount();
                            if (now + 2 <= outputSlot.getSlotStackLimit()) {
                                outputSlot.getStack().setCount(now + 2);
                                isProc = false;
                                procItem = ItemStack.EMPTY;
                            }
                        }
                    } else {
                        if (canInItemMap.containsKey(procItem.getItem())) {
                            outputSlot.putStack(new ItemStack(canInItemMap.get(procItem.getItem()), 2));
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
                        schedule = perTime;
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
}
