package com.elementtimes.elementtimes.common.eletricity;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.apache.commons.lang3.ArrayUtils;

import java.util.function.BiConsumer;

import static net.minecraft.util.Direction.*;

/**
 * 这个类种包含一些常用的工具类方法
 * @author EmptyDremas
 * @version V1.0
 */
public final class BlockPosUtil {
	
	/**
	 * 遍历指定方块周围的所有TE，不包含TE的不会进行遍历
	 * @param world 所在世界
	 * @param pos 中心方块
	 * @param run 要运行的代码，其中TE只遍历到的TE，Direction指TE相对于中心方块的方向
	 */
	public static void forEachAroundTE(World world, BlockPos pos, BiConsumer<? super TileEntity, Direction> run) {
		for (Direction direction : Direction.values()) {
			TileEntity te = world.getTileEntity(pos.offset(direction));
			if (te != null) {
				run.accept(te, direction);
			}
		}
	}
//
//	/** 只限水平范围 */
//	public static final int HORIZONTAL = 0;
//	/** 只限垂直范围 */
//	public static final int VERTICAL = 1;
//	/** 不限范围 */
//	public static final int ALL = 2;
//
	/**
	 * TODO clean old codes: 没用过？
	 * 从中心方块附近随机查找指定数量的空气坐标
//	 *
//	 * @param world 所在世界
//	 * @param center 中心方块坐标
//	 * @param model 模式
//	 * @return 数组长度与amount一致，其中可能有空值
//	 *
//	 * @throws IllegalArgumentException 如果model不在范围之内
	 */
//	public static BlockPos randomPos(World world, BlockPos center, int model) {
//		final Random random = new Random();
//		//最大尝试次数
//		final int allSize;
//		Plane plane;
//
//		switch (model) {
//			case HORIZONTAL:
//				allSize = 20;
//				plane = Plane.HORIZONTAL;
//				break;
//			case VERTICAL:
//				allSize = 10;
//				plane = Plane.VERTICAL;
//				break;
//			case ALL:
//				allSize = 30;
//				plane = null;
//				break;
//			default: throw new IllegalArgumentException("model[" + model + "]不在范围之内");
//		}
//
//		BlockPos temp;
//		int t = 0;
//		do {
//			Direction direction = plane == null ? Direction.random(random) : plane.random(random);
//			temp = center.offset(direction, 1);
//			// TODO clean old codes: 条件是不是这个方法？应该加 ！ 吗？不知道原来 canCatchFire 实现，isFlammable 应该是可燃
//		} while (++t < allSize && !world.isAirBlock(temp) && !world.getBlockState(temp).isFlammable(world, temp, DOWN));
//		return temp;
//	}

	// TODO clean old codes: 使用 NBTUtils 的版本
//	/**
//	 * 读取坐标
//	 * @param compound 要读取的标签
//	 * @param name 名称
//	 * @return 坐标
//	 */
//	public static BlockPos readBlockPos(CompoundNBT compound, String name) {
//		return new BlockPos(compound.getInteger(name + "_x"),
//				compound.getInteger(name + "_y"), compound.getInteger(name + "_z"));
//	}
//
//	/**
//	 * 写入一个坐标到标签中
//	 * @param compound 要写入的标签
//	 * @param pos 坐标
//	 * @param name 名称
//	 */
//	public static void writeBlockPos(CompoundNBT compound, BlockPos pos, String name) {
//		compound.putInt(name + "_x", pos.getX());
//		compound.putInt(name + "_y", pos.getY());
//		compound.putInt(name + "_z", pos.getZ());
//	}
	
