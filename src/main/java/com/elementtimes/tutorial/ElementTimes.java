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
 * 3.支持鼠标移动gui里面流体顺序，默认输出最下面的
 * 4.引导书兼容，详细模组步骤资料请请抄袭网易开发者内容管理里面写的元素时代资料(共十六章篇)
 * 5.发酵机未完成（豆焰的酶还没画呢）
 * 6.支持沙子粉(物品id:sandpowder,已经注册了)熄灭酒精灯
 * 7.化学实验机器诸多问题
 * bug:
 * 6.竹子生长问题
 * 非bug:
 * 1.木龙头支持ic2,参考https://github.com/TechReborn/TechReborn/tree/1.12?files=1
 * 2.竹板
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
