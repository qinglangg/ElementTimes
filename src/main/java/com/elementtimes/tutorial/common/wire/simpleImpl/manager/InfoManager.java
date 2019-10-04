/**   
* @Title: InfoManager.java
* @Package minedreams.mi.simpleImpl.manager
* @author EmptyDreams
* @date 2019年9月8日 上午11:13:53
* @version V1.0
*/
package com.elementtimes.tutorial.common.wire.simpleImpl.manager;

import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;

/**
 * @author EmptyDremas
 *
 */
public interface InfoManager<T extends TileEntity> {
	
	/** 管理的信息数量 */
	int size();
	
	/** 通过下标获取信息 */
	Object get(int index);
	
	/** 向te写入数据 */
	void writeTo(T t, ByteBuf buf);
	
	/** 从te读取数据 */
	void readFrom(ByteBuf buf);
	
	/** 读取数据到te中 */
	void readTo(T t);
	
}
