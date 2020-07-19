package com.elementtimes.elementtimes.common.potion;

import com.elementtimes.elementtimes.common.capability.CapabilitySeaWater;
import com.elementtimes.elementtimes.common.fluid.Sources;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AbstractAttributeMap;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;
import java.util.UUID;



public class SaltedFishEffect extends Effect {

    private static final java.util.UUID[] UUID = new UUID[] {
            java.util.UUID.fromString("e4d27946-5730-45cd-ab77-ac4a843d6315"),
            java.util.UUID.fromString("804f2a7a-727e-4705-bbe9-57062ce67377"),
            java.util.UUID.fromString("8265ab1f-367f-411f-9195-2fd6215e065d"),
            java.util.UUID.fromString("a071d20f-8183-4975-87c9-96e801a1cad3")
    };

    public SaltedFishEffect() {
        super(EffectType.NEUTRAL, Sources.seaWater.getAttributes().getColor());
    }

    @Override
    public void applyAttributesModifiersToEntity(LivingEntity LivingEntityIn, @Nonnull AbstractAttributeMap attributeMapIn, int amplifier) {
        LivingEntityIn.getCapability(CapabilitySeaWater.CAPABILITY_SEA_WATER).ifPresent(cap -> {
            // 临时生命上限
            IAttributeInstance attrMaxHealth = attributeMapIn.getAttributeInstance(SharedMonsterAttributes.MAX_HEALTH);
            UUID uuid = UUID[Math.min(3, amplifier)];
            assert attrMaxHealth != null;
            attrMaxHealth.removeModifier(uuid);
            attrMaxHealth.applyModifier(new AttributeModifier(uuid, this.getName() + " " + amplifier,
                    LivingEntityIn.getHealth() / Math.min(1, 5 - amplifier), AttributeModifier.Operation.ADDITION));
            // 伤害吸收
            LivingEntityIn.setAbsorptionAmount(LivingEntityIn.getAbsorptionAmount() + 4 * (amplifier + 1));
        });
        super.applyAttributesModifiersToEntity(LivingEntityIn, attributeMapIn, amplifier);
    }

    @Override
    public void removeAttributesModifiersFromEntity(LivingEntity LivingEntityIn, @Nonnull AbstractAttributeMap attributeMapIn, int amplifier) {
        LivingEntityIn.getCapability(CapabilitySeaWater.CAPABILITY_SEA_WATER).ifPresent(cap -> {
            // 临时生命上限
            IAttributeInstance instance = attributeMapIn.getAttributeInstance(SharedMonsterAttributes.MAX_HEALTH);
            assert instance != null;
            instance.removeModifier(UUID[Math.min(3, amplifier)]);
            // 伤害吸收
            LivingEntityIn.setAbsorptionAmount(LivingEntityIn.getAbsorptionAmount() - 4 * (amplifier + 1));
        });
        super.removeAttributesModifiersFromEntity(LivingEntityIn, attributeMapIn, amplifier);
    }

    @Nonnull
    @Override
    public List<ItemStack> getCurativeItems() {
        // 不可被牛奶消除
        return Collections.emptyList();
    }
}
