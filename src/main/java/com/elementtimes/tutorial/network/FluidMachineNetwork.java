package com.elementtimes.tutorial.network;

import com.elementtimes.tutorial.annotation.annotations.ModNetwork;
import com.elementtimes.tutorial.inventory.base.ContainerMachine;
import com.elementtimes.tutorial.other.SideHandlerType;
import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.ints.Int2IntArrayMap;
import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import net.minecraftforge.fluids.capability.templates.FluidHandlerConcatenate;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.HashMap;
import java.util.Map;

/**
 * 用于流体参与的机器的通信
 * @author luqin2007
 */
@ModNetwork(id = 0, handlerClass = "com.elementtimes.tutorial.network.FluidMachineNetwork$Handler", side = Side.CLIENT)
public class FluidMachineNetwork implements IMessage {

    Map<SideHandlerType, Int2ObjectMap<FluidStack>> fluids = new HashMap<>();
    Map<SideHandlerType, Int2IntMap> capabilities = new HashMap<>();

    public FluidMachineNetwork() { }

    public void put(SideHandlerType type, FluidHandlerConcatenate fluidHandler) {
        IFluidTankProperties[] properties = fluidHandler.getTankProperties();
        Int2ObjectMap<FluidStack> rFluids = new Int2ObjectArrayMap<>(properties.length);
        Int2IntMap rCapabilities = new Int2IntArrayMap(properties.length);
        for (int i = 0; i < properties.length; i++) {
            rFluids.put(i, properties[i].getContents());
            rCapabilities.put(i, properties[i].getCapacity());
        }
        fluids.put(type, rFluids);
        capabilities.put(type, rCapabilities);
    }

    /**
     * int typeCount
     * [
     *  String type
     *  int count
     *  [
     *      NBT fluid
     *      int capability
     *  ]
     * ]
     * @param buf bug
     */
    @Override
    @SideOnly(Side.CLIENT)
    public void fromBytes(ByteBuf buf) {
        int typeCount = buf.readInt();
        for (int t = 0; t < typeCount; t++) {
            SideHandlerType type = SideHandlerType.valueOf(ByteBufUtils.readUTF8String(buf));
            int count = buf.readInt();
            Int2ObjectMap<FluidStack> rFluids = new Int2ObjectArrayMap<>(count);
            Int2IntMap rCapabilities = new Int2IntArrayMap(count);
            for (int i = 0; i < count; i++) {
                int slot = buf.readInt();
                FluidStack fluid = FluidStack.loadFluidStackFromNBT(ByteBufUtils.readTag(buf));
                rFluids.put(slot, fluid);
                rCapabilities.put(slot, buf.readInt());
            }
            fluids.put(type, rFluids);
            capabilities.put(type, rCapabilities);
            System.out.println("parse");
        }
    }

    @Override
    @SideOnly(Side.SERVER)
    public void toBytes(ByteBuf buf) {
        buf.writeInt(fluids.size());
        fluids.keySet().forEach(type -> {
            ByteBufUtils.writeUTF8String(buf, type.name());
            Int2ObjectMap<FluidStack> rFluids = fluids.get(type);
            Int2IntMap rCapabilities = capabilities.get(type);
            buf.writeInt(rFluids.size());
            rFluids.keySet().forEach(slot -> {
                buf.writeInt(slot);
                FluidStack fluid = rFluids.get(slot);
                ByteBufUtils.writeTag(buf, fluid.writeToNBT(new NBTTagCompound()));
                buf.writeInt(rCapabilities.get(slot));
            });
            System.out.println("send");
        });
    }

    public static class Handler implements IMessageHandler<FluidMachineNetwork, IMessage> {

        @Override
        public IMessage onMessage(FluidMachineNetwork message, MessageContext ctx) {
            ContainerMachine.FLUIDS.clear();
            message.fluids.keySet().forEach(type -> {
                Int2ObjectMap<FluidStack> rFluids = message.fluids.get(type);
                Int2IntMap rCapabilities = message.capabilities.get(type);
                ContainerMachine.FLUIDS.put(type, new Int2ObjectArrayMap<>(rFluids.size()));
                rFluids.keySet().forEach(slot -> {
                    FluidStack fluidStack = rFluids.get(slot);
                    int capability = rCapabilities.get(slot);
                    ContainerMachine.FLUIDS.get(type).put(slot, ImmutablePair.of(fluidStack, capability));
                });
            });
            System.out.println("receive");
            return null;
        }
    }
}
