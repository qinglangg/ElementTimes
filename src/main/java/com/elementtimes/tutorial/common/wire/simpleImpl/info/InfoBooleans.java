/**   
* @Title: InfoBooleans.java
* @Package minedreams.mi.simpleImpl.info
* @author EmptyDreams
* @date 2019年9月8日 上午11:09:31
* @version V1.0
*/
package com.elementtimes.tutorial.common.wire.simpleImpl.info;

import java.util.ArrayList;
import java.util.List;

import io.netty.buffer.ByteBuf;

/**
 * @author EmptyDremas
 *
 */
public final class InfoBooleans implements SimpleImplInfo<Boolean> {

	private final List<Boolean> infos = new ArrayList<>(6);
	
	@Override
	public Boolean getInfo() {
		return (infos.size() == 0) ? null : infos.get(0);
	}

	/**
	 * 该方法返回值为内部存储的数据，根据规定用户不应该对返回值内容进行修改
	 */
	@Override
	public List<Boolean> getInfos() {
		return infos;
	}
	
	@Override
	public void add(Boolean info) {
		infos.add(info);
	}

	@Override
	public void delete(Boolean info) {
		infos.remove(info);
	}

	@Override
	public void writeTo(ByteBuf buf) {
		buf.writeInt(infos.size());
		for (boolean b : infos) {
			buf.writeBoolean(b);
		}
	}

	@Override
	public void readFrom(ByteBuf buf) {
		int size = buf.readInt();
		for (int i = 0; i < size; ++i) {
			infos.add(buf.readBoolean());
		}
	}

}
