package com.elementtimes.tutorial.interfaces.tileentity;

import com.elementtimes.tutorial.common.capability.impl.TankHandler;
import com.elementtimes.tutorial.other.recipe.MachineRecipeCapture;
import com.elementtimes.tutorial.other.recipe.MachineRecipeHandler;
import com.elementtimes.tutorial.util.FluidUtil;
import com.elementtimes.tutorial.util.ItemUtil;
import it.unimi.dsi.fastutil.ints.IntSet;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

/**
 * 提供合成表及一系列方法
 * @author luqin2007
 */
public interface IMachineRecipe extends INBTSerializable<NBTTagCompound> {
    String NBT_RECIPE = "_recipe_";

    /**
     * 获取合成表
     */
    MachineRecipeHandler getRecipes();

    /**
     * 更新合成表
     */
    void updateRecipes();

    /**
     * 提供合成表
     */
    @Nonnull
    MachineRecipeHandler createRecipe();

    /**
     * 根据所在上下文获取可行的下一个合成表
     * 通常这里只检查输入量是否足够
     */
    @Nullable
    default MachineRecipeCapture getNextRecipe(IItemHandler input, TankHandler tankHandler) {
        List<ItemStack> items = ItemUtil.toList(input, getRecipeSlotIgnore());
        List<FluidStack> fluids = FluidUtil.toListNotNull(tankHandler.getTankProperties());
        MachineRecipeCapture[] captures = getRecipes().matchInput(items, fluids);
        if (captures.length == 0) {
            return null;
        }
        return captures[0];
    }

    /**
     * 根据所在上下文获取正在执行的合成表
     */
    @Nullable
    MachineRecipeCapture getWorkingRecipe();

    /**
     * @return 合成表检查时忽略的机器槽位
     */
    @Nonnull
    IntSet getRecipeSlotIgnore();

    /**
     * 设置正在执行的合成表
     */
    void setWorkingRecipe(MachineRecipeCapture capture);

    @Override
    default NBTTagCompound serializeNBT() {
        return writeToNBT(new NBTTagCompound());
    }

    @Override
    default void deserializeNBT(NBTTagCompound nbt) {
        if (nbt.hasKey(NBT_RECIPE) && getRecipes() != null) {
            NBTTagCompound recipe = nbt.getCompoundTag(NBT_RECIPE);
            MachineRecipeCapture capture =
                    MachineRecipeCapture.fromNBT(recipe, getRecipes());
            if (capture != null) {
                setWorkingRecipe(capture);
            }
        }
    }

    default NBTTagCompound writeToNBT(NBTTagCompound nbtTagCompound) {
        MachineRecipeCapture recipe = getWorkingRecipe();
        if (recipe != null) {
            NBTTagCompound nbt = recipe.serializeNBT();
            nbtTagCompound.setTag(NBT_RECIPE, nbt);
        }
        return nbtTagCompound;
    }
}
