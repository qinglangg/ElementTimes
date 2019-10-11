package com.elementtimes.tutorial.common;

import com.elementtimes.tutorial.common.event.OreEvent;
import com.elementtimes.tutorial.common.init.ElementtimesGUI;
import com.elementtimes.tutorial.common.init.ElementtimesRecipe;
import com.elementtimes.tutorial.common.wire.simpleImpl.NetworkLoader;
import com.elementtimes.tutorial.plugin.slashblade.BladeElementknife;
import mods.flammpfeil.slashblade.SlashBlade;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/**
 * 服务器端 Proxy
 *
 * @author qinglan,ksgfk,luqin2007
 */
public class CommonProxy {

    public void preInit(FMLPreInitializationEvent event) {
    	new NetworkLoader();
        String flammPfeil = "flammpfeil.slashblade";
        if (Loader.isModLoaded(flammPfeil)) {
            SlashBlade.InitEventBus.register(new BladeElementknife());
        }
    }

    public void init(FMLInitializationEvent event) {
        new ElementtimesGUI().init();
        ElementtimesRecipe.init(event);
        MinecraftForge.ORE_GEN_BUS.register(OreEvent.class);
    }

    public void postInit(FMLPostInitializationEvent event) {
        FurnaceRecipes.instance().getSmeltingList().keySet()
                .removeIf(input -> input.getItem() == Item.getItemFromBlock(Blocks.IRON_ORE));
    }
}
