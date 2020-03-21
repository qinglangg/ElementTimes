package com.elementtimes.tutorial.plugin.jei.category;

import com.elementtimes.elementcore.api.template.tileentity.SideHandlerType;
import com.elementtimes.elementcore.api.template.tileentity.interfaces.IGuiProvider;
import com.elementtimes.elementcore.api.template.tileentity.interfaces.ITileItemHandler;
import com.elementtimes.tutorial.ElementTimes;
import com.elementtimes.tutorial.plugin.jei.wrapper.MachineRecipeWrapper;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiFluidStackGroup;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

/**
 * 有一个物品输入和一个物品输出的 JEI 合成表界面兼容
 * 标题：jei.[mod id].[machine]
 * 材质：ResourceLocation([mod id], "textures/gui/5.png")
 * uv：43 15
 * wh：90 44
 * @author luqin2007
 */
public class MachineRecipeCategory implements IRecipeCategory<MachineRecipeWrapper> {

    private IDrawable background;
    private String id;
    private String title;
    private int[][] items;
    private int[][] fluids;

    public MachineRecipeCategory(IGuiHelper helper, IGuiProvider gui, String id, int u, int v, int w, int h) {
        this(helper, gui, id, u, v, w, h, -1);
    }

    public MachineRecipeCategory(IGuiHelper helper, IGuiProvider gui, String id, int u, int v, int w, int h, int fixOffset) {
        this.background = helper.createDrawable(gui.getBackground(), u, v, w, h);
        this.id = id;
        this.title = gui.getTitle();
        IGuiProvider.FluidSlotInfo[] fluidSlots = gui.getFluids();
        int fluidLength = fluidSlots.length;
        this.fluids = new int[fluidLength][];
        for (int i = 0; i < fluidLength; i++) {
            IGuiProvider.FluidSlotInfo slot = fluidSlots[i];
            fluids[i] = new int[] { slot.x - u, slot.y - v };
        }
        Slot[] slots = gui.getSlots();
        if (gui instanceof ITileItemHandler && fluidLength > 0) {
            int inputCount = ((ITileItemHandler) gui).getItemHandler(SideHandlerType.INPUT).getSlots();
            int allowInputCount = inputCount - fluidLength;
            int allowOutputCount = slots.length - inputCount - fluidLength;
            int slotCount = allowInputCount + allowOutputCount;
            this.items = new int[slotCount][];
            for (int i = 0; i < allowInputCount; i++) {
                Slot slot = slots[i];
                items[i] = new int[] { slot.xPos - u + fixOffset, slot.yPos - v + fixOffset };
            }
            for (int i = 0; i < allowOutputCount; i++) {
                Slot slot = slots[i + inputCount];
                items[i + allowInputCount] = new int[] { slot.xPos - u + fixOffset, slot.yPos - v + fixOffset };
            }
        } else {
            this.items = new int[slots.length][];
            for (int i = 0; i < slots.length; i++) {
                Slot slot = slots[i];
                items[i] = new int[] { slot.xPos - u + fixOffset, slot.yPos - v + fixOffset };
            }
        }
    }

    @Nonnull
    @Override
    public String getUid() {
        return id;
    }

    @Nonnull
    @Override
    public String getTitle() {
        return title;
    }

    @Nonnull
    @Override
    public String getModName() {
        return ElementTimes.MODID;
    }

    @Nonnull
    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, MachineRecipeWrapper recipeWrapper, @Nonnull IIngredients ingredients) {
        int inputItemCount = recipeWrapper.inputItems.size();
        int inputFluidCount = recipeWrapper.inputFluids.size();
        recipeLayout.getItemStacks().addTooltipCallback((slotIndex, input, ingredient, tooltip) -> {
            List<Map<ItemStack, Supplier<List<String>>>> stringMaps = input ? recipeWrapper.itemInputString : recipeWrapper.itemOutputString;
            tooltip.addAll(stringMaps.get(input ? slotIndex : slotIndex - inputItemCount).get(ingredient).get());
        });
        recipeLayout.getFluidStacks().addTooltipCallback((slotIndex, input, ingredient, tooltip) -> {
            List<Map<FluidStack, Supplier<List<String>>>> stringMaps = input ? recipeWrapper.fluidInputString : recipeWrapper.fluidOutputString;
            tooltip.addAll(stringMaps.get(input ? slotIndex : slotIndex - inputFluidCount).get(ingredient).get());
        });
        IGuiItemStackGroup itemStacks = recipeLayout.getItemStacks();
        IGuiFluidStackGroup fluidStacks = recipeLayout.getFluidStacks();
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
