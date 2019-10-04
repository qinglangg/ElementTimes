/**   
* @Title: InfoLocation.java
* @Package minedreams.mi.simpleImpl.info
* @author EmptyDreams
* @date 2019年9月8日 上午11:06:50
* @version V1.0
*/
package com.elementtimes.tutorial.common.wire.simpleImpl.info;

import io.netty.buffer.ByteBuf;
import net.minecraft.util.math.BlockPos;

/**
 * @author EmptyDremas
 *
 */
public final class InfoLocation implements SimpleImplInfo<BlockPos> {

	private BlockPos pos;
	
	@Override
	public BlockPos getInfo() {
		return pos;
	}

	@Override
	public void add(BlockPos info) {
		pos = info;
	}

	@Override
	public void delete(BlockPos info) {
		pos = null;
	}

	@Override
	public void writeTo(ByteBuf buf) {
		if (pos == null) {
			buf.writeBoolean(false);
		} else {
			buf.writeBoolean(true);
			buf.writeInt(pos.getX());
			buf.writeInt(pos.getY());
			buf.writeInt(pos.getZ());
		}
	}

	@Override
	public void readFrom(ByteBuf buf) {
		if (buf.readBoolean())
			pos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
	}

}
