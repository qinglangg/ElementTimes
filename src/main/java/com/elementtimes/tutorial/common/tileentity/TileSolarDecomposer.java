package com.elementtimes.tutorial.common.tileentity;

import com.elementtimes.elementcore.api.annotation.ModInvokeStatic;
import com.elementtimes.elementcore.api.template.tileentity.recipe.MachineRecipeHandler;
import com.elementtimes.tutorial.common.init.ElementtimesBlocks;
import com.elementtimes.tutorial.common.init.ElementtimesGUI;

import javax.annotation.Nonnull;

/**
 * 虽然太阳能分解机继承固液反应器从逻辑上有点不太对，但因 GUI 相同，可通用方法很多
 * @author luqin2007
 */
@ModInvokeStatic("init")
public class TileSolarDecomposer extends TileSolidFluidReactor {

    public static MachineRecipeHandler RECIPE = null;

    public static void init() {
        if (RECIPE == null) {
            RECIPE = new MachineRecipeHandler(1, 2, 1, 2);
        }
    }

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
