package com.elementtimes.tutorial.common.init;

import com.elementtimes.elementcore.api.annotation.ModRecipe;
import com.elementtimes.tutorial.other.recipe.RecipeUtil;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionUtils;

import static com.elementtimes.tutorial.plugin.elementcore.HammerOreRecipe.Ore;

/**
 * 所有合成表
 *
 * @author luqin2007
 */
@SuppressWarnings("unused")
public class ElementtimesRecipeCrafting {

    @Ore(value = "leadpowder", output = "elementtimes:leadpowder")
    public static String oreLead = "oreLead";
	
    @Ore(value = "tinpowder", output = "elementtimes:tinpowder")
    public static String oreTin = "oreTin";
	
    @Ore(value = "fe2o3", output = "elementtimes:fe2o3")
    public static String oreIron = "oreIron";

    @Ore(value = "emeraldpowder", output = "elementtimes:greenstonepowder")
    public static String oreEmerald = "oreEmerald";

    @Ore(value = "sulfurpowder", output = "elementtimes:sulfurorepowder")
    public static String oreSulfur = "oreSulfur";

    @Ore(value = "coalpodwer", output = "elementtimes:coalpowder")
    public static String oreCoal = "oreCoal";

    @Ore(value = "silverpowder", output = "elementtimes:silverpowder")
    public static String oreSilver = "oreSilver";

    @Ore(value = "uraniumpowder", output = "elementtimes:uraniumpowder", dustCount = 1)
    public static String oreUranium = "oreUranium";

    @Ore(value = "salt", output = "elementtimes:salt", dustCount = 8)
    public static String oreSalt = "oreSalt";
    
    @Ore(value = "goldpowder", output = "elementtimes:goldpowder")
    public static String oreGold = "oreGold";

    @Ore(value = "copperpowder", output = "elementtimes:copperpowder")
    public static String oreCopper = "oreCopper";

    @Ore(value = "platinumorepowder", output = "elementtimes:platinumorepowder")
    public static String orePlatinum = "orePlatinum";

    @Ore(value = "quartz", output = "elementtimes:quartzpowder")
    public static String oreQuartz = "oreQuartz";

    @Ore(value = "lapis", output = "elementtimes:bluestonepowder")
    public static String oreLapis = "oreLapis";

    @Ore(value = "redstonepowder", damage = 2, output = "elementtimes:redstonepowder")
    public static String oreRedstone = "oreRedstone";

    @Ore(value = "diamondpowder", damage = 2, output = "elementtimes:diamondpowder")
    public static String oreDiamond = "oreDiamond";

    @Ore(value = "blaze_powder", damage = 2, dustCount = 8, output = "minecraft:blaze_powder")
    public static String blazeRod = "minecraft:blaze_rod";

    @Ore(value = "wood", dustCount = 16, output = "")
    public static String logWood = "logWood";

    @Ore(value = "gravel->flint", dustCount = 5, output = "minecraft:flint")
    public static String gravel = "minecraft:gravel";

    @ModRecipe(shaped = false)
    public static Object[] Al2O3_Na3AlF6 = new Object[] {
    		ElementtimesItems.Al2O3_Na3AlF6,
    		ElementtimesItems.Na3AlF6, Items.REDSTONE
    };

    @ModRecipe
    public static Object[] blockMultiSliverCopper = new Object[]{
            ElementtimesBlocks.blockMultiSilverCopper,
            "plateSilver", "rubber", "plateCopper",
            "gearSilver", "rubber", "gearCopper",
            "plateSilver", "rubber", "plateCopper"
    };

    @ModRecipe
    public static Object[] blockMultiCarbonSteel = new Object[]{
            ElementtimesBlocks.blockMultiCarbonSteel,
            "plateCarbon", "rubber", "plateSteel",
            "gearCarbon", "rubber", "gearSteel",
            "plateCarbon", "rubber", "plateSteel"
    };

