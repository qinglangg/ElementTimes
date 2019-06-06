package com.elementtimes.tutorial.common.tileentity;

import com.elementtimes.tutorial.annotation.ModElement;
import com.elementtimes.tutorial.config.ElementtimesConfig;
import com.elementtimes.tutorial.other.MachineRecipeHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.items.ItemHandlerHelper;

import javax.annotation.Nonnull;

/**
 * 电炉
 * @author luqin2007
 */
@ModElement
@ModElement.ModInvokeStatic("init")
public class TileFurnace extends BaseOneToOne {

    public TileFurnace() {
        super(ElementtimesConfig.FURNACE.maxEnergy);
    }

    public static MachineRecipeHandler sRecipeHandler;

    public static void init() {
        sRecipeHandler = new MachineRecipeHandler()
                .add("0")
                .addItemInput(itemStack -> !FurnaceRecipes.instance().getSmeltingResult(itemStack).isEmpty(),
                              itemStack -> ItemHandlerHelper.copyStackWithSize(itemStack, 1))
                .addItemOutput((recipe, slot, input) -> {
                    if (input == null || input.isEmpty()) {
                        return ItemStack.EMPTY;
                    }
                    return FurnaceRecipes.instance().getSmeltingResult(input).copy();
                })
                .addCost(100)
                .build();
    }

    @Nonnull
    @Override
    public MachineRecipeHandler updateRecipe(@Nonnull MachineRecipeHandler recipe) {
        return sRecipeHandler;
    }

    @Override
    public int getMaxEnergyChange() {
        return ElementtimesConfig.FURNACE.maxExtract;
    }

    @Override
    public void applyConfig() {
        setMaxTransfer(ElementtimesConfig.FURNACE.maxReceive);
    }
}

