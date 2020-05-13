package com.elementtimes.tutorial.plugin.jei;

import com.elementtimes.elementcore.api.template.tileentity.interfaces.IGuiProvider;
import com.elementtimes.elementcore.api.template.tileentity.recipe.MachineRecipeHandler;
import com.elementtimes.tutorial.common.init.ElementtimesBlocks;
import com.elementtimes.tutorial.plugin.jei.category.MachineRecipeCategory;
import com.elementtimes.tutorial.plugin.jei.wrapper.MachineRecipeWrapper;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.ingredients.IIngredientBlacklist;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import mezz.jei.api.recipe.VanillaRecipeCategoryUid;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * JEI 兼容
 * @author luqin
 */
@JEIPlugin
public class JeiSupport implements IModPlugin {

    private static final List<MachineRecipeData> MACHINES = new ArrayList<>();

    @Override
    public void register(IModRegistry registry) {
        // 玉米
        IIngredientBlacklist blacklist = registry.getJeiHelpers().getIngredientBlacklist();
        blacklist.addIngredientToBlacklist(new ItemStack(ElementtimesBlocks.cornCrop));
        // 机器配方
        for (MachineRecipeData machine : MACHINES) {
            registerJeiRecipes(registry, machine.block, machine.recipe, machine.id);
        }
        // 电炉
        registry.addRecipeCatalyst(new ItemStack(ElementtimesBlocks.furnace), VanillaRecipeCategoryUid.SMELTING);
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        // MachineRecipe 兼容
        IGuiHelper guiHelper = registry.getJeiHelpers().getGuiHelper();
        registry.addRecipeCategories(MACHINES.stream()
                .map(data -> new MachineRecipeCategory(guiHelper, data.gui, data.id, data.u, data.v, data.w, data.h))
                .toArray(MachineRecipeCategory[]::new));
    }

    private void registerJeiRecipes(IModRegistry registry, Block machine, MachineRecipeHandler recipes, String id) {
        List<MachineRecipeWrapper> wrappers = MachineRecipeWrapper.fromHandler(recipes);
        registry.addRecipes(wrappers, id);
        registry.addRecipeCatalyst(new ItemStack(machine), id);
    }

    public static void registerMachineRecipe(Block block, IGuiProvider gui, MachineRecipeHandler recipe, String id, int u, int v, int w, int h) {
        MACHINES.add(new MachineRecipeData(block, gui, recipe, id, u, v, w, h));
    }

    static class MachineRecipeData {
        Block block;
        IGuiProvider gui;
        String id;
        MachineRecipeHandler recipe;
        int u, v, w, h;
        MachineRecipeData(Block block, IGuiProvider gui, MachineRecipeHandler recipe, String id, int u, int v, int w, int h) {
            this.block = block;
            this.gui = gui;
            this.id = id;
            this.u = u;
            this.v = v;
            this.w = w;
            this.h = h;
            this.recipe =recipe;
        }
    }
}