    @ModRecipe
    public static Object[] blockMultiGoldPlatinum = new Object[]{
            ElementtimesBlocks.blockMultiGoldPlatinum,
            "platePlatinum", "rubber", "plateGold",
            "gearPlatinum", "rubber", "gearGold",
            "platePlatinum", "rubber", "plateGold"
    };

    @ModRecipe
    public static Object[] blockMultiWoodStone = new Object[]{
            ElementtimesBlocks.blockMultiWoodStone,
            "plateWood", "rubber", "plateStone",
            "gearWood", "rubber", "gearStone",
            "plateWood", "rubber", "plateStone"
    };

    @ModRecipe
    public static Object[] blockMultiTinLead = new Object[]{
            ElementtimesBlocks.blockMultiTinLead,
            "plateTin", "rubber", "plateLead",
            "gearTin", "rubber", "gearLead",
            "plateTin", "rubber", "plateLead"
    };

    @ModRecipe
    public static Object[] blockMultiIronQuartz = new Object[]{
            ElementtimesBlocks.blockMultiIronQuartz,
            "plateIron", "rubber", "plateQuartz",
            "gearIron", "rubber", "gearQuartz",
            "plateIron", "rubber", "plateQuartz"
    };

    @ModRecipe
    public static Object[] blockMultiObsidianDiamond = new Object[]{
            ElementtimesBlocks.blockMultiObsidianDiamond,
            "plateObsidian", "rubber", ElementtimesItems.plateAdamas,
            "gearObsidian", "rubber", ElementtimesItems.gearAdamas,
            "plateObsidian", "rubber", ElementtimesItems.plateAdamas
    };

    @ModRecipe
    public static Object[] spanner = new Object[]{
            ElementtimesItems.spanner,
            null, null, "ingotSteel",
            null, "ingotCopper", null,
            "ingotCopper"
    };

    @ModRecipe
    public static Object[] condenser = new Object[]{
            ElementtimesBlocks.condenser,
            ElementtimesBlocks.blockMultiCarbonSteel, ElementtimesBlocks.cementAndSteelBarMixture, ElementtimesBlocks.blockMultiGoldPlatinum,
            ElementtimesItems.waterElement, ElementtimesBlocks.fluidReactor, ElementtimesItems.waterElement,
            ElementtimesBlocks.blockMultiIronQuartz, ElementtimesBlocks.cementAndSteelBarMixture, ElementtimesBlocks.blockMultiObsidianDiamond
    };

    @ModRecipe
    public static Object[] fluidheater = new Object[]{
            ElementtimesBlocks.fluidHeater,
            ElementtimesBlocks.blockMultiSilverCopper, ElementtimesBlocks.cementAndSteelBarMixture, ElementtimesBlocks.blockMultiTinLead,
            ElementtimesItems.fireElement, ElementtimesBlocks.fluidReactor, ElementtimesItems.fireElement,
            ElementtimesBlocks.blockMultiWoodStone, ElementtimesBlocks.cementAndSteelBarMixture, ElementtimesBlocks.blockMultiObsidianDiamond
    };

    @ModRecipe
    public static Object[] solidmelter = new Object[]{
            ElementtimesBlocks.solidMelter,
            "gearGold", "blockSteel", "gearGold",
            ElementtimesBlocks.furnace, ElementtimesBlocks.furnace, ElementtimesBlocks.furnace,
            "gearDiamond", "blockCopper", "gearDiamond"
    };

    @ModRecipe
    public static Object[] fluidreactor = new Object[]{
            ElementtimesBlocks.fluidReactor,
            ElementtimesBlocks.cement, ElementtimesBlocks.blockMultiTinLead, ElementtimesBlocks.cement,
            ElementtimesBlocks.pumpFluid, ElementtimesBlocks.fuelGenerator, ElementtimesBlocks.pumpFluid,
            ElementtimesBlocks.blockMultiGoldPlatinum, ElementtimesBlocks.blockMultiIronQuartz, ElementtimesBlocks.blockMultiSilverCopper
    };

