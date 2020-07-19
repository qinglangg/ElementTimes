package com.elementtimes.elementtimes.common.pipeline;

import com.elementtimes.elementcore.api.utils.path.Path;
import com.google.common.collect.ImmutableList;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nullable;
import java.util.*;



public abstract class BasePipelinePathHelper<T> implements IPipelinePathHelper<T> {

    private final T mNullValue;

    public BasePipelinePathHelper(T nullValue) {
        mNullValue = nullValue;
    }

    @Override
    public BaseElement<T> testAsMiddle(BlockPos node, BaseElement<T> element, BlockPos prevNode, NodeInfo prevInfo) {
        IPipeline pipeline = getPipeline(node);
        if (pipeline == null) {
            return element.copy(mNullValue);
        } 
        return pipeline.receiveElement(getPipeline(prevNode), element, true);
    }

    @Override
    public NodeInfo getSingleNodeInfo(BlockPos node, BaseElement<T> element, PathInfo<T> info) {
        return new NodeInfo(0, info.totalTick, getElementCount(element));
    }

    @Override
    public Replace replacePath(Path<BlockPos, NodeInfo, PathInfo<T>> existPath, Path<BlockPos, NodeInfo, PathInfo<T>> newPath) {
        int existIndex = existPath.path.size() - 1;
        int newIndex = newPath.path.size() - 1;
        if (existPath.path.get(existIndex).equals(newPath.path.get(newIndex))) {
            NodeInfo existInfo = existPath.info.get(existIndex);
            NodeInfo newInfo = newPath.info.get(newIndex);
            if (existInfo.count == newInfo.count) {
                return existInfo.totalTick > newInfo.totalTick ? Replace.NEW : Replace.OLD;
            }
        }
        return Replace.BOTH;
    }

    @Override
    public Collection<BlockPos> getNextNodes(BlockPos node, BaseElement<T> element, @Nullable BlockPos prevNode, @Nullable NodeInfo prevInfo) {
        IPipeline pipeline = getPipeline(node);
        Set<BlockPos> nodes = new HashSet<>();
        if (pipeline != null) {
            nodes.addAll(pipeline.allReachablePipelines());
            nodes.addAll(pipeline.allReachableOutputs());
            nodes.remove(node);
            nodes.remove(prevNode);
        }
        return nodes;
    }

    @Override
    public NodeInfo getBeginNodeInfo(BlockPos node, BaseElement<T> element) {
        return new NodeInfo(0, 0, getElementCount(element));
    }

    @Override
    public NodeInfo getMiddleNodeInfo(BlockPos node, BaseElement<T> element, BlockPos prevNode, NodeInfo prevInfo) {
        IPipeline pipeline = getPipeline(node);
        if (pipeline == null) {
            return new NodeInfo(0, 0, 0);
        }
        int keepTime = pipeline.getKeepTime(element);
        return new NodeInfo(keepTime, prevInfo.totalTick + keepTime, getElementCount(element));
    }

    @Override
    public NodeInfo getEndNodeInfo(BlockPos blockPos, PathInfo<T> info, BlockPos prevNode, NodeInfo prevInfo) {
        return new NodeInfo(0, info.totalTick, getElementCount(info.stack));
    }

    @Override
    public boolean replacePath(List<Path<BlockPos, NodeInfo, PathInfo<T>>> existPaths, List<BlockPos> blockPos, List<NodeInfo> nodeInfos, BlockPos nextNode, NodeInfo nextInfo, BaseElement<T> element) {
        boolean isReplaced = false;
        for (Path<BlockPos, NodeInfo, PathInfo<T>> existPath : existPaths) {
            int existIndex = existPath.path.indexOf(nextNode);
            NodeInfo existInfo = existPath.info.get(existIndex);
            if (existIndex > 0 && existInfo.count == nextInfo.count) {
                if (existInfo.totalTick > nextInfo.totalTick) {
                    existPath.path = ImmutableList.<BlockPos>builder()
                            .addAll(blockPos)
                            .add(nextNode)
                            .addAll(existPath.path.subList(existIndex + 1, existPath.path.size()))
                            .build();
                    existPath.info = ImmutableList.<NodeInfo>builder()
                            .addAll(nodeInfos)
                            .add(nextInfo)
                            .addAll(existPath.info.subList(existIndex + 1, existPath.info.size()))
                            .build();
                    isReplaced = true;
                }
            }
        }
        return isReplaced;
    }

    @Override
    public boolean isElementEmpty(BaseElement<T> element) {
        return element.isEmpty();
    }

    @Nullable
    public abstract IPipeline getPipeline(BlockPos node);

    public abstract int getElementCount(BaseElement<T> element);

    public abstract int getElementCount(T element);
}
