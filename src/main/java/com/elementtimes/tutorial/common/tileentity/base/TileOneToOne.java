package com.elementtimes.tutorial.common.tileentity.base;

import com.elementtimes.tutorial.Elementtimes;
import com.elementtimes.tutorial.common.capability.RFEnergy;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

/**
 * @author KSGFK create in 2019/5/12
 */
public abstract class TileOneToOne extends TileMachine {

    private SlotItemHandler inputSlot = new SlotItemHandler(mItemHandlers.get(SideHandlerType.INPUT), 0, 56, 30) {
        @Override
        public boolean isItemValid(@Nonnull ItemStack stack) {
            return !getOutput(stack).isEmpty() && super.isItemValid(stack);
        }
    };

    private SlotItemHandler outputSlot = new SlotItemHandler(mItemHandlers.get(SideHandlerType.OUTPUT), 0, 110, 30) {
        @Override
        public boolean isItemValid(@Nonnull ItemStack stack) {
            return false;
        }
    };

    /**
     * 是否在处理
     */
    protected boolean isProc = false;

    /**
     * 处理进度
     */
    protected int schedule = 0;

    /**
     * 每次处理时间
     */
    protected int perTime = 0;

    /**
     * 每 tick 消耗能量
     */
    protected int rfConsumePerTick = 0;

    /**
     * 正在处理的物品
     */
    private ItemStack processItem = ItemStack.EMPTY;

    public TileOneToOne(int maxEnergy, int maxReceive) {
        super(maxEnergy, maxReceive, Integer.MAX_VALUE, new ItemStackHandler(1), new ItemStackHandler(1));

        for (EnumFacing facing : EnumFacing.values()) {
            if (mEnergyHandlerTypes.get(facing) == SideHandlerType.OUTPUT)
                mEnergyHandlerTypes.put(facing, SideHandlerType.NONE);
            else if (mEnergyHandlerTypes.get(facing) == SideHandlerType.IN_OUT)
                mEnergyHandlerTypes.put(facing, SideHandlerType.INPUT);
        }
    }

    @Override
    protected RFEnergy.EnergyProxy getEnergyProxy(EnumFacing facing) {
        if (mEnergyHandlerTypes.get(facing) == SideHandlerType.OUTPUT)
            return mEnergyHandler.new EnergyProxy(false, false);
        if (mEnergyHandlerTypes.get(facing) == SideHandlerType.IN_OUT)
            return mEnergyHandler.new EnergyProxy(true, false);
        return super.getEnergyProxy(facing);
    }

    @Override
    public void logic() {
        ItemStackHandler inputHandler = mItemHandlers.get(SideHandlerType.INPUT);
        ItemStackHandler outputHandler = mItemHandlers.get(SideHandlerType.OUTPUT);
        if (isProc) {
            // 检查输入是否变更
            ItemStack input = inputHandler.extractItem(0, 1, true);
            if (input.isEmpty() || !processItem.isItemEqual(input)) {
                stop();
                return;
            }
            // 检查能否完成
            if (schedule >= perTime) {
                ItemStack output = getOutput(input);
                ItemStack outputTest = outputHandler.insertItem(0, output, true);
                if (!outputTest.isEmpty()) return;
                // 处理完成
                inputHandler.extractItem(0, 1, false);
                outputHandler.insertItem(0, output, false);
                stop();
                return;
            }
            // 检查能量消耗
            if (mEnergyHandler.extractEnergy(rfConsumePerTick, true) < rfConsumePerTick) {
                isProc = false;
                return;
            }
            // 处理进度+1
            schedule++;
            mEnergyHandler.extractEnergy(rfConsumePerTick, false);
        } else {
            // 暂停：能源不足/从 NBT 恢复
            if (!processItem.isEmpty()) {
                if (mEnergyHandler.extractEnergy(rfConsumePerTick, true) < rfConsumePerTick) return;
                isProc = true;
                logic();
                return;
            }
            // 新任务
            ItemStack extract = inputHandler.extractItem(0, 1, true);
            if (extract.isEmpty()) return;
            ItemStack output = getOutput(extract);
            if (output.isEmpty()) return;
            schedule = 0;
            perTime = getTotalTime(extract);
            rfConsumePerTick = getEnergyConsumePerTick(extract);
            processItem = extract;
            isProc = true;
        }
    }

    private void stop() {
        isProc = false;
        schedule = 0;
        perTime = 0;
        rfConsumePerTick = 0;
        processItem = ItemStack.EMPTY;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        if (nbt.hasKey("schedule"))
            schedule = nbt.getInteger("schedule");
        if (nbt.hasKey("perTime"))
            perTime = nbt.getInteger("perTime");
        if (nbt.hasKey("rfConsume"))
            rfConsumePerTick = nbt.getInteger("rfConsume");
        if (nbt.hasKey("pItem"))
            processItem = new ItemStack(nbt.getCompoundTag("pItem"));
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        nbt.setInteger("schedule", schedule);
        nbt.setInteger("perTime", perTime);
        nbt.setInteger("rfConsume", rfConsumePerTick);
        nbt.setTag("pItem", processItem.serializeNBT());
        return super.writeToNBT(nbt);
    }

    @Override
    public Slot[] getSlots() {
        return new Slot[]{inputSlot, outputSlot};
    }

    protected abstract ItemStack getOutput(ItemStack input);

    /**
     * 获取处理总时间
     * @param input 被处理物品
     * @return 处理该物品所需总时间(tick)
     */
    protected abstract int getTotalTime(ItemStack input);

    /**
     * 获取处理物品所需能量
     * @param input 被处理物品
     * @return 处理该物品每 tick 所需能量(RF)
     */
    protected abstract int getEnergyConsumePerTick(ItemStack input);

    public int getPerTime() {
        return perTime;
    }

    public int getSchedule() {
        return schedule;
    }
}
