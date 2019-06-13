package com.elementtimes.tutorial.common.init;

import com.elementtimes.tutorial.ElementTimes;
import com.elementtimes.tutorial.annotation.ModRecipe;
import com.elementtimes.tutorial.common.item.ItemBottleFuel;
import com.elementtimes.tutorial.util.RecipeUtil;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 所有合成表
 * @author luqin2007
 */
@SuppressWarnings("unused")
public class ElementtimesRecipeCrafting {
	@ModRecipe
	@ModRecipe.Ore(value = "ironpower", output = "elementtimes:ironpower")
	public static String oreIron = "oreIron";

	@ModRecipe
	@ModRecipe.Ore(value = "emeraldpowder", output = "elementtimes:greenstonepowder")
	public static String oreEmerald = "oreEmerald";

	@ModRecipe
	@ModRecipe.Ore(value = "sulfurpowder", output = "elementtimes:sulfurorepowder")
	public static String oreSulfur = "oreSulfur";

	@ModRecipe
	@ModRecipe.Ore(value = "coalpodwer", output = "elementtimes:coalpowder")
	public static String oreCoal = "oreCoal";

	@ModRecipe
	@ModRecipe.Ore(value = "silverpowder", output = "elementtimes:silverpowder")
	public static String oreSilver = "oreSilver";

	@ModRecipe
	@ModRecipe.Ore(value = "salt", output = "elementtimes:salt", dustCount = 8)
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
	@ModRecipe.Ore(value = "quartz", output = "elementtimes:quartzpowder")
	public static String oreQuartz = "oreQuartz";

	@ModRecipe
	@ModRecipe.Ore(value = "lapis", output = "elementtimes:bluestonepowder")
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
			"blockSteel", ElementtimesBlocks.fuelGenerator, "blockSteel",
			"gearGold", "blockCopper", "gearGold"
	};

	@ModRecipe
	@ModRecipe.Crafting
	public static Object[] woodenHalter = new Object[] {
			ElementtimesItems.woodenHalter,
			null, "logWood", null,
			"logWood", "logWood", "logWood",
			"logWood"
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

	@ModRecipe
	@ModRecipe.Crafting
	public static IRecipe gearCarbon = RecipeUtil.gearRecipe("blockCoal", "coal", ElementtimesItems.gearCarbon);
	@ModRecipe
	@ModRecipe.Crafting
	public static IRecipe gearCopper = RecipeUtil.gearRecipe("blockCopper", "ingotCopper", ElementtimesItems.gearCopper);
	@ModRecipe
	@ModRecipe.Crafting
	public static IRecipe gearDiamond = RecipeUtil.gearRecipe("blockDiamond", "gemDiamond", ElementtimesItems.gearDiamond);
	@ModRecipe
	@ModRecipe.Crafting
	public static IRecipe gearGold = RecipeUtil.gearRecipe("blockGold", "ingotGold", ElementtimesItems.gearGold);
	@ModRecipe
	@ModRecipe.Crafting
	public static IRecipe gearIron = RecipeUtil.gearRecipe("blockIron", "ingotIron", ElementtimesItems.gearIron);
	@ModRecipe
	@ModRecipe.Crafting
	public static IRecipe gearPlatinum = RecipeUtil.gearRecipe("blockPlatinum", "ingotPlatinum", ElementtimesItems.gearPlatinum);
	@ModRecipe
	@ModRecipe.Crafting
	public static IRecipe gearQuartz = RecipeUtil.gearRecipe("blockQuartz", "gemQuartz", ElementtimesItems.gearQuartz);
	@ModRecipe
	@ModRecipe.Crafting
	public static IRecipe gearSteel = RecipeUtil.gearRecipe("blockSteel", "ingotSteel", ElementtimesItems.gearSteel);
	@ModRecipe
	@ModRecipe.Crafting
	public static IRecipe gearStone = RecipeUtil.gearRecipe(ElementtimesBlocks.stoneBlock, "stone", ElementtimesItems.gearStone);
	@ModRecipe
	@ModRecipe.Crafting
	public static IRecipe gearWood = RecipeUtil.gearRecipe("logWood", "plankWood", ElementtimesItems.gearWood);
	@ModRecipe
	@ModRecipe.Crafting
	public static IRecipe gearSilver = RecipeUtil.gearRecipe("blockSilver", "ingotSilver", ElementtimesItems.gearSilver);

	@ModRecipe
	@ModRecipe.Crafting
	public static IRecipe[] createFluidRecipes() {
		List<IRecipe> recipeList = new LinkedList<>();
		FluidRegistry.getRegisteredFluids().entrySet().stream()
				.filter(set -> set.getKey().startsWith(ElementTimes.MODID))
				.map(Map.Entry::getValue)
				.forEach(fluid -> {
					ItemStack bottle = ItemBottleFuel.createByFluid(fluid);
					ItemStack bucket = FluidUtil.getFilledBucket(new FluidStack(fluid, 1000));
					NonNullList<Ingredient> input0 = NonNullList.create();
					input0.add(Ingredient.fromItem(Items.GLASS_BOTTLE));
					input0.add(Ingredient.fromStacks(bucket));
					IRecipe bucketToBottle = new ShapelessRecipes("recipes", bottle, input0);
					bucketToBottle.setRegistryName(new ResourceLocation(ElementTimes.MODID, fluid.getName() + "_convert_0"));
					NonNullList<Ingredient> input1 = NonNullList.create();
					input1.add(Ingredient.fromItem(Items.BUCKET));
					input1.add(Ingredient.fromStacks(ItemBottleFuel.createByFluid(fluid)));
					IRecipe bottleToBucket = new ShapelessRecipes("recipes", bucket, input1);
					bottleToBucket.setRegistryName(new ResourceLocation(ElementTimes.MODID, fluid.getName() + "_convert_1"));
					recipeList.add(bottleToBucket);
					recipeList.add(bucketToBottle);
				});
		return recipeList.toArray(new IRecipe[0]);
	}
}
