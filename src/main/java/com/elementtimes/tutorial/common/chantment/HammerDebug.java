package com.elementtimes.tutorial.common.chantment;

import com.elementtimes.tutorial.common.init.ElementtimesItems;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraftforge.common.util.EnumHelper;

public class HammerDebug extends Enchantment {

    private static EnumEnchantmentType mType = EnumHelper.addEnchantmentType("hammer",
            input -> input == ElementtimesItems.bigHammer || input == ElementtimesItems.smallHammer);

    public HammerDebug() {
        super(Rarity.COMMON, mType, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND, EntityEquipmentSlot.OFFHAND});
    }
}
