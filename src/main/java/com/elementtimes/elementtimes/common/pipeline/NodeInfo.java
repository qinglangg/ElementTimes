package com.elementtimes.elementtimes.common.pipeline;



public class NodeInfo {
    public final int stayTick;
    public final int count;
    public final int totalTick;

    public NodeInfo(int stayTick, int totalTick, int elementCount) {
        this.stayTick = stayTick;
        this.totalTick = totalTick;
        this.count = elementCount;
    }
}
