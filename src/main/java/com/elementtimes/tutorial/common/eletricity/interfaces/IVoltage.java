package com.elementtimes.tutorial.common.eletricity.interfaces;

import javax.annotation.Nonnull;

import com.elementtimes.tutorial.common.eletricity.src.info.EnumVoltage;


/**
 * 电压值.
 * 该类在读写后不保证依然是原对象，可能会替换为{@link EnumVoltage}或匿名内部类
 * @author EmptyDreams
 * @version V1.0
 */
public interface IVoltage {
	
	/** 获取该电压的整数形式 */
	int getVoltage();
	
	/** 获取该电压的电力损耗指数 */
	int getLossIndex();
	
	default int compareTo(IVoltage o) {
		int k = Integer.compare(getVoltage(), o.getVoltage());
		if (k == 0) {
			k = Integer.compare(o.getLossIndex(), getLossIndex());
		}
		return k;
	}
	
	/** 复制当前对象，若对象本身为不可变可直接返回当前对象 */
	@Nonnull
	IVoltage copy();
	
	static IVoltage getInstance(int voltage, int loss) {
		for (EnumVoltage value : EnumVoltage.values()) {
			if (value.getVoltage() == voltage && value.getLossIndex() == loss) {
				return value;
			}
		}
		
		return new IVoltage() {
			@Override
			public int getVoltage() {
				return voltage;
			}
			
			@Override
			public int getLossIndex() {
				return loss;
			}
			
			@Nonnull
			@Override
			public IVoltage copy() {
				return this;
			}
		};
	}
	
}
