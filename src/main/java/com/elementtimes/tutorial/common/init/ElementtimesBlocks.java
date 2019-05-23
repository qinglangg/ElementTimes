package com.elementtimes.tutorial.common.init;

import com.elementtimes.tutorial.common.block.*;
import com.elementtimes.tutorial.common.block.base.BlockTileBase;
import com.elementtimes.tutorial.common.tileentity.TileCompressor;
import com.elementtimes.tutorial.common.tileentity.TileElementGenerator;
import com.elementtimes.tutorial.common.tileentity.TileFuelGenerator;
import com.elementtimes.tutorial.common.tileentity.TilePulverize;
import net.minecraft.block.Block;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class ElementtimesBlocks {
    public static final Block Woodessence = new Woodessence();
    public static final Block Leafessence = new Leafessence();
    public static final Block Stoneblock = new Stoneblock();
    public static final Block Cement = new Cement();
    public static final Block Cementandsteelbarmixture = new Cementandsteelbarmixture();
    public static final Block Copperore = new Copperore();
    public static final Block Copperbillet = new Copperbillet();
    public static final Block Sulfurore = new Sulfurore();
    public static final Block Calciumfluoride = new Calciumfluoride();
    public static final Block Platinumore = new Platinumore();
    public static final Block Platinumblock = new Platinumblock();
    public static final Block Diamondblock = new Diamondblock();   
    public static final Block Corncrop = new com.elementtimes.tutorial.common.block.CornCrop();
    public static final Block Corncropup = new CornCropUp();
    public static final Block Steelblock = new Steelblock();
    public static final Block pulverize
            = new BlockTileBase<>("pulverizer", "pulverizer", ElementtimesGUI.Pulverize, TilePulverize.class, true);
    public static final Block compressor
            = new BlockTileBase<>("compressor", "compressor", ElementtimesGUI.Compressor, TileCompressor.class, true);
    public static final Block elementGenerator
            = new BlockTileBase<>("elementGenerater", "elementGenerater", ElementtimesGUI.ElementGenerator, TileElementGenerator.class, true);
    public static final Block fuelGenerator
            = new BlockTileBase<>("fuelGenerator", "fuelGenerator", ElementtimesGUI.FuelGenerator, TileFuelGenerator.class, true);
}
