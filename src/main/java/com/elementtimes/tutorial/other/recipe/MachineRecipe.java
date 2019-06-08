package com.elementtimes.tutorial.other.recipe;

import com.elementtimes.tutorial.other.IngredientPart;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import java.util.List;
import java.util.function.ToIntFunction;

/**
 * 代表一个配方
 * 考虑到某些原料可被替代，该配方是不准确的
 * @author luqin2007
 */
@SuppressWarnings("WeakerAccess")
public class MachineRecipe {
    public String name;
    public List<IngredientPart<ItemStack>> inputs;
    public List<IngredientPart<ItemStack>> outputs;
    public List<IngredientPart<FluidStack>> fluidInputs;
    public List<IngredientPart<FluidStack>> fluidOutputs;
    public ToIntFunction<MachineRecipeCapture> energy = (a) -> 0;

    /**
     * 根据输入，从一个合成配方中获取匹配的准确合成表
     * @param input 输入物品
     * @param fluids 输入流体
     * @return 精准的合成表
     */
    public MachineRecipeCapture matchInput(List<ItemStack> input, List<FluidStack> fluids) {
        boolean match = input.size() >= inputs.size()
                && fluids.size() >= fluidInputs.size();
        // item
        for (int i = 0; match && i < inputs.size(); i++) {
            match = inputs.get(i).matcher.apply(this, i, input, fluids, input.get(i));
        }
        // fluid
        for (int i = 0; match && i < fluidInputs.size(); i++) {
            match = fluidInputs.get(i).matcher.apply(this, i, input, fluids, fluids.get(i));
        }
        // build
        return match ? new MachineRecipeCapture(this, input, fluids) : null;
    }
}