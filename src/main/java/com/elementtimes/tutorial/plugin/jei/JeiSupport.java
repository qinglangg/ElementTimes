package com.elementtimes.tutorial.plugin.jei;

import com.elementtimes.tutorial.ElementTimes;
import com.elementtimes.tutorial.common.init.ElementtimesBlocks;
import com.elementtimes.tutorial.common.init.ElementtimesItems;
import com.elementtimes.tutorial.common.tileentity.*;
import com.elementtimes.tutorial.other.machineRecipe.MachineRecipeHandler;
import com.elementtimes.tutorial.plugin.jei.category.MachineRecipeCategory;
import com.elementtimes.tutorial.plugin.jei.wrapper.MachineRecipeWrapper;
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
        registerJeiRecipes(registry, ElementtimesBlocks.forming, TileForming.sGearRecipeHandler, ID_FORMING);
        registerJeiRecipes(registry, ElementtimesBlocks.fluidReactor, TileFluidReactor.RECIPES, ID_FLUID_REACTOR);
        registerJeiRecipes(registry, ElementtimesBlocks.fluidHeater, TileFluidHeater.RECIPE, ID_FLUID_HEATER);
        registerJeiRecipes(registry, ElementtimesBlocks.extractor, TileExtractor.sRecipeHandler, ID_EXTRACTOR);
        registerJeiRecipes(registry, ElementtimesBlocks.elementGenerator, TileGeneratorElement.sRecipeHandler, ID_ELEMENT_GENERATOR);
        registerJeiRecipes(registry, ElementtimesBlocks.electrolyticCell, TileElectrolyticCell.RECIPE, ID_ELECTROLYTIC_CELL);
        registerJeiRecipes(registry, ElementtimesBlocks.condenser, TileCondenser.RECIPE, ID_CONDENSER);
        registerJeiRecipes(registry, ElementtimesBlocks.compressor, TileCompressor.sRecipeHandler, ID_COMPRESSOR);
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
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        IGuiHelper guiHelper = registry.getJeiHelpers().getGuiHelper();
        registry.addRecipeCategories(MachineRecipeCategory.createOneToOne(guiHelper, ID_ITEM_REDUCER, "itemreducer"));
        registry.addRecipeCategories(MachineRecipeCategory.createOneToOne(guiHelper, ID_COMPRESSOR, "compressor"));
        registry.addRecipeCategories(MachineRecipeCategory.createOneToOne(guiHelper, ID_REBUILD, "rebuild"));
        registry.addRecipeCategories(MachineRecipeCategory.createOneToOne(guiHelper, ID_PULVERIZE, "pulverize"));
        registry.addRecipeCategories(MachineRecipeCategory.createOneToOne(guiHelper, ID_EXTRACTOR, "extractor"));
        registry.addRecipeCategories(MachineRecipeCategory.createOneToOne(guiHelper, ID_FORMING, "forming"));
        registry.addRecipeCategories(new MachineRecipeCategory(guiHelper, "pump", ID_PUMP_AIR, "pumpair",
                45, 21, 80, 61, new int[0][], new int[][]{{77, 28}}));
        registry.addRecipeCategories(new MachineRecipeCategory(guiHelper, "condenser", ID_CONDENSER, "condenser",
                4, 11, 168, 92, new int[0][], new int[][]{{17, 25}, {143, 25}}));
        registry.addRecipeCategories(new MachineRecipeCategory(guiHelper, "electrolyticcell", ID_ELECTROLYTIC_CELL, "electrolyticcell",
                32, 8, 112, 93, new int[0][], new int[][]{{36, 12}, {88, 12}, {106, 12}, {124, 12}}));
        registry.addRecipeCategories(new MachineRecipeCategory(guiHelper, "0", ID_ELEMENT_GENERATOR, "elementgenerator",
                40, 24, 95, 39, new int[][]{{80, 30}}, new int[0][]));
        registry.addRecipeCategories(new MachineRecipeCategory(guiHelper, "fluidheater", ID_FLUID_HEATER, "fluidheater",
                4, 11, 168, 92, new int[0][], new int[][]{{17, 25}, {143, 25}}));
        registry.addRecipeCategories(new MachineRecipeCategory(guiHelper, "fluidreactor", ID_FLUID_REACTOR, "fluidreactor",
                14, 11, 148, 93, new int[][]{{88, 30}}, new int[][]{{18, 15}, {36, 15}, {106, 15}, {124, 15}, {142, 15}}));
        registry.addRecipeCategories(new MachineRecipeCategory(guiHelper, "solidmelter", ID_SOLID_MELTER, "solidmelter",
                38, 12, 100, 56, new int[][]{{45,31}}, new int[][]{{95,16}}));
        registry.addRecipeCategories(new MachineRecipeCategory(guiHelper, "solidreactor", ID_SOLID_REACTOR, "solidreactor",
                30, 5, 117, 96, new int[][]{{35,42}, {53,42}, {105,33}, {105,51}}, new int[][]{{126,11}}));
        registry.addRecipeCategories(new MachineRecipeCategory(guiHelper, "solidfluidreactor", ID_SOLID_FLUID_REACTOR, "solidfluidreactor",
                17, 10, 141, 55, new int[][]{{22,30}, {43,66}, {116,66}}, new int[][]{{42,15}, {116,15}, {137,15}}));
        registry.addRecipeCategories(new MachineRecipeCategory(guiHelper, "centrifuge", ID_CENTRIFUGE, "centrifuge",
                16, 13, 146, 52, new int[0][], new int[][]{{18, 15}, {70, 15}, {88,15}, {106,15}, {124, 15}, {142, 15}}));
        registry.addRecipeCategories(new MachineRecipeCategory(guiHelper, "coagulator", ID_COAGULATOR, "coagulator",
                44, 7, 83, 73, new int[][]{{108,30}}, new int[][]{{56,10}}));
        registry.addRecipeCategories(new MachineRecipeCategory(guiHelper, "solidcentrifuge", ID_SOLID_CENTRIFUGE, "solidcentrifuge",
                52, 12, 76, 58, new int[][] {{55, 31}, {109, 15}, {109, 33}, {109, 51}}, new int[0][]));
    }

    private void registerJeiRecipes(IModRegistry registry, Block machine, MachineRecipeHandler recipes, String id) {
        List<MachineRecipeWrapper> wrappers = MachineRecipeWrapper.fromHandler(recipes);
        registry.addRecipes(wrappers, id);
        registry.addRecipeCatalyst(new ItemStack(machine), id);
    }
}
