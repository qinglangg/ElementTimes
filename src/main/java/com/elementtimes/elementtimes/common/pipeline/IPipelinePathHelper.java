package com.elementtimes.elementtimes.common.pipeline;

import com.elementtimes.elementcore.api.utils.path.IPathHelper2;
import com.elementtimes.elementcore.api.utils.path.Path;
import net.minecraft.util.math.BlockPos;

import java.util.List;



public interface IPipelinePathHelper<T> extends IPathHelper2<BlockPos, NodeInfo, BaseElement<T>, PathInfo<T>> {

    ElementList<T> getPathResult(BaseElement<T> element, List<Path<BlockPos, NodeInfo, PathInfo<T>>> allPaths);
}
