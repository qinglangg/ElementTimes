package com.elementtimes.tutorial.common.eletricity.interfaces;

import com.elementtimes.tutorial.common.eletricity.info.EleEnergy;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;

/**
 * 可输出电能的方块的托管接口
 * @author EmptyDreams
 * @version V1.1
 */
public interface IEleOutputer extends IRegister {
	
	/**
	 * 取出指定数量的电能，不论是否可以完全提供都应做出反应
	 * @param te 对应方块的TE
	 * @param energy 需要的能量
	 * @param voltage 电压值
	 * @param simulation 是否为模拟计算，为true则不修改实际数据
	 * @return 返回实际输出的信息
	 * @throws NullPointerException if te == null || voltage == null
	 * @throws ClassCastException 如果不支持输入的TE
	 */
	EleEnergy output(TileEntity te, int energy, IVoltage voltage, boolean simulation);
	
	/**
	 * 用于当计算失误时回退能量操作
	 * @param te 当前TE
	 * @param energy 能量值
	 */
	void fallback(TileEntity te, int energy);
	
	/**
	 * 判断指定方块能否向指定方向输出电能
	 * @param te 当前方块
	 * @param facing 方向
	 * @throws ClassCastException 如果不支持输入的TE
	 */
	boolean isAllowable(TileEntity te, EnumFacing facing);
	
}
