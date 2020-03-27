package com.elementtimes.tutorial.common.item;

import com.elementtimes.elementcore.api.common.ECUtils;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.common.util.EnumHelper;

import javax.annotation.Nonnull;
import java.util.Map;

public class Elementpickaxe  extends ItemPickaxe {

	public Elementpickaxe() 
	{
		super(EnumHelper.addToolMaterial("elementpickaxe", 4, 1000, 1000.0F, 15.0F, 100));
	}

	@Override
	public void getSubItems(@Nonnull CreativeTabs tab, @Nonnull NonNullList<ItemStack> items) {
		super.getSubItems(tab, items);
		items.stream().filter(itemStack -> itemStack.getItem() == this).forEach(ECUtils.item::addAllEnchantments);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer player, EnumHand hand) {
		if (!worldIn.isRemote) {
			ItemStack stack = player.getHeldItem(hand);
			Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(stack);
			if (enchantments.containsKey(Enchantments.FORTUNE)) {
				enchantments.remove(Enchantments.FORTUNE);
				enchantments.put(Enchantments.SILK_TOUCH, Enchantments.SILK_TOUCH.getMaxLevel());
				EnchantmentHelper.setEnchantments(enchantments, stack);
			} else if (enchantments.containsKey(Enchantments.SILK_TOUCH)) {
				enchantments.remove(Enchantments.SILK_TOUCH);
				enchantments.put(Enchantments.FORTUNE, Enchantments.FORTUNE.getMaxLevel());
				EnchantmentHelper.setEnchantments(enchantments, stack);
			}
			return new ActionResult<>(EnumActionResult.PASS, stack);
		}
		return super.onItemRightClick(worldIn, player, hand);
	}
}