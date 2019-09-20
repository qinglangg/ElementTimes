package com.elementtimes.tutorial.common.init;

import com.elementtimes.elementcore.api.annotation.ModRecipe;
import com.elementtimes.tutorial.ElementTimes;
import com.elementtimes.tutorial.common.item.ItemBottleFuel;
import com.elementtimes.tutorial.other.FluidIngredient;
import net.minecraft.init.Items;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

/**
 * 用于兼容旧版本物品的合成表
 *
 * @author luqin2007
 */
@SuppressWarnings("unused")
public class ElementtimesRecipeCraftingOld {

    @ModRecipe
    public static Object createFluidRecipes() {
        return (Supplier) () -> {
            Ingredient ingredientBottle = Ingredient.fromItems(Items.GLASS_BOTTLE);
            Ingredient ingredientBucket = Ingredient.fromItem(Items.BUCKET);
            Set<Fluid> bucketFluids = FluidRegistry.getBucketFluids();
            List<IRecipe> recipeList = new ArrayList<>(bucketFluids.size() * 2);
            bucketFluids.forEach(fluid -> {
                NonNullList<Ingredient> input0 = NonNullList.create();
                input0.add(ingredientBottle);
                input0.add(FluidIngredient.bucket(fluid));
                IRecipe bucketToBottle = new ShapelessRecipes("recipe", ItemBottleFuel.createByFluid(fluid), input0);
                bucketToBottle.setRegistryName(new ResourceLocation(ElementTimes.MODID, fluid.getName() + "_convert_0"));
                NonNullList<Ingredient> input1 = NonNullList.create();
                input1.add(ingredientBucket);
                input1.add(FluidIngredient.bottle(fluid));
                IRecipe bottleToBucket = new ShapelessRecipes("recipe", FluidUtil.getFilledBucket(new FluidStack(fluid, Fluid.BUCKET_VOLUME)), input1);
                bottleToBucket.setRegistryName(new ResourceLocation(ElementTimes.MODID, fluid.getName() + "_convert_1"));
                recipeList.add(bottleToBucket);
                recipeList.add(bucketToBottle);
            });
            return recipeList;
        };
    }
}
