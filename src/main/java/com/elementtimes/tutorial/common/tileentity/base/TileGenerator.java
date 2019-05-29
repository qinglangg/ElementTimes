package com.elementtimes.tutorial.common.tileentity.base;

import com.elementtimes.tutorial.common.capability.RFEnergy;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;
import java.util.Arrays;

public abstract class TileGenerator extends TileMachine {
    private SlotItemHandler inputSlot = new SlotItemHandler(mItemHandlers.get(SideHandlerType.INPUT), 0, 80, 30);

    private int powerGening = 0;
    private int maxPowerGen = 0;

    public TileGenerator(int energyCapacity) {
        super(energyCapacity, Integer.MAX_VALUE, Integer.MAX_VALUE, 1, 0);
        for (EnumFacing value : EnumFacing.values()) {
            mEnergyHandlerTypes.put(value, SideHandlerType.OUTPUT);
        }
    }

    @Override
    protected RFEnergy.EnergyProxy getEnergyProxy(EnumFacing facing) {
        SideHandlerType type = mEnergyHandlerTypes.get(facing);
        if (type == SideHandlerType.IN_OUT || type == SideHandlerType.OUTPUT)
            return mEnergyHandler.new EnergyProxy(0, getMaxExtractRFPerTick(facing));
        return mEnergyHandler.new EnergyProxy(0, 0);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        powerGening = nbt.getInteger("Gening");
        maxPowerGen = nbt.getInteger("maxPowerGen");
    }

    @Nonnull
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        nbt.setInteger("Gening", powerGening);
        nbt.setInteger("maxPowerGen", maxPowerGen);
        return super.writeToNBT(nbt);
    }

    @Override
    public void logic() {
        if (powerGening == 0) {
            maxPowerGen = 0;
            ItemStack input = mItemHandlers.get(SideHandlerType.INPUT).extractItem(0, 1, true);
            if (!input.isEmpty()) {
                input = mItemHandlers.get(SideHandlerType.INPUT).extractItem(0, 1, false);
                maxPowerGen = getRFFromItem(input);
                powerGening = maxPowerGen;
            }
        }

        if (powerGening > 0) {
            int generatorEnergy = Math.min(getMaxGenerateRFPerTick(), powerGening);
            int testRec = mEnergyHandler.receiveEnergy(generatorEnergy, true);
            if (testRec > 0) {
                testRec = mEnergyHandler.receiveEnergy(generatorEnergy, false);
                powerGening -= testRec;
            }
        }

        /*发送RF*/
        Arrays.stream(EnumFacing.values()).forEach(facing -> {
            TileEntity te = world.getTileEntity(pos.offset(facing, 1));
            if (te != null && mEnergyHandler.getEnergyStored() != 0 && te.hasCapability(CapabilityEnergy.ENERGY, facing)) {
                IEnergyStorage energyStorage = te.getCapability(CapabilityEnergy.ENERGY, facing);
                int maxExtract = mEnergyHandler.extractEnergy(getMaxExtractRFPerTick(facing), true);
                if (maxExtract > 0) {
                    int test = energyStorage.receiveEnergy(maxExtract, true);
                    if (test > 0) {
                        energyStorage.receiveEnergy(test, false);
                        mEnergyHandler.extractEnergy(test, false);
                    }
                }
            }
        });
    }

    @Override
    void interrupt() {
        powerGening = 0;
        maxPowerGen = 0;
    }

    public int getMaxPowerGen() {
        return maxPowerGen;
    }

    public int getPowerGening() {
        return powerGening;
    }

    public boolean isClosed() {
        return maxPowerGen == 0 && inputSlot.getStack().isEmpty();
    }

    @Override
    protected boolean isInputItemValid(int slot, @Nonnull ItemStack stack) {
        return getRFFromItem(stack) > 0;
    }

    @Override
    public Slot[] getSlots() {
        return new Slot[] {inputSlot};
    }

    protected abstract int getRFFromItem(ItemStack item);

    protected abstract int getMaxGenerateRFPerTick();

    protected abstract int getMaxExtractRFPerTick(EnumFacing facing);
}

