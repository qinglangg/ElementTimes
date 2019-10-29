package com.elementtimes.tutorial.plugin.jei;

import com.elementtimes.elementcore.api.template.tileentity.recipe.MachineRecipeHandler;
import com.elementtimes.tutorial.ElementTimes;
import com.elementtimes.tutorial.common.init.ElementtimesBlocks;
import com.elementtimes.tutorial.common.init.ElementtimesItems;
import com.elementtimes.tutorial.common.tileentity.*;
import com.elementtimes.tutorial.other.recipe.RecipeWrapper;
import com.elementtimes.tutorial.plugin.jei.category.MachineRecipeCategory;
import com.elementtimes.tutorial.plugin.jei.wrapper.MachineRecipeWrapper;
import com.elementtimes.tutorial.plugin.jei.wrapper.VanillaRecipeWrapper;
import mezz.jei.api.*;
import mezz.jei.api.ingredients.IIngredientBlacklist;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import mezz.jei.api.recipe.VanillaRecipeCategoryUid;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

import java.util.List;

/**
 * JEI 兼容
 * @author luqin
 */
@JEIPlugin
public class JeiSupport implements IModPlugin {

    private static final String ID_COMPRESSOR = ElementTimes.MODID + ".compressor.jei.category";
    private static final String ID_PULVERIZE = ElementTimes.MODID + ".pulverize.jei.category";
    private static final String ID_REBUILD = ElementTimes.MODID + ".rebuild.jei.category";
    private static final String ID_ELEMENT_GENERATOR = ElementTimes.MODID + ".elementgenerator.jei.category";
    private static final String ID_EXTRACTOR = ElementTimes.MODID + ".extractor.jei.category";
    private static final String ID_FORMING = ElementTimes.MODID + ".forming.jei.category";
    private static final String ID_SOLID_MELTER = ElementTimes.MODID + ".solidmelter.jei.category";
    private static final String ID_FLUID_REACTOR = ElementTimes.MODID + ".fluidreactor.jei.category";
    private static final String ID_SOLID_REACTOR = ElementTimes.MODID + ".solidreactor.jei.category";
    private static final String ID_CONDENSER = ElementTimes.MODID + ".condenser.jei.category";
    private static final String ID_FLUID_HEATER = ElementTimes.MODID + ".fluidheater.jei.category";
    private static final String ID_ELECTROLYTIC_CELL = ElementTimes.MODID + ".electrolyticcell.jei.category";
    private static final String ID_SOLID_FLUID_REACTOR = ElementTimes.MODID + ".solidfluidreactor.jei.category";
    private static final String ID_PUMP_AIR = ElementTimes.MODID + ".pumpair.jei.category";
    private static final String ID_ITEM_REDUCER = ElementTimes.MODID + ".itemreducer.jei.category";
    private static final String ID_CENTRIFUGE = ElementTimes.MODID + ".centrifuge.jei.category";
    private static final String ID_COAGULATOR = ElementTimes.MODID + ".coagulator.jei.category";
    private static final String ID_SOLID_CENTRIFUGE = ElementTimes.MODID + ".solidcentrifuge.jei.category";


