package com.elementtimes.tutorial.common.tileentity.base;

import com.elementtimes.tutorial.common.tileentity.TileElementGenerator;
import com.elementtimes.tutorial.config.ElementtimesConfig;
import com.elementtimes.tutorial.util.RedStoneEnergy;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

public abstract class TileGenerator extends TileMachine {
    private SlotItemHandler inputSlot = new SlotItemHandler(mHandlers.get(ItemHandlerType.INPUT), 0, 80, 30) {
        @Override
        public boolean isItemValid(@Nonnull ItemStack stack) {
            return getRFFromItem(stack) > 0 && super.isItemValid(stack);
        }

        @Override
        public int getSlotStackLimit() {
            return 64;
        }
    };

    private int powerGening = 0;
    private int maxPowerGen = 0;

    public TileGenerator(int maxExtract, int maxReceive) {
        super(new RedStoneEnergy(ElementtimesConfig.general.generaterMaxEnergy), new ItemStackHandler(1), new ItemStackHandler(0));
        storage.setMaxExtract(maxExtract);
        storage.setMaxReceive(maxReceive);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        powerGening = nbt.getInteger("Gening");
        maxPowerGen = nbt.getInteger("maxPowerGen");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        nbt.setInteger("Gening", powerGening);
        nbt.setInteger("maxPowerGen", maxPowerGen);
        return super.writeToNBT(nbt);
    }

    @Override
    public void logic() {
        if (powerGening == 0) {
            ItemStack input = mHandlers.get(ItemHandlerType.INPUT).extractItem(0, 1, true);
            if (input.isEmpty()) return;
            input = mHandlers.get(ItemHandlerType.INPUT).extractItem(0, 1, false);
            maxPowerGen = getRFFromItem(input);
            powerGening = maxPowerGen;
        }

        if (powerGening > 0) {
            int testRec = storage.receiveEnergy(storage.getMaxReceive(), true);
            if (powerGening > testRec) {
                powerGening -= testRec;
                storage.receiveEnergy(storage.getMaxReceive(), false);
            } else {
                storage.receiveEnergy(powerGening, false);
                powerGening = 0;
            }
        }
        /*发送RF*/
        TileEntity[] all = {
                world.getTileEntity(pos.up()),
                world.getTileEntity(pos.down()),
                world.getTileEntity(pos.south()),
                world.getTileEntity(pos.north()),
                world.getTileEntity(pos.east()),
                world.getTileEntity(pos.west())};
        for (int a = 0; a < EnumFacing.values().length; a++) {
            if (all[a] != null) {
                receiveRF(all[a], EnumFacing.getFront(a), storage.getMaxExtract());
            }
        }
    }

    private void receiveRF(TileEntity tileEntity, EnumFacing to, int ext) {
        if (tileEntity.hasCapability(CapabilityEnergy.ENERGY, to) && !(tileEntity instanceof TileElementGenerator)) {
            if (tileEntity.getCapability(CapabilityEnergy.ENERGY, to).canReceive()) {
                int testRec = tileEntity.getCapability(CapabilityEnergy.ENERGY, to).receiveEnergy(ext, true);
                int testExt = storage.extractEnergy(ext, true);
                if (testRec > 0 && testExt > 0) {
                    tileEntity.getCapability(CapabilityEnergy.ENERGY, to).receiveEnergy(testRec, false);
                    storage.extractEnergy(testRec, false);
                }
            }
        }
    }

    public int getMaxPowerGen() {
        return maxPowerGen;
    }

    public int getPowerGening() {
        return powerGening;
    }

    @Override
    public Slot[] getSlots() {
        return new Slot[] {inputSlot};
    }

    protected abstract int getRFFromItem(ItemStack item);
}

