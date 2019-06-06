package com.elementtimes.tutorial.util;

import com.elementtimes.tutorial.common.capability.impl.TankHandler;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FluidUtil {

    public static NBTTagList toNBTList(List<FluidStack> fluids) {
        NBTTagList list = new NBTTagList();
        fluids.forEach(fluid -> list.appendTag(fluid.writeToNBT(new NBTTagCompound())));
        return list;
    }

    public static List<FluidStack> fromNBTList(NBTTagList list) {
        int count = list.tagCount();
        List<FluidStack> fluidStacks = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            fluidStacks.add(FluidStack.loadFluidStackFromNBT(list.getCompoundTagAt(i)));
        }
        return fluidStacks;
    }
}
