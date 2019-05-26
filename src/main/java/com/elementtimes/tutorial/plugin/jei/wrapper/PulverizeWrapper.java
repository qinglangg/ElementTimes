package com.elementtimes.tutorial.plugin.jei.wrapper;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.Collections;
import java.util.List;

public class PulverizeWrapper implements IRecipeWrapper {

    private List<ItemStack> input;
    private ItemStack output;

    public PulverizeWrapper(String name, Item item) {
        input = OreDictionary.getOres(name);
        output = new ItemStack(item);
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setInputLists(ItemStack.class, Collections.singletonList(input));
        ingredients.setOutputs(ItemStack.class, Collections.singletonList(output));
    }
}
