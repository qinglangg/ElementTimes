package com.elementtimes.tutorial.common.tileentity;

import com.elementtimes.elementcore.api.annotation.tools.ModInvokeStatic;
import com.elementtimes.elementcore.api.template.tileentity.BaseTileEntity;
import com.elementtimes.elementcore.api.template.tileentity.SideHandlerType;
import com.elementtimes.elementcore.api.template.tileentity.lifecycle.EnergyGeneratorLifecycle;
import com.elementtimes.elementcore.api.template.tileentity.lifecycle.FluidMachineLifecycle;
import com.elementtimes.elementcore.api.template.tileentity.recipe.IngredientPart;
import com.elementtimes.elementcore.api.template.tileentity.recipe.MachineRecipeHandler;
import com.elementtimes.tutorial.ElementTimes;
import com.elementtimes.tutorial.common.init.ElementtimesBlocks;
import com.elementtimes.tutorial.common.init.ElementtimesFluids;
import com.elementtimes.tutorial.common.init.ElementtimesGUI;
import net.minecraft.inventory.Slot;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

@ModInvokeStatic("init")
public class TileGeneratorFluid extends BaseTileEntity {

    public static MachineRecipeHandler RECIPE = null;

    public static void init() {
        if (RECIPE == null) {
            RECIPE = new MachineRecipeHandler(0, 0, 1, 0)
                    .newRecipe().addCost(-1000).addFluidInput(IngredientPart.forFluid(ElementtimesFluids.ethanol, 1000)).endAdd();
        }
    }

    public TileGeneratorFluid() {
        super(16000, 1, 1, 1, 16000, 0, 0);
        addLifeCycle(new EnergyGeneratorLifecycle<>(this));
        addLifeCycle(new FluidMachineLifecycle(this, 1, 0));
    }

    @Override
    public ResourceLocation getBackground() {
        return new ResourceLocation(ElementTimes.MODID, "textures/gui/fluidgenerator.png");
    }

    @Override
    public GuiSize getSize() {
        return new GuiSize().withSize(176, 164, 8, 82).withTitleY(71).withEnergy(45, 66, 24, 164, 86, 4);
    }

    @Nonnull
    @Override
    public FluidSlotInfo[] getFluids() {
        return new FluidSlotInfo[] {
                FluidSlotInfo.createInputHorizontal(0, 64, 19)
        };
    }

    @Override
    public MachineRecipeHandler getRecipes() {
        return RECIPE;
    }

    @Nonnull
    @Override
    public Slot[] getSlots() {
        return new Slot[] {
                new SlotItemHandler(getItemHandler(SideHandlerType.INPUT), 0, 70, 40),
                new SlotItemHandler(getItemHandler(SideHandlerType.OUTPUT), 0, 88, 40)
        };
    }

    @Override
    public String getTitle() {
        return ElementtimesBlocks.fluidGenerator.getLocalizedName();
    }

    @Override
    public int getGuiId() {
        return ElementtimesGUI.Machines.FluidGenerator.id();
    }
}
