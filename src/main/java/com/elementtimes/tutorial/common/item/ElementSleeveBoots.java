package com.elementtimes.tutorial.common.item;

import com.elementtimes.tutorial.common.init.Elementtimestab;

import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraftforge.common.util.EnumHelper;

public class ElementSleeveBoots extends ItemArmor
{
	public ElementSleeveBoots() 
	{
		super(EnumHelper.addArmorMaterial("elementsleeve", "elementtimes:element_sleeve", 10000, new int[] {30,60,80,30}, 30, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 50.0F), 1, EntityEquipmentSlot.FEET);
		setRegistryName("element_sleeve_boots"); 
		setUnlocalizedName("elementsleeveboots");
		setCreativeTab(Elementtimestab.tabBlocks);
	}

}