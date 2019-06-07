package com.elementtimes.tutorial.common.init;

import com.elementtimes.tutorial.annotation.ModBlock;
import com.elementtimes.tutorial.annotation.ModOreDict;
import com.elementtimes.tutorial.common.block.*;
import com.elementtimes.tutorial.common.block.base.BaseClosableMachine;
import com.elementtimes.tutorial.common.block.base.BlockTileBase;
import com.elementtimes.tutorial.common.creativetabs.ModCreativeTabs;
import com.elementtimes.tutorial.common.tileentity.*;
import net.minecraft.block.Block;
import net.minecraftforge.fml.common.Mod;

/**
 * 所有方块
 * @author KSGFK
 */
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
    @ModBlock(registerName = "rubber_sapling", unlocalizedName = "rubber_sapling")
    public static Block rubberSapling = new RubberSapling();
    @ModBlock(registerName = "rubber_log", unlocalizedName = "rubber_log")
    @ModOreDict("logWood")
    public static Block rubberLog = new RubberLog();
    @ModBlock(registerName = "rubber_leaf", unlocalizedName = "rubber_leaf")
    public static Block rubberLeaf = new RubberLeaf();

    @ModBlock(registerName = "furnace", unlocalizedName = "furnace", creativeTab = ModCreativeTabs.Industry)
    @ModBlock.TileEntity(name = "furnace", clazz = "com.elementtimes.tutorial.common.tileentity.TileFurnace")
    @ModBlock.StateMapperCustom
    @ModBlock.StateMap
    public static Block furnace = new BaseClosableMachine<>(ElementtimesGUI.FURNACE, TileFurnace.class, false);
    @ModBlock(registerName = "fuelGenerator", unlocalizedName = "fuelGenerator", creativeTab = ModCreativeTabs.Industry)
    @ModBlock.TileEntity(name = "fuel_generator", clazz = "com.elementtimes.tutorial.common.tileentity.TileGeneratorFuel")
    @ModBlock.StateMapperCustom
    @ModBlock.StateMap
    public static Block fuelGenerator = new BaseClosableMachine<>(ElementtimesGUI.FUEL_GENERATOR, TileGeneratorFuel.class, false);
    @ModBlock(registerName = "rebuild", unlocalizedName = "rebuild", creativeTab = ModCreativeTabs.Industry)
    @ModBlock.TileEntity(name = "rebuild", clazz = "com.elementtimes.tutorial.common.tileentity.TileRebuild")
    @ModBlock.StateMapperCustom
    @ModBlock.StateMap
    public static Block rebuild = new BaseClosableMachine<>(ElementtimesGUI.REBUILD, TileRebuild.class, false);
    @ModBlock(registerName = "compressor", unlocalizedName = "compressor", creativeTab = ModCreativeTabs.Industry)
    @ModBlock.TileEntity(name = "compressor", clazz = "com.elementtimes.tutorial.common.tileentity.TileCompressor")
    @ModBlock.StateMapperCustom
    @ModBlock.StateMap
    public static Block compressor = new BaseClosableMachine<>(ElementtimesGUI.COMPRESSOR, TileCompressor.class, false);
    @ModBlock(registerName = "forming", unlocalizedName = "forming", creativeTab = ModCreativeTabs.Industry)
    @ModBlock.TileEntity(name = "forming", clazz = "com.elementtimes.tutorial.common.tileentity.TileForming")
    @ModBlock.StateMapperCustom
    @ModBlock.StateMap
    public static Block forming = new BaseClosableMachine<>(ElementtimesGUI.REBUILD, TileForming.class, false);
    @ModBlock(registerName = "extractor", unlocalizedName = "extractor", creativeTab = ModCreativeTabs.Industry)
    @ModBlock.TileEntity(name = "extractor", clazz = "com.elementtimes.tutorial.common.tileentity.TileExtractor")
    @ModBlock.StateMapperCustom
    @ModBlock.StateMap
    public static Block extractor = new BaseClosableMachine<>(ElementtimesGUI.EXTRACTOR, TileExtractor.class, false);

    @ModBlock(registerName = "pulverizer", unlocalizedName = "pulverizer", creativeTab = ModCreativeTabs.Industry)
    @ModBlock.TileEntity(name = "pulverizer", clazz = "com.elementtimes.tutorial.common.tileentity.TilePulverize")
    public static Block pulverize = new BlockTileBase<>(ElementtimesGUI.PULVERIZE, TilePulverize.class, false);
    @ModBlock(registerName = "elementGenerater", unlocalizedName = "elementGenerater", creativeTab = ModCreativeTabs.Industry)
    @ModBlock.TileEntity(name = "element_generator", clazz = "com.elementtimes.tutorial.common.tileentity.TileGeneratorElement")
    public static Block elementGenerator = new BlockTileBase<>(ElementtimesGUI.ELEMENT_GENERATOR, TileGeneratorElement.class, true);

}
