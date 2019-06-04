package com.elementtimes.tutorial.other;

import com.elementtimes.tutorial.interfaces.function.Function3;
import com.elementtimes.tutorial.interfaces.function.Function5;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.fluids.FluidStack;

import java.util.List;

/**
 * 合成表匹配
 * @author luqin2007
 */
public class IngredientPart<T> extends Ingredient {

    Function5.Match<T> match;

    Function3.Stack<T> get;
}
