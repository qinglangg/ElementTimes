package com.elementtimes.tutorial.common.wire;

import com.elementtimes.tutorial.common.wire.simpleImpl.SimpleImplWire;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

import static com.elementtimes.tutorial.common.wire.Wire.*;

public class TileEntityWire extends ElectricityTranster {
	
	@Override
	public ElectricityEnergy getEnergy() {
		return null;
	}
	
	/**
	 * 存储已经更新过的玩家列表，因为作者认为单机时长会更多，所以选择1作为默认值。<br>
	 * 	不同方块不共用此列表且此列表不会离线存储，当玩家离开方块过远或退出游戏等操作导致
	 * 		方块暂时“删除”后此列表将重置以保证所有玩家可以正常渲染电线方块
	 */
	public final List<String> players = new ArrayList<>(1);
	
	@Override
	public void send(SimpleNetworkWrapper simple, boolean isClient) {
		if (isClient) {
			return;
		}
		super.send(simple, isClient);
		//新建消息
		SimpleImplWire siw = new SimpleImplWire();
		siw.ini(this);
		//遍历所有玩家
		for (EntityPlayer player : world.playerEntities) {
			//如果玩家已经更新过则跳过
			if (!EntitySelectors.NOT_SPECTATING.apply(player) || (players.contains(player.getName()))) {
				continue;
			}
			
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
	
	@Override
	public boolean canLink(TileEntity ele) {
		if (ele == null) {
			return false;
		}
		boolean exET = ele instanceof ElectricityTranster;
		if (!exET) {
			if (!(EleUtils.canLink(new LinkInfo(world, getPos(), ele.getPos(),
					getBlockType(), ele.getBlockType()), true, false))) {
				return false;
			}
			EnumFacing facing = Tools.whatFacing(getPos(), ele.getPos());
			if (linkBlock.containsKey(facing)) {
				return linkBlock.get(facing) == null;
			}
			return true;
		}
		if (ele.equals(next) || ele.equals(prev)) {
			return true;
		}
		return next == null || prev == null;
	}
	
	@Override
	public ElectricityTranster next(ElectricityTranster ele) {
		if (next != null && next.equals(ele)) {
			return prev;
		}
		if (prev != null && prev.equals(ele)) {
			return next;
		}
		return null;
	}
	
	@Override
	public boolean linkForce(TileEntity ele) {
		if (ele == null) {
			return false;
		}
		boolean exET = ele instanceof ElectricityTranster;
		if (!exET) {
			if (!(EleUtils.canLink(new LinkInfo(world, getPos(), ele.getPos(),
					getBlockType(), ele.getBlockType()), true, false))) {
				return false;
			}
			EnumFacing facing = Tools.whatFacing(getPos(), ele.getPos());
			if (linkBlock.containsKey(facing)) {
				if (linkBlock.get(facing) != null) {
					return false;
				}
			}
			linkBlock.put(facing, ele);
		}
		ElectricityTranster et = (ElectricityTranster) ele;
		if (ele.equals(next) || ele.equals(prev)) {
			return true;
		}
		if (next == null) {
			if (exET) {
				next = et;
				return true;
			}
		}
		if (prev == null) {
			if (exET) {
				prev = et;
				return true;
			}
		}
		return true;
	}
	
	/**
	 * 连接一个电线
	 * @param blockPos 需要连接的电线所在方块
	 * @return 是否连接成功
	 */
	@Override
	public boolean linkForce(BlockPos blockPos) {
		return linkForce(blockPos == null ? null : (TileEntityWire) world.getTileEntity(blockPos));
	}
	
	/**
	 * 通过IBlockState更新内部数据
	 */
	@Override
	public void update(@Nonnull IBlockState state) {
		up = state.getValue(UP);
		down = state.getValue(DOWN);
		east = state.getValue(EAST);
		west = state.getValue(WEST);
		south = state.getValue(SOUTH);
		north = state.getValue(NORTH);
		markDirty();
		if (!world.isRemote) {
			players.clear();
		}
	}
	
}
