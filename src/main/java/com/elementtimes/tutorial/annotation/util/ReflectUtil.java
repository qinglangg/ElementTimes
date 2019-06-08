package com.elementtimes.tutorial.annotation.util;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.elementtimes.tutorial.annotation.util.MessageUtil.warn;

/**
 * 处理反射有关的方法
 *
 * @author luqin2007
 */
public class ReflectUtil {

    /**
     * 获得可被注解条目的值
     * 只能获取静态值
     */
    @SuppressWarnings("unchecked")
    public static <T> Optional<T> getFromAnnotated(@Nonnull AnnotatedElement holder, @Nullable T defaultValue) {
        T obj;
        if (holder instanceof AccessibleObject) {
            ((AccessibleObject) holder).setAccessible(true);
        }

        if (holder instanceof Class) {
            obj = (T) create((Class) holder).orElse(defaultValue);
        } else if (holder instanceof Field) {
            obj = get((Field) holder, null, defaultValue, true).orElse(defaultValue);
        } else if (holder instanceof Constructor) {
            obj = create((Constructor<T>) holder).orElse(defaultValue);
        } else if (holder instanceof Method) {
            obj = (T) invoke((Method) holder, null).orElse(defaultValue);
        } else {
            obj = defaultValue;
        }
        return Optional.ofNullable(obj);
    }

    /**
     * 使用无参构造创建对象
     *
     * @param aClass 要创建的对象类
     * @param <T> 对象类型
     */
    public static <T> Optional<T> create(@Nonnull Class<? extends T> aClass) {
        T object = null;
        try {
            object = aClass.newInstance();
        } catch (IllegalAccessException | InstantiationException e) {
            warn("Cannot create an instance of {}. Please make sure the class has a public constructor with zero parameter.", aClass.getSimpleName());
            e.printStackTrace();
        }
        return Optional.ofNullable(object);
    }

    /**
     * 使用无参构造创建对象
     *
     * @param className 要创建的对象类的全类名
     */
    public static <T> Optional<T> create(@Nonnull String className) {
        T object = null;
        if (className.isEmpty()) {
            warn("You want to find an EMPTY class.");
        } else {
            try {
                Class<?> aClass = Class.forName(className);
                object = (T) create(aClass).orElse(null);
            } catch (ClassNotFoundException e) {
                warn("Class {} is not exist. Please make sure the class is exist and the ClassLoader can load the class", className);
                e.printStackTrace();
            }
        }
        return Optional.ofNullable(object);
    }

    public static <T> Optional<T> create(@Nonnull Constructor<T> constructor) {
        constructor.setAccessible(true);
        T t = null;
        try {
            t = constructor.newInstance();
        } catch (InstantiationException | InvocationTargetException | IllegalAccessException e) {
            warn("Constructor {} can not invoke", constructor.getName());
            e.printStackTrace();
        }
        return Optional.ofNullable(t);
    }

    /**
     * 获取一个成员变量
     * 方法会先尝试获取该成员本身的值
     * 否则会从该成员的类型中调用其无参构造尝试创建对象
     *
     * @param field 变量签名
     * @param defaultValue 若无法获取，使用的默认对象
     * @param object 变量所在对象，静态则为 null
     * @param setIfNull 当变量原值为 null 时，是否自动赋值
     * @param <T> 变量类型
     */
    public static <T> Optional<T> get(@Nonnull Field field, @Nullable Object object, @Nullable T defaultValue, boolean setIfNull) {
        T obj = null;
        field.setAccessible(true);
        boolean isStatic = Modifier.isStatic(field.getModifiers());
        // 成员本身值
        try {
            if (isStatic) {
                obj = (T) field.get(null);
            } else if (object != null) {
                obj = (T) field.get(object);
            } else {
                warn("Field {} is not static, but the object is null", field.getName());
            }
        } catch (IllegalAccessException e) {
            warn("Cannot get field {}", field.getName());
        }

        if (obj == null) {
            // 尝试根绝类型获取值
            obj = (T) create(field.getType()).orElse(null);
            if (obj == null) {
                obj = defaultValue;
            }
            // 尝试赋值
            if (setIfNull && obj != null) {
                set(field, obj, null);
            }
        }

        return Optional.ofNullable(obj);
    }

