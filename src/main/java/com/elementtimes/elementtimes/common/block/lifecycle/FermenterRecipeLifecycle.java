package com.elementtimes.elementtimes.common.block.lifecycle;

import com.elementtimes.elementcore.api.tileentity.BaseTileEntity;
import com.elementtimes.elementcore.api.tileentity.lifecycle.BaseTeRecipeLifecycle;
import com.elementtimes.elementtimes.common.block.recipe.FermenterRecipe;
import com.elementtimes.elementtimes.config.Config;



public class FermenterRecipeLifecycle extends BaseTeRecipeLifecycle<FermenterRecipe> {

    public FermenterRecipeLifecycle(BaseTileEntity tile) {
        super(FermenterRecipe.TYPE, tile);
    }

    // item

    @Override
    protected int getItemIngredientSlot(int index) {
        return index;
    }

    @Override
    protected int getItemIngredientIndex(int slot) {
        return slot;
    }

    @Override
    protected int getItemIngredientSlotCount() {
        return 2;
    }

    @Override
    protected boolean isItemIngredientSlot(int slot) {
        return slot == 0 || slot == 1;
    }

    @Override
    protected int getItemProductSlot(int index) {
        return 6;
    }

    @Override
    protected int getItemProductSlotCount() {
        return 1;
    }

    @Override
    protected boolean isItemProductSlot(int slot) {
        return slot == 6;
    }

    // fluid

    @Override
    protected int getFluidIngredientSlot(int index) {
        return 0;
    }

    @Override
    protected int getFluidIngredientIndex(int slot) {
        return 0;
    }

    @Override
    protected int getFluidIngredientSlotCount() {
        return 1;
    }

    @Override
    protected boolean isFluidIngredientSlot(int slot) {
        return slot == 0;
    }

    @Override
    protected boolean isFluidIngredientBucketInputSlot(int slot) {
        return slot == 2;
    }

    @Override
    protected boolean isFluidIngredientBucketOutputSlot(int slot) {
        return slot == 7;
    }

    @Override
    protected int getFluidProductSlot(int index) {
        return index + 1;
    }

    @Override
    protected int getFluidProductSlotCount() {
        return 3;
    }

    @Override
    protected boolean isFluidProductSlot(int slot) {
        return slot > 0 && slot < 4;
    }

    @Override
    protected boolean isFluidProductBucketInputSlot(int slot) {
        return slot > 2 && slot < 6;
    }

    @Override
    protected boolean isFluidProductBucketOutputSlot(int slot) {
        return slot > 7 && slot < 11;
    }

    // parameter

    @Override
    protected int getEnergySpeed() {
        return Config.formingSpeed.get() * getInterval();
    }

    @Override
    protected int getInterval() {
        return 5;
    }
}
