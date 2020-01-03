package com.elementtimes.tutorial.common.tileentity;

import com.elementtimes.elementcore.api.template.tileentity.BaseTileEntity;
import com.elementtimes.elementcore.api.template.tileentity.SideHandlerType;
import com.elementtimes.elementcore.api.template.tileentity.lifecycle.FluidMachineLifecycle;
import com.elementtimes.elementcore.api.template.tileentity.recipe.MachineRecipeHandler;
import com.elementtimes.tutorial.common.init.ElementtimesGUI;
import com.elementtimes.tutorial.plugin.elementcore.JeiRecipe;
import net.minecraft.inventory.Slot;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

public class TileFermenter extends BaseTileEntity {

    @JeiRecipe.MachineRecipe(block = "elementtimes:fermenter", gui = TileFermenter.class, u = 8, v = 7, w = 159, h = 97)
    public static MachineRecipeHandler RECIPE = new MachineRecipeHandler(2, 1, 1, 3);

    public TileFermenter() {
        super(10000, 6, 5, 1, 16000, 3, 16000);
        addLifeCycle(new FluidMachineLifecycle(this, 1, 3));
    }

    @Override
    public ResourceLocation getBackground() {
        return new ResourceLocation("elementtimes", "textures/gui/fermenter.png");
    }

    @Override
    public GuiSize getSize() {
        return new GuiSize().withSize(176, 210, 7, 127).withTitleY(115)
                .withProcess(63, 43).withEnergy(43, 117);
    }

    @Override
    public String getTitle() {
        return null;
    }

    @Override
    public int getGuiId() {
        return ElementtimesGUI.Machines.Fermenter.id();
    }

    @Nonnull
    @Override
    public Slot[] getSlots() {
        return new Slot[] {
                new SlotItemHandler(getItemHandler(SideHandlerType.INPUT), 0, 12, 37),
                new SlotItemHandler(getItemHandler(SideHandlerType.INPUT), 1, 66, 19),
                new SlotItemHandler(getItemHandler(SideHandlerType.INPUT), 2, 33, 63),
                new SlotItemHandler(getItemHandler(SideHandlerType.INPUT), 3, 111, 63),
                new SlotItemHandler(getItemHandler(SideHandlerType.INPUT), 4, 129, 63),
                new SlotItemHandler(getItemHandler(SideHandlerType.INPUT), 5, 147, 63),
                new SlotItemHandler(getItemHandler(SideHandlerType.OUTPUT), 0, 90, 42),
                new SlotItemHandler(getItemHandler(SideHandlerType.OUTPUT), 1, 33, 81),
                new SlotItemHandler(getItemHandler(SideHandlerType.OUTPUT), 2, 111, 81),
                new SlotItemHandler(getItemHandler(SideHandlerType.OUTPUT), 3, 129, 81),
                new SlotItemHandler(getItemHandler(SideHandlerType.OUTPUT), 4, 147, 81)
        };
    }

    @Nonnull
    @Override
    public FluidSlotInfo[] getFluids() {
        return new FluidSlotInfo[] {
                FluidSlotInfo.createInput(0, 33, 12),
                FluidSlotInfo.createOutput(0, 111, 12),
                FluidSlotInfo.createOutput(1, 129, 12),
                FluidSlotInfo.createOutput(2, 147, 12)
        };
    }

    @Override
    public MachineRecipeHandler getRecipes() {
        return RECIPE;
    }
}
