/*
* @Title: NetworkLoader.java
* @Package minedreams.mi.simpleImpl
* @author EmptyDreams
* @date 2019年8月31日 下午6:38:07
* @version V1.0
*/
package com.elementtimes.tutorial.common.wire.simpleImpl;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

import static net.minecraftforge.fml.relauncher.Side.CLIENT;
import static net.minecraftforge.fml.relauncher.Side.SERVER;

/**
 * @author EmptyDremas
 *
 */
public final class NetworkLoader {

	public static final SimpleNetworkWrapper instance = NetworkRegistry.INSTANCE.newSimpleChannel("elementtimes");

	private int nextID = 0;
	 
	public NetworkLoader() {
		registerMessage(SimpleImplWire.ReturnHandler.class, WireToServerMessage.class, SERVER);
		registerMessage(
				com.elementtimes.tutorial.common.wire.simpleImpl.client.WireHandler.class, SimpleImplWire.class, CLIENT);
	}
	
	private <REQ extends IMessage, REPLY extends IMessage> void registerMessage(
			Class<? extends IMessageHandler<REQ, REPLY>> messageHandler, Class<REQ> requestMessageType, Side side) {
		instance.registerMessage(messageHandler, requestMessageType, nextID++, side);
	}
	
}
