package com.elementtimes.tutorial.common.potion;

import com.elementtimes.tutorial.common.capability.CapabilitySeaWater;
import com.elementtimes.tutorial.common.init.ElementtimesFluids;
import com.elementtimes.tutorial.common.init.ElementtimesMagic;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AbstractAttributeMap;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
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
 * @author luqin2007
 */
public class SaltedFish extends Potion {

    private static final int LEVEL_I_COUNT = 1;
    private static final int LEVEL_II_COUNT = 8;
    private static final int LEVEL_III_COUNT = 32;
    private static final int LEVEL_IV_COUNT = 128;
    private static final int LEVEL_I_TICK = 100;
    private static final int LEVEL_II_TICK = 500;
    private static final int LEVEL_III_TICK = 1000;
    private static final int LEVEL_IV_TICK = 3000;
    private static final UUID[] UUID = new UUID[] {
            java.util.UUID.fromString("e4d27946-5730-45cd-ab77-ac4a843d6315"),
            java.util.UUID.fromString("804f2a7a-727e-4705-bbe9-57062ce67377"),
            java.util.UUID.fromString("8265ab1f-367f-411f-9195-2fd6215e065d"),
            java.util.UUID.fromString("a071d20f-8183-4975-87c9-96e801a1cad3")
    };

    public SaltedFish() {
        super(false, ElementtimesFluids.seawater.getColor());
        setIconIndex(5, 2);
    }

    @Override
    public boolean isInstant() {
        return super.isInstant();
    }

    @Override
    public void applyAttributesModifiersToEntity(EntityLivingBase entityLivingBaseIn, @Nonnull AbstractAttributeMap attributeMapIn, int amplifier) {
        CapabilitySeaWater.ISeaWater cap = entityLivingBaseIn.getCapability(CapabilitySeaWater.CAPABILITY_SEA_WATER, null);
        if (cap != null) {
            // 临时生命上限
            IAttributeInstance attrMaxHealth = attributeMapIn.getAttributeInstance(SharedMonsterAttributes.MAX_HEALTH);
            UUID uuid = UUID[Math.min(3, amplifier)];
            attrMaxHealth.removeModifier(uuid);
            attrMaxHealth.applyModifier(new AttributeModifier(uuid,
                    this.getName() + " " + amplifier,
                    entityLivingBaseIn.getHealth() / Math.min(1, 5 - amplifier), 0));
            // 伤害吸收
            entityLivingBaseIn.setAbsorptionAmount(entityLivingBaseIn.getAbsorptionAmount() + 4 * (amplifier + 1));
        }
        super.applyAttributesModifiersToEntity(entityLivingBaseIn, attributeMapIn, amplifier);
    }

    @Override
    public void removeAttributesModifiersFromEntity(EntityLivingBase entityLivingBaseIn, @Nonnull AbstractAttributeMap attributeMapIn, int amplifier) {
        CapabilitySeaWater.ISeaWater cap = entityLivingBaseIn.getCapability(CapabilitySeaWater.CAPABILITY_SEA_WATER, null);
        if (cap != null) {
            // 临时生命上限
            attributeMapIn.getAttributeInstance(SharedMonsterAttributes.MAX_HEALTH).removeModifier(UUID[Math.min(3, amplifier)]);
            // 伤害吸收
            entityLivingBaseIn.setAbsorptionAmount(entityLivingBaseIn.getAbsorptionAmount() - 4 * (amplifier + 1));
        }
        super.removeAttributesModifiersFromEntity(entityLivingBaseIn, attributeMapIn, amplifier);
    }

    @Nonnull
    @Override
    public List<ItemStack> getCurativeItems() {
        // 不可被牛奶消除
        return Collections.emptyList();
    }

    /**
     * 为玩家增加 咸鱼 效果
     * @param player 玩家
     */
    public static void effectOn(EntityPlayer player) {
        CapabilitySeaWater.ISeaWater cap = player.getCapability(CapabilitySeaWater.CAPABILITY_SEA_WATER, null);
        if (cap != null && cap.getSalt() > 0) {
            int salt = cap.getSalt();
            if (salt >= LEVEL_I_COUNT) {
                if (salt >= LEVEL_IV_COUNT) {
                    int tick = LEVEL_IV_TICK + salt;
                    player.addPotionEffect(new PotionEffect(ElementtimesMagic.saltedFish, tick, 4));
                    effectBeneficial(player, tick, 4);
                } else if (salt >= LEVEL_III_COUNT) {
                    int tick = LEVEL_III_TICK + salt;
                    player.addPotionEffect(new PotionEffect(ElementtimesMagic.saltedFish, tick, 3));
                    effectBeneficial(player, tick, 3);
                } else if (salt >= LEVEL_II_COUNT) {
                    int tick = LEVEL_II_TICK + salt;
                    player.addPotionEffect(new PotionEffect(ElementtimesMagic.saltedFish, tick, 2));
                    effectBeneficial(player, tick, 2);
                } else {
                    int tick = LEVEL_I_TICK + salt;
                    player.addPotionEffect(new PotionEffect(ElementtimesMagic.saltedFish, tick, 1));
                    effectBeneficial(player, tick, 1);
                }
            }
            cap.setSalt(0);
        }
    }

    private static void effectBeneficial(EntityPlayer player, int durationIn, int amplifier) {
        // 本来想除 100，这里除 128 使用移位比较快
        durationIn += durationIn >> 7;
        // 生命恢复
        player.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, durationIn, amplifier));
        // 饱和
        player.addPotionEffect(new PotionEffect(MobEffects.SATURATION, durationIn, amplifier));
        // 防火
        player.addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, durationIn, amplifier));
        // 免伤
        player.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, durationIn, 5));
        // 挖掘疲劳
        player.addPotionEffect(new PotionEffect(MobEffects.MINING_FATIGUE, durationIn, amplifier));
        // 失明
        player.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS));
        // 恢复所有生命
        player.setHealth(player.getMaxHealth());
    }
}