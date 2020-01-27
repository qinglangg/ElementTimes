package com.elementtimes.tutorial.common.init;

import com.elementtimes.elementcore.api.annotation.old.ModRecipe;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionUtils;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * 所有合成表
 * @author 卿岚
 */
public class ElementtimesRecipe {

    public static void init(FMLInitializationEvent event) {
    	BrewingRecipeRegistry.addRecipe(new ItemStack(Blocks.COAL_BLOCK), new ItemStack(Blocks.DIAMOND_BLOCK), new ItemStack(ElementtimesItems.diamondIngot));

		GameRegistry.addSmelting(Blocks.STONE, new ItemStack(ElementtimesItems.stoneIngot, 1), 2.0f);
		GameRegistry.addSmelting(ElementtimesBlocks.stoneBlock, new ItemStack(ElementtimesItems.calciumCarbonate, 1), 3.0f);
		GameRegistry.addSmelting(ElementtimesItems.calciumCarbonate, new ItemStack(ElementtimesItems.calciumOxide, 1), 4.0f);
		GameRegistry.addSmelting(ElementtimesBlocks.SilverOre, new ItemStack(ElementtimesItems.silver, 1), 5.0f);
		GameRegistry.addSmelting(ElementtimesBlocks.sulfurOre, new ItemStack(ElementtimesItems.sulfurPowder, 3), 5.0f);
		GameRegistry.addSmelting(ElementtimesBlocks.calciumFluoride, new ItemStack(Blocks.GLOWSTONE, 1), 5.0f);
		GameRegistry.addSmelting(ElementtimesItems.sulfurOrePowder, new ItemStack(ElementtimesItems.sulfurPowder, 2), 4.0f);
		GameRegistry.addSmelting(ElementtimesItems.greenstonePowder, new ItemStack(Items.EMERALD, 1), 4.0f);
		GameRegistry.addSmelting(ElementtimesItems.redstonePowder, new ItemStack(Items.REDSTONE, 2), 4.0f);
		GameRegistry.addSmelting(ElementtimesItems.bluestonePowder, new ItemStack(Items.DYE, 2, 4), 4.0f);
		GameRegistry.addSmelting(ElementtimesItems.goldPowder, new ItemStack(Items.GOLD_INGOT, 1), 4.0f);
		GameRegistry.addSmelting(ElementtimesItems.diamondPowder, new ItemStack(Items.DIAMOND, 1), 4.0f);
		GameRegistry.addSmelting(ElementtimesItems.coalPowder, new ItemStack(Items.COAL, 1,0), 4.0f);
		GameRegistry.addSmelting(ElementtimesItems.quartzPowder, new ItemStack(Items.QUARTZ, 2), 4.0f);
		GameRegistry.addSmelting(ElementtimesItems.silverPowder, new ItemStack(ElementtimesItems.silver, 1), 4.0f);
		GameRegistry.addSmelting(ElementtimesItems.leadPowder, new ItemStack(ElementtimesItems.lead, 1), 4.0f);
		GameRegistry.addSmelting(ElementtimesItems.tinPowder, new ItemStack(ElementtimesItems.tin, 1), 4.0f);
		GameRegistry.addSmelting(ElementtimesItems.corn, new ItemStack(ElementtimesItems.bakedCorn, 1), 2.0f);
		GameRegistry.addSmelting(ElementtimesBlocks.platinumOre, new ItemStack(ElementtimesItems.platinumIngot, 1), 5.0f);
		GameRegistry.addSmelting(ElementtimesItems.platinumOrePowder, new ItemStack(ElementtimesItems.platinumIngot, 1), 4.0f);
		GameRegistry.addSmelting(ElementtimesItems.rubberRaw, new ItemStack(ElementtimesItems.rubber), 2.0f);
		GameRegistry.addSmelting(ElementtimesBlocks.oreSalt, new ItemStack(ElementtimesItems.salt, 3), 2.0f);
		GameRegistry.addSmelting(ElementtimesBlocks.rubberLog, new ItemStack(Items.COAL, 1, 1), 2.0f);
		GameRegistry.addSmelting(ElementtimesItems.stonepowder, new ItemStack(ElementtimesItems.stoneIngot, 1), 2.0f);
		GameRegistry.addSmelting(ElementtimesItems.sandpowder, new ItemStack(Blocks.SAND, 1), 2.0f);
		GameRegistry.addSmelting(ElementtimesItems.smallHammer, new ItemStack(ElementtimesItems.copper, 1),5.0f);
		GameRegistry.addSmelting(ElementtimesItems.mediumHammer, new ItemStack(ElementtimesItems.steelIngot, 1),5.0f);
		GameRegistry.addSmelting(ElementtimesItems.bigHammer, new ItemStack(ElementtimesItems.diamondIngot, 1),5.0f);
    }

	@ModRecipe
	public static Object[] CEMENT_Fix = new Object[] {
			"elementtimes:cement",
			null, "minecraft:flint", null,
			"elementtimes:concrete", PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), PotionTypes.WATER), "elementtimes:concrete",
			null, "minecraft:flint", null
	};
}
