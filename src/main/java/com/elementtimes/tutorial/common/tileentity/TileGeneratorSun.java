package com.elementtimes.tutorial.common.tileentity;

import com.elementtimes.elementcore.api.template.tileentity.BaseTileEntity;
import com.elementtimes.elementcore.api.template.tileentity.interfaces.IMachineLifecycle;
import com.elementtimes.elementcore.api.template.tileentity.lifecycle.EnergyGeneratorLifecycle;
import com.elementtimes.elementcore.api.template.tileentity.recipe.MachineRecipeHandler;
import com.elementtimes.tutorial.ElementTimes;
import com.elementtimes.tutorial.common.init.ElementtimesBlocks;
import net.minecraft.util.ResourceLocation;

public class TileGeneratorSun extends BaseTileEntity {

    public static MachineRecipeHandler RECIPE = new MachineRecipeHandler(0, 0, 0, 0).newRecipe().addCost(-10).endAdd();

    public TileGeneratorSun() {
        super(16000, 0, 0);
        addLifeCycle(new EnergyGeneratorLifecycle<>(this));
        addLifeCycle(new IMachineLifecycle() {
            @Override
            public boolean onCheckStart() {
                return world != null && world.isDaytime();
            }
        });
    }

    @Override
    public ResourceLocation getBackground() {
        return new ResourceLocation(ElementTimes.MODID, "textures/gui/solargenerator.png");
    }

    @Override
    public GuiSize getSize() {
        return GUI_SIZE_176_166_84.copy().withEnergy(43, 46, 0, 166, 90, 4);
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
