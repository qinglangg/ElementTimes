package com.elementtimes.tutorial.common.tileentity;

import com.elementtimes.elementcore.api.template.tileentity.BaseTileEntity;
import com.elementtimes.elementcore.api.template.tileentity.SideHandlerType;
import com.elementtimes.elementcore.api.template.tileentity.lifecycle.FluidMachineLifecycle;
import com.elementtimes.elementcore.api.template.tileentity.lifecycle.RecipeMachineLifecycle;
import com.elementtimes.tutorial.ElementTimes;
import com.elementtimes.tutorial.common.init.ElementtimesBlocks;
import com.elementtimes.tutorial.common.init.ElementtimesGUI;
import com.elementtimes.tutorial.other.FluidBlockRecipeLifecycle;
import net.minecraft.inventory.Slot;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

/**
 * 液泵
 * @author luqin2007
 */
public class TilePumpFluid extends BaseTileEntity {

    public TilePumpFluid() {
        super(1000, 1, 1, 0, 0, 1, 16000);
        // TODO remove WorldReplaceMachineLifecycle
        removeLifecycle(RecipeMachineLifecycle.class);
        addLifeCycle(new FluidBlockRecipeLifecycle(this));
        addLifeCycle(new FluidMachineLifecycle(this));
    }

    @Override
    public int getGuiId() {
        return ElementtimesGUI.Machines.PumpFluid.id();
    }

    @Override
    public int getEnergyTick() {
        return 10;
    }

    @Nonnull
    @Override
    public Slot[] getSlots() {
        return new Slot[] {
                new SlotItemHandler(getItemHandler(SideHandlerType.INPUT), 0, 56, 58),
                new SlotItemHandler(getItemHandler(SideHandlerType.OUTPUT), 0, 98, 58)
        };
    }

    @Nonnull
    @Override
    public FluidSlotInfo[] getFluids() {
        return new FluidSlotInfo[] {
                FluidSlotInfo.createOutput(0, 77, 28)
        };
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
        return ElementtimesBlocks.pumpFluid.getLocalizedName();
    }
}
