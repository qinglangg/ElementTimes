package com.elementtimes.elementtimes.common.block.stand.capability;

import com.elementtimes.elementtimes.common.block.stand.module.ISupportStandModule;
import net.minecraftforge.common.util.LazyOptional;



public class ModuleCap<T> {

    public final String key;

    public final ISupportStandModule module;
    public final LazyOptional<T> capabilityOpt;
    public T capability;

    public ModuleCap(String key, ISupportStandModule module, LazyOptional<T> capabilityOpt) {
        this.key = key;
        this.module = module;
        this.capabilityOpt = capabilityOpt;
        reload();
    }

    public ModuleCap(ISupportStandModule module, LazyOptional<T> capabilityOpt) {
        this(module.getKey(), module, capabilityOpt);
    }

    public void reload() {
        capability = capabilityOpt.orElseThrow(RuntimeException::new);
    }
}
