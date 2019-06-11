package com.elementtimes.tutorial.annotation.processor;

import com.elementtimes.tutorial.annotation.ModElement;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.jline.utils.Log.warn;

/**
 * 加载其他被注解控制的元素
 * 处理所有 ModElement 注解
 * 目前只有一个 ModElement.ModInvokeStatic 注解
 *
 * @author luqin2007
 */
public class ModElementLoader {

    public static List<Method> sInvokers = new LinkedList<>();

    public static void getElements(Map<Class, ArrayList<AnnotatedElement>> elements) {
        elements.get(ModElement.class).forEach(ModElementLoader::buildInvoker);
    }

    private static void buildInvoker(AnnotatedElement element) {
        ModElement.ModInvokeStatic invoker = element.getAnnotation(ModElement.ModInvokeStatic.class);
        if (invoker != null) {
            String value = invoker.value();
            Method method = null;
            try {
                if (element instanceof Class) {
                    method = ((Class) element).getDeclaredMethod(value);
                } else if (element instanceof Field) {
                    method = ((Field) element).getType().getDeclaredMethod(value);
                }
            } catch (NoSuchMethodException e) {
                try {
                    if (element instanceof Class) {
                        method = ((Class) element).getMethod(value);
                    } else if (element instanceof Field) {
                        method = ((Field) element).getType().getMethod(value);
                    }
                } catch (NoSuchMethodException ex) {
                    warn("Skip Function: {} from {}", invoker.value(), element);
                }
            }
            if (method != null) {
                method.setAccessible(true);
                sInvokers.add(method);
            }
        }
    }
}
