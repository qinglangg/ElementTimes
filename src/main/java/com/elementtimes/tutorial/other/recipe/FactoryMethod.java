package com.elementtimes.tutorial.other.recipe;

import com.google.gson.JsonObject;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.IRecipeFactory;
import net.minecraftforge.common.crafting.JsonContext;

import javax.annotation.Nonnull;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * 返回值从方法中获取
 * 参数：
 *  override: 相当于原本的 type。
 *      Factory 会以该 type 创建 IRecipe 对象
 *      因此，该 json 应当包含 parent 对应 type 的所有元素
 *      parent 不可为 elementtimes:method，会导致递归调用
 *  result {
 *      class: 方法所在类
 *      method: 物品创建方法。应当返回一个 ItemStack 对象
 *          必须是静态方法。优先选择无参方法
 *          也可以接收一个 ItemStack 类型的对象，为原本返回的值（getRecipeOutput）
 *  }
 * @author luqin2007
 */
public class FactoryMethod implements IRecipeFactory {

    @Override
    public IRecipe parse(JsonContext context, JsonObject json) {
        String type = json.get("override").getAsString();
        json.addProperty("type", type);
        IRecipe recipe = CraftingHelper.getRecipe(json, context);
        return new RecipeWrapper(recipe) {
            private ItemStack mStack = null;

            @Nonnull
            @Override
            public ItemStack getCraftingResult(@Nonnull InventoryCrafting inv) {
                if (mStack == null) {
                    mStack = getResult(json, recipe);
                }
                return mStack.copy();
            }

            @Nonnull
            @Override
            public ItemStack getRecipeOutput() {
                if (mStack == null) {
                    mStack = getResult(json, recipe);
                }
                return mStack;
            }
        };
    }

    private ItemStack getResult(JsonObject json, IRecipe recipe) {
        JsonObject obj = json.getAsJsonObject("result");
        String className = obj.get("class").getAsString();
        String methodName = obj.get("method").getAsString();
        try {
            Class clazz = Thread.currentThread().getContextClassLoader().loadClass(className);
            try {
                ItemStack stack = getStack(clazz.getDeclaredMethod(methodName));
                if (!stack.isEmpty()) {
                    return stack;
                }
            } catch (NoSuchMethodException ignored) { }
            try {
                ItemStack stack = getStack(clazz.getDeclaredMethod(methodName, ItemStack.class), recipe.getRecipeOutput());
                if (!stack.isEmpty()) {
                    return stack;
                }
            } catch (NoSuchMethodException ignored) { }
        } catch (ClassNotFoundException e) { }
        return ItemStack.EMPTY;
    }

    private ItemStack getStack(Method method, Object... params) {
        try {
            if (Modifier.isStatic(method.getModifiers())) {
                if (!method.isAccessible()) {
                    method.setAccessible(true);
                }
                Object o = method.invoke(null, params);
                if (o instanceof ItemStack) {
                    return (ItemStack) o;
                }
            }
        } catch (IllegalAccessException | InvocationTargetException ignored) { }
        return ItemStack.EMPTY;
    }
}
