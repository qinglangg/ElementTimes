package com.elementtimes.tutorial.common.tileentity;

import com.elementtimes.elementcore.api.annotation.ModInvokeStatic;
import com.elementtimes.elementcore.api.template.tileentity.BaseTileEntity;
import com.elementtimes.elementcore.api.template.tileentity.SideHandlerType;
import com.elementtimes.elementcore.api.template.tileentity.lifecycle.FluidMachineLifecycle;
import com.elementtimes.elementcore.api.template.tileentity.recipe.MachineRecipeHandler;
import com.elementtimes.tutorial.ElementTimes;
import com.elementtimes.tutorial.common.init.ElementtimesBlocks;
import com.elementtimes.tutorial.common.init.ElementtimesFluids;
import com.elementtimes.tutorial.common.init.ElementtimesGUI;
import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import net.minecraft.inventory.Slot;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

/**
 * 冷凝机
 * @author luqin2007
 */
@ModInvokeStatic("init")
public class TileCondenser extends BaseTileEntity {

    public static MachineRecipeHandler RECIPE = null;

    public static void init() {
        if (RECIPE == null) {
            RECIPE = new MachineRecipeHandler(0, 0, 1, 1)
                    .add(1000, ElementtimesFluids.steam, ElementtimesFluids.waterDistilled);
        }
    }

    public TileCondenser() {
        super(100000, 2, 2, 1, 16000, 1, 16000);
        addLifeCycle(new FluidMachineLifecycle(this));
    }

    @Nonnull
    @Override
    public MachineRecipeHandler getRecipes() {
        return RECIPE;
    }

    @Override
    public int getGuiId() {
        return ElementtimesGUI.Machines.Condenser.id();
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
                new SlotItemHandler(getItemHandler(SideHandlerType.INPUT), 1, 134, 83),
                new SlotItemHandler(getItemHandler(SideHandlerType.OUTPUT), 0, 26, 83),
                new SlotItemHandler(getItemHandler(SideHandlerType.OUTPUT), 1, 151, 83)
        };
    }

    @Override
    public ResourceLocation getBackground() {
        return new ResourceLocation(ElementTimes.MODID, "textures/gui/condenser.png");
    }

    @Override
    public GuiSize getSize() {
        return GUI_SIZE_176_204_122.copy()
                .withProcess(54, 18, 0, 204, 70, 20)
                .withProcess(55, 52, 0, 224, 64, 15)
                .withEnergy(45, 89, 0, 239, 90, 4)
                .withTitleY(115);
    }

    @Override
    public String getTitle() {
        return ElementtimesBlocks.condenser.getLocalizedName();

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
