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
 * @TODO  1.流体发电机流体发电显示  2.元素发电机元素发电显示 3.管道 4.太阳能发电机无法发电 5.烧杯GUI 6.玉米地干以后停止生长 7.导线（空梦部分） 8.jei格子对齐
 * 9.JEI输出不显示名字	10.JEI流体输出时无流体显示为水不消耗 11.坩埚和蒸发皿没法用 12.支持沙子粉(物品id:sandpowder,已经注册了)熄灭酒精灯
 * 13.蒸发皿组合打不开gui 14.坩埚组合打开gui放不进东西，而且缺少放入酒精的物品格子，只有流体格子（让豆焰再画个）
 * 15.支持玩家对着方块并在手持下桶在shift键并且右键放入流体桶 
 * 16.添加竹子自然生成并且可以生长（完全抄mc高版本，可能得物品变成方块，具体看高版本竹子咋写的），添加完成以后移除现存的打草掉落竹子的方式，和打草掉落玉米在一个类
 * 17.输入栏的流体无法用空桶挖出（刚发现的）	
 * 以下为可选TODO
 * 18.支持鼠标移动gui里面流体顺序，默认输出最下面的
 * 19.引导书兼容，详细模组步骤资料请请抄袭网易开发者内容管理里面写的元素时代资料(共十六章篇)
 * 20.发酵机未完成（豆焰的酶还没画呢）
 * 21.联动并且支持匠魂（卿岚部分）
 */
@Mod(modid = ElementTimes.MODID, name = "Element Times", version = "@version@", dependencies = "required-after:elementcore@[0.2.5,0.3.0);before:guideapi")
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
