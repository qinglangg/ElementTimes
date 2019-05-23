package com.elementtimes.tutorial.common.init;

import com.elementtimes.tutorial.Elementtimes;
import com.elementtimes.tutorial.common.tileentity.TileCompressor;
import com.elementtimes.tutorial.common.tileentity.TileElementGenerator;
import com.elementtimes.tutorial.common.tileentity.TilePulverize;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * TileEntity初始化
 *
 * @author KSGFK create in 2019/2/17
 */
public class ElementtimesTile {
    public static void init() {
        GameRegistry.registerTileEntity(TileElementGenerator.class, new ResourceLocation(Elementtimes.MODID + ":" + "element_generater"));
        GameRegistry.registerTileEntity(TilePulverize.class, new ResourceLocation(Elementtimes.MODID + ":" + "pulverizer"));
        GameRegistry.registerTileEntity(TileCompressor.class, new ResourceLocation(Elementtimes.MODID + ":" + "compressor"));
    }
}
