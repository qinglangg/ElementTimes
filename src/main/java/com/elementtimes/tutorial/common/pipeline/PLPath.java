package com.elementtimes.tutorial.common.pipeline;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.*;

/**
 * 代表一条路线
 * @author luqin2007
 */
public class PLPath {

    public static List<PLPath> find(World world, BaseElement element, BlockPos beginPipeline, BlockPos blockFrom) {
        List<PLPath> result = new ArrayList<>();
        findWay(world, element, result, new LinkedList<>(), beginPipeline, blockFrom, new HashSet<>());
        return result;
    }

    /**
     * 路径查询
     * @param world 所在世界
     * @param element 传输物品
     * @param wayResults 查询结果
     * @param pathPositions 当前经过路径
     * @param thisPos 当前位置
     * @param elementContainer 物品源位置
     * @param checkedPositions 已经过位置
     */
    private static void findWay(World world, BaseElement element, List<PLPath> wayResults,
                                LinkedList<BlockPos> pathPositions, BlockPos thisPos, BlockPos elementContainer,
                                Set<BlockPos> checkedPositions) {
        if (!checkedPositions.contains(thisPos)) {
            checkedPositions.add(thisPos);
            pathPositions.add(thisPos);
            TileEntity te = world.getTileEntity(thisPos);
            if (te instanceof ITilePipeline) {
                ITilePipeline pipeline = (ITilePipeline) te;
                if (pipeline instanceof IPipelineOutput && ((IPipelineOutput) pipeline).canOutput(element)) {
                    PLPath path = tryBuildPLPath(world, element, elementContainer, pathPositions);
                    if (path != null) {
                        wayResults.add(path);
                    }
                } else {
                    for (EnumFacing direction : EnumFacing.values()) {
                        BlockPos offset = thisPos.offset(direction);
                        if (pipeline.canPost(offset, direction, element)) {
                            findWay(world, element, wayResults, new LinkedList<>(pathPositions), offset, elementContainer, new HashSet<>(checkedPositions));
                        }
                    }
                }
            }
        }
    }

    public BlockPos from;
    public LinkedList<BlockPos> path = new LinkedList<>();
    public long totalTick = Long.MAX_VALUE;

    private static PLPath tryBuildPLPath(World world, BaseElement element, BlockPos from, LinkedList<BlockPos> positions) {
        long tick = 0;
        for (BlockPos pos : positions) {
            TileEntity te = world.getTileEntity(pos);
            if (te instanceof ITilePipeline) {
                tick += ((ITilePipeline) te).getKeepTime(element);
            } else {
                return null;
            }
        }
        PLPath path = new PLPath();
        path.totalTick = tick;
        path.from = from;
        path.path.addAll(positions);
        return path;
    }

    public boolean isEmpty() {
        return path.isEmpty();
    }
}
