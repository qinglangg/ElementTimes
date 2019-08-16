package com.elementtimes.tutorial.other;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;

/**
 * 用于对原版创造模式物品栏的替换
 * @author luqin2007
 */
public class MiscTabWrapper extends CreativeTabs {

    private final CreativeTabs misc;
    private final List<Predicate<ItemStack>> itemPredicates = new LinkedList<>();
    private final Predicate<ItemStack> removeItem = itemStack -> {
        for (Predicate<ItemStack> predicate : itemPredicates) {
            if (!predicate.test(itemStack)) {
                return true;
            }
        }
        return false;
    };

    private static void setStaticFinalField(Field field, Object newValue) throws NoSuchFieldException, IllegalAccessException {
        final Field modifiersField = field.getClass().getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        final int modifiers = field.getModifiers();
        modifiersField.setInt(field, modifiers & ~Modifier.FINAL);
        field.set(null, newValue);
        modifiersField.setInt(field, modifiers);
    }

    public static MiscTabWrapper apply() {
        CreativeTabs misc = CreativeTabs.MISC;
        MiscTabWrapper newTab = new MiscTabWrapper(misc.getTabIndex(), "misc", misc);
        CREATIVE_TAB_ARRAY[misc.getTabIndex()] = newTab;
        for (Field field : CreativeTabs.class.getDeclaredFields()) {
            try {
                Object o = field.get(null);
                if (o == misc) {
                    field.setAccessible(true);
                    setStaticFinalField(field, newTab);
                    break;
                }
            } catch (IllegalAccessException | NoSuchFieldException e) {
                e.printStackTrace();
            }
        }
        return newTab;
    }

    public MiscTabWrapper(int index, String label, CreativeTabs misc) {
        super(index, label);
        this.misc = misc;
    }

    public void addPredicate(Predicate<ItemStack> predicate) {
        itemPredicates.add(predicate);
    }

    // 重写方法 ==================================================================

    @SideOnly(Side.CLIENT)
    @Override
    public void displayAllRelevantItems(NonNullList<ItemStack> items) {
        misc.displayAllRelevantItems(items);
        items.removeIf(removeItem);
    }

    // MC 原版方法 ===============================================================

    @Override
    @SideOnly(Side.CLIENT)
    public int getTabIndex() {
        return misc.getTabIndex();
    }

    @Override
    public CreativeTabs setBackgroundImageName(String texture) {
        misc.setBackgroundImageName(texture);
        return this;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public String getTabLabel() {
        return misc.getTabLabel();
    }

    @SideOnly(Side.CLIENT)
    @Override
    public String getTranslatedTabLabel() {
        return misc.getTranslatedTabLabel();
    }

    @SideOnly(Side.CLIENT)
    @Override
    public ItemStack getIconItemStack() {
        return misc.getIconItemStack();
    }

    @SideOnly(Side.CLIENT)
    @Override
    public ItemStack getTabIconItem() {
        return misc.getTabIconItem();
    }

    @SideOnly(Side.CLIENT)
    @Override
    public String getBackgroundImageName() {
        return misc.getBackgroundImageName();
    }

    @SideOnly(Side.CLIENT)
    @Override
    public boolean drawInForegroundOfTab() {
        return misc.drawInForegroundOfTab();
    }

    @Override
    public CreativeTabs setNoTitle() {
        misc.setNoTitle();
        return this;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public boolean shouldHidePlayerInventory() {
        return misc.shouldHidePlayerInventory();
    }

    @Override
    public CreativeTabs setNoScrollbar() {
        misc.setNoScrollbar();
        return this;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public int getTabColumn() {
        return misc.getTabColumn();
    }

    @SideOnly(Side.CLIENT)
    @Override
    public boolean isTabInFirstRow() {
        return misc.isTabInFirstRow();
    }

    @SideOnly(Side.CLIENT)
    @Override
    public boolean isAlignedRight() {
        return misc.isAlignedRight();
    }

    @Override
    public EnumEnchantmentType[] getRelevantEnchantmentTypes() {
        return misc.getRelevantEnchantmentTypes();
    }

    @Override
    public CreativeTabs setRelevantEnchantmentTypes(EnumEnchantmentType... types) {
        misc.setRelevantEnchantmentTypes(types);
        return this;
    }

    @Override
    public boolean hasRelevantEnchantmentType(@Nullable EnumEnchantmentType enchantmentType) {
        return misc.hasRelevantEnchantmentType(enchantmentType);
    }

    @Override
    public int getTabPage() {
        return misc.getTabPage();
    }

    @Override
    public boolean hasSearchBar() {
        return misc.hasSearchBar();
    }

    @Override
    public int getSearchbarWidth() {
        return misc.getSearchbarWidth();
    }

    @SideOnly(Side.CLIENT)
    @Override
    public net.minecraft.util.ResourceLocation getBackgroundImage() {
        return misc.getBackgroundImage();
    }
}
