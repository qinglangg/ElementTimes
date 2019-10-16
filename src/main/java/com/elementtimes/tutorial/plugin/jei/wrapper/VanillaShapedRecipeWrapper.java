package com.elementtimes.tutorial.plugin.jei.wrapper;

import com.elementtimes.tutorial.other.recipe.RecipeWrapper;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.recipe.wrapper.IShapedCraftingRecipeWrapper;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.common.crafting.IShapedRecipe;

/**
 * 替代 ShapedRecipesWrapper 和 ShapedOreRecipesWrapper
 * @author luqin2007
 */
public class VanillaShapedRecipeWrapper extends VanillaRecipeWrapper implements IShapedCraftingRecipeWrapper {

    public VanillaShapedRecipeWrapper(IJeiHelpers jeiHelpers, RecipeWrapper recipe) {
        super(jeiHelpers, recipe);
    }

    @Override
    public int getWidth() {
        IRecipe rRecipe = this.recipe.getRecipe();
        if (rRecipe instanceof IShapedRecipe) {
            return ((IShapedRecipe) rRecipe).getRecipeWidth();
        }
        return 3;
    }

    @Override
    public int getHeight() {
        IRecipe rRecipe = this.recipe.getRecipe();
        if (rRecipe instanceof IShapedRecipe) {
            return ((IShapedRecipe) rRecipe).getRecipeHeight();
        }
        return 3;
    }
}
