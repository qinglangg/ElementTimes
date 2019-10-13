/*
* @Title: ElectricityUser.java
* @Package minedreams.mi.api.electricity
* @author EmptyDreams
* @date 2019年8月22日 下午4:36:29
* @version V1.0
*/
package com.elementtimes.tutorial.common.wire;

import com.elementtimes.elementcore.api.annotation.ModBlock;

import java.util.Objects;

/**
 * 所有电力设备的父级TE，其中包含了最为基础的方法和循环接口
 * @author EmptyDremas
 */
@ModBlock.TileEntity(name = "IN_FATHER_ELECTRICITY_USER",
		clazz = "com.elementtimes.tutorial.common.wire.ElectricityUser")
public abstract class ElectricityUser extends Electricity {
	
	@Override
	public ElectricityEnergy getEnergy() {
		return EE;
	}
	
	/** 获取最小电压 */
	public final int getMinVoltage() {
		return minVoltage;
	}
	
	/** 获取最大电压 */
	public final int getMaxVoltage() {
		return maxVoltage;
	}
	
	/** 获取最长过载时间 */
	public final int getBiggerVoltageTime() {
		return biggerVoltageTime;
	}
	
	/** 获取过载超时后的操作 */
	public final BiggerVoltage getBiggerVoltageOperate() {
		return biggerVoltageOperate;
	}
	
	/** 用电器正常运行能量 */
	private final ElectricityEnergy EE = ElectricityEnergy.craet(100, 100, 0);
	/** 可以承受的最大电压 */
	private int maxVoltage = 120;
	/** 运行最低电压 */
	private int minVoltage = 80;
	/** 过载最长时间 */
	private int biggerVoltageTime = 0;
	/** 过载超时后的操作 */
	private BiggerVoltage biggerVoltageOperate = new BiggerVoltage(3);
	
	/** 设置过载超时后的操作 */
	protected final void setBiggerVoltageOperate(BiggerVoltage bv) {
		biggerVoltageOperate = bv;
	}
	
	/** 设置过载最长时间，当设置时间小于0时保持原设置不变 */
	protected final void setBiggerVoltageTime(int bvt) {
		biggerVoltageTime = (bvt >= 0) ? bvt : biggerVoltageTime;
	}
	
	/** 设置最大承受电压 */
	protected final void setMaxVolte(int max) {
		maxVoltage = (max >= EE.getVoltage()) ? max : maxVoltage;
	}
	
	/** 设置所需电能（单位：me，默认值100me），如果能量小于0，则保持原设置不变 */
	protected final void setEnergy(int me) {
		if (me >= 0) {
			EE.setEnergy(me);
		}
	}
	
	/**
	 * 设置所需电压（单位：V，默认值：220V），如果电压小于等于0，则保持原设置不变
	 */
	protected final void setVoltage(int v) {
		if (v >= 0) {
			EE.setVoltage(v);
		}
	}
	
	/**
	 * 深度比较两对象是否相等，当两对象所在世界都为null时比较结果可能不准确
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj instanceof ElectricityUser) {
			return equals((ElectricityUser) obj);
		}
		return false;
	}
	
	/**
	 * 深度比较两对象是否相等，当两对象所在世界都为null时比较结果可能不准确
	 */
	public boolean equals(ElectricityUser user) {
		if (world == null) {
			if (user.world != null) {
				return false;
			}
			return (biggerVoltageOperate == user.biggerVoltageOperate) &&
					(maxVoltage == user.maxVoltage) &&
					(biggerVoltageTime == user.biggerVoltageTime) &&
					EE.equals(user.getEnergy()) && pos.equals(user.pos);
		}
		return world.equals(user.world) &&
						(biggerVoltageOperate == user.biggerVoltageOperate) &&
						(maxVoltage == user.maxVoltage) &&
						(biggerVoltageTime == user.biggerVoltageTime) &&
						EE.equals(user.getEnergy()) && pos.equals(user.pos);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(pos, EE);
	}
	
}
