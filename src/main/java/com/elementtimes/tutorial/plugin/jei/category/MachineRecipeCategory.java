package com.elementtimes.tutorial.plugin.jei.category;

import com.elementtimes.tutorial.ElementTimes;
import com.elementtimes.tutorial.plugin.jei.wrapper.MachineRecipeWrapper;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiFluidStackGroup;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
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
public class MachineRecipeCategory implements IRecipeCategory<MachineRecipeWrapper> {

    private IDrawable mBackground;
    private String machine;
    private String id;
    private int[][] items;
    private int[][] fluids;

    public MachineRecipeCategory(IGuiHelper helper, String id, String machine) {
        this(helper, "5", id, machine, 43, 15, 90, 44,
                new int[][]{new int[] {56,30}, new int[]{110,30}}, new int[][]{});
    }

    public MachineRecipeCategory(IGuiHelper helper, String texture, String id, String machine, int u, int v, int width, int height, int[][] itemXYs, int[][] fluidXYs) {
        mBackground = helper.createDrawable(new ResourceLocation(ElementTimes.MODID, "textures/gui/" + texture + ".png"), u, v, width, height);
        this.machine = machine;
        this.id = id;
        for (int i = 0; i < itemXYs.length; i++) {
            int[] xy = itemXYs[i];
            xy[0] = xy[0] - u;
            xy[1] = xy[1] - v;
        }
        for (int i = 0; i < fluidXYs.length; i++) {
            int[] xy = fluidXYs[i];
            xy[0] = xy[0] - u;
            xy[1] = xy[1] - v;
        }
        items = itemXYs;
        fluids = fluidXYs;
    }

    @Nonnull
    @Override
    public String getUid() {
        return id;
    }

    @Nonnull
    @Override
    public String getTitle() {
        return net.minecraft.client.resources.I18n.format(String.format("jei.%s.%s", ElementTimes.MODID, machine));
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
        IGuiFluidStackGroup fluidStacks = recipeLayout.getFluidStacks();
        for (int i = 0; i < recipeWrapper.inputItems.size(); i++) {
            itemStacks.init(i, true, items[i][0], items[i][1]);
        }
        for (int i = 0; i < recipeWrapper.outputItems.size(); i++) {
            itemStacks.init(i, false,
                    fluids[i + recipeWrapper.inputItems.size()][0],
                    fluids[i + recipeWrapper.inputItems.size()][1]);
        }
        for (int i = 0; i < recipeWrapper.inputFluids.size(); i++) {
            fluidStacks.init(i, true, items[i][0], items[i][1]);
        }
        for (int i = 0; i < recipeWrapper.outputFluids.size(); i++) {
            fluidStacks.init(i, false,
                    fluids[i + recipeWrapper.outputFluids.size()][0],
                    fluids[i + recipeWrapper.outputFluids.size()][1]);
        }
        itemStacks.set(ingredients);
    }
}
