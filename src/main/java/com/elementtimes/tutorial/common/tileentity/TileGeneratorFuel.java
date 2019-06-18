package com.elementtimes.tutorial.common.tileentity;

import com.elementtimes.tutorial.annotation.annotations.ModElement;
import com.elementtimes.tutorial.common.init.ElementtimesGUI;
import com.elementtimes.tutorial.config.ElementtimesConfig;
import com.elementtimes.tutorial.other.recipe.MachineRecipeHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraftforge.items.ItemHandlerHelper;

import javax.annotation.Nonnull;
import java.util.Collections;

/**
 * 火力发电机的TileEntity
 * @author lq2007 create in 2019/5/22
 */
@ModElement
@ModElement.ModInvokeStatic("init")
public class TileGeneratorFuel extends BaseGenerator {

    public TileGeneratorFuel() {
        super(ElementtimesConfig.FUEL_GENERAL.generaterMaxEnergy);
    }

    public static MachineRecipeHandler sRecipeHandler;

    public static void init() {
        sRecipeHandler = new MachineRecipeHandler()
                .newRecipe("0")
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

    @Nonnull
    @Override
    public MachineRecipeHandler updateRecipe(@Nonnull MachineRecipeHandler recipe) {
        return sRecipeHandler;
    }

    @Override
    public int getMaxEnergyChange() {
        return ElementtimesConfig.FUEL_GENERAL.generaterMaxReceive;
    }

    @Override
    public void applyConfig() {
        setMaxTransfer(ElementtimesConfig.FUEL_GENERAL.generaterMaxExtract);
    }

    @Override
    public ElementtimesGUI.Machines getGuiType() {
        return ElementtimesGUI.Machines.FuelGenerator;
    }
}
