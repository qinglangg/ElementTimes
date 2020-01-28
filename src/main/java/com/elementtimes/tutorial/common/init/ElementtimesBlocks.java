package com.elementtimes.tutorial.common.init;

import com.elementtimes.elementcore.api.annotation.old.ModBlock;
import com.elementtimes.elementcore.api.annotation.old.ModItem;
import com.elementtimes.elementcore.api.annotation.old.ModOreDict;
import com.elementtimes.elementcore.api.annotation.enums.GenType;
import com.elementtimes.elementcore.api.annotation.tools.ModBurnTime;
import com.elementtimes.elementcore.api.template.block.BaseClosableMachine;
import com.elementtimes.elementcore.api.template.block.BlockTileBase;
import com.elementtimes.tutorial.ElementTimes;
import com.elementtimes.tutorial.common.block.*;
import com.elementtimes.tutorial.common.block.machine.Compressor;
import com.elementtimes.tutorial.common.block.pipeline.Pipeline;
import com.elementtimes.tutorial.common.block.pipeline.PipelineIO;
import com.elementtimes.tutorial.common.block.tree.*;
import com.elementtimes.tutorial.common.tileentity.*;
import com.elementtimes.tutorial.common.tileentity.pipeline.fluid.FluidConnectPipeline;
import com.elementtimes.tutorial.common.tileentity.pipeline.fluid.FluidInputPipeline;
import com.elementtimes.tutorial.common.tileentity.pipeline.fluid.FluidOutputPipeline;
import com.elementtimes.tutorial.common.tileentity.pipeline.item.ItemConnectPipeline;
import com.elementtimes.tutorial.common.tileentity.pipeline.item.ItemInputPipeline;
import com.elementtimes.tutorial.common.tileentity.pipeline.item.ItemOutputPipeline;
import com.elementtimes.tutorial.plugin.elementcore.SSMRegister.SupportStandModule;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.fml.common.Mod;

