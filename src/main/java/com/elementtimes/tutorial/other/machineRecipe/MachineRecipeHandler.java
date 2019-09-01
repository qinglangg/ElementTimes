package com.elementtimes.tutorial.other.machineRecipe;

import com.elementtimes.elementcore.api.ECUtils;
import com.elementtimes.tutorial.ElementTimes;
import com.elementtimes.tutorial.common.init.ElementtimesFluids;
import com.elementtimes.tutorial.common.init.ElementtimesItems;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

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
     * 化学方程式解析
     * 会解析 水 熔岩 以及 ElementTimes 流体，特殊物品请在 parseCustomItem 中添加对应物品
     * @param name 合成配方名称，请保证仅包含
     * @param energy 消耗能量
     * @param ethanol 消耗酒精
     * @param chemicalFormula 化学方程式
     * @param baseAmount 输入流体的最小单位，即方程式配平后标记 1 的项的流体数量 mb
     * @return MachineRecipeBuilder
     */
    public MachineRecipeHandler add(String name, int energy, int ethanol, String chemicalFormula, int baseAmount) {
        final MachineRecipeBuilder builder = newRecipe(name).addCost(energy);
        chemicalFormula = chemicalFormula.trim().toLowerCase();
        final String[] split;
        if (chemicalFormula.equals("=")) {
            split = new String[] { null, null };
        } else if (chemicalFormula.startsWith("=")) {
            split = new String[] { null, chemicalFormula.substring(1) };
        } else if (chemicalFormula.endsWith("=")) {
            split = new String[] { chemicalFormula.substring(0, chemicalFormula.length() - 1), null };
        } else {
            split = chemicalFormula.split("=");
        }
        if (split.length != 2) {
            throw new RuntimeException("Input string must has and only has one \"=\"");
        }
        if (split[0] != null) {
            for (String input : split[0].split("\\+")) {
                IngredientPart<FluidStack> fluid = parseChemicalFluid(input, baseAmount);
                if (fluid == null) {
                    final IngredientPart<ItemStack> item = parseChemicalItem(input);
                    builder.addItemInput(item);
                } else {
                    builder.addFluidInput(fluid);
                }
            }
        }
        if (split[1] != null) {
            for (String output : split[1].split("\\+")) {
                IngredientPart<FluidStack> fluid = parseChemicalFluid(output, baseAmount);
                if (fluid == null) {
                    final IngredientPart<ItemStack> item = parseChemicalItem(output);
                    builder.addItemOutput(item);
                } else {
                    builder.addFluidOutput(fluid);
                }
            }
        }
        builder.addFluidInput(IngredientPart.forFluid(ElementtimesFluids.ethanol, ethanol));
        return builder.endAdd();
    }

    private Object[] parseCountAndName(String input) {
        final String inputGroup = input.trim().toLowerCase();
        final char[] chars = inputGroup.toCharArray();
        int ptr = 0;
        int count = 0;
        for (int i = 0; i < chars.length; i++) {
            if (Character.isDigit(chars[i])) {
                count = count * 10 + i;
            } else {
                ptr = i;
                break;
            }
        }
        if (ptr == 0 || count == 0) {
            count = 1;
        }
        String name = inputGroup.substring(ptr);
        return new Object[] {count, name};
    }
    private IngredientPart<FluidStack> parseChemicalFluid(String input, int base) {
        final Object[] cn = parseCountAndName(input);
        final Integer count = (Integer) cn[0];
        String fluidName = (String) cn[1];
        if (fluidName.equals("water") || fluidName.equals("h2o") || fluidName.equals("minecraft:water")) {
            return IngredientPart.forFluid(FluidRegistry.WATER, count * base);
        }
        if (fluidName.equals("lava") || fluidName.equals("minecraft:lava")) {
            return IngredientPart.forFluid(FluidRegistry.LAVA, count * base);
        }
        if (!fluidName.contains(":")) {
            fluidName = ElementTimes.MODID + ":" + fluidName;
        }
        final Fluid fluid = FluidRegistry.getFluid(fluidName);
        if (fluid != null) {
            return IngredientPart.forFluid(fluid, count * base);
        } else {
            return null;
        }
    }
    private IngredientPart<ItemStack> parseChemicalItem(String input) {
        final Object[] cn = parseCountAndName(input);
        final Integer count = (Integer) cn[0];
        final String itemName = (String) cn[1];
        ItemStack itemStack = parseCustomItem(itemName, count);
        if (itemStack.isEmpty()) {
            if (OreDictionary.doesOreNameExist(itemName)) {
                return IngredientPart.forItem(itemName, count);
            }
        }
        if (itemStack.isEmpty()) {
            itemStack = ECUtils.recipe.getFromItemName(itemName);
        }
        if (itemStack.isEmpty()) {
            itemStack = ECUtils.recipe.getFromItemName(ElementTimes.MODID + ":" + itemName);
        }
        if (itemStack.isEmpty()) {
            throw new RuntimeException("Can't parse fluid or item from input" + input);
        }
        itemStack = itemStack.copy();
        itemStack.setCount(count);
        return IngredientPart.forItem(itemStack);
    }
    private ItemStack parseCustomItem(String s, int count) {
        // s 为全小写
        switch (s) {
            case "caco3":
                return new ItemStack(ElementtimesItems.calciumCarbonate,1);
            case "cao":
                return new ItemStack(ElementtimesItems.calciumOxide, 1);
            default:
                return ItemStack.EMPTY;
        }
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
