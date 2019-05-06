package com.elementtimes.tutorial.common.init;

import com.elementtimes.tutorial.Elementtimes;
import com.elementtimes.tutorial.network.ElementGenerater;
import com.elementtimes.tutorial.network.PulMsg;
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

        Elementtimes.getNetwork().registerMessage(PulMsg.Handler.class, PulMsg.class, 7, Side.CLIENT);
        Elementtimes.getNetwork().registerMessage(PulMsg.Handler.class, PulMsg.class, 8, Side.SERVER);
    }
}
