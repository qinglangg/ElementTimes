package com.elementtimes.tutorial.plugin.jei.wrapper;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.Collections;
import java.util.List;

public class CompressorWrapper implements IRecipeWrapper {

    private List<ItemStack> input;
    private ItemStack output;

    public CompressorWrapper(Object input, ItemStack output) {
        if (input instanceof String) this.input = OreDictionary.getOres((String) input);
        else if (input instanceof Item) this.input = Collections.singletonList(new ItemStack((Item) input));
        else if (input instanceof Block) this.input = Collections.singletonList(new ItemStack((Block) input));
        else this.input = Collections.singletonList(ItemStack.EMPTY);
        this.output = output;
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setInputs(ItemStack.class, Collections.singletonList(input));
        ingredients.setOutputs(ItemStack.class, Collections.singletonList(output));
    }
}
