package com.elementtimes.tutorial.plugin.jei.category;

import com.elementtimes.tutorial.Elementtimes;
import com.elementtimes.tutorial.plugin.jei.wrapper.MachineItemRecipeWrapper;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;

public class MachineOneToOneRecipe implements IRecipeCategory<MachineItemRecipeWrapper> {

    private IDrawable mBackground;
    private String machine;
    private String id;

    public MachineOneToOneRecipe(IGuiHelper helper, String id, String machine) {
        mBackground = helper.createDrawable(new ResourceLocation(Elementtimes.MODID, "textures/gui/5.png"), 43, 15, 90, 44);
        this.machine = machine;
        this.id = id;
    }

    @Override
    public String getUid() {
        return id;
    }

    @Override
    public String getTitle() {
        return I18n.format(String.format("jei.%s.%s", Elementtimes.MODID, machine));
    }

    @Override
    public String getModName() {
        return Elementtimes.MODID;
    }

    @Override
    public IDrawable getBackground() {
        return mBackground;
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, MachineItemRecipeWrapper recipeWrapper, IIngredients ingredients) {
        IGuiItemStackGroup itemStacks = recipeLayout.getItemStacks();
        recipeWrapper.getIngredients(ingredients);
        itemStacks.init(0, true, 12, 14);
        itemStacks.init(1, false, 66, 14);
        itemStacks.set(ingredients);
    }
}