    @ModRecipe
    public static Object[] compressor = new Object[]{
            ElementtimesBlocks.compressor,
            "gearDiamond", "blockCopper", "gearDiamond",
            "blockSteel", ElementtimesBlocks.fuelGenerator, "blockSteel",
            "gearGold", "blockCopper", "gearGold"
    };

    @ModRecipe
    public static Object[] electrolyticCell = new Object[]{
            ElementtimesBlocks.electrolyticCell,
            ElementtimesItems.carbonRod, ElementtimesBlocks.blockMultiCarbonSteel, ElementtimesItems.carbonRod,
            ElementtimesBlocks.fluidHeater, ElementtimesBlocks.coagulator, ElementtimesBlocks.condenser,
            ElementtimesItems.carbonRod, ElementtimesBlocks.blockMultiCarbonSteel, ElementtimesItems.carbonRod,
    };
    @ModRecipe
    public static Object[] coagulator = new Object[]{
            ElementtimesBlocks.coagulator,
            ElementtimesBlocks.blockMultiCarbonSteel, ElementtimesBlocks.cementAndSteelBarMixture, ElementtimesBlocks.blockMultiGoldPlatinum,
            ElementtimesItems.soilElement, ElementtimesBlocks.fluidReactor, ElementtimesItems.soilElement,
            ElementtimesBlocks.blockMultiIronQuartz, ElementtimesBlocks.cementAndSteelBarMixture, ElementtimesBlocks.blockMultiObsidianDiamond
    };
    @ModRecipe
    public static Object[] centrifuge = new Object[]{
            ElementtimesBlocks.centrifuge,
            Blocks.STICKY_PISTON, Items.SLIME_BALL, Blocks.CLAY,
            ElementtimesItems.woodElement, ElementtimesBlocks.fuelGenerator, ElementtimesItems.woodElement,
            Blocks.CLAY, Items.SLIME_BALL, Blocks.STICKY_PISTON,
    };
    @ModRecipe
    public static Object[] solidCentrifuge = new Object[]{
            ElementtimesBlocks.solidCentrifuge,
            Blocks.STICKY_PISTON,ElementtimesBlocks.cementAndSteelBarMixture, Blocks.CLAY,
            ElementtimesItems.woodElement, ElementtimesBlocks.centrifuge, ElementtimesItems.woodElement,
            Blocks.CLAY, ElementtimesBlocks.cementAndSteelBarMixture, Blocks.STICKY_PISTON,
    };


    @ModRecipe
    public static Object[] woodenHalter = new Object[]{
            ElementtimesItems.woodenHalter,
            null,      "logWood", null,
            "logWood", "logWood", "logWood",
            "logWood"
    };

    @ModRecipe(ore = false)
    public static Object[] rebuild = new Object[]{
            ElementtimesBlocks.rebuild,
            ElementtimesBlocks.coagulator, ElementtimesBlocks.pulverizer, ElementtimesBlocks.condenser,
            ElementtimesBlocks.forming, ElementtimesBlocks.electrolyticCell, ElementtimesBlocks.extractor,
            ElementtimesBlocks.centrifuge, ElementtimesBlocks.compressor, ElementtimesBlocks.blockMultiCarbonSteel
    };

    @ModRecipe
    public static Object[] forming = new Object[]{
            ElementtimesBlocks.forming,
            null, ElementtimesBlocks.blockMultiIronQuartz, null,
            ElementtimesBlocks.blockMultiGoldPlatinum, ElementtimesBlocks.fuelGenerator, ElementtimesBlocks.blockMultiCarbonSteel,
            null, ElementtimesBlocks.blockMultiTinLead, null
    };

    @ModRecipe
    public static Object[] extractor = new Object[]{
            ElementtimesBlocks.extractor,
            null, ElementtimesBlocks.blockMultiTinLead, null,
            ElementtimesBlocks.blockMultiCarbonSteel, ElementtimesBlocks.fuelGenerator, ElementtimesBlocks.blockMultiGoldPlatinum,
            null, ElementtimesBlocks.blockMultiIronQuartz, null
    };

