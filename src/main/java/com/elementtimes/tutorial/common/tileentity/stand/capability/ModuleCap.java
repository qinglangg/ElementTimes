package com.elementtimes.tutorial.common.tileentity.stand.capability;

import com.elementtimes.tutorial.common.tileentity.stand.module.ISupportStandModule;

public class ModuleCap<T> {

    public final String key;
    public final ISupportStandModule module;
    public final T capability;

    public ModuleCap(String key, ISupportStandModule module, T capability) {
        this.key = key;
        this.module = module;
        this.capability = capability;
    }

    public ModuleCap(ISupportStandModule module, T capability) {
        this(module.getKey(), module, capability);
    }
}
