package com.elementtimes.tutorial.common.tileentity;

import cofh.redstoneflux.api.IEnergyProvider;
import cofh.redstoneflux.api.IEnergyReceiver;
import cofh.redstoneflux.impl.EnergyStorage;
import com.elementtimes.tutorial.Elementtimes;
import com.elementtimes.tutorial.common.init.ElementtimesBlocks;
import com.elementtimes.tutorial.common.init.ElementtimesItems;
import com.elementtimes.tutorial.config.ElementtimesConfig;
import com.elementtimes.tutorial.network.ElementGenerater;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;

import javax.annotation.Nonnull;

/**
 * 发电机的TileEntity
 *
 * @author KSGFK create in 2019/2/17
 */
public class TileElementGenerater extends TileInventory implements ITickable, IEnergyProvider, IEnergyReceiver {
    private EnergyStorage storage = new EnergyStorage(ElementtimesConfig.general.generaterMaxEnergy);

    private Slot inputSlot = new Slot(this, 0, 80, 30) {
        @Override
        public boolean isItemValid(@Nonnull ItemStack stack) {
            Item i = stack.getItem();
            boolean isValid = i.equals(ElementtimesItems.Fiveelements) ||
                    i.equals(ElementtimesItems.Fireelement) ||
                    i.equals(ElementtimesItems.Endelement) ||
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

    private NBTTagCompound nbtTagCompound;
    private int powerGening = 0;
    private int maxPowerGen = 0;
    private EntityPlayerMP player;
    private boolean isOpenGui;

    public TileElementGenerater() {
        storage.setMaxExtract(ElementtimesConfig.general.generaterMaxExtract);
        storage.setMaxReceive(ElementtimesConfig.general.generaterMaxReceive);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        storage.readFromNBT(nbt.getCompoundTag("info"));
        this.powerGening = nbt.getCompoundTag("info").getInteger("Gening");
        if (inputSlot != null) {
            String name = nbt.getCompoundTag("info").getString("Name");
            switch (name) {
                case "item.fiveelements":
                    setInputSlot(nbt, ElementtimesItems.Fiveelements);
                    break;
                case "item.endelement":
                    setInputSlot(nbt, ElementtimesItems.Endelement);
                    break;
                case "item.soilelement":
                    setInputSlot(nbt, ElementtimesItems.Soilelement);
                    break;
                case "item.woodelement":
                    setInputSlot(nbt, ElementtimesItems.Woodelement);
                    break;
                case "item.waterelement":
                    setInputSlot(nbt, ElementtimesItems.Waterelement);
                    break;
                case "item.fireelement":
                    setInputSlot(nbt, ElementtimesItems.Fireelement);
                    break;
                case "item.goldelement":
                    setInputSlot(nbt, ElementtimesItems.Goldelement);
                    break;
            }
        }
        this.nbtTagCompound = nbt;
    }

    private void setInputSlot(NBTTagCompound nbt, Item item) {
        ItemStack i = new ItemStack(item);
        i.setCount(nbt.getCompoundTag("info").getShort("Count"));
        inputSlot.putStack(i);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        if (nbt.hasKey("info")) {
            storage.writeToNBT(nbt.getCompoundTag("info"));
            nbt.getCompoundTag("info").setShort("Count", (short) inputSlot.getStack().getCount());
            nbt.getCompoundTag("info").setString("Name", inputSlot.getStack().getUnlocalizedName());
            nbt.getCompoundTag("info").setInteger("Gening", this.powerGening);
        } else {
            NBTTagCompound n = new NBTTagCompound();
            storage.writeToNBT(n);
            n.setInteger("Gening", powerGening);
            if (inputSlot != null) {
                n.setShort("Count", (short) inputSlot.getStack().getCount());
                n.setString("Name", inputSlot.getStack().getUnlocalizedName());
            }
            nbt.setTag("info", n);
        }
        this.nbtTagCompound = nbt;
        return nbt;
    }

    /* IEnergyConnection */
    @Override
    public boolean canConnectEnergy(EnumFacing from) {
        return true;
    }

    /* IEnergyReceiver */
    @Override
    public int receiveEnergy(EnumFacing from, int maxReceive, boolean simulate) {
        return storage.receiveEnergy(maxReceive, simulate);
    }

    /* IEnergyProvider */
    @Override
    public int extractEnergy(EnumFacing from, int maxExtract, boolean simulate) {
        return storage.extractEnergy(maxExtract, simulate);
    }

    /* IEnergyHandler */
    @Override
    public int getEnergyStored(EnumFacing from) {
        return storage.getEnergyStored();
    }

    @Override
    public int getMaxEnergyStored(EnumFacing from) {
        return storage.getMaxEnergyStored();
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
        if (tileEntity instanceof IEnergyReceiver) {
            IEnergyReceiver r = (IEnergyReceiver) tileEntity;
            if (r.canConnectEnergy(to)) {
                int testRec = r.receiveEnergy(to, ext, true);
                int testExt = this.extractEnergy(null, ext, true);
                if (testRec > 0 && testExt > 0) {
                    r.receiveEnergy(to, testRec, false);
                    this.extractEnergy(null, testRec, false);
                }
            }
        }
    }

    /*储存*/
    private int[] a = new int[1];
    private ItemStack air = new ItemStack(Items.AIR);
    public ItemStack[] inventory = new ItemStack[]{air};

    @Override
    public int[] getSlotsForFace(EnumFacing side) {
        return a;
    }

    @Override
    public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction) {
        return true;
    }

    @Override
    public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
        return false;
    }

    @Override
    public int getSizeInventory() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        for (ItemStack stack : inventory) {
            if (!stack.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public ItemStack getStackInSlot(int index) {
        return inventory[index];
    }

    @Override
    public ItemStack decrStackSize(int index, int count) {
        if (inventory[index].isEmpty()) {
            return ItemStack.EMPTY;
        }
        if (inventory[index].getCount() <= count) {
            count = inventory[index].getCount();
        }
        ItemStack stack = inventory[index].splitStack(count);

        if (inventory[index].getCount() <= 0) {
            inventory[index] = ItemStack.EMPTY;
        }
        return stack;
    }

    @Override
    public ItemStack removeStackFromSlot(int index) {
        if (inventory[index].isEmpty()) {
            return ItemStack.EMPTY;
        }
        ItemStack stack = inventory[index];
        inventory[index] = ItemStack.EMPTY;
        return stack;
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        inventory[index] = stack;
        this.markDirty();
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public boolean isUsableByPlayer(EntityPlayer player) {
        return player.getDistanceSq(pos) <= 64D && world.getTileEntity(pos) == this;
    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        return true;
    }

    @Override
    public void clear() {
        inventory[0] = new ItemStack(Items.AIR);
    }

    @Override
    public String getName() {
        return "egen";
    }

    public void setPlayer(EntityPlayerMP player) {
        this.player = player;
    }

    public void setOpenGui(boolean openGui) {
        isOpenGui = openGui;
    }

    public Slot getInputSlot() {
        return inputSlot;
    }

    public void setInputSlot(Slot inputSlot) {
        this.inputSlot = inputSlot;
    }

    public EnergyStorage getStorage() {
        return storage;
    }

    public NBTTagCompound getNbtTagCompound() {
        return nbtTagCompound;
    }

    public int getMaxPowerGen() {
        return maxPowerGen;
    }
}
