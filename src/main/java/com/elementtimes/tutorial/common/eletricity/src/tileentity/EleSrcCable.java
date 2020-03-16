package com.elementtimes.tutorial.common.eletricity.src.tileentity;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import com.elementtimes.elementcore.api.annotation.ModBlock;
import com.elementtimes.tutorial.common.eletricity.EleWorker;
import com.elementtimes.tutorial.common.eletricity.Electricity;
import com.elementtimes.tutorial.common.eletricity.interfaces.IEleTransfer;
import com.elementtimes.tutorial.common.eletricity.interfaces.IVoltage;
import com.elementtimes.tutorial.common.eletricity.src.cache.WireLinkInfo;
import com.elementtimes.tutorial.common.eletricity.src.info.IETForEach;
import com.elementtimes.tutorial.common.autonet.IAutoNetwork;
import com.elementtimes.tutorial.common.autonet.NetworkRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;

import static net.minecraft.util.EnumFacing.DOWN;
import static net.minecraft.util.EnumFacing.EAST;
import static net.minecraft.util.EnumFacing.NORTH;
import static net.minecraft.util.EnumFacing.SOUTH;
import static net.minecraft.util.EnumFacing.UP;
import static net.minecraft.util.EnumFacing.WEST;

/**
 * @author EmptyDreams
 * @version V1.0
 */
@SuppressWarnings("unused")
@ModBlock.TileEntity(name = "IN_FATHER_ELECTRICITY_TRANSFER", value = EleSrcCable.class)
public class EleSrcCable extends Electricity implements IAutoNetwork, ITickable {
	
	public EleSrcCable() { }
	public EleSrcCable(int meMax, int loss) {
		this.meMax = meMax;
		this.loss = loss;
	}
	
	//六个方向是否连接
	private boolean up = false;
	private boolean down = false;
	private boolean east =false;
	private boolean west = false;
	private boolean south = false;
	private boolean north = false;
	/** 电线连接的方块，不包括电线方块 */
	private List<TileEntity> linkedBlocks = new ArrayList<TileEntity>(5) {
		
		@Override
		public boolean contains(Object o) {
			if (o instanceof TileEntity) {
				for (int i = 0, size = size(); i < size; ++i) {
					if (get(i).getPos().equals(((TileEntity) o).getPos())) return true;
				}
			}
			return false;
		}
		
		@Override
		public boolean add(TileEntity tileEntity) {
			Objects.requireNonNull(tileEntity, "tileEntity == null");
			return super.add(tileEntity);
		}
		
		@Override
		public boolean remove(Object o) {
			if (o instanceof TileEntity) {
				for (int i = 0, size = size(); i < size; ++i) {
					if (get(i).getPos().equals(((TileEntity) o).getPos())) {
						remove(i);
						return true;
					}
				}
			} else if (o instanceof BlockPos) {
				for (int i = 0, size = size(); i < size; ++i) {
					if (get(i).getPos().equals(o)) {
						remove(i);
						return true;
					}
				}
			}
			return false;
		}
	};
	/** 上一根电线 */
	private TileEntity prev = null;
	private IEleTransfer prevShip = null;
	/** 下一根电线 */
	private TileEntity next = null;
	private IEleTransfer nextShip = null;
	/** 最大电流量 */
	protected int meMax = 5000;
	/** 当前电流量 */
	private int me = 0;
	/** 电力损耗指数，指数越大损耗越多 */
	protected int loss = 0;
	/** 所属电路缓存 */
	WireLinkInfo cache = null;
	/** 在客户端存储电线连接数量 */
	private int _amount = 0;
	
	/**
	 * 判断一个方块能否连接当前电线
	 * @param target 要连接的方块
	 */
	public boolean canLink(TileEntity target) {
		if (EleWorker.isTransfer(target)) {
			return prev == null || prev.equals(target) ||
					       next == null || next.equals(target);
		}
		return EleWorker.isOutputer(target) || EleWorker.isInputer(target);
	}
	
	/**
	 * 获取下一根电线
	 * @param from 调用该方法的运输设备，当{@link #getLinkAmount()} <= 1时可以为null
	 *
	 * @throws IllegalArgumentException 如果 ele == null 且 {@link #getLinkAmount()} > 1
	 */
	public TileEntity next(TileEntity from) {
		if (from == null) {
			if (next == null) {
				if (prev == null) return null;
				return prev;
			}
			if (prev == null) return next;
			throw new IllegalArgumentException("from == null，信息不足！");
		}
		if (from.equals(next)) return prev;
		if (from.equals(prev)) return next;
		return null;
	}
	
