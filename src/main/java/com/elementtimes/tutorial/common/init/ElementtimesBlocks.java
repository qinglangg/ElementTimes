package com.elementtimes.tutorial.common.init;

import com.elementtimes.tutorial.annotation.ModBlock;
import com.elementtimes.tutorial.annotation.ModOreDict;
import com.elementtimes.tutorial.annotation.enums.GenType;
import com.elementtimes.tutorial.common.block.BlockSupportStand;
import com.elementtimes.tutorial.common.block.CornCropUp;
import com.elementtimes.tutorial.common.block.OreSalt;
import com.elementtimes.tutorial.common.block.Sulfurore;
import com.elementtimes.tutorial.common.block.base.BaseClosableMachine;
import com.elementtimes.tutorial.common.block.base.BlockTileBase;
import com.elementtimes.tutorial.common.block.machine.Compressor;
import com.elementtimes.tutorial.common.block.tree.*;
import com.elementtimes.tutorial.common.creativetabs.ModCreativeTabs;
import com.elementtimes.tutorial.common.tileentity.*;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.fml.common.Mod;

/**
 * 所有方块
 *
 * @author KSGFK
 */
@Mod.EventBusSubscriber
public class ElementtimesBlocks {

    // Plant

    @ModBlock(registerName = "corn_crop", unlocalizedName = "cornCrop", creativeTab = ModCreativeTabs.None)
    public static Block cornCrop = new com.elementtimes.tutorial.common.block.CornCrop();
    @ModBlock(registerName = "corn_crop_up", unlocalizedName = "cornCropUp", creativeTab = ModCreativeTabs.None)
    public static Block cornCropUp = new CornCropUp();
    @ModBlock(registerName = "rubber_sapling", unlocalizedName = "rubber_sapling", creativeTab = ModCreativeTabs.Agriculture)
    @ModBlock.WorldGenClass(value = "com.elementtimes.tutorial.world.gen.RubberGenerator$RubberNatureGenerator", type = GenType.Tree)
    public static Block rubberSapling = new RubberSapling();
    @ModBlock(registerName = "rubber_log", unlocalizedName = "rubber_log", burningTime = 300, creativeTab = ModCreativeTabs.Agriculture)
    @ModOreDict("logWood")
    public static Block rubberLog = new RubberLog();
    @ModBlock(registerName = "rubber_leaf", unlocalizedName = "rubber_leaf", creativeTab = ModCreativeTabs.Agriculture)
    public static Block rubberLeaf = new RubberLeaf();
    @ModBlock(creativeTab = ModCreativeTabs.Agriculture)
    public static Block saplingEssence = new EssenceSapling();
    @ModBlock(creativeTab = ModCreativeTabs.Agriculture)
    @ModBlock.HarvestLevel(toolClass = "axe")
    @ModOreDict("logWood")
    public static Block woodesSence = new EssenceLog().setHardness(50f).setResistance(15f).setLightLevel(50f);
    @ModBlock(creativeTab = ModCreativeTabs.Agriculture)
    public static Block leafesSence = new EssenceLeaf();

    // ore

    @ModBlock(creativeTab = ModCreativeTabs.Ore)
    @ModOreDict("oreCopper")
    @ModBlock.HarvestLevel
    @ModBlock.WorldGen
    public static Block copperOre = new Block(Material.ROCK).setHardness(20f).setResistance(10f);
    @ModBlock(creativeTab = ModCreativeTabs.Ore)
    @ModOreDict("orePlatinum")
    @ModBlock.HarvestLevel(level = 3)
    @ModBlock.WorldGen(YMin = 5, YRange = 10, probability = 0.3f)
    public static Block platinumOre = new Block(Material.ROCK).setHardness(20f).setResistance(10f);
    @ModBlock(creativeTab = ModCreativeTabs.Ore)
    @ModOreDict("oreSilver")
    @ModBlock.HarvestLevel
    @ModBlock.WorldGen
    public static Block SilverOre = new Block(Material.ROCK).setHardness(20f).setResistance(10f);
    @ModBlock(creativeTab = ModCreativeTabs.Ore)
    @ModOreDict("oreSalt")
    @ModBlock.WorldGenClass("com.elementtimes.tutorial.world.gen.SaltGenerator")
    public static OreSalt oreSalt = new OreSalt();
    @ModBlock(creativeTab = ModCreativeTabs.Ore)
    @ModOreDict("oreSulfur")
    @ModBlock.WorldGen
    public static Block sulfurOre = new Sulfurore();
    @ModBlock(creativeTab = ModCreativeTabs.Ore)
    @ModBlock.HarvestLevel
    @ModBlock.WorldGen
    public static Block calciumFluoride = new Block(Material.ROCK).setHardness(20f).setResistance(10f);

    // Block

