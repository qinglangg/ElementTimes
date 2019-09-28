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
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Slot;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

@ModInvokeStatic("init")
public class TileSolidReactor extends BaseTileEntity {

    public static MachineRecipeHandler RECIPE = null;

    public static void init() {
        if (RECIPE == null) {
            RECIPE = new MachineRecipeHandler(2, 2, 0, 1)
                    //CaO+SiO2=CaSiO3
                    .newRecipe()
                    .addCost(5000)
                    .addItemInput(IngredientPart.forItem(ElementtimesItems.calciumOxide,1))
                    .addItemInput(IngredientPart.forItem(Blocks.SAND, 1))
                    .addItemOutput(IngredientPart.forItem(ElementtimesBlocks.cement , 1))
                    .endAdd()
            		//CaCO3+SiO2=CaSiO3+co2
            		.newRecipe()
                    .addCost(5000)
                    .addItemInput(IngredientPart.forItem(ElementtimesItems.calciumCarbonate,1))
                    .addItemInput(IngredientPart.forItem(Blocks.SAND, 1))
                    .addItemOutput(IngredientPart.forItem(ElementtimesBlocks.cement , 1))
                    .addFluidOutput(IngredientPart.forFluid(ElementtimesFluids.co2, Fluid.BUCKET_VOLUME))
                    .endAdd()



            		//CaO+3C=CaC2+CO
                    .newRecipe()
                    .addCost(10000)
                    .addItemInput(IngredientPart.forItem(ElementtimesItems.calciumOxide,1))
                    .addItemInput(IngredientPart.forItem(Items.COAL, 3))
                    .addItemOutput(IngredientPart.forItem(ElementtimesItems.calciumacetylide , 1))
                    .addFluidOutput(IngredientPart.forFluid(ElementtimesFluids.co, Fluid.BUCKET_VOLUME))
                    .endAdd()


                    //铁矿石获得铁
                    .newRecipe()
                    .addCost(10000)
                    .addItemInput(IngredientPart.forItem(Blocks.IRON_ORE,1))
                    .addItemInput(IngredientPart.forItem(Items.COAL, 3))
                    .addItemOutput(IngredientPart.forItem(Items.IRON_INGOT, 2))
                    .addItemOutput(IngredientPart.forItem(ElementtimesItems.slag, 2))
                    .addFluidOutput(IngredientPart.forFluid(ElementtimesFluids.co,3000))
                    .endAdd()

                    .newRecipe()
                    .addCost(10000)
                    .addItemInput(IngredientPart.forItem(ElementtimesItems.Fe2O3,1))
                    .addItemInput(IngredientPart.forItem(Items.COAL, 3))
                    .addItemOutput(IngredientPart.forItem(Items.IRON_INGOT, 2))
                    .addItemOutput(IngredientPart.forItem(ElementtimesItems.slag, 1))
                    .addFluidOutput(IngredientPart.forFluid(ElementtimesFluids.co,3000))
                    .endAdd()

                  //铜矿石获得铜
                    .newRecipe()
                    .addCost(10000)
                    .addItemInput(IngredientPart.forItem("oreCopper",1))
                    .addItemInput(IngredientPart.forItem(Items.COAL, 1))
                    .addItemOutput(IngredientPart.forItem(ElementtimesItems.copper, 1))
                    .addItemOutput(IngredientPart.forItem(ElementtimesItems.slag, 1))
                    .addFluidOutput(IngredientPart.forFluid(ElementtimesFluids.co, Fluid.BUCKET_VOLUME))
                    .endAdd()

                    .newRecipe()
                    .addCost(10000)
                    .addItemInput(IngredientPart.forItem(ElementtimesItems.copperPowder,1))
                    .addItemInput(IngredientPart.forItem(Items.COAL, 1))
                    .addItemOutput(IngredientPart.forItem(ElementtimesItems.copper, 1))
                    .addItemOutput(IngredientPart.forItem(ElementtimesItems.slag, 1))
                    .addFluidOutput(IngredientPart.forFluid(ElementtimesFluids.co, Fluid.BUCKET_VOLUME))
                    .endAdd()


                    //钢锭
                    .newRecipe()
                    .addCost(10000)
                    .addItemInput(IngredientPart.forItem(Items.IRON_INGOT,2))
                    .addItemInput(IngredientPart.forItem(Items.COAL, 1))
                    .addItemOutput(IngredientPart.forItem(ElementtimesItems.steelIngot, 1))
                    .endAdd()

                    .newRecipe()
                    .addCost(100000)
                    .addItemInput(IngredientPart.forItem(Items.COAL,1))
                    .addItemInput(IngredientPart.forItem(Blocks.SAND,1))
                    .addItemOutput(IngredientPart.forItem(ElementtimesItems.coarseSilicon,1))
                    .addFluidOutput(IngredientPart.forFluid(ElementtimesFluids.co2, Fluid.BUCKET_VOLUME))
                    .endAdd()

                    .newRecipe()
                    .addCost(100000)
                    .addItemInput(IngredientPart.forItem(ElementtimesItems.Silicon,1))
                    .addItemInput(IngredientPart.forItem(ElementtimesItems.diamondIngot,1))
                    .addItemOutput(IngredientPart.forItem(ElementtimesItems.SiC,1))
                    .endAdd()
                    //铀锭的获得
                    .newRecipe()
                    .addCost(10000)
                    .addItemInput(IngredientPart.forItem(ElementtimesItems.UO2,1))
                    .addItemInput(IngredientPart.forItem(Items.COAL, 1))
                    .addFluidOutput(IngredientPart.forFluid(ElementtimesFluids.co2, Fluid.BUCKET_VOLUME))
                    .addItemOutput(IngredientPart.forItem(ElementtimesItems.uranium, 1))
                    .endAdd();
        }
    }

    public TileSolidReactor() {
        super(100000, 3, 3, 0, 0, 1, 16000);
        addLifeCycle(new FluidMachineLifecycle(this));
    }

    @Override
    public ResourceLocation getBackground() {
        return new ResourceLocation(ElementTimes.MODID, "textures/gui/solidreactor.png");
    }

    @Override
    public GuiSize getSize() {
        return GUI_SIZE_176_204_122.copy().withTitleY(85).withProcess(75, 42).withEnergy(43, 108);
    }

    @Override
    public String getTitle() {
        return ElementtimesBlocks.solidReactor.getLocalizedName();
    }

    @Override
    public int getGuiId() {
        return ElementtimesGUI.Machines.SolidReactor.id();
    }

    @Nonnull
    @Override
    public Slot[] getSlots() {
        return new Slot[] {
                new SlotItemHandler(getItemHandler(SideHandlerType.INPUT), 0, 35, 42),
                new SlotItemHandler(getItemHandler(SideHandlerType.INPUT), 1, 53, 42),
                new SlotItemHandler(getItemHandler(SideHandlerType.INPUT), 2, 126, 62),
                new SlotItemHandler(getItemHandler(SideHandlerType.OUTPUT), 0, 105, 33),
                new SlotItemHandler(getItemHandler(SideHandlerType.OUTPUT), 1, 105, 51),
                new SlotItemHandler(getItemHandler(SideHandlerType.OUTPUT), 2, 126, 80)
        };
    }

    @Nonnull
    @Override
    public FluidSlotInfo[] getFluids() {
        return new FluidSlotInfo[] {
                FluidSlotInfo.createOutput(0, 126, 11)
        };
    }

    @Nonnull
    @Override
    public MachineRecipeHandler getRecipes() {
        return RECIPE;
    }

    @Override
    public void update() {
        update(this);
    }
}