	/** 获取已经连接的电线的数量 */
	public int getLinkAmount() {
		if (world.isRemote) {
			return _amount;
		} else {
			if (prev == null) {
				if (next == null) return 0;
				return 1;
			}
			if (next == null) return 1;
			return 2;
		}
	}
	
	/**
	 * 连接一个方块. 这个方块可能是任意类型的方块，这个需要用户自行检测
	 * @param target 要连接的方块
	 * @return 连接成功返回true，否则返回false
	 */
	public boolean link(TileEntity target) {
		if (target == null || target == this || world.isRemote) return false;
		IEleTransfer et = EleWorker.getTransfer(target);
		if (et != null) {
			if (!et.canLink(target, this)) return false;
			if (next == null) {
				if (prev == null || !prev.equals(target)) {
					next = target;
					nextShip = et;
					cache.merge(nextShip.getLineCache(next));
					updateLinkShow();
					return true;
				}
			} else if (next.equals(target)) {
				return true;
			} else if (prev == null) {
				prev = target;
				prevShip = et;
				cache.merge(prevShip.getLineCache(prev));
				updateLinkShow();
				return true;
			} else return prev.equals(target);
			return false;
		}
		if (target.hasCapability(CapabilityEnergy.ENERGY, whatFacing(target.getPos(), pos))) {
			if (!linkedBlocks.contains(target)) linkedBlocks.add(target);
			updateLinkShow();
			return true;
		}
		return false;
	}
	
	/**
	 * 连接一个方块
	 * @param pos 要连接的方块
	 * @return 连接成功返回true，否则返回false
	 */
	public final boolean link(BlockPos pos) {
		return link(pos == null ? null : world.getTileEntity(pos));
	}
	
	public void updateLinkShow() {
		setEast(false);
		setWest(false);
		setNorth(false);
		setSouth(false);
		setUp(false);
		setDown(false);
		if (next != null) {
			switch (whatFacing(pos, next.getPos())) {
				case EAST: setEast(true); break;
				case WEST: setWest(true); break;
				case SOUTH: setSouth(true); break;
				case NORTH: setNorth(true); break;
				case UP: setUp(true); break;
				default: setDown(true);
			}
		}
		if (prev != null) {
			switch (whatFacing(pos, prev.getPos())) {
				case EAST: setEast(true); break;
				case WEST: setWest(true); break;
				case SOUTH: setSouth(true); break;
				case NORTH: setNorth(true); break;
				case UP: setUp(true); break;
				default: setDown(true);
			}
		}
		for (TileEntity block : linkedBlocks) {
			switch (whatFacing(pos, block.getPos())) {
				case EAST: setEast(true); break;
				case WEST: setWest(true); break;
				case SOUTH: setSouth(true); break;
				case NORTH: setNorth(true); break;
				case UP: setUp(true); break;
				default: setDown(true);
			}
		}
		markDirty();
		players.clear();
	}
	
	/**
	 * 删除指定连接，若pos不在连接列表中，则不会发生任何事情
	 * @param pos 要删除的连接坐标，为null时不会做任何事情
	 */
	public final void deleteLink(BlockPos pos) {
		if (pos == null) return;
		if (next != null && pos.equals(next.getPos())) {
			if (next instanceof EleSrcCable) {
				EleSrcCable next = (EleSrcCable) this.next;
				if (next.getLinkAmount() == 1) {
					cache.plusMakerAmount(-nextShip.getOutputerAround(next).size());
					next.setCache(null);
					this.next = null;
				} else {
					this.next = null;
					WireLinkInfo.calculateCache(this);
				}
			} else {
				if (nextShip.getLinkAmount(next) == 1) {
					cache.plusMakerAmount(-nextShip.getLineCache(next).getOutputerAmount());
					nextShip.setLineCache(next, nextShip.createLineCache(next));
					this.next = null;
				} else {
					cache.disperse(nextShip.getLineCache(next));
					this.next = null;
					WireLinkInfo.calculateCache(this);
				}
				
			}
		} else if (prev != null && pos.equals(prev.getPos())) {
			if (prev instanceof EleSrcCable) {
				EleSrcCable prev = (EleSrcCable) this.prev;
				if (prev.getLinkAmount() == 1) {
					cache.plusMakerAmount(-prevShip.getOutputerAround(prev).size());
					prev.setCache(null);
					this.prev = null;
				} else {
					this.prev = null;
					WireLinkInfo.calculateCache(this);
				}
			} else {
				if (prevShip.getLinkAmount(prev) == 1) {
					cache.plusMakerAmount(-prevShip.getLineCache(prev).getOutputerAmount());
					prevShip.setLineCache(next, prevShip.createLineCache(prev));
					prev = null;
				} else {
					cache.disperse(prevShip.getLineCache(prev));
					prev = null;
					WireLinkInfo.calculateCache(this);
				}
			}
		} else {
			//noinspection SuspiciousMethodCalls
			linkedBlocks.remove(pos);
		}
		updateLinkShow();
	}
	
