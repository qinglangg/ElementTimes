package com.elementtimes.tutorial.other;

import com.elementtimes.tutorial.interfaces.function.Function3;
import com.elementtimes.tutorial.interfaces.function.Function5;
import com.elementtimes.tutorial.util.FluidUtil;
import com.elementtimes.tutorial.util.ItemUtil;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nonnull;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;

/**
 * 保存合成表的类
 * @author luqin2007
 */
@SuppressWarnings("unused")
public class MachineRecipeHandler {
    
    private List<MachineRecipe> mMachineRecipes = new LinkedList<>();

    public MachineRecipeBuilder add(String name) {
        return new MachineRecipeBuilder(name);
    }

    public MachineRecipeHandler add(String name, int energy, String inputItemNameOrOreName, int inputCount, Item output, int outputCount) {
        return add(name)
                .addCost(energy)
                .addItemInput(IngredientPart.forItem(inputItemNameOrOreName, inputCount))
                .addItemOutput(IngredientPart.forItem(output, outputCount))
                .build();
    }

    public MachineRecipeHandler add(String name, int energy, Item input, int inputCount, Item output, int outputCount) {
        return add(name)
                .addCost(energy)
                .addItemInput(IngredientPart.forItem(input, inputCount))
                .addItemOutput(IngredientPart.forItem(output, outputCount)).build();
    }

    public MachineRecipeHandler add(String name, int energy, Block input, int inputCount, Item output, int outputCount) {
        return add(name)
                .addCost(energy)
                .addItemInput(IngredientPart.forItem(input, inputCount))
                .addItemOutput(IngredientPart.forItem(output, outputCount)).build();
    }

    public MachineRecipeHandler add(String name, int energy, Block input, int inputCount, Block output, int outputCount) {
        return add(name)
                .addCost(energy)
                .addItemInput(IngredientPart.forItem(input, inputCount))
                .addItemOutput(IngredientPart.forItem(output, outputCount)).build();
    }

    public MachineRecipeHandler add(String name, int energy, Item input, int inputCount, ItemStack output) {
        return add(name)
                .addCost(energy)
                .addItemInput(IngredientPart.forItem(input, inputCount))
                .addItemOutput(IngredientPart.forItem(output))
                .build();
    }

    public MachineRecipeHandler add(String name, int energy, ItemStack input, Item output, int outputCount) {
        return add(name)
                .addCost(energy)
                .addItemInput(IngredientPart.forItem(input))
                .addItemOutput(IngredientPart.forItem(output, outputCount))
                .build();
    }

    public MachineRecipeHandler add(String name, int energy, ItemStack input, ItemStack output) {
        return add(name)
                .addCost(energy)
                .addItemInput(IngredientPart.forItem(input))
                .addItemOutput(IngredientPart.forItem(output))
                .build();
    }

    public MachineRecipeHandler remove(MachineRecipe recipe) {
        mMachineRecipes.remove(recipe);
        return this;
    }

    public MachineRecipeHandler remove(String name) {
        mMachineRecipes.removeIf(recipe -> recipe.name.equals(name));
        return this;
    }

