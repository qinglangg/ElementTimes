package com.elementtimes.tutorial;

import com.elementtimes.tutorial.annotation.processor.ElementRegister;
import com.elementtimes.tutorial.common.CommonProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * TODO: 物质重构机 删除盐-煤
 * TODO: 钢块合成表
 * TODO: 碳 煤炭(在购)
 * TODO: 物质重组机打粉机（6）该压缩机
 * TODO: 删除所有依赖外 mod 橡胶的合成表，使用矿辞实现；树叶、木、树苗精华中加入一个橡胶
 * TODO: 元素刀汉化
 * TODO: 酿造台 1000000-10000(io)-16000(/item)
 * TODO: 木精华从树叶获取。具体获取方法未知
 * TODO: 盐矿 盐 盐粉 盐矿生成在水里，类似粘土，吃精准；数量和硫矿同，包括掉落，锤粉（合成），燃烧，酿造
 * TODO: 整形机测试及 jei 兼容
 * TODO: 重构机 多层块 合成
 * TODO: 元素树及其材质
 * TODO: 橡胶树 树叶 橡胶矿典
 * TODO: 提取机：出橡胶，叶子出1，树苗出2
 * TODO: 化学
 * TODO: 1.13.2 1.14 兼容
 * TODO: 1.7.10 兼容（优先度极低）
 * @author KSGFK
 */
@Mod(modid = Elementtimes.MODID, name = "Element Times", version = "@version@")
public class Elementtimes {
    public static final String MODID = "elementtimes";

    @SidedProxy(serverSide = "com.elementtimes.tutorial.common.CommonProxy", clientSide = "com.elementtimes.tutorial.client.ClientProxy")
    public static CommonProxy proxy;

    @Mod.Instance(Elementtimes.MODID)
    public static Elementtimes instance;

    private static Logger logger = LogManager.getLogger("ElementsTime");

    public static Logger getLogger() {
        return logger;
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        ElementRegister.invokeMethod();
    }
}