	/**
	 * 遍历整条线路，以当前电线为起点
	 * @param run 要运行的指令
	 */
	public final void forEachAll(IETForEach run) {
		if (getLinkAmount() <= 1) {
			forEach(null, run);
		} else {
			forEach(next, run, true);
			forEach(prev, run, false);
		}
	}
	
	/**
	 * 向指定方向遍历线路
	 * @param prev 上一根电线
	 * @param run 要运行的内容
	 */
	public final void forEach(TileEntity prev, IETForEach run) {
		forEach(prev, run, true);
	}
	
	/**
	 * 向指定方向遍历线路
	 * @param prev 上一根电线
	 * @param run 要运行的内容
	 * @param isNow 是否遍历当前电线
	 */
	@SuppressWarnings("ConstantConditions")
	private void forEach(TileEntity prev, IETForEach run, boolean isNow) {
		TileEntity next = next(prev);
		prev = this;
		if (next instanceof EleSrcCable) {
			if (isNow && !run.run(this, false, null)) return;
		} else {
			if (isNow && !run.run(this, true, next)) return;
		}
		for (EleSrcCable et = (EleSrcCable) next; !(et == null || et == this); et = (EleSrcCable) next) {
			next = et.next(prev);
			prev = et;
			if (next instanceof EleSrcCable) {
				if (run.run(et, false, null)) continue;
			} else {
				run.run(et, true, next);
				break;
			}
			break;
		}
	}
	
	//--------------------常规重写--------------------//
	
	private static final WireLinkInfo CLIENT_CACHE = new WireLinkInfo();
	
	@Override
	public void update() {
		if (cache == null) {
			NetworkRegister.register(this);
			if (world.isRemote) cache = CLIENT_CACHE;
			else {
				WireLinkInfo.calculateCache(this);
				if (cachePos != null) {
					if (cachePos[0] != null) next = world.getTileEntity(cachePos[0]);
					if (cachePos[1] != null) prev = world.getTileEntity(cachePos[1]);
					if (cacheFacing != null) {
						for (EnumFacing facing : cacheFacing)
							linkedBlocks.add(world.getTileEntity(pos.offset(facing)));
					}
					cacheFacing = null;
					cachePos = null;
				}
			}
		}
		
		if (!world.isRemote) {
			for (TileEntity block : linkedBlocks) {
				block = world.getTileEntity(block.getPos());
				if (block == null) continue;
				if (block.getCapability(CapabilityEnergy.ENERGY,
						whatFacing(pos, block.getPos())).receiveEnergy(1, true) > 0) {
					EleWorker.useEleEnergy(block);
				}
			}
		}
	}
	
	/** 设置最大电流指数 */
	public final void setMeMax(int max) { meMax = max; }
	/** 获取最大电流指数 */
	public final int getMeMax() { return meMax; }
	/** 获取当前电流量 */
	public final int getTransfer() { return me; }
	/** 通过电流 */
	public final void transfer(int me) { this.me += me; }
	/** 电流归零 */
	public final void clearTransfer() { me = 0; }
	/** 设置线路缓存 */
	public final void setCache(WireLinkInfo info) { this.cache = info; }
	/** 获取线路缓存 */
	public final WireLinkInfo getCache() { return cache; }
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
	/** 设置上方是否连接方块 */
	public final void setUp(boolean value) {
		up = value;
	}
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
	/** 获取损耗值 */
	public final int getLoss(IVoltage voltage) {
		return voltage.getLossIndex() * loss / 2;
	}
	/** 设置电力损耗指数 */
	public final void setLoss(int loss) { this.loss = loss; }
	/** 获取上一根电线 */
	public final TileEntity getPrev() { return prev; }
	/** 获取下一根电线 */
	public final TileEntity getNext() { return next; }
	/** 获取连接的方块. 返回的列表可以随意修改 */
	public final List<TileEntity> getLinkedBlocks() { return new ArrayList<>(linkedBlocks); }
	
	/*
	 * 因为在radFromNBT阶段世界没有加载完毕，调用world.getTileEntity会返回null，
	 * 所有建立一个临时列表在用户调用next方法的时候检查是否需要更新参数，
	 * 当cachePos==null的时候标志已经更新，不需要再次更新
	 */
	private BlockPos[] cachePos = new BlockPos[2];
	private EnumFacing[] cacheFacing;
	
