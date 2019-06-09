package com.elementtimes.tutorial.common.init;

import com.elementtimes.tutorial.annotation.ModRecipe;

/**
 * 所有合成表
 * @author luqin2007
 */
public class ElementtimesRecipeCrafting {
	@ModRecipe
	@ModRecipe.Ore(value = "ironpower", output = "elementtimes:ironpower")
	public static String oreIron = "oreIron";

	@ModRecipe
	@ModRecipe.Ore(value = "emerald", output = "minecraft:emerald")
	public static String oreEmerald = "oreEmerald";

	@ModRecipe
	@ModRecipe.Ore(value = "sulfurpowder", output = "elementtimes:sulfurpowder")
	public static String oreSulfur = "oreSulfur";

	@ModRecipe
	@ModRecipe.Ore(value = "salt", output = "elementtimes:salt")
	public static String oreSalt = "oreSalt";

	@ModRecipe
	@ModRecipe.Ore(value = "goldpowder", output = "elementtimes:goldpowder")
	public static String oreGold = "oreGold";

	@ModRecipe
	@ModRecipe.Ore(value = "copperpowder", output = "elementtimes:copperpowder")
	public static String oreCopper = "oreCopper";

	@ModRecipe
	@ModRecipe.Ore(value = "platinumorepowder", output = "elementtimes:platinumorepowder")
	public static String orePlatinum = "orePlatinum";

	@ModRecipe
	@ModRecipe.Ore(value = "quartz", output = "minecraft:quartz")
	public static String oreQuartz = "oreQuartz";

	@ModRecipe
	@ModRecipe.Ore(value = "lapis", damage = 2, output = "minecraft:dye:4")
	public static String oreLapis = "oreLapis";

	@ModRecipe
	@ModRecipe.Ore(value = "redstonepowder", damage = 2, output = "elementtimes:redstonepowder")
	public static String oreRedstone = "oreRedstone";

	@ModRecipe
	@ModRecipe.Ore(value = "diamondpowder", damage = 2, output = "elementtimes:diamondpowder")
	public static String oreDiamond = "oreDiamond";

	@ModRecipe
	@ModRecipe.Ore(value = "blaze_powder", damage = 2, dustCount = 8, output = "minecraft:blaze_powder")
	public static String blazeRod = "minecraft:blaze_rod";

	@ModRecipe
	@ModRecipe.Ore(value = "wood", dustCount = 10, output = "")
	public static String logWood = "logWood";

	@ModRecipe
	@ModRecipe.Crafting(width = 2)
	public static Object[] blockMultiSliverCopper = new Object[] {
			ElementtimesBlocks.blockMultiSilverCopper,
			"plateSilver", "plateCopper",
			"gearSilver", "gearCopper",
			"plateSilver", "plateCopper"
	};

	@ModRecipe
	@ModRecipe.Crafting(width = 2)
	public static Object[] blockMultiCarbonSteel = new Object[] {
			ElementtimesBlocks.blockMultiCarbonSteel,
			"plateCarbon", "plateSteel",
			"gearCarbon", "gearSteel",
			"plateCarbon", "plateSteel"
	};

	@ModRecipe
	@ModRecipe.Crafting(width = 2)
	public static Object[] blockMultiGoldPlatinum = new Object[] {
			ElementtimesBlocks.blockMultiGoldPlatinum,
			"platePlatinum", "plateGold",
			"gearPlatinum", "gearGold",
			"platePlatinum", "plateGold"
	};

	@ModRecipe
	@ModRecipe.Crafting(width = 2)
	public static Object[] blockMultiWoodStone = new Object[] {
			ElementtimesBlocks.blockMultiWoodStone,
			"plateWood", "plateStone",
			"gearWood", "gearStone",
			"plateWood", "plateStone"
	};

	@ModRecipe
	@ModRecipe.Crafting
	public static Object[] spanner = new Object[] {
			ElementtimesItems.spanner,
			null, null, "ingotSteel",
			null, "ingotCopper", null,
			"ingotCopper"
	};

	@ModRecipe
	@ModRecipe.Crafting
	public static Object[] compressor = new Object[] {
			ElementtimesBlocks.compressor,
			"gearDiamond", "blockCopper", "gearDiamond",
			"blockCopper", ElementtimesBlocks.fuelGenerator, "blockCopper",
			"gearGold", "blockCopper", "gearGold"
	};

	@ModRecipe
	@ModRecipe.Crafting(ore = false)
	public static Object[] woodenHalter = new Object[] {
			ElementtimesItems.woodenHalter,
			null, ElementtimesBlocks.rubberLog, null,
			ElementtimesBlocks.rubberLog, ElementtimesBlocks.rubberLog, ElementtimesBlocks.rubberLog,
			ElementtimesBlocks.rubberLog
	};

	@ModRecipe
	@ModRecipe.Crafting(ore = false)
	public static Object[] rebuild = new Object[] {
			ElementtimesBlocks.rebuild,
			ElementtimesBlocks.blockMultiSilverCopper, ElementtimesBlocks.cementAndSteelBarMixture, ElementtimesBlocks.blockMultiGoldPlatinum,
			ElementtimesBlocks.pulverizer, ElementtimesBlocks.forming, ElementtimesBlocks.compressor,
			ElementtimesBlocks.blockMultiWoodStone, ElementtimesBlocks.cementAndSteelBarMixture, ElementtimesBlocks.blockMultiCarbonSteel
	};

	@ModRecipe
	@ModRecipe.Crafting
	public static Object[] forming = new Object[] {
			ElementtimesBlocks.forming,
			null, "blockSteel", null,
			ElementtimesBlocks.blockMultiWoodStone, ElementtimesBlocks.fuelGenerator, ElementtimesBlocks.blockMultiSilverCopper,
			null, "blockSteel", null
	};
}
