package com.elementtimes.tutorial.annotation;

import com.elementtimes.tutorial.common.creativetabs.ModCreativeTabs;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD})
public @interface ModItem {
    String registerName();
    String unlocalizedName();
    ModCreativeTabs creativeTab() default ModCreativeTabs.Main;
}