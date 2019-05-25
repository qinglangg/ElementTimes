package com.elementtimes.tutorial.common.item;

import com.elementtimes.tutorial.Elementtimes;
import com.elementtimes.tutorial.util.ItemUtil;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.util.EnumHelper;

public class ElementSleeveBody extends ItemArmor
{
	public ElementSleeveBody() 
	{
		super(EnumHelper.addArmorMaterial("elementsleevebody", "elementtimes:element_sleeve", 10000, new int[] {30,60,80,30}, 30, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 80.0F), 1, EntityEquipmentSlot.CHEST);
	}
	
	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		super.getSubItems(tab, items); //  Item 实现：判断 添加一个物品
		items.stream().filter(itemStack -> itemStack.getItem() == this).forEach(itemStack -> {
			ItemUtil.addMaxEnchantments(itemStack);
			Elementtimes.getLogger().warn("find {}[{}] in {}", itemStack.getDisplayName(), itemStack.getItem().toString(), this.getRegistryName());
		});
	}
}