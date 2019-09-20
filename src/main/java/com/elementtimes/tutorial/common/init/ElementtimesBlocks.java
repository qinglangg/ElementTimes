package com.elementtimes.tutorial.common.init;

import com.elementtimes.elementcore.api.annotation.ModBlock;
import com.elementtimes.elementcore.api.annotation.ModOreDict;
import com.elementtimes.elementcore.api.annotation.enums.GenType;
import com.elementtimes.elementcore.api.template.block.BaseClosableMachine;
import com.elementtimes.elementcore.api.template.block.BlockTileBase;
import com.elementtimes.tutorial.ElementTimes;
import com.elementtimes.tutorial.common.block.*;
import com.elementtimes.tutorial.common.block.machine.Compressor;
import com.elementtimes.tutorial.common.block.tree.*;
import com.elementtimes.tutorial.common.tileentity.*;
import com.elementtimes.tutorial.plugin.elementcore.SSMRegister.SupportStandModule;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
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
    @ModBlock(registerName = "corn_crop", unlocalizedName = "cornCrop")
    public static Block cornCrop = new com.elementtimes.tutorial.common.block.CornCrop();
    @ModBlock(registerName = "corn_crop_up", unlocalizedName = "cornCropUp")
    public static Block cornCropUp = new CornCropUp();
    @ModBlock(creativeTabKey = ElementtimesTabs.AGRICULTURE, registerName = "rubber_sapling", unlocalizedName = "rubber_sapling")
    @ModBlock.WorldGenClass(value = "com.elementtimes.tutorial.common.generator.RubberGenerator$RubberNatureGenerator", type = GenType.Tree)
    public static Block rubberSapling = new RubberSapling();
    @ModBlock(creativeTabKey = ElementtimesTabs.AGRICULTURE, registerName = "rubber_log", unlocalizedName = "rubber_log")
    @ModBlock.BurningTime(300)
    @ModOreDict("logWood")
    public static Block rubberLog = new RubberLog();
    @ModBlock(creativeTabKey = ElementtimesTabs.AGRICULTURE, registerName = "rubber_leaf", unlocalizedName = "rubber_leaf")
    public static Block rubberLeaf = new RubberLeaf();
    @ModBlock(creativeTabKey = ElementtimesTabs.AGRICULTURE)
    public static Block saplingEssence = new EssenceSapling();
    @ModBlock(creativeTabKey = ElementtimesTabs.AGRICULTURE)
    @ModBlock.HarvestLevel(toolClass = "axe")
    @ModOreDict("logWood")
    public static Block woodesSence = new EssenceLog().setHardness(50f).setResistance(15f).setLightLevel(50f);
    @ModBlock(creativeTabKey = ElementtimesTabs.AGRICULTURE)
    public static Block leafesSence = new EssenceLeaf();

    // ore
    @ModBlock(creativeTabKey = ElementtimesTabs.ORE)
    @ModOreDict("oreTin")
    @ModBlock.HarvestLevel
    @ModBlock.WorldGen
    public static Block tinOre = new Block(Material.ROCK).setHardness(15f).setResistance(10f);
    @ModBlock(creativeTabKey = ElementtimesTabs.ORE)
    @ModOreDict("oreLead")
    @ModBlock.HarvestLevel
    @ModBlock.WorldGen
    public static Block leadOre = new Block(Material.ROCK).setHardness(15f).setResistance(10f);
    @ModBlock(creativeTabKey = ElementtimesTabs.ORE)
    @ModOreDict("oreCopper")
    @ModBlock.HarvestLevel
    @ModBlock.WorldGen
    @ModBlock.Tooltip({"\u00a79CuO", "SiO2", "CaCO3"})
    public static Block copperOre = new Block(Material.ROCK).setHardness(15f).setResistance(10f);
    @ModBlock(creativeTabKey = ElementtimesTabs.ORE)
    @ModOreDict("orePlatinum")
    @ModBlock.HarvestLevel(level = 3)
    @ModBlock.WorldGen(YMin = 5, YRange = 10, probability = 0.3f)
    public static Block platinumOre = new Block(Material.ROCK).setHardness(20f).setResistance(10f);
    @ModBlock(creativeTabKey = ElementtimesTabs.ORE)
    @ModOreDict("oreSilver")
    @ModBlock.HarvestLevel
    @ModBlock.WorldGen
    public static Block SilverOre = new Block(Material.ROCK).setHardness(15f).setResistance(10f);
    @ModBlock(creativeTabKey = ElementtimesTabs.ORE)
    @ModOreDict("oreSalt")
    @ModBlock.WorldGenClass("com.elementtimes.tutorial.common.generator.SaltGenerator")
    public static OreSalt oreSalt = new OreSalt();
    @ModBlock(creativeTabKey = ElementtimesTabs.ORE)
    @ModOreDict("oreSulfur")
    @ModBlock.WorldGen
    public static Block sulfurOre = new Sulfurore();
    @ModBlock(creativeTabKey = ElementtimesTabs.ORE)
    @ModBlock.HarvestLevel
    @ModBlock.WorldGen
    public static Block calciumFluoride = new Block(Material.ROCK).setHardness(15f).setResistance(10f);
    @ModBlock(creativeTabKey = ElementtimesTabs.ORE)
    @ModOreDict("oreUranium")
    @ModBlock.HarvestLevel(level = 3)
    @ModBlock.WorldGen(YMin = 5, YRange = 10, probability = 0.3f)
    @ModBlock.Tooltip("\u00a79U3O8 U^235/238")
    public static Block uraniumOre = new Block(Material.ROCK).setHardness(20f).setResistance(10f);


    // Block
    @ModBlock(creativeTabKey = ElementtimesTabs.ORE)
    @ModOreDict("blockTin")
    @ModBlock.HarvestLevel
    public static Block blockTin = new Block(Material.ROCK).setHardness(50f).setResistance(20f);
    @ModBlock(creativeTabKey = ElementtimesTabs.ORE)
    @ModOreDict("blockLead")
    @ModBlock.HarvestLevel
    public static Block blockLead = new Block(Material.ROCK).setHardness(50f).setResistance(20f);
    @ModBlock(creativeTabKey = ElementtimesTabs.ORE)
    @ModOreDict("blockCopper")
    @ModBlock.HarvestLevel
    public static Block copperBillet = new Block(Material.ROCK).setHardness(50f).setResistance(20f);
    @ModBlock(creativeTabKey = ElementtimesTabs.ORE)
    @ModOreDict("blockPlatinum")
    @ModBlock.HarvestLevel(level = 3)
    public static Block platinumBlock = new Block(Material.ROCK).setHardness(50f).setResistance(20f).setLightLevel(50f);
    @ModBlock(creativeTabKey = ElementtimesTabs.ORE)
    @ModOreDict("blockSilver")
    @ModBlock.HarvestLevel
    public static Block blockSilver = new Block(Material.ROCK).setHardness(50f).setResistance(20f).setLightLevel(50f);
    @ModBlock(creativeTabKey = ElementtimesTabs.INDUSTRY)
    @ModOreDict("blockSteel")
    @ModBlock.HarvestLevel
    public static Block steelBlock = new Block(Material.ROCK).setHardness(50f).setResistance(20f).setLightLevel(50f);
    @ModBlock(creativeTabKey = ElementtimesTabs.INDUSTRY)
    @ModBlock.HarvestLevel(level = 3)
    public static Block diamondBlock = new Block(Material.ROCK).setHardness(100f).setResistance(35f).setLightLevel(100f);

    // Machine
    @ModBlock(creativeTabKey = ElementtimesTabs.INDUSTRY)
    @ModBlock.TileEntity(name = "furnace", clazz = "com.elementtimes.tutorial.common.tileentity.TileFurnace")
    @ModBlock.StateMapperCustom
    @ModBlock.StateMap
    public static Block furnace = new BaseClosableMachine<>(TileFurnace.class, ElementTimes.instance);
    @ModBlock(creativeTabKey = ElementtimesTabs.INDUSTRY, registerName = "fuelGenerator", unlocalizedName = "fuelGenerator")
    @ModBlock.TileEntity(name = "fuel_generator", clazz = "com.elementtimes.tutorial.common.tileentity.TileGeneratorFuel")
    @ModBlock.StateMapperCustom
    @ModBlock.StateMap
    public static Block fuelGenerator = new BaseClosableMachine<>(TileGeneratorFuel.class, ElementTimes.instance);
    @ModBlock(creativeTabKey = ElementtimesTabs.INDUSTRY, registerName = "rebuild", unlocalizedName = "rebuild")
    @ModBlock.TileEntity(name = "rebuild", clazz = "com.elementtimes.tutorial.common.tileentity.TileRebuild")
    @ModBlock.StateMapperCustom
    @ModBlock.StateMap
    public static Block rebuild = new BaseClosableMachine<>(TileRebuild.class, ElementTimes.instance);
    @ModBlock(creativeTabKey = ElementtimesTabs.INDUSTRY)
    @ModBlock.TileEntity(name = "compressor", clazz = "com.elementtimes.tutorial.common.tileentity.TileCompressor")
    @ModBlock.StateMapperCustom
    @ModBlock.StateMap
    @ModBlock.AnimTESR
    public static Block compressor = new Compressor();
    @ModBlock(creativeTabKey = ElementtimesTabs.INDUSTRY)
    @ModBlock.TileEntity(name = "forming", clazz = "com.elementtimes.tutorial.common.tileentity.TileForming")
    @ModBlock.StateMapperCustom
    @ModBlock.StateMap
    public static Block forming = new BaseClosableMachine<>(TileForming.class, ElementTimes.instance);
    @ModBlock(creativeTabKey = ElementtimesTabs.INDUSTRY)
    @ModBlock.TileEntity(name = "extractor", clazz = "com.elementtimes.tutorial.common.tileentity.TileExtractor")
    @ModBlock.StateMapperCustom
    @ModBlock.StateMap
    public static Block extractor = new BaseClosableMachine<>(TileExtractor.class, ElementTimes.instance);
    @ModBlock(creativeTabKey = ElementtimesTabs.INDUSTRY)
    @ModBlock.TileEntity(name = "pulverizer", clazz = "com.elementtimes.tutorial.common.tileentity.TilePulverize")
    public static Block pulverizer = new BlockTileBase<>(TilePulverize.class, ElementTimes.instance);
    @ModBlock(creativeTabKey = ElementtimesTabs.INDUSTRY, registerName = "elementGenerater", unlocalizedName = "elementGenerater")
    @ModBlock.TileEntity(name = "element_generator", clazz = "com.elementtimes.tutorial.common.tileentity.TileGeneratorElement")
    public static Block elementGenerator = new BlockTileBase<>(TileGeneratorElement.class, ElementTimes.instance);
    @ModBlock(creativeTabKey = ElementtimesTabs.CHEMICAL, registerName = "support_stand", unlocalizedName = "support_stand")
    @ModBlock.TileEntity(name = "support_stand", clazz = "com.elementtimes.tutorial.common.tileentity.TileSupportStand")
    public static Block supportStand = new SupportStand();
    @ModBlock(creativeTabKey = ElementtimesTabs.INDUSTRY)
    @ModBlock.TileEntity(name = "solidmelter", clazz = "com.elementtimes.tutorial.common.tileentity.TileSolidMelter")
    @ModBlock.StateMapperCustom
    @ModBlock.StateMap
    public static Block solidMelter = new BaseClosableMachine<>(TileSolidMelter.class, ElementTimes.instance);
    @ModBlock(creativeTabKey = ElementtimesTabs.INDUSTRY)
    @ModBlock.TileEntity(name = "condenser", clazz = "com.elementtimes.tutorial.common.tileentity.TileCondenser")
    @ModBlock.StateMapperCustom
    @ModBlock.StateMap
    public static Block condenser = new BaseClosableMachine<>(TileCondenser.class, ElementTimes.instance);
    @ModBlock(creativeTabKey = ElementtimesTabs.INDUSTRY)
    @ModBlock.TileEntity(name = "fluidreactor", clazz = "com.elementtimes.tutorial.common.tileentity.TileFluidReactor")
    @ModBlock.StateMapperCustom
    @ModBlock.StateMap
    public static Block fluidReactor = new BaseClosableMachine<>(TileFluidReactor.class, ElementTimes.instance);
    @ModBlock(creativeTabKey = ElementtimesTabs.INDUSTRY)
    @ModBlock.TileEntity(name = "solidreactor", clazz = "com.elementtimes.tutorial.common.tileentity.TileSolidReactor")
    @ModBlock.StateMapperCustom
    @ModBlock.StateMap
    public static Block solidReactor = new BaseClosableMachine<>(TileSolidReactor.class, ElementTimes.instance);
    @ModBlock(creativeTabKey = ElementtimesTabs.INDUSTRY)
    @ModBlock.TileEntity(name = "fluidheater", clazz = "com.elementtimes.tutorial.common.tileentity.TileFluidHeater")
    @ModBlock.StateMapperCustom
    @ModBlock.StateMap
    public static Block fluidHeater = new BaseClosableMachine<>(TileFluidHeater.class, ElementTimes.instance);
    @ModBlock(creativeTabKey = ElementtimesTabs.INDUSTRY)
    @ModBlock.TileEntity(name = "electrolyticcell", clazz = "com.elementtimes.tutorial.common.tileentity.TileElectrolyticCell")
    @ModBlock.StateMapperCustom
    @ModBlock.StateMap
    public static Block electrolyticCell = new BaseClosableMachine<>(TileElectrolyticCell.class, ElementTimes.instance);
    @ModBlock(creativeTabKey = ElementtimesTabs.INDUSTRY)
    @ModBlock.TileEntity(name = "solidFluidreactor", clazz = "com.elementtimes.tutorial.common.tileentity.TileSolidFluidReactor")
    @ModBlock.StateMapperCustom
    @ModBlock.StateMap
    public static Block solidFluidReactor = new BaseClosableMachine<>(TileSolidFluidReactor.class, ElementTimes.instance);
    @ModBlock(creativeTabKey = ElementtimesTabs.INDUSTRY)
    @ModBlock.TileEntity(name = "pumpAir", clazz = "com.elementtimes.tutorial.common.tileentity.TilePumpAir")
    @ModBlock.StateMapperCustom
    @ModBlock.StateMap
    public static Block pumpAir = new BaseClosableMachine<>(TilePumpAir.class, ElementTimes.instance);
    @ModBlock(creativeTabKey = ElementtimesTabs.INDUSTRY)
    @ModBlock.TileEntity(name = "pumpFluid", clazz = "com.elementtimes.tutorial.common.tileentity.TilePumpFluid")
    @ModBlock.StateMapperCustom
    @ModBlock.StateMap
    public static Block pumpFluid = new BaseClosableMachine<>(TilePumpFluid.class, ElementTimes.instance);
    @ModBlock(creativeTabKey = ElementtimesTabs.INDUSTRY)
    @ModBlock.TileEntity(name = "itemreducer", clazz = "com.elementtimes.tutorial.common.tileentity.TileItemReducer")
    @ModBlock.StateMapperCustom
    @ModBlock.StateMap
    public static Block itemReducer = new BaseClosableMachine<>(TileItemReducer.class, ElementTimes.instance);
    @ModBlock(creativeTabKey = ElementtimesTabs.INDUSTRY)
    @ModBlock.TileEntity(name = "centrifuge", clazz = "com.elementtimes.tutorial.common.tileentity.TileCentrifuge")
    @ModBlock.StateMapperCustom
    @ModBlock.StateMap
    public static Block centrifuge = new BaseClosableMachine<>(TileCentrifuge.class, ElementTimes.instance);
    @ModBlock(creativeTabKey = ElementtimesTabs.INDUSTRY)
    @ModBlock.TileEntity(name = "coagulator", clazz = "com.elementtimes.tutorial.common.tileentity.TileCoagulator")
    @ModBlock.StateMapperCustom
    @ModBlock.StateMap
    public static Block coagulator = new BaseClosableMachine<>(TileCoagulator.class, ElementTimes.instance);
    @ModBlock(creativeTabKey = ElementtimesTabs.INDUSTRY)
    @ModBlock.TileEntity(name = "solidCentrifuge", clazz = "com.elementtimes.tutorial.common.tileentity.TileSolidCentrifuge")
    @ModBlock.StateMapperCustom
    @ModBlock.StateMap
    public static Block solidCentrifuge = new BaseClosableMachine<>(TileSolidCentrifuge.class, ElementTimes.instance);

    // pipeline
    @ModBlock(creativeTabKey = ElementtimesTabs.INDUSTRY)
    @ModBlock.TileEntity(name = "pipeline", clazz = "com.elementtimes.tutorial.common.tileentity.TilePipeline")
    @ModBlock.StateMapper(propertyName = "PL_TYPE", propertyIn = "com.elementtimes.tutorial.common.block.Pipeline")
    public static Block pipeline = new Pipeline();

    // 其他
    @ModBlock(creativeTabKey = ElementtimesTabs.CHEMICAL)
    @ModBlock.HarvestLevel
    public static Block stoneBlock = new Block(Material.ROCK).setHardness(100f).setResistance(15f);
    @ModBlock(creativeTabKey = ElementtimesTabs.CHEMICAL)
    @ModBlock.HarvestLevel(level = 3)
    public static Block cement = new Block(Material.ROCK).setHardness(1000f).setResistance(30f);
    @ModBlock(creativeTabKey = ElementtimesTabs.CHEMICAL)
    @ModBlock.HarvestLevel(level = 4)
    public static Block cementAndSteelBarMixture = new Block(Material.ROCK).setHardness(10000f).setResistance(150f);
    @ModBlock(creativeTabKey = ElementtimesTabs.INDUSTRY)
    @ModBlock.HarvestLevel
    public static Block blockMultiSilverCopper = new Block(Material.IRON);
    @ModBlock(creativeTabKey = ElementtimesTabs.INDUSTRY)
    @ModBlock.HarvestLevel
    public static Block blockMultiWoodStone = new Block(Material.IRON);
    @ModBlock(creativeTabKey = ElementtimesTabs.INDUSTRY)
    @ModBlock.HarvestLevel
    public static Block blockMultiGoldPlatinum = new Block(Material.IRON);
    @ModBlock(creativeTabKey = ElementtimesTabs.INDUSTRY)
    @ModBlock.HarvestLevel
    public static Block blockMultiCarbonSteel = new Block(Material.IRON);
    @ModBlock(creativeTabKey = ElementtimesTabs.INDUSTRY)
    @ModBlock.HarvestLevel
    public static Block blockMultiTinLead = new Block(Material.IRON);
    @ModBlock(creativeTabKey = ElementtimesTabs.INDUSTRY)
    @ModBlock.HarvestLevel
    public static Block blockMultiIronQuartz = new Block(Material.IRON);
    @ModBlock(creativeTabKey = ElementtimesTabs.INDUSTRY)
    @ModBlock.HarvestLevel
    public static Block blockMultiObsidianDiamond = new Block(Material.IRON);
    @ModBlock(creativeTabKey = ElementtimesTabs.CHEMICAL, registerName = "alcohol_lamp", unlocalizedName = "alcohol_lamp")
    @ModBlock.TileEntity(name = "alcohol_lamp", clazz = "com.elementtimes.tutorial.common.tileentity.TileAlcoholLamp")
    @SupportStandModule
    public static Block alcoholLamp = new AlcoholLamp();
    @ModBlock(creativeTabKey = ElementtimesTabs.CHEMICAL, registerName = "evaporating_dish", unlocalizedName = "evaporating_dish")
    @SupportStandModule
    public static Block evaporatingDish = new EvaporatingDish();
    @ModBlock(creativeTabKey = ElementtimesTabs.CHEMICAL)
    @SupportStandModule
    public static Block crucible = new Crucible();
    @ModBlock
    public static Block fr = new FluidReplace();
}