    @ModRecipe
    public static Object[] pumpAir = new Object[] {
            ElementtimesBlocks.pumpAir,
            Blocks.REDSTONE_BLOCK, Blocks.LAPIS_BLOCK, Blocks.REDSTONE_BLOCK,
            Blocks.LAPIS_BLOCK, ElementtimesBlocks.compressor, Blocks.LAPIS_BLOCK,
            Blocks.REDSTONE_BLOCK, Blocks.LAPIS_BLOCK, Blocks.REDSTONE_BLOCK
    };

    @ModRecipe
    public static Object[] pumpFluid = new Object[] {
            ElementtimesBlocks.pumpFluid,
            null, ElementtimesBlocks.blockMultiSilverCopper, null,
            ElementtimesBlocks.blockMultiGoldPlatinum, ElementtimesBlocks.pumpAir, ElementtimesBlocks.blockMultiCarbonSteel,
            null, ElementtimesBlocks.blockMultiWoodStone, null
    };
    
    @ModRecipe
    public static Object[] cement = new Object[] {
            ElementtimesBlocks.cement,
            null,Items.FLINT, null,
            ElementtimesItems.concrete,PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), PotionTypes.WATER),ElementtimesItems.concrete,
            null,Items.FLINT, null
    };

    @ModRecipe(shaped = false)
    public static Object[] itemReducer = new Object[] {
            ElementtimesBlocks.itemReducer,
            ElementtimesItems.woodElement, Blocks.CRAFTING_TABLE, ElementtimesBlocks.fuelGenerator
    };

    @ModRecipe
    public static Object gearCarbon = RecipeUtil.gearRecipe(Blocks.COAL_BLOCK, new ItemStack(Items.COAL, 1, 0), ElementtimesItems.gearCarbon);
    @ModRecipe
    public static Object gearCopper = RecipeUtil.gearRecipe("blockCopper", "ingotCopper", ElementtimesItems.gearCopper);
    @ModRecipe
    public static Object gearDiamond = RecipeUtil.gearRecipe("blockDiamond", "gemDiamond", ElementtimesItems.gearDiamond);
    @ModRecipe
    public static Object gearGold = RecipeUtil.gearRecipe("blockGold", "ingotGold", ElementtimesItems.gearGold);
    @ModRecipe
    public static Object gearIron = RecipeUtil.gearRecipe("blockIron", "ingotIron", ElementtimesItems.gearIron);
    @ModRecipe
    public static Object gearPlatinum = RecipeUtil.gearRecipe("blockPlatinum", "ingotPlatinum", ElementtimesItems.gearPlatinum);
    @ModRecipe
    public static Object gearQuartz = RecipeUtil.gearRecipe("blockQuartz", "gemQuartz", ElementtimesItems.gearQuartz);
    @ModRecipe
    public static Object gearSteel = RecipeUtil.gearRecipe("blockSteel", "ingotSteel", ElementtimesItems.gearSteel);
    @ModRecipe
    public static Object gearStone = RecipeUtil.gearRecipe(ElementtimesBlocks.stoneBlock, "stone", ElementtimesItems.gearStone);
    @ModRecipe
    public static Object gearWood = RecipeUtil.gearRecipe("logWood", "plankWood", ElementtimesItems.gearWood);
    @ModRecipe
    public static Object gearSilver = RecipeUtil.gearRecipe("blockSilver", "ingotSilver", ElementtimesItems.gearSilver);
    @ModRecipe
    public static Object gearTin = RecipeUtil.gearRecipe("blockTin", "ingotTin", ElementtimesItems.gearTin);
    @ModRecipe
    public static Object gearLead = RecipeUtil.gearRecipe("blockLead", "ingotLead", ElementtimesItems.gearLead);
    @ModRecipe
    public static Object gearObsidian = RecipeUtil.gearRecipe("blockObsidian", "ingotObsidian", ElementtimesItems.gearObsidian);

}
