package com.elementtimes.tutorial.common.init;

import com.elementtimes.tutorial.Elementtimes;
import com.elementtimes.tutorial.network.ElementGenerater;
import net.minecraftforge.fml.relauncher.Side;

/**
 * 网络系统初始化
 *
 * @author KSGFK create in 2019/2/17
 */
public class ElementtimesNetwork {
    public static void init() {
        Elementtimes.getNetwork().registerMessage(ElementGenerater.Handler.class, ElementGenerater.class, 5, Side.CLIENT);
        Elementtimes.getNetwork().registerMessage(ElementGenerater.Handler.class, ElementGenerater.class, 6, Side.SERVER);
    }
}
