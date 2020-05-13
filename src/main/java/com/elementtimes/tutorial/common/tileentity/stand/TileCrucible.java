package com.elementtimes.tutorial.common.tileentity.stand;

import com.elementtimes.elementcore.api.annotation.tools.ModInvokeStatic;
import com.elementtimes.elementcore.api.template.tileentity.recipe.IngredientPart;
import com.elementtimes.elementcore.api.template.tileentity.recipe.MachineRecipeHandler;
import com.elementtimes.tutorial.common.tileentity.stand.module.ModuleCrucible;
import com.elementtimes.tutorial.common.init.ElementtimesItems;

/**
 * 坩埚
 * @author luqin2007
 */
@ModInvokeStatic("init")
public class TileCrucible extends BaseTileModule<ModuleCrucible> {

    public static MachineRecipeHandler RECIPE = null;

    public TileCrucible() {
        super(new ModuleCrucible());
    }

    public static void init() {
        RECIPE = new MachineRecipeHandler(1, 2, 0, 2)
        		.newRecipe()
        		.addCost(200)
                .addItemInput(IngredientPart.forItem(ElementtimesItems.uraniumPowder, 1))
                .addItemOutput(IngredientPart.forItem(ElementtimesItems.UO2 ,1))
                .endAdd()
        		.newRecipe()
        		.addCost(200)
                .addItemInput(IngredientPart.forItem(ElementtimesItems.stoneIngot, 1))
                .addItemOutput(IngredientPart.forItem(ElementtimesItems.calciumOxide ,1))
                .endAdd();
    }
}
