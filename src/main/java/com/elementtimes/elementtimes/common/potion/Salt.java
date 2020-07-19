package com.elementtimes.elementtimes.common.potion;

import com.elementtimes.elementtimes.common.capability.CapabilitySeaWater;
import com.elementtimes.elementtimes.common.init.Magic;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Potion;

/**
 * TODO icon 6 2
 * 腌制
 * 使用后可以快速食用盐，恢复生命与饥饿值
 * 效果结束后有惊喜

 */
public class Salt extends Potion {

    private static final int LEVEL_IV = 220;
    private static final int LEVEL_III = 150;
    private static final int LEVEL_II = 80;
    private static final int LEVEL_IV_TICK = 600;
    private static final int LEVEL_III_TICK = 300;
    private static final int LEVEL_II_TICK = 200;
    private static final int LEVEL_I_TICK = 100;

    public static final EffectInstance EFFECT_I = new EffectInstance(Magic.saltEffect, LEVEL_I_TICK, 1);
    public static final EffectInstance EFFECT_II = new EffectInstance(Magic.saltEffect, LEVEL_II_TICK, 2);
    public static final EffectInstance EFFECT_III = new EffectInstance(Magic.saltEffect, LEVEL_III_TICK, 3);
    public static final EffectInstance EFFECT_IV = new EffectInstance(Magic.saltEffect, LEVEL_IV_TICK, 4);

    public Salt() {
        super("salt", EFFECT_I, EFFECT_II, EFFECT_III, EFFECT_IV);
    }

    public static void effectOn(PlayerEntity player, CapabilitySeaWater.ISeaWater effectCap) {
        EffectInstance effect = player.getActivePotionEffect(Magic.saltEffect);
        if (effect == null) {
            player.addPotionEffect(EFFECT_I);
            effectCap.resetCollidedTick();
        } else {
            int collidedTick = effectCap.getCollidedTick();
            int amplifier = effect.getAmplifier();
            if (amplifier == 1 && collidedTick >= LEVEL_II) {
                player.addPotionEffect(EFFECT_II);
                effectCap.resetCollidedTick();
            } else if (amplifier == 2 && collidedTick >= LEVEL_III) {
                player.addPotionEffect(EFFECT_III);
                effectCap.resetCollidedTick();
            } else if (amplifier == 3 && collidedTick >= LEVEL_IV) {
                player.addPotionEffect(EFFECT_IV);
                effectCap.resetCollidedTick();
            }
        }
    }
}
