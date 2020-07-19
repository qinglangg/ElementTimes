package com.elementtimes.elementtimes.common.pipeline;



public class PathInfo<T> {
    public final int totalTick;
    public final T stack;

    public PathInfo(int totalTick, T transformElement) {
        this.totalTick = totalTick;
        this.stack = transformElement;
    }
}
