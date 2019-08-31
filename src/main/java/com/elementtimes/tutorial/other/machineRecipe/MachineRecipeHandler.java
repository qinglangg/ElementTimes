package com.elementtimes.tutorial.other.machineRecipe;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nonnull;
import java.util.LinkedList;
import java.util.List;
import java.util.function.ToIntFunction;

/**
 * 保存合成配方的类
 * @author luqin2007
 */
public class MachineRecipeHandler {
    
    private List<MachineRecipe> mMachineRecipes = new LinkedList<>();

    /**
     * 获取所有合成配方
     * @return 配方列表
     */
    public List<MachineRecipe> getMachineRecipes() {
        return mMachineRecipes;
    }

    /**
     * 创建一个 MachineRecipeBuilder 辅助创建新的合成配方。
     * 所有 newRecipe 方法重载最终都会通过该方法添加
     * @param name 合成配方名称
     * @return MachineRecipeBuilder
     */
    public MachineRecipeBuilder newRecipe(String name) {
        return new MachineRecipeBuilder(name, this);
    }

    /**
     * 创建一个合成表，根据矿辞或 id 输入一类物品，输出一类物品
     * @param name 配方名
     * @param energy 消耗能量，产能则为负
     * @param inputItemNameOrOreName 输入物品矿辞名称或 RegisterName
     * @param inputCount 输入物品数量
     * @param output 输出物品
     * @param outputCount 输出物品数量
     * @return MachineRecipeHandler
     */
    public MachineRecipeHandler add(String name, int energy, String inputItemNameOrOreName, int inputCount, Item output, int outputCount) {
        return newRecipe(name)
                .addCost(energy)
                .addItemInput(IngredientPart.forItem(inputItemNameOrOreName, inputCount))
                .addItemOutput(IngredientPart.forItem(output, outputCount))
                .endAdd();
    }

    /**
     * 创建一个合成表，只有一个输入，每次消耗一个物品，没有输出
     * @param name 配方名
     * @param energy 能量消耗，产能则为负
     * @param input 输入物品
     * @return MachineRecipeHandler
     */
    public MachineRecipeHandler add(String name, ToIntFunction<MachineRecipeCapture> energy, Item input) {
        return newRecipe(name)
                .addCost(energy)
                .addItemInput(IngredientPart.forItem(input, 1))
                .endAdd();
    }

    /**
     * 创建一个合成表，有两个流体输入和一个流体输出
     * @param name 配方名
     * @param energy 能量消耗，产能则为负
     * @param input1 输入流体1
     * @param input2 输入流体2
     * @param output 输出流体
     * @return MachineRecipeHandler
     */
    public MachineRecipeHandler add(String name, int energy, Fluid input1, Fluid input2, Fluid output) {
        return newRecipe(name)
                .addCost(energy)
                .addFluidInput(IngredientPart.forFluid(input1, Fluid.BUCKET_VOLUME))
                .addFluidInput(IngredientPart.forFluid(input2, Fluid.BUCKET_VOLUME))
                .addFluidOutput(IngredientPart.forFluid(output, Fluid.BUCKET_VOLUME))
                .endAdd();
    }

    /**
     * 创建一个合成表，有一个输入和一个输出
     * @param name 配方名
     * @param energy 消耗能量，产能则为负
     * @param input 输入物品
     * @param inputCount 输入物品数量
     * @param output 输出物品
     * @param outputCount 输出物品数量
     * @return MachineRecipeHandler
     */
    public MachineRecipeHandler add(String name, int energy, Item input, int inputCount, Item output, int outputCount) {
        return newRecipe(name)
                .addCost(energy)
                .addItemInput(IngredientPart.forItem(input, inputCount))
                .addItemOutput(IngredientPart.forItem(output, outputCount))
                .endAdd();
    }

    /**
     * 创建一个合成表，有一个输入和一个输出，输入是方块
     * @param name 配方名
     * @param energy 消耗能量，产能则为负
     * @param input 输入物品
     * @param inputCount 输入物品数量
     * @param output 输出物品
     * @param outputCount 输出物品数量
     * @return MachineRecipeHandler
     */
    public MachineRecipeHandler add(String name, int energy, Block input, int inputCount, Item output, int outputCount) {
        return newRecipe(name)
                .addCost(energy)
                .addItemInput(IngredientPart.forItem(input, inputCount))
                .addItemOutput(IngredientPart.forItem(output, outputCount))
                .endAdd();
    }

