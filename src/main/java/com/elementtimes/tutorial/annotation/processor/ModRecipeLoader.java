package com.elementtimes.tutorial.annotation.processor;

import com.elementtimes.tutorial.Elementtimes;
import com.elementtimes.tutorial.annotation.ModRecipe;
import com.elementtimes.tutorial.annotation.ingredient.DamageIngredient;
import com.elementtimes.tutorial.annotation.util.ReflectUtil;
import com.elementtimes.tutorial.common.init.ElementtimesItems;
import com.elementtimes.tutorial.util.RecipeUtil;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.*;
import java.util.function.Supplier;

/**
 * 加载合成表
 * 处理所有 ModRecipe 注解的成员
 * 目前只有一个可用 ModRecipe.Ore
 *
 * @author luqin2007
 */
class ModRecipeLoader {

    /**
     * 获取所有合成表
     */
    static void getRecipes(Map<Class, ArrayList<AnnotatedElement>> elements, List<Supplier<IRecipe>> into) {
        elements.get(ModRecipe.class).forEach(element -> {
            Object obj = ReflectUtil.getFromAnnotated(element, null).orElse(null);
            if (obj == null) {
                return;
            }
            for (Annotation annotation : element.getAnnotations()) {
                if (annotation instanceof ModRecipe.Ore) {
                    addOreRecipe(obj, (ModRecipe.Ore) annotation, into);
                }
            }
        });
    }

    private static void addOreRecipe(Object ore, final ModRecipe.Ore info, List<Supplier<IRecipe>> into) {
        // 矿物锤合成
        if ("logWood".equals(ore)) {
            // 对原木进行特殊处理
            Map<ItemStack, ItemStack> itemStacks = new HashMap<>();
            RecipeUtil.collectOneBlockCraftingResult("logWood", itemStacks);
            for (Map.Entry<ItemStack, ItemStack> entry: itemStacks.entrySet()) {
                into.add(() -> {
                    NonNullList<Ingredient> input = NonNullList.create();
                    ItemStack output = entry.getValue().copy();
                    input.add(Ingredient.fromStacks(entry.getKey()));
                    return getRecipeHammer(input, output, info.damage(), info.value()
                            + "_" + output.getItem().getRegistryName().getResourceDomain()
                            + "_" + output.getItem().getRegistryName().getResourcePath()
                            + "_" + output.getItemDamage());
                });
            }
        } else {
            into.add(() -> {
                NonNullList<Ingredient> input = NonNullList.create();
                ItemStack[] matchingStacks = getIngredient(info.output()).getMatchingStacks();
                if (matchingStacks.length == 0) {
                    return null;
                }
                ItemStack output = matchingStacks[0].copy();
                input.add(getIngredient(ore));
                output.setCount(info.dustCount());
                return getRecipeHammer(input, output, info.damage(), info.value());
            });
        }
    }

    private static ShapelessOreRecipe getRecipeHammer(NonNullList<Ingredient> input, ItemStack output, int damage, String name) {
        input.add(new DamageIngredient(new Item[] {ElementtimesItems.smallHammer, ElementtimesItems.bigHammer}, damage));
        ShapelessOreRecipe recipe = new ShapelessOreRecipe(new ResourceLocation(Elementtimes.MODID, "recipe"), input, output){
            @Override
            public NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv) {
                NonNullList<ItemStack> ret = NonNullList.withSize(inv.getSizeInventory(), ItemStack.EMPTY);
                for (int i = 0; i < ret.size(); i++) {
                    ItemStack stack = inv.getStackInSlot(i);
                    if (stack.getItem() == ElementtimesItems.smallHammer || stack.getItem() == ElementtimesItems.bigHammer) {
                        stack.getOrCreateSubCompound(Elementtimes.MODID + "_bind").setInteger("damage", damage);
                    }
                    ret.set(i, ForgeHooks.getContainerItem(stack));
                }
                return ret;
            }
        };
        recipe.setRegistryName(Elementtimes.MODID, name);
        return recipe;
    }

    private static Ingredient getIngredient(Object obj) {
        try {
            if (obj instanceof String) {
                String oreOrName = (String) obj;
                // [ore] 开头 强制矿辞
                if (oreOrName.startsWith("[ore]")) {
                    oreOrName = oreOrName.substring(5);
                    return CraftingHelper.getIngredient(oreOrName);
                }
                // [id] 开头 强制 RegistryName
                if (oreOrName.startsWith("[id]")) {
                    oreOrName = oreOrName.substring(4);
                    return Ingredient.fromStacks(getFromItemName(oreOrName));
                }
                Ingredient ingredient = CraftingHelper.getIngredient(oreOrName);
                ItemStack[] matchingStacks = ingredient.getMatchingStacks();
                if (matchingStacks.length == 0 || Arrays.stream(matchingStacks).allMatch(ItemStack::isEmpty)) {
                    return Ingredient.fromStacks(getFromItemName(oreOrName));
                }
                return ingredient;
            } else {
                return CraftingHelper.getIngredient(obj);
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return Ingredient.EMPTY;
        }
    }

    private static ItemStack getFromItemName(String name) {
        String[] split = name.split(":");
        Item item;
        int damage = 0;
        if (split.length == 1) {
            item = Item.getByNameOrId("minecraft:" + name);
        } else if (split.length == 2) {
            item = Item.getByNameOrId(name);
        } else {
            try {
                item = Item.getByNameOrId(split[0] + ":" + split[1]);
                damage = Integer.valueOf(split[2]);
            } catch (Exception e) {
                item = Item.getByNameOrId(name);
            }
        }
        ItemStack stack = item == null ? new ItemStack(Items.AIR) : new ItemStack(item);
        stack.setItemDamage(damage);
        return stack;
    }
}
