package com.elementtimes.tutorial.common.tileentity;

import com.elementtimes.tutorial.annotation.annotations.ModElement;
import com.elementtimes.tutorial.common.init.ElementtimesFluids;
import com.elementtimes.tutorial.common.init.ElementtimesGUI;
import com.elementtimes.tutorial.other.lifecycle.FluidMachineLifecycle;
import com.elementtimes.tutorial.other.recipe.IngredientPart;
import com.elementtimes.tutorial.other.recipe.MachineRecipeHandler;

import javax.annotation.Nonnull;

@ModElement
@ModElement.ModInvokeStatic("init")
public class TileCentrifuge extends BaseMachine {

    public static MachineRecipeHandler RECIPE = null;

    public static void init() {
        if (RECIPE == null) {
            RECIPE = new MachineRecipeHandler()
                    .newRecipe("air")
                    .addCost(100)
                    .addFluidInput(IngredientPart.forFluid(ElementtimesFluids.air, 100))
                    .addFluidOutput(IngredientPart.forFluid(ElementtimesFluids.nitrogen, 78))
                    .addFluidOutput(IngredientPart.forFluid(ElementtimesFluids.oxygen, 20))
                    .addFluidOutput(IngredientPart.forFluid(ElementtimesFluids.steam, 1))
                    .addFluidOutput(IngredientPart.forFluid(ElementtimesFluids.rareGases, 1))
                    .endAdd();
        }
    }

    TileCentrifuge() {
        super(100000, 5, 5, 1, 16000, 4, 16000);
        markBucketInput(0, 1, 2, 3);
        addLifeCycle(new FluidMachineLifecycle(this, 1, 4));
    }

    @Override
    public ElementtimesGUI.Machines getGuiType() {
        return ElementtimesGUI.Machines.Centrifuge;
    }

    @Nonnull
    @Override
    public MachineRecipeHandler updateRecipe(@Nonnull MachineRecipeHandler recipe) {
        return RECIPE;
    }
}
