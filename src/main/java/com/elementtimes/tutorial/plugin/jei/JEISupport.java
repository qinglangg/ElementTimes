package com.elementtimes.tutorial.plugin.jei;

import com.elementtimes.tutorial.Elementtimes;
import com.elementtimes.tutorial.common.init.ElementtimesBlocks;
import com.elementtimes.tutorial.common.tileentity.TileCompressor;
import com.elementtimes.tutorial.common.tileentity.TilePulverize;
import com.elementtimes.tutorial.common.tileentity.TileRebuild;
import com.elementtimes.tutorial.plugin.jei.category.MachineOneToOneRecipe;
import com.elementtimes.tutorial.plugin.jei.wrapper.MachineItemRecipeWrapper;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.ingredients.IIngredientBlacklist;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import mezz.jei.api.recipe.VanillaRecipeCategoryUid;
import net.minecraft.item.ItemStack;

import java.util.List;

@JEIPlugin
public class JEISupport implements IModPlugin {

    private static final String ID_COMPRESSOR = Elementtimes.MODID + ".compressor.jei.category";
    private static final String ID_PULVERIZE = Elementtimes.MODID + ".pulverize.jei.category";
    private static final String ID_REBUILD = Elementtimes.MODID + ".rebuild.jei.category";

    @Override
    public void register(IModRegistry registry) {
        // 玉米
        IIngredientBlacklist blacklist = registry.getJeiHelpers().getIngredientBlacklist();
        blacklist.addIngredientToBlacklist(new ItemStack(ElementtimesBlocks.cornCrop));
        blacklist.addIngredientToBlacklist(new ItemStack(ElementtimesBlocks.cornCropUp));
        // 磨粉机
        List<MachineItemRecipeWrapper> pulverizeWrappers = MachineItemRecipeWrapper.fromHandler(TilePulverize.sRecipeHandler);
        registry.addRecipes(pulverizeWrappers, ID_PULVERIZE);
        registry.addRecipeCatalyst(new ItemStack(ElementtimesBlocks.pulverize), ID_PULVERIZE);
        // 物质重构机
        TileRebuild.init();
        List<MachineItemRecipeWrapper> rebuildWrappers = MachineItemRecipeWrapper.fromHandler(TileRebuild.sRecipeHandler);
        registry.addRecipes(rebuildWrappers, ID_REBUILD);
        registry.addRecipeCatalyst(new ItemStack(ElementtimesBlocks.rebuild), ID_REBUILD);
        // 压缩机
        TileCompressor.init();
        List<MachineItemRecipeWrapper> compressorWrappers = MachineItemRecipeWrapper.fromHandler(TileCompressor.sRecipeHandler);
        registry.addRecipes(compressorWrappers, ID_COMPRESSOR);
        registry.addRecipeCatalyst(new ItemStack(ElementtimesBlocks.compressor), ID_COMPRESSOR);
        // TODO: 酿造台
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
}
