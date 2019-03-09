package com.elementtimes.tutorial;

import com.elementtimes.tutorial.common.CommonProxy;
import com.elementtimes.tutorial.common.init.*;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = Elementtimes.MODID, dependencies = "required-after:cofhcore@[4.6.1,)")
public class Elementtimes {
    public static final String MODID = "elementtimes";

    @SidedProxy(serverSide = "com.elementtimes.tutorial.common.CommonProxy", clientSide = "com.elementtimes.tutorial.client.ClientProxy")
    public static CommonProxy proxy;

    private static SimpleNetworkWrapper network;

    public static SimpleNetworkWrapper getNetwork() {
        return network;
    }

    @Mod.Instance(Elementtimes.MODID)
    public static Elementtimes instance;

    private static ElementtimesGUI gui = new ElementtimesGUI();

    public static ElementtimesGUI getGui() {
        return gui;
    }

    private static Logger logger = LogManager.getLogger("ElementsTime");

    public static Logger getLogger() {
        return logger;
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(proxy);
        ElementtimesTile.init();
    }

    @EventHandler
    public static void Init(FMLInitializationEvent event) {
        GameRegistry.registerWorldGenerator(new WorldGenOHROres(), 0);
        ModRecipes.init();
        network = NetworkRegistry.INSTANCE.newSimpleChannel(MODID);
        ElementtimesNetwork.init();
        gui.init();
        OreDictionary.registerOre("oreCopper", ElementtimesBlocks.Copperore);
    }
}
