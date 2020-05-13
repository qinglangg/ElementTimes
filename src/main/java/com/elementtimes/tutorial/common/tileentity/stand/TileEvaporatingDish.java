package com.elementtimes.tutorial.common.tileentity.stand;

import com.elementtimes.elementcore.api.annotation.tools.ModInvokeStatic;
import com.elementtimes.elementcore.api.template.tileentity.recipe.IngredientPart;
import com.elementtimes.elementcore.api.template.tileentity.recipe.MachineRecipeHandler;
import com.elementtimes.tutorial.common.tileentity.stand.module.ModuleEvaporatingDish;
import com.elementtimes.tutorial.common.init.ElementtimesFluids;

/**
 * 蒸发皿
 * @author luqin2007
 */
@ModInvokeStatic("init")
public class TileEvaporatingDish extends BaseTileModule<ModuleEvaporatingDish> {

    public static MachineRecipeHandler RECIPE = null;

    public TileEvaporatingDish() {
        super(new ModuleEvaporatingDish());
    }

    public static void init() {
        RECIPE = new MachineRecipeHandler(0, 2, 1, 2)
        		.newRecipe()
        		.addCost(200)
                .addFluidInput(IngredientPart.forFluid(ElementtimesFluids.NaClSolutionDilute, 1000))
                .addFluidOutput(IngredientPart.forFluid(ElementtimesFluids.NaClSolutionConcentrated, 1000))
                .endAdd();
    }

}
