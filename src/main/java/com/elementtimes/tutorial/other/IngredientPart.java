package com.elementtimes.tutorial.other;

import com.elementtimes.tutorial.interfaces.function.Function3;
import com.elementtimes.tutorial.interfaces.function.Function5;
import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.oredict.OreIngredient;

import java.util.Arrays;

/**
 * 合成表匹配
 * @author luqin2007
 */
public class IngredientPart<T> {
    
    private static String SYMBOL_ITEM_ALL = "[all]";
    private static String SYMBOL_ITEM_ORE = "[ore]";
    private static String SYMBOL_ITEM_ID = "[id]";

    Function5.Match<T> matcher;

    Function3.Stack<T> getter;

    IngredientPart(Function5.Match<T> matcher, Function3.Stack<T> getter) {
        this.getter = getter;
        this.matcher = matcher;
    }

    private IngredientPart() {}

    public static IngredientPart<ItemStack> forItem(Ingredient ingredient, int count) {
        Function5.Match<ItemStack> match = (recipe, slot, inputItems, inputFluids, input) -> ingredient.apply(input);
        Function3.Stack<ItemStack> get = (recipe, slot, input) -> {
            if (input != null && !input.isEmpty()) {
                return ItemHandlerHelper.copyStackWithSize(input, count);
            } else {
                ItemStack[] stacks = ingredient.getMatchingStacks();
                if (stacks.length == 0) {
                    return ItemStack.EMPTY;
                } else {
                    return ItemHandlerHelper.copyStackWithSize(stacks[0], count);
                }
            }
        };
        return new IngredientPart<>(match, get);
    }

    public static IngredientPart<ItemStack> forItem(ItemStack itemStack) {
        Function5.Match<ItemStack> match = (recipe, slot, inputItems, inputFluids, input) -> {
            if (itemStack.isItemEqual(input)) {
                return itemStack.getCount() <= input.getCount();
            }
            return false;
        };
        Function3.Stack<ItemStack> get = (recipe, slot, input) -> itemStack.copy();
        return new IngredientPart<>(match, get);
    }

    public static IngredientPart<ItemStack> forItem(Item item, int count) {
        Function5.Match<ItemStack> match = (recipe, slot, inputItems, inputFluids, input) -> {
            if (input != null && item == input.getItem()) {
                return count <= input.getCount();
            }
            return false;
        };
        Function3.Stack<ItemStack> get = (recipe, slot, input) -> new ItemStack(item, count);
        return new IngredientPart<>(match, get);
    }

    public static IngredientPart<ItemStack> forItem(Block block, int count) {
        return forItem(Item.getItemFromBlock(block), count);
    }

    public static IngredientPart<ItemStack> forItem(IngredientPart<ItemStack>... ingredientParts) {
        return new IngredientPart<ItemStack>() {
            private final IngredientPart<ItemStack>[] contains = ingredientParts;

            {
                matcher = (recipe, slot, inputItems, inputFluids, input) -> {
                    for (IngredientPart<ItemStack> part : contains) {
                        if (!part.matcher.apply(recipe, slot, inputItems, inputFluids, input)) {
                            return false;
                        }
                    }
                    return true;
                };

                getter = (recipe, slot, input) -> {
                    for (IngredientPart<ItemStack> part : contains) {
                        ItemStack stack = part.getter.apply(recipe, slot, input);
                        if (!stack.isEmpty()) {
                            return stack;
                        }
                    }
                    return ItemStack.EMPTY;
                };
            }
        };
    }
    
    public static IngredientPart<ItemStack> forItem(String nameOrOreName, int count) {
        if (nameOrOreName.startsWith(SYMBOL_ITEM_ID)) {
            String name = nameOrOreName.substring(4);
            Item item = Item.getByNameOrId(name);
            return IngredientPart.forItem(item, count);
        } else if (nameOrOreName.startsWith(SYMBOL_ITEM_ORE)) {
            String name = nameOrOreName.substring(5);
            Ingredient ingredient = new OreIngredient(name);
            return IngredientPart.forItem(ingredient, count);
        } else if (nameOrOreName.startsWith(SYMBOL_ITEM_ALL)) {
            String name = nameOrOreName.substring(5);
            Item item = Item.getByNameOrId(name);
            Ingredient ingredientOre = new OreIngredient(name);
            Ingredient ingredientItem = Ingredient.fromItem(item == null ? Items.AIR : item);
            return IngredientPart.forItem(Ingredient.merge(Arrays.asList(ingredientItem, ingredientOre)), count);
        } else {
            Item item = Item.getByNameOrId(nameOrOreName);
            if (item == null || item == Items.AIR) {
                return IngredientPart.forItem(new OreIngredient(nameOrOreName), count);
            } else {
                return IngredientPart.forItem(item, count);
            }
        }
    }
    
    public static IngredientPart<FluidStack> forFluid(FluidStack fluidStack) {
        Function5.Match<FluidStack> match = (recipe, slot, inputItems, inputFluids, input) -> input.containsFluid(fluidStack);
        Function3.Stack<FluidStack> get = (recipe, slot, input) -> fluidStack.copy();
        return new IngredientPart<>(match, get);
    }

    public static IngredientPart<FluidStack> forFluid(Fluid fluid, int amount) {
        Function5.Match<FluidStack> match = (recipe, slot, inputItems, inputFluids, input) -> fluid == input.getFluid() && amount <= input.amount;
        Function3.Stack<FluidStack> get = (recipe, slot, input) -> new FluidStack(fluid, amount);
        return new IngredientPart<>(match, get);
    }

    public static IngredientPart<ItemStack> EMPTY_ITEM = new IngredientPart<>(
            (recipe, slot, inputItems, inputFluids, input) -> false,
            (recipe, slot, input) -> ItemStack.EMPTY);

    public static IngredientPart<FluidStack> EMPTY_FLUID = new IngredientPart<>(
            (recipe, slot, inputItems, inputFluids, input) -> false,
            (recipe, slot, input) -> null);
}
