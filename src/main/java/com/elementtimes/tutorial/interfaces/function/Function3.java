package com.elementtimes.tutorial.interfaces.function;

import com.elementtimes.tutorial.other.MachineRecipeHandler;

/**
 * 有五个输入的方法
 * 一个函数接口罢了
 *
 * @author luqin2007
 */
@FunctionalInterface
public interface Function3<T1, T2, T3, R> {

    /**
     * 一个函数接口罢了
     * @param v1 param1
     * @param v2 param2
     * @param v3 param3
     * @return return
     */
    R apply(T1 v1, T2 v2, T3 v3);

    @FunctionalInterface
    interface Stack<T> extends Function3<MachineRecipeHandler.MachineRecipe, Integer, T, T> {

        /**
         * 根据输入和合成表，获取匹配的物品
         * @param recipe 合成表
         * @param slot 输入物品的槽位
         * @param input 输入的物品
         * @return 合成所需的物品，包括数量
         */
        @Override
        T apply(MachineRecipeHandler.MachineRecipe recipe, Integer slot, T input);
    }
}
