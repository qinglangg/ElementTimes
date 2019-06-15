package com.elementtimes.tutorial.annotation.processor;

import com.elementtimes.tutorial.annotation.annotations.ModNetwork;

import java.lang.reflect.AnnotatedElement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.elementtimes.tutorial.annotation.util.MessageUtil.warn;

/**
 * 处理网络通信
 * @author luqin2007
 */
public class ModNetworkLoader {

    public static void getElements(Map<Class, ArrayList<AnnotatedElement>> elements, List<Object[]> into) {
        elements.get(ModNetwork.class).forEach(element -> {
            ModNetwork network = element.getAnnotation(ModNetwork.class);
            try {
                Class messageClass = (Class) element;
                Class handlerClass = Class.forName(network.handlerClass());
                into.add(new Object[] {network, handlerClass, messageClass});
            } catch (ClassNotFoundException e) {
                warn("Can't find the handler class {}", network.handlerClass());
            }
        });
    }
}