	/**
	 * TODO clean old codes
	 * 	我好像在 {@link com.elementtimes.elementcore.api.utils.BlockUtils#getPosFacing(BlockPos, BlockPos)} 有一个版本
	 * 判断other在now的哪个方向
	 */
	public static Direction whatFacing(BlockPos now, BlockPos other) {
		BlockPos cache = now.west();
		int code = other.hashCode();
		if (cache.hashCode() == code && cache.equals(other)) {
			return WEST;
		}
		cache = now.east();
		if (cache.hashCode() == code && cache.equals(other)) {
			return EAST;
		}
		cache = now.south();
		if (cache.hashCode() == code && cache.equals(other)) {
			return SOUTH;
		}
		cache = now.north();
		if (cache.hashCode() == code && cache.equals(other)) {
			return NORTH;
		}
		cache = now.down();
		if (cache.hashCode() == code && cache.equals(other)) {
			return DOWN;
		}
		return UP;
	}
	
	/** 去除数组中的null元素 */
	/**
	 * TODO clean old codes: use {@link ArrayUtils}
	 */
//	public static BlockPos[] removeNull(BlockPos[] array) {
//		if (array == null) {
//			return null;
//		}
//		int i = 0;
//		for (Object t : array) {
//			if (t != null) {
//				++i;
//			}
//		}
//		if (i == 0) {
//			return null;
//		}
//		BlockPos[] ts = new BlockPos[i];
//		i = 0;
//		for (BlockPos t : array) {
//			if (t != null) {
//				ts[i] = t;
//				++i;
//			}
//		}
//		return ts;
//	}
//
//	/** 在数组中查找某个元素 */
//	public static<T> int findValue(T[] array, T t) {
//		if (array == null) return -1;
//		if (t == null) {
//			for (int i = 0; i < array.length; ++i) {
//				if (array[i] == null) return i;
//			}
//		}
//		int hash = t.hashCode();
//		for (int i = 0; i < array.length; ++i) {
//			if (array[i] == null) continue;
//			if (array[i].hashCode() == hash && array[i].equals(t))
//				return i;
//		}
//		return -1;
//	}
//
//	/** 检查数组中是否包含某个元素 */
//	public static<T> boolean hasValue(T[] array, T t) {
//		if (array == null) return false;
//		for (T a : array) {
//			if (a == null) {
//				if (t == null) return true;
//				continue;
//			}
//			if (a.equals(t)) return true;
//		}
//		return false;
//	}
	/**
	 * TODO clean old codes: use {@link BlockPos#offset(Direction, int)}
	 */
//	public static BlockPos getBlockPos(BlockPos pos, Direction facing, int length) {
//		switch (facing) {
//			case DOWN: return pos.down(length);
//			case UP: return pos.up(length);
//			case NORTH: return pos.north(length);
//			case SOUTH: return pos.south(length);
//			case WEST: return pos.west(length);
//			default: return pos.east(length);
//		}
//	}
	/**
	 * TODO clean old codes: 没用过？
	 */
//	/** 从state数组获取block数组 */
//	public static Block[] toBlocks(BlockState[] states) {
//		Block[] blocks = new Block[states.length];
//		for (int i = 0; i < states.length; ++i) {
//			blocks[i] = states[i].getBlock();
//		}
//		return blocks;
//	}
	/**
	 * TODO clean old codes: use {@link Direction#getOpposite()}
	 */
//	/** 获取反向的Direction */
//	public static Direction upsideDown(Direction facing) {
//		switch (facing) {
//			case UP : return DOWN;
//			case DOWN : return UP;
//			case EAST : return WEST;
//			case WEST : return EAST;
//			case NORTH : return SOUTH;
//			default : return NORTH;
//		}
//	}
//
	/**
	 * TODO clean old codes: 没用过？
	 * 获取方块周围的BlockPos
	 * @param pos 当前方块的坐标
	 */
//	public static BlockPos[] getBlockPosList(BlockPos pos) {
//		BlockPos[] list = new BlockPos[6];
//		list[0] = pos.up();
//		list[1] = pos.down();
//		list[2] = pos.east();
//		list[3] = pos.west();
//		list[4] = pos.north();
//		list[5] = pos.south();
//		return list;
//	}
}
