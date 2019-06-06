package com.elementtimes.tutorial.common.tileentity;

import com.elementtimes.tutorial.common.capability.impl.ItemHandler;
import com.elementtimes.tutorial.common.capability.impl.RfEnergy;
import com.elementtimes.tutorial.common.capability.impl.TankHandler;
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
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

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
    private Map<SideHandlerType, ItemHandler> mItemHandlers = new HashMap<>();
    private Map<EnumFacing, SideHandlerType> mTankTypes = new HashMap<>();
    private Map<SideHandlerType, TankHandler> mTanks = new HashMap<>();
    private Map<EnumFacing, SideHandlerType> mItemHandlerTypes = new HashMap<>();
    private MachineRecipeHandler mRecipe = new MachineRecipeHandler();
    private Set<IMachineLifeCycle> mMachineLifeCycles = new LinkedHashSet<>();
    private MachineRecipeCapture mWorkingRecipe = null;
    private boolean isWorking = false;
    private boolean isPause = false;
    private int mEnergyProcessed = 0;
    private int mEnergyUnprocessed = 0;
    private Slot[] mGuiSlots;
    private GuiButton[] mGuiButtons;

    BaseMachine(int energyCapacity, int inputCount, int outputCount) {
        mEnergyHandler = new RfEnergy(energyCapacity, Integer.MAX_VALUE, Integer.MAX_VALUE);
        mItemHandlers.put(SideHandlerType.INPUT, new ItemHandler(inputCount, this::isInputValid));
        mItemHandlers.put(SideHandlerType.OUTPUT, new ItemHandler(outputCount, (integer, itemStack) -> false));
        // 空闲
        mItemHandlers.put(SideHandlerType.NONE, ItemHandler.EMPTY);
        mItemHandlers.put(SideHandlerType.READONLY, ItemHandler.EMPTY);
        mItemHandlers.put(SideHandlerType.IN_OUT, ItemHandler.EMPTY);
        mTanks.put(SideHandlerType.INPUT, new TankHandler(this::isFillValid, TankHandler.FALSE));
        mTanks.put(SideHandlerType.OUTPUT, new TankHandler(TankHandler.FALSE, TankHandler.TRUE));
        // 空闲
        mTanks.put(SideHandlerType.NONE, TankHandler.EMPTY);
        mTanks.put(SideHandlerType.READONLY, TankHandler.EMPTY);
        mTanks.put(SideHandlerType.IN_OUT, TankHandler.EMPTY);
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
        return mEnergyUnprocessed;
    }
    @Override
    public void setEnergyUnprocessed(int energyUnprocessed) {
        mEnergyUnprocessed = energyUnprocessed;
    }
    @Override
    public void interrupt() {
        setWorkingRecipe(null);
        setEnergyUnprocessed(0);
        setEnergyProcessed(0);
        setWorking(false);
        setPause(false);
    }

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

    @Override
    public RfEnergy getEnergyHandler() { return mEnergyHandler; }
    @Override
    public Map<EnumFacing, SideHandlerType> getEnergyTypeMap() { return mEnergyHandlerTypes; }

    @Override
    public Map<SideHandlerType, ItemHandler> getItemHandlerMap() { return mItemHandlers; }
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

    @Override
    public Slot[] getSlots() {
        return mGuiSlots;
    }
    @Override
    public GuiButton[] getButtons() {
        return mGuiButtons;
    }

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
