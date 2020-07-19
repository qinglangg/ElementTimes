package com.elementtimes.elementtimes.common.autonet;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.nbt.CompoundNBT;

/**
 * 自动化的网络传输类，实现该接口的类应该同时实现{@link net.minecraft.tileentity.TileEntity}
 * @author EmptyDreams
 * @version V1.0
 */
public interface IAutoNetwork {
	
	/**
	 * <p>获取需要发送的信息</p>
	 * <p>若是服务器向玩家发送信息，需要设置以下信息：
	 * 1.int类型的"playerAmount"：标记玩家数量</p><p>
	 * 2.String类型的"player_"：其中"_"由数字代替，从0开始，到playerAmount - 1结束，存储玩家的名称</p></b>
	 * @return 若不需要传递信息返回null
	 */
	@Nullable
	CompoundNBT send();
	
	/**
	 * 处理接收的信息
	 */
	void receive(@Nonnull CompoundNBT compound);
	
}
