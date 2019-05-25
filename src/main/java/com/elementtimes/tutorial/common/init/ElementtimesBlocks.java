package com.elementtimes.tutorial.common.init;

import com.elementtimes.tutorial.annotation.ModBlock;
import com.elementtimes.tutorial.annotation.ModOreDict;
import com.elementtimes.tutorial.common.block.*;
import com.elementtimes.tutorial.common.block.base.BlockTileBase;
import com.elementtimes.tutorial.common.block.machine.FuelGenerator;
import com.elementtimes.tutorial.common.block.machine.Furnace;
import com.elementtimes.tutorial.common.creativetabs.ModCreativeTabs;
import com.elementtimes.tutorial.common.tileentity.*;
import net.minecraft.block.Block;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class ElementtimesBlocks {
    @ModBlock(registerName = "woodessence", unlocalizedName = "woodessence")
    public static Block woodesSence = new Woodessence();
    @ModBlock(registerName = "leafessence", unlocalizedName = "leafessence")
    public static Block leafesSence = new Leafessence();
    @ModBlock(registerName = "stoneblock", unlocalizedName = "stoneblock", creativeTab = ModCreativeTabs.Chemical)
    public static Block stoneBlock = new Stoneblock();
    @ModBlock(registerName = "cement", unlocalizedName = "cement", creativeTab = ModCreativeTabs.Chemical)
    public static Block cement = new Cement();
    @ModBlock(registerName = "cementandsteelbarmixture", unlocalizedName = "cementandsteelbarmixture", creativeTab = ModCreativeTabs.Chemical)
    public static Block cementAndSteelBarMixture = new Cementandsteelbarmixture();
    @ModBlock(registerName = "copperore", unlocalizedName = "copperore", creativeTab = ModCreativeTabs.Ore)
    @ModOreDict("oreCopper")
    public static Block copperOre = new Copperore();
    @ModBlock(registerName = "copperbillet", unlocalizedName = "copperbillet", creativeTab = ModCreativeTabs.Ore)
    @ModOreDict("blockCopper")
    public static Block copperBillet = new Copperbillet();
    @ModBlock(registerName = "sulfurore", unlocalizedName = "sulfurore", creativeTab = ModCreativeTabs.Ore)
    public static Block sulfurOre = new Sulfurore();
    @ModBlock(registerName = "calciumfluoride", unlocalizedName = "calciumfluoride", creativeTab = ModCreativeTabs.Ore)
    public static Block calciumFluoride = new Calciumfluoride();
    @ModBlock(registerName = "platinumore", unlocalizedName = "platinumore", creativeTab = ModCreativeTabs.Ore)
    @ModOreDict("orePlatinum")
    public static Block platinumOre = new Platinumore();
    @ModBlock(registerName = "platinumblock", unlocalizedName = "platinumblock", creativeTab = ModCreativeTabs.Ore)
    @ModOreDict("blockPlatinum")
    public static Block platinumBlock = new Platinumblock();
    @ModBlock(registerName = "diamondblock", unlocalizedName = "diamondblock", creativeTab = ModCreativeTabs.Industry)
    public static Block diamondBlock = new Diamondblock();
    @ModBlock(registerName = "corn_crop", unlocalizedName = "cornCrop", creativeTab = ModCreativeTabs.None)
    public static Block cornCrop = new com.elementtimes.tutorial.common.block.CornCrop();
    @ModBlock(registerName = "corn_crop_up", unlocalizedName = "cornCropUp", creativeTab = ModCreativeTabs.None)
    public static Block cornCropUp = new CornCropUp();
    @ModBlock(registerName = "steelblock", unlocalizedName = "steelblock", creativeTab = ModCreativeTabs.Industry)
    @ModOreDict("blockSteel")
    public static Block steelBlock = new Steelblock();
    @ModBlock(registerName = "pulverizer", unlocalizedName = "pulverizer", creativeTab = ModCreativeTabs.Industry)
    @ModBlock.TileEntity(name = "pulverizer", clazz = "com.elementtimes.tutorial.common.tileentity.TilePulverize")
    public static Block pulverize = new BlockTileBase<>(ElementtimesGUI.Pulverize, TilePulverize.class, true);
    @ModBlock(registerName = "compressor", unlocalizedName = "compressor", creativeTab = ModCreativeTabs.Industry)
    @ModBlock.TileEntity(name = "compressor", clazz = "com.elementtimes.tutorial.common.tileentity.TileCompressor")
    public static Block compressor = new BlockTileBase<>(ElementtimesGUI.Compressor, TileCompressor.class, true);
    @ModBlock(registerName = "rebuild", unlocalizedName = "rebuild", creativeTab = ModCreativeTabs.None) // TODO
    @ModBlock.TileEntity(name = "rebuild", clazz = "com.elementtimes.tutorial.common.tileentity.TileRebuild")
    public static Block rebuild = new BlockTileBase<>(ElementtimesGUI.Rebuild, TileRebuild.class, true);
    @ModBlock(registerName = "elementGenerater", unlocalizedName = "elementGenerater", creativeTab = ModCreativeTabs.Industry)
    @ModBlock.TileEntity(name = "element_generator", clazz = "com.elementtimes.tutorial.common.tileentity.TileElementGenerator")
    public static Block elementGenerator = new BlockTileBase<>(ElementtimesGUI.ElementGenerator, TileElementGenerator.class, true);
    @ModBlock(registerName = "fuelGenerator", unlocalizedName = "fuelGenerator", creativeTab = ModCreativeTabs.Industry)
    @ModBlock.TileEntity(name = "fuel_generator", clazz = "com.elementtimes.tutorial.common.tileentity.TileFuelGenerator")
    @ModBlock.StateMapperCustom
    @ModBlock.StateMap
    public static FuelGenerator fuelGenerator;
    @ModBlock(registerName = "furnace", unlocalizedName = "efurnace", creativeTab = ModCreativeTabs.Industry)
    @ModBlock.TileEntity(name = "furnace", clazz = "com.elementtimes.tutorial.common.tileentity.TileFurnace")
    @ModBlock.StateMapperCustom
    @ModBlock.StateMap
    public static Furnace furnace;
}