    @ModBlock(creativeTab = ModCreativeTabs.Ore)
    @ModOreDict("blockCopper")
    @ModBlock.HarvestLevel
    public static Block copperBillet = new Block(Material.ROCK).setHardness(50f).setResistance(15f);
    @ModBlock(creativeTab = ModCreativeTabs.Ore)
    @ModOreDict("blockPlatinum")
    @ModBlock.HarvestLevel(level = 3)
    public static Block platinumBlock = new Block(Material.ROCK).setHardness(50f).setResistance(15f).setLightLevel(50f);
    @ModBlock(creativeTab = ModCreativeTabs.Ore)
    @ModOreDict("blockSilver")
    @ModBlock.HarvestLevel
    public static Block blockSilver = new Block(Material.ROCK).setHardness(50f).setResistance(15f).setLightLevel(50f);
    @ModBlock(creativeTab = ModCreativeTabs.Industry)
    @ModOreDict("blockSteel")
    @ModBlock.HarvestLevel
    public static Block steelBlock = new Block(Material.ROCK).setHardness(50f).setResistance(15f).setLightLevel(50f);
    @ModBlock(creativeTab = ModCreativeTabs.Industry)
    @ModBlock.HarvestLevel(level = 3)
    public static Block diamondBlock = new Block(Material.ROCK).setHardness(100f).setResistance(30f).setLightLevel(100f);

    // Machine

    @ModBlock(creativeTab = ModCreativeTabs.Industry)
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
    @ModBlock(creativeTab = ModCreativeTabs.Industry)
    @ModBlock.TileEntity(name = "compressor", clazz = "com.elementtimes.tutorial.common.tileentity.TileCompressor")
    @ModBlock.StateMapperCustom
    @ModBlock.StateMap
    public static Block compressor = new Compressor();
    @ModBlock(creativeTab = ModCreativeTabs.Industry)
    @ModBlock.TileEntity(name = "forming", clazz = "com.elementtimes.tutorial.common.tileentity.TileForming")
    @ModBlock.StateMapperCustom
    @ModBlock.StateMap
    public static Block forming = new BaseClosableMachine<>(ElementtimesGUI.FORMING, TileForming.class, false);
    @ModBlock(creativeTab = ModCreativeTabs.Industry)
    @ModBlock.TileEntity(name = "extractor", clazz = "com.elementtimes.tutorial.common.tileentity.TileExtractor")
    @ModBlock.StateMapperCustom
    @ModBlock.StateMap
    public static Block extractor = new BaseClosableMachine<>(ElementtimesGUI.EXTRACTOR, TileExtractor.class, false);
    @ModBlock(creativeTab = ModCreativeTabs.Industry)
    @ModBlock.TileEntity(name = "pulverizer", clazz = "com.elementtimes.tutorial.common.tileentity.TilePulverize")
    public static Block pulverizer = new BlockTileBase<>(ElementtimesGUI.PULVERIZE, TilePulverize.class, false);
    @ModBlock(registerName = "elementGenerater", unlocalizedName = "elementGenerater", creativeTab = ModCreativeTabs.Industry)
    @ModBlock.TileEntity(name = "element_generator", clazz = "com.elementtimes.tutorial.common.tileentity.TileGeneratorElement")
    public static Block elementGenerator = new BlockTileBase<>(ElementtimesGUI.ELEMENT_GENERATOR, TileGeneratorElement.class, true);
    @ModBlock(registerName = "support_stand", unlocalizedName = "support_stand", creativeTab = ModCreativeTabs.Industry)
    @ModBlock.TileEntity(name = "support_stand", clazz = "com.elementtimes.tutorial.common.tileentity.TileSupportStand")
    public static Block supportStand = new BlockSupportStand();

    // 其他

    @ModBlock(creativeTab = ModCreativeTabs.Chemical)
    @ModBlock.HarvestLevel
    public static Block stoneBlock = new Block(Material.ROCK).setHardness(100f).setResistance(15f);
    @ModBlock(creativeTab = ModCreativeTabs.Chemical)
    @ModBlock.HarvestLevel(level = 3)
    public static Block cement = new Block(Material.ROCK).setHardness(1000f).setResistance(30f);
    @ModBlock(creativeTab = ModCreativeTabs.Chemical)
    @ModBlock.HarvestLevel(level = 4)
    public static Block cementAndSteelBarMixture = new Block(Material.ROCK).setHardness(10000f).setResistance(150f);
    @ModBlock(creativeTab = ModCreativeTabs.Industry)
    @ModBlock.HarvestLevel
    public static Block blockMultiSilverCopper = new Block(Material.IRON);
    @ModBlock(creativeTab = ModCreativeTabs.Industry)
    @ModBlock.HarvestLevel
    public static Block blockMultiWoodStone = new Block(Material.IRON);
    @ModBlock(creativeTab = ModCreativeTabs.Industry)
    @ModBlock.HarvestLevel
    public static Block blockMultiGoldPlatinum = new Block(Material.IRON);
    @ModBlock(creativeTab = ModCreativeTabs.Industry)
    @ModBlock.HarvestLevel
    public static Block blockMultiCarbonSteel = new Block(Material.IRON);

}
