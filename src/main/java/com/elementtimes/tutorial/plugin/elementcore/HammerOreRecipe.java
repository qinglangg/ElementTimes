package com.elementtimes.tutorial.plugin.elementcore;

import com.elementtimes.elementcore.api.common.ECModContainer;
import com.elementtimes.elementcore.api.common.ECUtils;
import com.elementtimes.elementcore.api.common.LoaderHelper;
import com.elementtimes.elementcore.api.template.ingredient.DamageIngredient;
import com.elementtimes.tutorial.common.init.ElementtimesItems;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.fml.common.discovery.ASMDataTable;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import javax.annotation.Nonnull;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Map;
import java.util.Objects;

import static com.elementtimes.tutorial.common.item.Hammer.TAG_DAMAGE;
import static com.elementtimes.tutorial.common.item.Hammer.TAG_REMOVE;

/**
 * 锤子合成
 * 可标记 Item，Block，String(默认认为是 矿辞，id以 [id] 开头加以区分)
 * json 注册 我注册个锤子啊 头疼
 * @author luqin2007
 */
public class HammerOreRecipe {

    private static final String LOG_ORE_NAME = "logWood";

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    public @interface Ore {
        /**
         * name 用于注册
         */
        String value();

        /**
         * 消耗锤子的耐久
         */
        int damage() default 1;

        /**
         * 产物数量
         */
        int dustCount() default 3;

        /**
         * 产物 id
         */
        String output();
    }

    public static void parser(ASMDataTable.ASMData data, ECModContainer container) {
        // 矿物锤合成
        final Map<String, Object> annotationInfo = data.getAnnotationInfo();
        LoaderHelper.getOrLoadClass(container.elements, data.getClassName())
                .flatMap(clazz -> ECUtils.reflect.getField(clazz, data.getObjectName(), null, Object.class, container.logger))
                .ifPresent(ore -> {
            String value = (String) annotationInfo.get("value");
            int damage = (int) annotationInfo.getOrDefault("damage", 1);
            int dustCount = (int) annotationInfo.getOrDefault("dustCount", 3);
            String output = (String) annotationInfo.get("output");

            if (LOG_ORE_NAME.equals(ore)) {
                // 对原木进行特殊处理
                Map<ItemStack, ItemStack> itemStacks =
                        ECUtils.recipe.collectOneBlockCraftingResult(null, "logWood");
                for (Map.Entry<ItemStack, ItemStack> entry : itemStacks.entrySet()) {
                    container.elements.recipes.add(() -> {
                        ItemStack outputItem = entry.getValue().copy();
                        NonNullList<Ingredient> input = NonNullList.create();
                        input.add(Ingredient.fromStacks(entry.getKey()));
                        ShapelessOreRecipe recipe = getRecipeHammer(input, outputItem, damage, value
                                + "_" + Objects.requireNonNull(outputItem.getItem().getRegistryName()).getResourceDomain()
                                + "_" + outputItem.getItem().getRegistryName().getResourcePath()
                                + "_" + outputItem.getItemDamage(), container.id());
                        return new IRecipe[]{recipe};
                    });
                }
            } else {
                container.elements.recipes.add(() -> {
                    ItemStack[] matchingStacks = ECUtils.recipe.getIngredient(output).getMatchingStacks();
                    if (matchingStacks.length == 0) {
                        return null;
                    }
                    ItemStack outputItem = matchingStacks[0].copy();
                    NonNullList<Ingredient> input = NonNullList.create();
                    input.add(ECUtils.recipe.getIngredient(ore));
                    outputItem.setCount(dustCount);
                    ShapelessOreRecipe recipe = getRecipeHammer(input, outputItem, damage, value, container.id());
                    return new IRecipe[]{recipe};
                });
            }
        });
    }

    private static ShapelessOreRecipe getRecipeHammer(NonNullList<Ingredient> input, ItemStack output, int damage, String name, String modid) {
        input.add(new DamageIngredient(new Item[] {ElementtimesItems.smallHammer, ElementtimesItems.bigHammer}, damage));
        ShapelessOreRecipe recipe = new ShapelessOreRecipe(new ResourceLocation(modid, "recipe"), input, output) {
            @Nonnull
            @Override
            public NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv) {
                NonNullList<ItemStack> ret = NonNullList.withSize(inv.getSizeInventory(), ItemStack.EMPTY);
                for (int i = 0; i < ret.size(); i++) {
                    ItemStack stack = inv.getStackInSlot(i);
                    if (stack.getItem() == ElementtimesItems.smallHammer || stack.getItem() == ElementtimesItems.bigHammer) {
                        boolean removeTag = !stack.hasTagCompound();
                        NBTTagCompound nbt = stack.getOrCreateSubCompound(modid + "_bind");
                        nbt.setInteger(TAG_DAMAGE, damage);
                        nbt.setBoolean(TAG_REMOVE, removeTag);
                    }
                    ret.set(i, ForgeHooks.getContainerItem(stack));
                }
                return ret;
            }
        };
        recipe.setRegistryName(modid, name);
        return recipe;
    }
}
