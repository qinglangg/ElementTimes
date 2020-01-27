package com.elementtimes.tutorial.client;

import com.elementtimes.tutorial.common.CommonProxy;
import com.elementtimes.tutorial.common.wire.simpleImpl.NetworkLoader;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * 客户端 Proxy
 * @author qinglan,ksgfk,luqin2007
 */
@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy {
    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);
        new NetworkLoader();
    }
}
