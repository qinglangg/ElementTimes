package com.elementtimes.elementtimes.common.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.world.World;

import javax.annotation.Nonnull;



public class StarchBall extends ModFood {

    public StarchBall() {
        super(20, 20.0F);
    }

    @Override
    public int getBurnTime(ItemStack itemStack) {
        return 1600;
    }

    @Override
    @Nonnull
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, LivingEntity entityLiving) {
        if (!worldIn.isRemote && entityLiving instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) entityLiving;
            player.giveExperiencePoints(10);
            player.addPotionEffect(new EffectInstance(Effects.SLOWNESS, 40, 1));
        }
        return super.onItemUseFinish(stack, worldIn, entityLiving);
    }
}
