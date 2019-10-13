/*
* @Title: Tools.java
* @Package minedreams.mi.tools
* @author EmptyDreams
* @date 2019年7月28日 下午4:24:41
* @version V1.0
*/
package com.elementtimes.tutorial.common.wire;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

import static net.minecraft.util.EnumFacing.*;

/**
 * 这个类种包含一些常用的工具类方法
 * @author EmptyDremas
 */
public final class Tools {
	
	/**
	 * 判断other在now的哪个方向
	 */
	public static EnumFacing whatFacing(BlockPos now, BlockPos other) {
		BlockPos cache = now.west();
		int code = other.hashCode();
		if (cache.hashCode() == code && cache.equals(other))
			return WEST;
		cache = now.east();
		if (cache.hashCode() == code && cache.equals(other))
			return EAST;
		cache = now.south();
		if (cache.hashCode() == code && cache.equals(other))
			return SOUTH;
		cache = now.north();
		if (cache.hashCode() == code && cache.equals(other))
			return NORTH;
		cache = now.down();
		if (cache.hashCode() == code && cache.equals(other))
			return DOWN;
		return UP;
	}
	
	/** 去除数组中的null元素 */
	public static BlockPos[] removeNull(BlockPos[] array) {
		if (array == null) return null;
		int i = 0;
		for (Object t : array) {
			if (t != null) ++i;
		}
		if (i == 0) return null;
		BlockPos[] ts = new BlockPos[i];
		i = 0;
		for (BlockPos t : array) {
			if (t != null) {
				ts[i] = t;
				++i;
			}
		}
		return ts;
	}
	
	/** 在数组中查找某个元素 */
	public static<T> int findValue(T[] array, T t) {
		if (array == null) return -1;
		if (t == null) {
			for (int i = 0; i < array.length; ++i) {
				if (array[i] == null) return i;
			}
		}
		int hash = t.hashCode();
		for (int i = 0; i < array.length; ++i) {
			if (array[i] == null) continue;
			if (array[i].hashCode() == hash && array[i].equals(t))
				return i;
		}
		return -1;
	}
	
	/** 检查数组中是否包含某个元素 */
	public static<T> boolean hasValue(T[] array, T t) {
		if (array == null) return false;
		for (T a : array) {
			if (a == null) {
				if (t == null) return true;
				continue;
			}
			if (a.equals(t)) return true;
		}
		return false;
	}
	
	/** 从state数组获取block数组 */
	public static Block[] toBlocks(IBlockState[] states) {
		Block[] blocks = new Block[states.length];
		for (int i = 0; i < states.length; ++i) {
			blocks[i] = states[i].getBlock();
		}
		return blocks;
	}
	
	/** 获取反向的EnumFacing */
	public static EnumFacing upsideDown(EnumFacing facing) {
		switch (facing) {
			case UP : return DOWN;
			case DOWN : return UP;
			case EAST : return WEST;
			case WEST : return EAST;
			case NORTH : return SOUTH;
			default : return NORTH;
		}
	}
	
	/**
	 * 获取方块周围的BlockPos
	 * @param pos 当前方块的坐标
	 */
	public static BlockPos[] getBlockPosList(BlockPos pos) {
		BlockPos[] poss = new BlockPos[6];
		poss[0] = pos.up();
		poss[1] = pos.down();
		poss[2] = pos.east();
		poss[3] = pos.west();
		poss[5] = pos.north();
		poss[4] = pos.south();
		return poss;
	}
	
}
