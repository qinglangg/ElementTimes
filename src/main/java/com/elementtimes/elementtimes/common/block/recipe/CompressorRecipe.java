package com.elementtimes.elementtimes.common.block.recipe;

import com.elementtimes.elementcore.api.recipe.BaseRecipe;
import com.elementtimes.elementcore.api.recipe.IIngredient;
import com.elementtimes.elementcore.api.recipe.ItemIngredient;
import com.elementtimes.elementcore.api.recipe.ItemStackIngredient;
import com.elementtimes.elementcore.api.utils.TagUtils;
import com.elementtimes.elementtimes.ElementTimes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.Tag;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;



public class CompressorRecipe extends BaseRecipe {

    public static final BaseRecipeType<CompressorRecipe> TYPE = new BaseRecipeType<CompressorRecipe>() {};

    public CompressorRecipe(int energy, float experience, NonNullList<IIngredient<ItemStack>> itemInputs, NonNullList<IIngredient<ItemStack>> itemOutputs, NonNullList<IIngredient<FluidStack>> fluidInputs, NonNullList<IIngredient<FluidStack>> fluidOutputs, ResourceLocation id) {
        super(energy, experience, itemInputs, itemOutputs, fluidInputs, fluidOutputs, TYPE, id);
    }

    public CompressorRecipe(Tag<Item> input, IItemProvider output, String id) {
        super(8000, 0f,
                NonNullList.withSize(1, new ItemIngredient(1, Ingredient.fromTag(input))),
                NonNullList.withSize(1, new ItemStackIngredient(1, output)),
                NonNullList.create(), NonNullList.create(), TYPE, new ResourceLocation(ElementTimes.MODID, id));
    }

    public CompressorRecipe(String inputType, String inputMaterial, Item output, String id) {
        this(TagUtils.forgeItem(inputType, inputMaterial), output, id);
    }

    public CompressorRecipe(IItemProvider input, IItemProvider output, String id) {
        super(8000, 0f,
                NonNullList.withSize(1, new ItemStackIngredient(1, input)),
                NonNullList.withSize(1, new ItemStackIngredient(5, output)),
                NonNullList.create(), NonNullList.create(), TYPE, new ResourceLocation(ElementTimes.MODID, id));
    }
}
