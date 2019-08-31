package com.elementtimes.tutorial.network;

import com.elementtimes.elementcore.api.annotation.annotations.ModNetwork;
import com.elementtimes.tutorial.inventory.base.ContainerMachine;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

@ModNetwork(handlerClass = "com.elementtimes.tutorial.network.EnergyMachineNetwork$Handler", side = Side.CLIENT)
public class EnergyMachineNetwork implements IMessage {

    int capacity;
    int energy;

    public EnergyMachineNetwork(int capacity, int energy) {
        this.capacity = capacity;
        this.energy = energy;
    }

    public EnergyMachineNetwork() {
        this(0, 0);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        capacity = buf.readInt();
        energy = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(capacity);
        buf.writeInt(energy);
    }

    public static class Handler implements IMessageHandler<EnergyMachineNetwork, IMessage> {

        public Handler() {}

        @Override
        public IMessage onMessage(EnergyMachineNetwork message, MessageContext ctx) {
            ContainerMachine.ENERGY_ENERGY = message.energy;
            ContainerMachine.ENERGY_CAPACITY = message.capacity;
            return null;
        }
    }
}
