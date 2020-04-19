package com.elementtimes.tutorial.common.init;

import com.elementtimes.elementcore.api.annotation.ModBlock;
import com.elementtimes.elementcore.api.annotation.ModItem;
import com.elementtimes.elementcore.api.annotation.enums.GenType;
import com.elementtimes.elementcore.api.annotation.enums.HarvestType;
import com.elementtimes.elementcore.api.annotation.part.Getter;
import com.elementtimes.elementcore.api.annotation.part.Getter2;
import com.elementtimes.elementcore.api.annotation.tools.ModBurnTime;
import com.elementtimes.elementcore.api.annotation.tools.ModOreDict;
import com.elementtimes.elementcore.api.template.block.BaseClosableMachine;
import com.elementtimes.elementcore.api.template.block.BlockTileBase;
import com.elementtimes.tutorial.ElementTimes;
import com.elementtimes.tutorial.common.block.*;
import com.elementtimes.tutorial.common.block.machine.Compressor;
import com.elementtimes.tutorial.common.block.pipeline.Pipeline;
import com.elementtimes.tutorial.common.block.pipeline.PipelineIO;
import com.elementtimes.tutorial.common.block.tree.*;
import com.elementtimes.tutorial.common.generator.BambooGenerator;
import com.elementtimes.tutorial.common.generator.RubberGenerator;
import com.elementtimes.tutorial.common.generator.SaltGenerator;
import com.elementtimes.tutorial.common.generator.SulfurGenerator;
import com.elementtimes.tutorial.common.tileentity.*;
import com.elementtimes.tutorial.common.tileentity.pipeline.fluid.FluidConnectPipeline;
import com.elementtimes.tutorial.common.tileentity.pipeline.fluid.FluidInputPipeline;
import com.elementtimes.tutorial.common.tileentity.pipeline.fluid.FluidOutputPipeline;
import com.elementtimes.tutorial.common.tileentity.pipeline.item.ItemConnectPipeline;
import com.elementtimes.tutorial.common.tileentity.pipeline.item.ItemInputPipeline;
import com.elementtimes.tutorial.common.tileentity.pipeline.item.ItemOutputPipeline;
import com.elementtimes.tutorial.plugin.elementcore.SSMRegister.SupportStandModule;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;
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
    @ModBlock(registerName = "corn_crop")
    public static Block cornCrop = new CornCrop();
    @ModBlock(creativeTabKey = ElementtimesTabs.AGRICULTURE, registerName = "rubber_sapling", unlocalizedName = "rubber_sapling")
    @ModBlock.WorldGenObj(value = @Getter(RubberGenerator.RubberNatureGenerator.class), type = GenType.Tree)
    @ModOreDict({"treeSapling","treeSaplingRubber"})
    public static Block rubberSapling = new RubberSapling();
    @ModBlock(creativeTabKey = ElementtimesTabs.AGRICULTURE, registerName = "rubber_log", unlocalizedName = "rubber_log")
    @ModBurnTime(8000)
    @ModOreDict({"woodRubber","logWood"})
    public static Block rubberLog = new RubberLog();
    @ModBlock(creativeTabKey = ElementtimesTabs.AGRICULTURE, registerName = "rubber_leaf", unlocalizedName = "rubber_leaf")
    @ModOreDict({"leavesRubber","treeLeaves"})
    public static Block rubberLeaf = new RubberLeaf();
    @ModBlock(creativeTabKey = ElementtimesTabs.AGRICULTURE)
    public static Block saplingEssence = new EssenceSapling();
    @ModBlock(creativeTabKey = ElementtimesTabs.AGRICULTURE)
    @ModBlock.HarvestLevel(toolClass = HarvestType.axe)
    public static Block woodesSence = new EssenceLog().setHardness(50f).setResistance(15f).setLightLevel(50f);
    @ModBlock(creativeTabKey = ElementtimesTabs.AGRICULTURE)
    public static Block leafesSence = new EssenceLeaf();
    @ModBlock(creativeTabKey = ElementtimesTabs.AGRICULTURE)
    @ModOreDict("bamboo")
    @ModBlock.WorldGenObj(value = @Getter(BambooGenerator.class), type = GenType.Tree)
    public static Block bamboo = new Bamboo();

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
    @ModBlock.WorldGenObj(@Getter(SaltGenerator.class))
    public static OreSalt oreSalt = new OreSalt();
    @ModBlock(creativeTabKey = ElementtimesTabs.ORE)
    @ModOreDict("oreSulfur")
    @ModBlock.WorldGenObj(@Getter(SulfurGenerator.class))
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
    @ModBlock.TileEntity(name = "furnace", value = TileFurnace.class)
    public static Block furnace = new BaseClosableMachine<>(TileFurnace.class, ElementTimes.instance);
    @ModBlock(creativeTabKey = ElementtimesTabs.INDUSTRY)
    @ModBlock.TileEntity(name = "fuel_generator", value = TileGeneratorFuel.class)
    public static Block fuelGenerator = new BaseClosableMachine<>(TileGeneratorFuel.class, ElementTimes.instance);
    @ModBlock(creativeTabKey = ElementtimesTabs.INDUSTRY)
    @ModBlock.TileEntity(TileGeneratorSun.class)
    public static Block sunGenerator = new BaseClosableMachine<>(TileGeneratorSun.class, ElementTimes.instance);
    @ModBlock(creativeTabKey = ElementtimesTabs.INDUSTRY)
    @ModBlock.TileEntity(name = "rebuild", value = TileRebuild.class)
    public static Block rebuild = new BaseClosableMachine<>(TileRebuild.class, ElementTimes.instance);
    @ModBlock(creativeTabKey = ElementtimesTabs.INDUSTRY)
    @ModBlock.TileEntity(name = "compressor", value = TileCompressor.class)
    @ModBlock.AnimTESR
    public static Block compressor = new Compressor();
    @ModBlock(creativeTabKey = ElementtimesTabs.INDUSTRY)
    @ModBlock.TileEntity(name = "forming", value = TileForming.class)
    public static Block forming = new BaseClosableMachine<>(TileForming.class, ElementTimes.instance);
    @ModBlock(creativeTabKey = ElementtimesTabs.INDUSTRY)
    @ModBlock.TileEntity(name = "extractor", value = TileExtractor.class)
    public static Block extractor = new BaseClosableMachine<>(TileExtractor.class, ElementTimes.instance);
    @ModBlock(creativeTabKey = ElementtimesTabs.INDUSTRY)
    @ModBlock.TileEntity(name = "pulverizer", value = TilePulverize.class)
    public static Block pulverizer = new BlockTileBase<>(TilePulverize.class, ElementTimes.instance);
    @ModBlock(creativeTabKey = ElementtimesTabs.INDUSTRY, registerName = "elementgenerater")
    @ModBlock.TileEntity(name = "element_generator", value = TileGeneratorElement.class)
    public static Block elementGenerator = new BlockTileBase<>(TileGeneratorElement.class, ElementTimes.instance);
    @ModBlock(creativeTabKey = ElementtimesTabs.CHEMICAL, registerName = "support_stand")
    @ModBlock.TileEntity(name = "support_stand", value = TileSupportStand.class)
    public static Block supportStand = new SupportStand();
    @ModBlock(creativeTabKey = ElementtimesTabs.INDUSTRY)
    @ModBlock.TileEntity(name = "solidmelter", value = TileSolidMelter.class)
    public static Block solidMelter = new BaseClosableMachine<>(TileSolidMelter.class, ElementTimes.instance);
    @ModBlock(creativeTabKey = ElementtimesTabs.INDUSTRY)
    @ModBlock.TileEntity(name = "condenser", value = TileCondenser.class)
    public static Block condenser = new BaseClosableMachine<>(TileCondenser.class, ElementTimes.instance);
    @ModBlock(creativeTabKey = ElementtimesTabs.INDUSTRY)
    @ModBlock.TileEntity(name = "fluidreactor", value = TileFluidReactor.class)
    public static Block fluidReactor = new BaseClosableMachine<>(TileFluidReactor.class, ElementTimes.instance);
    @ModBlock(creativeTabKey = ElementtimesTabs.INDUSTRY)
    @ModBlock.TileEntity(TileGeneratorFluid.class)
    public static Block fluidGenerator = new BaseClosableMachine<>(TileGeneratorFluid.class, ElementTimes.instance);
    @ModBlock(creativeTabKey = ElementtimesTabs.INDUSTRY)
    @ModBlock.TileEntity(name = "solidreactor", value = TileSolidReactor.class)
    public static Block solidReactor = new BaseClosableMachine<>(TileSolidReactor.class, ElementTimes.instance);
    @ModBlock(creativeTabKey = ElementtimesTabs.INDUSTRY)
    @ModBlock.TileEntity(name = "fluidheater", value = TileFluidHeater.class)
    public static Block fluidHeater = new BaseClosableMachine<>(TileFluidHeater.class, ElementTimes.instance);
    @ModBlock(creativeTabKey = ElementtimesTabs.INDUSTRY)
    @ModBlock.TileEntity(name = "electrolyticcell", value = TileElectrolyticCell.class)
    public static Block electrolyticCell = new BaseClosableMachine<>(TileElectrolyticCell.class, ElementTimes.instance);
    @ModBlock(creativeTabKey = ElementtimesTabs.INDUSTRY)
    @ModBlock.TileEntity(name = "solidFluidreactor", value = TileSolidFluidReactor.class)
    public static Block solidFluidReactor = new BaseClosableMachine<>(TileSolidFluidReactor.class, ElementTimes.instance);
    @ModBlock(creativeTabKey = ElementtimesTabs.INDUSTRY)
    @ModBlock.TileEntity(name = "pumpAir", value = TilePumpAir.class)
    public static Block pumpAir = new BaseClosableMachine<>(TilePumpAir.class, ElementTimes.instance);
    @ModBlock(creativeTabKey = ElementtimesTabs.INDUSTRY)
    @ModBlock.TileEntity(name = "pumpFluid", value = TilePumpFluid.class)
    public static Block pumpFluid = new BaseClosableMachine<>(TilePumpFluid.class, ElementTimes.instance);
    @ModBlock(creativeTabKey = ElementtimesTabs.INDUSTRY)
    @ModBlock.TileEntity(name = "itemreducer", value = TileItemReducer.class)
    public static Block itemReducer = new BaseClosableMachine<>(TileItemReducer.class, ElementTimes.instance);
    @ModBlock(creativeTabKey = ElementtimesTabs.INDUSTRY)
    @ModBlock.TileEntity(name = "centrifuge", value = TileCentrifuge.class)
    public static Block centrifuge = new BaseClosableMachine<>(TileCentrifuge.class, ElementTimes.instance);
    @ModBlock(creativeTabKey = ElementtimesTabs.INDUSTRY)
    @ModBlock.TileEntity(name = "coagulator", value = TileCoagulator.class)
    public static Block coagulator = new BaseClosableMachine<>(TileCoagulator.class, ElementTimes.instance);
    @ModBlock(creativeTabKey = ElementtimesTabs.INDUSTRY)
    @ModBlock.TileEntity(name = "solidCentrifuge", value = TileSolidCentrifuge.class)
    public static Block solidCentrifuge = new BaseClosableMachine<>(TileSolidCentrifuge.class, ElementTimes.instance);
    @ModBlock(creativeTabKey = ElementtimesTabs.INDUSTRY)
    @ModBlock.TileEntity(TileSolarDecomposer.class)
    public static Block solarDecomposer = new BaseClosableMachine<>(TileSolarDecomposer.class, ElementTimes.instance);
    @ModBlock(creativeTabKey = ElementtimesTabs.INDUSTRY)
    @ModBlock.TileEntity(TileFermenter.class)
    public static Block fermenter = new BaseClosableMachine<>(TileFermenter.class, ElementTimes.instance);
    @ModBlock(creativeTabKey = ElementtimesTabs.INDUSTRY)
    @ModBlock.TileEntity(TileFluidTank.class)
    @ModBlock.TESR("com.elementtimes.tutorial.client.block.FluidTankRender")
    @ModBlock.StateMapper(@Getter2("com.elementtimes.tutorial.other.EmptyMachineState"))
    public static Block fluidTank = new BlockTileBase<>(TileFluidTank.class);
    @ModBlock(creativeTabKey = ElementtimesTabs.INDUSTRY)
    @ModBlock.TileEntity(TileEnergyBox.class)
    @ModBlock.TESR("com.elementtimes.tutorial.client.block.EnergyBoxRender")
    @ModBlock.StateMapper(@Getter2("com.elementtimes.tutorial.other.EmptyMachineState"))
    public static Block energyBox = new BlockTileBase<>(TileEnergyBox.class);

    // Pipeline
    @ModBlock(creativeTabKey = ElementtimesTabs.INDUSTRY)
    @ModBlock.TileEntity(ItemInputPipeline.class)
    public static Block pipelineItemInput = new PipelineIO(ItemInputPipeline::new);
    @ModBlock(creativeTabKey = ElementtimesTabs.INDUSTRY)
    @ModBlock.TileEntity(ItemOutputPipeline.class)
    public static Block pipelineItemOutput = new PipelineIO(ItemOutputPipeline::new);
    @ModBlock(creativeTabKey = ElementtimesTabs.INDUSTRY)
    @ModBlock.TileEntity(ItemConnectPipeline.class)
    public static Block pipelineItemConnect = new Pipeline(ItemConnectPipeline::new);
    @ModBlock(creativeTabKey = ElementtimesTabs.INDUSTRY)
    @ModBlock.TileEntity(FluidInputPipeline.class)
    public static Block pipelineFluidInput = new PipelineIO(FluidInputPipeline::new);
    @ModBlock(creativeTabKey = ElementtimesTabs.INDUSTRY)
    @ModBlock.TileEntity(FluidOutputPipeline.class)
    public static Block pipelineFluidOutput = new PipelineIO(FluidOutputPipeline::new);
    @ModBlock(creativeTabKey = ElementtimesTabs.INDUSTRY)
    @ModBlock.TileEntity(FluidConnectPipeline.class)
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
    @ModBlock(creativeTabKey = ElementtimesTabs.AGRICULTURE)
    @ModBurnTime(1600)
    public static Block bambooBlock = new Block(Material.WOOD).setHardness(7f).setResistance(5f);
    @ModBurnTime(3200)
    public static Block bambooDoor = Door.wood();
    @ModBurnTime(800)
    public static Block bambooSlab = BambooSlab.half();
    public static Block bambooSlabDouble = BambooSlab.double_();
    @ModBlock(creativeTabKey = ElementtimesTabs.AGRICULTURE)
    @ModBurnTime(1600)
    public static Block bambooStair = new BlockStairs(bambooBlock.getDefaultState()) {};

    // Chemical
    @ModBlock(creativeTabKey = ElementtimesTabs.CHEMICAL, registerName = "alcohol_lamp")
    @ModBlock.TileEntity(TileAlcoholLamp.class)
    @SupportStandModule
    public static Block alcoholLamp = new AlcoholLamp();
    @ModBlock(creativeTabKey = ElementtimesTabs.CHEMICAL, registerName = "evaporating_dish")
    @ModBlock.TileEntity(TileEvaporatingDish.class)
    @SupportStandModule
    public static Block evaporatingDish = new EvaporatingDish();
    @ModBlock(creativeTabKey = ElementtimesTabs.CHEMICAL)
    @ModBlock.TileEntity(TileCrucible.class)
    @SupportStandModule
    public static Block crucible = new Crucible();
    @ModBlock(creativeTabKey = ElementtimesTabs.CHEMICAL)
    @ModBlock.TileEntity(TileBeaker.class)
    @SupportStandModule
    public static Block beaker = new Beaker();

    // Test
    @ModBlock
    @ModBlock.TileEntity(TileTest.class)
    public static Block test = new Test();
    @ModBlock(creativeTabKey = ElementtimesTabs.INDUSTRY)
    @ModBlock.TileEntity(TileCreativeEnergyBox.class)
    @ModBlock.StateMapper(@Getter2("com.elementtimes.tutorial.other.InfiniteMachineState"))
    public static Block creativeEnergy = new BlockTileBase<>(TileCreativeEnergyBox.class);
    @ModBlock(creativeTabKey = ElementtimesTabs.INDUSTRY)
    @ModBlock.TileEntity(TileCreativeFluidTank.class)
    @ModBlock.StateMapper(@Getter2("com.elementtimes.tutorial.other.InfiniteMachineState"))
    public static Block creativeTank = new BlockTileBase<>(TileCreativeFluidTank.class);
    @ModBlock(creativeTabKey = ElementtimesTabs.INDUSTRY)
    @ModBlock.TileEntity(TileCreativeItemChest.class)
    @ModBlock.StateMapper(@Getter2("com.elementtimes.tutorial.other.InfiniteMachineState"))
    public static Block creativeChest = new BlockTileBase<>(TileCreativeItemChest.class);
}
