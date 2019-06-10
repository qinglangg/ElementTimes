package com.elementtimes.tutorial.common.item;

import com.elementtimes.tutorial.util.ItemUtil;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.util.EnumHelper;

import javax.annotation.Nonnull;

public class ElementSleeveBoots extends ItemArmor {

	public ElementSleeveBoots() {
		super(EnumHelper.addArmorMaterial("elementsleeve", "elementtimes:element_sleeve", 10000, new int[] {30,60,80,30}, 30, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 50.0F), 1, EntityEquipmentSlot.FEET);
	}

	@Override
	public void getSubItems(@Nonnull CreativeTabs tab, @Nonnull NonNullList<ItemStack> items) {
		super.getSubItems(tab, items);
		items.stream().filter(itemStack -> itemStack.getItem() == this).forEach(ItemUtil::addMaxEnchantments);
	}
}