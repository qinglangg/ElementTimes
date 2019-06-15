package com.elementtimes.tutorial.other;

import com.elementtimes.tutorial.annotation.AnnotationElements;
import com.elementtimes.tutorial.common.capability.impl.ItemHandler;
import com.elementtimes.tutorial.common.capability.impl.TankHandler;
import com.elementtimes.tutorial.common.init.ElementtimesGUI;
import com.elementtimes.tutorial.common.tileentity.BaseMachine;
import com.elementtimes.tutorial.interfaces.tileentity.IMachineLifeCycle;
import com.elementtimes.tutorial.network.FluidMachineNetwork;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

/**
 * 拥有流体输入/输出槽位的机器，在 tick 前后检查槽位物品
 * @author luqin2007
 */
public class FluidMachineLifecycle implements IMachineLifeCycle {

    private BaseMachine mMachine;
    private Int2ObjectMap<int[]> mInputs;
    private Int2ObjectMap<int[]> mOutputs;

    public FluidMachineLifecycle(BaseMachine machine, Int2ObjectMap<int[]> bucketInputSlots, Int2ObjectMap<int[]> bucketOutputSlots) {
        mMachine = machine;
        mInputs = bucketInputSlots;
        mOutputs = bucketOutputSlots;
    }

    @Override
    public void onTickStart() {
        ItemHandler itemInput = mMachine.getItemHandler(SideHandlerType.INPUT);
        ItemHandler itemOutput = mMachine.getItemHandler(SideHandlerType.OUTPUT);
        TankHandler tanks = mMachine.getTanks(SideHandlerType.INPUT);
        mInputs.int2ObjectEntrySet().forEach(entry -> {
            int handler = entry.getIntKey();
            int slot = entry.getValue()[0];
            int output = entry.getValue()[1];
            ItemStack itemStack = itemInput.getStackInSlot(slot);
            if (itemStack.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null)) {
                IFluidHandlerItem fluidHandler = itemStack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
                boolean notEmpty = true;
                for (IFluidTankProperties property : fluidHandler.getTankProperties()) {
                    if (property.getContents() != null && property.getContents().amount > 0) {
                        int tryFill = tanks.fill(handler, property.getContents(), false);
                        if (tryFill > 0) {
                            FluidStack tryFluid = new FluidStack(property.getContents(), tryFill);
                            FluidStack tryDrain = fluidHandler.drain(tryFluid, false);
                            if (tryDrain.amount == tryFill) {
                                FluidStack result = tryDrain.copy();
                                tanks.fill(handler, result, true);
                                fluidHandler.drain(result, true);
                            }
                        }

                    }
                    if (notEmpty && (property.getContents() == null || property.getContents().amount <= 0)) {
                        notEmpty = false;
                    }
                }
                if (!notEmpty) {
                    ItemStack empty = itemInput.extractItem(slot, 1, true);
                    if (!empty.isEmpty()) {
                        ItemStack item = itemOutput.insertItem(output, empty, true);
                        if (item.isEmpty()) {
                            itemInput.extractItem(slot, 1, false);
                            itemOutput.insertItem(output, empty, false);
                        }
                    }
                }
            }
        });
    }

    @Override
    public void onTickFinish() {
        ItemHandler itemInput = mMachine.getItemHandler(SideHandlerType.INPUT);
        ItemHandler itemOutput = mMachine.getItemHandler(SideHandlerType.OUTPUT);
        TankHandler tanks = mMachine.getTanks(SideHandlerType.OUTPUT);
        mOutputs.int2ObjectEntrySet().forEach(entry -> {
            int handler = entry.getIntKey();
            int slot = entry.getValue()[0];
            int output = entry.getValue()[1];
            FluidStack contents = tanks.getTankProperties()[handler].getContents();
            if (contents != null && contents.amount > 0) {
                ItemStack itemStack = itemInput.getStackInSlot(slot);
                if (itemStack.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null)) {
                    IFluidHandlerItem fluidHandler = itemStack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
                    int fill = fluidHandler.fill(contents, false);
                    if (fill > 0) {
                        fill = fluidHandler.fill(contents, true);
                        tanks.drain(handler, fill, true);
                        ItemStack item = itemInput.extractItem(slot, 1, true);
                        if (!item.isEmpty()) {
                            ItemStack item1 = itemOutput.insertItem(output, item, true);
                            if (item1.isEmpty()) {
                                item = itemInput.extractItem(slot, 1, false);
                                itemOutput.insertItem(output, item, false);
                            }
                        }
                    }
                }
            }
        });

        ElementtimesGUI.Machines type = mMachine.getGuiType();
        if (ElementtimesGUI.GUI_DISPLAYED.containsKey(type)) {
            FluidMachineNetwork message = new FluidMachineNetwork();
            if (!mMachine.getTanks(SideHandlerType.INPUT).isEmpty()) {
                message.put(SideHandlerType.INPUT, mMachine.getTanks(SideHandlerType.INPUT));
            }
            if (!mMachine.getTanks(SideHandlerType.OUTPUT).isEmpty()) {
                message.put(SideHandlerType.OUTPUT, mMachine.getTanks(SideHandlerType.OUTPUT));
            }
            for (EntityPlayerMP player : ElementtimesGUI.GUI_DISPLAYED.get(type)) {
                System.out.println("send to " + player + " in " + type.name());
                AnnotationElements.CHANNEL.sendTo(message, player);
            }
        }
    }
}
