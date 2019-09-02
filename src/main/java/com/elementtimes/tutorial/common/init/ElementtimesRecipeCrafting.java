package com.elementtimes.tutorial.common.init;

import com.elementtimes.elementcore.api.annotation.ModRecipe;
import com.elementtimes.tutorial.ElementTimes;
import com.elementtimes.tutorial.common.item.ItemBottleFuel;
import com.elementtimes.tutorial.other.FluidIngredient;
import com.elementtimes.tutorial.plugin.elementcore.HammerOreRecipe;
import com.elementtimes.tutorial.other.recipe.RecipeUtil;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Supplier;

/**
 * 所有合成表
 *
 * @author luqin2007
 */
@SuppressWarnings("unused")
public class ElementtimesRecipeCrafting {

    @HammerOreRecipe.Ore(value = "leadpowder", output = "elementtimes:leadpowder")
    public static String oreLead = "oreLead";
	
    @HammerOreRecipe.Ore(value = "tinpowder", output = "elementtimes:tinpowder")
    public static String oreTin = "oreTin";
	
    @HammerOreRecipe.Ore(value = "ironpowder", output = "elementtimes:ironpowder")
    public static String oreIron = "oreIron";

    @HammerOreRecipe.Ore(value = "emeraldpowder", output = "elementtimes:greenstonepowder")
    public static String oreEmerald = "oreEmerald";

    @HammerOreRecipe.Ore(value = "sulfurpowder", output = "elementtimes:sulfurorepowder")
    public static String oreSulfur = "oreSulfur";

    @HammerOreRecipe.Ore(value = "coalpodwer", output = "elementtimes:coalpowder")
    public static String oreCoal = "oreCoal";

    @HammerOreRecipe.Ore(value = "silverpowder", output = "elementtimes:silverpowder")
    public static String oreSilver = "oreSilver";

    @HammerOreRecipe.Ore(value = "uraniumpowder", output = "elementtimes:uraniumpowder", dustCount = 1)
    public static String oreUranium = "oreUranium";

    @HammerOreRecipe.Ore(value = "salt", output = "elementtimes:salt", dustCount = 8)
    public static String oreSalt = "oreSalt";
    
    @HammerOreRecipe.Ore(value = "goldpowder", output = "elementtimes:goldpowder")
    public static String oreGold = "oreGold";

    @HammerOreRecipe.Ore(value = "copperpowder", output = "elementtimes:copperpowder")
    public static String oreCopper = "oreCopper";

    @HammerOreRecipe.Ore(value = "platinumorepowder", output = "elementtimes:platinumorepowder")
    public static String orePlatinum = "orePlatinum";

    @HammerOreRecipe.Ore(value = "quartz", output = "elementtimes:quartzpowder")
    public static String oreQuartz = "oreQuartz";

    @HammerOreRecipe.Ore(value = "lapis", output = "elementtimes:bluestonepowder")
    public static String oreLapis = "oreLapis";

    @HammerOreRecipe.Ore(value = "redstonepowder", damage = 2, output = "elementtimes:redstonepowder")
    public static String oreRedstone = "oreRedstone";

    @HammerOreRecipe.Ore(value = "diamondpowder", damage = 2, output = "elementtimes:diamondpowder")
    public static String oreDiamond = "oreDiamond";

    @HammerOreRecipe.Ore(value = "blaze_powder", damage = 2, dustCount = 8, output = "minecraft:blaze_powder")
    public static String blazeRod = "minecraft:blaze_rod";

    @HammerOreRecipe.Ore(value = "wood", dustCount = 16, output = "")
    public static String logWood = "logWood";


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
            ElementtimesBlocks.blockMultiWoodStone, ElementtimesBlocks.cementAndSteelBarMixture, ElementtimesBlocks.blockMultiCarbonSteel,
            ElementtimesItems.waterElement, ElementtimesItems.waterElement, ElementtimesItems.waterElement,
            ElementtimesBlocks.blockMultiGoldPlatinum, ElementtimesBlocks.cementAndSteelBarMixture, ElementtimesBlocks.blockMultiSilverCopper
    };

    @ModRecipe
    public static Object[] fluidheater = new Object[]{
            ElementtimesBlocks.fluidHeater,
            ElementtimesBlocks.blockMultiWoodStone, ElementtimesBlocks.cementAndSteelBarMixture, ElementtimesBlocks.blockMultiCarbonSteel,
            ElementtimesItems.fireElement, ElementtimesItems.fireElement, ElementtimesItems.fireElement,
            ElementtimesBlocks.blockMultiGoldPlatinum, ElementtimesBlocks.cementAndSteelBarMixture, ElementtimesBlocks.blockMultiSilverCopper
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
            ElementtimesBlocks.blockMultiWoodStone, ElementtimesBlocks.blockMultiTinLead, ElementtimesBlocks.blockMultiCarbonSteel,
            ElementtimesBlocks.fluidHeater, ElementtimesBlocks.pumpFluid, ElementtimesBlocks.condenser,
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
            ElementtimesBlocks.fluidHeater, ElementtimesBlocks.fluidReactor, ElementtimesBlocks.condenser,
            ElementtimesItems.carbonRod, ElementtimesBlocks.blockMultiCarbonSteel, ElementtimesItems.carbonRod,
    };

    @ModRecipe
    public static Object[] woodenHalter = new Object[]{
            ElementtimesItems.woodenHalter,
            null, "logWood", null,
            "logWood", "logWood", "logWood",
            "logWood"
    };

    @ModRecipe(ore = false)
    public static Object[] rebuild = new Object[]{
            ElementtimesBlocks.rebuild,
            ElementtimesBlocks.blockMultiSilverCopper, ElementtimesBlocks.blockMultiIronQuartz, ElementtimesBlocks.blockMultiGoldPlatinum,
            ElementtimesBlocks.forming, ElementtimesBlocks.electrolyticCell, ElementtimesBlocks.extractor,
            ElementtimesBlocks.blockMultiObsidianDiamond, ElementtimesBlocks.blockMultiTinLead, ElementtimesBlocks.blockMultiCarbonSteel
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

    @ModRecipe
    public static Object createFluidRecipes() {
        return (Supplier) () -> {
            List<IRecipe> recipeList = new LinkedList<>();
            FluidRegistry.getBucketFluids()
                    .forEach(fluid -> {
                        NonNullList<Ingredient> input0 = NonNullList.create();
                        input0.add(Ingredient.fromItem(Items.GLASS_BOTTLE));
                        input0.add(FluidIngredient.bucket(fluid));
                        IRecipe bucketToBottle = new ShapelessRecipes("recipe", ItemBottleFuel.createByFluid(fluid), input0);
                        bucketToBottle.setRegistryName(new ResourceLocation(ElementTimes.MODID, fluid.getName() + "_convert_0"));
                        NonNullList<Ingredient> input1 = NonNullList.create();
                        input1.add(Ingredient.fromItem(Items.BUCKET));
                        input1.add(FluidIngredient.bottle(fluid));
                        IRecipe bottleToBucket = new ShapelessRecipes("recipe", FluidUtil.getFilledBucket(new FluidStack(fluid, Fluid.BUCKET_VOLUME)), input1);
                        bottleToBucket.setRegistryName(new ResourceLocation(ElementTimes.MODID, fluid.getName() + "_convert_1"));
                        recipeList.add(bottleToBucket);
                        recipeList.add(bucketToBottle);
                    });
            return recipeList;
        };
    }
}
