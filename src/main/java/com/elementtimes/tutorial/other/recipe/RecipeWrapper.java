package com.elementtimes.tutorial.other.recipe;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * 对合成表的包装类
 * @author luqin2007
 */
public class RecipeWrapper implements IRecipe {

    private IRecipe mRecipe;

    @SuppressWarnings("WeakerAccess")
    public RecipeWrapper(IRecipe recipe) {
        mRecipe = recipe;
    }

    public IRecipe getRecipe() {
        return mRecipe;
    }

    // IRecipe
    @Override
    public boolean matches(@Nonnull InventoryCrafting inv, @Nonnull World worldIn) {
        return mRecipe.matches(inv, worldIn);
    }

    @Nonnull
    @Override
    public ItemStack getCraftingResult(@Nonnull InventoryCrafting inv) {
        return mRecipe.getCraftingResult(inv);
    }

    @Override
    public boolean canFit(int width, int height) {
        return mRecipe.canFit(width, height);
    }

    @Nonnull
    @Override
    public ItemStack getRecipeOutput() {
        return mRecipe.getRecipeOutput();
    }

    @Nonnull
    @Override
    public NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv) {
        return mRecipe.getRemainingItems(inv);
    }

    @Nonnull
    @Override
    public NonNullList<Ingredient> getIngredients() {
        return mRecipe.getIngredients();
    }

    @Override
    public boolean isDynamic() {
        return mRecipe.isDynamic();
    }

    @Nonnull
    @Override
    public String getGroup() {
        return mRecipe.getGroup();
    }

    // IForgeRegistryEntry
    @Override
    public IRecipe setRegistryName(ResourceLocation name) {
        mRecipe.setRegistryName(name);
        return this;
    }

    @Nullable
    @Override
    public ResourceLocation getRegistryName() {
        return mRecipe.getRegistryName();
    }

    @Override
    public Class<IRecipe> getRegistryType() {
        return mRecipe.getRegistryType();
    }
}
