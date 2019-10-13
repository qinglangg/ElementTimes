/*
* @Title: ElectricityTranster.java
* @Package minedreams.mi.api.electricity
* @author EmptyDreams
* @date 2019年10月1日 下午10:05:30
* @version V1.0
*/
package com.elementtimes.tutorial.common.wire;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 电力传输设备的父级TE
 * @author EmptyDremas
 */
public abstract class ElectricityTranster extends Electricity {
	
	/**
	 * 电力传输工具类，其中提供了一些额外的方法与参数
	 */
	public final static class EETransfer {
	
		/** 最先调用的ET对象，可以为null */
		protected final ElectricityTranster ET;
		
		/** 当前方块的ET，用于计算损耗 */
		protected ElectricityTranster nowET;
		
		/** 电压 */
		public final double VOLTAGE;
		
		/** 已经损失的电能 */
		public double loss = 0;
		
		/** 用户请求的电能 */
		public int need = 0;
		
		/**
		 * @param et 调用的ET可为null
		 */
		public EETransfer(ElectricityTranster et) {
			ET = et;
			VOLTAGE = et.getEnergy().getVoltage();
		}
		
		/** 设置当前方块的ET */
		public EETransfer setET(ElectricityTranster et) {
			//这里是更新loss,need和nowET的地方并且返回this
			return this;
		}
		
	}
	
	//六个方向是否连接
	protected boolean up = false;
	protected boolean down = false;
	protected boolean east =false;
	protected boolean west = false;
	protected boolean south = false;
	protected boolean north = false;
	/** 连接的上一根电线 */
	protected ElectricityTranster next;
	/** 连接的下一根电线 */
	protected ElectricityTranster prev;
	/** 已经连接的方块 */
	protected final Map<EnumFacing, TileEntity> linkBlock = new HashMap<EnumFacing, TileEntity>(6);
	
	/** 电力损耗指数，指数越大损耗越多 */
	protected int loss = 0;
	
	/**
	 * 判断一个方块能否连接当前电线
	 * @param ele 要连接的方块
	 */
	abstract public boolean canLink(TileEntity ele);
	
	/**
	 * 获取下一根电线
	 * @param ele 调用该方法的运输设备
	 */
	abstract public ElectricityTranster next(ElectricityTranster ele);
	
	/**
	 * 尝试连接一个方块，这个方块可能是用电器也可能是传输设备或原版方块，
	 * 这个需要用户自行检测
	 * @param ele 调用方块
	 * @return 连接成功返回true，否则返回false
	 */
	abstract public boolean linkForce(TileEntity ele);
	
	/**
	 * 尝试连接一个方块，这个方块可能是用电器也可能是传输设备或原版方块，
	 * 这个需要用户自行检测
	 * @param pos 调用方块
	 * @return 连接成功返回true，否则返回false
	 */
	abstract public boolean linkForce(BlockPos pos);
	
	/** 根据state更新内部数据 */
	abstract public void update(IBlockState state);
	
	/**
	 * 因为在radFromNBT阶段世界没有加载完毕，调用world.getTileEntity会返回null，
	 * 所有建立一个临时列表在用户调用next方法的时候检查是否需要更新参数，
	 * 当cachePos==null的时候标志已经更新，不需要再次更新
	 */
	private BlockPos[] cachePos = new BlockPos[2];
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		up = compound.getBoolean("up");
		down = compound.getBoolean("down");
		east = compound.getBoolean("east");
		west = compound.getBoolean("west");
		south = compound.getBoolean("south");
		north = compound.getBoolean("north");
		
		int[] is;
		if (compound.getBoolean("hasNext")) {
			is = compound.getIntArray("next");
			cachePos[0] = new BlockPos(is[0], is[1], is[2]);
		}
		if (compound.getBoolean("hasPrev")) {
			is = compound.getIntArray("prev");
			cachePos[1] = new BlockPos(is[0], is[1], is[2]);
		}
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setBoolean("up", up);
		compound.setBoolean("down", down);
		compound.setBoolean("east", east);
		compound.setBoolean("west", west);
		compound.setBoolean("south", south);
		compound.setBoolean("north", north);
		
