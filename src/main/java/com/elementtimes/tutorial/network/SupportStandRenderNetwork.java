package com.elementtimes.tutorial.network;

import com.elementtimes.tutorial.annotation.annotations.ModNetwork;
import com.elementtimes.tutorial.interfaces.tileentity.ITESRSupport;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

import java.util.Objects;

/**
 * 用于 SupportStand 的 TESR 传递
 * @author luqin2007
 */
@ModNetwork(id = 2, handlerClass = "com.elementtimes.tutorial.network.SupportStandRenderNetwork$Handler", side = Side.CLIENT)
public class SupportStandRenderNetwork implements IMessage {

    public NBTTagCompound nbt;
    public int dim;
    public BlockPos pos;

    public SupportStandRenderNetwork() { }

    public SupportStandRenderNetwork(NBTTagCompound nbt, int dim, BlockPos pos) {
        this.nbt = nbt;
        this.dim = dim;
        this.pos = pos;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        nbt = ByteBufUtils.readTag(buf);
        pos = NBTUtil.getPosFromTag(Objects.requireNonNull(ByteBufUtils.readTag(buf)));
        dim = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeTag(buf, nbt);
        ByteBufUtils.writeTag(buf, NBTUtil.createPosTag(pos));
        buf.writeInt(dim);
    }

    public static class Handler implements IMessageHandler<SupportStandRenderNetwork, IMessage> {

        @Override
        public IMessage onMessage(SupportStandRenderNetwork message, MessageContext ctx) {
            World world = Minecraft.getMinecraft().world;
            if (world.provider.getDimension() == message.dim) {
                TileEntity te = world.getTileEntity(message.pos);
                if (te instanceof ITESRSupport) {
                    ((ITESRSupport) te).receiveRenderMessage(message.nbt);
                }
            }
            return null;
        }
    }
}
