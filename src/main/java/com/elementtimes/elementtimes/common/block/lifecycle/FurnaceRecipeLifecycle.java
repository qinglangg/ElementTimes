package com.elementtimes.elementtimes.common.block.lifecycle;

import com.elementtimes.elementcore.api.capability.AccessibleItemHandler;
import com.elementtimes.elementcore.api.lifecycle.ILifecycle;
import com.elementtimes.elementcore.api.tileentity.BaseTileEntity;
import com.elementtimes.elementcore.api.utils.RecipeUtils;
import com.elementtimes.elementtimes.config.Config;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipe;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;

import java.util.Optional;



public class FurnaceRecipeLifecycle implements ILifecycle {

    protected boolean mSave;
    protected BaseTileEntity mTile;
    protected int mEnergyTotal, mEnergyCost;
    protected ItemStack mOutput = ItemStack.EMPTY;

    public FurnaceRecipeLifecycle(BaseTileEntity tile) {
        mTile = tile;
    }

    @Override
    public void onTickStart() {
        mSave = false;
    }

    @Override
    public boolean onCheckStart() {
        World world = mTile.getWorld();
        if (world == null || world.isRemote) {
            return false;
        }
        AccessibleItemHandler handler = mTile.getItemHandler();
        Optional<FurnaceRecipe> recipe = RecipeUtils.getCooking(world, handler.extractItemUncheck(0, 1, true));
        if (recipe.isPresent()) {
            FurnaceRecipe furnaceRecipe = recipe.get();
            int cookTime = furnaceRecipe.getCookTime();
            int energy = (int) (cookTime * Config.furnaceMultiple.get());
            int energyFirst = Math.min(energy, Config.furnaceSpeed.get());
            if (mTile.getEnergyHandler().extractEnergyUncheck(energyFirst, true) == energyFirst) {
                mEnergyTotal = energy;
                mEnergyCost = 0;
                mOutput = furnaceRecipe.getRecipeOutput();
                mSave = true;
                return true;
            }
        }
        return false;
    }

    @Override
    public void onStart() {
        mTile.getItemHandler().extractItemUncheck(0, 1, false);
    }

    @Override
    public boolean onLoop() {
        int energy = Math.min(mEnergyTotal - mEnergyCost, Config.furnaceSpeed.get());
        int energyCost = mTile.getEnergyHandler().extractEnergyUncheck(energy, true);
        if (energyCost < energy) {
            return false;
        }
        mEnergyCost += energyCost;
        mSave = true;
        return true;
    }

    @Override
    public boolean onCheckResume() {
        int energy = Math.min(mEnergyTotal - mEnergyCost, Config.furnaceSpeed.get());
        return mTile.getEnergyHandler().extractEnergyUncheck(energy, true) == energy;
    }

    @Override
    public boolean onCheckFinish() {
        return mEnergyCost >= mEnergyTotal;
    }

    @Override
    public boolean onFinish() {
        mOutput = mTile.getItemHandler().insertItemUncheck(1, mOutput, false);
        mSave = true;
        return mOutput.isEmpty();
    }

    @Override
    public boolean needSave() {
        return mSave;
    }

    @Override
    public boolean needSync() {
        return mSave;
    }

    @Override
    public void saveData() {
        if (!mOutput.isEmpty()) {
            CompoundNBT nbt = new CompoundNBT();
            nbt.putInt("energy", mEnergyTotal);
            nbt.putInt("cost", mEnergyCost);
            nbt.put("output", mOutput.write(new CompoundNBT()));
            mTile.bindNbt.put("furnaceRecipe", nbt);
        }
    }

    @Override
    public void resumeData() {
        mEnergyTotal = 0;
        mEnergyCost = 0;
        mOutput = ItemStack.EMPTY;
        if (mTile.bindNbt.contains("furnaceRecipe", Constants.NBT.TAG_COMPOUND)) {
            CompoundNBT nbt = mTile.bindNbt.getCompound("furnaceRecipe");
            mEnergyTotal = nbt.getInt("energy");
            mEnergyCost = nbt.getInt("cost");
            mOutput = ItemStack.read(nbt.getCompound("output"));
        }
    }

    @Override
    public void readServerData() {
        if (mTile.nbtFromServer.contains("furnaceRecipe", Constants.NBT.TAG_COMPOUND)) {
            CompoundNBT nbt = mTile.bindNbt.getCompound("furnaceRecipe");
            mEnergyTotal = nbt.getInt("energy");
            mEnergyCost = nbt.getInt("cost");
        }
    }

    @Override
    public void writeClientData() {
        CompoundNBT nbt = new CompoundNBT();
        nbt.putInt("energy", mEnergyTotal);
        nbt.putInt("cost", mEnergyCost);
        mTile.nbtToClient.put("furnaceRecipe", nbt);
    }

    public int getEnergyCost() {
        return mEnergyCost;
    }

    public int getEnergyTotal() {
        return mEnergyTotal;
    }
}
