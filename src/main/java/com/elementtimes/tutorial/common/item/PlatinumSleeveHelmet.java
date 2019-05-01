package com.elementtimes.tutorial.common.item;

import com.elementtimes.tutorial.common.init.Elementtimestab;

import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraftforge.common.util.EnumHelper;

public class PlatinumSleeveHelmet extends ItemArmor
{
	public PlatinumSleeveHelmet() 
	{
		super(EnumHelper.addArmorMaterial("platinumsleeve", "elementtimes:platinum_sleeve", 2000, new int[] {3,6,8,3}, 15, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 6.0F), 1, EntityEquipmentSlot.HEAD);
		setRegistryName("platinum_sleeve_helmet");
		setUnlocalizedName("platinumsleevehelmet");
		setCreativeTab(Elementtimestab.tabBlocks);
	}

}