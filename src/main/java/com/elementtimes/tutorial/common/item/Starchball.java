package com.elementtimes.tutorial.common.item;

import akka.japi.Effect;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class Starchball extends ItemFood {

	public Starchball() {
		super(20, 20.0F, false);
	}

	@Override
	public int getItemBurnTime(ItemStack itemStack) {
		return 1600;
	}
    @Override
    public void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player)
    {
        if (!worldIn.isRemote)
        {
            player.addExperience(10);
        }
        super.onFoodEaten(stack, worldIn, player);
    }
}
