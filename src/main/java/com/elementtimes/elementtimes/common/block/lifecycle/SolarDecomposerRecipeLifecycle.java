package com.elementtimes.elementtimes.common.block.lifecycle;

import com.elementtimes.elementcore.api.tileentity.BaseTileEntity;
import com.elementtimes.elementcore.api.tileentity.lifecycle.BaseTeRecipeLifecycle;
import com.elementtimes.elementtimes.common.block.recipe.CentrifugeRecipe;



public class SolarDecomposerRecipeLifecycle extends BaseTeRecipeLifecycle<CentrifugeRecipe> {

    public SolarDecomposerRecipeLifecycle(BaseTileEntity tile) {
        super(CentrifugeRecipe.TYPE, tile);
    }

    // item

    @Override
    protected int getItemIngredientSlot(int index) {
        return 0;
    }

    @Override
    protected int getItemIngredientIndex(int slot) {
        return 0;
    }

    @Override
    protected int getItemIngredientSlotCount() {
        return 0;
    }

    @Override
    protected boolean isItemIngredientSlot(int slot) {
        return false;
    }

    @Override
    protected int getItemProductSlot(int index) {
        return 0;
    }

    @Override
    protected int getItemProductSlotCount() {
        return 0;
    }

    @Override
    protected boolean isItemProductSlot(int slot) {
        return false;
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
        return slot == 0;
    }

    @Override
    protected boolean isFluidIngredientBucketOutputSlot(int slot) {
        return slot == 4;
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
        return slot > 0 && slot < 4;
    }

    @Override
    protected boolean isFluidProductBucketOutputSlot(int slot) {
        return slot > 4 && slot < 8;
    }

    // parameter

    @Override
    protected int getEnergySpeed() {
        return 30 * getInterval();
    }

    @Override
    protected int getInterval() {
        return 5;
    }
}
