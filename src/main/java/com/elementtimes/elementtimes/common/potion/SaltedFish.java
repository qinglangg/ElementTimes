package com.elementtimes.elementtimes.common.potion;

import com.elementtimes.elementtimes.common.capability.CapabilitySeaWater;
import com.elementtimes.elementtimes.common.init.Magic;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.potion.Potion;

/**
 * TODO icon 5, 2
 * 咸鱼 效果
 * 效果说明：
 *  削减 90% XZ 方向速度，削减 100% Y 轴速度
 *  恢复所有生命
 *  身为一条咸鱼，你几乎是不死的
 *      获得防火，抗性提升5（免伤），饱和，生命恢复，临时生命上限等效果
 *  身为一条咸鱼，你只想躺着
 *      无法跳跃，速度削减 90%，挖掘疲劳，失明
 *  身为一条咸鱼，你以盐为生命
 *      在水中，你将收到伤害
 *  当效果结束后，增加 0.2*咸鱼持续时间的生命恢复效果，等级为咸鱼等级
 *  不可被牛奶消除

 */
public class SaltedFish extends Potion {

    private static final int LEVEL_I_TICK = 100;
    private static final int LEVEL_II_TICK = 500;
    private static final int LEVEL_III_TICK = 1000;
    private static final int LEVEL_IV_TICK = 3000;
    private static final int LEVEL_I_COUNT = 1;
    private static final int LEVEL_II_COUNT = 8;
    private static final int LEVEL_III_COUNT = 32;
    private static final int LEVEL_IV_COUNT = 128;

    private static final EffectInstance EFFECT_I = new EffectInstance(Magic.saltedFishEffect, LEVEL_I_TICK, 1);
    private static final EffectInstance EFFECT_II = new EffectInstance(Magic.saltedFishEffect, LEVEL_II_TICK, 2);
    private static final EffectInstance EFFECT_III = new EffectInstance(Magic.saltedFishEffect, LEVEL_III_TICK, 3);
    private static final EffectInstance EFFECT_IV = new EffectInstance(Magic.saltedFishEffect, LEVEL_IV_TICK, 4);

    public SaltedFish() {
        super("saltedfish", EFFECT_I, EFFECT_II, EFFECT_III, EFFECT_IV);
    }

    /**
     * 为玩家增加 咸鱼 效果
     * @param player 玩家
     */
    public static void effectOn(PlayerEntity player) {
        player.getCapability(CapabilitySeaWater.CAPABILITY_SEA_WATER).filter(cap -> cap.getSalt() > 0).ifPresent(cap -> {
            int salt = cap.getSalt();
            if (salt >= LEVEL_I_COUNT) {
                if (salt >= LEVEL_IV_COUNT) {
                    int tick = LEVEL_IV_TICK + salt;
                    player.addPotionEffect(EFFECT_IV);
                    effectBeneficial(player, tick, 4);
                } else if (salt >= LEVEL_III_COUNT) {
                    int tick = LEVEL_III_TICK + salt;
                    player.addPotionEffect(EFFECT_III);
                    effectBeneficial(player, tick, 3);
                } else if (salt >= LEVEL_II_COUNT) {
                    int tick = LEVEL_II_TICK + salt;
                    player.addPotionEffect(EFFECT_II);
                    effectBeneficial(player, tick, 2);
                } else {
                    int tick = LEVEL_I_TICK + salt;
                    player.addPotionEffect(EFFECT_I);
                    effectBeneficial(player, tick, 1);
                }
            }
            cap.setSalt(0);
        });
    }

    private static void effectBeneficial(PlayerEntity player, int durationIn, int amplifier) {
        // 本来想除 100，这里除 128 使用移位比较快
        durationIn += durationIn >> 7;
        // 生命恢复
        player.addPotionEffect(new EffectInstance(Effects.REGENERATION, durationIn, amplifier));
        // 饱和
        player.addPotionEffect(new EffectInstance(Effects.SATURATION, durationIn, amplifier));
        // 防火
        player.addPotionEffect(new EffectInstance(Effects.FIRE_RESISTANCE, durationIn, amplifier));
        // 免伤
        player.addPotionEffect(new EffectInstance(Effects.RESISTANCE, durationIn, 5));
        // 挖掘疲劳
        player.addPotionEffect(new EffectInstance(Effects.MINING_FATIGUE, durationIn, amplifier));
        // 失明
        player.addPotionEffect(new EffectInstance(Effects.BLINDNESS));
        // 恢复所有生命
        player.setHealth(player.getMaxHealth());
    }
}