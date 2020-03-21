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
import java.util.function.Supplier;

/**
 * 机器 jei 合成表
 * @author luqin2007
 */
public class MachineRecipeWrapper implements IRecipeWrapper {

    public final List<List<ItemStack>> inputItems;
    public final List<List<FluidStack>> inputFluids;
    public final List<List<ItemStack>> outputItems;
    public final List<List<FluidStack>> outputFluids;
    public final List<Map<ItemStack,  Supplier<List<String>>>> itemOutputString;
    public final List<Map<ItemStack,  Supplier<List<String>>>> itemInputString;
    public final List<Map<FluidStack, Supplier<List<String>>>> fluidOutputString;
    public final List<Map<FluidStack, Supplier<List<String>>>> fluidInputString;

    private MachineRecipeWrapper(MachineRecipe recipe) {
        inputItems = new ArrayList<>();
        itemInputString = new ArrayList<>();
        for (IngredientPart<ItemStack> input : recipe.inputs) {
            List<ItemStack> itemInput = input.allViableValues.get();
            List<ItemStack> itemList = new ArrayList<>();
            Map<ItemStack, Supplier<List<String>>> stringMap = new HashMap<>();
            for (ItemStack item : itemInput) {
                ItemStack stack = item.copy();
                itemList.add(stack);
                stringMap.put(stack, () -> getTooltips(input, stack, true));
            }
            inputItems.add(itemList);
            itemInputString.add(stringMap);
        }

        inputFluids = new ArrayList<>();
        fluidInputString = new ArrayList<>();
        for (IngredientPart<FluidStack> input : recipe.fluidInputs) {
            List<FluidStack> fluidInput = input.allViableValues.get();
            List<FluidStack> fluidList = new ArrayList<>();
            Map<FluidStack, Supplier<List<String>>> stringMap = new HashMap<>();
            for (FluidStack fluid : fluidInput) {
                FluidStack stack = fluid.copy();
                fluidList.add(stack);
                stringMap.put(stack, () -> getTooltips(input, stack, true));
            }
            inputFluids.add(fluidList);
            fluidInputString.add(stringMap);
        }

        outputItems = new ArrayList<>();
        itemOutputString = new ArrayList<>();
        for (IngredientPart<ItemStack> output : recipe.outputs) {
            List<ItemStack> itemOutput = output.allViableValues.get();
            List<ItemStack> itemList = new ArrayList<>();
            Map<ItemStack, Supplier<List<String>>> stringMap = new HashMap<>();
            for (ItemStack item : itemOutput) {
                ItemStack stack = item.copy();
                itemList.add(stack);
                stringMap.put(stack, () -> getTooltips(output, stack, false));
            }
            outputItems.add(itemList);
            itemOutputString.add(stringMap);
        }

        outputFluids = new ArrayList<>();
        fluidOutputString = new ArrayList<>();
        for (IngredientPart<FluidStack> output : recipe.fluidOutputs) {
            List<FluidStack> fluidOutput = output.allViableValues.get();
            List<FluidStack> fluidList = new ArrayList<>();
            Map<FluidStack, Supplier<List<String>>> stringMap = new HashMap<>();
            for (FluidStack fluid : fluidOutput) {
                FluidStack stack = fluid.copy();
                fluidList.add(stack);
                stringMap.put(stack, () -> getTooltips(output, stack, false));
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

    private List<String> getTooltips(IngredientPart<ItemStack> ingredient, ItemStack stack, boolean isInput) {
        List<String> tooltips = new ArrayList<>();
        if (stack.getCount() == 0 && isInput) {
            stack.setCount(1);
            tooltips.add("催化剂");
        }
        if (ingredient.probability < 1) {
            tooltips.add("概率：" + ingredient.probability * 100 + "%");
        }
        tooltips.addAll(ingredient.getTooltips());
        return tooltips;
    }

    private List<String> getTooltips(IngredientPart<FluidStack> ingredient, FluidStack stack, boolean isInput) {
        List<String> tooltips = new ArrayList<>();
        if (stack.amount == 0 && isInput) {
            stack.amount = 1000;
            tooltips.add("催化剂");
        }
        tooltips.addAll(ingredient.getTooltips());
        return tooltips;
    }
}
