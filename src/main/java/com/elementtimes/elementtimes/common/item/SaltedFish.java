package com.elementtimes.elementtimes.common.item;

import com.elementtimes.elementtimes.common.capability.CapabilitySeaWater;
import com.elementtimes.elementtimes.common.init.Magic;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.world.World;



public class SaltedFish extends ModFood {

    public SaltedFish() {
        super(0, 0, true);
    }

    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, LivingEntity entityLiving) {
        EffectInstance saltEffect = entityLiving.getActivePotionEffect(Magic.saltEffect);
        // Salt 效果前置：设置最大使用时间 = 2*(5-amplifier)
        if (saltEffect != null) {
            entityLiving.getCapability(CapabilitySeaWater.CAPABILITY_SEA_WATER).ifPresent(cap -> {
                // 增加咸度
                cap.increaseSalt(1);
                // 恢复饥饿值与生命
                int amplifier = saltEffect.getAmplifier();
                if (entityLiving instanceof PlayerEntity) {
                    ((PlayerEntity) entityLiving).getFoodStats().addStats((int) (amplifier * 1.5), amplifier * 2f);
                }
                entityLiving.heal(amplifier);
            });
        }
        return super.onItemUseFinish(stack, worldIn, entityLiving);
    }
}
