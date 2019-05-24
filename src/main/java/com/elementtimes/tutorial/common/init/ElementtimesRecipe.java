package com.elementtimes.tutorial.common.init;

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

public class ElementtimesRecipe {
    public static void Init(FMLInitializationEvent event) {
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
				if(input.getItem()==Item.getItemFromBlock(Blocks.COAL_BLOCK)&&ingredient.getItem()==Item.getItemFromBlock(Blocks.DIAMOND_BLOCK))
				return new ItemStack(ElementtimesItems.diamondIngot);
				return new ItemStack(Items.AIR);
			}
		});
    }

	public static void init() {
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
}
