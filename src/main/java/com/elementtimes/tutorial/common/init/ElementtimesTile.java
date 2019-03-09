package com.elementtimes.tutorial.common.init;

import com.elementtimes.tutorial.Elementtimes;
import com.elementtimes.tutorial.common.tileentity.TileElementGenerater;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * TileEntity初始化
 *
 * @author KSGFK create in 2019/2/17
 */
public class ElementtimesTile {
    public static void init() {
        GameRegistry.registerTileEntity(TileElementGenerater.class, new ResourceLocation(Elementtimes.MODID + ":" + "element_generater"));
    }
}
