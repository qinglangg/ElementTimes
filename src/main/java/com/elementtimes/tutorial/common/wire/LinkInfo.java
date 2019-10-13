/*
* @Title: LinkInfo.java
* @Package minedreams.mi.api.electricity
* @author EmptyDreams
* @date 2019年9月13日 下午5:02:46
* @version V1.0
*/
package com.elementtimes.tutorial.common.wire;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * 存储连接信息
 * @author EmptyDremas
 */
public final class LinkInfo {
	
	/* 必填信息 */
	/** 所在世界 */
	public final World world;
	/** 调用方块的坐标 */
	public final BlockPos fromPos;
	/** 当前方块坐标 */
	public final BlockPos nowPos;
	/** 调用方块的种类 */
	public final Block fromBlock;
	/** 当前方块的种类 */
	public final Block nowBlock;
	
	/* 可选信息 */
	/** 调用方块的TE */
	public TileEntity fromUser;
	/** 当前方块的TE */
	public Electricity nowUser;
	/** 调用方块的IBlockState */
	public IBlockState fromState;
	/** 当前方块的IBlockState */
	public IBlockState nowState;
	
	public LinkInfo(World world, BlockPos fromPos, BlockPos nowPos, Block fromBlock, Block nowBlock) {
		this.world = world;
		this.fromPos = fromPos;
		this.nowPos = nowPos;
		this.fromBlock = fromBlock;
		this.nowBlock = nowBlock;
	}
	
}
