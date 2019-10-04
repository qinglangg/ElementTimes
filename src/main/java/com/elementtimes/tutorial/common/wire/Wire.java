/*
* @Title: Wire.java
* @Package minedreams.mi.blocks.tools.wire
* @author EmptyDreams
* @date 2019年7月8日 下午9:16:36
* @version V1.0
*/
package com.elementtimes.tutorial.common.wire;

import static com.elementtimes.tutorial.ElementTimes.MODID;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.ArrayUtils;

import com.elementtimes.tutorial.common.wire.simpleImpl.SimpleImplWire;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * @author EmptyDremas
 *
 */
public final class Wire extends Item {

	static {
		GameRegistry.registerTileEntity(NBT.class, MODID +  ":wire");
	}
	
	public static final AxisAlignedBB B_POINT = new AxisAlignedBB(0.375F, 0.375F, 0.375F, 0.625F, 0.625F, 0.625F);
	public static final AxisAlignedBB B_EAST = new AxisAlignedBB(0.625F, 0.375F, 0.375F, 1, 0.625F, 0.625F);
	public static final AxisAlignedBB B_WEST = new AxisAlignedBB(0, 0.375F, 0.375F, 0.375F, 0.625F, 0.625F);
	public static final AxisAlignedBB B_SOUTH = new AxisAlignedBB(0.375F, 0.375F, 0.625F, 0.625F, 0.625F, 1);
	public static final AxisAlignedBB B_NORTH = new AxisAlignedBB(0.375F, 0.375F, 0, 0.625F, 0.625F, 0.375F);
	
	/** 模型标记 */
	public static final PropertyBool SOUTH = PropertyBool.create("south");
	public static final PropertyBool NORTH = PropertyBool.create("north");
	public static final PropertyBool WEST = PropertyBool.create("west");
	public static final PropertyBool EAST = PropertyBool.create("east");
	public static final PropertyBool UP = PropertyBool.create("up");
	public static final PropertyBool DOWN = PropertyBool.create("down");
	
	public static PropertyBool getProperty(EnumFacing facing) {
		switch (facing) {
			case DOWN : return DOWN;
			case UP : return UP;
			case EAST : return EAST;
			case WEST : return WEST;
			case SOUTH : return SOUTH;
			default : return NORTH;
		}
	}
	
	/** 方块 */
	private final Block block;
	
	public Wire(Block block, String name) {
		this.block = block;
		setRegistryName(MODID, name);
		setUnlocalizedName(name);
		setCreativeTab(block.getCreativeTabToDisplayOn());
	}
	
