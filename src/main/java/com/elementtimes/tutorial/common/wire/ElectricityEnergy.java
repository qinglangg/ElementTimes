/**   
* @Title: ElectricityEnergy.java
* @Package minedreams.mi.api.electricity.info
* @author EmptyDreams
* @date 2019年10月2日 下午4:52:57
* @version V1.0
*/
package com.elementtimes.tutorial.common.wire;

import javax.annotation.Nonnull;
import java.util.Objects;

/**
 * 能量单位
 * @author EmptyDremas
 */
public final class ElectricityEnergy {

	/** 能量 */
	private double energy = 0;
	/**
	 * 电压<br>
	 * 未来版本可能开启电压损耗
	 */
	private double voltage = 0;
	/**
	 * 电力损耗指数，指数越大损耗越多，
	 * 一般情况下只有电线的损耗指数为非0，
	 */
	private double loss = 0;
	
	private ElectricityEnergy() { }
	
	public static ElectricityEnergy craet(int energy, int voltage, int loss) {
		ElectricityEnergy ee = new ElectricityEnergy();
		ee.energy = energy;
		ee.voltage = voltage;
		ee.loss = loss;
		return ee;
	}
	
	/** 获取可用的能量大小 */
	public double getUseEnergy() {
		return energy - getLossEnergy();
	}
	
	/** 计算损失的能量 */
	public double getLossEnergy() {
		return loss * (voltage / 3 * 2) * 0.1;
	}
	
	public void setLoss(int loss) {
		this.loss = loss;
	}
	
	public void setVoltage(int voltage) {
		this.voltage = voltage;
	}
	
	public void setEnergy(int energy) {
		this.energy = energy;
	}
	
	public double getLoss() {
		return loss;
	}
	
	public double getVoltage() {
		return voltage;
	}
	
	public double getEnergy() {
		return energy;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj == this) {
			return true;
		}
		if (obj instanceof ElectricityEnergy) {
			return equals((ElectricityEnergy) obj);
		}
		return false;
	}
	
	public boolean equals(@Nonnull ElectricityEnergy ee) {
		return energy == ee.energy && voltage == ee.voltage && loss == ee.loss;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(energy, voltage, loss);
	}
	
}
