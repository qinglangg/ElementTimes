package com.elementtimes.tutorial.network;

import com.elementtimes.tutorial.Elementtimes;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

/**
 * 同步客户端数据
 *
 * @author KSGFK create in 2019/2/17
 */
public class ElementGenerater implements IMessage {
    private int energy;
    private int maxEnergy;
    private int gening;
    private int maxGen;

    public ElementGenerater() {//不能删,不能改,会报错
    }

    public ElementGenerater(int energy, int maxEnergy, int gening, int maxGen) {
        this.energy = energy;
        this.maxEnergy = maxEnergy;
        this.gening = gening;
        this.maxGen = maxGen;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        String e = ByteBufUtils.readUTF8String(buf);
        String m = ByteBufUtils.readUTF8String(buf);
        String g = ByteBufUtils.readUTF8String(buf);
        String mg = ByteBufUtils.readUTF8String(buf);
        energy = Integer.parseInt(e);
        maxEnergy = Integer.parseInt(m);
        gening = Integer.parseInt(g);
        maxGen = Integer.parseInt(mg);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, String.valueOf(energy));
        ByteBufUtils.writeUTF8String(buf, String.valueOf(maxEnergy));
        ByteBufUtils.writeUTF8String(buf, String.valueOf(gening));
        ByteBufUtils.writeUTF8String(buf, String.valueOf(maxGen));
    }

    private int getEnergy() {
        return energy;
    }

    private int getMaxEnergy() {
        return maxEnergy;
    }

    private int getGening() {
        return gening;
    }

    public int getMaxGen() {
        return maxGen;
    }

    public static class Handler implements IMessageHandler<ElementGenerater, IMessage> {

        @Override
        public IMessage onMessage(ElementGenerater message, MessageContext ctx) {
            if (ctx.side == Side.CLIENT) {
                Minecraft.getMinecraft().addScheduledTask(() -> {
                    Elementtimes.getGui().getGenerater().setEnergy(message.getEnergy());
                    Elementtimes.getGui().getGenerater().setMaxEnergy(message.getMaxEnergy());
                    Elementtimes.getGui().getGenerater().setPowerGening(message.getGening());
                    Elementtimes.getGui().getGenerater().setMaxPowerGen(message.getMaxGen());
                });
            }
            return null;
        }
    }
}
