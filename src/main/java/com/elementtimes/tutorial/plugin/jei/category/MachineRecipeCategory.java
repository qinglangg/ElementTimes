package com.elementtimes.tutorial.plugin.jei.category;

import com.elementtimes.tutorial.ElementTimes;
import com.elementtimes.tutorial.common.init.ElementtimesGUI;
import com.elementtimes.tutorial.interfaces.tileentity.IGuiProvider;
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

    public static MachineRecipeCategory createOneToOne(IGuiHelper helper, String id, String machine) {
        return new MachineRecipeCategory(helper, "5", id, machine, 44, 16, 90, 44,
                new int[][]{new int[] {55,29}, new int[]{109,29}}, new int[0][]);
    }

    public static MachineRecipeCategory createFromBaseMachine(IGuiHelper helper, ElementtimesGUI.Machines guiType,
                                                              int u, int v, int w, int h, int[][] itemXYs, int[][] fluidXYs) {
        final String name = guiType.name().toLowerCase();
        final String id = ElementTimes.MODID + "." + name + ".jei.category";
        return new MachineRecipeCategory(helper, guiType.texture(), id, name, u, v, w, h, itemXYs, fluidXYs);
    }

    public MachineRecipeCategory(IGuiHelper helper, String texture, String id, String machine,
                                 int u, int v, int width, int height,
                                 int[][] itemXYs, int[][] fluidXYs) {
        mBackground = helper.createDrawable(new ResourceLocation(ElementTimes.MODID, "textures/gui/" + texture + ".png"), u, v, width, height);
        this.machine = machine;
        this.id = id;
        for (int[] xy : itemXYs) {
            xy[0] -= u;
            xy[1] -= v;
        }
        for (int[] xy : fluidXYs) {
            xy[0] -= u;
            xy[1] -= v;
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
        int inputItemCount = recipeWrapper.inputItems.size();
        int inputFluidCount = recipeWrapper.inputFluids.size();
        for (int i = 0; i < items.length; i++) {
            itemStacks.init(i, i < inputItemCount, items[i][0], items[i][1]);
        }
        for (int i = 0; i < fluids.length; i++) {
            fluidStacks.init(i, i < inputFluidCount, fluids[i][0], fluids[i][1], 16, 46, 16000, true, null);
        }
        itemStacks.set(ingredients);
        fluidStacks.set(ingredients);
    }
}
