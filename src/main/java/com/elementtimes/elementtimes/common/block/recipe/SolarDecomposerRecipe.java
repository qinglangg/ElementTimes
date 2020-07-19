package com.elementtimes.elementtimes.common.block.recipe;

import com.elementtimes.elementcore.api.recipe.BaseRecipe;
import com.elementtimes.elementcore.api.recipe.FluidIngredient;
import com.elementtimes.elementcore.api.recipe.FluidStackIngredient;
import com.elementtimes.elementcore.api.recipe.IIngredient;
import com.elementtimes.elementtimes.ElementTimes;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;



public class SolarDecomposerRecipe extends BaseRecipe {

    public static final BaseRecipeType<SolarDecomposerRecipe> TYPE = new BaseRecipeType<SolarDecomposerRecipe>() {};

    private static NonNullList<IIngredient<FluidStack>> output(FluidStack output0, FluidStack output1, FluidStack output2) {
        IIngredient<FluidStack> out0 = new FluidStackIngredient(output0);
        IIngredient<FluidStack> out1 = new FluidStackIngredient(output1);
        IIngredient<FluidStack> out2 = new FluidStackIngredient(output2);
        NonNullList<IIngredient<FluidStack>> outputs = NonNullList.withSize(3, new FluidIngredient());
        outputs.set(0, out0);
        outputs.set(1, out1);
        outputs.set(2, out2);
        return outputs;
    }

    public SolarDecomposerRecipe(int energy, String name, FluidStack input, FluidStack output0, FluidStack output1) {
        this(energy, name, input, output0, output1, FluidStack.EMPTY);
    }

    public SolarDecomposerRecipe(int energy, String name, FluidStack input, FluidStack output0, FluidStack output1, FluidStack output2) {
        super(energy, 0f,
                NonNullList.create(),
                NonNullList.create(),
                NonNullList.withSize(1, new FluidStackIngredient(input)),
                output(output0, output1, output2),
                TYPE, new ResourceLocation(ElementTimes.MODID, name));
    }
}
