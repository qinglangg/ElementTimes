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

public class ElementtimesRecipe {
    public static void Init(FMLInitializationEvent event) {
    	BrewingRecipeRegistry.addRecipe(PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), PotionTypes.EMPTY), new ItemStack(ElementtimesItems.Sulfurpowder),new ItemStack(ElementtimesItems.Sulfitesolution,2));
    	BrewingRecipeRegistry.addRecipe(PotionUtils.addPotionToItemStack(new ItemStack(ElementtimesItems.Steam), PotionTypes.EMPTY), new ItemStack(Items.IRON_INGOT),new ItemStack(ElementtimesItems.Hydrogen));
    	BrewingRecipeRegistry.addRecipe(PotionUtils.addPotionToItemStack(new ItemStack(ElementtimesItems.Sulphuricacid), PotionTypes.EMPTY),"ingotCopper",new ItemStack(ElementtimesItems.Hydrogen));
    	BrewingRecipeRegistry.addRecipe(PotionUtils.addPotionToItemStack(new ItemStack(ElementtimesItems.Steam), PotionTypes.EMPTY), new ItemStack(ElementtimesItems.Ironpower),new ItemStack(Items.IRON_INGOT));
    	BrewingRecipeRegistry.addRecipe(PotionUtils.addPotionToItemStack(new ItemStack(ElementtimesItems.Steam), PotionTypes.EMPTY), new ItemStack(ElementtimesItems.Coalpowder),new ItemStack(Items.COAL));
    	BrewingRecipeRegistry.addRecipe(PotionUtils.addPotionToItemStack(new ItemStack(ElementtimesItems.Steam), PotionTypes.EMPTY), new ItemStack(ElementtimesItems.Copperpowder),new ItemStack(ElementtimesItems.Copper));
    	BrewingRecipeRegistry.addRecipe(PotionUtils.addPotionToItemStack(new ItemStack(ElementtimesItems.Steam), PotionTypes.EMPTY), new ItemStack(ElementtimesItems.Goldpowder),new ItemStack(Items.GOLD_INGOT));
    	BrewingRecipeRegistry.addRecipe(PotionUtils.addPotionToItemStack(new ItemStack(ElementtimesItems.Steam), PotionTypes.EMPTY), new ItemStack(ElementtimesItems.Diamondpowder),new ItemStack(Items.DIAMOND));
    	BrewingRecipeRegistry.addRecipe(PotionUtils.addPotionToItemStack(new ItemStack(ElementtimesItems.Steam), PotionTypes.EMPTY), new ItemStack(ElementtimesItems.Greenstonepowder),new ItemStack(Items.EMERALD));
    	BrewingRecipeRegistry.addRecipe(PotionUtils.addPotionToItemStack(new ItemStack(ElementtimesItems.Steam), PotionTypes.EMPTY), new ItemStack(ElementtimesItems.Platinumorepowder),new ItemStack(ElementtimesItems.Platinumingot));
    	BrewingRecipeRegistry.addRecipe(PotionUtils.addPotionToItemStack(new ItemStack(ElementtimesItems.Steam), PotionTypes.EMPTY), new ItemStack(ElementtimesItems.Redstonepowder),new ItemStack(Items.REDSTONE,2));
      	BrewingRecipeRegistry.addRecipe(PotionUtils.addPotionToItemStack(new ItemStack(ElementtimesItems.Steam), PotionTypes.EMPTY), new ItemStack(ElementtimesItems.Bluestonepowder),new ItemStack(Items.DYE, 2, 4));
    	BrewingRecipeRegistry.addRecipe(new IBrewingRecipe() {
			
			@Override
			public boolean isInput(ItemStack input) {
				if(input.getItem()==Item.getItemFromBlock(Blocks.COAL_BLOCK))//药水槽
					return true;
				return false;
			}
			
			@Override
			public boolean isIngredient(ItemStack ingredient) {
				if(ingredient.getItem()==Item.getItemFromBlock(Blocks.DIAMOND_BLOCK))//材料
					return true;
				return false;
			}
			
			@Override
			public ItemStack getOutput(ItemStack input, ItemStack ingredient) {
				//返回的材料
				if(input.getItem()==Item.getItemFromBlock(Blocks.COAL_BLOCK)&&ingredient.getItem()==Item.getItemFromBlock(Blocks.DIAMOND_BLOCK))
				return new ItemStack(ElementtimesItems.Diamondingot);
				return new ItemStack(Items.AIR);
			}
		});
    }
}
