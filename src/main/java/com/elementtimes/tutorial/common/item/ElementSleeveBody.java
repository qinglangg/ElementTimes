package com.elementtimes.tutorial.common.item;

import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraftforge.common.util.EnumHelper;
import tab.Elementtimestab;

public class ElementSleeveBody extends ItemArmor
{
	public ElementSleeveBody() 
	{
		super(EnumHelper.addArmorMaterial("elementsleevebody", "elementtimes:element_sleeve", 100, new int[] {20, 60, 40, 20}, 30, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 20.0F), 1, EntityEquipmentSlot.CHEST);
		setRegistryName("element_sleeve_body");
		setUnlocalizedName("elementsleevebody");
		setCreativeTab(Elementtimestab.tabBlocks);
	}
}