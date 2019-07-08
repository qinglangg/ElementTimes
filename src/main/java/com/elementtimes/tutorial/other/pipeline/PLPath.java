package com.elementtimes.tutorial.other.pipeline;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * 代表一条路线
 * @author luqin2007
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class PLPath {

    private LinkedList<PLInfo> mPaths;
    private long mTotalTick;

    /**
     * 用于遍历查找最短路径，不要在此保存其他内容
     */
    long findFlag = -1;

    /**
     * 根据已有路径创建路径
     * @param start 路径起点
     * @param next 剩余路径
     */
    public PLPath(PLInfo start, List<PLInfo> next) {
        mPaths = new LinkedList<>(next);
        mPaths.addFirst(start);
        mTotalTick = mPaths.stream().mapToLong(PLInfo::getKeepTick).sum();
    }

    /**
     * 根据已有路径创建路径
     * @param start 路径起点
     * @param next 剩余路径
     */
    public PLPath(PLInfo start, PLPath next) {
        mPaths = new LinkedList<>(next.getPaths());
        mPaths.addFirst(start);
        mTotalTick = next.getTotalTick() + start.getKeepTick();
    }

    /**
     * 根据已有路径创建路径
     * @param before 之前路径
     * @param end 路径终点
     */
    public PLPath(List<PLInfo> before, PLInfo end) {
        mPaths = new LinkedList<>(before);
        mPaths.addLast(end);
        mTotalTick = mPaths.stream().mapToLong(PLInfo::getKeepTick).sum();
    }

    /**
     * 根据已有路径创建路径
     * @param before 之前路径
     * @param end 路径终点
     */
    public PLPath(PLPath before, PLInfo end) {
        mPaths = new LinkedList<>(before.getPaths());
        mPaths.addLast(end);
        mTotalTick = before.getTotalTick() + end.getKeepTick();
    }

    /**
     * 根据所有路径节点创建路径
     * @param paths 路径节点
     */
    public PLPath(PLInfo... paths) {
        mPaths = new LinkedList<>(Arrays.asList(paths));
        mTotalTick = mPaths.stream().mapToLong(PLInfo::getKeepTick).sum();
    }

    /**
     * 根据所有路径节点创建路径
     * @param paths 路径节点
     */
    public PLPath(Collection<PLInfo> paths) {
        mPaths = paths instanceof LinkedList ? (LinkedList<PLInfo>) paths : new LinkedList<>(paths);
        mTotalTick = mPaths.stream().mapToLong(PLInfo::getKeepTick).sum();
    }

    /**
     * 获取路径终点
     * @return 路径终点
     */
    public PLInfo getEnd() {
        return mPaths.getLast();
    }

    /**
     * 获取路径起点
     * @return 路径起点
     */
    public PLInfo getStart() {
        return mPaths.getFirst();
    }

    /**
     * 获取详细路径
     * @return 路径
     */
    public LinkedList<PLInfo> getPaths() {
        return mPaths;
    }

    /**
     * 获取通过该路径的总时间（tick）
     * @return 总时间
     */
    public long getTotalTick() {
        return mTotalTick;
    }

    /**
     * 获取某一节点的下一节管道
     * @param pipeline 查询节点
     * @return 下一节管道
     */
    public PLInfo getNextPipeline(PLInfo pipeline) {
        if (pipeline == mPaths.getLast()) {
            return pipeline;
        }
        int i = mPaths.indexOf(pipeline);
        if (i < 0) {
            return mPaths.getFirst();
        } else {
            return mPaths.get(i + 1);
        }
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    /**
     * 计算最短路径时使用。用于比较并更新路径
     * @param path 新路径
     */
    void update(PLPath path) {
        if (path != null) {
            // 更新条件：用时更短，或用时相同路径更短
            long tick = path.getTotalTick();
            LinkedList<PLInfo> tempPaths = path.getPaths();
            boolean needUpdate = getTotalTick() > tick
                    || (mTotalTick == tick && mPaths.size() > tempPaths.size());
            if (needUpdate) {
                mTotalTick = tick;
                mPaths = tempPaths;
            }
        }
    }
}
