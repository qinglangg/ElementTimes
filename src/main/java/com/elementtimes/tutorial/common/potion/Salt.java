package com.elementtimes.tutorial.common.potion;

import com.elementtimes.tutorial.common.init.ElementtimesFluids;
import net.minecraft.potion.Potion;

/**
 * 腌制
 * 使用后可以快速食用盐，恢复生命与饥饿值
 * 效果结束后有惊喜
 * @author luqin2007
 */
public class Salt extends Potion {

    public Salt() {
        super(false, ElementtimesFluids.seawater.getColor());
        setIconIndex(6, 2);
    }
}
