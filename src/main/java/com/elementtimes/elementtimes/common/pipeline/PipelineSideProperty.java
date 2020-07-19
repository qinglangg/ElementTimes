package com.elementtimes.elementtimes.common.pipeline;

import com.google.common.collect.Lists;
import net.minecraft.state.EnumProperty;
import net.minecraft.util.Direction;

import java.util.Arrays;
import java.util.List;

/**
 * 避免泛型数组

 */
public class PipelineSideProperty extends EnumProperty<ConnectType> {

    protected static List<ConnectType> VALUES = Lists.newArrayList(ConnectType.values());

    public PipelineSideProperty(Direction direction) {
        super(direction.getName(), ConnectType.class, VALUES);
    }
}
