package com.elementtimes.tutorial.other;

import com.elementtimes.tutorial.common.capability.impl.ItemHandler;
import com.elementtimes.tutorial.common.capability.impl.RfEnergy;
import com.elementtimes.tutorial.common.capability.impl.TankHandler;
import com.elementtimes.tutorial.common.init.ElementtimesGUI;
import com.elementtimes.tutorial.common.tileentity.BaseMachine;
import com.elementtimes.tutorial.interfaces.tileentity.IMachineLifeCycle;
import com.elementtimes.tutorial.other.recipe.MachineRecipeCapture;
import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

import java.util.Map;

/**
 * 默认机器的生命周期方法
 * @author luqin2007
 */
public class DefaultMachineLifecycle implements IMachineLifeCycle {

    private BaseMachine machine;
    private boolean needBind = true;

    private MachineRecipeCapture recipe;
    private ItemHandler inputItems;
    private TankHandler inputTanks;
    private ItemHandler outputItems;
    private TankHandler outputTanks;
    private Int2IntMap mBindInputToOutputMap = new Int2IntOpenHashMap();

    public DefaultMachineLifecycle(final BaseMachine machine) {
        this.machine = machine;
        inputItems = machine.getItemHandler(SideHandlerType.INPUT);
        inputTanks = machine.getTanks(SideHandlerType.INPUT);
        outputItems = machine.getItemHandler(SideHandlerType.OUTPUT);
        outputTanks = machine.getTanks(SideHandlerType.OUTPUT);
        inputItems.onItemChangeListener.add(slot -> {
            if (mBindInputToOutputMap.containsKey(slot)) {
                ItemStack input = inputItems.getStackInSlot(slot);
                outputItems.setStackInSlot(mBindInputToOutputMap.get(slot), input.copy());
            }
        });
        outputItems.onItemChangeListener.add(slot -> {
            if (mBindInputToOutputMap.containsValue(slot)) {
                ItemStack output = outputItems.getStackInSlot(slot);
                mBindInputToOutputMap.entrySet().stream()
                        .filter(kv -> kv.getValue().equals(slot))
                        .findFirst()
                        .map(Map.Entry::getKey)
                        .ifPresent(slotInput -> {
                            if (output.isEmpty() || inputItems.isItemValid(slotInput, output)) {
                                mBindInputToOutputMap.remove(slotInput);
                                inputItems.setSlotIgnoreChangeListener(slotInput, output);
                            }
                        });
            }
        });
        outputItems.onUnbindAllListener.add(itemStacks -> {
            mBindInputToOutputMap.clear();
            needBind = true;
        });
    }

    @Override
    public void onTickStart() {
        if (needBind) {
            for (int i = 0; i < inputItems.getSlots(); i++) {
                ItemStack is = inputItems.getStackInSlot(i);
                if (!is.isEmpty() && !inputItems.isItemValid(i, is)) {
                    bind(i);
                }
            }
            needBind = false;
        }
    }

    @Override
    public boolean onCheckStart() {
        // 合成表
        recipe = machine.getNextRecipe(inputItems, inputTanks);
        if (machine.isRecipeCanWork(recipe, inputItems, inputTanks)) {
            // 能量
            assert recipe != null;
            int change = recipe.energy.applyAsInt(recipe);
            if (change > 0) {
                change = Math.min(change, machine.getMaxEnergyChange());
                return machine.getEnergyHandler().extractEnergy(change, true) >= change;
            } else if (change < 0) {
                change = Math.min(-change, machine.getMaxEnergyChange());
                return machine.getEnergyHandler().receiveEnergy(change, true) > 0;
            }
            return true;
        }
        return false;
    }

    @Override
    public void onStart() {
        outputItems.unbindAll();
        needBind = false;
        assert recipe != null;
        machine.setWorkingRecipe(recipe);
        machine.setEnergyUnprocessed(recipe.energy.applyAsInt(recipe));
        // items
        for (int i = recipe.inputs.size() - 1; i >= 0; i--) {
            ItemStack input = recipe.inputs.get(i);
            inputItems.extractItem(i, input.getCount(), false);
            if (input.getItem().hasContainerItem(input)) {
                inputItems.insertItemIgnoreValid(i, input.getItem().getContainerItem(input), false);
                bind(i);
            }
        }
    }

    @Override
    public boolean onLoop() {
        assert recipe != null;
        int unprocessed = machine.getEnergyUnprocessed();

        int change = 0;
        RfEnergy handler = machine.getEnergyHandler();
        if (unprocessed > 0) {
            // 耗能过程
            change = Math.min(machine.getMaxEnergyChange(), unprocessed);
            if (handler.extractEnergy(change, true) < change) {
                return false;
            }
            handler.extractEnergy(change, false);
            machine.processEnergy(change);
        } else if (unprocessed < 0) {
            // 产能过程
            change = Math.min(machine.getMaxEnergyChange(), -unprocessed);
            change = handler.receiveEnergy(change, false);
            machine.processEnergy(change);
        }

        // 流体消耗/产出
        float a = (float) change / (float) Math.abs(machine.getEnergyProcessed() + machine.getEnergyUnprocessed());
        if (fluid(true, a)) {
            fluid(false, a);
        } else {
            return false;
        }
        return true;
    }

