/**   
* @Title: ElcUtils.java
* @Package minedreams.mi.api.electricity
* @author EmptyDreams
* @date 2019年10月1日 下午8:52:18
* @version V1.0
*/
package com.elementtimes.tutorial.common.wire;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * 这个类用于电线的一些判断，目前只实现了判断方块电线是否可以连接和
 * 获取方块需要的电能两个功能，
 * 其中涉及了原版的方块（如：铁块、钻石块等）
 * @author EmptyDremas
 */
public final class ElcUtils {

	/**
	 * 判断方块是否可以连接电线，
	 * <b>注意：此方法不保证fromPos/nowPos在此时已经在世界存在，所以提供了额外的参数</b>
	 * @param info 附加信息
	 * @param nowIsExist 当前方块是否存在
	 * @param fromIsExist 调用方块是否存在
	 */
	public static boolean canLink(LinkInfo info, boolean nowIsExist, boolean fromIsExist) {
		if (info.nowBlock.getRegistryName().getResourceDomain().equals("minecraft")) {
			switch (info.nowBlock.getRegistryName().getResourcePath()) {
				case "gravel": case "gold_ore": case "iron_ore": case "lapis_ore":
				case "lapis_block": case "gold_block": case "iron_block": case "diamond_ore":
				case "diamond_block": case "iron_door": case "redstone_ore": case "lit_redstone_ore":
				case "iron_bars": case "redstone_lamp": case "lit_redstone_lamp": case "emerald_ore":
				case "emerald_block": case "anvil": case "redstone_block": case "quartz_ore": case "hopper":
				case "iron_trapdoor": case "sea_lantern": return true;
			}
		} else if (info.nowBlock instanceof IEleInfo) {
			return ((IEleInfo) info.nowBlock).canLink(info, nowIsExist, fromIsExist);
		}
		return false;
	}
	
	/**
	 * 获取一个方块消耗的电能
	 * @param world 所在世界
	 * @param pos 方块坐标
	 * @param block 方块种类(可以为null)
	 * @param te 方块TE(可以为null)
	 * @return
	 */
	public static double energy(World world, BlockPos pos, Block block, TileEntity te) {
		if (block == null) block = world.getBlockState(pos).getBlock();
		if (block.getRegistryName().getResourceDomain().equals("minecraft")) {
			switch (block.getRegistryName().getResourcePath()) {
				case "gravel":
				case "quartz_ore":
				case "emerald_ore":
					return 5; case "gold_ore":
				case "lit_redstone_lamp":
				case "diamond_block":
				case "lapis_block":
					return 20;
				case "iron_ore":
				case "redstone_lamp":
					return 15; case "lapis_ore":
				case "sea_lantern":
				case "emerald_block":
				case "redstone_ore":
					return 10;
				case "gold_block":
				case "redstone_block":
				case "anvil":
					return 100;
				case "iron_block":
				case "iron_door":
					return 80; case "diamond_ore": return 9;
				case "lit_redstone_ore": return 11;
				case "iron_bars":
				case "iron_trapdoor":
					return 40;
				case "hopper": return 50;
			}
		} else if (block instanceof IEleInfo) {
			Electricity ele;
			if (te == null) ele = (Electricity) world.getTileEntity(pos);
			else ele = (Electricity) te;
			return ele.getEnergy().getEnergy();
		}
		return 0;
	}
	
}
