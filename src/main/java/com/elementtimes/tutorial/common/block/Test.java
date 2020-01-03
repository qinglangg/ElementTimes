package com.elementtimes.tutorial.common.block;

import com.elementtimes.elementcore.api.template.block.BlockTileBase;
import com.elementtimes.tutorial.ElementTimes;
import com.elementtimes.tutorial.common.tileentity.TileTest;

/**
 * 用于测试的功能不定的方块
 * @author luqin2007
 */
public class Test extends BlockTileBase<TileTest> {

    public Test() {
        super(TileTest.class, ElementTimes.instance);
    }
}
