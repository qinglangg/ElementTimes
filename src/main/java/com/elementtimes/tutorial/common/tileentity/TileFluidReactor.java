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
import com.elementtimes.tutorial.common.init.ElementtimesItems;
import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import net.minecraft.inventory.Slot;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

/**
 * 流体反应器
 * @author luqin2007
 */
@ModInvokeStatic("init")
public class TileFluidReactor extends BaseTileEntity {

    public static MachineRecipeHandler RECIPES = null;
    public static void init() {
        if (RECIPES == null) {
            RECIPES = new MachineRecipeHandler(0, 1, 2, 3)
            		//AlCl3 + 3NH3·H2O =Al(OH)3↓+ 3NH4Cl
            		.newRecipe()
                    .addCost(1000)
                    .addFluidInput(IngredientPart.forFluid(ElementtimesFluids.AlCl3,1000))
                    .addFluidInput(IngredientPart.forFluid(ElementtimesFluids.ammonia, 3000))
                    .addFluidOutput(IngredientPart.forFluid(ElementtimesFluids.calmogastrin, 1))
                    .addFluidOutput(IngredientPart.forFluid(ElementtimesFluids.NH4Cl, 3000))
                    .endAdd()

                   //Al(OH)₃+NaOH=NaAlO₂+2H₂O
                    .newRecipe()
                    .addCost(1000)
                    .addFluidInput(IngredientPart.forFluid(ElementtimesFluids.calmogastrin,1000))
                    .addFluidInput(IngredientPart.forFluid(ElementtimesFluids.Naoh, 1000))
                    .addFluidOutput(IngredientPart.forFluid(ElementtimesFluids.NaAlO2, 1))
                    .addFluidOutput(IngredientPart.forFluid(FluidRegistry.WATER, 2000))
                    .endAdd()

                    //NaAlO2+HCl+H2O=Al(OH)3+NaCl
                    .newRecipe()
                    .addCost(1000)
                    .addFluidInput(IngredientPart.forFluid(ElementtimesFluids.NaAlO2,1000))
                    .addFluidInput(IngredientPart.forFluid(ElementtimesFluids.HCl, 1000))
                    .addFluidOutput(IngredientPart.forFluid(ElementtimesFluids.calmogastrin, 1))
                    .addFluidOutput(IngredientPart.forFluid(ElementtimesFluids.NaClSolutionDilute, 1000))
                    .endAdd()

                    //Al(OH)3 + 3HCl = AlCl3 + 3H20
                    .newRecipe()
                    .addCost(1000)
                    .addFluidInput(IngredientPart.forFluid(ElementtimesFluids.calmogastrin,1000))
                    .addFluidInput(IngredientPart.forFluid(ElementtimesFluids.HCl, 3000))
                    .addFluidOutput(IngredientPart.forFluid(ElementtimesFluids.AlCl3, 1))
                    .addFluidOutput(IngredientPart.forFluid(FluidRegistry.WATER, 3000))
                    .endAdd()


            		//HCl + NH3 =NH4Cl
                    .newRecipe()
                    .addCost(1000)
                    .addFluidInput(IngredientPart.forFluid(ElementtimesFluids.HCl,1000))
                    .addFluidInput(IngredientPart.forFluid(ElementtimesFluids.ammonia, 1000))
                    .addFluidOutput(IngredientPart.forFluid(ElementtimesFluids.NH4Cl, 1000))
                    .endAdd()



            		//H3(AlF6)+3naoh=Na3AlF6+3H2O
                    .newRecipe()
                    .addCost(20000)
                    .addFluidInput(IngredientPart.forFluid(ElementtimesFluids.FluoroaluminicAcid,1000))
                    .addFluidInput(IngredientPart.forFluid(ElementtimesFluids.Naoh, 3000))
                    .addItemOutput(IngredientPart.forItem(ElementtimesItems.Na3AlF6, 1))
                    .addFluidOutput(IngredientPart.forFluid(FluidRegistry.WATER, 3000))
                    .endAdd()

            		//6HF+Al(OH)3 = H3(AlF6) + 3H2O
                    .newRecipe()
                    .addCost(2000)
                    .addFluidInput(IngredientPart.forFluid(ElementtimesFluids.hf, 6000))
                    .addFluidInput(IngredientPart.forFluid(ElementtimesFluids.calmogastrin,1000))
                    .addFluidOutput(IngredientPart.forFluid(ElementtimesFluids.FluoroaluminicAcid , 1000))
                    .addFluidOutput(IngredientPart.forFluid(FluidRegistry.WATER, 3000))
                    .endAdd()


              		//CO+H2O(g)=co2+H2
                    .newRecipe()
                    .addCost(5000)
                    .addFluidInput(IngredientPart.forFluid(ElementtimesFluids.co, 1000))
                    .addFluidInput(IngredientPart.forFluid(ElementtimesFluids.waterDistilled,1000))
                    .addFluidOutput(IngredientPart.forFluid(ElementtimesFluids.co2, 1000))
                    .addFluidOutput(IngredientPart.forFluid(ElementtimesFluids.H, 1000))
                    .endAdd()


                    //CO+3H2=CH4+H20(g)
                    .newRecipe()
                    .addCost(1000)
                    .addFluidInput(IngredientPart.forFluid(ElementtimesFluids.co, 1000))
                    .addFluidInput(IngredientPart.forFluid(ElementtimesFluids.H,3000))
                    .addFluidOutput(IngredientPart.forFluid(ElementtimesFluids.methane, 1000))
                    .addFluidOutput(IngredientPart.forFluid(ElementtimesFluids.waterDistilled, 1000))
                    .endAdd()

            		//精致硅单质获得
                    .newRecipe()
                    .addCost(5000)
                    .addFluidInput(IngredientPart.forFluid(ElementtimesFluids.Sicl4, 1000))
                    .addFluidInput(IngredientPart.forFluid(ElementtimesFluids.H,2000))
                    .addFluidOutput(IngredientPart.forFluid(ElementtimesFluids.HCl, 4000))
                    .addItemOutput(IngredientPart.forItem(ElementtimesItems.Silicon , 1))
                    .endAdd()



                    .newRecipe()
                    .addCost(10000)
                    .addFluidInput(IngredientPart.forFluid(ElementtimesFluids.H,1000))
                    .addFluidInput(IngredientPart.forFluid(ElementtimesFluids.acetylene, 1000))
                    .addFluidOutput(IngredientPart.forFluid(ElementtimesFluids.ethylene, 2000))
                    .endAdd()
                    .newRecipe()
                    .addCost(10000)
                    .addFluidInput(IngredientPart.forFluid(ElementtimesFluids.H,1000))
                    .addFluidInput(IngredientPart.forFluid(ElementtimesFluids.ethylene, 1000))
                    .addFluidOutput(IngredientPart.forFluid(ElementtimesFluids.ethane, 2000))
                    .endAdd()
                    .newRecipe()
                    .addCost(10000)
                    .addFluidInput(IngredientPart.forFluid(ElementtimesFluids.waterDistilled,1000))
                    .addFluidInput(IngredientPart.forFluid(ElementtimesFluids.ethylene, 1000))
                    .addFluidOutput(IngredientPart.forFluid(ElementtimesFluids.ethanol, 2000))
                    .endAdd()


                    //NH3
                    .newRecipe()
                    .addCost(10000)
                    .addFluidInput(IngredientPart.forFluid(ElementtimesFluids.H,3000))
                    .addFluidInput(IngredientPart.forFluid(ElementtimesFluids.nitrogen, 1000))
                    .addFluidOutput(IngredientPart.forFluid(ElementtimesFluids.ammonia, 1000))
                    .endAdd()


                    //Co2+Ca(OH)2=CaCO3+H2O
                    .newRecipe()
                    .addCost(0)
                    .addFluidInput(IngredientPart.forFluid(ElementtimesFluids.co2, Fluid.BUCKET_VOLUME))
                    .addFluidInput(IngredientPart.forFluid(ElementtimesFluids.calciumHydroxide, Fluid.BUCKET_VOLUME))
                    .addItemOutput(IngredientPart.forItem(ElementtimesItems.calciumCarbonate , 1))
                    .addFluidOutput(IngredientPart.forFluid(FluidRegistry.WATER, Fluid.BUCKET_VOLUME))
                    .endAdd();
        }
    }

