package com.elementtimes.tutorial.common.tileentity;

import com.elementtimes.tutorial.Elementtimes;
import com.elementtimes.tutorial.common.init.ElementtimesItems;
import com.elementtimes.tutorial.config.ElementtimesConfig;
import com.elementtimes.tutorial.network.ElementGenerater;
import com.elementtimes.tutorial.util.RedStoneEnergy;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

/**
 * 发电机的TileEntity
 *
 * @author KSGFK create in 2019/2/17
 */
public class TileElementGenerater extends TileMachine implements ISidedInventory {
    private SlotItemHandler inputSlot = new SlotItemHandler(items, 0, 80, 30) {
        @Override
        public boolean isItemValid(@Nonnull ItemStack stack) {
            Item i = stack.getItem();
            boolean isValid = i.equals(ElementtimesItems.Fiveelements) ||
                    i.equals(ElementtimesItems.Fireelement) ||
                    i.equals(ElementtimesItems.Endelement) ||
                    i.equals(ElementtimesItems.Photoelement) ||
                    i.equals(ElementtimesItems.Photoelement) ||
                    i.equals(ElementtimesItems.Goldelement) ||
                    i.equals(ElementtimesItems.Waterelement) ||
                    i.equals(ElementtimesItems.Woodelement) ||
                    i.equals(ElementtimesItems.Soilelement);
            return isValid && super.isItemValid(stack);
        }

        @Override
        public int getSlotStackLimit() {
            return 64;
        }
    };

    private int powerGening = 0;
    private int maxPowerGen = 0;

    public TileElementGenerater() {
        super(new RedStoneEnergy(ElementtimesConfig.general.generaterMaxEnergy), new ItemStackHandler(1));
        storage.setMaxExtract(ElementtimesConfig.general.generaterMaxExtract);
        storage.setMaxReceive(ElementtimesConfig.general.generaterMaxReceive);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        powerGening = nbt.getInteger("Gening");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        nbt.setInteger("Gening", powerGening);
        return super.writeToNBT(nbt);
    }

    public void update() {
        if (!world.isRemote) {
            ItemStack nature = inputSlot.getStack();
            if (powerGening == 0) {
                if (nature.getCount() > 0) {
                    if (nature.getItem().equals(ElementtimesItems.Fiveelements))
                        setPower(nature, ElementtimesConfig.general.generaterFive);
                    else if (nature.getItem().equals(ElementtimesItems.Endelement))
                        setPower(nature, ElementtimesConfig.general.generaterEnd);
                    else if (nature.getItem().equals(ElementtimesItems.Photoelement))
                        setPower(nature, ElementtimesConfig.general.generaterSun);
                    else if (nature.getItem().equals(ElementtimesItems.Soilelement))
                        setPower(nature, ElementtimesConfig.general.generaterSoilGen);
                    else if (nature.getItem().equals(ElementtimesItems.Woodelement))
                        setPower(nature, ElementtimesConfig.general.generaterWoodGen);
                    else if (nature.getItem().equals(ElementtimesItems.Waterelement))
                        setPower(nature, ElementtimesConfig.general.generaterWaterGen);
                    else if (nature.getItem().equals(ElementtimesItems.Goldelement))
                        setPower(nature, ElementtimesConfig.general.generaterGoldGen);
                    else if (nature.getItem().equals(ElementtimesItems.Fireelement))
                        setPower(nature, ElementtimesConfig.general.generaterFireGen);
                }
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
            if (isOpenGui) {
                Elementtimes.getNetwork().sendTo(new ElementGenerater(storage.getEnergyStored(), storage.getMaxEnergyStored(), powerGening, maxPowerGen), player);
            }
        }
    }

    private void setPower(ItemStack inputSlot, int power) {
        inputSlot.setCount(inputSlot.getCount() - 1);
        maxPowerGen = power;
        powerGening = maxPowerGen;
    }

    private void receiveRF(TileEntity tileEntity, EnumFacing to, int ext) {
        if (tileEntity.hasCapability(CapabilityEnergy.ENERGY, to) && !(tileEntity instanceof TileElementGenerater)) {
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

    public SlotItemHandler getInputSlot() {
        return inputSlot;
    }

    public int getMaxPowerGen() {
        return maxPowerGen;
    }

    //ISidedInventory接口开始
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
        return this.items.getStackInSlot(index);
    }

    @Override
    public ItemStack decrStackSize(int index, int count) {
        return this.inputSlot.inventory.decrStackSize(index, count);
    }

    @Override
    public ItemStack removeStackFromSlot(int index) {
        return this.items.getStackInSlot(index);
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
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
        this.inputSlot.inventory.openInventory(player);
    }

    @Override
    public void closeInventory(EntityPlayer player) {
        this.inputSlot.inventory.closeInventory(player);
    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        return this.inputSlot.inventory.isItemValidForSlot(index, stack);
    }

    @Override
    public int getField(int id) {
        return this.inputSlot.inventory.getField(id);
    }

    @Override
    public void setField(int id, int value) {
        this.inputSlot.inventory.setField(id, value);
    }

    @Override
    public int getFieldCount() {
        return this.inputSlot.inventory.getFieldCount();
    }

    @Override
    public void clear() {
        this.inputSlot.inventory.clear();
    }

    @Override
    public String getName() {
        return this.inputSlot.inventory.getName();
    }

    @Override
    public boolean hasCustomName() {
        return this.inputSlot.inventory.hasCustomName();
    }

    private int[] temp = new int[]{0};

    @Override
    public int[] getSlotsForFace(EnumFacing side) {
        return temp;
    }

    @Override
    public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction) {
        return this.inputSlot.isItemValid(itemStackIn);
    }

    @Override
    public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
        return false;
    }
    //结束
}
