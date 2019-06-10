package com.elementtimes.tutorial.other.recipe;

import com.elementtimes.tutorial.other.IngredientPart;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
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
     * 所有 add 方法重载最终都会通过该方法添加
     * @param name 合成配方名称
     * @return MachineRecipeBuilder
     */
    public MachineRecipeBuilder add(String name) {
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
        return add(name)
                .addCost(energy)
                .addItemInput(IngredientPart.forItem(inputItemNameOrOreName, inputCount))
                .addItemOutput(IngredientPart.forItem(output, outputCount))
                .build();
    }

    /**
     * 创建一个合成表，只有一个输入，每次消耗一个物品，没有输出
     * @param name 配方名
     * @param energy 能量消耗，产能则为负
     * @param input 输入物品
     * @return MachineRecipeHandler
     */
    public MachineRecipeHandler add(String name, ToIntFunction<MachineRecipeCapture> energy, Item input) {
        return add(name)
                .addCost(energy)
                .addItemInput(IngredientPart.forItem(input, 1))
                .build();
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
        return add(name)
                .addCost(energy)
                .addItemInput(IngredientPart.forItem(input, inputCount))
                .addItemOutput(IngredientPart.forItem(output, outputCount))
                .build();
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
        return add(name)
                .addCost(energy)
                .addItemInput(IngredientPart.forItem(input, inputCount))
                .addItemOutput(IngredientPart.forItem(output, outputCount))
                .build();
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
        return add(name)
                .addCost(energy)
                .addItemInput(IngredientPart.forItem(input, inputCount))
                .addItemOutput(IngredientPart.forItem(output, outputCount))
                .build();
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
        return add(name)
                .addCost(energy)
                .addItemInput(IngredientPart.forItem(input, inputCount))
                .addItemOutput(IngredientPart.forItem(output))
                .build();
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
        return add(name)
                .addCost(energy)
                .addItemInput(IngredientPart.forItem(input))
                .addItemOutput(IngredientPart.forItem(output, outputCount))
                .build();
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
        return add(name)
                .addCost(energy)
                .addItemInput(IngredientPart.forItem(input))
                .addItemOutput(IngredientPart.forItem(output))
                .build();
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
     * 判断对应槽位是否可接收物品
     * 以后可能会挪到 TileEntity 中，或者在这里进行更严格的检查。
     * 现在机器都只有一个槽 先这样
     *
     * @param slot 槽位
     * @param inputItems 已输入物品
     * @param inputFluids 已输入流体
     * @param itemStack 待检查物品
     * @return 是否可放入该槽位
     */
    public boolean accept(int slot, List<ItemStack> inputItems, List<FluidStack> inputFluids, ItemStack itemStack) {
        return mMachineRecipes.stream().anyMatch(r -> r.inputs.get(slot).matcher.apply(r, slot, inputItems, inputFluids, itemStack));
    }

    /**
     * 判断对应槽位是否可接收流体
     * 以后可能会挪到 TileEntity 中，或者在这里进行更严格的检查。
     * 现在机器都只有一个槽 先这样
     *
     * @param slot 槽位
     * @param inputItems 已输入物品
     * @param inputFluids 已输入流体
     * @param fluid 待检查流体
     * @return 是否可放入该槽位
     */
    public boolean accept(int slot, List<ItemStack> inputItems, List<FluidStack> inputFluids, FluidStack fluid) {
        return mMachineRecipes.stream().anyMatch(r -> r.fluidInputs.get(slot).matcher.apply(r, slot, inputItems, inputFluids, fluid));
    }
}
