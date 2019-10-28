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
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

/**
 * 固液反应器
 *
 * @author luqin2007
 */
@ModInvokeStatic("init")
public class TileSolidFluidReactor extends BaseTileEntity {

    public static MachineRecipeHandler RECIPE = null;

    public static void init() {
        if (RECIPE == null) {
            RECIPE = new MachineRecipeHandler(1, 2, 1, 2)
                    //铁水变钢水
                    .newRecipe()
                    .addCost(10000)
                    .addFluidInput(IngredientPart.forFluid(ElementtimesFluids.Fe, 2000))
                    .addItemInput(IngredientPart.forItem(new ItemStack(Items.COAL, 1, 0)))
                    .addFluidOutput(IngredientPart.forFluid(ElementtimesFluids.steel, 2000))
                    .endAdd()
                    
                    //AL2O3+6HCL=2ALCL3+3H2O
                    .newRecipe()
                    .addCost(2000)
                    .addItemInput(IngredientPart.forItem(new ItemStack(Items.DYE, 1, 4)))
                    .addFluidInput(IngredientPart.forFluid(ElementtimesFluids.DiluteHydrochloricAcid, 6000))
                    .addFluidOutput(IngredientPart.forFluid(ElementtimesFluids.AlCl3, 2000))
                    .addFluidOutput(IngredientPart.forFluid(FluidRegistry.WATER, 3000))
                    .endAdd()
                    .newRecipe()
                    .addCost(2000)
                    .addItemInput(IngredientPart.forItem(Items.REDSTONE, 1))
                    .addFluidInput(IngredientPart.forFluid(ElementtimesFluids.DiluteHydrochloricAcid, 6000))
                    .addFluidOutput(IngredientPart.forFluid(ElementtimesFluids.AlCl3, 2000))
                    .addFluidOutput(IngredientPart.forFluid(FluidRegistry.WATER, 3000))
                    .endAdd()
                    //Al2O3 + 2NaOH== 2NaAlO2(偏铝酸钠) + H2O
                    .newRecipe()
                    .addCost(2000)
                    .addItemInput(IngredientPart.forItem(new ItemStack(Items.DYE, 2, 4)))
                    .addFluidInput(IngredientPart.forFluid(ElementtimesFluids.Naoh, 2000))
                    .addFluidOutput(IngredientPart.forFluid(ElementtimesFluids.NaAlO2, 2000))
                    .addFluidOutput(IngredientPart.forFluid(FluidRegistry.WATER, 1000))
                    .endAdd()
                    .newRecipe()
                    .addCost(2000)
                    .addItemInput(IngredientPart.forItem(Items.REDSTONE, 1))
                    .addFluidInput(IngredientPart.forFluid(ElementtimesFluids.Naoh, 2000))
                    .addFluidOutput(IngredientPart.forFluid(ElementtimesFluids.NaAlO2, 2000))
                    .addFluidOutput(IngredientPart.forFluid(FluidRegistry.WATER, 1000))
                    .endAdd()

                    //CaF2+H2SO4==CaSO4+2HF
                    .newRecipe()
                    .addCost(2000)
                    .addItemInput(IngredientPart.forItem(ElementtimesBlocks.calciumFluoride, 1))
                    .addFluidInput(IngredientPart.forFluid(ElementtimesFluids.h2so4, 1000))
                    .addItemOutput(IngredientPart.forItem(ElementtimesItems.CaSo4, 1))
                    .addFluidOutput(IngredientPart.forFluid(ElementtimesFluids.hf, 2000))
                    .endAdd()
                    .newRecipe()
                    .addCost(2000)
                    .addItemInput(IngredientPart.forItem(Blocks.GLOWSTONE, 1))
                    .addFluidInput(IngredientPart.forFluid(ElementtimesFluids.h2so4, 1000))
                    .addItemOutput(IngredientPart.forItem(ElementtimesItems.CaSo4, 1))
                    .addFluidOutput(IngredientPart.forFluid(ElementtimesFluids.hf, 2000))
                    .endAdd()


                    //2Na+Cl2=2NaCl
                    .newRecipe()
                    .addCost(0)
                    .addItemInput(IngredientPart.forItem(ElementtimesItems.na, 2))
                    .addFluidInput(IngredientPart.forFluid(ElementtimesFluids.chlorine, 1000))
                    .addItemOutput(IngredientPart.forItem(ElementtimesItems.salt, 2))
                    .endAdd()

                    //2Ag+Cl2=2AgCl
                    .newRecipe()
                    .addCost(5000)
                    .addItemInput(IngredientPart.forItem(ElementtimesItems.silver, 2))
                    .addFluidInput(IngredientPart.forFluid(ElementtimesFluids.chlorine, 1000))
                    .addItemOutput(IngredientPart.forItem(ElementtimesItems.AgCl, 2))
                    .endAdd()
                    //2Ag+Br2=2AgBr
                    .newRecipe()
                    .addCost(5000)
                    .addItemInput(IngredientPart.forItem(ElementtimesItems.silver, 2))
                    .addFluidInput(IngredientPart.forFluid(ElementtimesFluids.Br2, 1000))
                    .addItemOutput(IngredientPart.forItem(ElementtimesItems.AgBr, 2))
                    .endAdd()
                    //2Ag+Br2=2AgI
                    .newRecipe()
                    .addCost(5000)
                    .addItemInput(IngredientPart.forItem(ElementtimesItems.silver, 2))
                    .addFluidInput(IngredientPart.forFluid(ElementtimesFluids.I2, 1000))
                    .addItemOutput(IngredientPart.forItem(ElementtimesItems.AgI, 2))
                    .endAdd()

                    //C+H2O(g)=CO+H2
                    .newRecipe()
                    .addCost(6000)
                    .addItemInput(IngredientPart.forItem(Items.COAL, 1))
                    .addFluidInput(IngredientPart.forFluid(ElementtimesFluids.steam, 1000))
                    .addFluidOutput(IngredientPart.forFluid(ElementtimesFluids.H, 1000))
                    .addFluidOutput(IngredientPart.forFluid(ElementtimesFluids.co, 1000))
                    .endAdd()

                    //钠与水反应
                    .newRecipe()
                    .addCost(0)
                    .addFluidInput(IngredientPart.forFluid(FluidRegistry.WATER, 2000))
                    .addItemInput(IngredientPart.forItem(ElementtimesItems.na, 2))
                    .addFluidOutput(IngredientPart.forFluid(ElementtimesFluids.Naoh, 2000))
                    .addFluidOutput(IngredientPart.forFluid(ElementtimesFluids.H, 1000))
                    .endAdd()
                    .newRecipe()
                    .addCost(0)
                    .addFluidInput(IngredientPart.forFluid(ElementtimesFluids.waterDistilled, 2000))
                    .addItemInput(IngredientPart.forItem(ElementtimesItems.na, 2))
                    .addFluidOutput(IngredientPart.forFluid(ElementtimesFluids.Naoh, 2000))
                    .addFluidOutput(IngredientPart.forFluid(ElementtimesFluids.H, 1000))
                    .endAdd()

                    //电石制取乙炔
                    .newRecipe()
                    .addCost(0)
                    .addFluidInput(IngredientPart.forFluid(ElementtimesFluids.NaClSolutionConcentrated, 2000))
                    .addItemInput(IngredientPart.forItem(ElementtimesItems.calciumacetylide, 1))
                    .addFluidOutput(IngredientPart.forFluid(ElementtimesFluids.calciumHydroxide, 1000))
                    .addFluidOutput(IngredientPart.forFluid(ElementtimesFluids.acetylene, 1000))
                    .addItemOutput(IngredientPart.forItem(ElementtimesItems.salt, 2))
                    .endAdd()

                    //粗硅和Cl2出SiCl4
                    .newRecipe()
                    .addCost(5000)
                    .addFluidInput(IngredientPart.forFluid(ElementtimesFluids.chlorine, 2000))
                    .addItemInput(IngredientPart.forItem(ElementtimesItems.coarseSilicon, 1))
                    .addFluidOutput(IngredientPart.forFluid(ElementtimesFluids.Sicl4, 1000))
                    .endAdd()

                    //铁矿石获得铁
                    .newRecipe()
                    .addCost(10000)
                    .addItemInput(IngredientPart.forItem(Blocks.IRON_ORE, 1))
                    .addFluidInput(IngredientPart.forFluid(ElementtimesFluids.H, 3000))
                    .addItemOutput(IngredientPart.forItem(Items.IRON_INGOT, 2))
                    .addItemOutput(IngredientPart.forItem(ElementtimesItems.slag, 2))
                    .addFluidOutput(IngredientPart.forFluid(FluidRegistry.WATER, 3000))
                    .endAdd()

                    .newRecipe()
                    .addCost(10000)
                    .addItemInput(IngredientPart.forItem(ElementtimesItems.Fe2O3, 1))
                    .addFluidInput(IngredientPart.forFluid(ElementtimesFluids.H, 3000))
                    .addItemOutput(IngredientPart.forItem(Items.IRON_INGOT, 2))
                    .addItemOutput(IngredientPart.forItem(ElementtimesItems.slag, 1))
                    .addFluidOutput(IngredientPart.forFluid(FluidRegistry.WATER, 3000))
                    .endAdd()


                    .newRecipe()
                    .addCost(10000)
                    .addItemInput(IngredientPart.forItem(Blocks.IRON_ORE, 1))
                    .addFluidInput(IngredientPart.forFluid(ElementtimesFluids.co, 3000))
                    .addItemOutput(IngredientPart.forItem(Items.IRON_INGOT, 2))
                    .addItemOutput(IngredientPart.forItem(ElementtimesItems.slag, 2))
                    .addFluidOutput(IngredientPart.forFluid(ElementtimesFluids.co2, 3000))
                    .endAdd()

                    .newRecipe()
                    .addCost(10000)
                    .addItemInput(IngredientPart.forItem(ElementtimesItems.Fe2O3, 1))
                    .addFluidInput(IngredientPart.forFluid(ElementtimesFluids.co, 3000))
                    .addItemOutput(IngredientPart.forItem(Items.IRON_INGOT, 2))
                    .addItemOutput(IngredientPart.forItem(ElementtimesItems.slag, 1))
                    .addFluidOutput(IngredientPart.forFluid(ElementtimesFluids.co2, 3000))
                    .endAdd()


                    //铜矿石获得铜
                    .newRecipe()
                    .addCost(10000)
                    .addItemInput(IngredientPart.forItem("oreCopper", 1))
                    .addFluidInput(IngredientPart.forFluid(ElementtimesFluids.H, 1000))
                    .addItemOutput(IngredientPart.forItem(ElementtimesItems.copper, 1))
                    .addItemOutput(IngredientPart.forItem(ElementtimesItems.slag, 2))
                    .addFluidOutput(IngredientPart.forFluid(FluidRegistry.WATER, 1000))
                    .endAdd()

                    .newRecipe()
                    .addCost(10000)
                    .addItemInput(IngredientPart.forItem(ElementtimesItems.copperPowder, 1))
                    .addFluidInput(IngredientPart.forFluid(ElementtimesFluids.H, 1000))
                    .addItemOutput(IngredientPart.forItem(ElementtimesItems.copper, 1))
                    .addItemOutput(IngredientPart.forItem(ElementtimesItems.slag, 1))
                    .addFluidOutput(IngredientPart.forFluid(FluidRegistry.WATER, 1000))
                    .endAdd()


                    .newRecipe()
                    .addCost(10000)
                    .addItemInput(IngredientPart.forItem("oreCopper", 1))
                    .addFluidInput(IngredientPart.forFluid(ElementtimesFluids.co, 1000))
                    .addItemOutput(IngredientPart.forItem(ElementtimesItems.copper, 1))
                    .addItemOutput(IngredientPart.forItem(ElementtimesItems.slag, 2))
                    .addFluidOutput(IngredientPart.forFluid(ElementtimesFluids.co2, 1000))
                    .endAdd()

                    .newRecipe()
                    .addCost(10000)
                    .addItemInput(IngredientPart.forItem(ElementtimesItems.copperPowder, 1))
                    .addFluidInput(IngredientPart.forFluid(ElementtimesFluids.co, 1000))
                    .addItemOutput(IngredientPart.forItem(ElementtimesItems.copper, 1))
                    .addItemOutput(IngredientPart.forItem(ElementtimesItems.slag, 1))
                    .addFluidOutput(IngredientPart.forFluid(ElementtimesFluids.co2, 1000))
                    .endAdd();
        }
    }

