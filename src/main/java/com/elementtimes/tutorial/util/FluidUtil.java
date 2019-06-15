package com.elementtimes.tutorial.util;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.List;

/**
 * 流体工具类
 * @author luqin2007
 */
public class FluidUtil {

    /**
     * 将流体列表转化为 NBT 列表
     * @param fluids 流体列表
     * @return NBT 列表
     */
    public static NBTTagList toNBTList(List<FluidStack> fluids) {
        NBTTagList list = new NBTTagList();
        fluids.forEach(fluid -> list.appendTag(fluid.writeToNBT(new NBTTagCompound())));
        return list;
    }

    /**
     * 从 NBT 列表读取流体
     * @param list NBT 列表
     * @return 流体
     */
    public static List<FluidStack> fromNBTList(NBTTagList list) {
        int count = list.tagCount();
        List<FluidStack> fluidStacks = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            fluidStacks.add(FluidStack.loadFluidStackFromNBT(list.getCompoundTagAt(i)));
        }
        return fluidStacks;
    }

    public static final FluidStack EMPTY = new FluidStack(FluidRegistry.WATER, 0);
}
