package com.elementtimes.tutorial.common.init;

import com.elementtimes.tutorial.client.tesr.TileSupportStandRender;
import com.elementtimes.tutorial.common.tileentity.TileSupportStand;
import net.minecraftforge.fml.client.registry.ClientRegistry;

/**
 * @author KSGFK create in 2019/6/12
 */
public class ElementTimesRender {
    public static void init() {
        ClientRegistry.bindTileEntitySpecialRenderer(TileSupportStand.class, new TileSupportStandRender());
    }
}
