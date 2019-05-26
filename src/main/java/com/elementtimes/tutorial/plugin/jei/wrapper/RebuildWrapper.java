package com.elementtimes.tutorial.plugin.jei.wrapper;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;

import java.util.Collections;
import java.util.List;

public class RebuildWrapper implements IRecipeWrapper {

    private ItemStack input;
    private ItemStack output;
    private int energy;

    public RebuildWrapper(ItemStack input, ItemStack output, int energy) {
        this.input = input;
        this.output = output;
        this.energy = energy;
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setInputs(ItemStack.class, Collections.singletonList(input));
        ingredients.setOutputs(ItemStack.class, Collections.singletonList(output));
    }

    @Override
    public List<String> getTooltipStrings(int mouseX, int mouseY) {
        if (mouseY >= 40 && mouseY <= 44)
            return Collections.singletonList(I18n.format("tooltip.elementtimes.jei.rebuild.energy", energy));
        return Collections.emptyList();
    }
}
