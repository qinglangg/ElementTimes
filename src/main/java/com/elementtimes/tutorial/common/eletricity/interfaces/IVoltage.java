package com.elementtimes.tutorial.common.eletricity.interfaces;

/**
 * @author EmptyDreams
 * @version V1.0
 */
public interface IVoltage {
	
	int getVoltage();
	
	int getLossIndex();
	
	static IVoltage create(int voltage, int lossIndex) {
		return new IVoltage() {
			@Override
			public int getVoltage() {
				return voltage;
			}
			
			@Override
			public int getLossIndex() {
				return lossIndex;
			}
		};
	}
	
}
