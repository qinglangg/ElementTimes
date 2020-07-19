package com.elementtimes.elementtimes.common.pipeline;

import com.elementtimes.elementcore.api.utils.path.Path;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.function.ToIntFunction;



public class ElementList<T> extends ArrayList<Path<BlockPos, NodeInfo, PathInfo<T>>> {
    public final BaseElement<T> surplusElement;

    public ElementList(BaseElement<T> surplusElement) {
        super();
        this.surplusElement = surplusElement;
    }

    public ElementList(Collection<? extends Path<BlockPos, NodeInfo, PathInfo<T>>> c, BaseElement<T> surplusElement) {
        super(c);
        this.surplusElement = surplusElement;
    }

    public static <T> Comparator<Path<BlockPos, NodeInfo, PathInfo<T>>> byTick() {
        return (o1, o2) -> Integer.compare(o2.extra.totalTick, o1.extra.totalTick);
    }

    public static <T> Comparator<Path<BlockPos, NodeInfo, PathInfo<T>>> byCount(ToIntFunction<T> toCount) {
        return (o1, o2) -> Integer.compare(toCount.applyAsInt(o2.extra.stack), toCount.applyAsInt(o1.extra.stack));
    }
}
