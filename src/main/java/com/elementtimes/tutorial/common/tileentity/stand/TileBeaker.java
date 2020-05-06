package com.elementtimes.tutorial.common.tileentity.stand;

import com.elementtimes.elementcore.api.annotation.tools.ModInvokeStatic;
import com.elementtimes.elementcore.api.template.tileentity.recipe.IngredientPart;
import com.elementtimes.elementcore.api.template.tileentity.recipe.MachineRecipeHandler;
import com.elementtimes.tutorial.common.block.stand.module.ModuleBeaker;
import com.elementtimes.tutorial.common.init.ElementtimesFluids;
import com.elementtimes.tutorial.common.init.ElementtimesItems;
import net.minecraftforge.fluids.Fluid;

@ModInvokeStatic("init")
public class TileBeaker extends BaseTileModule<ModuleBeaker> {
    public static MachineRecipeHandler RECIPE = new MachineRecipeHandler(3, 0, 2, 1);

    public TileBeaker() {
        super(new ModuleBeaker());
    }

    public static void init() {
        if (RECIPE.getMachineRecipes().isEmpty() && RECIPE.getMachineRecipes().isEmpty()) {
            RECIPE
                    //盐溶解
                    .newRecipe()
                    .addItemInput(IngredientPart.forItem(ElementtimesItems.salt, 1))
                    .addFluidInput(IngredientPart.forFluid(ElementtimesFluids.waterDistilled, Fluid.BUCKET_VOLUME))
                    .addFluidOutput(IngredientPart.forFluid(ElementtimesFluids.NaClSolutionDilute, Fluid.BUCKET_VOLUME))
                    .endAdd()
                    //盐浓溶液稀释
                    .newRecipe()
                    .addFluidInput(IngredientPart.forFluid(ElementtimesFluids.NaClSolutionDilute, Fluid.BUCKET_VOLUME))
                    .addFluidInput(IngredientPart.forFluid(ElementtimesFluids.waterDistilled, Fluid.BUCKET_VOLUME))
                    .addFluidOutput(IngredientPart.forFluid(ElementtimesFluids.waterDistilled, Fluid.BUCKET_VOLUME))
                    .endAdd();
        }
    }
}