	/**
	 * @param player 玩家对象
	 * @param worldIn 所在世界
	 * @param pos 右键方块所在坐标
	 * @param hand 左右手
	 * @param facing 右键方块的哪个方向
	 */
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand,
			EnumFacing facing, float hitX, float hitY, float hitZ) {
		IBlockState iblockstate = worldIn.getBlockState(pos);
        Block block = iblockstate.getBlock();
        BlockPos blockPos;
        if (block.isReplaceable(worldIn, pos)) {
            blockPos = pos;
            pos = null;
        } else {
        	blockPos = pos.offset(facing);
        	if (!block.isReplaceable(worldIn, blockPos)) {
        		return EnumActionResult.FAIL;
        	}
        }
        ItemStack itemstack = player.getHeldItem(hand);
        if (!itemstack.isEmpty() && player.canPlayerEdit(blockPos, facing, itemstack)) {
           Object[] os = whatState(worldIn, this.block, blockPos, new BlockPos[] { pos });
            IBlockState iblockstate1 = placeBlockAt(itemstack, player, worldIn, blockPos, (IBlockState) os[0]);
            if (iblockstate1 != null) {
            	//更新TileEntity
            	NBT nbt = (NBT) worldIn.getTileEntity(blockPos);
	            nbt.update(iblockstate1);
            	List<BlockPos> linked = (List<BlockPos>) os[2];
            	linked.forEach(bp -> {
            		NBT te = (NBT) worldIn.getTileEntity(bp);
            		nbt.link(te);
            	});
            	nbt.markDirty();
            	
                SoundType soundtype = this.block.getSoundType(iblockstate1, worldIn, blockPos, player);
                worldIn.playSound(player, blockPos, soundtype.getPlaceSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
                itemstack.shrink(1);
                
                linked = (List<BlockPos>) os[1];
	            for (BlockPos bp : linked) {
		            updateBlock(worldIn, bp, null);
	            }
            }
            
            return EnumActionResult.SUCCESS;
        } else {
            return EnumActionResult.FAIL;
        }
	}
	
	private IBlockState placeBlockAt(ItemStack stack, EntityPlayer player, World world, BlockPos pos, IBlockState newState) {
        if (!world.setBlockState(pos, newState, 11)) return null;

        IBlockState state = world.getBlockState(pos);
        if (state.getBlock() == this.block) {
            ItemBlock.setTileEntityNBT(world, player, pos, stack);
            this.block.onBlockPlacedBy(world, pos, state, player, stack);
            if (player instanceof EntityPlayerMP)
                CriteriaTriggers.PLACED_BLOCK.trigger((EntityPlayerMP)player, pos, stack);
        }

        return state;
    }
	
	/**
	 * 更新电线显示，该方法其中主要功能通过调用{@link #whatState(World, IBlockState, BlockPos, BlockPos[], boolean, BlockPos...)}
	 * 实现，同时兼并电线TileEntity的更新功能，方法内部自动调用world.setBlockState与world.markBlockRangeForRenderUpdate。
	 * 注意：<b>该方法更新电线显示不会触发TileEntity的数据同步，如需同步数据还需自行实现</b>
	 * @param world 当前世界
	 * @param pos 需要更新的电线的坐标
	 * @param donnotLink 不要连接的方块坐标
	 */
	public static void updateBlock(World world, BlockPos pos, BlockPos[] donnotLink) {
		NBT nbt = (NBT) world.getTileEntity(pos);
		Block block = nbt.getBlockType();
		//以前将deleteAllLink()放置在了whatState前方导致更新错误现在已经解决
		Object[] os = whatState(world, block.getDefaultState(), pos, donnotLink, true, nbt.getLinks());
		IBlockState state = (IBlockState) os[0];
		List<BlockPos> linked = (List<BlockPos>) os[2];
		nbt.deleteAllLink();
		//更新连接
		for (BlockPos bp : linked) {
			nbt.link((NBT) world.getTileEntity(bp));
		}
		world.setBlockState(pos, state);
		nbt.validate();
		world.setTileEntity(pos, nbt);
		world.markBlockRangeForRenderUpdate(pos, pos);
		nbt.update(state);
	}
	
	/**
	 * 该方法时{@link #whatState(World, IBlockState, BlockPos, BlockPos[], boolean, BlockPos...)}方法的转接，
	 * (whatState(world, block.getDefaultState(), pos, null, false, firstPos))，其中某些参数使用默认参数
	 * @param world 方块所在世界
	 * @param block 电线种类
	 * @param pos 当前方块所在坐标
	 * @param firstPos 首要连接的方块
	 */
	private static Object[] whatState(World world, Block block, BlockPos pos, BlockPos[] firstPos) {
		return whatState(world, block.getDefaultState(), pos, null, false, firstPos);
	}
	
	/**
	 * 判断应该使用哪一个IBlockState
	 * @param world 方块所在世界
	 * @param state 当前IBlockState，方法运行时不覆盖原有内容
	 * @param pos 当前方块所在坐标
	 * @param donnotLink 不要连接的方块
	 * @param isExisting 周围方块是否已经存在，该选项错误可能会导致渲染错误
	 * @param firstPos 首要连接的方块
	 * @return 0 -> IBlockState，1 -> 需要更新的方块(List《BlockPos》)， 2 -> 连接的电线坐标(List《BlockPos》)
	 */
	private static Object[] whatState(World world, IBlockState state, BlockPos pos, BlockPos[] donnotLink, boolean isExisting, BlockPos... firstPos) {
		//首先清除首要连接中的null项来简化后期运算
		firstPos = removeNull(firstPos);
		//清除首要连接中与禁止连接中的重复项
		firstPos = ArrayUtils.removeElements(firstPos, donnotLink);
		/* 获取方块附近方块的信息 */
		//附近方块的BlockPos，移除不连接的方块
		BlockPos[] allPos = ArrayUtils.removeElements(getAllPos(pos), donnotLink);
		//附近方块的IBlockState
		IBlockState[] states = new IBlockState[allPos.length];
		Block[] blocks = new Block[allPos.length];
		//附近方块能否连接
		boolean[] bool = new boolean[allPos.length];
		//存储需要更新显示的方块
		List<BlockPos> needUpdate = new ArrayList<>();
		//存储连接的方向（仅包括电线与电线的连接）
		List<BlockPos> linkWire = new ArrayList<>(2);
		
		/* 初始化附近方块信息 */
		//已经连接的电线数量
		int alreadyLinks = 0;
		for (int i = 0; i < allPos.length; ++i) {
			states[i] = world.getBlockState(allPos[i]);
			blocks[i] = states[i].getBlock();
			LinkInfo info = new LinkInfo(world, allPos[i], pos, blocks[i], state.getBlock());
			info.fromState = states[i];
			bool[i] = ElcUtils.canLink(info, false, isExisting);
		}
		
		/* 当首要连接不为空时优先遍历首要连接 */
		if (firstPos != null) {
			for (BlockPos bp : firstPos) {
				//获取对应下标
				int index = findValue(allPos, bp);
				//如果不存在则报错
				if (index == -1) {
					throw new IllegalArgumentException("Wire[whatState]:firstPos中的元素不在数列中");
				}
				//判断是否可以连接
				if (!bool[index]) continue;
				//如果连接电线数量超出极限则不再连接并添加附加的标志
				if (bool[index] && blocks[index] instanceof WireBlock) {
					if (alreadyLinks >= 2) {
						continue;
					}
					linkWire.add(bp);
					++alreadyLinks;
				}
				//更新state
				state = state.withProperty(getProperty(whatFacing(pos, bp)), true);
				//添加更新标识
				needUpdate.add(bp);
			}
		}
		
		BlockPos bp;
		for (int i = 0; i < allPos.length; ++i) {
			bp = allPos[i];
			if (hasValue(firstPos, bp)) continue;
			if (!bool[i]) continue;
			//如果连接电线数量超出极限则不再连接
			if (bool[i] && blocks[i] instanceof WireBlock) {
				if (alreadyLinks >= 2) {
					continue;
				}
				linkWire.add(bp);
				++alreadyLinks;
			}
			state = state.withProperty(getProperty(whatFacing(pos, bp)), true);
			needUpdate.add(bp);
		}
		
		return new Object[] { state, needUpdate, linkWire };
	}
	
	/** 判断other在now的哪个方位 */
	public static EnumFacing whatFacing(BlockPos now, BlockPos other) {
		BlockPos cache = now.west();
		int code = other.hashCode();
		if (cache.hashCode() == code && cache.equals(other))
			return EnumFacing.WEST;
		cache = now.east();
		if (cache.hashCode() == code && cache.equals(other))
			return EnumFacing.EAST;
		cache = now.south();
		if (cache.hashCode() == code && cache.equals(other))
			return EnumFacing.SOUTH;
		cache = now.north();
		if (cache.hashCode() == code && cache.equals(other))
			return EnumFacing.NORTH;
		cache = now.down();
		if (cache.hashCode() == code && cache.equals(other))
			return EnumFacing.DOWN;
		return EnumFacing.UP;
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
	
	/** 获取一个方块周围的BlockPos */
	public static BlockPos[] getAllPos(BlockPos pos) {
		BlockPos[] poss = new BlockPos[6];
		poss[0] = pos.up();
		poss[1] = pos.down();
		poss[2] = pos.east();
		poss[3] = pos.west();
		poss[4] = pos.south();
		poss[5] = pos.north();
		return poss;
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
	
	public final static class NBT extends ElectricityUser  {
		
		@Override
		public void send(SimpleNetworkWrapper simple, boolean isClient) {
			if (isClient) return;
			
			if (cachePos != null) {
				if (cachePos[0] != null) next = (NBT) world.getTileEntity(cachePos[0]);
				if (cachePos[1] != null) prev = (NBT) world.getTileEntity(cachePos[1]);
				cachePos = null;
			}
			
			//新建消息
			SimpleImplWire siw = new SimpleImplWire();
			siw.ini(this);
			//遍历所有玩家
			for (EntityPlayer player : world.playerEntities) {
				//如果玩家已经更新过则跳过
				if (!EntitySelectors.NOT_SPECTATING.apply(player) || (players.contains(player.getName())))
					continue;
				
				//判断玩家是否在范围之内（判断方法借用World中的代码）
				double d = player.getDistance(pos.getX(), pos.getY(), pos.getZ());
				if (d < 4096) {
					if (player instanceof EntityPlayerMP) {
						//发送消息
						simple.sendTo(siw, (EntityPlayerMP) player);
					}
				}
			}
		}
		
		@Override
		public boolean run() {
			return true;
		}
		
		@Override
		public boolean useElectricity() {
			return false;
		}
		
		/** 连接的下一根电线 */
		private NBT next = null;
		/** 连接的上一根电线 */
		private NBT prev = null;
		
		/** 取消所有连接 */
		public void deleteAllLink() {
			next = prev = null;
		}
		
		/**
		 * 获取下一个电线
		 * @param from 调用电线
		 * @return 如果不存在下一根电线则返回null
		 */
		public NBT next(BlockPos from) {
			if (from.equals(next.pos)) return prev;
			if (from.equals(prev.pos)) return next;
			return null;
		}
		
		/**
		 * 检查电线是否可以连接
		 * @param from 调用方块，如果调用方块和已连接方块一致也返回true
		 */
		public boolean canLink(BlockPos from) {
			if (from == null) {
				return next == null || prev == null;
			}
			if (next != null) {
				if (prev == null) return true;
				return next.pos.equals(from) || prev.pos.equals(from);
			}
			return true;
		}
		
		/**
		 * 连接一个电线
		 * @param pos 需要连接的电线所在方块
		 * @return 是否连接成功
		 */
		public boolean link(BlockPos blockPos) {
			return link(blockPos == null ? null : (NBT) world.getTileEntity(blockPos));
		}
		
		/**
		 * 连接一个电线
		 * @param pos
		 * @return 是否连接成功
		 */
		public boolean link(NBT block) {
			if (next == null) {
				next = block;
				return true;
			}
			if (prev == null) {
				prev = block;
				return true;
			}
			return false;
		}
		
		boolean up = false;
		boolean down = false;
		boolean east =false;
		boolean west = false;
		boolean south = false;
		boolean north = false;
		
		/** 获取连接的所有的电线 */
		public BlockPos[] getLinks() { return new BlockPos[]{ ((next == null) ? null : next.pos), ((prev == null) ? null : prev.pos) }; }
		/** 获取上一根电线 */
		public BlockPos getPrev() { return (prev == null) ? null : prev.pos; }
		/** 获取下一根电线 */
		public BlockPos getNext() { return (next == null) ? null : next.pos; }
		/** 获取上方是否连接方块 */
		public boolean getUp() { return up; }
		/** 获取下方是否连接方块 */
		public boolean getDown() { return down; }
		/** 获取东方是否连接方块 */
		public boolean getEast() { return east; }
		/** 获取西方是否连接方块 */
		public boolean getWest() { return west; }
		/** 获取南方是否连接方块 */
		public boolean getSouth() { return south; }
		/** 获取北方是否连接方块 */
		public boolean getNorth() { return north; }
		/** 设置上方是否连接方块 */
		public void setUp(boolean value) { up = value; }
		/** 设置下方是否连接方块 */
		public void setDown(boolean value) { down = value; }
		/** 设置东方是否连接方块 */
		public void setEast(boolean value) { east = value; }
		/** 设置西方是否连接方块 */
		public void setWest(boolean value) { west = value; }
		/** 设置北方是否连接方块 */
		public void setNorth(boolean value) { north = value; }
		/** 设置南方是否连接方块 */
		public void setSouth(boolean value) { south = value; }
		
		/**
		 * 通过IBlockState更新内部数据
		 */
		public void update(IBlockState state) {
			up = state.getValue(UP);
	        down = state.getValue(DOWN);
	        east = state.getValue(EAST);
	        west = state.getValue(WEST);
	        south = state.getValue(SOUTH);
	        north = state.getValue(NORTH);
	        markDirty();
		}
		
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
		 * 存储已经更新过的玩家列表，因为作者认为单机时长会更多，所以选择1作为默认值。<br>
		 * 	不同方块不共用此列表且此列表不会离线存储，当玩家离开方块过远或退出游戏等操作导致
		 * 		方块暂时“删除”后此列表将重置以保证所有玩家可以正常渲染电线方块
		 */
		public final List<String> players = new ArrayList<>(1);
		
		@Override
		public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate) {
			return oldState.getBlock() != newSate.getBlock();
		}
		
		@Override
		public boolean equals(Object obj) {
			if (obj == this) return true;
			if (obj == null) return false;
			if (obj instanceof NBT) {
				NBT nbt = (NBT) obj;
				return (world == nbt.world) && (pos.equals(nbt.pos));
			}
			return false;
		}
		
		@Override
		public int hashCode() {
			return Objects.hash(world, pos);
		}
		
		@Override
		public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
			return false;
		}
		
		@Override
		public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
			return null;
		}
		
	}
	
}
