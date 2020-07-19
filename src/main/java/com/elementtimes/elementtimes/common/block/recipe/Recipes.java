package com.elementtimes.elementtimes.common.block.recipe;

import com.elementtimes.elementcore.api.recipe.RecipeManager;
import com.elementtimes.elementcore.api.utils.TagUtils;
import com.elementtimes.elementtimes.common.fluid.Sources;
import com.elementtimes.elementtimes.common.init.Items;
import com.elementtimes.elementtimes.common.init.blocks.Agriculture;
import com.elementtimes.elementtimes.common.init.blocks.Ore;
import net.minecraft.block.Blocks;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.ItemTags;
import net.minecraftforge.common.Tags;
import net.minecraftforge.fluids.FluidStack;



public class Recipes {

    public static void registerRecipes() {
        RecipeManager.addAll(CentrifugeRecipe.TYPE,
                new CentrifugeRecipe(10000, "centrifuge_air", new FluidStack(Sources.air, 1000), new FluidStack(Sources.nitrogen, 780), new FluidStack(Sources.oxygen, 210), new FluidStack(Fluids.WATER, 1), new FluidStack(Sources.rareGases, 8), new FluidStack(Sources.CO2, 1)),
                new CentrifugeRecipe(10000, "centrifuge_sea", new FluidStack(Sources.seaWater, 1000), new FluidStack(Sources.NaClSolutionDilute, 700), new FluidStack(Sources.MagnesiumChlorideSolution, 230), new FluidStack(Sources.AlCl3, 40), new FluidStack(Sources.HBr, 20), new FluidStack(Sources.HI, 10))
        );
        RecipeManager.addAll(CoagulatorRecipe.TYPE,
                new CoagulatorRecipe(10000, "coagulator_ice0", Sources.waterDistilled, Blocks.ICE),
                new CoagulatorRecipe(10000, "coagulator_ice1", Fluids.WATER, Blocks.ICE),
                new CoagulatorRecipe(10000, "coagulator_steel", Sources.Steel, Items.steelIngot),
                new CoagulatorRecipe(10000, "coagulator_fe", Sources.Fe, net.minecraft.item.Items.IRON_INGOT),
                new CoagulatorRecipe(20000, "coagulator_na", Sources.Na, Items.Na),
                new CoagulatorRecipe(20000, "coagulator_mg", Sources.Mg, Items.Mg),
                new CoagulatorRecipe(20000, "coagulator_al", Sources.Al, Items.Al),
                new CoagulatorRecipe(20000, "coagulator_na3alf6", Sources.Na3AlF6, Items.Na3AlF6)
        );
        RecipeManager.addAll(CompressorRecipe.TYPE,
                new CompressorRecipe(ItemTags.LOGS, Items.plateWood, "compressor_woods"),
                new CompressorRecipe(Tags.Items.STONE, Items.plateStone, "compressor_stone"),
                new CompressorRecipe("ingots", "tin"     , Items.plateTin     , "compressor_tin"),
                new CompressorRecipe("ingots", "gold"    , Items.plateGold    , "compressor_gold"),
                new CompressorRecipe("ingots", "iron"    , Items.plateIron    , "compressor_iron"),
                new CompressorRecipe("ingots", "lead"    , Items.plateLead    , "compressor_lead"),
                new CompressorRecipe("ingots", "steel"   , Items.plateSteel   , "compressor_steel"),
                new CompressorRecipe("ingots", "copper"  , Items.plateCopper  , "compressor_copper"),
                new CompressorRecipe("ingots", "silver"  , Items.plateSilver  , "compressor_silver"),
                new CompressorRecipe("ingots", "platinum", Items.platePlatinum, "compressor_platinum"),
                new CompressorRecipe("gems"  , "quartz"  , Items.plateQuartz  , "compressor_quartz"),
                new CompressorRecipe("gems"  , "diamond" , Items.plateDiamond , "compressor_diamond"),
                new CompressorRecipe(net.minecraft.item.Items.COAL, Items.plateCarbon, "compressor_coal"),
                new CompressorRecipe(Blocks.OBSIDIAN, Items.plateObsidian, "compressor_obsidian"),
                new CompressorRecipe(Items.stoneIngot, Items.plateStone, "compressor_stone_ingot"),
                new CompressorRecipe(Items.diamondIngot, Items.plateRawDiamond, "compressor_raw_diamond"),
                new CompressorRecipe(Items.Silicon, Items.plateSilicon, "compressor_si"),
                new CompressorRecipe(Items.Al, Items.plateAluminium, "compressor_al")
        );
        RecipeManager.addAll(CondenserRecipe.TYPE,
                new CondenserRecipe(Sources.steam, Sources.waterDistilled, "condenser_steam")
        );
        RecipeManager.addAll(ExtractorRecipe.TYPE,
                new ExtractorRecipe(1000, "extractor_rubber0", Agriculture.leavesRubber, Items.rubberRaw, 1),
                new ExtractorRecipe(4000, "extractor_rubber1", Agriculture.logRubber, Items.rubberRaw, 4),
                new ExtractorRecipe(2000, "extractor_rubber2", Agriculture.saplingRubber, Items.rubberRaw, 2),
                new ExtractorRecipe(2000, "extractor_sulfur", net.minecraft.item.Items.GUNPOWDER, Items.sulfurPowder, 1)
        );
        RecipeManager.addAll(ElectrolyticCellRecipe.TYPE,
                new ElectrolyticCellRecipe(40000, "electrolytic_cell_nacl", new FluidStack(Sources.NaCl, 2000), new FluidStack(Sources.Na, 2000), new FluidStack(Sources.chlorine, 1000)),
                new ElectrolyticCellRecipe(10000, "electrolytic_cell_hi", new FluidStack(Sources.HI, 2000), new FluidStack(Sources.H, 1000), new FluidStack(Sources.I2, 1000)),
                new ElectrolyticCellRecipe(10000, "electrolytic_cell_hbr", new FluidStack(Sources.HBr, 2000), new FluidStack(Sources.H, 1000), new FluidStack(Sources.Br2, 1000)),
                new ElectrolyticCellRecipe(10000, "electrolytic_cell_hcl", new FluidStack(Sources.HCl, 2000), new FluidStack(Sources.H, 1000), new FluidStack(Sources.chlorine, 1000)),
                new ElectrolyticCellRecipe(20000, "electrolytic_cell_steam", new FluidStack(Sources.steam, 2000), new FluidStack(Sources.H, 2000), new FluidStack(Sources.oxygen, 1000)),
                new ElectrolyticCellRecipe(20000, "electrolytic_cell_nacl_solution", new FluidStack(Sources.NaClSolutionConcentrated, 2000), new FluidStack(Sources.NaOH, 2000), new FluidStack(Sources.chlorine, 1000), new FluidStack(Sources.H, 1000)),
                new ElectrolyticCellRecipe(50000, "electrolytic_cell_mgcl2", new FluidStack(Sources.MgCl2, 1000), new FluidStack(Sources.Mg, 1000), new FluidStack(Sources.chlorine, 1000)),
                new ElectrolyticCellRecipe(60000, "electrolytic_cell_al2o3_na3alf6", new FluidStack(Sources.Al2O3_Na3AlF6, 2000), new FluidStack(Sources.Al, 4000), new FluidStack(Sources.oxygen, 3000), new FluidStack(Sources.Na3AlF6, 2000))
        );
        RecipeManager.addAll(FluidHeaterRecipe.TYPE,
                new FluidHeaterRecipe(10000, "fluid_heater_nacl_solution_concentrated", Sources.NaClSolutionConcentrated, Sources.NaCl),
                new FluidHeaterRecipe(10000, "fluid_heater_magnesium", Sources.MagnesiumChlorideSolution, Sources.MgCl2),
                new FluidHeaterRecipe( 1000, "fluid_heater_water", Fluids.WATER, Sources.steam),
                new FluidHeaterRecipe(10000, "fluid_heater_nacl_solution_dilute", Sources.NaClSolutionDilute, Sources.NaClSolutionConcentrated),
                new FluidHeaterRecipe(10000, "fluid_heater_dilute_hydrochloric_acid", Sources.DiluteHydrochloricAcid, Sources.ConcentratedHydrochloricAcid),
                new FluidHeaterRecipe(10000, "fluid_heater_concentrated_hydrochloric_acid", Sources.ConcentratedHydrochloricAcid, Sources.HCl),
                new FluidHeaterRecipe(10000, "fluid_heater_dilute_sulfuric_acid", Sources.diluteSulfuricAcid, Sources.concentratedSulfuricAcid),
                new FluidHeaterRecipe(10000, "fluid_heater_concentrated_sulfuric_acid", Sources.concentratedSulfuricAcid, Sources.H2SO4)
        );
        RecipeManager.addAll(FluidReactorRecipe.TYPE,
                FluidReactorRecipe.builder2().input0(Sources.H2SO4).input1(Sources.waterDistilled).output0(Sources.concentratedSulfuricAcid).build("concentrated_sulfuric_acid"),
                FluidReactorRecipe.builder2().input0(Sources.concentratedSulfuricAcid).input1(Sources.waterDistilled).output0(Sources.diluteSulfuricAcid).build("dilute_sulfuric_acid"),
                FluidReactorRecipe.builder2().input0(Sources.HCl).input1(Sources.waterDistilled).output0(Sources.ConcentratedHydrochloricAcid).build("concentrated_hydrochloric_acid"),
                FluidReactorRecipe.builder2().input0(Sources.ConcentratedHydrochloricAcid).input1(Sources.waterDistilled).output0(Sources.DiluteHydrochloricAcid).build("dilute_hydrochloric_acid"),
                FluidReactorRecipe.builder2().input0(Sources.HNO3).input1(Sources.waterDistilled).output0(Sources.HNO3Concentrated).build("nong_hno3"),
                FluidReactorRecipe.builder2().input0(Sources.HNO3Concentrated).input1(Sources.waterDistilled).output0(Sources.HNO3Dilute).build("xi_hno3"),
                FluidReactorRecipe.builder2().input0(Sources.AlCl3).input1(Sources.ammonia, 3000).output0(Sources.calmogastrin).output1(Sources.NH4Cl, 3000).build("calmogastrin_nh4cl"),
                FluidReactorRecipe.builder2().input0(Sources.calmogastrin).input1(Sources.NaOH).output0(Sources.NaAlO2).output1(Fluids.WATER, 2000).build("naalo2_water"),
                FluidReactorRecipe.builder2().input0(Sources.NaAlO2).input1(Sources.DiluteHydrochloricAcid).output0(Sources.calmogastrin).output1(Sources.NaClSolutionDilute).build("calmogastrin_nacl_solution_dilute"),
                FluidReactorRecipe.builder2().input0(Sources.calmogastrin).input1(Sources.DiluteHydrochloricAcid, 3000).output0(Sources.AlCl3).output1(Fluids.WATER, 3000).build("alcl3_water"),
                FluidReactorRecipe.builder2().input0(Sources.DiluteHydrochloricAcid).input1(Sources.ammonia).output0(Sources.NH4Cl).build("nh4cl"),
                FluidReactorRecipe.builder2().input0(Sources.FluoroaluminicAcid).input1(Sources.NaOH, 3000).item(Items.Na3AlF6).output0(Fluids.WATER, 3000).build("na3alf6_water", 20000),
                FluidReactorRecipe.builder2().input0(Sources.HF, 6000).input1(Sources.calmogastrin).output0(Sources.FluoroaluminicAcid).output1(Fluids.WATER, 2000).build("fluoroaluminic_acid_water"),
                FluidReactorRecipe.builder2().input0(Sources.SO2, 2000).input1(Sources.oxygen).output0(Sources.SO3, 2000).build("so3"),
                FluidReactorRecipe.builder2().input0(Sources.SO2).input1(Sources.waterDistilled).output0(Sources.H2SO3).build("h2so3"),
                FluidReactorRecipe.builder2().input0(Sources.SO3).input1(Sources.waterDistilled).output0(Sources.H2SO4).build("h2so4"),
                FluidReactorRecipe.builder2().input0(Sources.H2SO3, 2000).input1(Sources.oxygen).output0(Sources.H2SO4, 2000).build("h2so4_2"),
                FluidReactorRecipe.builder2().input0(Sources.CO).input1(Sources.waterDistilled).output0(Sources.CO2).output1(Sources.H).build("co2_h", 5000),
                FluidReactorRecipe.builder2().input0(Sources.CO).input1(Sources.H, 3000).output0(Sources.methane).output1(Sources.waterDistilled).build("methane_water_distilled"),
                FluidReactorRecipe.builder2().input0(Sources.SiCl4).input1(Sources.H, 2000).output0(Sources.DiluteHydrochloricAcid, 4000).item(Items.Silicon).build("dilute_hydrochloric_acid_silicon", 5000),
                FluidReactorRecipe.builder2().input0(Sources.H).input1(Sources.acetylene).output0(Sources.ethylene, 2000).build("ethylene", 10000),
                FluidReactorRecipe.builder2().input0(Sources.H).input1(Sources.ethylene).output0(Sources.ethane, 2000).build("ethane", 10000),
                FluidReactorRecipe.builder2().input0(Sources.waterDistilled).input1(Sources.ethylene).output0(Sources.ethanol, 2000).build("ethanol", 10000),
                FluidReactorRecipe.builder2().input0(Sources.H).input1(Sources.nitrogen).output0(Sources.ammonia, 2000).build("ammonia", 10000),
                FluidReactorRecipe.builder2().input0(Sources.H).input1(Sources.chlorine).output0(Sources.HCl, 2000).build("hcl"),
                FluidReactorRecipe.builder2().input0(Sources.NO, 2000).input1(Sources.oxygen).output0(Sources.NO2, 2000).build("no2"),
                FluidReactorRecipe.builder2().input0(Sources.NO2, 3000).input1(Sources.waterDistilled).output0(Sources.HNO3, 2000).output1(Sources.NO).build("hno3_no"),
                FluidReactorRecipe.builder2().input0(Sources.HNO3).input1(Sources.NaOH).output0(Sources.NaNO3).output1(Fluids.WATER).build("nano3_water"),
                FluidReactorRecipe.builder2().input0(Sources.CO2).input1(Sources.calciumHydroxide).item(Items.calciumCarbonate).output1(Fluids.WATER).build("calcium_carbonate_water")
        );
        RecipeManager.addAll(FormingRecipe.TYPE,
                new FormingRecipe("forming_raw_diamond", Items.diamondIngot, Items.gearRawDiamond),
                new FormingRecipe("forming_wood"       , ItemTags.PLANKS, Items.gearWood),
                new FormingRecipe("forming_coal"       , ItemTags.COALS, Items.gearCarbon),
                new FormingRecipe("forming_stone"      , Tags.Items.STONE, Items.gearSilver),
                new FormingRecipe("forming_gold"       , Tags.Items.INGOTS_GOLD, Items.gearGold),
                new FormingRecipe("forming_diamond"    , Tags.Items.GEMS_DIAMOND, Items.gearDiamond),
                new FormingRecipe("forming_iron"       , Tags.Items.INGOTS_IRON, Items.gearIron),
                new FormingRecipe("forming_lead"       , Tags.Items.OBSIDIAN, Items.gearObsidian),
                new FormingRecipe("forming_quartz"     , Tags.Items.GEMS_QUARTZ, Items.gearQuartz),
                new FormingRecipe("forming_silver"     , TagUtils.forgeItem("ingots", "silver"), Items.gearSilver),
                new FormingRecipe("forming_steel"      , TagUtils.forgeItem("ingots", "steel"), Items.gearSteel),
                new FormingRecipe("forming_platinum"   , TagUtils.forgeItem("ingots", "platinum"), Items.gearPlatinum),
                new FormingRecipe("forming_copper"     , TagUtils.forgeItem("ingots", "copper"), Items.gearCopper),
                new FormingRecipe("forming_lead"       , TagUtils.forgeItem("ingots", "lead"), Items.gearLead),
                new FormingRecipe("forming_tin"        , TagUtils.forgeItem("ingots", "tin"), Items.gearTin)
        );
        RecipeManager.addAll(ItemReducerRecipe.TYPE,
                new ItemReducerRecipe("item_reducer_bamboo", Items.bambooCharcoal, new ItemStack(Blocks.BAMBOO, 4)),
                new ItemReducerRecipe("item_reducer_bamboo_2", Agriculture.bambooBlock, new ItemStack(Blocks.BAMBOO, 8)),
                new ItemReducerRecipe("item_reducer_reed", Items.sucroseCharCoal, new ItemStack(Blocks.SUGAR_CANE, 4)),
                new ItemReducerRecipe("item_reducer_tin", "tin", new ItemStack(Items.tin, 9)),
                new ItemReducerRecipe("item_reducer_lead", "lead", new ItemStack(Items.lead, 9)),
                new ItemReducerRecipe("item_reducer_copper", "copper", new ItemStack(Items.copper, 9)),
                new ItemReducerRecipe("item_reducer_platinum", "platinum", new ItemStack(Items.platinumIngot, 9)),
                new ItemReducerRecipe("item_reducer_silver", "silver", new ItemStack(Items.silver, 9)),
                new ItemReducerRecipe("item_reducer_steel", "steel", new ItemStack(Items.steelIngot, 9)),
                new ItemReducerRecipe("item_reducer_raw_diamond", Ore.blockRawDiamond, new ItemStack(Items.diamondIngot, 9)),
                new ItemReducerRecipe("item_reducer_melon", Blocks.MELON, new ItemStack(net.minecraft.item.Items.MELON, 9)),
                new ItemReducerRecipe("item_reducer_nether_wart", Blocks.NETHER_WART, new ItemStack(net.minecraft.item.Items.NETHER_WART, 9)),
                new ItemReducerRecipe("item_reducer_glow_stone", Blocks.GLOWSTONE, new ItemStack(net.minecraft.item.Items.GLOWSTONE_DUST, 4)),
                new ItemReducerRecipe("item_reducer_wool", ItemTags.WOOL, new ItemStack(net.minecraft.item.Items.STRING, 4)),
                new ItemReducerRecipe("item_reducer_clay", Blocks.CLAY, new ItemStack(net.minecraft.item.Items.CLAY_BALL, 4)),
                new ItemReducerRecipe("item_reducer_web", Blocks.COBWEB, new ItemStack(net.minecraft.item.Items.STRING, 4)),
                new ItemReducerRecipe("item_reducer_bamboo_block", new ItemStack(Agriculture.bambooSlab, 2), new ItemStack(Agriculture.bambooBlock))
        );
        RecipeManager.addAll(PulverizerRecipe.TYPE,
                new PulverizerRecipe("pulverizer_iron"     , Tags.Items.ORES_IRON, Items.ironPowder),
                new PulverizerRecipe("pulverizer_redstone" , Tags.Items.ORES_REDSTONE, Items.redstonePowder),
                new PulverizerRecipe("pulverizer_gold"     , Tags.Items.ORES_GOLD, Items.goldPowder),
                new PulverizerRecipe("pulverizer_diamond"  , Tags.Items.ORES_DIAMOND, Items.diamondPowder),
                new PulverizerRecipe("pulverizer_lapis"    , Tags.Items.ORES_LAPIS, Items.bluestonePowder),
                new PulverizerRecipe("pulverizer_emerald"  , Tags.Items.ORES_EMERALD, Items.greenstonePowder),
                new PulverizerRecipe("pulverizer_coal"     , Tags.Items.ORES_COAL, Items.coalPowder),
                new PulverizerRecipe("pulverizer_emerald"  , Tags.Items.ORES_QUARTZ, Items.quartzPowder),
                new PulverizerRecipe("pulverizer_blaze"    , Tags.Items.RODS_BLAZE, net.minecraft.item.Items.BLAZE_POWDER),
                new PulverizerRecipe("pulverizer_platinum" , TagUtils.forgeItem("ores", "platinum"), Items.platinumOrePowder),
                new PulverizerRecipe("pulverizer_copper"   , TagUtils.forgeItem("ores", "copper"), Items.copperPowder),
                new PulverizerRecipe("pulverizer_silver"   , TagUtils.forgeItem("ores", "silver"), Items.silverPowder),
                new PulverizerRecipe("pulverizer_sulfur"   , TagUtils.forgeItem("ores", "sulfur"), Items.sulfurOrePowder),
                new PulverizerRecipe("pulverizer_lead"     , TagUtils.forgeItem("ores", "lead"), Items.leadPowder),
                new PulverizerRecipe("pulverizer_tin"      , TagUtils.forgeItem("ores", "tin"), Items.tinPowder),
                new PulverizerRecipe("pulverizer_salt"     , TagUtils.forgeItem("ores", "salt"), Items.salt),
                new PulverizerRecipe("pulverizer_nickel"   , TagUtils.forgeItem("ores", "nickel"), Items.nickelpowder),
                new PulverizerRecipe("pulverizer_osmium"   , TagUtils.forgeItem("ores", "osmium"), Items.osmiumpowder),
                new PulverizerRecipe("pulverizer_ardite"   , TagUtils.forgeItem("ores", "ardite"), Items.arditepowder),
                new PulverizerRecipe("pulverizer_cobalt"   , TagUtils.forgeItem("ores", "cobalt"), Items.cobaltpowder)
        );
        RecipeManager.addAll(RebuildRecipe.TYPE,
                new RebuildRecipe(10000000, "rebuild_egg", net.minecraft.item.Items.EGG, Blocks.DRAGON_EGG, 1),
                new RebuildRecipe(4000, "rebuild_coal", Items.amylum, Items.sucroseCharCoal, 1),
                new RebuildRecipe(10000, "rebuild_totem", Items.woodElement, net.minecraft.item.Items.TOTEM_OF_UNDYING, 1),
                new RebuildRecipe(4000, "rebuild_coal0", net.minecraft.item.Items.CHARCOAL, net.minecraft.item.Items.COAL, 1),
                new RebuildRecipe(50000, "rebuild_coal1", net.minecraft.item.Items.COAL, net.minecraft.item.Items.DIAMOND, 1),
                new RebuildRecipe(50000, "rebuild_diamond", Blocks.DIAMOND_BLOCK, Items.diamondIngot, 3),
                new RebuildRecipe(10000, "rebuild_dirt", Blocks.DIRT, Blocks.FARMLAND, 1),
                new RebuildRecipe(10000, "rebuild_grass", Blocks.GRASS, Blocks.GRASS_PATH, 1),
                new RebuildRecipe(20000, "rebuild_sand", Blocks.SAND, Blocks.SOUL_SAND, 1),
                new RebuildRecipe(20000, "rebuild_glass", Blocks.GLASS, net.minecraft.item.Items.QUARTZ, 1),
                new RebuildRecipe(2000, "rebuild_bc", Items.sucroseCharCoal, Items.bambooCharcoal, 1),
                new RebuildRecipe(2000, "rebuild_cc", Items.bambooCharcoal, Items.sucroseCharCoal, 1)
        );
        RecipeManager.addAll(SolarDecomposerRecipe.TYPE,
                new SolarDecomposerRecipe(200, "solar_decomposer_water_distilled", new FluidStack(Sources.waterDistilled, 2000), new FluidStack(Sources.oxygen, 1000), new FluidStack(Sources.H, 2000)),
                new SolarDecomposerRecipe(100, "solar_decomposer_nh4cl", new FluidStack(Sources.NH4Cl, 1000), new FluidStack(Sources.HCl, 1000), new FluidStack(Sources.ammonia, 1000)),
                new SolarDecomposerRecipe(200, "solar_decomposer_hclo", new FluidStack(Sources.HClO, 2000), new FluidStack(Sources.oxygen, 1000), new FluidStack(Sources.HCl, 2000)),
                new SolarDecomposerRecipe(400, "solar_decomposer_hno3", new FluidStack(Sources.HNO3, 4000), new FluidStack(Fluids.WATER, 2000), new FluidStack(Sources.NO2, 4000), new FluidStack(Sources.oxygen, 1000))
        );
        RecipeManager.addAll(SolidCentrifugeRecipe.TYPE,
                new SolidCentrifugeRecipe("solid_centrifuge_iron", "dusts", "iron", Items.Fe2O3, Items.stonepowder, Items.sandpowder),
                new SolidCentrifugeRecipe("solid_centrifuge_copper", "dusts", "copper", Items.CuO, Items.stonepowder, Items.sandpowder),
                new SolidCentrifugeRecipe(5000, "solid_centrifuge_uranium", Items.uraniumPowder, Items.bingU3O8, Items.smallU3O8, Items.slag),
                new SolidCentrifugeRecipe("solid_centrifuge_slag", Items.slag, Items.stonepowder, Items.sandpowder)
        );
        RecipeManager.addAll(SolidFluidReactorRecipe.TYPE,
                // todo other recipes
                new SolidFluidReactorRecipe(10000, new ItemStack(net.minecraft.item.Items.COAL), new FluidStack(Sources.Fe, 2000), ItemStack.EMPTY, ItemStack.EMPTY, new FluidStack(Sources.Steel, 2000), FluidStack.EMPTY, "solid_fluid_reactor_steel")
        );
        RecipeManager.addAll(SolidMelterRecipe.TYPE,
                new SolidMelterRecipe("solid_melter_salt", Items.salt, Sources.NaCl),
                new SolidMelterRecipe("solid_melter_iron_ingot", net.minecraft.item.Items.IRON_INGOT, Sources.Fe),
                new SolidMelterRecipe("solid_melter_steel_ingot", "ingots", "steel", Sources.Steel),
                new SolidMelterRecipe("solid_melter_al2o3_na3alf6", Items.Al2O3_Na3AlF6, Sources.Al2O3_Na3AlF6)
        );
        RecipeManager.addAll(SolidReactorRecipe.TYPE,
                new SolidReactorRecipe(5000, "solid_reactor_grass_path", new ItemStack(Blocks.GRASS), new ItemStack(Blocks.DIRT), new ItemStack(Blocks.GRASS_PATH), ItemStack.EMPTY, FluidStack.EMPTY)
        );
        RecipeManager.addAll(ElementGeneratorRecipe.TYPE,
                new ElementGeneratorRecipe(Items.fiveElements, 15000000),
                new ElementGeneratorRecipe(Items.endElement  , 15000000),
                new ElementGeneratorRecipe(Items.photoElement, 15000000),
                new ElementGeneratorRecipe(Items.waterElement, 90000000),
                new ElementGeneratorRecipe(Items.woodElement , 1500000),
                new ElementGeneratorRecipe(Items.soilElement , 1000000),
                new ElementGeneratorRecipe(Items.goldElement , 4000000),
                new ElementGeneratorRecipe(Items.fireElement , 2000000)
        );
    }
}
