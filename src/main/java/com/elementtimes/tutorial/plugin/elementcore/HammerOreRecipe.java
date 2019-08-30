package com.elementtimes.tutorial.plugin.elementcore;

import net.minecraftforge.fml.common.discovery.ASMDataTable;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 锤子合成
 * 可标记 Item，Block，String(默认认为是 矿辞，id以 [id] 开头加以区分)
 * json 注册 我注册个锤子啊 头疼
 * @author luqin2007
 */
public class HammerOreRecipe {

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    public @interface Ore {
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

    public static void parser(ASMDataTable.ASMData data) {

    }
}
