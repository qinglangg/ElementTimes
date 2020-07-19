package com.elementtimes.elementtimes.common.block.recipe;

import com.elementtimes.elementcore.api.recipe.*;
import com.elementtimes.elementtimes.ElementTimes;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;

import java.util.Map;



public class FluidReactorRecipe extends BaseRecipe {

    public static final BaseRecipeType<FluidReactorRecipe> TYPE = new BaseRecipeType<FluidReactorRecipe>() {};

    public FluidReactorRecipe(int energy, float experience, NonNullList<IIngredient<ItemStack>> itemInputs, NonNullList<IIngredient<ItemStack>> itemOutputs, NonNullList<IIngredient<FluidStack>> fluidInputs, NonNullList<IIngredient<FluidStack>> fluidOutputs, IRecipeType<?> type, ResourceLocation id) {
        super(energy, experience, itemInputs, itemOutputs, fluidInputs, fluidOutputs, type, id);
    }

    public static FluidReactorRecipe create(int energy, float experience, NonNullList<IIngredient<ItemStack>> itemInputs, NonNullList<IIngredient<ItemStack>> itemOutputs, NonNullList<IIngredient<FluidStack>> fluidInputs, NonNullList<IIngredient<FluidStack>> fluidOutputs, Map<String, Object> otherData, IRecipeType<?> type, ResourceLocation id) {
        return new FluidReactorRecipe(energy, experience, itemInputs, itemOutputs, fluidInputs, fluidOutputs, type, id);
    }

    public static Builder builder2() {
        return new Builder();
    }

    public static class Builder extends RecipeBuilder {

        private FluidStack input0 = FluidStack.EMPTY;
        private FluidStack input1 = FluidStack.EMPTY;
        private FluidStack output0 = FluidStack.EMPTY;
        private FluidStack output1 = FluidStack.EMPTY;
        private ItemStack item = ItemStack.EMPTY;

        public Builder input0(Fluid fluid) {
            return input0(fluid, 1000);
        }

        public Builder input0(Fluid fluid, int amount) {
            input0 = new FluidStack(fluid, amount);
            return this;
        }

        public Builder input1(Fluid fluid) {
            return input1(fluid, 1000);
        }

        public Builder input1(Fluid fluid, int amount) {
            input1 = new FluidStack(fluid, amount);
            return this;
        }

        public Builder output0(Fluid fluid) {
            return output0(fluid, 1000);
        }

        public Builder output0(Fluid fluid, int amount) {
            output0 = new FluidStack(fluid, amount);
            return this;
        }

        public Builder output1(Fluid fluid) {
            return output1(fluid, 1000);
        }

        public Builder output1(Fluid fluid, int amount) {
            output1 = new FluidStack(fluid, amount);
            return this;
        }

        public Builder item(IItemProvider item) {
            return item(item, 1000);
        }

        public Builder item(IItemProvider item, int count) {
            this.item = new ItemStack(item, count);
            return this;
        }

        public FluidReactorRecipe build(String name) {
            return build(name, 1000);
        }

        public FluidReactorRecipe build(String name, int energy) {
            return this
                    .fluidInput(new FluidStackIngredient(input0))
                    .fluidInput(new FluidStackIngredient(input1))
                    .fluidOutput(new FluidStackIngredient(output0))
                    .fluidOutput(new FluidStackIngredient(output1))
                    .itemOutput(new ItemStackIngredient(item))
                    .energy(energy)
                    .build(TYPE, new ResourceLocation(ElementTimes.MODID, "fluid_reactor_" + name), FluidReactorRecipe::create);
        }
    }
}
