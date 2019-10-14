package com.elementtimes.tutorial.common.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
/**
 * @author 卿岚
 */
public class Starchball extends ModFood {

    public Starchball() {
        super(20, 20.0F);
    }

    @Override
    public int getItemBurnTime(ItemStack itemStack) {
        return 1600;
    }

    @Override
    protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player) {
        if (!worldIn.isRemote) {
            player.addExperience(10);
            player.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 40, 1));
        }

        super.onFoodEaten(stack, worldIn, player);
    }
}