	@Override
	public void receive(@Nonnull NBTTagCompound message) {
		up = message.getBoolean("up");
		down = message.getBoolean("down");
		east = message.getBoolean("east");
		west = message.getBoolean("west");
		south = message.getBoolean("south");
		north = message.getBoolean("north");
		_amount = message.getInteger("amount");
		world.markBlockRangeForRenderUpdate(pos, pos);
	}
	
	/**
	 * 存储已经更新过的玩家列表，因为作者认为单机时长会更多，所以选择1作为默认值。<br>
	 * 	不同方块不共用此列表且此列表不会离线存储，当玩家离开方块过远或退出游戏等操作导致
	 * 		方块暂时“删除”后此列表将重置以保证所有玩家可以正常渲染电线方块
	 */
	private final List<String> players = new ArrayList<>(1);
	
	/**
	 * 这其中写有更新内部数据的代码，重写时应该调用
	 *
	 * @return null
	 */
	@Override
	public NBTTagCompound send() {
		if (world.isRemote) return null;
		
		if (players.size() == world.playerEntities.size()) return null;
		Set<String> sendPlayers = new HashSet<>();
		NBTTagCompound compound = new NBTTagCompound();
		compound.setBoolean("up", up);
		compound.setBoolean("down", down);
		compound.setBoolean("south", south);
		compound.setBoolean("north", north);
		compound.setBoolean("west", west);
		compound.setBoolean("east", east);
		
		//遍历所有玩家
		for (EntityPlayer player : world.playerEntities) {
			//如果玩家已经更新过则跳过
			if (players.contains(player.getName())) continue;
			
			//判断玩家是否在范围之内（判断方法借用World中的代码）
			double d = player.getDistance(pos.getX(), pos.getY(), pos.getZ());
			if (d < 4096) {
				if (player instanceof EntityPlayerMP) {
					players.add(player.getName());
					sendPlayers.add(player.getName());
				}
			}
		}
		
		compound.setInteger("playerAmount", sendPlayers.size());
		int i = 0;
		for (String player : sendPlayers) {
			compound.setString("player" + i, player);
			++i;
		}
		
		return compound;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		up = compound.getBoolean("up");
		down = compound.getBoolean("down");
		east = compound.getBoolean("east");
		west = compound.getBoolean("west");
		south = compound.getBoolean("south");
		north = compound.getBoolean("north");
		
		if (compound.getBoolean("hasNext")) {
			cachePos[0] = readBlockPos(compound, "next");
		}
		if (compound.getBoolean("hasPrev")) {
			cachePos[1] = readBlockPos(compound, "prev");
		}
		
		int size = compound.getInteger("maker_size");
		cacheFacing = new EnumFacing[size];
		for (int i = 0; i < size; ++i) {
			cacheFacing[i] = EnumFacing.getFront(compound.getInteger("facing_" + i));
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
			writeBlockPos(compound, next.getPos(), "next");
		}
		if (prev != null) {
			writeBlockPos(compound, prev.getPos(), "prev");
		}
		
		int size = 0;
		for (TileEntity te : linkedBlocks) {
			compound.setInteger("facing_" + size++, whatFacing(pos, te.getPos()).getIndex());
		}
		compound.setInteger("maker_size", size);
		
		return compound;
	}
	
	@Override
	public final boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return false;
	}
	@Override
	public final <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		return null;
	}
	
	/**
	 * 读取坐标
	 * @param compound 要读取的标签
	 * @param name 名称
	 * @return 坐标
	 */
	public static BlockPos readBlockPos(NBTTagCompound compound, String name) {
		return new BlockPos(compound.getInteger(name + "_x"),
				compound.getInteger(name + "_y"), compound.getInteger(name + "_z"));
	}
	
	/**
	 * 写入一个坐标到标签中
	 * @param compound 要写入的标签
	 * @param pos 坐标
	 * @param name 名称
	 */
	public static void writeBlockPos(NBTTagCompound compound, BlockPos pos, String name) {
		compound.setInteger(name + "_x", pos.getX());
		compound.setInteger(name + "_y", pos.getY());
		compound.setInteger(name + "_z", pos.getZ());
	}
	
	public static EnumFacing whatFacing(BlockPos now, BlockPos pos) {
		BlockPos cache = now.west();
		int code = pos.hashCode();
		if (cache.hashCode() == code && cache.equals(pos))
			return WEST;
		cache = now.east();
		if (cache.hashCode() == code && cache.equals(pos))
			return EAST;
		cache = now.south();
		if (cache.hashCode() == code && cache.equals(pos))
			return SOUTH;
		cache = now.north();
		if (cache.hashCode() == code && cache.equals(pos))
			return NORTH;
		cache = now.down();
		if (cache.hashCode() == code && cache.equals(pos))
			return DOWN;
		return UP;
	}
	
}
