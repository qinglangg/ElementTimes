package com.elementtimes.tutorial.common.tileentity.base;

import com.elementtimes.tutorial.util.RedStoneEnergy;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

/**
 * @author KSGFK create in 2019/5/12
 */
public abstract class TileOneToOne extends TileMachine {
    private SlotItemHandler inputSlot = new SlotItemHandler(mHandlers.get(ItemHandlerType.INPUT), 0, 56, 30) {
        @Override
        public boolean isItemValid(@Nonnull ItemStack stack) {
            return !getOutput(stack).isEmpty() && super.isItemValid(stack);
        }
    };
    private SlotItemHandler outputSlot = new SlotItemHandler(mHandlers.get(ItemHandlerType.OUTPUT), 0, 110, 30) {
        @Override
        public boolean isItemValid(@Nonnull ItemStack stack) {
            return false;
        }
    };

    /**
     * 是否在处理
     */
    private boolean isProc = false;

    /**
     * 处理进度
     */
    private int schedule = 0;

    /**
     * 每次处理时间
     */
    private int perTime;

    public TileOneToOne(int maxEnergy, int maxReceive, int maxExtract, int perTime) {
        super(new RedStoneEnergy(maxEnergy, maxReceive, maxExtract),
                new ItemStackHandler(1),
                new ItemStackHandler(1));
        this.perTime = perTime;
    }

    @Override
    public void logic() {
        ItemStackHandler inputHandler = mHandlers.get(ItemHandlerType.INPUT);
        ItemStackHandler outputHandler = mHandlers.get(ItemHandlerType.OUTPUT);
        if (isProc) {
            if (schedule <= perTime) {
                if (storage.canExtract()) {
                    int test = storage.extractEnergy(storage.getMaxExtract(), true);
                    if (test <= perTime) {
                        storage.extractEnergy(test, false);
                        schedule += test;
                    }
                }
            } else {
                ItemStack extract = inputHandler.extractItem(0, 1, true);
                if (extract.isEmpty()) return;
                ItemStack output = getOutput(extract);
                if (output.isEmpty()) return;
                ItemStack insert = outputHandler.insertItem(0, output, true);
                if (!insert.isEmpty()) return;

                inputHandler.extractItem(0, 1, false);
                outputHandler.insertItem(0, output, false);
                isProc = false;
            }
        } else {
            ItemStack extract = inputHandler.extractItem(0, 1, true);
            if (extract.isEmpty()) return;
            ItemStack output = getOutput(extract);
            if (output.isEmpty()) return;

            inputHandler.extractItem(0, 1, false);
            isProc = true;
            schedule = 0;
        }
    }

    @Override
    public void update() {
        super.update();
        onUpdate(isProc, schedule, perTime);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        if (nbt.hasKey("schedule"))
            schedule = nbt.getInteger("schedule");
        if (nbt.hasKey("perTime"))
            perTime = nbt.getInteger("perTime");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        nbt.setInteger("schedule", schedule);
        nbt.setInteger("perTime", perTime);
        return super.writeToNBT(nbt);
    }

    @Override
    public Slot[] getSlots() {
        return new Slot[]{inputSlot, outputSlot};
    }

    protected abstract ItemStack getOutput(ItemStack input);

    public int getPerTime() {
        return perTime;
    }

    public int getSchedule() {
        return schedule;
    }

    /**
     * 每次 update 后调用
     * @param isProc 机器是否正在处理
     * @param schedule 当前已处理时间
     * @param perTime 处理该物品需要总时间
     */
    protected abstract void onUpdate(boolean isProc, int schedule, int perTime);
}
