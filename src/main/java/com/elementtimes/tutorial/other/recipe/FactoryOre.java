package com.elementtimes.tutorial.other.recipe;

import com.elementtimes.elementcore.api.common.ECUtils;
import com.elementtimes.elementcore.api.template.ingredient.DamageIngredient;
import com.elementtimes.tutorial.ElementTimes;
import com.elementtimes.tutorial.common.init.ElementtimesItems;
import com.google.gson.JsonObject;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.crafting.IRecipeFactory;
import net.minecraftforge.common.crafting.JsonContext;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import javax.annotation.Nonnull;
import java.util.Map;

import static com.elementtimes.tutorial.common.item.Hammer.TAG_DAMAGE;
import static com.elementtimes.tutorial.common.item.Hammer.TAG_REMOVE;

/**
 * 原本 @Ore 注解的 json 实现
 * @author luqin2007
 */
public class FactoryOre implements IRecipeFactory {
    private static final String LOG_ORE_NAME = "logWood";

    @Override
    public IRecipe parse(JsonContext jsonContext, JsonObject jsonObject) {
        String input = jsonObject.get("input").getAsString();
        String group = jsonObject.get("group").getAsString();
        int damage = jsonObject.get("damage").getAsInt();
        JsonObject outputObj = jsonObject.get("output").getAsJsonObject();
        String output = outputObj.get("item").getAsString();
        int count = outputObj.get("count").getAsInt();

        if (LOG_ORE_NAME.equals(input)) {
            // 对原木进行特殊处理
            Map<ItemStack, ItemStack> itemStacks = ECUtils.recipe.collectOneBlockCraftingResult(null, "logWood");
            for (Map.Entry<ItemStack, ItemStack> entry : itemStacks.entrySet()) {
                ItemStack outputItem = entry.getValue().copy();
                outputItem.setCount(count);
                NonNullList<Ingredient> inputIngredients = NonNullList.create();
                inputIngredients.add(Ingredient.fromStacks(entry.getKey()));
                return getRecipeHammer(inputIngredients, outputItem, damage, group);
            }
        } else {
            ItemStack[] matchingStacks = ECUtils.recipe.getIngredient(output).getMatchingStacks();
            if (matchingStacks.length == 0) {
                return null;
            }
            ItemStack outputItem = matchingStacks[0].copy();
            NonNullList<Ingredient> inputIngredients = NonNullList.create();
            inputIngredients.add(ECUtils.recipe.getIngredient(input));
            outputItem.setCount(count);
            return getRecipeHammer(inputIngredients, outputItem, damage, group);
        }
        return null;
    }

    private ShapelessOreRecipe getRecipeHammer(NonNullList<Ingredient> input, ItemStack output, int damage, String group) {
        input.add(new DamageIngredient(new Item[] {ElementtimesItems.smallHammer, ElementtimesItems.mediumHammer, ElementtimesItems.bigHammer}, damage));
        return new ShapelessOreRecipe(new ResourceLocation(group), input, output) {
            @Nonnull
            @Override
            public NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv) {
                NonNullList<ItemStack> ret = NonNullList.withSize(inv.getSizeInventory(), ItemStack.EMPTY);
                for (int i = 0; i < ret.size(); i++) {
                    ItemStack stack = inv.getStackInSlot(i);
                    if (stack.getItem() == ElementtimesItems.smallHammer || stack.getItem() == ElementtimesItems.bigHammer) {
                        boolean removeTag = !stack.hasTagCompound();
                        NBTTagCompound nbt = stack.getOrCreateSubCompound(ElementTimes.MODID + "_bind");
                        nbt.setInteger(TAG_DAMAGE, damage);
                        nbt.setBoolean(TAG_REMOVE, removeTag);
                    }
                    ret.set(i, ForgeHooks.getContainerItem(stack));
                }
                return ret;
            }
        };
    }
}