    /**
     * 创建一个合成表，有一个输入和一个输出，输入和输出都是方块
     * @param name 配方名
     * @param energy 消耗能量，产能则为负
     * @param input 输入物品
     * @param inputCount 输入物品数量
     * @param output 输出物品
     * @param outputCount 输出物品数量
     * @return MachineRecipeHandler
     */
    public MachineRecipeHandler add(String name, int energy, Block input, int inputCount, Block output, int outputCount) {
        return newRecipe(name)
                .addCost(energy)
                .addItemInput(IngredientPart.forItem(input, inputCount))
                .addItemOutput(IngredientPart.forItem(output, outputCount))
                .endAdd();
    }

    /**
     * 创建一个合成表，有一个输入和一个输出
     * @param name 配方名
     * @param energy 消耗能量，产能则为负
     * @param input 输入物品
     * @param inputCount 输入物品数量
     * @param output 输出物品栈
     * @return MachineRecipeHandler
     */
    public MachineRecipeHandler add(String name, int energy, Item input, int inputCount, ItemStack output) {
        return newRecipe(name)
                .addCost(energy)
                .addItemInput(IngredientPart.forItem(input, inputCount))
                .addItemOutput(IngredientPart.forItem(output))
                .endAdd();
    }

    /**
     * 创建一个合成表，有一个输入和一个输出
     * @param name 配方名
     * @param energy 消耗能量，产能则为负
     * @param input 输入物品栈
     * @param output 输出物品
     * @param outputCount 输出物品数量
     * @return MachineRecipeHandler
     */
    public MachineRecipeHandler add(String name, int energy, ItemStack input, Item output, int outputCount) {
        return newRecipe(name)
                .addCost(energy)
                .addItemInput(IngredientPart.forItem(input))
                .addItemOutput(IngredientPart.forItem(output, outputCount))
                .endAdd();
    }

    /**
     * 创建一个合成表，有一个输入和一个输出
     * @param name 配方名
     * @param energy 消耗能量，产能则为负
     * @param input 输入物品栈
     * @param output 输出物品栈
     * @return MachineRecipeHandler
     */
    public MachineRecipeHandler add(String name, int energy, ItemStack input, ItemStack output) {
        return newRecipe(name)
                .addCost(energy)
                .addItemInput(IngredientPart.forItem(input))
                .addItemOutput(IngredientPart.forItem(output))
                .endAdd();
    }

    public MachineRecipeHandler add(String name, int energy, Item input, Fluid output) {
        return newRecipe(name)
                .addCost(energy)
                .addItemInput(IngredientPart.forItem(input, 1))
                .addFluidOutput(IngredientPart.forFluid(output, Fluid.BUCKET_VOLUME))
                .endAdd();
    }

    public MachineRecipeHandler add(String name, int energy, Block input, Fluid output) {
        return newRecipe(name)
                .addCost(energy)
                .addItemInput(IngredientPart.forItem(input, 1))
                .addFluidOutput(IngredientPart.forFluid(output, Fluid.BUCKET_VOLUME))
                .endAdd();
    }

    public MachineRecipeHandler add(String name, int energy, Fluid input, Fluid output) {
        return newRecipe(name)
                .addCost(energy)
                .addFluidInput(IngredientPart.forFluid(input, Fluid.BUCKET_VOLUME))
                .addFluidOutput(IngredientPart.forFluid(output, Fluid.BUCKET_VOLUME))
                .endAdd();
    }

    /**
     * 匹配输入符合的合成表
     * @return 筛选出的符合要求的合成表
     */
    @Nonnull
    public MachineRecipeCapture[] matchInput(List<ItemStack> input, List<FluidStack> fluids) {
        List<MachineRecipeCapture> captures = new LinkedList<>();
        for (MachineRecipe recipe : mMachineRecipes) {
            MachineRecipeCapture capture = recipe.matchInput(input, fluids);
            if (capture != null) {
                captures.add(capture);
            }
        }
        return captures.toArray(new MachineRecipeCapture[0]);
    }

    /**
     * 可能输入有对应合成表
     * @return 是否可能有合成
     */
    public boolean acceptInput(List<ItemStack> input, List<FluidStack> fluids) {
        for (MachineRecipe recipe : mMachineRecipes) {
           if (recipe.checkInput(input, fluids)) {
               return true;
           }
        }
        return false;
    }
}
