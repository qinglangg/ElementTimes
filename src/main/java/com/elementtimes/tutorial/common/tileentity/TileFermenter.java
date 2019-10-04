package com.elementtimes.tutorial.common.tileentity;

import com.elementtimes.elementcore.api.template.tileentity.BaseTileEntity;
import com.elementtimes.elementcore.api.template.tileentity.lifecycle.FluidMachineLifecycle;
import com.elementtimes.elementcore.api.template.tileentity.recipe.MachineRecipeHandler;
import com.elementtimes.tutorial.common.init.ElementtimesGUI;
import net.minecraft.util.ResourceLocation;

public class TileFermenter extends BaseTileEntity {

    public static MachineRecipeHandler RECIPE = null;

    public static void init() {
        if (RECIPE == null) {
            RECIPE = new MachineRecipeHandler(2, 1, 1, 3);
        }
    }

    public TileFermenter() {
        super(10000, 6, 5, 1, 16000, 3, 16000);
        addLifeCycle(new FluidMachineLifecycle(this, 1, 3));
    }

    @Override
    public ResourceLocation getBackground() {
        return new ResourceLocation("elementtimes", "textures/gui/fermenter.png");
    }

    @Override
    public GuiSize getSize() {
        return new GuiSize().withSize(210, 176, 7, 127).withTitleY(115)
                .withProcess(63, 43).withEnergy(43, 117);
    }

    @Override
    public String getTitle() {
        return null;
    }

    @Override
    public int getGuiId() {
        return ElementtimesGUI.Machines.Fermenter.id();
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
