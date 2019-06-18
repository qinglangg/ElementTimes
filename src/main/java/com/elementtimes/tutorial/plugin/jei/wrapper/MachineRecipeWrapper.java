package com.elementtimes.tutorial.plugin.jei.wrapper;

import com.elementtimes.tutorial.other.recipe.IngredientPart;
import com.elementtimes.tutorial.other.recipe.MachineRecipe;
import com.elementtimes.tutorial.other.recipe.MachineRecipeHandler;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * 只有物品输入输出的机器 jei 合成表
 * @author luqin2007
 */
public class MachineRecipeWrapper implements IRecipeWrapper {

    private List<List<ItemStack>> inputItems;
    private List<List<ItemStack>> outputItems;

    private MachineRecipeWrapper(MachineRecipe recipe) {
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

    public static List<MachineRecipeWrapper> fromHandler(MachineRecipeHandler handler) {
        List<MachineRecipe> recipes = handler.getMachineRecipes();
        List<MachineRecipeWrapper> wrappers = new ArrayList<>(recipes.size());
        for (MachineRecipe recipe : recipes) {
            wrappers.add(new MachineRecipeWrapper(recipe));
        }
        return wrappers;
    }
}
