package com.elementtimes.tutorial.client;

import com.elementtimes.tutorial.client.tesr.TileSupportStandRender;
import com.elementtimes.tutorial.common.CommonProxy;
import com.elementtimes.tutorial.common.tileentity.TileSupportStand;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/**
 * 客户端 Proxy
 * @author KSGFK
 */
public class ClientProxy extends CommonProxy {
    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);

        ClientRegistry.bindTileEntitySpecialRenderer(TileSupportStand.class, new TileSupportStandRender());
    }
}