    public MachineRecipeHandler clear() {
        mMachineRecipes.clear();
        return this;
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

    public boolean accept(int slot, List<ItemStack> inputItems, List<FluidStack> inputFluids, ItemStack itemStack) {
        return mMachineRecipes.stream().anyMatch(r -> r.inputs.get(slot).matcher.apply(r, slot, inputItems, inputFluids, itemStack));
    }

    public boolean accept(int slot, List<ItemStack> inputItems, List<FluidStack> inputFluids, FluidStack fluid) {
        return mMachineRecipes.stream().anyMatch(r -> r.fluidInputs.get(slot).matcher.apply(r, slot, inputItems, inputFluids, fluid));
    }

    @SuppressWarnings("WeakerAccess")
    public class MachineRecipeBuilder {
        ToIntFunction<MachineRecipeCapture> cost = (a) -> 0;
        List<IngredientPart<ItemStack>> inputItems = new LinkedList<>();
        List<IngredientPart<FluidStack>> inputFluids = new LinkedList<>();
        List<IngredientPart<ItemStack>> outputItems = new LinkedList<>();
        List<IngredientPart<FluidStack>> outputFluids = new LinkedList<>();
        String name;

        private MachineRecipeBuilder(String name) {
            this.name = name;
        }

        public MachineRecipeBuilder addCost(int energyCost) {
            ToIntFunction<MachineRecipeCapture> c = cost;
            cost = (a) -> c.applyAsInt(a) + energyCost;
            return this;
        }

        public MachineRecipeBuilder addCost(ToIntFunction<MachineRecipeCapture> energyCost) {
            ToIntFunction<MachineRecipeCapture> c = cost;
            cost = (a) -> c.applyAsInt(a) + energyCost.applyAsInt(a);
            return this;
        }

        public MachineRecipeBuilder addItemInput(IngredientPart<ItemStack> item) {
            inputItems.add(item);
            return this;
        }

        @SafeVarargs
        public final MachineRecipeBuilder addItemInputs(IngredientPart<ItemStack>... items) {
            Collections.addAll(inputItems, items);
            return this;
        }

        public MachineRecipeBuilder addItemOutput(IngredientPart<ItemStack> item) {
            outputItems.add(item);
            return this;
        }

        @SafeVarargs
        public final MachineRecipeBuilder addItemOutputs(IngredientPart<ItemStack>... items) {
            Collections.addAll(outputItems, items);
            return this;
        }

        public MachineRecipeBuilder addFluidInput(IngredientPart<FluidStack> item) {
            inputFluids.add(item);
            return this;
        }

        @SafeVarargs
        public final MachineRecipeBuilder addFluidInputs(IngredientPart<FluidStack>... items) {
            Collections.addAll(inputFluids, items);
            return this;
        }

        public MachineRecipeBuilder addFluidOutput(IngredientPart<FluidStack> item) {
            outputFluids.add(item);
            return this;
        }

        @SafeVarargs
        public final MachineRecipeBuilder addFluidOutputs(IngredientPart<FluidStack>... items) {
            Collections.addAll(outputFluids, items);
            return this;
        }

        public MachineRecipeBuilder addItemInput(Predicate<ItemStack> inputCheck, Function<ItemStack, ItemStack> inputConvert) {
            Function3.Stack<ItemStack> get = (recipe, slot, input) -> inputConvert.apply(input);
            Function5.Match<ItemStack> match = (recipe, slot, inputItems1, inputFluids1, input) -> inputCheck.test(input);

            return addItemInput(new IngredientPart<>(match, get));
        }

        public MachineRecipeBuilder addItemOutput(Function3.Stack<ItemStack> outputGetter) {
            Function5.Match<ItemStack> matcher = (recipe, slot, inputItems1, inputFluids1, input) -> true;
            return addItemOutput(new IngredientPart<>(matcher, outputGetter));
        }

        public MachineRecipeHandler build() {
            MachineRecipe recipe = new MachineRecipe();
            recipe.name = name;
            recipe.energy = cost;
            recipe.inputs = inputItems;
            recipe.fluidInputs = inputFluids;
            recipe.outputs = outputItems;
            recipe.fluidOutputs = outputFluids;
            MachineRecipeHandler.this.mMachineRecipes.add(recipe);
            return MachineRecipeHandler.this;
        }
    }

    /**
     * 代表一个合成表
     */
    public static class MachineRecipe {
        public String name;
        public List<IngredientPart<ItemStack>> inputs;
        public List<IngredientPart<ItemStack>> outputs;
        public List<IngredientPart<FluidStack>> fluidInputs;
        public List<IngredientPart<FluidStack>> fluidOutputs;
        public ToIntFunction<MachineRecipeCapture> energy = (a) -> 0;

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

    /**
     * 代表一个实际的合成表。这时输入 输出 能量消耗已经定下来了
     */
    public static class MachineRecipeCapture implements INBTSerializable<NBTTagCompound> {
        public MachineRecipe recipe;
        public List<ItemStack> inputs;
        public List<ItemStack> outputs;
        public List<FluidStack> fluidInputs;
        public List<FluidStack> fluidOutputs;
        public ToIntFunction<MachineRecipeCapture> energy;
        public String name;

        public static String NBT_RECIPE_NAME = "_recipe_name_";
        public static String NBT_RECIPE_ITEM_INPUT = "_recipe_item_input_";
        public static String NBT_RECIPE_ITEM_OUTPUT = "_recipe_item_output_";
        public static String NBT_RECIPE_FLUID_INPUT = "_recipe_fluid_input_";
        public static String NBT_RECIPE_FLUID_OUTPUT = "_recipe_fluid_output_";

        public static MachineRecipeCapture fromNBT(NBTTagCompound nbt, MachineRecipeHandler handler) {
            MachineRecipeCapture capture = new MachineRecipeCapture();
            capture.deserializeNBT(nbt);
            if (capture.name == null) {
                return null;
            }
            capture.init(handler);
            return capture;
        }

        MachineRecipeCapture() {}

        MachineRecipeCapture(MachineRecipe recipe, List<ItemStack> input, List<FluidStack> fluids) {
            this.recipe = recipe;
            this.energy = recipe.energy;
            this.name = recipe.name;

            this.inputs = new ArrayList<>(recipe.inputs.size());
            for (int i = 0; i < recipe.inputs.size(); i++) {
                inputs.add(i, recipe.inputs.get(i).getter.apply(recipe, i, input.get(i)));
            }

            this.outputs = new ArrayList<>(recipe.outputs.size());
            for (int i = 0; i < recipe.outputs.size(); i++) {
                outputs.add(i, recipe.outputs.get(i).getter.apply(recipe, i, null));
            }

            this.fluidInputs = new ArrayList<>(recipe.fluidInputs.size());
            for (int i = 0; i < recipe.fluidInputs.size(); i++) {
                fluidInputs.add(i, recipe.fluidInputs.get(i).getter.apply(recipe, i, fluids.get(i)));
            }

            this.fluidOutputs = new ArrayList<>(recipe.fluidOutputs.size());
            for (int i = 0; i < recipe.fluidOutputs.size(); i++) {
                fluidOutputs.add(i, recipe.fluidOutputs.get(i).getter.apply(recipe, i, null));
            }
        }

        @Override
        public NBTTagCompound serializeNBT() {
            NBTTagCompound nbtRecipe = new NBTTagCompound();
            nbtRecipe.setString(NBT_RECIPE_NAME, recipe.name);
            nbtRecipe.setTag(NBT_RECIPE_ITEM_INPUT, ItemUtil.toNBTList(inputs));
            nbtRecipe.setTag(NBT_RECIPE_FLUID_INPUT, FluidUtil.toNBTList(fluidInputs));
            nbtRecipe.setTag(NBT_RECIPE_ITEM_OUTPUT, ItemUtil.toNBTList(outputs));
            nbtRecipe.setTag(NBT_RECIPE_FLUID_OUTPUT, FluidUtil.toNBTList(fluidOutputs));
            return nbtRecipe;
        }

        @Override
        public void deserializeNBT(NBTTagCompound nbt) {
            if (nbt.hasKey(NBT_RECIPE_NAME)) {
                this.name = nbt.getString(NBT_RECIPE_NAME);

                this.inputs = !nbt.hasKey(NBT_RECIPE_ITEM_INPUT) ? Collections.emptyList()
                        : ItemUtil.fromNBTList((NBTTagList) nbt.getTag(NBT_RECIPE_ITEM_INPUT));
                this.outputs = !nbt.hasKey(NBT_RECIPE_ITEM_OUTPUT) ? Collections.emptyList()
                        : ItemUtil.fromNBTList((NBTTagList) nbt.getTag(NBT_RECIPE_ITEM_OUTPUT));
                this.fluidInputs = !nbt.hasKey(NBT_RECIPE_FLUID_INPUT) ? Collections.emptyList()
                        : FluidUtil.fromNBTList((NBTTagList) nbt.getTag(NBT_RECIPE_FLUID_INPUT));
                this.fluidOutputs = !nbt.hasKey(NBT_RECIPE_FLUID_OUTPUT) ? Collections.emptyList()
                        : FluidUtil.fromNBTList((NBTTagList) nbt.getTag(NBT_RECIPE_FLUID_OUTPUT));
            }
        }

        public void init(MachineRecipeHandler handler) {
            Optional<MachineRecipe> recipe = handler.mMachineRecipes.stream()
                    .filter(it -> it.name.equals(this.name))
                    .findFirst();
            this.recipe = recipe.orElse(null);
            if (this.recipe != null) {
                this.energy = this.recipe.energy;
            }
        }
    }
}
