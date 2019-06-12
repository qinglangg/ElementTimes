package com.elementtimes.tutorial.common.tileentity;

import com.elementtimes.tutorial.other.recipe.MachineRecipeHandler;

import javax.annotation.Nonnull;

/**
 * @author KSGFK create in 2019/6/12
 */
public class TileSupportStand extends BaseMachine {
    private MachineRecipeHandler recipe = new MachineRecipeHandler();

    public TileSupportStand() {
        super(0, 0, 0);
    }

    @Nonnull
    @Override
    public MachineRecipeHandler updateRecipe(@Nonnull MachineRecipeHandler recipe) {
        return recipe;
    }

    @Override
    public boolean hasFastRenderer() {
        return true;
    }
}
