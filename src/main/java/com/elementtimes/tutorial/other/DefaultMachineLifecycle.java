package com.elementtimes.tutorial.other;

import com.elementtimes.tutorial.common.capability.impl.ItemHandler;
import com.elementtimes.tutorial.common.capability.impl.RfEnergy;
import com.elementtimes.tutorial.common.capability.impl.TankHandler;
import com.elementtimes.tutorial.common.tileentity.BaseMachine;
import com.elementtimes.tutorial.interfaces.tileentity.IMachineLifeCycle;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

/**
 * 默认机器的生命周期方法
 * @author luqin2007
 */
public class DefaultMachineLifecycle implements IMachineLifeCycle {

    private BaseMachine machine;

    private MachineRecipeHandler.MachineRecipeCapture recipe;
    private ItemHandler inputItems;
    private TankHandler inputTanks;
    private ItemHandler outputItems;
    private TankHandler outputTanks;

    public DefaultMachineLifecycle(final BaseMachine machine) {
        this.machine = machine;
        inputItems = machine.getItemHandler(SideHandlerType.INPUT);
        inputTanks = machine.getTanks(SideHandlerType.INPUT);
        outputItems = machine.getItemHandler(SideHandlerType.OUTPUT);
        outputTanks = machine.getTanks(SideHandlerType.OUTPUT);
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
        assert recipe != null;
        machine.setWorkingRecipe(recipe);
        machine.setEnergyUnprocessed(recipe.energy.applyAsInt(recipe));
        machine.setWorking(true);
        // items
        for (int i = recipe.inputs.size() - 1; i >= 0; i--) {
            inputItems.extractItem(i, recipe.inputs.get(i).getCount(), false);
        }
        // fluids
        for (int i = 0; i < recipe.fluidInputs.size(); i++) {
            inputTanks.drain(recipe.fluidInputs.get(i), true);
        }
    }

    @Override
    public boolean onLoop() {
        assert recipe != null;
        int unprocessed = machine.getEnergyUnprocessed();

        RfEnergy handler = machine.getEnergyHandler();
        if (unprocessed > 0) {
            // 耗能过程
            int change = Math.min(machine.getMaxEnergyChange(), unprocessed);
            if (handler.extractEnergy(change, true) >= change) {
                handler.extractEnergy(change, false);
                machine.processEnergy(change);
                return true;
            }
        } else if (unprocessed < 0) {
            // 产能过程
            int change = Math.min(machine.getMaxEnergyChange(), -unprocessed);
            change = handler.receiveEnergy(change, false);
            machine.processEnergy(change);
            return true;
        }
        return false;
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

        if (machine.getWorkingRecipe().energy.applyAsInt(recipe) > 0) {
            return machine.getEnergyHandler().extractEnergy(change, true) >= change;
        } else {
            return machine.getEnergyHandler().receiveEnergy(change, true) > 0;
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
}
