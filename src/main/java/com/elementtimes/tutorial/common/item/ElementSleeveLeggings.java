package com.elementtimes.tutorial.common.item;

import com.elementtimes.tutorial.common.init.Elementtimestab;

import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraftforge.common.util.EnumHelper;

public class ElementSleeveLeggings extends ItemArmor
{
	public ElementSleeveLeggings() 
	{
		super(EnumHelper.addArmorMaterial("elementsleeve", "elementtimes:element_sleeve", 10000, new int[] {30,60,80,30}, 30, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 70.0F), 1, EntityEquipmentSlot.LEGS);
		setRegistryName("element_sleeve_leggings");
		setUnlocalizedName("elementsleeveleggings");
		setCreativeTab(Elementtimestab.tabBlocks);
	}

}