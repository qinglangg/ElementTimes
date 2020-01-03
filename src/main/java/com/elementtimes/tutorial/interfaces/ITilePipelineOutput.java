package com.elementtimes.tutorial.interfaces;

import com.elementtimes.tutorial.common.pipeline.BaseElement;

/**
 * 输出管道接口
 * @author luqin2007
 */
public interface ITilePipelineOutput extends ITilePipelineIO {
    /**
     * 将物品输出到某个方向
     * @param element 物品
     * @return 未输出的物品
     */
    BaseElement output(BaseElement element);

    /**
     * 判断物品是否可以输出
     * @param element 传输乳品
     * @return 是否可输出
     */
    boolean canOutput(BaseElement element);
}
