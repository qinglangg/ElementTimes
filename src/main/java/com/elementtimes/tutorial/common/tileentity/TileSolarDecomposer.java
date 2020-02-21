package com.elementtimes.tutorial.common.tileentity;

import com.elementtimes.elementcore.api.template.tileentity.recipe.IngredientPart;
import com.elementtimes.elementcore.api.template.tileentity.recipe.MachineRecipeHandler;
import com.elementtimes.tutorial.common.init.ElementtimesBlocks;
import com.elementtimes.tutorial.common.init.ElementtimesFluids;
import com.elementtimes.tutorial.common.init.ElementtimesGUI;
import com.elementtimes.tutorial.plugin.elementcore.JeiRecipe;

import javax.annotation.Nonnull;

/**
 * 虽然太阳能分解机继承固液反应器从逻辑上有点不太对，但因 GUI 相同，可通用方法很多
 * @author luqin2007
 */
public class TileSolarDecomposer extends TileSolidFluidReactor {

    @JeiRecipe.MachineRecipe(block = "elementtimes:solardecomposer", gui = TileSolarDecomposer.class, u = 17, v = 10, w = 141, h = 55)
    public static MachineRecipeHandler RECIPE = new MachineRecipeHandler(1, 2, 1, 2)
    		.newRecipe()
            .addCost(200)
            .addFluidInput(IngredientPart.forFluid(ElementtimesFluids.waterDistilled, 2000))
            .addFluidOutput(IngredientPart.forFluid(ElementtimesFluids.oxygen, 1000))
            .addFluidOutput(IngredientPart.forFluid(ElementtimesFluids.H, 2000))
            .endAdd()
            
    		.newRecipe()
            .addCost(100)
            .addFluidInput(IngredientPart.forFluid(ElementtimesFluids.NH4Cl, 1000))
            .addFluidOutput(IngredientPart.forFluid(ElementtimesFluids.HCl, 1000))
            .addFluidOutput(IngredientPart.forFluid(ElementtimesFluids.ammonia, 1000))
            .endAdd()
    
    		.newRecipe()
            .addCost(200)
            .addFluidInput(IngredientPart.forFluid(ElementtimesFluids.HClO, 2000))
            .addFluidOutput(IngredientPart.forFluid(ElementtimesFluids.oxygen, 1000))
            .addFluidOutput(IngredientPart.forFluid(ElementtimesFluids.HCl, 2000))
            .endAdd()
            //4HNO3=2H2O+4NO2+O2
    		//.newRecipe()
            //.addCost(400)
            //.addFluidInput(IngredientPart.forFluid(ElementtimesFluids.hno3, 4000))
            //.addFluidOutput(IngredientPart.forFluid(FluidRegistry.WATER, 2000))
           // .addFluidOutput(IngredientPart.forFluid(ElementtimesFluids.no2, 4000))
            //.addFluidOutput(IngredientPart.forFluid(ElementtimesFluids.oxygen, 1000))
            //.endAdd()
    ;

    @Override
    public String getTitle() {
        return ElementtimesBlocks.solarDecomposer.getLocalizedName();
    }

    @Override
    public int getGuiId() {
        return ElementtimesGUI.Machines.SolarDecomposer.id();
    }

    @Nonnull
    @Override
    public MachineRecipeHandler getRecipes() {
        return RECIPE;
    }
}
