package com.elementtimes.tutorial.common.init;

import com.elementtimes.tutorial.annotation.annotations.ModBlock;
import com.elementtimes.tutorial.annotation.annotations.ModOreDict;
import com.elementtimes.tutorial.annotation.enums.GenType;
import com.elementtimes.tutorial.common.block.*;
import com.elementtimes.tutorial.common.block.base.BaseClosableMachine;
import com.elementtimes.tutorial.common.block.base.BlockTileBase;
import com.elementtimes.tutorial.common.block.machine.Compressor;
import com.elementtimes.tutorial.common.block.tree.*;
import com.elementtimes.tutorial.common.creativetabs.ModCreativeTabs;
import com.elementtimes.tutorial.common.tileentity.*;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.fml.common.Mod;

/**
 * 所有方块
 *
 * @author KSGFK
 */
@SuppressWarnings({"WeakerAccess", "unused"})
@Mod.EventBusSubscriber
public class ElementtimesBlocks {

    // Plant

    @ModBlock(registerName = "corn_crop", unlocalizedName = "cornCrop", creativeTab = ModCreativeTabs.None)
    public static Block cornCrop = new com.elementtimes.tutorial.common.block.CornCrop();
    @ModBlock(registerName = "corn_crop_up", unlocalizedName = "cornCropUp", creativeTab = ModCreativeTabs.None)
    public static Block cornCropUp = new CornCropUp();
    @ModBlock(registerName = "rubber_sapling", unlocalizedName = "rubber_sapling", creativeTab = ModCreativeTabs.Agriculture)
    @ModBlock.WorldGenClass(value = "com.elementtimes.tutorial.common.generator.RubberGenerator$RubberNatureGenerator", type = GenType.Tree)
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
    @ModOreDict("oreTin")
    @ModBlock.HarvestLevel
    @ModBlock.WorldGen
    public static Block tinOre = new Block(Material.ROCK).setHardness(15f).setResistance(10f);
    @ModBlock(creativeTab = ModCreativeTabs.Ore)
    @ModOreDict("oreLead")
    @ModBlock.HarvestLevel
    @ModBlock.WorldGen
    public static Block leadOre = new Block(Material.ROCK).setHardness(15f).setResistance(10f);
    @ModBlock(creativeTab = ModCreativeTabs.Ore)
    @ModOreDict("oreCopper")
    @ModBlock.HarvestLevel
    @ModBlock.WorldGen
    public static Block copperOre = new Block(Material.ROCK).setHardness(15f).setResistance(10f);
    @ModBlock(creativeTab = ModCreativeTabs.Ore)
    @ModOreDict("orePlatinum")
    @ModBlock.HarvestLevel(level = 3)
    @ModBlock.WorldGen(YMin = 5, YRange = 10, probability = 0.3f)
    public static Block platinumOre = new Block(Material.ROCK).setHardness(20f).setResistance(10f);
    @ModBlock(creativeTab = ModCreativeTabs.Ore)
    @ModOreDict("oreSilver")
    @ModBlock.HarvestLevel
    @ModBlock.WorldGen
    public static Block SilverOre = new Block(Material.ROCK).setHardness(15f).setResistance(10f);
    @ModBlock(creativeTab = ModCreativeTabs.Ore)
    @ModOreDict("oreSalt")
    @ModBlock.WorldGenClass("com.elementtimes.tutorial.common.generator.SaltGenerator")
    public static OreSalt oreSalt = new OreSalt();
    @ModBlock(creativeTab = ModCreativeTabs.Ore)
    @ModOreDict("oreSulfur")
    @ModBlock.WorldGen
    public static Block sulfurOre = new Sulfurore();
    @ModBlock(creativeTab = ModCreativeTabs.Ore)
    @ModBlock.HarvestLevel
    @ModBlock.WorldGen
    public static Block calciumFluoride = new Block(Material.ROCK).setHardness(15f).setResistance(10f);
    @ModBlock(creativeTab = ModCreativeTabs.Ore)
    @ModOreDict("oreUranium")
    @ModBlock.HarvestLevel(level = 3)
    @ModBlock.WorldGen(YMin = 5, YRange = 10, probability = 0.3f)
    public static Block uraniumOre = new Block(Material.ROCK).setHardness(20f).setResistance(10f);


