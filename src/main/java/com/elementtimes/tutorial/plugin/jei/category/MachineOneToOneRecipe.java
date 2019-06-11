package com.elementtimes.tutorial.plugin.jei.category;

import com.elementtimes.tutorial.ElementTimes;
import com.elementtimes.tutorial.plugin.jei.wrapper.MachineRecipeWrapper;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

/**
 * 有一个物品输入和一个物品输出的 JEI 合成表界面兼容
 * 标题：jei.[mod id].[machine]
 * 材质：ResourceLocation([mod id], "textures/gui/5.png")
 * uv：43 15
 * wh：90 44
 * @author luqin2007
 */
public class MachineOneToOneRecipe implements IRecipeCategory<MachineRecipeWrapper> {

    private IDrawable mBackground;
    private String machine;
    private String id;

    public MachineOneToOneRecipe(IGuiHelper helper, String id, String machine) {
        mBackground = helper.createDrawable(new ResourceLocation(ElementTimes.MODID, "textures/gui/5.png"), 43, 15, 90, 44);
        this.machine = machine;
        this.id = id;
    }

    @Nonnull
    @Override
    public String getUid() {
        return id;
    }

    @Nonnull
    @Override
    public String getTitle() {
        return I18n.format(String.format("jei.%s.%s", ElementTimes.MODID, machine));
    }

    @Nonnull
    @Override
    public String getModName() {
        return ElementTimes.MODID;
    }

    @Nonnull
    @Override
    public IDrawable getBackground() {
        return mBackground;
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, MachineRecipeWrapper recipeWrapper, @Nonnull IIngredients ingredients) {
        IGuiItemStackGroup itemStacks = recipeLayout.getItemStacks();
        recipeWrapper.getIngredients(ingredients);
        itemStacks.init(0, true, 12, 14);
        itemStacks.init(1, false, 66, 14);
        itemStacks.set(ingredients);
    }
}
