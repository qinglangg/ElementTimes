package com.elementtimes.tutorial.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 使用 BlockStateMapper 简化 BlockState 与模型的对应关系
 *  不使用 assets.{mod id}.blockstates.{block id}.json
 *  使用多个 assets.{mod id}.blockstates.{property.name()}{suffix}.json
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ModStateMapper {

    // suffix() 模型文件名的后缀
    String suffix();
    // IProperty 属性名
    // withName() 模型 state 文件名的主体
    String propertyName();
    // ignore() 模型 state 文件中 忽略的属性
    String[] propertyIgnore() default {};
    // IProperty 属性所在类
    String propertyIn();
}
