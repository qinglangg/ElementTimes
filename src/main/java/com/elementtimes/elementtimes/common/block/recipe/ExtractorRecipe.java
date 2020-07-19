package com.elementtimes.elementtimes.common.block.recipe;

import com.elementtimes.elementcore.api.recipe.BaseRecipe;
import com.elementtimes.elementcore.api.recipe.IIngredient;
import com.elementtimes.elementcore.api.recipe.ItemIngredient;
import com.elementtimes.elementcore.api.recipe.ItemStackIngredient;
import com.elementtimes.elementtimes.ElementTimes;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;



public class ExtractorRecipe extends BaseRecipe {

    public static final BaseRecipeType<ExtractorRecipe> TYPE = new BaseRecipeType<ExtractorRecipe>() {};

    private static NonNullList<IIngredient<ItemStack>> buildOutputs(ItemStack output0, ItemStack output1, ItemStack output2) {
        IIngredient<ItemStack> out0 = new ItemStackIngredient(output0);
        IIngredient<ItemStack> out1 = new ItemStackIngredient(output1);
        IIngredient<ItemStack> out2 = new ItemStackIngredient(output2);
        return NonNullList.from(ItemIngredient.EMPTY, out0, out1, out2);
    }

    public ExtractorRecipe(int energy, String name, ItemStack input, ItemStack output0, ItemStack output1, ItemStack output2) {
        super(energy, 0f,
                NonNullList.withSize(1, new ItemStackIngredient(input)),
                buildOutputs(output0, output1, output2),
                NonNullList.create(),
                NonNullList.create(), TYPE, new ResourceLocation(ElementTimes.MODID, name));
    }

    public ExtractorRecipe(int energy, String name, IItemProvider input, IItemProvider output, int count) {
        super(energy, 0f,
                NonNullList.withSize(1, new ItemStackIngredient(input)),
                buildOutputs(new ItemStack(output, count), ItemStack.EMPTY, ItemStack.EMPTY),
                NonNullList.create(),
                NonNullList.create(), TYPE, new ResourceLocation(ElementTimes.MODID, name));
    }
}