    // Block
    
    @ModBlock(creativeTab = ModCreativeTabs.Ore)
    @ModOreDict("blockTin")
    @ModBlock.HarvestLevel
    public static Block blockTin = new Block(Material.ROCK).setHardness(50f).setResistance(20f);
    @ModBlock(creativeTab = ModCreativeTabs.Ore)
    @ModOreDict("blockLead")
    @ModBlock.HarvestLevel
    public static Block blockLead = new Block(Material.ROCK).setHardness(50f).setResistance(20f);
    @ModBlock(creativeTab = ModCreativeTabs.Ore)
    @ModOreDict("blockCopper")
    @ModBlock.HarvestLevel
    public static Block copperBillet = new Block(Material.ROCK).setHardness(50f).setResistance(20f);
    @ModBlock(creativeTab = ModCreativeTabs.Ore)
    @ModOreDict("blockPlatinum")
    @ModBlock.HarvestLevel(level = 3)
    public static Block platinumBlock = new Block(Material.ROCK).setHardness(50f).setResistance(20f).setLightLevel(50f);
    @ModBlock(creativeTab = ModCreativeTabs.Ore)
    @ModOreDict("blockSilver")
    @ModBlock.HarvestLevel
    public static Block blockSilver = new Block(Material.ROCK).setHardness(50f).setResistance(20f).setLightLevel(50f);
    @ModBlock(creativeTab = ModCreativeTabs.Industry)
    @ModOreDict("blockSteel")
    @ModBlock.HarvestLevel
    public static Block steelBlock = new Block(Material.ROCK).setHardness(50f).setResistance(20f).setLightLevel(50f);
    @ModBlock(creativeTab = ModCreativeTabs.Industry)
    @ModBlock.HarvestLevel(level = 3)
    public static Block diamondBlock = new Block(Material.ROCK).setHardness(100f).setResistance(35f).setLightLevel(100f);

    // Machine

