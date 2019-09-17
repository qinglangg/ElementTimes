package com.elementtimes.tutorial.common.tileentity;

import com.elementtimes.elementcore.api.annotation.ModInvokeStatic;
import com.elementtimes.elementcore.api.template.tileentity.BaseTileEntity;
import com.elementtimes.elementcore.api.template.tileentity.SideHandlerType;
import com.elementtimes.elementcore.api.template.tileentity.recipe.MachineRecipeHandler;
import com.elementtimes.tutorial.ElementTimes;
import com.elementtimes.tutorial.common.init.ElementtimesBlocks;
import com.elementtimes.tutorial.common.init.ElementtimesGUI;
import com.elementtimes.tutorial.config.ElementtimesConfig;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;
import java.util.Collections;

/**
 * 电炉
 * @author luqin2007
 */
@ModInvokeStatic("init")
public class TileFurnace extends BaseTileEntity {

    public static MachineRecipeHandler RECIPE;

    public static void init() {
        RECIPE = new MachineRecipeHandler(1, 1, 0, 0)
                .newRecipe()
                .addItemInput(itemStack -> !FurnaceRecipes.instance().getSmeltingResult(itemStack).isEmpty(),
                              itemStack -> ItemHandlerHelper.copyStackWithSize(itemStack, 1),
                              Collections.emptyList())
                .addItemOutput((recipe, input, fluids, i, p) -> {
                    if (input == null || input.isEmpty() || input.get(0).isEmpty()) {
                        return ItemStack.EMPTY;
                    }
                    return FurnaceRecipes.instance().getSmeltingResult(input.get(0)).copy();
                }, Collections.emptyList())
                .addCost(100)
                .endAdd();
    }

    public TileFurnace() {
        super(ElementtimesConfig.FURNACE.maxEnergy, 1, 1);
    }

    @Nonnull
    @Override
    public MachineRecipeHandler getRecipes() {
        return RECIPE;
    }

    @Override
    public int getEnergyTick() {
        return ElementtimesConfig.FURNACE.maxExtract;
    }

    @Override
    public void applyConfig() {
        setEnergyTransfer(ElementtimesConfig.FURNACE.maxReceive);
    }

    @Override
    @Nonnull
    public Slot[] getSlots() {
        return new Slot[]{new SlotItemHandler(this.getItemHandler(SideHandlerType.INPUT), 0, 56, 30), new SlotItemHandler(this.getItemHandler(SideHandlerType.OUTPUT), 0, 110, 30)};
    }

    @Override
    public ResourceLocation getBackground() {
        return new ResourceLocation(ElementTimes.MODID, "textures/gui/5.png");
    }

    @Override
    public GuiSize getSize() {
        return GUI_SIZE_176_156_74.copy().withTitleY(60)
                .withProcess(80, 30, 0, 156, 24, 17)
                .withEnergy(43, 55, 24, 156, 90, 4);
    }

    @Override
    public String getTitle() {
        return ElementtimesBlocks.furnace.getLocalizedName();

    }

    @Override
    public int getGuiId() {
        return ElementtimesGUI.Machines.Furnace.id();
    }

    @Override
    public void update() {
        update(this);
    }
}

