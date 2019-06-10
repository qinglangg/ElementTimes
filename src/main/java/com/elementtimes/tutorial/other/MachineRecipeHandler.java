package com.elementtimes.tutorial.other;

import com.elementtimes.tutorial.interfaces.function.Function3;
import com.elementtimes.tutorial.util.FluidUtil;
import com.elementtimes.tutorial.util.ItemUtil;
import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import net.minecraftforge.items.ItemHandlerHelper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;

import static org.jline.utils.Log.warn;

/**
 * 保存合成表的类
 * @author luqin2007
 */
@SuppressWarnings("unused")
public class MachineRecipeHandler {
    
    private List<MachineRecipe> mMachineRecipes = new LinkedList<>();

    /**
     * 添加一个合成表。
     * input/output 支持
     *  Item/Block：添加一个物品
     *  ItemStack：添加一个物品栈
     *  String：以 [ore][id][all] 开头可强制只检查矿辞/RegistryName/二者都检查，否则先检查矿辞，没有则检查RegistryName
     *  Ingredient：添加一个合成原料，可用于自定义输入
     *  Fluid：添加 1000mb 流体
     *  IFluidTank/FluidTankInfo：添加指定数量的流体。
     *  null/ItemStack.EMPTY：添加一个 ItemStack.EMPTY 用于占位
     *  EmptyFluidHandler.INSTANCE：添加一个 EmptyFluidHandler.INSTANCE 用于占位
     *  List：列表中元素支持前面的所有类型，可混用。用于为多个槽位一次性添加一组物品/流体。
     *        添加时，物品和流体单独使用各自的计数器，因此只要保证彼此
     *  IFluidHandler：一次性为多个槽位添加流体，FluidHandlerFluidMap 不保证位置
     *  所有物品最终以 Ingredient 形式保存于 List 中，所有流体最终以 FluidStack 形式保存于 List 中
     *
     * @param energyCost 能量消耗。产能则为负值。
     */
    public MachineRecipeHandler set(String name, ToIntFunction<MachineRecipeCapture> energyCost,
                                    @Nullable Object input,
                                    @Nullable Object output) {
        return set(name, energyCost, input, 1, output, 1);
    }

    public MachineRecipeHandler set(String name, int energyCost,
                                    @Nullable Object input,
                                    @Nullable Object output) {
        return set(name, (a) -> energyCost, input, output);
    }

    public MachineRecipeHandler set(String name, int energyCost,
                                    @Nullable Object input, @Nonnull Int2IntMap inputCount,
                                    @Nullable Object output, @Nonnull Int2IntMap outputCount) {
        return set(name, (a) -> energyCost, input, inputCount, output, outputCount);
    }

    public MachineRecipeHandler set(String name, ToIntFunction<MachineRecipeCapture> energyCost,
                                    @Nullable Object input, @Nonnull Int2IntMap inputCount,
                                    @Nullable Object output, @Nonnull Int2IntMap outputCount) {
        return set(name, energyCost, input, inputCount::get, output, outputCount::get);
    }

    public MachineRecipeHandler set(String name, int energyCost,
                                    @Nullable Object input, int inputCount,
                                    @Nullable Object output, int outputCount) {
        return set(name, (a) -> energyCost, input, inputCount, output, outputCount);
    }

    public MachineRecipeHandler set(String name, ToIntFunction<MachineRecipeCapture> energyCost,
                                    @Nullable Object input, int inputCount,
                                    @Nullable Object output, int outputCount) {
        return set(name, energyCost, input, (i) -> inputCount, output, (i) -> outputCount);
    }

    public MachineRecipeHandler set(String name, ToIntFunction<MachineRecipeCapture> energyCost,
                                    @Nullable Object input, @Nonnull Function<Integer, Integer> mappedInputCount,
                                    @Nullable Object output, @Nonnull Function<Integer, Integer> mappedOutputCount) {
        List[] inputs = parseObject(input, null);
        List[] outputs = parseObject(output, null);

        List<IngredientPart<ItemStack>> inputsPart = new ArrayList<>();
        List<IngredientPart<ItemStack>> outputsPart = new ArrayList<>();
        List<IngredientPart<FluidStack>> fluidInputsPart = new ArrayList<>();
        List<IngredientPart<FluidStack>> fluidOutputsPart = new ArrayList<>();

        addPartToList(inputs, inputsPart, fluidInputsPart, mappedInputCount);
        addPartToList(outputs, outputsPart, fluidOutputsPart, mappedOutputCount);
        return set(name, energyCost, inputsPart, fluidInputsPart, outputsPart, fluidOutputsPart);
    }

