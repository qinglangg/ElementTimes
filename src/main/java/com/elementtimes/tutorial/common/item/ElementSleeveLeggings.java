package com.elementtimes.tutorial.common.item;

import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraftforge.common.util.EnumHelper;
import tab.Elementtimestab;

public class ElementSleeveLeggings extends ItemArmor
{
	public ElementSleeveLeggings() 
	{
		super(EnumHelper.addArmorMaterial("elementsleeve", "elementtimes:element_sleeve", 33, new int[] {20, 60, 40, 20}, 30, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 15.0F), 1, EntityEquipmentSlot.LEGS);
		setRegistryName("element_sleeve_leggings");
		setUnlocalizedName("elementsleeveleggings");
		setCreativeTab(Elementtimestab.tabBlocks);
	}

}