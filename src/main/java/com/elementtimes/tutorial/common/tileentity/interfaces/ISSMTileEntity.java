package com.elementtimes.tutorial.common.tileentity.interfaces;

import com.elementtimes.tutorial.common.block.SupportStand;
import com.elementtimes.tutorial.common.block.interfaces.ISupportStandModule;

import java.util.Set;

public interface ISSMTileEntity {

    Set<String> getModules();

    void putModule(ISupportStandModule module);

    default void removeModule(ISupportStandModule module) {
        removeModule(module.getKey());
    }

    void removeModule(String key);

    Object getModuleObject(String key);

    default ISupportStandModule getModule(String key) {
        return SupportStand.MODULES.get(key);
    }

    void setActiveModule(String key);

    Object getActiveModuleObject();
}
