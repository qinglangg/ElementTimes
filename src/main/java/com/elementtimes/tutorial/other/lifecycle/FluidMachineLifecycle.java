package com.elementtimes.tutorial.other.lifecycle;

import com.elementtimes.tutorial.annotation.AnnotationElements;
import com.elementtimes.tutorial.common.capability.impl.ItemHandler;
import com.elementtimes.tutorial.common.capability.impl.TankHandler;
import com.elementtimes.tutorial.common.item.ItemBottleFuel;
import com.elementtimes.tutorial.common.tileentity.BaseMachine;
import com.elementtimes.tutorial.interfaces.tileentity.IMachineLifeCycle;
import com.elementtimes.tutorial.network.FluidMachineNetwork;
import com.elementtimes.tutorial.other.SideHandlerType;
import com.elementtimes.tutorial.util.FluidUtil;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

import java.util.List;

/**
 * 拥有流体输入/输出槽位的机器，在 tick 前后检查槽位物品
 *
 * @author luqin2007
 */
public class FluidMachineLifecycle implements IMachineLifeCycle {

    private BaseMachine mMachine;
    // slot -> input, output
    private Int2ObjectMap<int[]> mInputs;
    // slot -> input, output
    private Int2ObjectMap<int[]> mOutputs;

    private ItemHandler inputItems, outputItems;
    private TankHandler inputFluids, outputFluids;

    public FluidMachineLifecycle(BaseMachine machine, Int2ObjectMap<int[]> bucketInputSlots, Int2ObjectMap<int[]> bucketOutputSlots) {
        mMachine = machine;
        mInputs = bucketInputSlots;
        mOutputs = bucketOutputSlots;
        inputItems = machine.getItemHandler(SideHandlerType.INPUT);
        outputItems = machine.getItemHandler(SideHandlerType.OUTPUT);
        inputFluids = machine.getTanks(SideHandlerType.INPUT);
        outputFluids = machine.getTanks(SideHandlerType.OUTPUT);
    }

    @Override
    public void onTickStart() {
        IFluidTankProperties[] propertiesInput = inputFluids.getTankProperties();
        List<FluidStack> inputFluidList = FluidUtil.toListNotNull(propertiesInput);
        mInputs.int2ObjectEntrySet().forEach(entry -> {
            int slot = entry.getIntKey();
            int bucketInput = entry.getValue()[0];
            int bucketOutput = entry.getValue()[1];
            if (!FluidUtil.isFull(propertiesInput, slot)) {
                ItemStack containerIn = inputItems.getStackInSlot(bucketInput);
                IFluidHandlerItem fluidHandler = containerIn.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
                if (fluidHandler != null) {
                    FluidStack fluidInput = inputFluidList.get(slot);
                    FluidStack transfer;
                    if (fluidInput != null && fluidInput.amount > 0) {
                        FluidStack take = fluidHandler.drain(new FluidStack(fluidInput, propertiesInput[slot].getCapacity() - fluidInput.amount), false);
                        transfer = new FluidStack(fluidInput, take == null ? 0 : take.amount);
                    } else if (mMachine.getWorkingRecipe() != null) {
                        transfer = mMachine.getWorkingRecipe().fluidInputs.get(slot);
                    } else {
                        transfer = fluidHandler.drain(propertiesInput[slot].getCapacity(), false);
                    }

                    if (transfer != null && transfer.amount != 0) {
                        int fill = inputFluids.fill(slot, transfer, false);
                        if (fill > 0) {
                            FluidStack drain = fluidHandler.drain(transfer, false);
                            if (drain != null && drain.amount > 0) {
                                drain.amount = Math.min(drain.amount, fill);
                                inputFluids.fill(slot, drain, true);
                                fluidHandler.drain(drain, true);
                            }
                        }
                    }

                    if (FluidUtil.isEmpty(fluidHandler.getTankProperties())) {
                        ItemStack extractItem = inputItems.extractItem(bucketInput, 1, true);
                        if (!extractItem.isEmpty()) {
                            ItemStack insertItem = outputItems.insertItemIgnoreValid(bucketOutput, fluidHandler.getContainer(), true);
                            if (insertItem.isEmpty()) {
                                inputItems.extractItem(bucketInput, 1, false);
                                outputItems.insertItemIgnoreValid(bucketOutput, fluidHandler.getContainer(), false);
                            }
                        }
                    }
                }
            }
        });
    }

