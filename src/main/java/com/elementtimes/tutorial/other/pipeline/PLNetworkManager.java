package com.elementtimes.tutorial.other.pipeline;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.HashSet;
import java.util.Set;

/**
 * 管道网络管理
 * @author luqin2007
 */
public class PLNetworkManager {

    private static Set<PLNetwork> sNetworks = new HashSet<>();
    private static Set<PLNetwork> sNetworkDirty = new HashSet<>();

    public static PLNetwork join(World world, PLInfo pipeline) {
        return sNetworks.stream()
                .filter(net -> net.getDim() == world.provider.getDimension())
                .filter(net -> net.getType() == pipeline.getType().type())
                // TODO: 编译中断
                .findFirst()
                .get();
    }

    public static void markDirty(PLNetwork network) {
        if (network != null && sNetworks.contains(network)) {
            sNetworkDirty.add(network);
        }
    }

    public static void update() {
        sNetworkDirty.forEach(network -> {
            if (!network.isEmpty()) {
                // 。。。
            }
        });
    }

    public static void findWay() {

    }
}
