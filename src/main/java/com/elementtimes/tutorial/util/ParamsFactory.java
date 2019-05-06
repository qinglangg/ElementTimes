package com.elementtimes.tutorial.util;

import java.util.function.Function;

/**
 * (好像没啥用，先放在这里
 *
 * @author KSGFK create in 2019/5/6
 */
public class ParamsFactory<T, R> implements IFactory<R> {
    private T arg;
    private Function<T, R> func;

    public ParamsFactory(Function<T, R> func, T arg) {
        this.arg = arg;
        this.func = func;
    }

    @Override
    public R get() {
        return func.apply(arg);
    }
}
