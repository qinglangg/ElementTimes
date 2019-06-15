package com.elementtimes.tutorial.annotation.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 这里有个合成表(＠_＠;)
 * 标记合成表产物或者原料 具体看具体类型的合成
 * 该系列注解只能用于成员变量，不能用于类
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
public @interface ModRecipe {

    /**
     * 锤子合成
     * 可标记 Item，Block，String(默认认为是 矿辞，id以 [id] 开头加以区分)
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

    /**
     * 工作台合成
     * 注解 IRecipe 类/对象上
     * 类型为 IRecipe/Supplier<IRecipe> 或 Object[]，或生成他们的方法。
     * 若为数组或集合，且该数组或集合第一个元素为 IRecipe/Supplier<IRecipe>，则只会解析 IRecipe/Supplier<IRecipe>
     *     否则，会被当作一个合成表解析，第一个元素为返回物品，其余依次为合成槽位内的物品
     * 数组/列表值使用 CraftingHelper.getIngredient 解析
     * @see net.minecraftforge.common.crafting.CraftingHelper#getIngredient(Object)
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.FIELD, ElementType.METHOD})
    @interface Crafting {
        /**
         * 合成表名
         * 留空则使用 Field 变量名/Class 名
         * @return 合成表名
         */
        String value() default "";

        /**
         * @return 是否为有序合成
         */
        boolean shaped() default true;

        /**
         * @return 是否为矿辞合成
         */
        boolean ore() default true;

        /**
         * @return 有序合成的宽
         */
        int width() default 3;

        /**
         * @return 有序合成的高
         */
        int height() default 3;
    }
}
