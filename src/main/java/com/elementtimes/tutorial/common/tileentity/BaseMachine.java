package com.elementtimes.tutorial.common.tileentity;

import com.elementtimes.tutorial.common.capability.impl.*;
import com.elementtimes.tutorial.interfaces.tileentity.*;
import com.elementtimes.tutorial.other.DefaultMachineLifecycle;
import com.elementtimes.tutorial.other.MachineRecipeHandler;
import com.elementtimes.tutorial.other.MachineRecipeHandler.MachineRecipeCapture;
import com.elementtimes.tutorial.other.SideHandlerType;
import com.elementtimes.tutorial.util.ItemUtil;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

/**
 * 对所有机器的抽象
 * 大量细节被我隐藏在接口中
 * @author KSGFK create in 2019/3/9
 */
public abstract class BaseMachine extends TileEntity implements
        IMachineTickable, IMachineRecipe, ITileHandler, IGuiProvider, IConfigApply {
    public static ItemStackHandler EMPTY = new ItemStackHandler(0);
    private RfEnergy mEnergyHandler;
    private Map<EnumFacing, SideHandlerType> mEnergyHandlerTypes = new HashMap<>();
    private Map<SideHandlerType, ItemStackHandler> mItemHandlers = new HashMap<>();
    private Map<EnumFacing, SideHandlerType> mTankTypes = new HashMap<>();
    private Map<SideHandlerType, TankHandler> mTanks = new HashMap<>();
    private Map<EnumFacing, SideHandlerType> mItemHandlerTypes = new HashMap<>();
    private MachineRecipeHandler mRecipe = new MachineRecipeHandler();
    private Set<IMachineLifeCycle> mMachineLifeCycles = new LinkedHashSet<>();
    private MachineRecipeCapture mWorkingRecipe = null;
    private boolean isWorking = false;
    private boolean isPause = false;
    private int mEnergyProcessed = 0;
    private Slot[] mGuiSlots;
    private GuiButton[] mGuiButtons;

    BaseMachine(int energyCapacity, int inputCount, int outputCount) {
        mEnergyHandler = new RfEnergy(energyCapacity, Integer.MAX_VALUE, Integer.MAX_VALUE);
        mItemHandlers.put(SideHandlerType.INPUT, new ItemHandler(inputCount, this::isInputValid));
        mItemHandlers.put(SideHandlerType.OUTPUT, new ItemHandler(outputCount, (integer, itemStack) -> false));
        mItemHandlers.put(SideHandlerType.NONE, EMPTY); // 空闲
        mItemHandlers.put(SideHandlerType.READONLY, EMPTY); // 空闲
        mItemHandlers.put(SideHandlerType.IN_OUT, EMPTY); // 空闲
        mTanks.put(SideHandlerType.INPUT, new TankHandler(this::isFillValid, TankHandler.FALSE));
        mTanks.put(SideHandlerType.OUTPUT, new TankHandler(TankHandler.FALSE, TankHandler.TRUE));
        mTanks.put(SideHandlerType.NONE, TankHandler.EMPTY); // 空闲
        mTanks.put(SideHandlerType.READONLY, TankHandler.EMPTY); // 空闲
        mTanks.put(SideHandlerType.IN_OUT, TankHandler.EMPTY); // 空闲
        for (EnumFacing facing : EnumFacing.values()) {
            if (facing == EnumFacing.DOWN) {
                mItemHandlerTypes.put(facing, SideHandlerType.OUTPUT);
            } else {
                mItemHandlerTypes.put(facing, SideHandlerType.INPUT);
            }
            mEnergyHandlerTypes.put(facing, SideHandlerType.INPUT);
        }
        mRecipe = updateRecipe(mRecipe);
        mGuiSlots = createSlots();
        mGuiButtons = createButton();
        addLifeCycle(new DefaultMachineLifecycle(this));
        applyConfig();
    }

    // IMachineTickable & IMachineLifecycleManager
    @Override
    public Set<IMachineLifeCycle> getAllLifecycles() {
        return mMachineLifeCycles;
    }
    @Override
    public void setWorking(boolean working) {
        isWorking = working;
    }
    @Override
    public boolean isWorking() {
        return isWorking;
    }
    @Override
    public void setPause(boolean pause) {
        isPause = pause;
    }
    @Override
    public boolean isPause() {
        return isPause;
    }
    @Override
    public int getEnergyProcessed() {
        return mEnergyProcessed;
    }
    @Override
    public void setEnergyProcessed(int energyProcessed) {
        mEnergyProcessed = energyProcessed;
    }
    @Override
    public int getEnergyUnprocessed() {
        MachineRecipeCapture recipe = getWorkingRecipe();
        int unprocessed = 0;
        if (recipe != null) {
            unprocessed = Math.abs(recipe.energy.applyAsInt(recipe)) - getEnergyProcessed();
        }
        return unprocessed;
    }
    @Override
    public void setEnergyUnprocessed(int energyUnprocessed) {
        MachineRecipeCapture recipe = getWorkingRecipe();
        if (recipe != null) {
            int total = Math.abs(recipe.energy.applyAsInt(recipe));
            if (total > energyUnprocessed) {
                setEnergyProcessed(total - energyUnprocessed);
            } else {
                setEnergyProcessed(0);
            }
        }
    }
    @Override
    public void interrupt() {
        setWorkingRecipe(null);
        setEnergyUnprocessed(0);
        setEnergyProcessed(0);
        setWorking(false);
        setPause(false);
    }

    // IMachineRecipe
    @Nullable
    @Override
    public MachineRecipeCapture getWorkingRecipe() {
        return mWorkingRecipe;
    }
    @Override
    public void setWorkingRecipe(MachineRecipeCapture workingRecipe) {
        mWorkingRecipe = workingRecipe;
    }
    @Override
    public MachineRecipeHandler getRecipes() {
        return mRecipe;
    }

    // ITileEnergyHandler
    @Override
    public RfEnergy getEnergyHandler() { return mEnergyHandler; }
    @Override
    public Map<EnumFacing, SideHandlerType> getEnergyTypeMap() { return mEnergyHandlerTypes; }

    // ITileItemHandler
    @Override
    public Map<SideHandlerType, ItemStackHandler> getItemHandlerMap() { return mItemHandlers; }
    @Override
    public Map<EnumFacing, SideHandlerType> getItemTypeMap() { return mItemHandlerTypes; }
    @Override
    public boolean isInputValid(int slot, ItemStack stack) {
        ItemStackHandler itemHandler = getItemHandler(SideHandlerType.INPUT);
        return getRecipes().accept(slot,
                ItemUtil.toList(itemHandler),
                getTanks(SideHandlerType.INPUT).getFluidStacks(),
                stack);
    }

    // ITileFluidHandler
    @Override
    public Map<EnumFacing, SideHandlerType> getTankTypeMap() { return mTankTypes; }
    @Override
    public Map<SideHandlerType, TankHandler> getTanksMap() { return mTanks; }
    @Override
    public boolean isFillValid(Fluid fluid, int count) {
        ItemStackHandler itemHandler = getItemHandler(SideHandlerType.INPUT);
        return getRecipes().accept(0,
                ItemUtil.toList(itemHandler),
                getTanks(SideHandlerType.INPUT).getFluidStacks(),
                new FluidStack(fluid, count));
    }

    // GUI
    @Override
    public Slot[] getSlots() {
        return mGuiSlots;
    }
    @Override
    public GuiButton[] getButtons() {
        return mGuiButtons;
    }

    // TileEntity
    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        ITileHandler.super.deserializeNBT(nbt);
        IMachineTickable.super.deserializeNBT(nbt);
        IMachineRecipe.super.deserializeNBT(nbt);
        super.readFromNBT(nbt);
    }
    @Nonnull
    @Override
    public NBTTagCompound getUpdateTag() {
        return writeToNBT(new NBTTagCompound());
    }
    @Override
    @SuppressWarnings("NullableProblems")
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        ITileHandler.super.writeToNBT(nbt);
        IMachineTickable.super.writeToNBT(nbt);
        IMachineRecipe.super.writeToNBT(nbt);
        return nbt;
    }
    @Nullable
    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        return new SPacketUpdateTileEntity(this.pos, 0, serializeNBT());
    }
    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        deserializeNBT(pkt.getNbtCompound());
    }
    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        return ITileHandler.super.hasCapability(capability, facing)
                || super.hasCapability(capability, facing);
    }
    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        T t = ITileHandler.super.getCapability(capability, facing);
        return t != null ? t : super.getCapability(capability, facing);
    }
}