    // 所有的 set 最终都会调用方这个方法
    public MachineRecipeHandler set(String name, ToIntFunction<MachineRecipeCapture> energyCost,
                                    List<IngredientPart<ItemStack>> inputs,
                                    List<IngredientPart<FluidStack>> fluidInputs,
                                    List<IngredientPart<ItemStack>> outputs,
                                    List<IngredientPart<FluidStack>> fluidOutput) {
        MachineRecipe recipe = new MachineRecipe();
        recipe.fluidInputs = fluidInputs;
        recipe.fluidOutputs = fluidOutput;
        recipe.inputs = inputs;
        recipe.outputs = outputs;
        recipe.energy = energyCost;
        recipe.name = name;
        mMachineRecipes.add(recipe);
        return this;
    }


    public MachineRecipeBuilder set(String name) {
        return new MachineRecipeBuilder(name);
    }

    private void addPartToList(List[] params,
                               List<IngredientPart<ItemStack>> itemList,
                               List<IngredientPart<FluidStack>> fluidList,
                               @Nonnull Function<Integer, Integer> mappedOutputCount) {
        List<Ingredient> outputsStacks = params[0];
        IntArrayList outputsCountList = (IntArrayList) params[1];
        List<FluidStack> outputsFluidStacks = params[2];
        for (int i = 0; i < outputsStacks.size(); i++) {
            final Ingredient ing = outputsStacks.get(i);
            Integer specialCount = mappedOutputCount.apply(i);
            int count = specialCount == null ? outputsCountList.getInt(i) : specialCount;
            IngredientPart<ItemStack> part;
            if (ing instanceof IngredientPart) {
                part = (IngredientPart<ItemStack>) ing;
            } else {
                part = new IngredientPart<>();
                part.match = (v1, v2, v3, v4, v5) -> ing.apply(v5);
                part.get = (recipe, integer, stack) -> {
                    if (stack != null) {
                        return ItemHandlerHelper.copyStackWithSize(stack, count);
                    } else {
                        ItemStack[] stacks = outputsStacks.get(integer).getMatchingStacks();
                        return stacks.length > 0 ? ItemHandlerHelper.copyStackWithSize(stacks[0], count) : ItemStack.EMPTY;
                    }
                };
            }

            itemList.add(i, part);
        }
        for (int i = 0; i < outputsFluidStacks.size(); i++) {
            FluidStack stack = outputsFluidStacks.get(i);
            IngredientPart<FluidStack> part = new IngredientPart<>();
            part.match = (v1, v2, v3, v4, v5) -> stack.containsFluid(v5);
            part.get = (recipe, integer, s) -> stack;
            fluidList.add(i, part);
        }
    }

