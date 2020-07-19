package com.elementtimes.elementtimes.common.eletricity;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;

/**
 * 所有于电有关的设备的父级TE
 *
 * @author EmptyDremas
 * @version V2.0
 */
public abstract class Electricity extends TileEntity {
	
	/** 没有信息 */
	public static final Object NO_HAVE_INFO = new Object();

	public Electricity(TileEntityType<?> tileEntityTypeIn) {
		super(tileEntityTypeIn);
	}

	// TODO clean old codes: 无此方法
//	@Override
//	public boolean shouldRefresh(World world, BlockPos pos, BlockState oldState, BlockState newSate) {
//		return oldState.getBlock() != newSate.getBlock();
//	}
	
	/** 过载最长时间 */
	protected int biggerMaxTime = 50;
	
	/** 设置过载最长时间(单位：tick，默认值：50tick)，当设置时间小于0时保持原设置不变 */
	protected final Electricity setBiggerMaxTime(int bvt) {
		biggerMaxTime = (bvt >= 0) ? bvt : biggerMaxTime;
		return this;
	}
	/** 获取最长过载时间 */
	public final int getBiggerMaxTime() {
		return biggerMaxTime;
	}
	
	/** 设置方块类型 */
	// TODO clean old codes: 无此变量
	public final void setBlockType(Block block) {
//		blockType = block;
	}
	
	@Override
	public String toString() {
		return "Electricity{ pos=" + pos + '}';
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Electricity e = (Electricity) o;
		if (world == null) {
			return e.getWorld() == null;
		}
		return world == e.getWorld() && pos.equals(e.getPos());
	}
	
	@Override
	public int hashCode() {
		return pos.hashCode();
	}
}