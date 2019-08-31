package com.elementtimes.tutorial.other.machineRecipe;

import com.elementtimes.elementcore.api.utils.FluidUtils;
import com.elementtimes.tutorial.interfaces.function.Function4;
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
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * 合成表匹配
 * @author luqin2007
 */
@SuppressWarnings({"unused", "FieldCanBeLocal", "WeakerAccess", "unchecked"})
public class IngredientPart<T> {

    private static String SYMBOL_ITEM_ALL = "[all]";
    private static String SYMBOL_ITEM_ORE = "[ore]";
    private static String SYMBOL_ITEM_ID = "[id]";

    /**
     * 测试输入是否匹配
     * 用于机器检索合成表
     */
    public Function5.Match<T> matcher;

    /**
     * 根据输入获取实际物品及数量
     * 用于获取输入输出物品
     */
    public Function4.Stack<T> getter;

    /**
     * 获取所有可用值
     * 用于 Jei 兼容
     */
    public Supplier<List<T>> allViableValues;

    public IngredientPart(Function5.Match<T> matcher, Function4.Stack<T> getter, Supplier<List<T>> allViableValues) {
        this.getter = getter;
        this.matcher = matcher;
        this.allViableValues = allViableValues;
    }

    private IngredientPart() {}

    public static IngredientPart<ItemStack> forItem(Ingredient ingredient, int count) {
        Function5.Match<ItemStack> match = (recipe, slot, inputItems, inputFluids, input) -> ingredient.apply(input);
        Function4.Stack<ItemStack> get = (recipe, items, fluids, slot) -> {
            if (items != null && !items.isEmpty() && !items.get(slot).isEmpty()) {
                return ItemHandlerHelper.copyStackWithSize(items.get(slot), count);
            }
			ItemStack[] stacks = ingredient.getMatchingStacks();
			if (stacks.length == 0) {
			    return ItemStack.EMPTY;
			}
			return ItemHandlerHelper.copyStackWithSize(stacks[0], count);
        };
        Supplier<List<ItemStack>> allViableValues = () ->
                Arrays.stream(ingredient.getMatchingStacks())
                        .map(itemStack -> ItemHandlerHelper.copyStackWithSize(itemStack, count))
                        .collect(Collectors.toList());
        return new IngredientPart<>(match, get, allViableValues);
    }

    public static IngredientPart<ItemStack> forItem(ItemStack itemStack) {
        Function5.Match<ItemStack> match = (recipe, slot, inputItems, inputFluids, input) -> {
            if (itemStack.isItemEqual(input)) {
                return itemStack.getCount() <= input.getCount();
            }
            return false;
        };
        Function4.Stack<ItemStack> get = (recipe, items, fluids, slot) -> itemStack.copy();
        Supplier<List<ItemStack>> allViableValues = () -> Collections.singletonList(itemStack);
        return new IngredientPart<>(match, get, allViableValues);
    }

    public static IngredientPart<ItemStack> forItem(Item item, int count) {
        Function5.Match<ItemStack> match = (recipe, slot, inputItems, inputFluids, input) -> {
            if (input != null && item == input.getItem()) {
                return count <= input.getCount();
            }
            return false;
        };
        Function4.Stack<ItemStack> get = (recipe, items, fluids, slot) -> new ItemStack(item, count);
        Supplier<List<ItemStack>> allViableValues = () -> Collections.singletonList(new ItemStack(item, count));
        return new IngredientPart<>(match, get, allViableValues);
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

                getter = (recipe, items, fluids, slot) -> {
                    for (IngredientPart<ItemStack> part : contains) {
                        ItemStack stack = part.getter.apply(recipe, items, fluids, slot);
                        if (!stack.isEmpty()) {
                            return stack;
                        }
                    }
                    return ItemStack.EMPTY;
                };

                allViableValues = () -> {
                    List<ItemStack> items = new LinkedList<>();
                    Arrays.stream(contains)
                            .map(contain -> contain.allViableValues.get())
                            .forEach(items::addAll);
                    return items;
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
            }
			return IngredientPart.forItem(item, count);
        }
    }
    
    public static IngredientPart<FluidStack> forFluid(FluidStack fluidStack) {
        Function5.Match<FluidStack> match = (recipe, slot, inputItems, inputFluids, input) -> input.containsFluid(fluidStack);
        Function4.Stack<FluidStack> get = (recipe, items, fluids, slot) -> fluidStack.copy();
        Supplier<List<FluidStack>> allViableValues = () -> Collections.singletonList(fluidStack);
        return new IngredientPart<>(match, get, allViableValues);
    }

    public static IngredientPart<FluidStack> forFluid(Fluid fluid, int amount) {
        Function5.Match<FluidStack> match = (recipe, slot, inputItems, inputFluids, input) -> input != null && fluid == input.getFluid() && amount <= input.amount;
        Function4.Stack<FluidStack> get = (recipe, items, fluids, slot) -> new FluidStack(fluid, amount);
        Supplier<List<FluidStack>> allViableValues = () -> Collections.singletonList(new FluidStack(fluid, amount));
        return new IngredientPart<>(match, get, allViableValues);
    }

    public static IngredientPart<ItemStack> EMPTY_ITEM = new IngredientPart<>(
            (recipe, slot, inputItems, inputFluids, input) -> true,
            (recipe, items, fluids, slot) -> ItemStack.EMPTY,
            () -> Collections.singletonList(ItemStack.EMPTY));

    public static IngredientPart<FluidStack> EMPTY_FLUID = new IngredientPart<>(
            (recipe, slot, inputItems, inputFluids, input) -> true,
            (recipe, items, fluids, slot) -> FluidUtils.EMPTY,
            () -> Collections.singletonList(FluidUtils.EMPTY));
}