    @Override
    public boolean onCheckFinish() {
        return machine.getEnergyUnprocessed() == 0;
    }

    @Override
    public boolean onCheckResume() {
        recipe = machine.getWorkingRecipe();
        assert recipe != null;
        int change = Math.min(machine.getMaxEnergyChange(), Math.abs(machine.getEnergyUnprocessed()));

        float a = (float) change / (float) Math.abs(machine.getEnergyProcessed() + machine.getEnergyUnprocessed());
        if (machine.getWorkingRecipe().energy.applyAsInt(recipe) > 0) {
            return machine.getEnergyHandler().extractEnergy(change, true) >= change && fluid(true, a);
        } else {
            return machine.getEnergyHandler().receiveEnergy(change, true) > 0 && fluid(true, a);
        }
    }

    @Override
    public boolean onFinish() {
        assert recipe != null;
        boolean output = output(true);
        if (output) {
            output(false);
        }
        return output;
    }

    private boolean output(boolean simulate) {
        int itemCount = Math.min(recipe.outputs.size(), outputItems.getSlots());
        int fluidCount = Math.min(recipe.fluidOutputs.size(), outputTanks.getTankProperties().length);

        boolean pushAll = true;
        // item
        for (int i = 0; pushAll && i < itemCount; i++) {
            ItemStack left = recipe.outputs.get(i);
            // 已有物品
            for (int j = 0; j < outputItems.getSlots(); j++) {
                ItemStack slot = outputItems.getStackInSlot(j);
                if (!slot.isEmpty() && slot.isItemEqual(left)) {
                    left = outputItems.insertItemIgnoreValid(j, left, simulate);
                }
            }
            // 空槽位
            for (int j = 0; j < outputItems.getSlots(); j++) {
                ItemStack slot = outputItems.getStackInSlot(j);
                if (slot.isEmpty()) {
                    left = outputItems.insertItemIgnoreValid(j, left, simulate);
                }
            }
            if (simulate) {
                pushAll = left.isEmpty();
            }
        }
        // fluid
        for (int i = 0; pushAll && i < fluidCount; i++) {
            FluidStack stack = recipe.fluidOutputs.get(i);
            if (simulate) {
                FluidStack drain = outputTanks.drain(stack, false);
                pushAll = drain != null && drain.amount >= stack.amount;
            } else {
                outputTanks.drain(stack, true);
            }
        }
        return pushAll;
    }

    private boolean fluid(boolean simulate, float a) {
        int max = Math.max(recipe.fluidInputs.size(), recipe.fluidOutputs.size());
        for (int i = 0; i < max; i++) {
            if (recipe.fluidInputs.size() > i) {
                final FluidStack fluid = recipe.fluidInputs.get(i);
                int amount = (int) (fluid.amount * a);
                if (simulate) {
                    FluidStack drain = inputTanks.drain(new FluidStack(fluid.getFluid(), amount, fluid.writeToNBT(new NBTTagCompound())), false);
                    if (drain == null || drain.amount < amount) {
                        return false;
                    }
                } else {
                    inputTanks.drain(new FluidStack(fluid.getFluid(), amount, fluid.writeToNBT(new NBTTagCompound())), true);
                }
            }

            if (recipe.fluidOutputs.size() > i) {
                final FluidStack fluid = recipe.fluidOutputs.get(i);
                int amount = (int) (fluid.amount * a);
                final FluidStack fillStack = new FluidStack(fluid, amount);
                if (simulate) {
                    int fill = outputTanks.fill(fillStack, false);
                    if (fill < amount) {
                        return false;
                    }
                } else {
                    outputTanks.fill(fillStack, true);
                }
            }
        }

        for (int i = 0; i < recipe.fluidOutputs.size(); i++) {
            final FluidStack fluid = recipe.fluidOutputs.get(i);
            int amount = (int) (fluid.amount * a);
            int fill = outputTanks.fill(new FluidStack(fluid.getFluid(), amount, fluid.writeToNBT(new NBTTagCompound())), false);
            if (fill == amount) {
                outputTanks.fill(new FluidStack(fluid.getFluid(), amount, fluid.writeToNBT(new NBTTagCompound())), false);
            } else {
                return false;
            }
        }
        return true;
    }

    private void bind(int slotInput) {
        ItemStack input = inputItems.getStackInSlot(slotInput).copy();
        int bind = outputItems.bind(input);
        mBindInputToOutputMap.put(slotInput, bind);
    }
}
