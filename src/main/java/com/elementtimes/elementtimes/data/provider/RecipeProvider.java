package com.elementtimes.elementtimes.data.provider;

import com.elementtimes.elementcore.api.utils.MathUtils;
import com.elementtimes.elementtimes.ElementTimes;
import com.elementtimes.elementtimes.common.init.Items;
import net.minecraft.block.Blocks;
import net.minecraft.data.CookingRecipeBuilder;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.ShapelessRecipeBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags;

import javax.annotation.Nonnull;
import java.util.function.Consumer;



public class RecipeProvider extends net.minecraft.data.RecipeProvider {

    public RecipeProvider(DataGenerator dataGenerator) {
        super(dataGenerator);
    }

    @Override
    protected void registerRecipes(@Nonnull Consumer<IFinishedRecipe> consumer) {
        // cooking
        // todo other recipes
        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(Items.goldPowder), net.minecraft.item.Items.GOLD_INGOT, 5, 200)
                .addCriterion("gold_ingot", hasItem(Items.goldPowder))
                .build(consumer, new ResourceLocation(ElementTimes.MODID, "gold_ingot"));
        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(Blocks.STONE), Items.stoneIngot, 5, 200)
                .addCriterion("has_stone", this.hasItem(Blocks.STONE))
                .build(consumer, new ResourceLocation(ElementTimes.MODID, "stone_ingot"));
        // crafting
        // todo other recipes
        Tag<Item> hammerTag = ItemTags.getCollection().getOrCreate(new ResourceLocation(ElementTimes.MODID, "hammer"));
        for (Item log : ItemTags.LOGS.getAllElements()) {
            for (ResourceLocation tag : log.getTags()) {
                String path = tag.getPath();
                if (path.endsWith("_logs")) {
                    ResourceLocation plankLocation = new ResourceLocation(tag.getNamespace(), path.substring(0, path.length() - 5) + "_planks");
                    Tag<Item> plankTag = ItemTags.getCollection().get(plankLocation);
                    if (plankTag != null) {
                        Item element = plankTag.getRandomElement(MathUtils.RAND);
                        Tag<Item> logTag = ItemTags.getCollection().getOrCreate(tag);
                        new ShapelessRecipeBuilder(element, 16)
                                .addIngredient(logTag)
                                .addIngredient(hammerTag)
                                .setGroup(ElementTimes.MODID)
                                .addCriterion("has_hammer", hasItem(hammerTag))
                                .setGroup(ElementTimes.MODID)
                                .build(consumer, new ResourceLocation(ElementTimes.MODID, plankLocation.getPath()));
                        break;
                    }
                }
            }
        }
        for (Item ore : Tags.Items.ORES.getAllElements()) {
            for (ResourceLocation tag : ore.getTags()) {
                String path = tag.getPath();
                if (path.startsWith("ores/")) {
                    String oreName = path.substring(5);
                    ResourceLocation dustLocation = new ResourceLocation(tag.getNamespace(), "dusts/" + oreName);
                    Tag<Item> dustTag = ItemTags.getCollection().get(dustLocation);
                    if (dustTag != null) {
                        Item element = dustTag.getRandomElement(MathUtils.RAND);
                        Tag<Item> oreTag = ItemTags.getCollection().getOrCreate(tag);
                        new ShapelessRecipeBuilder(element, 3)
                                .addIngredient(oreTag)
                                .addIngredient(hammerTag)
                                .setGroup(ElementTimes.MODID)
                                .addCriterion("has_hammer", hasItem(hammerTag))
                                .build(consumer, new ResourceLocation(ElementTimes.MODID, "dust_" + oreName));
                        break;
                    }
                }
            }
        }
    }
}
