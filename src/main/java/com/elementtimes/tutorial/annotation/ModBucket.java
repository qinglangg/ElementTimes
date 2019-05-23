package com.elementtimes.tutorial.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ModBucket {
    String registerName();
    String unlocalizedName();
    String creativeTabClass() default "com.lq2007.mymod.creativetab.MyCreativeTab";
}
