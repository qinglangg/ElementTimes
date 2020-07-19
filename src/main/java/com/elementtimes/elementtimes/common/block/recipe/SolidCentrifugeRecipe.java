package com.elementtimes.elementtimes.common.block.recipe;

import com.elementtimes.elementcore.api.recipe.BaseRecipe;
import com.elementtimes.elementcore.api.recipe.IIngredient;
import com.elementtimes.elementcore.api.recipe.ItemIngredient;
import com.elementtimes.elementcore.api.recipe.ItemStackIngredient;
import com.elementtimes.elementcore.api.utils.TagUtils;
import com.elementtimes.elementtimes.ElementTimes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.Tag;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;



public class SolidCentrifugeRecipe extends BaseRecipe {

    public static final BaseRecipeType<SolidCentrifugeRecipe> TYPE = new BaseRecipeType<SolidCentrifugeRecipe>() {};

    private static NonNullList<IIngredient<ItemStack>> output(IItemProvider output0, IItemProvider output1, IItemProvider output2) {
        NonNullList<IIngredient<ItemStack>> list = NonNullList.withSize(3, ItemIngredient.EMPTY);
        list.set(0, new ItemStackIngredient(output0));
        list.set(1, new ItemStackIngredient(output1));
        list.set(2, new ItemStackIngredient(output2));
        return list;
    }

    private static NonNullList<IIngredient<ItemStack>> output(IItemProvider output0, IItemProvider output1) {
        NonNullList<IIngredient<ItemStack>> list = NonNullList.withSize(3, ItemIngredient.EMPTY);
        list.set(0, new ItemStackIngredient(output0));
        list.set(1, new ItemStackIngredient(output1));
        return list;
    }

    public SolidCentrifugeRecipe(String name, IItemProvider input, IItemProvider output0, IItemProvider output1, IItemProvider output2) {
        super(1000, 0, NonNullList.withSize(1, new ItemStackIngredient(input)),
                output(output0, output1, output2),
                NonNullList.create(), NonNullList.create(), TYPE, new ResourceLocation(ElementTimes.MODID, name));
    }

    public SolidCentrifugeRecipe(int energy, String name, IItemProvider input, IItemProvider output0, IItemProvider output1, IItemProvider output2) {
        super(energy, 0, NonNullList.withSize(1, new ItemStackIngredient(input)),
                output(output0, output1, output2),
                NonNullList.create(), NonNullList.create(), TYPE, new ResourceLocation(ElementTimes.MODID, name));
    }

    public SolidCentrifugeRecipe(String name, String type, String material, IItemProvider output0, IItemProvider output1, IItemProvider output2) {
        super(1000, 0, NonNullList.withSize(1, new ItemIngredient(1, Ingredient.fromTag(TagUtils.forgeItem(type, material)))),
                output(output0, output1, output2),
                NonNullList.create(), NonNullList.create(), TYPE, new ResourceLocation(ElementTimes.MODID, name));
    }

    public SolidCentrifugeRecipe(String name, IItemProvider input, IItemProvider output0, IItemProvider output1) {
        super(1000, 0, NonNullList.withSize(1, new ItemStackIngredient(input)),
                output(output0, output1),
                NonNullList.create(), NonNullList.create(), TYPE, new ResourceLocation(ElementTimes.MODID, name));
    }

    public SolidCentrifugeRecipe(Tag<Item> input, IItemProvider output, String id) {
        super(8000, 0f,
                NonNullList.withSize(1, new ItemIngredient(1, Ingredient.fromTag(input))),
                NonNullList.withSize(1, new ItemStackIngredient(output)),
                NonNullList.create(), NonNullList.create(), TYPE, new ResourceLocation(ElementTimes.MODID, id));
    }

    public SolidCentrifugeRecipe(String inputType, String inputMaterial, Item output, String id) {
        this(TagUtils.forgeItem(inputType, inputMaterial), output, id);
    }

    public SolidCentrifugeRecipe(IItemProvider input, IItemProvider output, String id) {
        super(8000, 0f,
                NonNullList.withSize(1, new ItemStackIngredient(input)),
                NonNullList.withSize(1, new ItemStackIngredient(5, output)),
                NonNullList.create(), NonNullList.create(), TYPE, new ResourceLocation(ElementTimes.MODID, id));
    }

    public SolidCentrifugeRecipe(Tag<Item> input, IIngredient<ItemStack> output, String id) {
        super(8000, 0f,
                NonNullList.withSize(1, new ItemIngredient(1, Ingredient.fromTag(input))),
                NonNullList.withSize(1, output),
                NonNullList.create(), NonNullList.create(), TYPE, new ResourceLocation(ElementTimes.MODID, id));
    }
}
