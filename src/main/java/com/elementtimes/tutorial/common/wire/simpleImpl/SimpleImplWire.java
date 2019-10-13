/*
* @Title: SimpleImplWire.java
* @Package minedreams.mi.simpleImpl
* @author EmptyDreams
* @date 2019年8月31日 下午6:02:47
* @version V1.0
*/
package com.elementtimes.tutorial.common.wire.simpleImpl;

import com.elementtimes.tutorial.common.wire.TileEntityWire;
import com.elementtimes.tutorial.common.wire.simpleImpl.manager.InfoManagerTileEntity;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 * 此类用于所有电线的数据同步
 * @author EmptyDremas
 */
public final class SimpleImplWire implements IMessage {
	
	/** 存储需要同步的电线 */
	public final InfoManagerTileEntity manager = new InfoManagerTileEntity();
	/** 需要同步的方块 */
	private TileEntityWire nbt;
	
	public void ini(TileEntityWire nbt) {
		this.nbt = nbt;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		manager.readFrom(buf);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		manager.writeTo(nbt, buf);
	}
	
	public static final class ReturnHandler implements IMessageHandler<WireToServerMessage, IMessage> {
		
		@Override
		public IMessage onMessage(WireToServerMessage message, MessageContext ctx) {
			if (message.dimension == -100) {
				return null;
			}
			World world = FMLCommonHandler.instance().getMinecraftServerInstance().getWorld(message.dimension);
			TileEntityWire nbt = (TileEntityWire) world.getTileEntity(message.pos.getInfo());
			EntityPlayerMP player = ctx.getServerHandler().player;
			nbt.players.add(player.getName());
			return null;
		}
		
	}
	
}
