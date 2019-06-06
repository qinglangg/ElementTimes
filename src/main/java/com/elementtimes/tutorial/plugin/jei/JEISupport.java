//package com.elementtimes.tutorial.plugin.jei;
//
//import com.elementtimes.tutorial.common.init.ElementtimesBlocks;
//import com.elementtimes.tutorial.common.tileentity.TileCompressor;
//import com.elementtimes.tutorial.common.tileentity.TilePulverize;
//import com.elementtimes.tutorial.common.tileentity.TileRebuild;
//import com.elementtimes.tutorial.plugin.jei.category.CompressorRecipe;
//import com.elementtimes.tutorial.plugin.jei.category.PulverizeRecipe;
//import com.elementtimes.tutorial.plugin.jei.category.RebuildRecipe;
//import mezz.jei.api.IGuiHelper;
//import mezz.jei.api.IModPlugin;
//import mezz.jei.api.IModRegistry;
//import mezz.jei.api.JEIPlugin;
//import mezz.jei.api.ingredients.IIngredientBlacklist;
//import mezz.jei.api.recipe.IRecipeCategoryRegistration;
//import mezz.jei.api.recipe.VanillaRecipeCategoryUid;
//import net.minecraft.item.ItemStack;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@JEIPlugin
//public class JEISupport implements IModPlugin {
//
//    @Override
//    public void register(IModRegistry registry) {
//        // 玉米
//        IIngredientBlacklist blacklist = registry.getJeiHelpers().getIngredientBlacklist();
//        blacklist.addIngredientToBlacklist(new ItemStack(ElementtimesBlocks.cornCrop));
//        blacklist.addIngredientToBlacklist(new ItemStack(ElementtimesBlocks.cornCropUp));
//        // 磨粉机
//        TilePulverize.init();
//        List<PulverizeWrapper> pulverizeWrappers = TilePulverize.dict.entrySet().stream()
//                .map(entity -> new PulverizeWrapper(entity.getKey(), entity.getValue()))
//                .collect(Collectors.toList());
//        registry.addRecipes(pulverizeWrappers, PulverizeRecipe.ID);
//        registry.addRecipeCatalyst(new ItemStack(ElementtimesBlocks.pulverize), PulverizeRecipe.ID);
//        // 物质重构机
//        TileRebuild.init();
//        List<RebuildWrapper> rebuildWrappers = TileRebuild.rebuildMap.stream()
//                .map(entity -> new RebuildWrapper(entity.left, entity.right, TileRebuild.getEnergyCost(entity.left)))
//                .collect(Collectors.toList());
//        registry.addRecipes(rebuildWrappers, RebuildRecipe.ID);
//        registry.addRecipeCatalyst(new ItemStack(ElementtimesBlocks.rebuild), RebuildRecipe.ID);
//        // 压缩机
//        TileCompressor.init();
//        List<CompressorWrapper> compressorWrappers = TileCompressor.recipes.entrySet().stream()
//                .map(recipe -> new CompressorWrapper(recipe.getKey(), recipe.getValue()))
//                .collect(Collectors.toList());
//        registry.addRecipes(compressorWrappers, CompressorRecipe.ID);
//        registry.addRecipeCatalyst(new ItemStack(ElementtimesBlocks.compressor), CompressorRecipe.ID);
//        // TODO: 酿造台
//        // 电炉
//        registry.addRecipeCatalyst(new ItemStack(ElementtimesBlocks.furnace), VanillaRecipeCategoryUid.SMELTING);
//    }
//
//    @Override
//    public void registerCategories(IRecipeCategoryRegistration registry) {
//        IGuiHelper guiHelper = registry.getJeiHelpers().getGuiHelper();
//        registry.addRecipeCategories(new PulverizeRecipe(guiHelper));
//        registry.addRecipeCategories(new RebuildRecipe(guiHelper));
//        registry.addRecipeCategories(new CompressorRecipe(guiHelper));
//    }
//}
