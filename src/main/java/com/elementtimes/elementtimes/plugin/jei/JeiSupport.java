package com.elementtimes.elementtimes.plugin.jei;

import com.elementtimes.elementcore.api.recipe.BaseRecipe;
import com.elementtimes.elementcore.api.recipe.IRecipeType;
import com.elementtimes.elementcore.api.recipe.RecipeManager;
import com.elementtimes.elementcore.api.tileentity.BaseTileEntity;
import com.elementtimes.elementtimes.ElementTimes;
import com.elementtimes.elementtimes.common.block.gui.*;
import com.elementtimes.elementtimes.common.block.recipe.*;
import com.elementtimes.elementtimes.common.init.blocks.Agriculture;
import com.elementtimes.elementtimes.common.init.blocks.Industry;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaRecipeCategoryUid;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;

/**
 * JEI 兼容
 * @author luqin
 */
@JeiPlugin
public class JeiSupport implements IModPlugin {

    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(ElementTimes.MODID, "jei");
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(Industry.furnace), VanillaRecipeCategoryUid.FURNACE);
        registration.addRecipeCatalyst(new ItemStack(Industry.generatorFuel), VanillaRecipeCategoryUid.FUEL);
        registration.addRecipeCatalyst(new ItemStack(Industry.generatorFluid), VanillaRecipeCategoryUid.FUEL);
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        Set<ItemStack> remove = Collections.singleton(new ItemStack(Agriculture.cornCrop));
        registration.getIngredientManager().removeIngredientsAtRuntime(VanillaTypes.ITEM, remove);
        addRecipes(registration, CentrifugeRecipe.TYPE, CentrifugeData.INSTANCE);
        addRecipes(registration, CoagulatorRecipe.TYPE, CoagulatorData.INSTANCE);
        addRecipes(registration, CompressorRecipe.TYPE, CompressorData.INSTANCE);
        addRecipes(registration, CentrifugeRecipe.TYPE, CondenserData.INSTANCE);
        addRecipes(registration, ElectrolyticCellRecipe.TYPE, ElectrolyticCellData.INSTANCE);
        addRecipes(registration, ElementGeneratorRecipe.TYPE, ElementGeneratorData.INSTANCE);
        addRecipes(registration, ExtractorRecipe.TYPE, ExtractorData.INSTANCE);
        addRecipes(registration, FermenterRecipe.TYPE, FermenterData.INSTANCE);
        addRecipes(registration, FluidHeaterRecipe.TYPE, FluidHeaterData.INSTANCE);
        addRecipes(registration, FluidReactorRecipe.TYPE, FluidReactorData.INSTANCE);
        addRecipes(registration, FormingRecipe.TYPE, FormingData.INSTANCE);
        addRecipes(registration, ItemReducerRecipe.TYPE, ItemReducerData.INSTANCE);
        addRecipes(registration, PulverizerRecipe.TYPE, PulverizerData.INSTANCE);
        addRecipes(registration, RebuildRecipe.TYPE, RebuildData.INSTANCE);
        addRecipes(registration, SolidCentrifugeRecipe.TYPE, SolidCentrifugeData.INSTANCE);
        addRecipes(registration, SolidFluidReactorRecipe.TYPE, SolidFluidReactorData.INSTANCE);
        addRecipes(registration, SolidMelterRecipe.TYPE, SolidMelterData.INSTANCE);
        addRecipes(registration, SolidReactorRecipe.TYPE, SolidReactorData.INSTANCE);
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        // todo x y u v
        addCategory(registration, CentrifugeRecipe.class, CentrifugeData.INSTANCE, 0, 0, 0, 0);
        addCategory(registration, CoagulatorRecipe.class, CoagulatorData.INSTANCE, 0, 0, 0, 0);
        addCategory(registration, CompressorRecipe.class, CompressorData.INSTANCE, 0, 0, 0, 0);
        addCategory(registration, CentrifugeRecipe.class, CondenserData.INSTANCE, 0, 0, 0, 0);
        addCategory(registration, ElectrolyticCellRecipe.class, ElectrolyticCellData.INSTANCE, 0, 0, 0, 0);
        addCategory(registration, ElementGeneratorRecipe.class, ElementGeneratorData.INSTANCE, 0, 0, 0, 0);
        addCategory(registration, ExtractorRecipe.class, ExtractorData.INSTANCE, 0, 0, 0, 0);
        addCategory(registration, FermenterRecipe.class, FermenterData.INSTANCE, 0, 0, 0, 0);
        addCategory(registration, FluidHeaterRecipe.class, FluidHeaterData.INSTANCE, 0, 0, 0, 0);
        addCategory(registration, FluidReactorRecipe.class, FluidReactorData.INSTANCE, 0, 0, 0, 0);
        addCategory(registration, FormingRecipe.class, FormingData.INSTANCE, 0, 0, 0, 0);
        addCategory(registration, ItemReducerRecipe.class, ItemReducerData.INSTANCE, 0, 0, 0, 0);
        addCategory(registration, PulverizerRecipe.class, PulverizerData.INSTANCE, 0, 0, 0, 0);
        addCategory(registration, RebuildRecipe.class, RebuildData.INSTANCE, 0, 0, 0, 0);
        addCategory(registration, SolidCentrifugeRecipe.class, SolidCentrifugeData.INSTANCE, 0, 0, 0, 0);
        addCategory(registration, SolidFluidReactorRecipe.class, SolidFluidReactorData.INSTANCE, 0, 0, 0, 0);
        addCategory(registration, SolidMelterRecipe.class, SolidMelterData.INSTANCE, 0, 0, 0, 0);
        addCategory(registration, SolidReactorRecipe.class, SolidReactorData.INSTANCE, 0, 0, 0, 0);
    }

    private <R extends BaseRecipe, T extends BaseTileEntity> void addCategory(IRecipeCategoryRegistration registration, Class<R> recipeClass, BaseGuiData<T> data, int u, int v, int w, int h) {
        registration.addRecipeCategories(new MachineCategory<>(registration.getJeiHelpers().getGuiHelper(), recipeClass, data, u, v, w, h));
    }
    
    private void addRecipes(IRecipeRegistration registration, IRecipeType<? extends BaseRecipe> type, BaseGuiData<?> data) {
        registration.addRecipes(RecipeManager.getAll(type), Objects.requireNonNull(data.getBlock().getRegistryName()));
    }
}
