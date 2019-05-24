package com.elementtimes.tutorial.common.tileentity;

import com.elementtimes.tutorial.common.block.machine.Furnace;
import com.elementtimes.tutorial.common.tileentity.base.TileOneToOne;
import com.elementtimes.tutorial.config.ElementtimesConfig;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;

public class TileFurnace extends TileOneToOne {

    public TileFurnace() {
        super(ElementtimesConfig.furnace.maxEnergy,
                ElementtimesConfig.furnace.maxReceive,
                ElementtimesConfig.furnace.maxExtract, 16000);
    }

    @Override
    protected ItemStack getOutput(ItemStack input) {
        if (input.isEmpty()) return ItemStack.EMPTY;
        return FurnaceRecipes.instance().getSmeltingResult(input);
    }

    @Override
    public boolean onUpdate() {
        IBlockState iBlockState = world.getBlockState(pos);
        if (isProc != iBlockState.getValue(Furnace.BURNING)) {
            IBlockState iBlockState1 = iBlockState.withProperty(Furnace.BURNING, isProc);
            world.setBlockState(pos, iBlockState1);
            return false;
        }
        return true;
    }
}
