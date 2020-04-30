package com.elementtimes.tutorial;

import com.elementtimes.elementcore.ElementCore;
import com.elementtimes.elementcore.api.annotation.enums.LoadState;
import com.elementtimes.elementcore.api.common.ECModContainer;
import com.elementtimes.elementcore.api.common.ECModElements;
import com.elementtimes.tutorial.common.CommonProxy;
import com.elementtimes.tutorial.plugin.elementcore.JeiRecipe;
import com.elementtimes.tutorial.plugin.elementcore.SSMRegister;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/**
 * @author qinglan,luqin2007
 * TODO
 * 1.烧杯GUI
 * 2.禁止ic2铁矿粉和铜矿粉燃烧，使用固体反应器等同元素时代铁矿粉和铜矿粉
 * 5.发酵机未完成（豆焰的酶还没画呢）
 * 6.支持沙子粉(物品id:sandpowder,已经注册了)熄灭酒精灯
 * 7.化学实验机器诸多问题
 * 9.通过矿词禁用其他模组矿粉，纯净矿粉燃烧。如下:"crushedPurifiedCopper","crushedPurifiedIron","crushedCopper","crushedIron".备注：固体、固液已经完成了相应矿词的反应。
 */
@Mod(modid = ElementTimes.MODID, name = "Element Times", version = "@version@", dependencies = "required-after:elementcore@[0.6.0,1.0.0);before:guideapi")
public class ElementTimes {
    public static final String MODID = "elementtimes";

    @SidedProxy(serverSide = "com.elementtimes.tutorial.common.CommonProxy", clientSide = "com.elementtimes.tutorial.client.ClientProxy")
    public static CommonProxy proxy;

    @Mod.Instance(ElementTimes.MODID)
    public static ElementTimes instance;

    public static ECModContainer CONTAINER;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInit(event);
        ECModElements.Builder builder = ElementCore.builder()
                .registerAnnotation(LoadState.Init, SSMRegister.SupportStandModule.class, SSMRegister::register)
                .registerAnnotation(LoadState.Init, JeiRecipe.MachineRecipe.class, JeiRecipe::parser);
        CONTAINER = builder.build(event);
        IC2Support.register();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }
}
