package com.elementtimes.tutorial.common.capability.impl;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import net.minecraftforge.fluids.capability.templates.FluidHandlerConcatenate;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

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
    public static final BiPredicate<Fluid, Integer> TRUE = (fluid, integer) -> true;
    public static final BiPredicate<Fluid, Integer> FALSE = (fluid, integer) -> false;

    public TankHandler(BiPredicate<Fluid, Integer> fillCheck, BiPredicate<Fluid, Integer> drankCheck) {
        this(fillCheck, drankCheck, 1, 1000);
    }

    public TankHandler(BiPredicate<Fluid, Integer> fillCheck, BiPredicate<Fluid, Integer> drankCheck, int size) {
        this(fillCheck, drankCheck, size, 1000);
    }

    public TankHandler(BiPredicate<Fluid, Integer> fillCheck, BiPredicate<Fluid, Integer> drankCheck, int size, int... capacities) {
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

    private BiPredicate<Fluid, Integer> mInputValid;
    private BiPredicate<Fluid, Integer> mOutputValid;

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
    public int fill(FluidStack resource, boolean doFill) {
        if (!mInputValid.test(resource.getFluid(), resource.amount)) {
            return 0;
        }
        return super.fill(resource, doFill);
    }

    @Override
    public FluidStack drain(FluidStack resource, boolean doDrain) {
        if (!mOutputValid.test(resource.getFluid(), resource.amount)) {
            return null;
        }
        return super.drain(resource, doDrain);
    }

    @Override
    public FluidStack drain(int maxDrain, boolean doDrain) {
        if (!mOutputValid.test(null, maxDrain)) {
            return null;
        }
        return super.drain(maxDrain, doDrain);
    }

    /**
     * 获取所有流体。
     * 用于合成表识别
     * @return 所有流体信息
     */
    public List<FluidStack> getFluidStacks() {
        return Arrays.stream(getTankProperties()).map(IFluidTankProperties::getContents).collect(Collectors.toList());
    }

    /**
     * 获取是否为空
     * @return 没有槽位
     */
    public boolean isEmpty() {
        return subHandlers.length == 0;
    }
}
