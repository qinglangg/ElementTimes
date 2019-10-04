/**   
* @Title: Electricity.java
* @Package minedreams.mi.api.electricity
* @author EmptyDreams
* @date 2019年10月1日 下午4:40:17
* @version V1.0
*/
package com.elementtimes.tutorial.common.wire;

import com.elementtimes.elementcore.api.annotation.ModBlock;
import com.elementtimes.tutorial.common.wire.simpleImpl.NetworkLoader;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;

/**
 * @author EmptyDremas
 *
 */
@ModBlock.TileEntity(name = "IN_FATHER_ELECTRICITY", clazz = "com.elementtimes.tutorial.common.wire.Electricity")
public abstract class Electricity extends TileEntity implements ITickable {

	private static Object uniqueSign;
	private final Object SIGN = new Object();
	
	abstract public ElectricityEnergy getEnergy();
	
	@Override
	public final void update() {
		send(NetworkLoader.instance, world.isRemote);
		
		//这里写
	}
	
	/**
	 * 机器运行的接口，该接口不应该处理电器运行内容，
	 * 应当处理电器运行前的例行检查，应该使用{@link #useElectricity()}
	 * 方法来处理运行内容。<br>
	 * 该方法默认50ms运行一次，与MC时间线相同，玩家可以通过修改{@link SysInfo}
	 * 内的信息来更改运行间隔，间隔最小为10ms，最大为100ms，
	 * 如果需要实现更多的间隔需要玩家自己实现。
	 * 
	 * @return boolean 是否成功完成运行，如果返回false，系统将自动播放中断音效
	 */
	abstract boolean run();
	
	/**
	 * 使用电能，该方法在用户设置取电标志后调用。该方法用来处理电器运行时的操作，
	 * 例如：计算工作进度；更新数据等操作。
	 * <b><pre>
	 * 该方法在设置标志后在一下情况不一定被调用：
	 *    1.电力供给不足；
	 *    2.电器运行前因电力供给错误而损坏；
	 *    3.用户(或其他用户)手动跳过了该电器的运行
	 *    4.TE中存储的world对象为null
	 *    5.TE中其它与方块相关的信息错误</pre>
	 *
	 * @return boolean 电力是否被消耗，若返回false则表示电器没有消耗电力，将返回电力损耗
	 */
	abstract boolean useElectricity();
	
	/**
	 * 网络通信
	 * @param simple 可用于网络通讯的Simple
	 * @param isClient 是否在客户端
	 */
	public void send(SimpleNetworkWrapper simple, boolean isClient) {	
	}
	
}
