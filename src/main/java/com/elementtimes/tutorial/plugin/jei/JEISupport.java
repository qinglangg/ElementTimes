package com.elementtimes.tutorial.plugin.jei;

import com.elementtimes.tutorial.ElementTimes;
import com.elementtimes.tutorial.common.init.ElementtimesBlocks;
import com.elementtimes.tutorial.common.init.ElementtimesItems;
import com.elementtimes.tutorial.common.item.ItemBottleFuel;
import com.elementtimes.tutorial.common.tileentity.TileCompressor;
import com.elementtimes.tutorial.common.tileentity.TilePulverize;
import com.elementtimes.tutorial.common.tileentity.TileRebuild;
import com.elementtimes.tutorial.other.recipe.MachineRecipeHandler;
import com.elementtimes.tutorial.plugin.jei.category.MachineOneToOneRecipe;
import com.elementtimes.tutorial.plugin.jei.wrapper.MachineRecipeWrapper;
import mezz.jei.api.*;
import mezz.jei.api.ingredients.IIngredientBlacklist;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import mezz.jei.api.recipe.VanillaRecipeCategoryUid;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.List;

@JEIPlugin
public class JEISupport implements IModPlugin {

    private static final String ID_COMPRESSOR = ElementTimes.MODID + ".compressor.jei.category";
    private static final String ID_PULVERIZE = ElementTimes.MODID + ".pulverize.jei.category";
    private static final String ID_REBUILD = ElementTimes.MODID + ".rebuild.jei.category";

    @Override
    public void registerItemSubtypes(ISubtypeRegistry subtypeRegistry) {
        subtypeRegistry.useNbtForSubtypes(ElementtimesItems.bottle);
    }

    @Override
    public void register(IModRegistry registry) {
        // 玉米
        IIngredientBlacklist blacklist = registry.getJeiHelpers().getIngredientBlacklist();
        blacklist.addIngredientToBlacklist(new ItemStack(ElementtimesBlocks.cornCrop));
        blacklist.addIngredientToBlacklist(new ItemStack(ElementtimesBlocks.cornCropUp));
        // 机器配方
        registerJeiRecipes(registry, ElementtimesBlocks.pulverizer, TilePulverize.sRecipeHandler, ID_PULVERIZE);
        registerJeiRecipes(registry, ElementtimesBlocks.rebuild, TileRebuild.sRecipeHandler, ID_REBUILD);
        registerJeiRecipes(registry, ElementtimesBlocks.compressor, TileCompressor.sRecipeHandler, ID_COMPRESSOR);
        // TODO: 酿造台
        // TODO: 成型机
        // 电炉
        registry.addRecipeCatalyst(new ItemStack(ElementtimesBlocks.furnace), VanillaRecipeCategoryUid.SMELTING);
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        IGuiHelper guiHelper = registry.getJeiHelpers().getGuiHelper();
        registry.addRecipeCategories(new MachineOneToOneRecipe(guiHelper, ID_COMPRESSOR, "compressor"));
        registry.addRecipeCategories(new MachineOneToOneRecipe(guiHelper, ID_REBUILD, "rebuild"));
        registry.addRecipeCategories(new MachineOneToOneRecipe(guiHelper, ID_PULVERIZE, "pulverize"));
    }

    private void registerJeiRecipes(IModRegistry registry, Block machine, MachineRecipeHandler recipes, String id) {
        List<MachineRecipeWrapper> wrappers = MachineRecipeWrapper.fromHandler(recipes);
        registry.addRecipes(wrappers, id);
        registry.addRecipeCatalyst(new ItemStack(machine), id);
    }
}
