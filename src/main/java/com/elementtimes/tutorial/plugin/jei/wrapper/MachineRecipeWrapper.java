//package com.elementtimes.tutorial.plugin.jei.wrapper;
//
//import com.elementtimes.tutorial.other.MachineRecipeHandler;
//import mezz.jei.api.ingredients.IIngredients;
//import mezz.jei.api.recipe.IRecipeWrapper;
//import net.minecraft.block.Block;
//import net.minecraft.item.Item;
//import net.minecraft.item.ItemStack;
//import net.minecraftforge.oredict.OreDictionary;
//
//import java.util.Collections;
//import java.util.List;
//
//public class MachineRecipeWrapper implements IRecipeWrapper {
//
//    MachineRecipeHandler.MachineRecipe mRecipe;
//
//    public MachineRecipeWrapper(MachineRecipeHandler.MachineRecipe recipe) {
//        mRecipe = recipe;
//    }
//
//    @Override
//    public void getIngredients(IIngredients ingredients) {
//
//        ingredients.setInputs(ItemStack.class, Collections.singletonList(input));
//        ingredients.setOutputs(ItemStack.class, Collections.singletonList(output));
//    }
//}