    @Override
    public void onTickFinish() {
        IFluidTankProperties[] properties = outputFluids.getTankProperties();
        List<FluidStack> outputFluidList = FluidUtil.toListNotNull(properties);
        mOutputs.int2ObjectEntrySet().forEach(entry -> {
            int slot = entry.getIntKey();
            int bucketInput = entry.getValue()[0];
            int bucketOutput = entry.getValue()[1];
            ItemStack containerIn = inputItems.getStackInSlot(bucketInput);
            IFluidHandlerItem fluidHandler = containerIn.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
            if (fluidHandler != null) {
                if (!FluidUtil.isEmpty(properties, slot)) {
                    FluidStack output = outputFluidList.get(slot);
                    if (output != null && output.amount > 0) {
                        FluidStack drain = outputFluids.drain(slot, output, false);
                        int fill = fluidHandler.fill(output, false);
                        if (fill > 0) {
                            drain.amount = Math.min(drain.amount, fill);
                            outputFluids.drain(slot, drain, true);
                            fluidHandler.fill(drain, true);
                        }
                    }
                }

                if (FluidUtil.isFull(fluidHandler.getTankProperties())) {
                    ItemStack extractItem = inputItems.extractItem(bucketInput, 1, true);
                    if (!extractItem.isEmpty()) {
                        ItemStack itemStack = fluidHandler.getContainer();
                        ItemStack insertItem = outputItems.insertItemIgnoreValid(bucketOutput, itemStack, true);
                        if (insertItem.isEmpty()) {
                            inputItems.extractItem(bucketInput, 1, false);
                            outputItems.insertItemIgnoreValid(bucketOutput, itemStack, false);
                        }
                    }
                }
            } else if (containerIn.getItem() == Items.GLASS_BOTTLE) {
                FluidStack output = outputFluidList.get(slot);
                if (output != null && output.amount >= Fluid.BUCKET_VOLUME) {
                    FluidStack drain = outputFluids.drain(slot, output, false);
                    if (drain.amount >= Fluid.BUCKET_VOLUME) {
                        ItemStack extractItem = inputItems.extractItem(bucketInput, 1, true);
                        if (!extractItem.isEmpty()) {
                            ItemStack insertItem = outputItems.insertItemIgnoreValid(bucketOutput, ItemBottleFuel.createByFluid(drain), true);
                            if (insertItem.isEmpty()) {
                                inputItems.extractItem(bucketInput, 1, false);
                                outputItems.insertItemIgnoreValid(bucketOutput, ItemBottleFuel.createByFluid(drain.getFluid()), false);
                                outputFluids.drain(slot, Fluid.BUCKET_VOLUME, true);
                            }
                        }
                    }
                }
            }
        });
        FluidMachineNetwork message = new FluidMachineNetwork();
        TankHandler input = mMachine.getTanks(SideHandlerType.INPUT);
        if (!FluidUtil.isNoCapability(input.getTankProperties())) {
            message.put(SideHandlerType.INPUT, input);
        }
        TankHandler output = mMachine.getTanks(SideHandlerType.OUTPUT);
        if (!FluidUtil.isNoCapability(output.getTankProperties())) {
            message.put(SideHandlerType.OUTPUT, output);
        }
        for (EntityPlayerMP player : mMachine.getOpenedPlayers()) {
            AnnotationElements.CHANNEL.sendTo(message, player);
        }
    }
}
