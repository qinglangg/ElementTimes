package com.elementtimes.tutorial.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 这里有个合成表(＠_＠;)
 * 标记合成表产物或者原料 具体看具体类型的合成
 * 可标记 Item，Block，String(默认认为是 矿辞，id以 [id]: 开头加以区分)
 * 该系列注解只能用于成员变量，不能用于类
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ModRecipe {
    /**
     * 锤子合成
     * json 注册 我注册个锤子啊 头疼
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    @interface Ore {
        /**
         * name 用于注册
         */
        String value();

        /**
         * 消耗锤子的耐久
         */
        int damage() default 1;

        /**
         * 产物数量
         */
        int dustCount() default 3;

        /**
         * 产物 id
         */
        String output();
    }

    enum RecipeType {
        Shaped, Shapeless, OreShaped, OreShapeless
    }
}
