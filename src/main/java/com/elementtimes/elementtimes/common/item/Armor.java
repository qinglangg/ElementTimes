package com.elementtimes.elementtimes.common.item;

import com.elementtimes.elementcore.api.utils.ItemUtils;
import com.elementtimes.elementcore.api.utils.TagUtils;
import com.elementtimes.elementtimes.common.init.Groups;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.LazyLoadBase;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;

import javax.annotation.Nonnull;
import java.util.function.Supplier;



public class Armor extends ArmorItem {

    public static IArmorMaterial ELEMENT = new Material("element", 10000, new int[] {30,60,80,30}, 30, 50, () -> Ingredient.EMPTY);

    public static IArmorMaterial PLATINUM = new Material("platinum", 2000, new int[] {3,6,8,3}, 15, 16, () -> Ingredient.fromTag(TagUtils.forgeItem("ingots", "platinum")));

    public Armor(IArmorMaterial materialIn, EquipmentSlotType slot) {
        super(materialIn, slot, new Properties().group(Groups.Main));
    }

    @Override
    public void fillItemGroup(@Nonnull ItemGroup group, @Nonnull NonNullList<ItemStack> items) {
        super.fillItemGroup(group, items);
        if (getArmorMaterial() == ELEMENT) {
            items.stream().filter(itemStack -> itemStack.getItem() == this).forEach(ItemUtils::addAllEnchantments);
        }
    }

    protected static class Material implements IArmorMaterial {
        private final int mFactory;
        private final int[] mDamageDeduction;
        private final int mEnchantability;
        private final float mToughness;
        private final LazyLoadBase<Ingredient> mRepairMaterial;
        private final String mName;

        public Material(String name, int factory, int[] damageDeduction, int enchantability, float toughness, Supplier<Ingredient> repairMaterial) {
            mFactory = factory;
            mDamageDeduction = damageDeduction;
            mEnchantability = enchantability;
            mToughness = toughness;
            mRepairMaterial = new LazyLoadBase<>(repairMaterial);
            mName = name;
        }

        @Override
        public int getDurability(@Nonnull EquipmentSlotType slotIn) {
            switch (slotIn) {
                case MAINHAND:
                case FEET:
                    return 13 * mFactory;
                case OFFHAND:
                case LEGS:
                    return 15 * mFactory;
                case HEAD:
                    return 11 * mFactory;
                case CHEST:
                    return 16 * mFactory;
                default: return 0;
            }
        }

        @Override
        public int getDamageReductionAmount(@Nonnull EquipmentSlotType slotIn) {
            return mDamageDeduction[slotIn.getIndex()];
        }

        @Override
        public int getEnchantability() {
            return mEnchantability;
        }

        @Override
        @Nonnull
        public SoundEvent getSoundEvent() {
            return SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND;
        }

        @Override
        @Nonnull
        public Ingredient getRepairMaterial() {
            return mRepairMaterial.getValue();
        }

        @Override
        @Nonnull
        public String getName() {
            return mName;
        }

        @Override
        public float getToughness() {
            return mToughness;
        }
    }
}
