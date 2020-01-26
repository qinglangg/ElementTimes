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
import com.elementtimes.tutorial.plugin.elementcore.JeiRecipe;
import net.minecraft.inventory.Slot;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

/**
 * 流体加热器
 * @author luqin2007
 */
@ModInvokeStatic("init")
public class TileFluidHeater extends BaseTileEntity {

    @JeiRecipe.MachineRecipe(block = "elementtimes:fluidheater", gui = TileFluidHeater.class, u = 4, v = 11, w = 168, h = 92)
    public static MachineRecipeHandler RECIPE = new MachineRecipeHandler(0, 0, 1, 1);

    public static void init() {
        if (RECIPE.getMachineRecipes().isEmpty()) {
            RECIPE.newRecipe()
                    .addCost(10000)
                    .addFluidInput(IngredientPart.forFluid(ElementtimesFluids.NaClSolutionConcentrated, Fluid.BUCKET_VOLUME))
                    .addFluidOutput(IngredientPart.forFluid(ElementtimesFluids.NaCl, Fluid.BUCKET_VOLUME))
                    .endAdd()
                    .newRecipe()
                    .addCost(10000)
                    .addFluidInput(IngredientPart.forFluid(ElementtimesFluids.MagnesiumChlorideSolution, Fluid.BUCKET_VOLUME))
                    .addFluidOutput(IngredientPart.forFluid(ElementtimesFluids.MgCl2, Fluid.BUCKET_VOLUME))
                    .endAdd()
                    .newRecipe()
                    .addCost(1000)
                    .addFluidInput(IngredientPart.forFluid(FluidRegistry.WATER, Fluid.BUCKET_VOLUME))
                    .addFluidOutput(IngredientPart.forFluid(ElementtimesFluids.steam, Fluid.BUCKET_VOLUME))
                    .endAdd()
                    //蒸发皿完成后移到蒸发皿的内容
                    .newRecipe()
                    .addCost(10000)
                    .addFluidInput(IngredientPart.forFluid(ElementtimesFluids.NaClSolutionDilute, Fluid.BUCKET_VOLUME))
                    .addFluidOutput(IngredientPart.forFluid(ElementtimesFluids.NaClSolutionConcentrated, Fluid.BUCKET_VOLUME))
                    .endAdd()
                    //蒸发皿完成后移到蒸发皿的内容
                    .newRecipe()
                    .addCost(10000)
                    .addFluidInput(IngredientPart.forFluid(ElementtimesFluids.DiluteHydrochloricAcid, Fluid.BUCKET_VOLUME))
                    .addFluidOutput(IngredientPart.forFluid(ElementtimesFluids.ConcentratedHydrochloricAcid, Fluid.BUCKET_VOLUME))
                    .endAdd()
                    //蒸发皿完成后移到蒸发皿的内容
                    .newRecipe()
                    .addCost(10000)
                    .addFluidInput(IngredientPart.forFluid(ElementtimesFluids.ConcentratedHydrochloricAcid, Fluid.BUCKET_VOLUME))
                    .addFluidOutput(IngredientPart.forFluid(ElementtimesFluids.HCl, Fluid.BUCKET_VOLUME))
                    .endAdd()
                    //蒸发皿完成后移到蒸发皿的内容
                    .newRecipe()
                    .addCost(10000)
                    .addFluidInput(IngredientPart.forFluid(ElementtimesFluids.diluteSulfuricAcid, Fluid.BUCKET_VOLUME))
                    .addFluidOutput(IngredientPart.forFluid(ElementtimesFluids.concentratedSulfuricAcid, Fluid.BUCKET_VOLUME))
                    .endAdd()
                    //蒸发皿完成后移到蒸发皿的内容
                    .newRecipe()
                    .addCost(10000)
                    .addFluidInput(IngredientPart.forFluid(ElementtimesFluids.concentratedSulfuricAcid, Fluid.BUCKET_VOLUME))
                    .addFluidOutput(IngredientPart.forFluid(ElementtimesFluids.h2so4, Fluid.BUCKET_VOLUME))
                    .endAdd()
                    
                    
                    
                    
                    
                    
                    ;
        }
    }

    public TileFluidHeater() {
        super(100000, 2, 2, 1, 16000, 1, 16000);
        addLifeCycle(new FluidMachineLifecycle(this));
    }

    @Override
    public ResourceLocation getBackground() {
        return new ResourceLocation(ElementTimes.MODID, "textures/gui/fluidheater.png");
    }

    @Override
    public GuiSize getSize() {
        return GUI_SIZE_176_204_122.copy().withTitleY(100)
                .withProcess(53, 18, 0, 204, 70, 19)
                .withProcess(57, 52, 0, 223, 62, 15)
                .withEnergy(45, 89, 0, 237, 90, 4);
    }

    @Override
    public String getTitle() {
        return ElementtimesBlocks.fluidHeater.getLocalizedName();
    }

    @Override
    public int getGuiId() {
        return ElementtimesGUI.Machines.FluidHeater.id();
    }

    @Nonnull
    @Override
    public MachineRecipeHandler getRecipes() {
        return RECIPE;
    }

    @Override
    public int getEnergyTick() {
        return 10;
    }

    @Nonnull
    @Override
    public FluidSlotInfo[] getFluids() {
        return new FluidSlotInfo[] {
                FluidSlotInfo.createInput(0, 17, 25),
                FluidSlotInfo.createOutput(0, 143, 25)
        };
    }

    @Nonnull
    @Override
    public Slot[] getSlots() {
        return new Slot[] {
                new SlotItemHandler(getItemHandler(SideHandlerType.INPUT), 0, 8, 83),
                new SlotItemHandler(getItemHandler(SideHandlerType.INPUT), 1, 136, 83),
                new SlotItemHandler(getItemHandler(SideHandlerType.OUTPUT), 0, 26, 83),
                new SlotItemHandler(getItemHandler(SideHandlerType.OUTPUT), 1, 152, 83)
        };
    }
}
