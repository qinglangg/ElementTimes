package com.elementtimes.tutorial.common.init;

import com.elementtimes.tutorial.annotation.ModRecipe;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionUtils;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.common.brewing.IBrewingRecipe;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * 所有合成表
 * @author KSGFK
 */
public class ElementtimesRecipe {
    public static void init(FMLInitializationEvent event) {
    	BrewingRecipeRegistry.addRecipe(PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), PotionTypes.EMPTY), new ItemStack(ElementtimesItems.sulfurPowder),new ItemStack(ElementtimesItems.sulfiteSolution,2));
    	BrewingRecipeRegistry.addRecipe(PotionUtils.addPotionToItemStack(new ItemStack(ElementtimesItems.steam), PotionTypes.EMPTY), new ItemStack(Items.IRON_INGOT),new ItemStack(ElementtimesItems.hydrogen));
    	BrewingRecipeRegistry.addRecipe(PotionUtils.addPotionToItemStack(new ItemStack(ElementtimesItems.sulphuricAcid), PotionTypes.EMPTY),"ingotCopper",new ItemStack(ElementtimesItems.hydrogen));
    	BrewingRecipeRegistry.addRecipe(PotionUtils.addPotionToItemStack(new ItemStack(ElementtimesItems.steam), PotionTypes.EMPTY), new ItemStack(ElementtimesItems.ironPower),new ItemStack(Items.IRON_INGOT));
    	BrewingRecipeRegistry.addRecipe(PotionUtils.addPotionToItemStack(new ItemStack(ElementtimesItems.steam), PotionTypes.EMPTY), new ItemStack(ElementtimesItems.coalPowder),new ItemStack(Items.COAL));
    	BrewingRecipeRegistry.addRecipe(PotionUtils.addPotionToItemStack(new ItemStack(ElementtimesItems.steam), PotionTypes.EMPTY), new ItemStack(ElementtimesItems.copperPowder),new ItemStack(ElementtimesItems.copper));
    	BrewingRecipeRegistry.addRecipe(PotionUtils.addPotionToItemStack(new ItemStack(ElementtimesItems.steam), PotionTypes.EMPTY), new ItemStack(ElementtimesItems.goldPowder),new ItemStack(Items.GOLD_INGOT));
    	BrewingRecipeRegistry.addRecipe(PotionUtils.addPotionToItemStack(new ItemStack(ElementtimesItems.steam), PotionTypes.EMPTY), new ItemStack(ElementtimesItems.diamondPowder),new ItemStack(Items.DIAMOND));
    	BrewingRecipeRegistry.addRecipe(PotionUtils.addPotionToItemStack(new ItemStack(ElementtimesItems.steam), PotionTypes.EMPTY), new ItemStack(ElementtimesItems.greenstonePowder),new ItemStack(Items.EMERALD));
    	BrewingRecipeRegistry.addRecipe(PotionUtils.addPotionToItemStack(new ItemStack(ElementtimesItems.steam), PotionTypes.EMPTY), new ItemStack(ElementtimesItems.platinumOrePowder),new ItemStack(ElementtimesItems.platinumIngot));
    	BrewingRecipeRegistry.addRecipe(PotionUtils.addPotionToItemStack(new ItemStack(ElementtimesItems.steam), PotionTypes.EMPTY), new ItemStack(ElementtimesItems.redstonePowder),new ItemStack(Items.REDSTONE,2));
      	BrewingRecipeRegistry.addRecipe(PotionUtils.addPotionToItemStack(new ItemStack(ElementtimesItems.steam), PotionTypes.EMPTY), new ItemStack(ElementtimesItems.bluestonePowder),new ItemStack(Items.DYE, 2, 4));
    	BrewingRecipeRegistry.addRecipe(PotionUtils.addPotionToItemStack(new ItemStack(ElementtimesItems.steam), PotionTypes.EMPTY), new ItemStack(ElementtimesItems.quartzPowder),new ItemStack(Items.QUARTZ,2));
    	BrewingRecipeRegistry.addRecipe(new IBrewingRecipe() {
			
			@Override
			public boolean isInput(ItemStack input) {
                //药水槽
                return input.getItem() == Item.getItemFromBlock(Blocks.COAL_BLOCK);
            }
			
			@Override
			public boolean isIngredient(ItemStack ingredient) {
                //材料
                return ingredient.getItem() == Item.getItemFromBlock(Blocks.DIAMOND_BLOCK);
            }
			
			@Override
			public ItemStack getOutput(ItemStack input, ItemStack ingredient) {
				//返回的材料
				if(input.getItem()==Item.getItemFromBlock(Blocks.COAL_BLOCK)&&ingredient.getItem()==Item.getItemFromBlock(Blocks.DIAMOND_BLOCK)) {
                    return new ItemStack(ElementtimesItems.diamondIngot);
                }
				return new ItemStack(Items.AIR);
			}
		});

		GameRegistry.addSmelting(Blocks.STONE, new ItemStack(ElementtimesItems.stoneIngot, 1), 2.0f);
		GameRegistry.addSmelting(ElementtimesBlocks.stoneBlock, new ItemStack(ElementtimesItems.calciumCarbonate, 1), 3.0f);
		GameRegistry.addSmelting(ElementtimesItems.calciumCarbonate, new ItemStack(ElementtimesItems.calciumOxide, 2), 4.0f);
		GameRegistry.addSmelting(ElementtimesBlocks.copperOre, new ItemStack(ElementtimesItems.copper, 1), 5.0f);
		GameRegistry.addSmelting(ElementtimesBlocks.sulfurOre, new ItemStack(ElementtimesItems.sulfurPowder, 3), 5.0f);
		GameRegistry.addSmelting(ElementtimesBlocks.calciumFluoride, new ItemStack(Blocks.GLOWSTONE, 1), 5.0f);
		GameRegistry.addSmelting(ElementtimesItems.sulfurOrePowder, new ItemStack(ElementtimesItems.sulfurPowder, 2), 4.0f);
		GameRegistry.addSmelting(ElementtimesItems.greenstonePowder, new ItemStack(Items.EMERALD, 1), 4.0f);
		GameRegistry.addSmelting(ElementtimesItems.redstonePowder, new ItemStack(Items.REDSTONE, 2), 4.0f);
		GameRegistry.addSmelting(ElementtimesItems.bluestonePowder, new ItemStack(Items.DYE, 2, 4), 4.0f);
		GameRegistry.addSmelting(ElementtimesItems.ironPower, new ItemStack(Items.IRON_INGOT, 1), 4.0f);
		GameRegistry.addSmelting(ElementtimesItems.goldPowder, new ItemStack(Items.GOLD_INGOT, 1), 4.0f);
		GameRegistry.addSmelting(ElementtimesItems.diamondPowder, new ItemStack(Items.DIAMOND, 1), 4.0f);
		GameRegistry.addSmelting(ElementtimesItems.copperPowder, new ItemStack(ElementtimesItems.copper, 1), 4.0f);
		GameRegistry.addSmelting(ElementtimesItems.coalPowder, new ItemStack(Items.COAL, 1,0), 4.0f);
		GameRegistry.addSmelting(ElementtimesItems.quartzPowder, new ItemStack(Items.QUARTZ, 2), 4.0f);
		GameRegistry.addSmelting(ElementtimesItems.corn, new ItemStack(ElementtimesItems.bakedCorn, 1), 2.0f);
		GameRegistry.addSmelting(ElementtimesBlocks.platinumOre, new ItemStack(ElementtimesItems.platinumIngot, 1), 5.0f);
		GameRegistry.addSmelting(ElementtimesItems.platinumOrePowder, new ItemStack(ElementtimesItems.platinumIngot, 1), 4.0f);
		GameRegistry.addSmelting(Items.POTIONITEM, new ItemStack(ElementtimesItems.steam, 1), 3.0f);
		GameRegistry.addSmelting(ElementtimesItems.sulfiteSolution, new ItemStack(ElementtimesItems.sulphuricAcid, 2), 3.0f);
    }

    @ModRecipe
	@ModRecipe.Ore(value = "ironpower", output = "elementtimes:ironpower")
    public static String oreIron = "oreIron";

	@ModRecipe
	@ModRecipe.Ore(value = "goldpowder", output = "elementtimes:goldpowder")
	public static String oreGold = "oreGold";

	@ModRecipe
	@ModRecipe.Ore(value = "copperpowder", output = "elementtimes:copperpowder")
	public static String oreCopper = "oreCopper";

//	@ModRecipe
//	@ModRecipe.Ore(output = "elementtimes:copperpowder")
//	public static String oreSalt = "oreSalt";

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
//
//	@ModRecipe
//	@ModRecipe.Ore(output = "elementtimes:ironpower")
//	public static String log;


}
