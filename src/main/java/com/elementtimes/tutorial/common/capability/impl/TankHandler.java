package com.elementtimes.tutorial.common.capability.impl;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidHandlerConcatenate;

import java.util.function.BiPredicate;
import java.util.function.Supplier;

/**
 * 自定义实现的 IFluidHandler
 * 基于 FluidHandlerConcatenate，可以存储不同种类的流体
 * 实现了 NBT 序列化/反序列化
 * 实现了输入输出的过滤功能
 *
 * @author luqin2007
 */
public class TankHandler extends FluidHandlerConcatenate implements INBTSerializable<NBTTagCompound> {

    public static final TankHandler EMPTY = new TankHandler(TankHandler.FALSE, TankHandler.FALSE, 0);
    public static final BiPredicate<Integer, FluidStack> TRUE = (i, fluid) -> true;
    public static final BiPredicate<Integer, FluidStack> FALSE = (i, fluid) -> false;

    public TankHandler(BiPredicate<Integer, FluidStack> fillCheck, BiPredicate<Integer, FluidStack> drankCheck, int size) {
        this(fillCheck, drankCheck, size, 1000);
    }

    public TankHandler(BiPredicate<Integer, FluidStack> fillCheck, BiPredicate<Integer, FluidStack> drankCheck, int size, int... capacities) {
        this(((Supplier<IFluidHandler[]>) () -> {
            IFluidHandler[] handlers = new IFluidHandler[size];
            int last = -1;
            for (int i = 0; i < size; i++) {
                int capacity;
                if (capacities.length > i) {
                    capacity = capacities[i];
                } else if (last > 0) {
                    capacity = last;
                } else {
                    capacity = 1000;
                }
                handlers[i] = new FluidTank(capacity);
                last = capacity;
            }
            return handlers;
        }).get());
        this.mInputValid = fillCheck;
        this.mOutputValid = drankCheck;
    }

    public TankHandler(IFluidHandler... fluidHandlers) {
        super(fluidHandlers);
    }

    private BiPredicate<Integer, FluidStack> mInputValid;
    private BiPredicate<Integer, FluidStack> mOutputValid;

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound tag = new NBTTagCompound();
        NBTTagList fluids = new NBTTagList();
        for (int i = 0; i < subHandlers.length; i++) {
            IFluidHandler handler = subHandlers[i];
            NBTTagCompound nbt = new NBTTagCompound();
            // 目前仅支持 FluidTank，以后有需求再添加吧
            if (handler != null) {
                FluidTank tank = (FluidTank) handler;
                nbt.setTag("_tank_", tank.writeToNBT(new NBTTagCompound()));
                nbt.setInteger("_capacity_", tank.getCapacity());
            }
            fluids.appendTag(nbt);
        }
        tag.setTag("_fluids_", fluids);
        return tag;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        if (nbt.hasKey("_fluids_")) {
            NBTTagList list = (NBTTagList) nbt.getTag("_fluids_");
            for (int i = 0; i < Math.min(subHandlers.length, list.tagCount()); i++) {
                NBTTagCompound nbtFluid = (NBTTagCompound) list.get(i);
                FluidTank tank = new FluidTank(nbtFluid.getInteger("_capacity_"));
                tank.readFromNBT(nbtFluid.getCompoundTag("_tank_"));
                subHandlers[i] = tank;
            }
        }
    }

    @Override
    @Deprecated
    public int fill(FluidStack resource, boolean doFill) {
        if (!mInputValid.test(-1, resource)) {
            return 0;
        }
        return super.fill(resource, doFill);
    }

    public int fill(int slot, FluidStack resource, boolean doFill) {
        if (!mInputValid.test(slot, resource)) {
            return 0;
        }
        if (resource == null || resource.amount <= 0) {
            return 0;
        }
        return subHandlers[slot].fill(resource, doFill);
    }

    public int fillIgnoreCheck(int slot, FluidStack resource, boolean doFill) {
        if (resource == null || resource.amount <= 0) {
            return 0;
        }
        return subHandlers[slot].fill(resource, doFill);
    }

    @Override
    @Deprecated
    public FluidStack drain(FluidStack resource, boolean doDrain) {
        if (!mOutputValid.test(-1, resource)) {
            return null;
        }
        return super.drain(resource, doDrain);
    }

    public FluidStack drain(int slot, FluidStack resource, boolean doDrain) {
        if (!mOutputValid.test(slot, resource)
                || resource == null
                || resource.amount <= 0) {
            return null;
        }

        return subHandlers[slot].drain(resource, doDrain);
    }

    public FluidStack drainIgnoreCheck(int slot, FluidStack resource, boolean doDrain) {
        if (resource == null || resource.amount <= 0) {
            return null;
        }

        return subHandlers[slot].drain(resource, doDrain);
    }

    @Override
    @Deprecated
    public FluidStack drain(int maxDrain, boolean doDrain) {
        return super.drain(maxDrain, doDrain);
    }

    public FluidStack drain(int slot, int maxDrain, boolean doDrain) {
        if (!mOutputValid.test(-1, new FluidStack(subHandlers[slot].getTankProperties()[0].getContents(), maxDrain))
                || maxDrain <= 0) {
            return null;
        }
        return subHandlers[slot].drain(maxDrain, doDrain);
    }

    public FluidStack drainIgnoreCheck(int slot, int maxDrain, boolean doDrain) {
        if (maxDrain <= 0) {
            return null;
        }
        return subHandlers[slot].drain(maxDrain, doDrain);
    }
}
