package com.elementtimes.tutorial.common.tileentity;

import com.elementtimes.elementcore.api.template.tileentity.recipe.MachineRecipeHandler;
import com.elementtimes.tutorial.common.init.ElementtimesBlocks;
import com.elementtimes.tutorial.common.init.ElementtimesGUI;
import com.elementtimes.tutorial.plugin.elementcore.JeiRecipe;

import javax.annotation.Nonnull;

/**
 * 虽然太阳能分解机继承固液反应器从逻辑上有点不太对，但因 GUI 相同，可通用方法很多
 * @author luqin2007
 */
public class TileSolarDecomposer extends TileSolidFluidReactor {

    @JeiRecipe.MachineRecipe(block = "elementtimes:solardecomposer", gui = TileSolarDecomposer.class, u = 17, v = 10, w = 141, h = 55)
    public static MachineRecipeHandler RECIPE = new MachineRecipeHandler(1, 2, 1, 2);

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
