package com.elementtimes.tutorial.common;

import com.elementtimes.tutorial.common.autonet.NetworkLoader;
import com.elementtimes.tutorial.common.init.ElementtimesRecipe;
import com.elementtimes.tutorial.common.pipeline.ElementType;
import com.elementtimes.tutorial.common.pipeline.FluidElement;
import com.elementtimes.tutorial.common.pipeline.ItemElement;
import com.elementtimes.tutorial.plugin.slashblade.BladeElementknife;
import mods.flammpfeil.slashblade.SlashBlade;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.oredict.OreDictionary;

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
        ElementtimesRecipe.init(event);
        ElementType.register(FluidElement.TYPE, ItemElement.TYPE);
    }

    public void postInit(@SuppressWarnings("unused") FMLPostInitializationEvent event) {
        FurnaceRecipes.instance().getSmeltingList().keySet()
                .removeIf(input -> {
                    for (int oreID : OreDictionary.getOreIDs(input)) {
                        String oreName = OreDictionary.getOreName(oreID);
                        if ("crushedPurifiedCopper".equals(oreName) || "crushedPurifiedIron".equals(oreName) || "crushedCopper".equals(oreName) || "crushedIron".equals(oreName)) {
                            return true;
                        }
                    }
                    return input.getItem() == Item.getItemFromBlock(Blocks.IRON_ORE);
                });
    }
}
