package com.elementtimes.tutorial.common.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class Starchball extends ItemFood {

    public Starchball() {
        super(20, 0.5F, false);
    }

    @Override
    public int getItemBurnTime(ItemStack itemStack) {
        return 1600;
    }

    @Override
    protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player) {
        if (!worldIn.isRemote) {
            player.addExperience(10);
            player.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 20, 0));
        }

        super.onFoodEaten(stack, worldIn, player);
    }
}
