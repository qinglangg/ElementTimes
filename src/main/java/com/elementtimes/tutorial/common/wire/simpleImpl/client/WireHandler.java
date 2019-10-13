/*
 * @Title: WireHandler.java
 * @Package minedreams.mi.simpleImpl.client
 * @author EmptyDreams
 * @date 2019年9月22日 下午2:26:41
 * @version V1.0
 */
package com.elementtimes.tutorial.common.wire.simpleImpl.client;

import com.elementtimes.tutorial.common.wire.TileEntityWire;
import com.elementtimes.tutorial.common.wire.simpleImpl.SimpleImplWire;
import com.elementtimes.tutorial.common.wire.simpleImpl.WireToServerMessage;
import com.elementtimes.tutorial.common.wire.simpleImpl.info.InfoLocation;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author EmptyDremas
 *
 */
public final class WireHandler implements IMessageHandler<SimpleImplWire, WireToServerMessage> {
	
	@Override
	@Nullable
	public WireToServerMessage onMessage(@Nonnull SimpleImplWire message, @Nonnull MessageContext ctx) {
		InfoLocation pos = (InfoLocation) message.manager.get(3);
		WireToServerMessage wt = new WireToServerMessage();
		wt.dimension = -100;
		if (pos == null) {
			return wt;
		}
		TileEntityWire nbt = (TileEntityWire) net.minecraft.client.Minecraft.getMinecraft().world.getTileEntity(pos.getInfo());
		if (nbt == null) {
			return wt;
		}
		nbt.deleteAllLink();
		message.manager.readTo(nbt);
		
		wt.dimension = (Integer) message.manager.get(4);
		wt.pos = pos;
		net.minecraft.client.Minecraft.getMinecraft().world.markBlockRangeForRenderUpdate(pos.getInfo(), pos.getInfo());
		return wt;
	}
}
