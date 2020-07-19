package com.elementtimes.elementtimes.common.block.recipe;

import com.elementtimes.elementcore.api.recipe.BaseRecipe;
import com.elementtimes.elementcore.api.recipe.FluidStackIngredient;
import com.elementtimes.elementcore.api.recipe.IIngredient;
import com.elementtimes.elementtimes.ElementTimes;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;



public class CentrifugeRecipe extends BaseRecipe {

    public static final BaseRecipeType<CentrifugeRecipe> TYPE = new BaseRecipeType<CentrifugeRecipe>() {};

    private static NonNullList<IIngredient<FluidStack>> buildOutputs(FluidStack output0, FluidStack output1, FluidStack output2, FluidStack output3, FluidStack output4) {
        IIngredient<FluidStack> out0 = new FluidStackIngredient(output0);
        IIngredient<FluidStack> out1 = new FluidStackIngredient(output1);
        IIngredient<FluidStack> out2 = new FluidStackIngredient(output2);
        IIngredient<FluidStack> out3 = new FluidStackIngredient(output3);
        IIngredient<FluidStack> out4 = new FluidStackIngredient(output4);
        NonNullList<IIngredient<FluidStack>> outputs = NonNullList.withSize(5, new FluidStackIngredient());
        outputs.set(0, out0);
        outputs.set(1, out1);
        outputs.set(2, out2);
        outputs.set(3, out3);
        outputs.set(4, out4);
        return outputs;
    }

    public CentrifugeRecipe(int energy, String name, FluidStack input, FluidStack output0, FluidStack output1, FluidStack output2, FluidStack output3, FluidStack output4) {
        super(energy, 0f, NonNullList.create(), NonNullList.create(), NonNullList.withSize(1, new FluidStackIngredient(input)),
                buildOutputs(output0, output1, output2, output3, output4), TYPE, new ResourceLocation(ElementTimes.MODID, name));
    }
}
