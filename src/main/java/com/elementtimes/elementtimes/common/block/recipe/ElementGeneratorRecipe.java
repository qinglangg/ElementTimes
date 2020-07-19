package com.elementtimes.elementtimes.common.block.recipe;

import com.elementtimes.elementcore.api.recipe.BaseRecipe;
import com.elementtimes.elementcore.api.recipe.ItemStackIngredient;
import com.elementtimes.elementtimes.ElementTimes;
import net.minecraft.item.Item;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;



public class ElementGeneratorRecipe extends BaseRecipe {

    public static final BaseRecipeType<ElementGeneratorRecipe> TYPE = new BaseRecipeType<ElementGeneratorRecipe>() {};

    public ElementGeneratorRecipe(Item element, int energyAbs) {
        super(-energyAbs, 0f,
                NonNullList.withSize(1, new ItemStackIngredient(element)),
                NonNullList.create(),
                NonNullList.create(),
                NonNullList.create(),
                TYPE, new ResourceLocation(ElementTimes.MODID, "gen_elem_" + element.getRegistryName().getPath()));
    }
}
