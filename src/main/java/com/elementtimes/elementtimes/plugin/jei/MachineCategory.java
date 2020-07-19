package com.elementtimes.elementtimes.plugin.jei;

import com.elementtimes.elementcore.api.gui.*;
import com.elementtimes.elementcore.api.recipe.BaseRecipe;
import com.elementtimes.elementcore.api.recipe.IIngredient;
import com.elementtimes.elementcore.api.recipe.IngredientActionType;
import com.elementtimes.elementcore.api.tileentity.BaseTileEntity;
import com.elementtimes.elementcore.api.utils.CollectionUtils;
import com.elementtimes.elementtimes.common.block.gui.BaseGuiData;
import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IGuiFluidStackGroup;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 有一个物品输入和一个物品输出的 JEI 合成表界面兼容

 */
public class MachineCategory<R extends BaseRecipe, T extends BaseTileEntity> implements IRecipeCategory<R> {

    private final ResourceLocation uid;
    private final Class<R> recipeClass;
    private final String title;
    private final IDrawable background, icon;
    private final IGuiData<T> guiData;

    public MachineCategory(IGuiHelper helper, ResourceLocation uid, Class<R> recipeClass, Block machine, IGuiData<T> guiData, int u, int v, int w, int h) {
        this.uid = uid;
        this.recipeClass = recipeClass;
        this.title = "jei." + machine.getRegistryName();
        this.background = helper.createDrawable(guiData.getBackground(), u, v, w, h);
        this.icon = helper.createDrawableIngredient(new ItemStack(machine));
        this.guiData = guiData;
    }

    public MachineCategory(IGuiHelper helper, Class<R> recipeClass, BaseGuiData<T> data, int u, int v, int w, int h) {
        this(helper, Objects.requireNonNull(data.getBlock().getRegistryName()), recipeClass, data.getBlock(), data, u, v, w, h);
    }

    @Override
    public ResourceLocation getUid() {
        return uid;
    }

    @Override
    public Class<? extends R> getRecipeClass() {
        return recipeClass;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setIngredients(R recipe, IIngredients ingredients) {
        ingredients.setInputLists(VanillaTypes.ITEM, recipe.getItemInputs()
                .stream()
                .map(IIngredient::getAllValues)
                .map(Arrays::asList)
                .collect(Collectors.toList()));
        ingredients.setInputLists(VanillaTypes.FLUID, recipe.getFluidInputs()
                .stream()
                .map(IIngredient::getAllValues)
                .map(Arrays::asList)
                .collect(Collectors.toList()));
        ingredients.setOutputs(VanillaTypes.ITEM, CollectionUtils.toNonNullList(recipe.getItemOutputs(), ItemStack.EMPTY, (idx, i) -> i.toOutput(idx)));
        ingredients.setOutputs(VanillaTypes.FLUID, CollectionUtils.toNonNullList(recipe.getFluidOutputs(), FluidStack.EMPTY, (idx, i) -> i.toOutput(idx)));
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, R recipe, IIngredients ingredients) {
        applyItems(recipeLayout.getItemStacks(), recipe, ingredients);
        applyFluids(recipeLayout.getFluidStacks(), recipe, ingredients);
    }

    private void applyFluids(IGuiFluidStackGroup fluidLayout, R recipe, IIngredients ingredients) {
        int fluidPtr = 0, fluidInputPtr = 0, fluidOutputPtr = 0;
        Int2IntMap fluidSlotInput = new Int2IntOpenHashMap();
        Int2IntMap fluidSlotOutput = new Int2IntOpenHashMap();
        for (FluidSlot fluidSlot : guiData.getFluids()) {
            if (fluidSlot.type == FluidType.INGREDIENT) {
                fluidLayout.init(fluidPtr, true, fluidSlot.x, fluidSlot.y, fluidSlot.w, fluidSlot.h, 16000, true, null);
                fluidLayout.set(fluidPtr, ingredients.getInputs(VanillaTypes.FLUID).get(fluidInputPtr));
                fluidSlotInput.put(fluidPtr++, fluidInputPtr++);
            } else if (fluidSlot.type == FluidType.PRODUCT) {
                fluidLayout.init(fluidPtr, false, fluidSlot.x, fluidSlot.y, fluidSlot.w, fluidSlot.h, 16000, true, null);
                fluidLayout.set(fluidPtr, ingredients.getOutputs(VanillaTypes.FLUID).get(fluidOutputPtr));
                fluidSlotOutput.put(fluidPtr++, fluidOutputPtr++);
            }
        }
        fluidLayout.addTooltipCallback((slotIndex, input, ingredient, tooltip) -> {
            if (input) {
                int i = fluidSlotInput.get(slotIndex);
                recipe.getFluidInputs().get(i).editTooltips(tooltip, ingredient, IngredientActionType.INPUT);
            } else {
                int i = fluidSlotOutput.get(slotIndex);
                recipe.getFluidOutputs().get(i).editTooltips(tooltip, ingredient, IngredientActionType.OUTPUT);
            }
        });
    }

    private void applyItems(IGuiItemStackGroup itemLayout, R recipe, IIngredients ingredients) {
        int itemPtr = 0, itemInputPtr = 0, itemOutputPtr = 0;
        Int2IntMap itemSlotInput = new Int2IntOpenHashMap();
        Int2IntMap itemSlotOutput = new Int2IntOpenHashMap();
        for (ItemSlot itemSlot : guiData.getSlots()) {
            if (itemSlot.type == ItemType.INGREDIENT) {
                itemLayout.init(itemPtr, true, itemSlot.x, itemSlot.y);
                itemLayout.set(itemPtr, ingredients.getInputs(VanillaTypes.ITEM).get(itemInputPtr));
                itemSlotInput.put(itemPtr++, itemInputPtr++);
            } else if (itemSlot.type == ItemType.PRODUCT) {
                itemLayout.init(itemPtr, false, itemSlot.x, itemSlot.y);
                itemLayout.set(itemPtr, ingredients.getOutputs(VanillaTypes.ITEM).get(itemOutputPtr));
                itemSlotOutput.put(itemPtr++, itemOutputPtr++);
            }
        }
        itemLayout.addTooltipCallback((slotIndex, input, ingredient, tooltip) -> {
            if (input) {
                int i = itemSlotInput.get(slotIndex);
                recipe.getItemInputs().get(i).editTooltips(tooltip, ingredient, IngredientActionType.INPUT);
            } else {
                int i = itemSlotOutput.get(slotIndex);
                recipe.getItemOutputs().get(i).editTooltips(tooltip, ingredient, IngredientActionType.OUTPUT);
            }
        });
    }
}
