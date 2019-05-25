package com.elementtimes.tutorial.common.tileentity;

import com.elementtimes.tutorial.common.block.machine.Furnace;
import com.elementtimes.tutorial.common.tileentity.base.TileOneToOne;
import com.elementtimes.tutorial.config.ElementtimesConfig;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;

public class TileFurnace extends TileOneToOne {

    public TileFurnace() {
        super(ElementtimesConfig.furnace.maxEnergy, ElementtimesConfig.furnace.maxReceive);
    }

    @Override
    protected ItemStack getOutput(ItemStack input) {
        if (input.isEmpty()) return ItemStack.EMPTY;
        return FurnaceRecipes.instance().getSmeltingResult(input).copy();
    }

    @Override
    protected int getTotalTime(ItemStack input) {
        return ElementtimesConfig.furnace.totalTime;
    }

    @Override
    protected int getEnergyConsumePerTick(ItemStack input) {
        return ElementtimesConfig.furnace.maxExtract;
    }

    @Override
    protected IBlockState updateState(IBlockState old) {
        if (isProc != old.getValue(Furnace.BURNING)) {
            return old.withProperty(Furnace.BURNING, isProc);
        }
        return super.updateState(old);
    }
}
