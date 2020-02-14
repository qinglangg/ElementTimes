package com.elementtimes.tutorial.common.tileentity;

import com.elementtimes.elementcore.api.annotation.tools.ModInvokeStatic;
import com.elementtimes.elementcore.api.template.tileentity.BaseTileEntity;
import com.elementtimes.elementcore.api.template.tileentity.SideHandlerType;
import com.elementtimes.elementcore.api.template.tileentity.lifecycle.FluidMachineLifecycle;
import com.elementtimes.elementcore.api.template.tileentity.recipe.IngredientPart;
import com.elementtimes.elementcore.api.template.tileentity.recipe.MachineRecipeHandler;
import com.elementtimes.tutorial.ElementTimes;
import com.elementtimes.tutorial.common.init.ElementtimesBlocks;
import com.elementtimes.tutorial.common.init.ElementtimesFluids;
import com.elementtimes.tutorial.common.init.ElementtimesGUI;
import com.elementtimes.tutorial.plugin.elementcore.JeiRecipe;
import net.minecraft.inventory.Slot;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

/**
 * 电解池
 *
 * @author luqin2007
 */
@ModInvokeStatic("init")
public class TileElectrolyticCell extends BaseTileEntity {

    @JeiRecipe.MachineRecipe(block = "elementtimes:electrolyticcell", gui = TileElectrolyticCell.class, u = 32, v = 8, w = 112, h = 93)
    public static MachineRecipeHandler RECIPE = new MachineRecipeHandler(0, 0, 1, 3);

    public static void init() {
        if (RECIPE.getMachineRecipes().isEmpty()) {
            RECIPE.newRecipe()
            		.addCost(40000)
            		.addFluidInput(IngredientPart.forFluid(ElementtimesFluids.NaCl, 2000))
            		.addFluidOutput(IngredientPart.forFluid(ElementtimesFluids.Na, 2000))
            		.addFluidOutput(IngredientPart.forFluid(ElementtimesFluids.chlorine, 1000))
            		.endAdd()         		
            		.newRecipe()
                    .addCost(10000)
                    .addFluidInput(IngredientPart.forFluid(ElementtimesFluids.HI, 2000))
                    .addFluidOutput(IngredientPart.forFluid(ElementtimesFluids.H, 1000))
                    .addFluidOutput(IngredientPart.forFluid(ElementtimesFluids.I2, 1000))
                    .endAdd()
                    .newRecipe()
                    .addCost(10000)
                    .addFluidInput(IngredientPart.forFluid(ElementtimesFluids.HBr, 2000))
                    .addFluidOutput(IngredientPart.forFluid(ElementtimesFluids.H, 1000))
                    .addFluidOutput(IngredientPart.forFluid(ElementtimesFluids.Br2, 1000))
                    .endAdd()
                    .newRecipe()
                    .addCost(10000)
                    .addFluidInput(IngredientPart.forFluid(ElementtimesFluids.HCl, 2000))
                    .addFluidOutput(IngredientPart.forFluid(ElementtimesFluids.H, 1000))
                    .addFluidOutput(IngredientPart.forFluid(ElementtimesFluids.chlorine, 1000))
                    .endAdd()
                    .newRecipe()
                    .addCost(20000)
                    .addFluidInput(IngredientPart.forFluid(ElementtimesFluids.steam, 2000))
                    .addFluidOutput(IngredientPart.forFluid(ElementtimesFluids.H, 2000))
                    .addFluidOutput(IngredientPart.forFluid(ElementtimesFluids.oxygen, 1000))
                    .endAdd()
                    .newRecipe()
                    .addCost(20000)
                    .addFluidInput(IngredientPart.forFluid(ElementtimesFluids.NaClSolutionConcentrated, 2000))
                    .addFluidOutput(IngredientPart.forFluid(ElementtimesFluids.Naoh, 2000))
                    .addFluidOutput(IngredientPart.forFluid(ElementtimesFluids.chlorine, 1000))
                    .addFluidOutput(IngredientPart.forFluid(ElementtimesFluids.H, 1000))
                    .endAdd()
                    .newRecipe()
                    .addCost(50000)
                    .addFluidInput(IngredientPart.forFluid(ElementtimesFluids.MgCl2, 1000))
                    .addFluidOutput(IngredientPart.forFluid(ElementtimesFluids.Mg, 1000))
                    .addFluidOutput(IngredientPart.forFluid(ElementtimesFluids.chlorine, 1000))
                    .endAdd()
                    .newRecipe()
                    .addCost(60000)
                    .addFluidInput(IngredientPart.forFluid(ElementtimesFluids.Al2O3_Na3AlF6, 2000))
                    .addFluidOutput(IngredientPart.forFluid(ElementtimesFluids.Al, 4000))
                    .addFluidOutput(IngredientPart.forFluid(ElementtimesFluids.oxygen, 3000))
                    .addFluidOutput(IngredientPart.forFluid(ElementtimesFluids.Na3AlF6, 2000))
                    .endAdd();
        }
    }

    public TileElectrolyticCell() {
        super(10000, 4, 4, 1, 16000, 3, 16000);
        addLifeCycle(new FluidMachineLifecycle(this));
    }

    @Override
    public ResourceLocation getBackground() {
        return new ResourceLocation(ElementTimes.MODID, "textures/gui/electrolyticcell.png");
    }

    @Override
    public GuiSize getSize() {
        return GUI_SIZE_176_204_122.copy().withTitleY(108)
                .withProcess(58, 20, 0, 204, 24, 17)
                .withProcess(63, 35, 0, 221, 16, 16)
                .withEnergy(43, 107, 24, 204, 90, 4);
    }

    @Override
    public String getTitle() {
        return ElementtimesBlocks.electrolyticCell.getLocalizedName();
    }

    @Override
    public int getGuiId() {
        return ElementtimesGUI.Machines.ElectrolyticCell.id();
    }

    @Nonnull
    @Override
    public MachineRecipeHandler getRecipes() {
        return RECIPE;
    }

    @Nonnull
    @Override
    public Slot[] getSlots() {
        return new Slot[]{
                new SlotItemHandler(getItemHandler(SideHandlerType.INPUT), 0, 36, 63),
                new SlotItemHandler(getItemHandler(SideHandlerType.INPUT), 1, 88, 63),
                new SlotItemHandler(getItemHandler(SideHandlerType.INPUT), 2, 106, 63),
                new SlotItemHandler(getItemHandler(SideHandlerType.INPUT), 3, 124, 63),
                new SlotItemHandler(getItemHandler(SideHandlerType.OUTPUT), 0, 36, 81),
                new SlotItemHandler(getItemHandler(SideHandlerType.OUTPUT), 1, 88, 81),
                new SlotItemHandler(getItemHandler(SideHandlerType.OUTPUT), 2, 106, 81),
                new SlotItemHandler(getItemHandler(SideHandlerType.OUTPUT), 3, 124, 81)
        };
    }

    @Nonnull
    @Override
    public FluidSlotInfo[] getFluids() {
        return new FluidSlotInfo[]{
                FluidSlotInfo.createInput(0, 36, 12),
                FluidSlotInfo.createOutput(0, 88, 12),
                FluidSlotInfo.createOutput(1, 106, 12),
                FluidSlotInfo.createOutput(2, 124, 12),
        };
    }

    @Override
    public int getEnergyTick() {
        return 10;
    }
}
