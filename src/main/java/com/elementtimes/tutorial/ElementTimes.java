package com.elementtimes.tutorial;

import com.elementtimes.elementcore.ElementCore;
import com.elementtimes.elementcore.api.annotation.enums.LoadState;
import com.elementtimes.elementcore.api.common.ECModContainer;
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
 * TODO  1.流体发电机  2.元素发电机JEI显示每个元素发电量 3.流体储存器 4.给矿粉加矿词(别加锭粉矿词) 5.烧杯GUI
 *  6.玉米地干以后居然还在生长，应该和原版一样停止继续长，并破碎掉落一个玉米
 * 7.导线（空梦部分） 8.jei格子对齐  9.JEI输出不显示名字  10.支持玩家对着方块并在手持下桶在shift键并且右键放入流体桶 
 * 11.输入栏的流体无法用空桶挖出	
 * 以下为可选TODO
 * 1.JEI流体输出时无流体显示为水不消耗
 * 2.添加竹子自然生成并且可以生长（完全抄mc高版本，可能得物品变成方块，具体看高版本竹子咋写的），添加完成以后移除现存的打草掉落竹子的方式，和打草掉落玉米在一个类
 * 3.支持鼠标移动gui里面流体顺序，默认输出最下面的
 * 4.引导书兼容，详细模组步骤资料请请抄袭网易开发者内容管理里面写的元素时代资料(共十六章篇)
 * 5.发酵机未完成（豆焰的酶还没画呢）
 * 6.支持沙子粉(物品id:sandpowder,已经注册了)熄灭酒精灯
 * 7.化学实验机器诸多问题
 */
@Mod(modid = ElementTimes.MODID, name = "Element Times", version = "@version@", dependencies = "required-after:elementcore@[0.4.0,0.5.0);before:guideapi")
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
        CONTAINER = ElementCore.builder()
                .disableDebugMessage()
                .registerAnnotation(LoadState.Init, SSMRegister.SupportStandModule.class, SSMRegister::register)
                .registerAnnotation(LoadState.Init, JeiRecipe.MachineRecipe.class, JeiRecipe::parser)
                .build(event);
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
