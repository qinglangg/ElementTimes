package com.elementtimes.tutorial.common.pipeline.test;

import com.elementtimes.elementcore.api.annotation.ModNetwork;
import com.elementtimes.tutorial.common.pipeline.BaseElement;
import com.elementtimes.tutorial.common.pipeline.ElementType;
import io.netty.buffer.ByteBuf;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.ArrayList;
import java.util.Objects;

@ModNetwork(handlerClass = "com.elementtimes.tutorial.common.pipeline.test.PLTestData$Handler", side = Side.CLIENT)
public class PLTestData implements IMessage {

    public BlockPos pos;
    public BaseElement element;
    public PLTestNetwork.TestElementType type;

    public PLTestData() {}
    public PLTestData(BlockPos pos, BaseElement element, PLTestNetwork.TestElementType type) {
        this.pos = pos;
        this.element = element;
        this.type = type;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        pos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
        type = PLTestNetwork.TestElementType.get(buf.readInt());
        String type = ByteBufUtils.readUTF8String(buf);
        element = ElementType.TYPES.get(type).newInstance();
        element.deserializeNBT(Objects.requireNonNull(ByteBufUtils.readTag(buf)));
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(pos.getX());
        buf.writeInt(pos.getY());
        buf.writeInt(pos.getZ());
        buf.writeInt(type.index);
        ByteBufUtils.writeUTF8String(buf, element.type);
        ByteBufUtils.writeTag(buf, element.serializeNBT());
    }

    public static class Handler implements IMessageHandler<PLTestData, IMessage> {

        @Override
        public IMessage onMessage(PLTestData message, MessageContext ctx) {
            if (!PLTestNetwork.data.containsKey(message.pos)) {
                PLTestNetwork.data.put(message.pos, new ArrayList<>());
            }
            PLTestNetwork.data.get(message.pos).add(ImmutablePair.of(message.type, message.element));
            return null;
        }
    }
}