    @ModBlock(creativeTab = ModCreativeTabs.Industry)
    @ModBlock.TileEntity(name = "furnace", clazz = "com.elementtimes.tutorial.common.tileentity.TileFurnace")
    @ModBlock.StateMapperCustom
    @ModBlock.StateMap
    public static Block furnace = new BaseClosableMachine<>(TileFurnace.class);
    @ModBlock(registerName = "fuelGenerator", unlocalizedName = "fuelGenerator", creativeTab = ModCreativeTabs.Industry)
    @ModBlock.TileEntity(name = "fuel_generator", clazz = "com.elementtimes.tutorial.common.tileentity.TileGeneratorFuel")
    @ModBlock.StateMapperCustom
    @ModBlock.StateMap
    public static Block fuelGenerator = new BaseClosableMachine<>(TileGeneratorFuel.class);
    @ModBlock(registerName = "rebuild", unlocalizedName = "rebuild", creativeTab = ModCreativeTabs.Industry)
    @ModBlock.TileEntity(name = "rebuild", clazz = "com.elementtimes.tutorial.common.tileentity.TileRebuild")
    @ModBlock.StateMapperCustom
    @ModBlock.StateMap
    public static Block rebuild = new BaseClosableMachine<>(TileRebuild.class);
    @ModBlock(creativeTab = ModCreativeTabs.Industry)
    @ModBlock.TileEntity(name = "compressor", clazz = "com.elementtimes.tutorial.common.tileentity.TileCompressor")
    @ModBlock.StateMapperCustom
    @ModBlock.StateMap
    @ModBlock.AnimTESR
    public static Block compressor = new Compressor();
    @ModBlock(creativeTab = ModCreativeTabs.Industry)
    @ModBlock.TileEntity(name = "forming", clazz = "com.elementtimes.tutorial.common.tileentity.TileForming")
    @ModBlock.StateMapperCustom
    @ModBlock.StateMap
    public static Block forming = new BaseClosableMachine<>(TileForming.class);
    @ModBlock(creativeTab = ModCreativeTabs.Industry)
    @ModBlock.TileEntity(name = "extractor", clazz = "com.elementtimes.tutorial.common.tileentity.TileExtractor")
    @ModBlock.StateMapperCustom
    @ModBlock.StateMap
    public static Block extractor = new BaseClosableMachine<>(TileExtractor.class);
    @ModBlock(creativeTab = ModCreativeTabs.Industry)
    @ModBlock.TileEntity(name = "pulverizer", clazz = "com.elementtimes.tutorial.common.tileentity.TilePulverize")
    public static Block pulverizer = new BlockTileBase<>(TilePulverize.class);
    @ModBlock(registerName = "elementGenerater", unlocalizedName = "elementGenerater", creativeTab = ModCreativeTabs.Industry)
    @ModBlock.TileEntity(name = "element_generator", clazz = "com.elementtimes.tutorial.common.tileentity.TileGeneratorElement")
    public static Block elementGenerator = new BlockTileBase<>(TileGeneratorElement.class);
    @ModBlock(registerName = "support_stand", unlocalizedName = "support_stand", creativeTab = ModCreativeTabs.Chemical)
    @ModBlock.TileEntity(name = "support_stand", clazz = "com.elementtimes.tutorial.common.tileentity.TileSupportStand")
    @ModBlock.TESR("com.elementtimes.tutorial.client.tesr.TileSupportStandRender")
    public static Block supportStand = new BlockSupportStand();
    @ModBlock(creativeTab = ModCreativeTabs.Industry)
    @ModBlock.TileEntity(name = "solidmelter", clazz = "com.elementtimes.tutorial.common.tileentity.TileSolidMelter")
    @ModBlock.StateMapperCustom
    @ModBlock.StateMap
    public static Block solidMelter = new BaseClosableMachine<>(TileSolidMelter.class);
    @ModBlock(creativeTab = ModCreativeTabs.Industry)
    @ModBlock.TileEntity(name = "condenser", clazz = "com.elementtimes.tutorial.common.tileentity.TileCondenser")
    @ModBlock.StateMapperCustom
    @ModBlock.StateMap
    public static Block condenser = new BaseClosableMachine<>(TileCondenser.class);
    @ModBlock(creativeTab = ModCreativeTabs.Industry)
    @ModBlock.TileEntity(name = "fluidreactor", clazz = "com.elementtimes.tutorial.common.tileentity.TileFluidReactor")
    @ModBlock.StateMapperCustom
    @ModBlock.StateMap
    public static Block fluidReactor = new BaseClosableMachine<>(TileFluidReactor.class);
    @ModBlock(creativeTab = ModCreativeTabs.Industry)
    @ModBlock.TileEntity(name = "solidreactor", clazz = "com.elementtimes.tutorial.common.tileentity.TileSolidReactor")
    @ModBlock.StateMapperCustom
    @ModBlock.StateMap
    public static Block solidReactor = new BaseClosableMachine<>(TileSolidReactor.class);
    @ModBlock(creativeTab = ModCreativeTabs.Industry)
    @ModBlock.TileEntity(name = "fluidheater", clazz = "com.elementtimes.tutorial.common.tileentity.TileFluidHeater")
    @ModBlock.StateMapperCustom
    @ModBlock.StateMap
    public static Block fluidHeater = new BaseClosableMachine<>(TileFluidHeater.class);
    @ModBlock(creativeTab = ModCreativeTabs.Industry)
    @ModBlock.TileEntity(name = "electrolyticcell", clazz = "com.elementtimes.tutorial.common.tileentity.TileElectrolyticCell")
    @ModBlock.StateMapperCustom
    @ModBlock.StateMap
    public static Block electrolyticCell = new BaseClosableMachine<>(TileElectrolyticCell.class);
    @ModBlock(creativeTab = ModCreativeTabs.Industry)
    @ModBlock.TileEntity(name = "solidFluidreactor", clazz = "com.elementtimes.tutorial.common.tileentity.TileSolidFluidReactor")
    @ModBlock.StateMapperCustom
    @ModBlock.StateMap
    public static Block solidFluidReactor = new BaseClosableMachine<>(TileSolidFluidReactor.class);
    @ModBlock(creativeTab = ModCreativeTabs.Industry)
    @ModBlock.TileEntity(name = "pumpAir", clazz = "com.elementtimes.tutorial.common.tileentity.TilePumpAir")
    @ModBlock.StateMapperCustom
    @ModBlock.StateMap
    public static Block pumpAir = new BaseClosableMachine<>(TilePumpAir.class);
    @ModBlock(creativeTab = ModCreativeTabs.Industry)
    @ModBlock.TileEntity(name = "pumpFluid", clazz = "com.elementtimes.tutorial.common.tileentity.TilePumpFluid")
    @ModBlock.StateMapperCustom
    @ModBlock.StateMap
    public static Block pumpFluid = new BaseClosableMachine<>(TilePumpFluid.class);
    @ModBlock(creativeTab = ModCreativeTabs.Industry)
    @ModBlock.TileEntity(name = "itemreducer", clazz = "com.elementtimes.tutorial.common.tileentity.TileItemReducer")
    @ModBlock.StateMapperCustom
    @ModBlock.StateMap
    public static Block itemReducer = new BaseClosableMachine<>(TileItemReducer.class);
    @ModBlock(creativeTab = ModCreativeTabs.Industry)
    @ModBlock.TileEntity(name = "centrifuge", clazz = "com.elementtimes.tutorial.common.tileentity.TileCentrifuge")
    @ModBlock.StateMapperCustom
    @ModBlock.StateMap
    public static Block centrifuge = new BaseClosableMachine<>(TileCentrifuge.class);
    @ModBlock(creativeTab = ModCreativeTabs.Industry)
    @ModBlock.TileEntity(name = "coagulator", clazz = "com.elementtimes.tutorial.common.tileentity.TileCoagulator")
    @ModBlock.StateMapperCustom
    @ModBlock.StateMap
    public static Block coagulator = new BaseClosableMachine<>(TileCoagulator.class);
    @ModBlock(creativeTab = ModCreativeTabs.Industry)
    @ModBlock.TileEntity(name = "solidCentrifuge", clazz = "com.elementtimes.tutorial.common.tileentity.TileSolidCentrifuge")
    @ModBlock.StateMapperCustom
    @ModBlock.StateMap
    public static Block solidCentrifuge = new BaseClosableMachine<>(TileSolidCentrifuge.class);

