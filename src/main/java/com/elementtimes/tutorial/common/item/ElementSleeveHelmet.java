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
		super(EnumHelper.addArmorMaterial("elementsleeve", "elementtimes:element_sleeve", 100, new int[] {20, 60, 40, 20}, 30, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 10.0F), 1, EntityEquipmentSlot.HEAD);
		setRegistryName("element_sleeve_helmet");
		setUnlocalizedName("elementsleevehelmet");
		setCreativeTab(Elementtimestab.tabBlocks);
	}

}