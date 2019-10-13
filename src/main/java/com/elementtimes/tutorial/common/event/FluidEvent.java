package com.elementtimes.tutorial.common.event;

import com.elementtimes.tutorial.ElementTimes;
import net.minecraftforge.fml.common.Mod;

/**
 * 流体相关事件
 * TODO: 牛奶
 * 看 forge 源码，ItemMilkBucket 里面好像获取了一个名为 milk 的流体，暂没验证 forge 是否注册了名为 milk 的 fluid
 * 暂定方案 创建名为 milk 的流体，bucket=false，通过事件关联原版牛奶桶
 * 标记：https://sourcegraph.com/github.com/Lothrazar/Cyclic@01e4322039150710fa329e9c2a0951f3cc65ab76/-/blob/src/main/java/com/lothrazar/cyclicmagic/liquid/milk/FluidMilk.java
 * @author luqin2007
 */
@Mod.EventBusSubscriber(modid = ElementTimes.MODID)
public class FluidEvent {

//    @SubscribeEvent
//    public static void onFill(FillBucketEvent event) {
//    }
//
//    @SubscribeEvent
//    public static void onDrain() {
//    }
}
