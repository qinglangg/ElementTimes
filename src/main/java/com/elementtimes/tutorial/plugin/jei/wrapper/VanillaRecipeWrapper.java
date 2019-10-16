package com.elementtimes.tutorial.plugin.jei.wrapper;

import com.elementtimes.tutorial.other.recipe.RecipeWrapper;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IStackHelper;
import mezz.jei.api.recipe.wrapper.ICraftingRecipeWrapper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.IShapedRecipe;

import javax.annotation.Nullable;
import java.util.List;

/**
 * 对！抄的就是 ShapelessRecipeWrapper（谁让你不给我用呢）
 * @author luqin2007
 */
public class VanillaRecipeWrapper implements ICraftingRecipeWrapper {

    private final IJeiHelpers jeiHelpers;
    protected final RecipeWrapper recipe;

    public static VanillaRecipeWrapper create(IModRegistry registry, RecipeWrapper recipe) {
        if (recipe.getRecipe() instanceof IShapedRecipe) {
            return new VanillaShapedRecipeWrapper(registry.getJeiHelpers(), recipe);
        } else {
            return new VanillaRecipeWrapper(registry.getJeiHelpers(), recipe);
        }
    }

    public VanillaRecipeWrapper(IJeiHelpers jeiHelpers, RecipeWrapper recipe) {
        this.jeiHelpers = jeiHelpers;
        this.recipe = recipe;
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ItemStack recipeOutput = recipe.getRecipeOutput();
        IStackHelper stackHelper = jeiHelpers.getStackHelper();

        try {
            List<List<ItemStack>> inputLists = stackHelper.expandRecipeItemStackInputs(recipe.getIngredients());
            ingredients.setInputLists(VanillaTypes.ITEM, inputLists);
            ingredients.setOutput(VanillaTypes.ITEM, recipeOutput);
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    @Nullable
    @Override
    public ResourceLocation getRegistryName() {
        return recipe.getRegistryName();
    }
}
