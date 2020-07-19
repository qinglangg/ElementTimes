package com.elementtimes.elementtimes.common.pipeline;

import java.util.HashMap;
import java.util.Map;



public interface ElementType<T> {

    Map<String, ElementType<?>> TYPES = new HashMap<>();

    String type();

    BaseElement<T> newInstance(T object);

    BaseElement<T> newInstance();

    static void register(ElementType<?>... types) {
        for (ElementType<?> type : types) {
            TYPES.put(type.type(), type);
        }
    }
}
