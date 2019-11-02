package com.elementtimes.tutorial.plugin.jei.wrapper;

import com.elementtimes.elementcore.api.template.tileentity.recipe.IngredientPart;
import com.elementtimes.elementcore.api.template.tileentity.recipe.MachineRecipe;
import com.elementtimes.elementcore.api.template.tileentity.recipe.MachineRecipeHandler;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import java.util.*;

/**
 * 机器 jei 合成表
 * @author luqin2007
 */
public class MachineRecipeWrapper implements IRecipeWrapper {

    public final List<List<ItemStack>> inputItems;
    public final List<List<FluidStack>> inputFluids;
    public final List<List<ItemStack>> outputItems;
    public final List<List<FluidStack>> outputFluids;
    public final List<Map<ItemStack, List<String>>> itemOutputString;
    public final List<Map<ItemStack, List<String>>> itemInputString;
    public final List<Map<FluidStack, List<String>>> fluidOutputString;
    public final List<Map<FluidStack, List<String>>> fluidInputString;

    private MachineRecipeWrapper(MachineRecipe recipe) {
        inputItems = new ArrayList<>();
        itemInputString = new ArrayList<>();
        for (IngredientPart<ItemStack> input : recipe.inputs) {
            List<ItemStack> itemInput = input.allViableValues.get();
            List<ItemStack> itemList = new ArrayList<>();
            Map<ItemStack, List<String>> stringMap = new HashMap<>();
            for (ItemStack item : itemInput) {
                List<String> strings = new ArrayList<>();
                ItemStack stack = addString(input, item, strings, true);
                itemList.add(stack);
                stringMap.put(stack, strings);
            }
            inputItems.add(itemList);
            itemInputString.add(stringMap);
        }

        inputFluids = new ArrayList<>();
        fluidInputString = new ArrayList<>();
        for (IngredientPart<FluidStack> input : recipe.fluidInputs) {
            List<FluidStack> fluidInput = input.allViableValues.get();
            List<FluidStack> fluidList = new ArrayList<>();
            Map<FluidStack, List<String>> stringMap = new HashMap<>();
            for (FluidStack fluid : fluidInput) {
                List<String> strings = new ArrayList<>();
                FluidStack stack = addString(input, fluid, strings, true);
                fluidList.add(stack);
                stringMap.put(stack, strings);
            }
            inputFluids.add(fluidList);
            fluidInputString.add(stringMap);
        }

        outputItems = new ArrayList<>();
        itemOutputString = new ArrayList<>();
        for (IngredientPart<ItemStack> output : recipe.outputs) {
            List<ItemStack> itemOutput = output.allViableValues.get();
            List<ItemStack> itemList = new ArrayList<>();
            Map<ItemStack, List<String>> stringMap = new HashMap<>();
            for (ItemStack item : itemOutput) {
                List<String> strings = new ArrayList<>();
                ItemStack stack = addString(output, item, strings, false);
                itemList.add(stack);
                stringMap.put(stack, strings);
            }
            outputItems.add(itemList);
            itemOutputString.add(stringMap);
        }

        outputFluids = new ArrayList<>();
        fluidOutputString = new ArrayList<>();
        for (IngredientPart<FluidStack> output : recipe.fluidOutputs) {
            List<FluidStack> fluidOutput = output.allViableValues.get();
            List<FluidStack> fluidList = new ArrayList<>();
            Map<FluidStack, List<String>> stringMap = new HashMap<>();
            for (FluidStack fluid : fluidOutput) {
                List<String> strings = new ArrayList<>();
                FluidStack stack = addString(output, fluid, strings, true);
                fluidList.add(stack);
                stringMap.put(stack, strings);
            }
            outputFluids.add(fluidList);
            fluidOutputString.add(stringMap);
        }
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setInputLists(VanillaTypes.ITEM, inputItems);
        ingredients.setInputLists(VanillaTypes.FLUID, inputFluids);
        ingredients.setOutputLists(VanillaTypes.ITEM, outputItems);
        ingredients.setOutputLists(VanillaTypes.FLUID, outputFluids);
    }

    public static List<MachineRecipeWrapper> fromHandler(MachineRecipeHandler handler) {
        if (handler == null) {
            return Collections.emptyList();
        }
        List<MachineRecipe> recipes = handler.getMachineRecipes();
        List<MachineRecipeWrapper> wrappers = new ArrayList<>(recipes.size());
        for (MachineRecipe recipe : recipes) {
            wrappers.add(new MachineRecipeWrapper(recipe));
        }
        return wrappers;
    }

    private ItemStack addString(IngredientPart<ItemStack> ingredient, ItemStack item, List<String> tooltips, boolean isInput) {
        ItemStack stack = item.copy();
        if (stack.getCount() == 0 && isInput) {
            stack.setCount(1);
            tooltips.add("无消耗");
        }
        if (ingredient.probability < 1) {
            tooltips.add("概率：" + ingredient.probability * 100 + "%");
        }
        tooltips.addAll(ingredient.tooltips);
        return stack;
    }

    private FluidStack addString(IngredientPart<FluidStack> ingredient, FluidStack fluid, List<String> tooltips, boolean isInput) {
        FluidStack stack = fluid.copy();
        if (stack.amount == 0 && isInput) {
            stack.amount = 1000;
            tooltips.add("无消耗");
        }
        tooltips.addAll(ingredient.tooltips);
        return stack;
    }
}
