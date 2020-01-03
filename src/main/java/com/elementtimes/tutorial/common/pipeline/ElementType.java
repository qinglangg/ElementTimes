package com.elementtimes.tutorial.common.pipeline;

import java.util.HashMap;
import java.util.Map;

public interface ElementType {

    Map<String, ElementType> TYPES = new HashMap<>();

    String type();

    BaseElement newInstance();

    static void register(ElementType... types) {
        for (ElementType type : types) {
            TYPES.put(type.type(), type);
        }
    }
}
