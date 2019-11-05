/*
* @Title: WireBlock.java
* @Package minedreams.mi.blocks.tools.wire
* @author EmptyDreams
* @date 2019年7月29日 下午4:52:17
* @version V1.0
*/
package com.elementtimes.tutorial.common.wire;

import com.elementtimes.tutorial.common.init.ElementtimesTabs;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 普通电线
 * @author EmptyDremas
 */
public final class WireBlock extends Block implements IEleInfo, ITileEntityProvider {
	
	public WireBlock(String name) {
		super(Material.CIRCUITS);
		setSoundType(SoundType.SNOW);
		setHardness(0.35F);
		setCreativeTab(ElementtimesTabs.Industry);
		setRegistryName("elementtimes", name);
		setUnlocalizedName(name);
		setDefaultState(getDefaultState().withProperty(Wire.SOUTH, false)
				.withProperty(Wire.NORTH, false).withProperty(Wire.WEST, false).withProperty(Wire.EAST, false)
				.withProperty(Wire.DOWN, false).withProperty(Wire.UP, false));
	}
	
	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		TileEntityWire nbt = (TileEntityWire) worldIn.getTileEntity(pos);
		state = state.withProperty(Wire.UP, nbt.getUp()).withProperty(Wire.DOWN, nbt.getDown())
							.withProperty(Wire.EAST, nbt.getEast()).withProperty(Wire.WEST, nbt.getWest())
							.withProperty(Wire.NORTH, nbt.getNorth()).withProperty(Wire.SOUTH, nbt.getSouth());
		return state;
	}
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return Wire.B_POINT;
	}
	
	@Override
	public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox,
			List<AxisAlignedBB> list, Entity entityIn, boolean isActualState) {
		if (!isActualState) {
			state = getActualState(state, worldIn, pos);
		}
		
		addCollisionBoxToList(pos, entityBox, list, Wire.B_POINT);
		if (state.getValue(Wire.SOUTH)) {
			addCollisionBoxToList(pos, entityBox, list, Wire.B_SOUTH);
		}
		if (state.getValue(Wire.NORTH)) {
			addCollisionBoxToList(pos, entityBox, list, Wire.B_NORTH);
		}
		if (state.getValue(Wire.WEST)) {
			addCollisionBoxToList(pos, entityBox, list, Wire.B_WEST);
		}
		if (state.getValue(Wire.EAST)) {
			addCollisionBoxToList(pos, entityBox, list, Wire.B_EAST);
		}
	}
	
	/**
	 * 当电线被破坏时主动更新附近电器的连接信息
	 */
	@Override
	public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player) {
		onBlockBreak(worldIn, pos, null, true);
	}
	
	/**
	 * 主动更新附近电器的连接信息
	 * @param world 当前世界对象
	 * @param pos 需要更新的方块的坐标
	 * @param noUpdate 不需要更新的方块的坐标，用来防止循环调用
	 * @param donnotLink 是否将更新方块设置为不可连接
	 */
	private static void onBlockBreak(World world, BlockPos pos, List<BlockPos> noUpdate, boolean donnotLink) {
		noUpdate = (noUpdate == null) ? new ArrayList<>() : noUpdate;
		noUpdate.add(pos);
		//更新不区分客户端与服务端，以此减少信息同步异常的可能
		BlockPos[] poss = Tools.getBlockPosList(pos);
		Block block;
		
		IBlockState oldState;
		IBlockState newState;
		for (BlockPos p : poss) {
			oldState = world.getBlockState(p);
			block = oldState.getBlock();
			if (block instanceof WireBlock) {
				Wire.updateBlock(world, p, donnotLink ? new BlockPos[] { pos } : null);
				newState = world.getBlockState(p);
				if (needUpdate(oldState, newState) && !noUpdate.contains(p)) {
					onBlockBreak(world, p, noUpdate, false);
				}
			}
		}
	}
	
	@Override
	public void onBlockExploded(World world, BlockPos pos, Explosion explosion) {
		super.onBlockExploded(world, pos, explosion);
		onBlockHarvested(world, pos, null, null);
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}
	
	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		return 0;
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, Wire.EAST, Wire.NORTH, Wire.SOUTH, Wire.WEST, Wire.DOWN, Wire.UP);
	}
	
	@Override
	public ElectricityTranster createNewTileEntity(World worldIn, int meta) {
		return new TileEntityWire();
	}

	@Override
	public int quantityDropped(Random random) {
		return 1;
	}

	@Override
	public boolean canLink(LinkInfo info, boolean nowIsExist, boolean fromIsExist) {
		//判断当前方块是否存在
		if (nowIsExist) {
			//当前方块存在，判断调用方块
			if (info.fromBlock instanceof WireBlock) {
				ElectricityTranster nbt = (ElectricityTranster)
						                          ((info.nowUser == null) ?
								                           info.world.getTileEntity(info.nowPos) :info.nowUser);
				ElectricityTranster nbt2 = (ElectricityTranster)
						                           ((info.fromUser == null) ?
								                            info.world.getTileEntity(info.fromPos) : info.fromUser);
				return nbt.canLink(nbt2) && nbt2.canLink(nbt);
			} else if (info.fromBlock instanceof IEleInfo) {
				LinkInfo info2 = new LinkInfo(info.world, info.nowPos, info.fromPos, info.nowBlock, info.fromBlock);
				return ((IEleInfo) info.fromBlock).canLink(info2, fromIsExist, nowIsExist);
			}
			return false;
		} else {
			if (info.fromBlock instanceof IEleInfo) {
				if (info.nowUser == null) {
					throw new NullPointerException("判断信息不足，nowUser设置不能为空");
				}
				ElectricityTranster nbt;
				if (info.fromUser != null) {
					nbt = (ElectricityTranster) info.fromUser;
				} else {
					nbt = (ElectricityTranster) info.world.getTileEntity(info.fromPos);
				}
				if (info.nowUser instanceof ElectricityTranster) {
					return ((ElectricityTranster) info.nowUser).canLink(nbt) && nbt.canLink(info.nowUser);
				} else {
					return nbt.canLink(info.nowUser);
				}
			} else {
				return EleUtils.canLinkMinecraft(info.fromBlock);
			}
		}
	}
	
	/**
	 * 判断方块显示是否需要更新
	 */
	public static boolean needUpdate(IBlockState old, IBlockState now) {
		return (!old.getValue(Wire.UP).equals(now.getValue(Wire.UP))) ||
				       (!old.getValue(Wire.DOWN).equals(now.getValue(Wire.DOWN))) ||
				       (!old.getValue(Wire.SOUTH).equals(now.getValue(Wire.SOUTH))) ||
				       (!old.getValue(Wire.NORTH).equals(now.getValue(Wire.NORTH))) ||
				       (!old.getValue(Wire.WEST).equals(now.getValue(Wire.WEST))) ||
				       (!old.getValue(Wire.EAST).equals(now.getValue(Wire.EAST)));
	}
	
}
