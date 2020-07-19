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

import java.util.Arrays;



public class FermenterRecipe extends BaseRecipe {

    public static final BaseRecipeType<FermenterRecipe> TYPE = new BaseRecipeType<FermenterRecipe>() {};

    private static <T> NonNullList<T> list(T... list) {
        NonNullList<T> newList = NonNullList.create();
        newList.addAll(Arrays.asList(list));
        return newList;
    }

    public FermenterRecipe(int energy, String name,
                           IIngredient<ItemStack> inputItem0, IIngredient<ItemStack> inputItem1, IIngredient<ItemStack> outputItem,
                           IIngredient<FluidStack> inputFluid, IIngredient<FluidStack> outputFluid0, IIngredient<FluidStack> outputFluid1, IIngredient<FluidStack> outputFluid2) {
        super(energy, 0, list(inputItem0, inputItem1), NonNullList.withSize(1, outputItem),
                NonNullList.withSize(1, inputFluid), list(outputFluid0, outputFluid1, outputFluid2),
                TYPE, new ResourceLocation(ElementTimes.MODID, name));
    }

    public FermenterRecipe(Tag<Item> input, IItemProvider output, String id) {
        super(8000, 0f,
                NonNullList.withSize(1, new ItemIngredient(1, Ingredient.fromTag(input))),
                NonNullList.withSize(1, new ItemIngredient(1, Ingredient.fromItems(output))),
                NonNullList.create(), NonNullList.create(), TYPE, new ResourceLocation(ElementTimes.MODID, id));
    }

    public FermenterRecipe(String inputType, String inputMaterial, Item output, String id) {
        this(TagUtils.forgeItem(inputType, inputMaterial), output, id);
    }

    public FermenterRecipe(IItemProvider input, IItemProvider output, String id) {
        super(8000, 0f,
                NonNullList.withSize(1, new ItemStackIngredient(1, input)),
                NonNullList.withSize(5, new ItemStackIngredient(1, output)),
                NonNullList.create(), NonNullList.create(), TYPE, new ResourceLocation(ElementTimes.MODID, id));
    }

    public FermenterRecipe(Tag<Item> input, IIngredient<ItemStack> output, String id) {
        super(8000, 0f,
                NonNullList.withSize(1, new ItemIngredient(1, Ingredient.fromTag(input))),
                NonNullList.withSize(1, output),
                NonNullList.create(), NonNullList.create(), TYPE, new ResourceLocation(ElementTimes.MODID, id));
    }
}
