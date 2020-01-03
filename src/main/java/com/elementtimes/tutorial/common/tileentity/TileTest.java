package com.elementtimes.tutorial.common.tileentity;

import com.elementtimes.elementcore.api.template.capability.item.IItemHandler;
import com.elementtimes.elementcore.api.template.tileentity.BaseTileEntity;
import com.elementtimes.elementcore.api.template.tileentity.SideHandlerType;
import com.elementtimes.elementcore.api.template.tileentity.lifecycle.RecipeMachineLifecycle;
import com.elementtimes.tutorial.ElementTimes;
import com.elementtimes.tutorial.common.init.ElementtimesBlocks;
import com.elementtimes.tutorial.common.init.ElementtimesGUI;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

/**
 * 测试用
 * @author luqin2007
 */
public class TileTest extends BaseTileEntity {

    public TileTest() {
        super(Integer.MAX_VALUE, 1, 0);
        removeLifecycle(RecipeMachineLifecycle.class);
    }

    @Nonnull
    @Override
    public Slot[] getSlots() {
        return new Slot[] {new SlotItemHandler(getItemHandler(SideHandlerType.INPUT), 0, 80, 30)};
    }

    @Override
    public ResourceLocation getBackground() {
        return new ResourceLocation(ElementTimes.MODID, "textures/gui/0.png");
    }

    @Override
    public GuiSize getSize() {
        return GUI_SIZE_176_156_74.copy().withTitleY(60);
    }

    @Override
    public String getTitle() {
        return ElementtimesBlocks.test.getLocalizedName();
    }

    @Override
    public int getGuiId() {
        return ElementtimesGUI.Machines.Test.id();
    }

    @Override
    public void update() {
        IItemHandler handler = getItemHandler(SideHandlerType.INPUT);
        if (!handler.getStackInSlot(0).isEmpty()) {
            handler.setStackInSlot(0, ItemStack.EMPTY);
        }
    }

    @Override
    public boolean isInputValid(int slot, ItemStack stack) {
        return true;
    }
}
