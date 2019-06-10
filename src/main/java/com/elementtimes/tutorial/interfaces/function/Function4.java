package com.elementtimes.tutorial.interfaces.function;

import com.elementtimes.tutorial.other.recipe.MachineRecipe;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import java.util.List;

/**
 * 有五个输入的方法
 * 一个函数接口罢了
 *
 * @author luqin2007
 */
@FunctionalInterface
public interface Function4<T1, T2, T3, T4, R> {

    /**
     * 一个函数接口罢了
     * @param v1 param1
     * @param v2 param2
     * @param v3 param3
     * @param v4 param4
     * @return return
     */
    R apply(T1 v1, T2 v2, T3 v3, T4 v4);

    @FunctionalInterface
    interface Stack<T> extends Function4<MachineRecipe, List<ItemStack>, List<FluidStack>, Integer, T> {

        /**
         * 根据输入和合成表，获取匹配的物品
         * @param recipe 合成表
         * @param inputItems 输入物品
         * @param inputFluids 输入流体
         * @param slot 槽位
         * @return 合成所需的物品，包括数量
         */
        @Override
        T apply(MachineRecipe recipe, List<ItemStack> inputItems, List<FluidStack> inputFluids, Integer slot);
    }
}
