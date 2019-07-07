package com.elementtimes.tutorial.plugin.jei.wrapper;

import com.elementtimes.tutorial.other.recipe.IngredientPart;
import com.elementtimes.tutorial.other.recipe.MachineRecipe;
import com.elementtimes.tutorial.other.recipe.MachineRecipeHandler;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.List;

/**
 * 只有物品输入输出的机器 jei 合成表
 * @author luqin2007
 */
public class MachineRecipeWrapper implements IRecipeWrapper {

    public List<List<ItemStack>> inputItems;
    public List<List<FluidStack>> inputFluids;
    public List<List<ItemStack>> outputItems;
    public List<List<FluidStack>> outputFluids;

    private MachineRecipeWrapper(MachineRecipe recipe) {
        inputItems = new ArrayList<>(recipe.inputs.size());
        for (IngredientPart<ItemStack> input : recipe.inputs) {
            inputItems.add(input.allViableValues.get());
        }

        inputFluids = new ArrayList<>(recipe.fluidInputs.size());
        for (IngredientPart<FluidStack> input : recipe.fluidInputs) {
            inputFluids.add(input.allViableValues.get());
        }

        outputItems = new ArrayList<>(recipe.outputs.size());
        for (IngredientPart<ItemStack> output : recipe.outputs) {
            outputItems.add(output.allViableValues.get());
        }

        outputFluids = new ArrayList<>(recipe.fluidOutputs.size());
        for (IngredientPart<FluidStack> output : recipe.fluidOutputs) {
            outputFluids.add(output.allViableValues.get());
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
        List<MachineRecipe> recipes = handler.getMachineRecipes();
        List<MachineRecipeWrapper> wrappers = new ArrayList<>(recipes.size());
        for (MachineRecipe recipe : recipes) {
            wrappers.add(new MachineRecipeWrapper(recipe));
        }
        return wrappers;
    }
}