    /**
     * 调用一个方法，获取其返回值
     * 方法会先尝试使用无参调用该方法
     * 否则会从该成员的类型中调用其无参构造尝试创建对象
     *
     * @param method 方法签名
     * @param <T> 方法类型
     */
    public static <T> Optional<T> invoke(@Nonnull Method method, @Nullable Object object) {
        T obj = null;
        method.setAccessible(true);
        boolean isStatic = Modifier.isStatic(method.getModifiers());
        // 成员本身值
        try {
            if (isStatic) {
                obj = (T) method.invoke(null);
            } else if (object != null) {
                obj = (T) method.invoke(object);
            } else {
                warn("Method {} is not static, but the object is null", method.getName());
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            warn("Cannot invoke method {}", method.getName());
        }

        if (obj == null) {
            // 尝试根绝类型获取值
            obj = (T) create(method.getReturnType()).orElse(null);
        }
        return Optional.ofNullable(obj);
    }

    /**
     * 尝试为一个成员变量赋值
     *
     * @param field 要赋值的变量
     * @param value 要赋的值
     * @param object 所在对象。静态值可为 null
     */
    public static void set(@Nonnull Field field, @Nullable Object value, @Nullable Object object) {
        field.setAccessible(true);
        try {
            int modifiers = field.getModifiers();
            if (Modifier.isStatic(modifiers)) {
                field.set(null, value);
            } else {
                if (object == null) {
                    warn("Field {} is not state, but the object is null", field.getName());
                }
                field.set(object, value);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            warn("Field {} cannot set the value: \n\t{}\n Maybe it was a final field.", field.getName(), value);
        }
    }

    /**
     * 将注解数组转化为注解 Map，用于筛选指定注解
     *
     * @param handler 包含注解的 Class, AccessibleObject 或类实例 object
     * @param find 需要筛选出的注解
     * @return Map，以注解 Class 对象为键，注解实例为值
     */
    public static Map<Class<? extends Annotation>, Annotation> getAnnotations(@Nonnull AnnotatedElement handler, @Nullable Class<? extends Annotation>[] find) {
        Map<Class<? extends Annotation>, Annotation> annotationMap = new HashMap<>();
        if (handler instanceof AccessibleObject) {
            ((AccessibleObject) handler).setAccessible(true);
        }
        if (find != null && find.length > 0) {
            for (Class<? extends Annotation> aClass : find) {
                Annotation annotation = handler.getAnnotation(aClass);
                if (annotation != null) {
                    annotationMap.put(aClass, annotation);
                }
            }
        }
        return annotationMap;
    }

    /**
     * 获取类成员
     * 优先获取静态成员
     *
     * @param clazz 成员所在类
     * @param fieldName 成员名
     * @param object 类实例
     * @param <T> 成员类型
     * @return 尝试获取成员的结果
     */
    @SuppressWarnings("unchecked")
    public static <T> Optional<T> getField(@Nonnull Class clazz, @Nonnull String fieldName, @Nullable Object object) {
        T field = null;
        try {
            // 静态成员
            Field holder = clazz.getDeclaredField(fieldName);
            holder.setAccessible(true);
            field = (T) get(holder, null, null, false).orElse(null);
            if (field == null) {
                // 实例成员
                holder = clazz.getField(fieldName);
                holder.setAccessible(true);
                if (object == null) {
                    // 尝试初始化实例
                    Constructor constructor = clazz.getConstructor();
                    constructor.setAccessible(true);
                    object = constructor.newInstance();
                }
                field = (T) get(holder, object, null, false);
            }
        } catch (InstantiationException
                | NoSuchFieldException
                | InvocationTargetException
                | NoSuchMethodException
                | IllegalAccessException e) {
            warn("Cannot get field {} from {}", fieldName, clazz.getCanonicalName());
            e.printStackTrace();
        }
        return Optional.ofNullable(field);
    }

    public static Optional<String> getName(@Nullable AnnotatedElement element) {
        String defaultName = null;
        if (element instanceof Field) {
            defaultName = ((Field) element).getName();
        } else if (element instanceof Class) {
            defaultName = ((Class) element).getSimpleName();
        }
        return Optional.ofNullable(defaultName);
    }
}
