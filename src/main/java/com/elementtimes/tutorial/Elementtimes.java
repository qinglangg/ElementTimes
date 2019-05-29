package com.elementtimes.tutorial;

import com.elementtimes.tutorial.annotation.processor.ElementRegister;
import com.elementtimes.tutorial.common.CommonProxy;
import com.elementtimes.tutorial.common.event.BreakBlockListener;
import com.elementtimes.tutorial.common.init.*;
import com.elementtimes.tutorial.common.slashblade.BladeElementknife;
import com.elementtimes.tutorial.world.gen.WorldGenETOres;
import mods.flammpfeil.slashblade.SlashBlade;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// TODO: add: 酿造台 1000000-10000(io)-16000(/item)
// TODO: add: 木精华从树叶获取。具体获取方法未知
// TODO: add: 盐矿 盐 盐粉 盐矿生成在水里，类似粘土，吃精准；数量和硫矿同，包括掉落，锤粉（合成），燃烧，酿造
// TODO: add: 整形机测试及 jei 兼容
// TODO: add: 磨粉机添加 烈焰棒->烈焰粉*8 合成表
// TODO: add: 重构机 多层块 合成
// TODO: add: 化学
// TODO: add: 1.7.10 兼容

@Mod(modid = Elementtimes.MODID, name = "Element Times", version = "@version@")
public class Elementtimes {
    public static final String MODID = "elementtimes";

    @SidedProxy(serverSide = "com.elementtimes.tutorial.common.CommonProxy", clientSide = "com.elementtimes.tutorial.client.ClientProxy")
    public static CommonProxy proxy;

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
        ElementRegister.init();
        if (Loader.isModLoaded("flammpfeil.slashblade")) {
            SlashBlade.InitEventBus.register(new BladeElementknife());
        }
    }

    @EventHandler
    public void Init(FMLInitializationEvent event) {
        GameRegistry.registerWorldGenerator(new WorldGenETOres(), 0);
        gui.init();
        ElementtimesRecipe.Init(event);
    }
}
