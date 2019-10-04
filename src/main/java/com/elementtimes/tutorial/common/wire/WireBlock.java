/**   
* @Title: WireBlock.java
* @Package minedreams.mi.blocks.tools.wire
* @author EmptyDreams
* @date 2019年7月29日 下午4:52:17
* @version V1.0
*/
package com.elementtimes.tutorial.common.wire;

import static com.elementtimes.tutorial.common.wire.Wire.DOWN;
import static com.elementtimes.tutorial.common.wire.Wire.EAST; 
import static com.elementtimes.tutorial.common.wire.Wire.NORTH;
import static com.elementtimes.tutorial.common.wire.Wire.SOUTH;
import static com.elementtimes.tutorial.common.wire.Wire.UP;   
import static com.elementtimes.tutorial.common.wire.Wire.WEST; 

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.elementtimes.elementcore.api.annotation.ModItem;
import com.elementtimes.tutorial.ElementTimes;
import com.elementtimes.tutorial.common.init.ElementtimesTabs;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/**
 * 普通电线
 * @author EmptyDremas
 */
public final class WireBlock extends BlockContainer implements IEleInfo {
	
	public WireBlock(String name) {
		super(Material.CIRCUITS);
		setSoundType(SoundType.SNOW);
		setHardness(0.35F);
	    setCreativeTab(ElementtimesTabs.Industry);
		setRegistryName(ElementTimes.MODID, name);
		setUnlocalizedName(name);
		setDefaultState(getDefaultState().withProperty(SOUTH, false)
				.withProperty(NORTH, false).withProperty(WEST, false).withProperty(EAST, false)
				.withProperty(DOWN, false).withProperty(UP, false));
		
		//这里注册方块 交给执着
		//AutoRegister.addAutoBlock(this, BlockRegister.WIRE_COPPER);
		//AutoRegister.addItem(getItemBlock(), getItemBlock().getRegistryName().getResourcePath());
	}
	
	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		Wire.NBT nbt = (Wire.NBT) worldIn.getTileEntity(pos);
		state = state.withProperty(UP, nbt.up).withProperty(DOWN, nbt.down)
							.withProperty(EAST, nbt.east).withProperty(WEST, nbt.west)
							.withProperty(NORTH, nbt.north).withProperty(SOUTH, nbt.south);
		return state;
	}
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return Wire.B_POINT;
	}
	
	@Override
	public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox,
			List<AxisAlignedBB> list, Entity entityIn, boolean isActualState) {
		if (!isActualState)
			state = getActualState(state, worldIn, pos);
		
		addCollisionBoxToList(pos, entityBox, list, Wire.B_POINT);
		if (state.getValue(SOUTH)) addCollisionBoxToList(pos, entityBox, list, Wire.B_SOUTH);
		if (state.getValue(NORTH)) addCollisionBoxToList(pos, entityBox, list, Wire.B_NORTH);
		if (state.getValue(WEST)) addCollisionBoxToList(pos, entityBox, list, Wire.B_WEST);
		if (state.getValue(EAST)) addCollisionBoxToList(pos, entityBox, list, Wire.B_EAST);
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
		BlockPos[] poss = Wire.getAllPos(pos);
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
		return new BlockStateContainer(this, EAST, NORTH, SOUTH, WEST, DOWN, UP);
	}
	
	@Override
	public ElectricityUser createNewTileEntity(World worldIn, int meta) {
		return new Wire.NBT();
	}

	@Override
	public int quantityDropped(Random random) {
		return 1;
	}

	@Override
	public boolean canLink(LinkInfo info, boolean nowIsExist, boolean fromIsExist) {
		if (nowIsExist) {
			if (info.fromBlock instanceof WireBlock) {
				Wire.NBT nbt = (Wire.NBT) ((info.nowUser == null) ? info.world.getTileEntity(info.nowPos) : info.nowUser);
				Wire.NBT nbt2 = (Wire.NBT) ((info.fromUser == null) ? info.world.getTileEntity(info.fromPos) : info.fromUser);
				return nbt.canLink(info.fromPos) && nbt2.canLink(info.nowPos);
			} else if (info.fromBlock instanceof IEleInfo) {
				LinkInfo info2 = new LinkInfo(info.world, info.nowPos, info.fromPos, info.nowBlock, info.fromBlock);
				return ((IEleInfo) info.fromBlock).canLink(info2, fromIsExist, nowIsExist);
			}
			return false;
		} else {
			if (info.fromBlock instanceof WireBlock) {
				Wire.NBT nbt;
				if (info.fromUser != null)
					nbt = (Wire.NBT) (info.fromUser);
				else
					nbt = (Wire.NBT) info.world.getTileEntity(info.fromPos);
				return nbt.canLink(info.nowPos);
			} else if (info.fromBlock instanceof IEleInfo) {
				LinkInfo info2 = new LinkInfo(info.world, info.nowPos, info.fromPos, info.nowBlock, info.fromBlock);
				return ((IEleInfo) info.fromBlock).canLink(info2, fromIsExist, nowIsExist);
			}
			return false;
		}
	}
	
	/**
	 * 判断方块显示是否需要更新
	 */
	public static boolean needUpdate(IBlockState old, IBlockState now) {
		return (old.getValue(UP) != now.getValue(UP)) ||
				       (old.getValue(DOWN) != now.getValue(DOWN)) ||
				       (old.getValue(SOUTH) != now.getValue(SOUTH)) ||
				       (old.getValue(NORTH) != now.getValue(NORTH)) ||
				       (old.getValue(WEST) != now.getValue(WEST)) ||
				       (old.getValue(EAST) != now.getValue(EAST));
	}
	
}