package com.elementtimes.tutorial.common.item;

import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraftforge.common.util.EnumHelper;
import tab.Elementtimestab;

public class ElementSleeveHelmet extends ItemArmor
{
	public ElementSleeveHelmet() 
	{
		super(EnumHelper.addArmorMaterial("elementsleeve", "elementtimes:element_sleeve", 100, new int[] {3,6,8,3}, 30, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 50.0F), 1, EntityEquipmentSlot.HEAD);
		setRegistryName("element_sleeve_helmet");
		setUnlocalizedName("elementsleevehelmet");
		setCreativeTab(Elementtimestab.tabBlocks);
	}

}