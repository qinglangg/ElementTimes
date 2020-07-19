package com.elementtimes.elementtimes.common.block.recipe;

import com.elementtimes.elementcore.api.recipe.*;
import com.elementtimes.elementcore.api.utils.TagUtils;
import com.elementtimes.elementtimes.ElementTimes;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;



public class SolidFluidReactorRecipe extends BaseRecipe {

    public static final BaseRecipeType<SolidFluidReactorRecipe> TYPE = new BaseRecipeType<SolidFluidReactorRecipe>() {};

    public static NonNullList<IIngredient<ItemStack>> items(ItemStack output0, ItemStack output1) {
        NonNullList<IIngredient<ItemStack>> list = NonNullList.withSize(2, ItemIngredient.EMPTY);
        list.set(0, new ItemStackIngredient(output0));
        list.set(1, new ItemStackIngredient(output1));
        return list;
    }

    public static NonNullList<IIngredient<FluidStack>> fluids(FluidStack output0, FluidStack output1) {
        NonNullList<IIngredient<FluidStack>> list = NonNullList.withSize(2, new FluidIngredient());
        list.set(0, new FluidStackIngredient(output0));
        list.set(1, new FluidStackIngredient(output1));
        return list;
    }

    public SolidFluidReactorRecipe(int energy, ItemStack inputItem, FluidStack inputFluid,
                                   ItemStack outputItem0, ItemStack outputItem1, FluidStack outputFluid0, FluidStack outputFluid1, String name) {
        super(energy, 0,
                NonNullList.withSize(1, new ItemStackIngredient(inputItem)),
                items(outputItem0, outputItem1),
                NonNullList.withSize(1, new FluidStackIngredient(inputFluid)),
                fluids(outputFluid0, outputFluid1),
                TYPE, new ResourceLocation(ElementTimes.MODID, name));
    }

    public SolidFluidReactorRecipe(int energy, String type, String material, int count, FluidStack inputFluid,
                                   ItemStack outputItem0, ItemStack outputItem1, FluidStack outputFluid0, FluidStack outputFluid1, String name) {
        super(energy, 0,
                NonNullList.withSize(1, new ItemIngredient(count, Ingredient.fromTag(TagUtils.forgeItem(type, material)))),
                items(outputItem0, outputItem1),
                NonNullList.withSize(1, new FluidStackIngredient(inputFluid)),
                fluids(outputFluid0, outputFluid1),
                TYPE, new ResourceLocation(ElementTimes.MODID, name));
    }
}