		compound.setBoolean("hasNext", next != null);
		compound.setBoolean("hasPrev", prev != null);
		if (next != null) {
			compound.setIntArray("next", new int[] { next.pos.getX(), next.pos.getY(), next.pos.getZ()});
		}
		if (prev != null) {
			compound.setIntArray("prev", new int[] { prev.pos.getX(), prev.pos.getY(), prev.pos.getZ()});
		}
		return compound;
	}
	
	/**
	 * 这其中写有更新内部数据的代码，重写时应该调用
	 * @param simple 可用于网络通讯的Simple
	 * @param isClient 是否在客户端
	 */
	@Override
	public void send(SimpleNetworkWrapper simple, boolean isClient) {
		if (isClient) {
			return;
		}
		if (cachePos != null) {
			if (cachePos[0] != null) {
				next = (ElectricityTranster) world.getTileEntity(cachePos[0]);
			}
			if (cachePos[1] != null) {
				prev = (ElectricityTranster) world.getTileEntity(cachePos[1]);
			}
			cachePos = null;
		}
	}
	
	public EETransfer transTo(Electricity from, EETransfer ee) {
		//这里是电力传输的接口
		//按需求更改参数及返回值
		return ee;
	}
	
	/** 取消所有连接 */
	public final void deleteAllLink() { next = prev = null; }
	/** 取消某一个连接 */
	public final void deleteLink(TileEntity e) {
		if (e == null) {
			return;
		}
		if (e instanceof ElectricityTranster) {
			if (e == next) {
				next = null;
			} else if (e == prev) {
				prev = null;
			} else {
				throw new IndexOutOfBoundsException("未知的连接：" + e);
			}
		} else {
			if (linkBlock.containsValue(e)) {
				EnumFacing key = null;
				for (Map.Entry<EnumFacing, TileEntity> m : linkBlock.entrySet()) {
					if (m.getValue().equals(e)) {
						key = m.getKey();
						break;
					}
				}
				if (key == null) {
					throw new IndexOutOfBoundsException("未知的连接：" + e);
				} else {
					linkBlock.remove(key);
				}
			}
		}
	}
	/** 获取连接的所有的电线 */
	public final BlockPos[] getLinks() { return new BlockPos[]{ ((next == null) ? null :
			                                                       next.pos), ((prev == null) ? null : prev.pos) }; }
	/** 获取上一根电线 */
	public final BlockPos getPrev() { return (prev == null) ? null : prev.pos; }
	/** 获取下一根电线 */
	public final BlockPos getNext() { return (next == null) ? null : next.pos; }
	/** 获取上方是否连接方块 */
	public final boolean getUp() { return up; }
	/** 获取下方是否连接方块 */
	public final boolean getDown() { return down; }
	/** 获取东方是否连接方块 */
	public final boolean getEast() { return east; }
	/** 获取西方是否连接方块 */
	public final boolean getWest() { return west; }
	/** 获取南方是否连接方块 */
	public final boolean getSouth() { return south; }
	/** 获取北方是否连接方块 */
	public final boolean getNorth() { return north; }
	/** 获取电力损耗指数 */
	public final int getLoss() { return loss; }
	/** 设置上方是否连接方块 */
	public final void setUp(boolean value) { up = value; }
	/** 设置下方是否连接方块 */
	public final void setDown(boolean value) { down = value; }
	/** 设置东方是否连接方块 */
	public final void setEast(boolean value) { east = value; }
	/** 设置西方是否连接方块 */
	public final void setWest(boolean value) { west = value; }
	/** 设置北方是否连接方块 */
	public final void setNorth(boolean value) { north = value; }
	/** 设置南方是否连接方块 */
	public final void setSouth(boolean value) { south = value; }
	/** 设置电力损耗指数 */
	public final void setLoss(int loss) { this.loss = loss; }
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (obj instanceof ElectricityTranster) {
			return equals((ElectricityTranster) obj);
		}
		return false;
	}
	
	/**
	 * 当两个对象的world都为空时判断可能不准确
	 */
	public boolean equals(ElectricityTranster et) {
		if (et == null) {
			return false;
		}
		if (et == this) {
			return true;
		}
		if (next == null) {
			if (et.next == null) {
				if (prev == null) {
					if (et.prev == null) {
						if (world == null) {
							if (et.world == null) {
								return pos.equals(et.pos);
							}
							return false;
						}
					}
					return false;
				}
			}
			return false;
		} else if (et.next == null) {
			return false;
		} else if (prev == null) {
			if (et.prev == null) {
				return pos.equals(et.pos);
			}
			return false;
		} else if (et.prev == null) {
			return false;
		} else if (world == null) {
			if (et.world == null) {
				return next.pos.equals(et.next.pos) && prev.pos.equals(et.prev.pos) && pos.equals(et.pos);
			}
			return false;
		} else if (et.world == null) {
			return false;
		} else {
			return world.equals(et.world) && next.pos.equals(et.next.pos) &&
					       prev.pos.equals(et.prev.pos) && pos.equals(et.pos);
		}
	}
	
	@Override
	public int hashCode() {
		if (next == null) {
			if (prev == null) {
				return Objects.hash(pos);
			}
			return Objects.hash(pos, prev.pos);
		} else {
			if (prev == null) {
				return Objects.hash(pos, next.pos);
			}
			return Objects.hash(pos, next.pos, prev.pos);
		}
	}
	
	@Override
	public final boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return false;
	}
	
	@Override
	public final <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		return null;
	}
	
}
