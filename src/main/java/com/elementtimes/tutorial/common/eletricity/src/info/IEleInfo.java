package com.elementtimes.tutorial.common.eletricity.src.info;

import com.elementtimes.tutorial.common.eletricity.src.tileentity.EleSrcCable;
import net.minecraft.tileentity.TileEntity;

/**
 * 存储电力方块的信息，所有可以连接电线的方块都应该实现这个接口
 * @author EmptyDremas
 * @version 1.0
 */
public interface IEleInfo {

	/**
	 * 判断方块是否可以连接电线，
	 * <b>
	 *     注意：此方法不保证fromPos/nowPos在此时已经在世界存在，所以提供了额外的参数，</b>
	 * 同时这个类不允许调用{@link EleSrcCable#canLink(TileEntity)}，
	 * 因为ET的canLink此方法可能依赖{@link EleSrcCable#canLink(TileEntity)}
	 *
	 * @param info 附加信息
	 * @param nowIsExist 当前方块是否存在
	 * @param fromIsExist 调用方块是否存在
	 */
	boolean canLink(LinkInfo info, boolean nowIsExist, boolean fromIsExist);
	
}
