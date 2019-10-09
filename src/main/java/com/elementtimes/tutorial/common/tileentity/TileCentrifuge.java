package com.elementtimes.tutorial.common.tileentity;

import com.elementtimes.elementcore.api.annotation.ModInvokeStatic;
import com.elementtimes.elementcore.api.template.tileentity.BaseTileEntity;
import com.elementtimes.elementcore.api.template.tileentity.SideHandlerType;
import com.elementtimes.elementcore.api.template.tileentity.lifecycle.FluidMachineLifecycle;
import com.elementtimes.elementcore.api.template.tileentity.recipe.IngredientPart;
import com.elementtimes.elementcore.api.template.tileentity.recipe.MachineRecipeHandler;
import com.elementtimes.tutorial.ElementTimes;
import com.elementtimes.tutorial.common.init.ElementtimesBlocks;
import com.elementtimes.tutorial.common.init.ElementtimesFluids;
import com.elementtimes.tutorial.common.init.ElementtimesGUI;
import net.minecraft.inventory.Slot;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

/**
 * 离心机
 * @author luqin2007
 */
@ModInvokeStatic("init")
public class TileCentrifuge extends BaseTileEntity {

    public static MachineRecipeHandler RECIPE = null;

    public static void init() {
        if (RECIPE == null) {
            RECIPE = new MachineRecipeHandler(0, 0, 1, 5)
                    .newRecipe()
                    .addCost(10000)
                    .addFluidInput(IngredientPart.forFluid(ElementtimesFluids.air, 1000))
                    .addFluidOutput(IngredientPart.forFluid(ElementtimesFluids.nitrogen, 780))
                    .addFluidOutput(IngredientPart.forFluid(ElementtimesFluids.oxygen, 210))
                    .addFluidOutput(IngredientPart.forFluid(FluidRegistry.WATER, 1))
                    .addFluidOutput(IngredientPart.forFluid(ElementtimesFluids.rareGases, 8))
                    .addFluidOutput(IngredientPart.forFluid(ElementtimesFluids.co2, 1))
                    .endAdd()
                    .newRecipe()
                    .addCost(10000)
                    .addFluidInput(IngredientPart.forFluid(ElementtimesFluids.seawater, 1000))
                    .addFluidOutput(IngredientPart.forFluid(ElementtimesFluids.NaClSolutionDilute, 780))
                    .addFluidOutput(IngredientPart.forFluid(ElementtimesFluids.MgCl2, 210))
                    .addFluidOutput(IngredientPart.forFluid(ElementtimesFluids.AlCl3, 10))
                    .endAdd();
        }
    }

    public TileCentrifuge() {
        super(100000, 6, 6, 1, 16000, 5, 16000);
        addLifeCycle(new FluidMachineLifecycle(this));
    }

    @Nonnull
    @Override
    public MachineRecipeHandler getRecipes() {
        return RECIPE;
    }

    @Nonnull
    @Override
    public Slot[] getSlots() {
        return new Slot[] {
                new SlotItemHandler(getItemHandler(SideHandlerType.INPUT), 0, 18, 66),
                new SlotItemHandler(getItemHandler(SideHandlerType.INPUT), 1, 70, 66),
                new SlotItemHandler(getItemHandler(SideHandlerType.INPUT), 2, 88, 66),
                new SlotItemHandler(getItemHandler(SideHandlerType.INPUT), 3, 106, 66),
                new SlotItemHandler(getItemHandler(SideHandlerType.INPUT), 4, 124, 66),
                new SlotItemHandler(getItemHandler(SideHandlerType.INPUT), 5, 142, 66),
                new SlotItemHandler(getItemHandler(SideHandlerType.OUTPUT), 0, 18, 84),
                new SlotItemHandler(getItemHandler(SideHandlerType.OUTPUT), 1, 70, 84),
                new SlotItemHandler(getItemHandler(SideHandlerType.OUTPUT), 2, 88, 84),
                new SlotItemHandler(getItemHandler(SideHandlerType.OUTPUT), 3, 106, 84),
                new SlotItemHandler(getItemHandler(SideHandlerType.OUTPUT), 4, 124, 84),
                new SlotItemHandler(getItemHandler(SideHandlerType.OUTPUT), 5, 142, 84)
        };
    }

    @Nonnull
    @Override
    public FluidSlotInfo[] getFluids() {
        return new FluidSlotInfo[] {
                FluidSlotInfo.createInput(0, 18, 15),
                FluidSlotInfo.createOutput(0, 70, 15),
                FluidSlotInfo.createOutput(1, 88, 15),
                FluidSlotInfo.createOutput(2, 106, 15),
                FluidSlotInfo.createOutput(3, 124, 15),
                FluidSlotInfo.createOutput(4, 142, 15),
        };
    }

    @Override
    public int getGuiId() {
        return ElementtimesGUI.Machines.Centrifuge.id();
    }

    @Override
    public ResourceLocation getBackground() {
        return new ResourceLocation(ElementTimes.MODID, "textures/gui/centrifuge.png");
    }

    @Override
    public GuiSize getSize() {
        return GUI_SIZE_176_204_122.copy().withProcess(40, 30).withEnergy(43, 107).withTitleY(112);
    }

    @Override
    public String getTitle() {
        return ElementtimesBlocks.centrifuge.getLocalizedName();

    }

    @Override
    public void update() {
        update(this);
    }
}
