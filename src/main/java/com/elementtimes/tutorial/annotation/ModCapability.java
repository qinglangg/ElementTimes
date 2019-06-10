package com.elementtimes.tutorial.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Deprecated
public @interface ModCapability {

    /**
     * Capability 对应数据接口类
     */
    String typeInterfaceClass();

    /**
     * Capability 对应数据接口类的实现类
     */
    String typeImplementationClass();

    /**
     * Capability.IStorage 实现类
     */
    String storageClass();
}
