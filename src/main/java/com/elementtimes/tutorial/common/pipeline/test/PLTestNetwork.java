package com.elementtimes.tutorial.common.pipeline.test;

import com.elementtimes.tutorial.ElementTimes;
import com.elementtimes.tutorial.common.pipeline.BaseElement;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.FMLCommonHandler;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PLTestNetwork {
    public static Map<BlockPos, List<ImmutablePair<TestElementType, BaseElement>>> data = new HashMap<>();

    public enum TestElementType {
        /**
         */
        input(0), output(1), send(2), receive(3), post(4), drop(5);

        public final int index;
        TestElementType(int index) {
            this.index = index;
        }

        public static TestElementType get(int i) {
            switch (i) {
                case 0: return input;
                case 1: return output;
                case 2: return send;
                case 3: return receive;
                case 4: return post;
                default: return drop;
            }
        }

        public String toString(BaseElement element) {
            switch (this) {
                case receive: return "receive: " + element.element;
                case output: return "output: " + element.element;
                case input: return "input: " + element.element;
                case send: return "send: " + element.element;
                case post: return "post: " + element.element;
                default: return "drop: " + element.element;
            }
        }
    }

    public static void sendMessage(TestElementType type, BaseElement element, BlockPos pos) {
        MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
        for (EntityPlayerMP player : server.getPlayerList().getPlayers()) {
            ElementTimes.CONTAINER.elements.channel.sendTo(new PLTestData(pos, element, type), player);
        }
    }
}
