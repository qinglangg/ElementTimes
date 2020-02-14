package com.elementtimes.tutorial.common.tileentity;

import com.elementtimes.elementcore.api.annotation.tools.ModInvokeStatic;
import com.elementtimes.elementcore.api.template.tileentity.BaseTileEntity;
import com.elementtimes.elementcore.api.template.tileentity.SideHandlerType;
import com.elementtimes.elementcore.api.template.tileentity.lifecycle.EnergyGeneratorLifecycle;
import com.elementtimes.elementcore.api.template.tileentity.recipe.MachineRecipeHandler;
import com.elementtimes.tutorial.ElementTimes;
import com.elementtimes.tutorial.common.init.ElementtimesBlocks;
import com.elementtimes.tutorial.common.init.ElementtimesGUI;
import com.elementtimes.tutorial.config.ElementtimesConfig;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;
import java.util.Collections;

/**
 * 火力发电机的TileEntity
 *
 * @author lq2007 create in 2019/5/22
 */
@ModInvokeStatic("init")
public class TileGeneratorFuel extends BaseTileEntity {

    public static MachineRecipeHandler RECIPE = new MachineRecipeHandler(1, 0, 0, 0);

    public static void init() {
        if (RECIPE.getMachineRecipes().isEmpty()) {
            RECIPE.newRecipe()
                    .addItemInput(itemStack -> TileEntityFurnace.getItemBurnTime(itemStack) > 0,
                            itemStack -> ItemHandlerHelper.copyStackWithSize(itemStack, 1),
                            Collections.emptyList())
                    .addCost(value -> {
                        if (value.inputs.isEmpty()) {
                            return 0;
                        }
                        ItemStack stack = value.inputs.get(0);
                        return -TileEntityFurnace.getItemBurnTime(stack) * 10;
                    }).endAdd();
        }
    }

    public TileGeneratorFuel() {
        super(ElementtimesConfig.FUEL_GENERAL.generaterMaxEnergy, 1, 0);
        addLifeCycle(new EnergyGeneratorLifecycle<>(this));
    }

    @Nonnull
    @Override
    public MachineRecipeHandler getRecipes() {
        return RECIPE;
    }

    @Override
    public int getEnergyTick() {
        return ElementtimesConfig.FUEL_GENERAL.generaterMaxReceive;
    }

    @Override
    public void applyConfig() {
        setEnergyTransfer(ElementtimesConfig.FUEL_GENERAL.generaterMaxExtract);
    }

    @Override
    public int getGuiId() {
        return ElementtimesGUI.Machines.FuelGenerator.id();
    }

    @Nonnull
    @Override
    public Slot[] getSlots() {
        return new Slot[]{
                new SlotItemHandler(getItemHandler(SideHandlerType.INPUT), 0, 80, 30)
        };
    }

    @Override
    public ResourceLocation getBackground() {
        return new ResourceLocation(ElementTimes.MODID, "textures/gui/0.png");
    }

    @Override
    public GuiSize getSize() {
        return GUI_SIZE_176_156_74.copy().withTitleY(60).withEnergy(43, 55, 0, 156, 89, 4);
    }

    @Override
    public String getTitle() {
        return ElementtimesBlocks.fuelGenerator.getLocalizedName();
    }
}
