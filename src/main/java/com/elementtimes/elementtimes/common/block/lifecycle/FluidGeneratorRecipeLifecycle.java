package com.elementtimes.elementtimes.common.block.lifecycle;

import com.elementtimes.elementcore.api.lifecycle.BaseRecipeLifecycle;
import com.elementtimes.elementcore.api.recipe.RecipeCapture;
import com.elementtimes.elementtimes.common.block.machine.TileFluidGenerator;
import com.elementtimes.elementtimes.config.Config;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;

import javax.annotation.Nonnull;
import java.util.Optional;



public class FluidGeneratorRecipeLifecycle extends BaseRecipeLifecycle {

    private final TileFluidGenerator mTile;

    private static final String TAG_RECIPE = "recipe";
    private static final String TAG_PROCESS = "process";
    private static final String TAG_ENERGY = "energy";
    private static final String TAG = "fluid_recipe";

    public FluidGeneratorRecipeLifecycle(TileFluidGenerator tile) {
        mTile = tile;
    }

    @Override
    protected void inputItem(RecipeCapture capture) { }

    @Override
    protected void inputFluid(RecipeCapture capture) {
        FluidStack stack = capture.getFluidInputs().get(0);
        mTile.getFluidHandler().drainUncheck(0, stack, IFluidHandler.FluidAction.EXECUTE);
    }

    @Override
    protected Optional<RecipeCapture> getNextRecipe() {
        FluidStack stack = mTile.getFluidHandler().drain(0, 1000, IFluidHandler.FluidAction.SIMULATE);
        if (stack.getAmount() == 1000) {
            ItemStack bucket = stack.getFluid().getAttributes().getBucket(stack);
            int burnTime = ForgeHooks.getBurnTime(bucket);
            if (burnTime > 0) {
                int energy = (int) (burnTime * Config.genFluidMultiple.get());
                RecipeCapture capture = new RecipeCapture(NonNullList.create(), NonNullList.create(), NonNullList.withSize(1, stack), NonNullList.create(), -energy, 0);
                return Optional.of(capture);
            }
        }
        return Optional.empty();
    }

    @Override
    protected void outputFluid(RecipeCapture recipe, int charge) { }

    @Override
    protected int receiveEnergy(int charge) {
        int energy = Math.min(charge, getEnergySpeed());
        int receive = mTile.getEnergyHandler().receiveEnergyUncheck(energy, false);
        mEnergyAmount += receive;
        return receive;
    }

    @Override
    protected int extractEnergy(int charge) {
        return 0;
    }

    @Override
    protected int testEnergy(int charge) {
        int energy = Math.min(charge, getEnergySpeed());
        return mTile.getEnergyHandler().receiveEnergyUncheck(energy, true) < energy ? 0 : energy;
    }

    @Override
    protected void outputItem(RecipeCapture recipe) { }

    @Override
    protected boolean testItem(RecipeCapture recipe) {
        return false;
    }

    @Override
    protected boolean isRecipeCanWork(@Nonnull RecipeCapture capture) {
        if (mTile.getFluidHandler().drainUncheck(0, capture.getFluidInputs().get(0), IFluidHandler.FluidAction.SIMULATE).getAmount() == 1000) {
            return testEnergy(capture.getEnergyAbs()) > 0;
        }
        return false;
    }

    @Override
    protected boolean testFluid(RecipeCapture recipe, int charge) {
        return false;
    }

    @Override
    public void saveData() {
        if (mRecipeCapture != null) {
            CompoundNBT nbt = new CompoundNBT();
            nbt.put(TAG_RECIPE, mRecipeCapture.serializeNBT());
            nbt.putFloat(TAG_PROCESS, mProcess);
            nbt.putInt(TAG_ENERGY, mEnergyAmount);
            mTile.bindNbt.put(TAG, nbt);
        }
    }

    @Override
    public void resumeData() {
        if (mTile.bindNbt.contains(TAG, Constants.NBT.TAG_COMPOUND)) {
            CompoundNBT nbt = mTile.bindNbt.getCompound(TAG);
            mRecipeCapture = new RecipeCapture(nbt.getCompound(TAG_RECIPE));
            mProcess = nbt.getFloat(TAG_PROCESS);
            mEnergyAmount = nbt.getInt(TAG_ENERGY);
            mTile.bindNbt.remove(TAG);
        }
    }

    @Override
    protected int getInterval() {
        return 5;
    }

    protected int getEnergySpeed() {
        return Config.genFluidSpeed.get() * getInterval();
    }
}
