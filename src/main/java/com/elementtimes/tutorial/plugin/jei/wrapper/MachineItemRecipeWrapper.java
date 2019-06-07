package com.elementtimes.tutorial.plugin.jei.wrapper;

import com.elementtimes.tutorial.other.IngredientPart;
import com.elementtimes.tutorial.other.MachineRecipeHandler;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class MachineItemRecipeWrapper implements IRecipeWrapper {

    List<List<ItemStack>> inputItems;
    List<List<ItemStack>> outputItems;

    public MachineItemRecipeWrapper(MachineRecipeHandler.MachineRecipe recipe) {
        inputItems = new ArrayList<>(recipe.inputs.size());
        for (IngredientPart<ItemStack> input : recipe.inputs) {
            inputItems.add(input.allViableValues.get());
        }

        outputItems = new ArrayList<>(recipe.outputs.size());
        for (IngredientPart<ItemStack> output : recipe.outputs) {
            outputItems.add(output.allViableValues.get());
        }
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setInputLists(ItemStack.class, inputItems);
        ingredients.setOutputLists(ItemStack.class, outputItems);
    }

    public static List<MachineItemRecipeWrapper> fromHandler(MachineRecipeHandler handler) {
        List<MachineRecipeHandler.MachineRecipe> recipes = handler.getMachineRecipes();
        List<MachineItemRecipeWrapper> wrappers = new ArrayList<>(recipes.size());
        for (MachineRecipeHandler.MachineRecipe recipe : recipes) {
            wrappers.add(new MachineItemRecipeWrapper(recipe));
        }
        return wrappers;
    }
}