    @Override
    public void register(IModRegistry registry) {
        // 玉米
        IIngredientBlacklist blacklist = registry.getJeiHelpers().getIngredientBlacklist();
        blacklist.addIngredientToBlacklist(new ItemStack(ElementtimesBlocks.cornCrop));
        blacklist.addIngredientToBlacklist(new ItemStack(ElementtimesBlocks.cornCropUp));
        // 机器配方
        registerJeiRecipes(registry, ElementtimesBlocks.pulverizer, TilePulverize.RECIPE, ID_PULVERIZE);
        registerJeiRecipes(registry, ElementtimesBlocks.rebuild, TileRebuild.RECIPE, ID_REBUILD);
        registerJeiRecipes(registry, ElementtimesBlocks.forming, TileForming.RECIPE, ID_FORMING);
        registerJeiRecipes(registry, ElementtimesBlocks.fluidReactor, TileFluidReactor.RECIPES, ID_FLUID_REACTOR);
        registerJeiRecipes(registry, ElementtimesBlocks.fluidHeater, TileFluidHeater.RECIPE, ID_FLUID_HEATER);
        registerJeiRecipes(registry, ElementtimesBlocks.extractor, TileExtractor.RECIPE, ID_EXTRACTOR);
        registerJeiRecipes(registry, ElementtimesBlocks.elementGenerator, TileGeneratorElement.RECIPE, ID_ELEMENT_GENERATOR);
        registerJeiRecipes(registry, ElementtimesBlocks.electrolyticCell, TileElectrolyticCell.RECIPE, ID_ELECTROLYTIC_CELL);
        registerJeiRecipes(registry, ElementtimesBlocks.condenser, TileCondenser.RECIPE, ID_CONDENSER);
        registerJeiRecipes(registry, ElementtimesBlocks.compressor, TileCompressor.RECIPE, ID_COMPRESSOR);
        registerJeiRecipes(registry, ElementtimesBlocks.pumpAir, TilePumpAir.RECIPE, ID_PUMP_AIR);
        registerJeiRecipes(registry, ElementtimesBlocks.solidFluidReactor, TileSolidFluidReactor.RECIPE, ID_SOLID_FLUID_REACTOR);
        registerJeiRecipes(registry, ElementtimesBlocks.solidMelter, TileSolidMelter.RECIPE, ID_SOLID_MELTER);
        registerJeiRecipes(registry, ElementtimesBlocks.solidReactor, TileSolidReactor.RECIPE, ID_SOLID_REACTOR);
        registerJeiRecipes(registry, ElementtimesBlocks.itemReducer, TileItemReducer.RECIPE, ID_ITEM_REDUCER);
        registerJeiRecipes(registry, ElementtimesBlocks.centrifuge, TileCentrifuge.RECIPE, ID_CENTRIFUGE);
        registerJeiRecipes(registry, ElementtimesBlocks.coagulator, TileCoagulator.RECIPE, ID_COAGULATOR);
        registerJeiRecipes(registry, ElementtimesBlocks.solidCentrifuge, TileSolidCentrifuge.RECIPE, ID_SOLID_CENTRIFUGE);
        // 电炉
        registry.addRecipeCatalyst(new ItemStack(ElementtimesBlocks.furnace), VanillaRecipeCategoryUid.SMELTING);
        // RecipeWrapper 兼容
        registry.handleRecipes(RecipeWrapper.class,
                recipe -> VanillaRecipeWrapper.create(registry, recipe), VanillaRecipeCategoryUid.CRAFTING);
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        // MachineRecipe 兼容
        IGuiHelper guiHelper = registry.getJeiHelpers().getGuiHelper();
        registry.addRecipeCategories(
                new MachineRecipeCategory(guiHelper, new TileItemReducer(), ID_ITEM_REDUCER, 44, 16, 90, 44),
                new MachineRecipeCategory(guiHelper, new TileCompressor(), ID_COMPRESSOR, 44, 16, 90, 44),
                new MachineRecipeCategory(guiHelper, new TileRebuild(), ID_REBUILD, 44, 16, 90, 44),
                new MachineRecipeCategory(guiHelper, new TilePulverize(), ID_PULVERIZE, 44, 16, 90, 44),
                new MachineRecipeCategory(guiHelper, new TileExtractor(), ID_EXTRACTOR, 44, 16, 90, 44),
                new MachineRecipeCategory(guiHelper, new TileForming(), ID_FORMING, 44, 16, 90, 44),
                new MachineRecipeCategory(guiHelper, new TilePumpAir(), ID_PUMP_AIR, 45, 21, 80, 61),
                new MachineRecipeCategory(guiHelper, new TileCondenser(), ID_CONDENSER, 4, 11, 168, 92),
                new MachineRecipeCategory(guiHelper, new TileElectrolyticCell(), ID_ELECTROLYTIC_CELL, 32, 8, 112, 93),
                new MachineRecipeCategory(guiHelper, new TileGeneratorElement(), ID_ELEMENT_GENERATOR, 40, 24, 95, 39),
                new MachineRecipeCategory(guiHelper, new TileFluidHeater(), ID_FLUID_HEATER, 4, 11, 168, 92),
                new MachineRecipeCategory(guiHelper, new TileFluidReactor(), ID_FLUID_REACTOR, 14, 11, 148, 93),
                new MachineRecipeCategory(guiHelper, new TileSolidMelter(), ID_SOLID_MELTER, 38, 12, 100, 56),
                new MachineRecipeCategory(guiHelper, new TileSolidReactor(), ID_SOLID_REACTOR, 30, 5, 117, 96),
                new MachineRecipeCategory(guiHelper, new TileSolidFluidReactor(), ID_SOLID_FLUID_REACTOR,  17, 10, 141, 55),
                new MachineRecipeCategory(guiHelper, new TileCentrifuge(), ID_CENTRIFUGE, 16, 13, 146, 52),
                new MachineRecipeCategory(guiHelper, new TileCoagulator(), ID_COAGULATOR, 44, 7, 83, 73),
                new MachineRecipeCategory(guiHelper, new TileSolidCentrifuge(), ID_SOLID_CENTRIFUGE, 52, 12, 76, 58)
        );
    }

    private void registerJeiRecipes(IModRegistry registry, Block machine, MachineRecipeHandler recipes, String id) {
        List<MachineRecipeWrapper> wrappers = MachineRecipeWrapper.fromHandler(recipes);
        registry.addRecipes(wrappers, id);
        registry.addRecipeCatalyst(new ItemStack(machine), id);
    }
}
