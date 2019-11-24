package com.elementtimes.tutorial.common.block.pipeline;

import java.util.HashMap;
import java.util.Map;

public interface ElementType<T extends BaseElement> {

    Map<String, ElementType> TYPES = new HashMap<>();

    String type();

    T newInstance();

    static void register(ElementType type) {
        TYPES.put(type.type(), type);
    }
}
