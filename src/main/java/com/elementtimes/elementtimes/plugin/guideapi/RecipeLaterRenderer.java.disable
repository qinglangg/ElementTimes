package com.elementtimes.tutorial.plugin.guideapi;

import amerifrance.guideapi.api.IRecipeRenderer;
import amerifrance.guideapi.api.impl.Book;
import amerifrance.guideapi.api.impl.abstraction.CategoryAbstract;
import amerifrance.guideapi.api.impl.abstraction.EntryAbstract;
import amerifrance.guideapi.gui.GuiBase;
import amerifrance.guideapi.page.reciperenderer.ShapedOreRecipeRenderer;
import amerifrance.guideapi.page.reciperenderer.ShapedRecipesRenderer;
import amerifrance.guideapi.page.reciperenderer.ShapelessOreRecipeRenderer;
import amerifrance.guideapi.page.reciperenderer.ShapelessRecipesRenderer;
import com.elementtimes.tutorial.ElementTimes;
import com.google.gson.JsonObject;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.JsonContext;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

public class RecipeLaterRenderer implements IRecipeRenderer {

    private Supplier<IRecipe> mRecipeBuilder;
    private IRecipeRenderer mRenderer = null;
    private IRecipe mRecipe = null;

    public RecipeLaterRenderer(JsonObject json) {
        mRecipeBuilder = () -> {
            IRecipe recipe;
            if (json.has("id")) {
                recipe = ForgeRegistries.RECIPES.getValue(new ResourceLocation(JsonUtils.getString(json, "id")));
            } else if (json.has("recipe")) {
                recipe = CraftingHelper.getRecipe(json.get("recipe").getAsJsonObject(), new JsonContext(ElementTimes.MODID));
            } else {
                recipe = new ShapelessRecipes("empty", ItemStack.EMPTY, NonNullList.create());
            }
            return recipe;
        };
    }

    @Nonnull
    public IRecipe getRecipe() {
        if (mRecipe == null) {
            mRecipe = mRecipeBuilder.get();
            IRecipe recipe = mRecipe == null ? new ShapelessRecipes("empty", ItemStack.EMPTY, NonNullList.create()) : mRecipe;
            if (recipe instanceof ShapedRecipes) {
                mRenderer = new ShapedRecipesRenderer((ShapedRecipes) recipe);
            } else if (recipe instanceof ShapelessRecipes) {
                mRenderer = new ShapelessRecipesRenderer((ShapelessRecipes) recipe);
            } else if (recipe instanceof ShapedOreRecipe) {
                mRenderer = new ShapedOreRecipeRenderer((ShapedOreRecipe) recipe);
            } else {
                mRenderer = new ShapelessOreRecipeRenderer((ShapelessOreRecipe) recipe);
            }
        }
        return mRecipe;
    }

    @Override
    public void draw(Book book, CategoryAbstract category, EntryAbstract entry, int guiLeft, int guiTop, int mouseX, int mouseY, GuiBase guiBase, FontRenderer fontRendererObj) {
        getRecipe();
        mRenderer.draw(book, category, entry, guiLeft, guiTop, mouseX, mouseY, guiBase, fontRendererObj);
    }

    @Override
    public void drawExtras(Book book, CategoryAbstract category, EntryAbstract entry, int guiLeft, int guiTop, int mouseX, int mouseY, GuiBase guiBase, FontRenderer fontRendererObj) {
        getRecipe();
        mRenderer.drawExtras(book, category, entry, guiLeft, guiTop, mouseX, mouseY, guiBase, fontRendererObj);
    }
}