    // pipeline

    @ModBlock(creativeTab = ModCreativeTabs.Industry)
    @ModBlock.TileEntity(name = "pipeline", clazz = "com.elementtimes.tutorial.common.tileentity.TilePipeline")
    @ModBlock.StateMapper(propertyName = "PL_TYPE", propertyIn = "com.elementtimes.tutorial.common.block.Pipeline")
    @ModBlock.StateMap
    public static Block pipeline = new Pipeline();

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
    @ModBlock(creativeTab = ModCreativeTabs.Industry)
    @ModBlock.HarvestLevel
    public static Block blockMultiTinLead = new Block(Material.IRON);
    @ModBlock(creativeTab = ModCreativeTabs.Industry)
    @ModBlock.HarvestLevel
    public static Block blockMultiIronQuartz = new Block(Material.IRON);
    @ModBlock(creativeTab = ModCreativeTabs.Industry)
    @ModBlock.HarvestLevel
    public static Block blockMultiObsidianDiamond = new Block(Material.IRON);
    @ModBlock(registerName = "alcohol_lamp", unlocalizedName = "alcohol_lamp", creativeTab = ModCreativeTabs.Chemical)
    @ModBlock.TileEntity(name = "alcohol_lamp", clazz = "com.elementtimes.tutorial.common.tileentity.TileAlcoholLamp")
    public static Block alcoholLamp = new AlcoholLamp();
    @ModBlock(registerName = "evaporating_dish", unlocalizedName = "evaporating_dish", creativeTab = ModCreativeTabs.Chemical)
    public static Block evaporatingDish = new BlockAABB(new AxisAlignedBB(0.25D, 0.0D, 0.25D, 0.75D, 0.25D, 0.75D));
    @ModBlock(creativeTab = ModCreativeTabs.Chemical)
    public static Block crucible = new Crucible();
    @ModBlock(creativeTab = ModCreativeTabs.None)
    public static Block fr = new FluidReplace();
}
