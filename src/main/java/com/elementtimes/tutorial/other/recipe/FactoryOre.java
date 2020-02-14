package com.elementtimes.tutorial.other.recipe;

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
import net.minecraftforge.oredict.OreIngredient;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import javax.annotation.Nonnull;

import static com.elementtimes.tutorial.common.item.Hammer.TAG_DAMAGE;
import static com.elementtimes.tutorial.common.item.Hammer.TAG_REMOVE;

/**
 * 原本 @Ore 注解的 json 实现
 * @author luqin2007
 */
public class FactoryOre implements IRecipeFactory {

    @Override
    public IRecipe parse(JsonContext jsonContext, JsonObject jsonObject) {
        String input = jsonObject.get("input").getAsString();
        String group = jsonObject.get("group").getAsString();
        int damage = jsonObject.get("damage").getAsInt();
        JsonObject outputObj = jsonObject.get("output").getAsJsonObject();
        String output = outputObj.get("item").getAsString();
        int count = outputObj.get("count").getAsInt();
        ItemStack outputItem;
        if (output.contains(":")) {
            Item item = Item.getByNameOrId(output);
            if (item == null) {
                return null;
            }
            outputItem = new ItemStack(item, count);
        } else {
            ItemStack[] stacks = new OreIngredient(output).getMatchingStacks();
            if (stacks.length > 0) {
                outputItem = stacks[0].copy();
            } else {
                return null;
            }
        }
        Ingredient inputItem;
        if (input.contains(":")) {
            Item item = Item.getByNameOrId(input);
            if (item == null) {
                return null;
            }
            inputItem = Ingredient.fromStacks(new ItemStack(item));
        } else {
            inputItem = new OreIngredient(input);
        }
        NonNullList<Ingredient> inputIngredients = NonNullList.create();
        inputIngredients.add(inputItem);
        outputItem.setCount(count);
        return getRecipeHammer(inputIngredients, outputItem, damage, group);
    }

    private ShapelessOreRecipe getRecipeHammer(NonNullList<Ingredient> input, ItemStack output, int damage, String group) {
        input.add(new DamageIngredient(new Item[] {
                ElementtimesItems.smallHammer, ElementtimesItems.mediumHammer, ElementtimesItems.bigHammer}, damage));
        return new ShapelessOreRecipe(new ResourceLocation(group), input, output) {
            @Nonnull
            @Override
            public NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv) {
                NonNullList<ItemStack> ret = NonNullList.withSize(inv.getSizeInventory(), ItemStack.EMPTY);
                for (int i = 0; i < ret.size(); i++) {
                    ItemStack stack = inv.getStackInSlot(i);
                    if (stack.getItem() == ElementtimesItems.smallHammer
                            || stack.getItem() == ElementtimesItems.mediumHammer
                            || stack.getItem() == ElementtimesItems.bigHammer) {
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
