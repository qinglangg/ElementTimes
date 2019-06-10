package com.elementtimes.tutorial.annotation;

import com.elementtimes.tutorial.annotation.enums.FluidBlockType;
import com.elementtimes.tutorial.common.creativetabs.ModCreativeTabs;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 流体注册
 * @deprecated 暂时有问题，没写注册
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD})
@Deprecated
public @interface ModFluid {
    /**
     * 代表流体注册名
     * 当该注解注解 Field 且流体 registerName 与属性名相同（忽略大小写，使用 toLowerCase 处理）时，可省略
     * 当该注解注解 Class 且流体 registerName 与类名相同（忽略大小写，使用 toLowerCase 处理）时，可省略
     * @return name
     */
    String name() default "";

    /**
     * UnlocalizedName，用于获取流体显示名
     * 当 unlocalizedName 与 registerName 相同时，可省略
     * @return unlocalizedName
     */
    String unlocalizedName() default "";

    /**
     * @return 是否启用万能桶
     */
    boolean bucket() default true;

    /**
     * @return 静止材质
     */
    String stillResource();

    /**
     * @return 流动材质
     */
    String flowingResource();

    /**
     * @return 覆盖材质
     */
    String overlayResource() default "";

    /**
     * @return 流体颜色 ARGB
     */
    int color() default 0xFFFFFFFF;

    /**
     * @return 流体桶所在创造列表
     */
    ModCreativeTabs creativeTabClass() default ModCreativeTabs.Main;

    /**
     * 流体方块
     */
    @interface FluidBlock {
        FluidBlockType type() default FluidBlockType.Classic;
    }
}