    public MachineRecipeHandler remove(MachineRecipe recipe) {
        mMachineRecipes.remove(recipe);
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
            captures.add(recipe.matchInput(input, fluids));
        }
        return captures.toArray(new MachineRecipeCapture[0]);
    }

    public boolean accept(int slot, List<ItemStack> inputItems, List<FluidStack> inputFluids, ItemStack itemStack) {
        return mMachineRecipes.stream().anyMatch(r -> r.inputs.get(slot).match.apply(r, slot, inputItems, inputFluids, itemStack));
    }

    public boolean accept(int slot, List<ItemStack> inputItems, List<FluidStack> inputFluids, FluidStack fluid) {
        return mMachineRecipes.stream().anyMatch(r -> r.fluidInputs.get(slot).match.apply(r, slot, inputItems, inputFluids, fluid));
    }

    @Nonnull
    private List[] parseObject(@Nullable Object object, @Nullable List[] receiver) {
        final List[] rReceiver = new List[3];
        rReceiver[0] = (receiver != null && receiver.length >= 1) ? receiver[0] : new ArrayList();
        rReceiver[1] = (receiver != null && receiver.length >= 2) ? receiver[1] : new IntArrayList();
        rReceiver[2] = (receiver != null && receiver.length >= 3) ? receiver[2] : new ArrayList();

        List<Ingredient> items = rReceiver[0];
        IntArrayList itemCounts = (IntArrayList) rReceiver[1];
        List<FluidStack> fluids = rReceiver[2];
        if (object == null) {
            items.add(Ingredient.EMPTY);
            itemCounts.add(0);
        } else if (object instanceof List) {
            //noinspection unchecked
            ((List) object).forEach(o -> parseObject(o, rReceiver));
        } else if (object instanceof Ingredient || object instanceof Item || object instanceof Block) {
            items.add(CraftingHelper.getIngredient(object));
            itemCounts.add(1);
        } else if (object instanceof ItemStack)  {
            items.add(Ingredient.fromStacks((ItemStack) object));
            itemCounts.add(((ItemStack) object).getCount());
        } else if (object instanceof String) {
            String o = (String) object;
            if (o.startsWith("[id]")) {
                ItemStack item = ItemUtil.getItemById(((String) object).substring(4));
                items.add(Ingredient.fromStacks(item));
            } else if (o.startsWith("[ore]")) {
                items.add(CraftingHelper.getIngredient(((String) object).substring(5)));
            } else if (o.startsWith("[all]")) {
                String i = ((String) object).substring(5);
                Ingredient ore = CraftingHelper.getIngredient(i);
                Ingredient item = Ingredient.fromStacks(ItemUtil.getItemById(i));
                items.add(Ingredient.merge(Arrays.asList(ore, item)));
            } else {
                Ingredient ore = CraftingHelper.getIngredient(o);
                if (ore.getMatchingStacks().length > 0) {
                    items.add(ore);
                } else {
                    items.add(Ingredient.fromStacks(ItemUtil.getItemById(o)));
                }
            }
            itemCounts.add(1);
        } else if (object instanceof FluidStack) {
            fluids.add((FluidStack) object);
        } else if (object instanceof FluidTankInfo) {
            fluids.add(((FluidTankInfo) object).fluid);
        } else if (object instanceof IFluidTank) {
            fluids.add(((IFluidTank) object).getFluid());
        } else if (object instanceof IFluidHandler) {
            for (IFluidTankProperties fInfos : ((IFluidHandler) object).getTankProperties()) {
                fluids.add(fInfos.getContents());
            }
        } else {
            warn("Ignore: {}", object);
        }
        return rReceiver;
    }

    public class MachineRecipeBuilder {
        ToIntFunction<MachineRecipeCapture> cost = (a) -> 0;
        List<Object> inputs = new LinkedList<>();
        List<Object> outputs = new LinkedList<>();
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

        public MachineRecipeBuilder addInputs(Object... inputs) {
            this.inputs.addAll(Arrays.asList(inputs));
            return this;
        }

        public MachineRecipeBuilder addInputs(Predicate<ItemStack> input, Function<ItemStack, ItemStack> output) {
            IngredientPart<ItemStack> e = new IngredientPart<>();
            e.match = (recipe, slot, itemInputs, inputFluids, inputItem) -> input.test(inputItem);
            e.get = (recipe, slot, inputItem) -> input.test(inputItem) ? output.apply(inputItem) : ItemStack.EMPTY;
            this.inputs.add(e);
            return this;
        }

        public MachineRecipeBuilder addInputs(Collection<Object> inputs) {
            this.inputs.addAll(inputs);
            return this;
        }

        public MachineRecipeBuilder addOutputs(Object... outputs) {
            this.outputs.addAll(Arrays.asList(outputs));
            return this;
        }

        public MachineRecipeBuilder addOutputs(Function3.Stack<ItemStack> output) {
            IngredientPart<ItemStack> part = new IngredientPart<>();
            part.get = output;
            part.match =(recipe, slot, items, fluids, input) -> !part.get.apply(recipe, slot, input).isEmpty();
            this.outputs.add(part);
            return this;
        }

        public MachineRecipeBuilder addOutputs(Collection<Object> outputs) {
            this.outputs.addAll(outputs);
            return this;
        }

        public MachineRecipeHandler build() {
            MachineRecipeHandler.this.set(name, cost, inputs, outputs);
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
                match = inputs.get(i).match.apply(this, i, input, fluids, input.get(i));
            }
            // fluid
            for (int i = 0; match && i < fluidInputs.size(); i++) {
                match = fluidInputs.get(i).match.apply(this, i, input, fluids, fluids.get(i));
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
                inputs.add(i, recipe.inputs.get(i).get.apply(recipe, i, input.get(i)));
            }

            this.outputs = new ArrayList<>(recipe.outputs.size());
            for (int i = 0; i < recipe.outputs.size(); i++) {
                outputs.add(i, recipe.outputs.get(i).get.apply(recipe, i, null));
            }

            this.fluidInputs = new ArrayList<>(recipe.fluidInputs.size());
            for (int i = 0; i < recipe.fluidInputs.size(); i++) {
                fluidInputs.add(i, recipe.fluidInputs.get(i).get.apply(recipe, i, fluids.get(i)));
            }

            this.fluidOutputs = new ArrayList<>(recipe.fluidOutputs.size());
            for (int i = 0; i < recipe.fluidOutputs.size(); i++) {
                fluidOutputs.add(i, recipe.fluidOutputs.get(i).get.apply(recipe, i, null));
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
