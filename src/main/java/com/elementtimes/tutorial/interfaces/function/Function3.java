package com.elementtimes.tutorial.interfaces.function;

import net.minecraftforge.fluids.Fluid;

/**
 * 有三个输入，一个输出的函数
 * @author luqin2007
 */
@FunctionalInterface
public interface Function3<T1, T2, T3, R> {

    R apply(T1 v1, T2 v2, T3 v3);

    interface FluidCheck extends Function3<Integer, Fluid, Integer, Boolean> {
        @Override
        Boolean apply(Integer slot, Fluid fluid, Integer amount);
    }
}