    public TileFluidReactor() {
        super(10000, 5, 6, 2, 16000, 3, 16000);
        addLifeCycle(new FluidMachineLifecycle(this, 2, 3));
        markBucketInput(0, 1, 2, 3, 4);
    }

    @Override
    public ResourceLocation getBackground() {
        return new ResourceLocation(ElementTimes.MODID, "textures/gui/fluidreactor.png");
    }

    @Override
    public GuiSize getSize() {
        return GUI_SIZE_176_204_122.copy().withTitleY(100).withProcess(58, 30).withEnergy(43, 108);
    }

    @Override
    public String getTitle() {
        return ElementtimesBlocks.fluidReactor.getLocalizedName();

    }

    @Override
    public int getGuiId() {
        return ElementtimesGUI.Machines.FluidReactor.id();
    }

    @Nonnull
    @Override
    public MachineRecipeHandler getRecipes() {
        return RECIPES;
    }

    @Nonnull
    @Override
    public Slot[] getSlots() {
        return new Slot[] {
                new SlotItemHandler(getItemHandler(SideHandlerType.INPUT), 0, 88, 30),
                new SlotItemHandler(getItemHandler(SideHandlerType.INPUT), 1, 18, 66),
                new SlotItemHandler(getItemHandler(SideHandlerType.INPUT), 2, 36, 66),
                new SlotItemHandler(getItemHandler(SideHandlerType.INPUT), 3, 106, 66),
                new SlotItemHandler(getItemHandler(SideHandlerType.INPUT), 4, 124, 66),
                new SlotItemHandler(getItemHandler(SideHandlerType.OUTPUT), 0, 18, 84),
                new SlotItemHandler(getItemHandler(SideHandlerType.OUTPUT), 1, 36, 84),
                new SlotItemHandler(getItemHandler(SideHandlerType.OUTPUT), 2, 106, 84),
                new SlotItemHandler(getItemHandler(SideHandlerType.OUTPUT), 3, 124, 84),
                new SlotItemHandler(getItemHandler(SideHandlerType.OUTPUT), 4, 142, 84),
                new SlotItemHandler(getItemHandler(SideHandlerType.OUTPUT), 5, 142, 66)
        };
    }

    @Nonnull
    @Override
    public FluidSlotInfo[] getFluids() {
        return new FluidSlotInfo[] {
                FluidSlotInfo.createInput(0, 18, 15),
                FluidSlotInfo.createInput(1, 36, 15),
                FluidSlotInfo.createOutput(0, 106, 15),
                FluidSlotInfo.createOutput(1, 124, 15),
                FluidSlotInfo.createOutput(2, 142, 15)
        };
    }

    @Override
    public int getEnergyTick() {
        return 10;
    }

    @Override
    public void update() {
        update(this);
    }
}
