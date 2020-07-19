package com.elementtimes.elementtimes;

import com.elementtimes.elementcore.ElementCore;
import com.elementtimes.elementcore.api.ECModContainer;
import com.elementtimes.elementtimes.common.block.recipe.Recipes;
import com.elementtimes.elementtimes.common.block.stand.module.*;
import com.elementtimes.elementtimes.common.block.stand.te.TileSupportStand;
import com.elementtimes.elementtimes.common.init.Items;
import com.elementtimes.elementtimes.common.init.blocks.Ore;
import com.elementtimes.elementtimes.config.Config;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartedEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;



@Mod(ElementTimes.MODID)
public class ElementTimes {
    public static final String MODID = "elementtimes";

    public static ECModContainer CONTAINER;

    public ElementTimes() {
        CONTAINER = ElementCore.builder().disableDebugMessage().useSimpleNetwork().build();
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.build());
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onInit);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onInit);
    }

    private void onInit(FMLCommonSetupEvent event) {
        BrewingRecipeRegistry.addRecipe(Ingredient.fromItems(Blocks.COAL_BLOCK), Ingredient.fromItems(Blocks.DIAMOND_BLOCK), new ItemStack(Ore.blockRawDiamond));
        BrewingRecipeRegistry.addRecipe(Ingredient.fromItems(Blocks.COAL_BLOCK), Ingredient.fromItems(net.minecraft.item.Items.DIAMOND), new ItemStack(Items.diamondIngot));
        Recipes.registerRecipes();
        // 酒精灯 Module
        TileSupportStand.register(ModuleAlcoholLamp.KEY, ModuleAlcoholLamp::new);
        TileSupportStand.register(ModuleBeaker.KEY, ModuleBeaker::new);
        TileSupportStand.register(ModuleCrucible.KEY, ModuleCrucible::new);
        TileSupportStand.register(ModuleEvaporatingDish.KEY, ModuleEvaporatingDish::new);
    }

    private void onServerInit(FMLServerStartedEvent event) {
        event.getServer().getRecipeManager();
        // todo 更新木板合成表。注意 reload 时也要加载
    }
}
