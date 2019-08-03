package com.elementtimes.tutorial.other.lifecycle;

import com.elementtimes.tutorial.interfaces.tileentity.IMachineLifecycle;

import java.util.function.BooleanSupplier;

/**
 * 机器运行是有前提的，当 premise.getAsBoolean 返回 false 时不会进行反应，已经开始的反应也会暂停
 * @author luqin2007
 */
public class PremiseMachineLifecycle implements IMachineLifecycle {

    private final BooleanSupplier premise;

    public PremiseMachineLifecycle(BooleanSupplier premise) {
        this.premise = premise;
    }

    @Override
    public boolean onCheckStart() {
        return premise.getAsBoolean();
    }

    @Override
    public boolean onLoop() {
        return premise.getAsBoolean();
    }

    @Override
    public boolean onCheckResume() {
        return premise.getAsBoolean();
    }
}
