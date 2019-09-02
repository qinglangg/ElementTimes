package com.elementtimes.tutorial.other.machineRecipe;

import com.elementtimes.elementcore.api.common.ECUtils;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.ToIntFunction;

/**
 * 代表一个实际的合成表。
 * 此合成表是确定的，输入 输出都已明确，唯有能量消耗不确定。
 * 主要是考虑到能量消耗可能因配置文件或其他原因随时更改，且能量准确值在确定消耗前用处不大
 * @author luqin2007
 */
public class MachineRecipeCapture implements INBTSerializable<NBTTagCompound> {
    public MachineRecipe recipe;
    public List<ItemStack> inputs;
    public List<ItemStack> outputs;
    public List<FluidStack> fluidInputs;
    public List<FluidStack> fluidOutputs;
    public ToIntFunction<MachineRecipeCapture> energy;
    public String name;

    private static final String NBT_RECIPE_NAME = "_recipe_name_";
    private static final String NBT_RECIPE_ITEM_INPUT = "_recipe_item_input_";
    private static final String NBT_RECIPE_ITEM_OUTPUT = "_recipe_item_output_";
    private static final String NBT_RECIPE_FLUID_INPUT = "_recipe_fluid_input_";
    private static final String NBT_RECIPE_FLUID_OUTPUT = "_recipe_fluid_output_";

    /**
     * 从 NBT 数据恢复合成表
     * @param nbt NBT 数据
     * @param handler 合成配方集合。主要从其中获取能量消耗的计算函数
     * @return 恢复的合成表
     */
    public static MachineRecipeCapture fromNBT(NBTTagCompound nbt, MachineRecipeHandler handler) {
        MachineRecipeCapture capture = new MachineRecipeCapture();
        capture.deserializeNBT(nbt);
        if (capture.name == null) {
            return null;
        }

        // 从 MachineRecipeHandler 获取能量信息
        handler.getMachineRecipes().stream()
                .filter(it -> it.name.equals(capture.name))
                .findFirst()
                .ifPresent(recipe -> capture.energy = recipe.energy);

        return capture;
    }

    /**
     * 仅用于 fromNbt 方法
     */
    private MachineRecipeCapture() {}

    /**
     * 根据输入输出，确定每个参与合成的物品/流体的实际种类、数量
     * @param recipe 选定的合成表
     * @param input 物品输入
     * @param fluids 流体输入
     */
    MachineRecipeCapture(MachineRecipe recipe, List<ItemStack> input, List<FluidStack> fluids) {
        this.recipe = recipe;
        this.energy = recipe.energy;
        this.name = recipe.name;

        this.inputs = new ArrayList<>(recipe.inputs.size());
        for (int i = 0; i < recipe.inputs.size(); i++) {
            inputs.add(i, recipe.inputs.get(i).getter.apply(recipe, input, fluids, i));
        }

        this.outputs = new ArrayList<>(recipe.outputs.size());
        for (int i = 0; i < recipe.outputs.size(); i++) {
            outputs.add(i, recipe.outputs.get(i).getter.apply(recipe, input, fluids, i));
        }

        this.fluidInputs = new ArrayList<>(recipe.fluidInputs.size());
        for (int i = 0; i < recipe.fluidInputs.size(); i++) {
            fluidInputs.add(i, recipe.fluidInputs.get(i).getter.apply(recipe, input, fluids, i));
        }

        this.fluidOutputs = new ArrayList<>(recipe.fluidOutputs.size());
        for (int i = 0; i < recipe.fluidOutputs.size(); i++) {
            fluidOutputs.add(i, recipe.fluidOutputs.get(i).getter.apply(recipe, input, fluids, i));
        }
    }

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound nbtRecipe = new NBTTagCompound();
        nbtRecipe.setString(NBT_RECIPE_NAME, name);
        nbtRecipe.setTag(NBT_RECIPE_ITEM_INPUT, ECUtils.item.toNBTList(inputs));
        nbtRecipe.setTag(NBT_RECIPE_FLUID_INPUT, ECUtils.fluid.toNbtList(fluidInputs));
        nbtRecipe.setTag(NBT_RECIPE_ITEM_OUTPUT, ECUtils.item.toNBTList(outputs));
        nbtRecipe.setTag(NBT_RECIPE_FLUID_OUTPUT, ECUtils.fluid.toNbtList(fluidOutputs));
        return nbtRecipe;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        if (nbt.hasKey(NBT_RECIPE_NAME)) {
            this.name = nbt.getString(NBT_RECIPE_NAME);

            this.inputs = !nbt.hasKey(NBT_RECIPE_ITEM_INPUT) ? Collections.emptyList()
                    : ECUtils.item.fromNBTList((NBTTagList) nbt.getTag(NBT_RECIPE_ITEM_INPUT));
            this.outputs = !nbt.hasKey(NBT_RECIPE_ITEM_OUTPUT) ? Collections.emptyList()
                    : ECUtils.item.fromNBTList((NBTTagList) nbt.getTag(NBT_RECIPE_ITEM_OUTPUT));
            this.fluidInputs = !nbt.hasKey(NBT_RECIPE_FLUID_INPUT) ? Collections.emptyList()
                    : ECUtils.fluid.fromNbtList((NBTTagList) nbt.getTag(NBT_RECIPE_FLUID_INPUT));
            this.fluidOutputs = !nbt.hasKey(NBT_RECIPE_FLUID_OUTPUT) ? Collections.emptyList()
                    : ECUtils.fluid.fromNbtList((NBTTagList) nbt.getTag(NBT_RECIPE_FLUID_OUTPUT));
        }
    }
}
