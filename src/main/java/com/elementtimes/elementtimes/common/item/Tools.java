package com.elementtimes.elementtimes.common.item;

import com.elementtimes.elementcore.api.utils.ItemUtils;
import com.elementtimes.elementcore.api.utils.TagUtils;
import com.elementtimes.elementtimes.common.init.Groups;
import net.minecraft.item.*;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;

import javax.annotation.Nonnull;



public class Tools {

    public static final IItemTier ELEMENT = new IItemTier() {

        @Override
        public int getHarvestLevel() {
            return 4;
        }

        @Override
        public int getMaxUses() {
            return 1000000;
        }

        @Override
        public float getEfficiency() {
            return 50;
        }

        @Override
        public float getAttackDamage() {
            return 150;
        }

        @Override
        public int getEnchantability() {
            return 100;
        }

        @Override
        @Nonnull
        public Ingredient getRepairMaterial() {
            return Ingredient.EMPTY;
        }
    };

    public static final IItemTier PLATINUM = new IItemTier() {

        @Override
        public int getHarvestLevel() {
            return 4;
        }

        @Override
        public int getMaxUses() {
            return 8000;
        }

        @Override
        public float getEfficiency() {
            return 20;
        }

        @Override
        public float getAttackDamage() {
            return 100;
        }

        @Override
        public int getEnchantability() {
            return 30;
        }

        @Override
        @Nonnull
        public Ingredient getRepairMaterial() {
            return Ingredient.fromTag(TagUtils.forgeItem("ingots", "platinum"));
        }
    };

    public static class Shovel extends ShovelItem {

        public Shovel(IItemTier tier) {
            super(tier, 1.5f, -3, new Properties().group(Groups.Main));
        }

        @Override
        public void fillItemGroup(@Nonnull ItemGroup group, @Nonnull NonNullList<ItemStack> items) {
            super.fillItemGroup(group, items);
            if (getTier() == Tools.ELEMENT) {
                items.stream().filter(itemStack -> itemStack.getItem() == this).forEach(ItemUtils::addAllEnchantments);
            }
        }
    }

    public static class Shear extends ShearsItem {

        private final boolean enchanted;

        public Shear(int damage, boolean enchanted) {
            super(new Properties().group(Groups.Main).maxDamage(damage));
            this.enchanted = enchanted;
        }

        @Override
        public void fillItemGroup(@Nonnull ItemGroup group, @Nonnull NonNullList<ItemStack> items) {
            super.fillItemGroup(group, items);
            if (enchanted) {
                items.stream().filter(itemStack -> itemStack.getItem() == this).forEach(ItemUtils::addAllEnchantments);
            }
        }
    }

    public static class Hoe extends HoeItem {

        public Hoe(IItemTier tier) {
            super(tier, tier.getHarvestLevel() - 4, new Properties().group(Groups.Main));
        }

        @Override
        public void fillItemGroup(@Nonnull ItemGroup group, @Nonnull NonNullList<ItemStack> items) {
            super.fillItemGroup(group, items);
            if (getTier() == Tools.ELEMENT) {
                items.stream().filter(itemStack -> itemStack.getItem() == this).forEach(ItemUtils::addAllEnchantments);
            }
        }
    }

    public static class Sword extends SwordItem {

        public Sword(IItemTier tier) {
            super(tier, 3, -2.4f, new Properties().group(Groups.Main));
        }

        @Override
        public void fillItemGroup(@Nonnull ItemGroup group, @Nonnull NonNullList<ItemStack> items) {
            super.fillItemGroup(group, items);
            if (getTier() == Tools.ELEMENT) {
                items.stream().filter(itemStack -> itemStack.getItem() == this).forEach(ItemUtils::addAllEnchantments);
            }
        }
    }

    public static class Axe extends AxeItem {

        public Axe(IItemTier tier) {
            super(tier, 10, -3, new Properties().group(Groups.Main));
        }

        @Override
        public void fillItemGroup(@Nonnull ItemGroup group, @Nonnull NonNullList<ItemStack> items) {
            super.fillItemGroup(group, items);
            if (getTier() == Tools.ELEMENT) {
                items.stream().filter(itemStack -> itemStack.getItem() == this).forEach(ItemUtils::addAllEnchantments);
            }
        }
    }

    public static class Bow extends BowItem {

        boolean enchanted;

        public Bow(int damage, boolean enchanted) {
            super(new Properties().group(Groups.Main).maxDamage(damage));
            this.enchanted = enchanted;
        }

        @Override
        public void fillItemGroup(@Nonnull ItemGroup group, @Nonnull NonNullList<ItemStack> items) {
            super.fillItemGroup(group, items);
            if (enchanted) {
                items.stream().filter(itemStack -> itemStack.getItem() == this).forEach(ItemUtils::addAllEnchantments);
            }
        }
    }

    public static class Pickaxe extends PickaxeItem {

        public Pickaxe(IItemTier tier) {
            super(tier, 1, -2.8f, new Properties().group(Groups.Main));
        }

        @Override
        public void fillItemGroup(@Nonnull ItemGroup group, @Nonnull NonNullList<ItemStack> items) {
            super.fillItemGroup(group, items);
            if (getTier() == Tools.ELEMENT) {
                items.stream().filter(itemStack -> itemStack.getItem() == this).forEach(ItemUtils::addAllEnchantments);
            }
        }
    }

    public static class FishingRod extends FishingRodItem {

        boolean enchanted;

        public FishingRod(int damage, boolean enchanted) {
            super(new Properties().group(Groups.Main).maxDamage(damage));
            this.enchanted = enchanted;
        }

        @Override
        public void fillItemGroup(@Nonnull ItemGroup group, @Nonnull NonNullList<ItemStack> items) {
            super.fillItemGroup(group, items);
            if (enchanted) {
                items.stream().filter(itemStack -> itemStack.getItem() == this).forEach(ItemUtils::addAllEnchantments);
            }
        }
    }
}
