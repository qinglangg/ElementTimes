package com.elementtimes.tutorial.capability;

import com.elementtimes.tutorial.capability.component.CapabilityComponent;
import com.elementtimes.tutorial.capability.component.IComponentContain;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CapabilityLoader {
    @CapabilityInject(IComponentContain.class)
    public static Capability<IComponentContain> positionHistory;
    public CapabilityLoader(FMLPreInitializationEvent event)
    {
        CapabilityManager.INSTANCE.register(IComponentContain.class, new CapabilityComponent.Storage(),
                CapabilityComponent.Implementation.class);
    }
}
