package com.elementtimes.tutorial.util;

import java.util.function.Supplier;

/**
 * 其实没啥用（
 *
 * @author KSGFK create in 2019/5/6
 */
public class BaseFactory<T> implements IFactory<T> {
    private Supplier<T> func;

    public BaseFactory(Supplier<T> func) {
        this.func = func;
    }

    public T get() {
        return func.get();
    }
}
