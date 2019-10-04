package com.elementtimes.tutorial.common.tileentity;

import com.elementtimes.elementcore.api.annotation.ModInvokeStatic;
import com.elementtimes.elementcore.api.template.tileentity.BaseTileEntity;
import com.elementtimes.elementcore.api.template.tileentity.lifecycle.EnergyGeneratorLifecycle;
import com.elementtimes.elementcore.api.template.tileentity.recipe.MachineRecipeHandler;
import com.elementtimes.tutorial.common.init.ElementtimesBlocks;
import net.minecraft.util.ResourceLocation;

@ModInvokeStatic("init")
public class TileGeneratorSun extends BaseTileEntity {

    public static MachineRecipeHandler RECIPE = null;

    public static void init() {
        if (RECIPE == null) {
            RECIPE = new MachineRecipeHandler(0, 0, 0, 0)
                    .newRecipe().addCost(-10).endAdd();
        }
    }

    public TileGeneratorSun() {
        super(16000, 0, 0);
        addLifeCycle(new EnergyGeneratorLifecycle<>(this));
    }

    @Override
    public ResourceLocation getBackground() {
        return null;
    }

    @Override
    public GuiSize getSize() {
        return null;
    }

    @Override
    public String getTitle() {
        return ElementtimesBlocks.sunGenerator.getLocalizedName();
    }

    @Override
    public int getGuiId() {
        return -1;
    }

    @Override
    public void update() {
        update(this);
    }

    @Override
    public MachineRecipeHandler getRecipes() {
        return RECIPE;
    }
}
