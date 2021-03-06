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
import com.elementtimes.tutorial.common.init.ElementtimesItems;

import com.elementtimes.tutorial.plugin.elementcore.JeiRecipe;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Slot;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

/**
 * 凝固器
 * @author luqin2007
 */
@ModInvokeStatic("init")
public class TileCoagulator extends BaseTileEntity {

    @JeiRecipe.MachineRecipe(block = "elementtimes:coagulator", gui = TileCoagulator.class, u = 44, v = 7, w = 83, h = 73)
    public static MachineRecipeHandler RECIPE = new MachineRecipeHandler(0, 1, 1, 0);

    public static void init() {
        if (RECIPE.getMachineRecipes().isEmpty()) {
            RECIPE.newRecipe()
            		.addCost(1000)
            		.addFluidInput(IngredientPart.forFluid(ElementtimesFluids.waterDistilled, 1000))
            		.addItemOutput(IngredientPart.forItem(Blocks.ICE,1))
            		.endAdd()
            		.newRecipe()
            		.addCost(1000)
            		.addFluidInput(IngredientPart.forFluid(FluidRegistry.WATER, 1000))
            		.addItemOutput(IngredientPart.forItem(Blocks.ICE,1))
            		.endAdd()
            		.newRecipe()
            		.addCost(10000)
            		.addFluidInput(IngredientPart.forFluid(ElementtimesFluids.steel, 1000))
            		.addItemOutput(IngredientPart.forItem(ElementtimesItems.steelIngot,1))
            		.endAdd()
            		.newRecipe()
            		.addCost(10000)
            		.addFluidInput(IngredientPart.forFluid(ElementtimesFluids.Fe, 1000))
            		.addItemOutput(IngredientPart.forItem(Items.IRON_INGOT,1))
            		.endAdd()
            		.newRecipe()
            		.addCost(10000)
            		.addFluidInput(IngredientPart.forFluid(FluidRegistry.LAVA, 1000))
            		.addItemOutput(IngredientPart.forItem(Blocks.MAGMA,1))
            		.endAdd()
            		.newRecipe()
                    .addCost(20000)
                    .addFluidInput(IngredientPart.forFluid(ElementtimesFluids.Na, 1000))
                    .addItemOutput(IngredientPart.forItem(ElementtimesItems.na,1))
                    .endAdd()
                    .newRecipe()
                    .addCost(20000)
                    .addFluidInput(IngredientPart.forFluid(ElementtimesFluids.Mg, 1000))
                    .addItemOutput(IngredientPart.forItem(ElementtimesItems.Mg,1))
                    .endAdd()
                    .newRecipe()
                    .addCost(20000)
                    .addFluidInput(IngredientPart.forFluid(ElementtimesFluids.Al, 1000))
                    .addItemOutput(IngredientPart.forItem(ElementtimesItems.Al,1))
                    .endAdd()
            		.newRecipe()
            		.addCost(20000)
            		.addFluidInput(IngredientPart.forFluid(ElementtimesFluids.Na3AlF6, 1000))
            		.addItemOutput(IngredientPart.forItem(ElementtimesItems.Na3AlF6,1))
            		.endAdd();
        }
    }

    public TileCoagulator() {
        super(100000, 1, 2, 1, 16000, 0, 0);
        addLifeCycle(new FluidMachineLifecycle(this));
    }

    @Override
    public int getGuiId() {
        return ElementtimesGUI.Machines.Coagulator.id();
    }

    @Nonnull
    @Override
    public MachineRecipeHandler getRecipes() {
        return RECIPE;
    }

    @Override
    public int getEnergyTick() {
        return 30;
    }

    @Nonnull
    @Override
    public Slot[] getSlots() {
        return new Slot[] {
                new SlotItemHandler(getItemHandler(SideHandlerType.INPUT), 0, 47, 61),
                new SlotItemHandler(getItemHandler(SideHandlerType.OUTPUT), 0, 108, 30),
                new SlotItemHandler(getItemHandler(SideHandlerType.OUTPUT), 1, 65, 61)
        };
    }

    @Nonnull
    @Override
    public FluidSlotInfo[] getFluids() {
        return new FluidSlotInfo[] {
                FluidSlotInfo.createInput(0, 56, 10)
        };
    }

    @Override
    public ResourceLocation getBackground() {
        return new ResourceLocation(ElementTimes.MODID, "textures/gui/coagulator.png");
    }

    @Override
    public GuiSize getSize() {
        return GUI_SIZE_176_179_97.copy().withProcess(81, 36, 0, 196, 16, 16).withEnergy(43, 86).withTitleY(85);
    }

    @Override
    public String getTitle() {
        return ElementtimesBlocks.coagulator.getLocalizedName();

    }
}
