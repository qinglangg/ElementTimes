/**   
* @Title: BooleanMessage.java
* @Package minedreams.mi.simpleImpl
* @author EmptyDreams
* @date 2019年9月1日 下午1:45:53
* @version V1.0
*/
package com.elementtimes.tutorial.common.wire.simpleImpl;

import com.elementtimes.tutorial.common.wire.simpleImpl.info.InfoLocation;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

/**
 * @author EmptyDremas
 *
 */
public final class WireToServerMessage implements IMessage {

	public InfoLocation pos;
	public int dimension;
	
	@Override
	public void fromBytes(ByteBuf buf) {
		pos = new InfoLocation();
		dimension = buf.readInt();
		if (buf.readBoolean()) {
			pos.readFrom(buf);
		}
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(dimension);
		if (pos != null) {
			buf.writeBoolean(true);
			pos.writeTo(buf);
		} else {
			buf.writeBoolean(false);
		}
	}

}