/**
 * 所有方块
 *
 * @author 卿岚
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
    @ModOreDict("treeSapling")
    public static Block rubberSapling = new RubberSapling();
    @ModBlock(creativeTabKey = ElementtimesTabs.AGRICULTURE, registerName = "rubber_log", unlocalizedName = "rubber_log")
    @ModBurnTime(300)
    @ModOreDict("logWood")
    public static Block rubberLog = new RubberLog();
    @ModBlock(creativeTabKey = ElementtimesTabs.AGRICULTURE, registerName = "rubber_leaf", unlocalizedName = "rubber_leaf")
    @ModOreDict("treeLeaves")
    public static Block rubberLeaf = new RubberLeaf();
    @ModBlock(creativeTabKey = ElementtimesTabs.AGRICULTURE)
    public static Block saplingEssence = new EssenceSapling();
    @ModBlock(creativeTabKey = ElementtimesTabs.AGRICULTURE)
    @ModBlock.HarvestLevel(toolClass = "axe")
    public static Block woodesSence = new EssenceLog().setHardness(50f).setResistance(15f).setLightLevel(50f);
    @ModBlock(creativeTabKey = ElementtimesTabs.AGRICULTURE)
    public static Block leafesSence = new EssenceLeaf();

    // Ore
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
    @ModBlock.HarvestLevel(level = 1)
    @ModBlock.WorldGen
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
    @ModBlock.WorldGenClass("com.elementtimes.tutorial.common.generator.SulfurGenerator")
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
    public static Block furnace = new BaseClosableMachine<>(TileFurnace.class, ElementTimes.instance);
    @ModBlock(creativeTabKey = ElementtimesTabs.INDUSTRY, registerName = "fuelGenerator", unlocalizedName = "fuelGenerator")
    @ModBlock.TileEntity(name = "fuel_generator", clazz = "com.elementtimes.tutorial.common.tileentity.TileGeneratorFuel")
    public static Block fuelGenerator = new BaseClosableMachine<>(TileGeneratorFuel.class, ElementTimes.instance);
    @ModBlock(creativeTabKey = ElementtimesTabs.INDUSTRY)
    @ModBlock.TileEntity(clazz = "com.elementtimes.tutorial.common.tileentity.TileGeneratorSun")
    public static Block sunGenerator = new BaseClosableMachine<>(TileGeneratorSun.class, ElementTimes.instance);
    @ModBlock(creativeTabKey = ElementtimesTabs.INDUSTRY, registerName = "rebuild", unlocalizedName = "rebuild")
    @ModBlock.TileEntity(name = "rebuild", clazz = "com.elementtimes.tutorial.common.tileentity.TileRebuild")
    public static Block rebuild = new BaseClosableMachine<>(TileRebuild.class, ElementTimes.instance);
    @ModBlock(creativeTabKey = ElementtimesTabs.INDUSTRY)
    @ModBlock.TileEntity(name = "compressor", clazz = "com.elementtimes.tutorial.common.tileentity.TileCompressor")
    @ModBlock.AnimTESR
    public static Block compressor = new Compressor();
    @ModBlock(creativeTabKey = ElementtimesTabs.INDUSTRY)
    @ModBlock.TileEntity(name = "forming", clazz = "com.elementtimes.tutorial.common.tileentity.TileForming")
    public static Block forming = new BaseClosableMachine<>(TileForming.class, ElementTimes.instance);
    @ModBlock(creativeTabKey = ElementtimesTabs.INDUSTRY)
    @ModBlock.TileEntity(name = "extractor", clazz = "com.elementtimes.tutorial.common.tileentity.TileExtractor")
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
    public static Block solidMelter = new BaseClosableMachine<>(TileSolidMelter.class, ElementTimes.instance);
    @ModBlock(creativeTabKey = ElementtimesTabs.INDUSTRY)
    @ModBlock.TileEntity(name = "condenser", clazz = "com.elementtimes.tutorial.common.tileentity.TileCondenser")
    public static Block condenser = new BaseClosableMachine<>(TileCondenser.class, ElementTimes.instance);
    @ModBlock(creativeTabKey = ElementtimesTabs.INDUSTRY)
    @ModBlock.TileEntity(name = "fluidreactor", clazz = "com.elementtimes.tutorial.common.tileentity.TileFluidReactor")
    public static Block fluidReactor = new BaseClosableMachine<>(TileFluidReactor.class, ElementTimes.instance);
    @ModBlock(creativeTabKey = ElementtimesTabs.INDUSTRY)
    @ModBlock.TileEntity(clazz = "com.elementtimes.tutorial.common.tileentity.TileGeneratorFluid")
    public static Block fluidGenerator = new BaseClosableMachine<>(TileGeneratorFluid.class, ElementTimes.instance);
    @ModBlock(creativeTabKey = ElementtimesTabs.INDUSTRY)
    @ModBlock.TileEntity(name = "solidreactor", clazz = "com.elementtimes.tutorial.common.tileentity.TileSolidReactor")
    public static Block solidReactor = new BaseClosableMachine<>(TileSolidReactor.class, ElementTimes.instance);
    @ModBlock(creativeTabKey = ElementtimesTabs.INDUSTRY)
    @ModBlock.TileEntity(name = "fluidheater", clazz = "com.elementtimes.tutorial.common.tileentity.TileFluidHeater")
    public static Block fluidHeater = new BaseClosableMachine<>(TileFluidHeater.class, ElementTimes.instance);
    @ModBlock(creativeTabKey = ElementtimesTabs.INDUSTRY)
    @ModBlock.TileEntity(name = "electrolyticcell", clazz = "com.elementtimes.tutorial.common.tileentity.TileElectrolyticCell")
    public static Block electrolyticCell = new BaseClosableMachine<>(TileElectrolyticCell.class, ElementTimes.instance);
    @ModBlock(creativeTabKey = ElementtimesTabs.INDUSTRY)
    @ModBlock.TileEntity(name = "solidFluidreactor", clazz = "com.elementtimes.tutorial.common.tileentity.TileSolidFluidReactor")
    public static Block solidFluidReactor = new BaseClosableMachine<>(TileSolidFluidReactor.class, ElementTimes.instance);
    @ModBlock(creativeTabKey = ElementtimesTabs.INDUSTRY)
    @ModBlock.TileEntity(name = "pumpAir", clazz = "com.elementtimes.tutorial.common.tileentity.TilePumpAir")
    public static Block pumpAir = new BaseClosableMachine<>(TilePumpAir.class, ElementTimes.instance);
    @ModBlock(creativeTabKey = ElementtimesTabs.INDUSTRY)
    @ModBlock.TileEntity(name = "pumpFluid", clazz = "com.elementtimes.tutorial.common.tileentity.TilePumpFluid")
    public static Block pumpFluid = new BaseClosableMachine<>(TilePumpFluid.class, ElementTimes.instance);
    @ModBlock(creativeTabKey = ElementtimesTabs.INDUSTRY)
    @ModBlock.TileEntity(name = "itemreducer", clazz = "com.elementtimes.tutorial.common.tileentity.TileItemReducer")
    public static Block itemReducer = new BaseClosableMachine<>(TileItemReducer.class, ElementTimes.instance);
    @ModBlock(creativeTabKey = ElementtimesTabs.INDUSTRY)
    @ModBlock.TileEntity(name = "centrifuge", clazz = "com.elementtimes.tutorial.common.tileentity.TileCentrifuge")
    public static Block centrifuge = new BaseClosableMachine<>(TileCentrifuge.class, ElementTimes.instance);
    @ModBlock(creativeTabKey = ElementtimesTabs.INDUSTRY)
    @ModBlock.TileEntity(name = "coagulator", clazz = "com.elementtimes.tutorial.common.tileentity.TileCoagulator")
    public static Block coagulator = new BaseClosableMachine<>(TileCoagulator.class, ElementTimes.instance);
    @ModBlock(creativeTabKey = ElementtimesTabs.INDUSTRY)
    @ModBlock.TileEntity(name = "solidCentrifuge", clazz = "com.elementtimes.tutorial.common.tileentity.TileSolidCentrifuge")
    public static Block solidCentrifuge = new BaseClosableMachine<>(TileSolidCentrifuge.class, ElementTimes.instance);
    @ModBlock(creativeTabKey = ElementtimesTabs.INDUSTRY)
    @ModBlock.TileEntity(clazz = "com.elementtimes.tutorial.common.tileentity.TileSolarDecomposer")
    public static Block solarDecomposer = new BaseClosableMachine<>(TileSolarDecomposer.class, ElementTimes.instance);
    @ModBlock(creativeTabKey = ElementtimesTabs.INDUSTRY)
    @ModBlock.TileEntity(clazz = "com.elementtimes.tutorial.common.tileentity.TileFermenter")
    public static Block fermenter = new BaseClosableMachine<>(TileFermenter.class, ElementTimes.instance);
    @ModBlock(creativeTabKey = ElementtimesTabs.INDUSTRY)
    @ModBlock.TileEntity(clazz = "com.elementtimes.elementcore.api.template.block.BlockTileBase")
    public static Block fluidTank = new BlockTileBase<>(TileFluidTank.class, ElementTimes.instance);

    // Pipeline
    @ModBlock(creativeTabKey = ElementtimesTabs.INDUSTRY)
    @ModBlock.TileEntity(clazz = "com.elementtimes.tutorial.common.tileentity.pipeline.item.ItemInputPipeline")
    public static Block pipelineItemInput = new PipelineIO(ItemInputPipeline::new);
    @ModBlock(creativeTabKey = ElementtimesTabs.INDUSTRY)
    @ModBlock.TileEntity(clazz = "com.elementtimes.tutorial.common.tileentity.pipeline.item.ItemOutputPipeline")
    public static Block pipelineItemOutput = new PipelineIO(ItemOutputPipeline::new);
    @ModBlock(creativeTabKey = ElementtimesTabs.INDUSTRY)
    @ModBlock.TileEntity(clazz = "com.elementtimes.tutorial.common.tileentity.pipeline.item.ItemConnectPipeline")
    public static Block pipelineItemConnect = new Pipeline(ItemConnectPipeline::new);
    @ModBlock(creativeTabKey = ElementtimesTabs.INDUSTRY)
    @ModBlock.TileEntity(clazz = "com.elementtimes.tutorial.common.tileentity.pipeline.fluid.FluidInputPipeline")
    public static Block pipelineFluidInput = new PipelineIO(FluidInputPipeline::new);
    @ModBlock(creativeTabKey = ElementtimesTabs.INDUSTRY)
    @ModBlock.TileEntity(clazz = "com.elementtimes.tutorial.common.tileentity.pipeline.fluid.FluidOutputPipeline")
    public static Block pipelineFluidOutput = new PipelineIO(FluidOutputPipeline::new);
    @ModBlock(creativeTabKey = ElementtimesTabs.INDUSTRY)
    @ModBlock.TileEntity(clazz = "com.elementtimes.tutorial.common.tileentity.pipeline.fluid.FluidConnectPipeline")
    public static Block pipelineFluidConnect = new Pipeline(FluidConnectPipeline::new);

    // 其他
    @ModBlock(creativeTabKey = ElementtimesTabs.CHEMICAL)
    @ModBlock.HarvestLevel
    @ModItem.Tooltip("\u00a79CaCO3")
    public static Block stoneBlock = new Block(Material.ROCK).setHardness(50f).setResistance(20f);
    @ModBlock(creativeTabKey = ElementtimesTabs.CHEMICAL)
    @ModBlock.HarvestLevel(level = 3)
    @ModOreDict("cement")
    public static Block cement = new Block(Material.ROCK).setHardness(200f).setResistance(50f);
    @ModBlock(creativeTabKey = ElementtimesTabs.CHEMICAL)
    @ModBlock.HarvestLevel(level = 4)
    public static Block cementAndSteelBarMixture = new Block(Material.ROCK).setHardness(1000f).setResistance(150f);
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
    @ModBlock
    public static Block fr = new FluidReplace();

    // Chemical
    @ModBlock(creativeTabKey = ElementtimesTabs.CHEMICAL, registerName = "alcohol_lamp", unlocalizedName = "alcohol_lamp")
    @ModBlock.TileEntity(clazz = "com.elementtimes.tutorial.common.tileentity.TileAlcoholLamp")
    @SupportStandModule
    public static Block alcoholLamp = new AlcoholLamp();
    @ModBlock(creativeTabKey = ElementtimesTabs.CHEMICAL, registerName = "evaporating_dish", unlocalizedName = "evaporating_dish")
    @ModBlock.TileEntity(clazz = "com.elementtimes.tutorial.common.tileentity.TileEvaporatingDish")
    @SupportStandModule
    public static Block evaporatingDish = new EvaporatingDish();
    @ModBlock(creativeTabKey = ElementtimesTabs.CHEMICAL)
    @ModBlock.TileEntity(clazz = "com.elementtimes.tutorial.common.tileentity.TileCrucible")
    @SupportStandModule
    public static Block crucible = new Crucible();
    @ModBlock(creativeTabKey = ElementtimesTabs.CHEMICAL)
    @ModBlock.TileEntity(clazz = "com.elementtimes.tutorial.common.tileentity.TileBeaker")
    @SupportStandModule
    public static Block beaker = new Beaker();

    // Test
    @ModBlock(creativeTabKey = ElementtimesTabs.MAIN)
    @ModBlock.TileEntity(clazz = "com.elementtimes.tutorial.common.tileentity.TileTest")
    public static Block test = new Test();
}
