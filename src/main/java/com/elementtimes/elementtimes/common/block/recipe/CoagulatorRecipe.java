package com.elementtimes.elementtimes.common.block.recipe;

import com.elementtimes.elementcore.api.recipe.BaseRecipe;
import com.elementtimes.elementcore.api.recipe.FluidIngredient;
import com.elementtimes.elementcore.api.recipe.FluidStackIngredient;
import com.elementtimes.elementcore.api.recipe.ItemStackIngredient;
import com.elementtimes.elementtimes.ElementTimes;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;



public class CoagulatorRecipe extends BaseRecipe {

    public static final BaseRecipeType<CoagulatorRecipe> TYPE = new BaseRecipeType<CoagulatorRecipe>() {};

    public CoagulatorRecipe(int energy, String name, Fluid input, IItemProvider output) {
        super(energy, 0f,
                NonNullList.create(),
                NonNullList.withSize(1, new ItemStackIngredient(output)),
                NonNullList.withSize(1, new FluidIngredient(input)),
                NonNullList.create(), TYPE, new ResourceLocation(ElementTimes.MODID, name));
    }

    public CoagulatorRecipe(int energy, String name, FluidStack input, ItemStack output) {
        super(energy, 0f,
                NonNullList.create(),
                NonNullList.withSize(1, new ItemStackIngredient(output)),
                NonNullList.withSize(1, new FluidStackIngredient(input)),
                NonNullList.create(), TYPE, new ResourceLocation(ElementTimes.MODID, name));
    }
}
