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

    private SlotItemHandler inputSlot = new SlotItemHandler(mItemHandlers.get(SideHandlerType.INPUT), 0, 56, 30);
    private SlotItemHandler outputSlot = new SlotItemHandler(mItemHandlers.get(SideHandlerType.OUTPUT), 0, 110, 30);

    /**
     * 是否在处理
     */
    protected boolean isProc = false;

    /**
     * 处理进度
     */
    private int consume = 0;

    /**
     * 每次处理时间
     */
    private int consumeTotal = 0;

    /**
     * 每 tick 消耗能量
     */
    private int consumePerTick = 0;

    /**
     * 当前合成的输入
     */
    private ItemStack processItem = ItemStack.EMPTY;

    /**
     * 当前合成的产物
     */
    private ItemStack processOutputCache = ItemStack.EMPTY;

    public TileOneToOne(int maxEnergy, int maxReceive) {
        super(maxEnergy, maxReceive, Integer.MAX_VALUE, 1, 1);

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
        if (isProc) {
            if (consume >= consumeTotal) {
                // 已完成
                processItem = ItemStack.EMPTY;
                ItemStackHandler outputHandler = mItemHandlers.get(SideHandlerType.OUTPUT);
                processOutputCache = outputHandler.insertItem(0, processOutputCache, false);
                isProc = false;
                newRecipeLoop();
            } else if (mEnergyHandler.extractEnergy(consumePerTick, true) < consumePerTick) {
                // 能量不足
                isProc = false;
            } else {
                // 处理进度++
                consume += consumePerTick;
                mEnergyHandler.extractEnergy(consumePerTick, false);
            }
        } else {
            if (!processItem.isEmpty()) {
                // 尝试从能量不足恢复
                isProc = mEnergyHandler.extractEnergy(consumePerTick, true) >= consumePerTick;
            } else {
                if (!processOutputCache.isEmpty())
                    Elementtimes.getLogger().warn("{} has exist!", processOutputCache);
                // 尝试从产物阻塞恢复
                if (!processOutputCache.isEmpty()) {
                    ItemStackHandler outputHandler = mItemHandlers.get(SideHandlerType.OUTPUT);
                    processOutputCache = outputHandler.insertItem(0, processOutputCache, false);
                }
                if (processOutputCache.isEmpty()) {
                    newRecipeLoop();
                }
            }
        }
    }

    private void newRecipeLoop() {
        // 检查能否进行下一轮合成
        ItemStackHandler input = mItemHandlers.get(SideHandlerType.INPUT);
        int energyCost = getEnergyCostPerTick(input);
        if (mEnergyHandler.extractEnergy(energyCost, true) < energyCost) return;
        ItemStack output = getOutput(input, true);
        if (output.isEmpty()) return;
        // 初始化合成环境
        consume = 0;
        consumeTotal = getTotalEnergyCost(input);
        consumePerTick = getEnergyCostPerTick(input);
        processItem = getInput(input);
        processOutputCache = getOutput(input, false);
        isProc = true;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        if (nbt.hasKey("isProc"))
            isProc = nbt.getBoolean("isProc");
        if (nbt.hasKey("consume"))
            consume = nbt.getInteger("consume");
        if (nbt.hasKey("consumeTotal"))
            consumeTotal = nbt.getInteger("consumeTotal");
        if (nbt.hasKey("consumeTick"))
            consumePerTick = nbt.getInteger("consumeTick");
        if (nbt.hasKey("pItem"))
            processItem = new ItemStack(nbt.getCompoundTag("pItem"));
        if (nbt.hasKey("oItem"))
            processOutputCache = new ItemStack(nbt.getCompoundTag("oItem"));
    }

    @Nonnull
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        nbt.setBoolean("isProc", isProc);
        nbt.setInteger("consume", consume);
        nbt.setInteger("consumeTotal", consumeTotal);
        nbt.setInteger("consumeTick", consumePerTick);
        nbt.setTag("pItem", processItem.serializeNBT());
        nbt.setTag("oItem", processOutputCache.serializeNBT());
        return super.writeToNBT(nbt);
    }

    @Override
    public Slot[] getSlots() {
        return new Slot[]{inputSlot, outputSlot};
    }

    /**
     * 获取合成产物
     * @param handler 输入物品
     * @param simulate 是否模拟。该值为 true 时会修改 handler 的内容
     */
    protected abstract ItemStack getOutput(ItemStackHandler handler, boolean simulate);

    /**
     * 获取合成实际消耗的物品
     * @param handler 输入物品
     */
    protected abstract ItemStack getInput(ItemStackHandler handler);

    /**
     * 获取处理总时间
     * @param handler 被处理物品
     * @return 处理该物品所需总时间(tick)
     */
    protected abstract int getTotalEnergyCost(ItemStackHandler handler);

    /**
     * 获取处理物品所需能量
     * @param handler 被处理物品
     * @return 处理该物品每 tick 所需能量(RF)
     */
    protected abstract int getEnergyCostPerTick(ItemStackHandler handler);

    public int getConsume() {
        return consume;
    }

    public int getConsumeTotal() {
        return consumeTotal;
    }
}