    public TileSolidFluidReactor() {
        super(10000, 4, 5, 1, 16000, 2, 16000);
        addLifeCycle(new FluidMachineLifecycle(this));
    }

    @Override
    public ResourceLocation getBackground() {
        return new ResourceLocation(ElementTimes.MODID, "textures/gui/solidfluidreactor.png");
    }

    @Override
    public GuiSize getSize() {
        return GUI_SIZE_176_204_122.copy().withTitleY(14).withProcess(65, 30).withEnergy(43, 108);
    }

    @Override
    public String getTitle() {
        return ElementtimesBlocks.solidFluidReactor.getLocalizedName();
    }

    @Override
    public int getGuiId() {
        return ElementtimesGUI.Machines.SolidFluidReactor.id();
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
        return new FluidSlotInfo[]{
                FluidSlotInfo.createInput(0, 42, 15),
                FluidSlotInfo.createOutput(0, 116, 15),
                FluidSlotInfo.createOutput(1, 137, 15)
        };
    }

    @Nonnull
    @Override
    public Slot[] getSlots() {
        return new Slot[]{
                new SlotItemHandler(getItemHandler(SideHandlerType.INPUT), 0, 22, 30),
                new SlotItemHandler(getItemHandler(SideHandlerType.INPUT), 1, 43, 66),
                new SlotItemHandler(getItemHandler(SideHandlerType.INPUT), 2, 116, 66),
                new SlotItemHandler(getItemHandler(SideHandlerType.INPUT), 3, 137, 66),
                new SlotItemHandler(getItemHandler(SideHandlerType.OUTPUT), 0, 95, 22),
                new SlotItemHandler(getItemHandler(SideHandlerType.OUTPUT), 1, 95, 34),
                new SlotItemHandler(getItemHandler(SideHandlerType.OUTPUT), 2, 43, 84),
                new SlotItemHandler(getItemHandler(SideHandlerType.OUTPUT), 3, 116, 84),
                new SlotItemHandler(getItemHandler(SideHandlerType.OUTPUT), 4, 137, 84)
        };
    }

    @Override
    public void update() {
        update(this);
    }
}
