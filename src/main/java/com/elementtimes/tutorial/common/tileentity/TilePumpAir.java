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
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

/**
 * 气泵
 *
 * @author luqin2007
 */
@ModInvokeStatic("init")
public class TilePumpAir extends BaseTileEntity {

    @JeiRecipe.MachineRecipe(block = "elementtimes:pumpair", gui = TilePumpAir.class, u = 45, v = 21, w = 80, h = 61)
    public static MachineRecipeHandler RECIPE = new MachineRecipeHandler(0, 0, 0, 1)
            .newRecipe().addCost(100).addFluidOutput(IngredientPart.forFluid(ElementtimesFluids.air, 1)).endAdd();

    public TilePumpAir() {
        super(1000, 1, 1, 0, 0, 1, 16000);
        addLifeCycle(new FluidMachineLifecycle(this));
    }

    @Override
    public ResourceLocation getBackground() {
        return new ResourceLocation(ElementTimes.MODID, "textures/gui/pump.png");
    }

    @Override
    public GuiSize getSize() {
        return GUI_SIZE_176_204_122.copy().withTitleY(85).withEnergy(43, 108, 24, 204, 90, 4);
    }

    @Override
    public String getTitle() {
        return ElementtimesBlocks.pumpAir.getLocalizedName();

    }

    @Override
    public int getGuiId() {
        return ElementtimesGUI.Machines.PumpAir.id();
    }

    @Nonnull
    @Override
    public MachineRecipeHandler getRecipes() {
        return RECIPE;
    }

    @Override
    public int getEnergyTick() {
        return 1;
    }

    @Nonnull
    @Override
    public Slot[] getSlots() {
        return new Slot[]{
                new SlotItemHandler(getItemHandler(SideHandlerType.INPUT), 0, 56, 58),
                new SlotItemHandler(getItemHandler(SideHandlerType.OUTPUT), 0, 98, 58)
        };
    }

    @Nonnull
    @Override
    public FluidSlotInfo[] getFluids() {
        return new FluidSlotInfo[]{
                FluidSlotInfo.createOutput(0, 77, 28)
        };
    }
}
