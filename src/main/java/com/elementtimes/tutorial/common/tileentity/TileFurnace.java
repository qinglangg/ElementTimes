package com.elementtimes.tutorial.common.tileentity;

import com.elementtimes.tutorial.common.block.Furnace;
import com.elementtimes.tutorial.common.tileentity.base.TileOneToOne;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;

public class TileFurnace extends TileOneToOne {

    public TileFurnace(int maxEnergy, int maxReceive, int maxExtract, int perTime) {
        super(maxEnergy, maxReceive, maxExtract, 16000);
    }

    @Override
    protected ItemStack getOutput(ItemStack input) {
        if (input.isEmpty()) return ItemStack.EMPTY;
        return FurnaceRecipes.instance().getSmeltingResult(input);
    }

    @Override
    protected void onUpdate(boolean isProc, int schedule, int perTime) {
        if (!world.isRemote) {
            if (isProc != world.getBlockState(pos).getValue(Furnace.IS_BURNING)) {
                IBlockState state = world.getBlockState(pos).withProperty(Furnace.IS_BURNING, isProc);
                world.setBlockState(pos, state, 4);
            }
        }
    }
}
