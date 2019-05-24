package com.elementtimes.tutorial.common.item;

import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraftforge.common.util.EnumHelper;

public class PlatinumSleeveBody extends ItemArmor
{
	public PlatinumSleeveBody() 
	{
		super(EnumHelper.addArmorMaterial("platinumsleevebody", "elementtimes:platinum_sleeve", 2000, new int[] {3,6,8,3}, 15, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 16.0F), 1, EntityEquipmentSlot.CHEST);
	}
}