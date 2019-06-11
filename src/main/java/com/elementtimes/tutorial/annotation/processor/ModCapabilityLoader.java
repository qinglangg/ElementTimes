package com.elementtimes.tutorial.annotation.processor;

import com.elementtimes.tutorial.annotation.ModCapability;

import java.lang.reflect.AnnotatedElement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 对于 Capability 注解解析
 * @author luqin2007
 */
public class ModCapabilityLoader {

    public static void getCapabilities(HashMap<Class, ArrayList<AnnotatedElement>> map, List<ModCapability> into) {
        map.get(ModCapability.class).forEach(element -> into.add(element.getAnnotation(ModCapability.class)));
    }
}
