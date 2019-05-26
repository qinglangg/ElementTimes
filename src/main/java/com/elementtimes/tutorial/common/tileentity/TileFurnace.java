package com.elementtimes.tutorial.common.tileentity;

import com.elementtimes.tutorial.Elementtimes;
import com.elementtimes.tutorial.common.block.machine.Furnace;
import com.elementtimes.tutorial.common.tileentity.base.TileOneToOne;
import com.elementtimes.tutorial.config.ElementtimesConfig;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;

public class TileFurnace extends TileOneToOne {

    public TileFurnace() {
        super(ElementtimesConfig.furnace.maxEnergy, ElementtimesConfig.furnace.maxReceive);
    }

    @Override
    protected IBlockState updateState(IBlockState old) {
        if (isProc != old.getValue(Furnace.BURNING)) {
            Elementtimes.getLogger().warn("furnace: change {} -> {}", old.getValue(Furnace.BURNING), isProc);
            return old.withProperty(Furnace.BURNING, isProc);
        }
        return super.updateState(old);
    }

    @Override
    protected ItemStack getInput(ItemStackHandler handler) {
        return handler.extractItem(0, 1, true);
    }

    @Override
    protected ItemStack getOutput(ItemStackHandler handler, boolean simulate) {
        ItemStack input = handler.extractItem(0, 1, simulate);
        if (input.isEmpty()) return ItemStack.EMPTY;
        return FurnaceRecipes.instance().getSmeltingResult(input).copy();
    }

    @Override
    protected int getTotalEnergyCost(ItemStackHandler handler) {
        return ElementtimesConfig.furnace.totalTime * getEnergyCostPerTick(handler);
    }

    @Override
    protected int getEnergyCostPerTick(ItemStackHandler handler) {
        return ElementtimesConfig.furnace.maxExtract;
    }

    @Override
    protected boolean isInputItemValid(int slot, @Nonnull ItemStack stack) {
        return !FurnaceRecipes.instance().getSmeltingResult(stack).isEmpty();
    }
}

