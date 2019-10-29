package com.elementtimes.tutorial.common.item;

import com.elementtimes.tutorial.common.capability.CapabilitySeaWater;
import com.elementtimes.tutorial.common.init.ElementtimesMagic;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class SaltedFish extends ModFood {

    public SaltedFish() {
        super(0, 0);
        setAlwaysEdible();
    }

    @Override
    protected void onFoodEaten(ItemStack stack, World worldIn, @Nonnull EntityPlayer player) {
        super.onFoodEaten(stack, worldIn, player);
        // Salt 效果前置：设置最大使用时间 = 2*(5-amplifier)
        PotionEffect saltEffect = player.getActivePotionEffect(ElementtimesMagic.salt);
        if (saltEffect != null) {
            // 增加咸度
            CapabilitySeaWater.ISeaWater cap = player.getCapability(CapabilitySeaWater.CAPABILITY_SEA_WATER, null);
            if (cap != null) {
                cap.increaseSalt(1);
            }
            // 恢复饥饿值与生命
            int amplifier = saltEffect.getAmplifier();
            player.getFoodStats().addStats((int) (amplifier * 1.5), amplifier * 2f);
            player.heal(amplifier);
        }
    }
}
