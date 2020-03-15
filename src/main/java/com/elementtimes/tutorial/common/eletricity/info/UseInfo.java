package com.elementtimes.tutorial.common.eletricity.info;

import com.elementtimes.tutorial.common.eletricity.interfaces.IVoltage;

/**
 * 计算结果信息储存
 * @author EmptyDreams
 * @version V1.0
 */
public final class UseInfo {
	
	private IVoltage voltage;
	private int energy;
	
	public UseInfo() { }
	
	public UseInfo(int energy, IVoltage voltage) {
		this.voltage = voltage;
		this.energy = energy;
	}
	
	public IVoltage getVoltage() {
		return voltage;
	}
	
	public UseInfo setVoltage(IVoltage voltage) {
		this.voltage = voltage;
		return this;
	}
	
	public int getEnergy() {
		return energy;
	}
	
	public UseInfo setEnergy(int energy) {
		this.energy = energy;
		return this;
	}
}
