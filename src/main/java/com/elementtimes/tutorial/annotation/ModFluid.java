package com.elementtimes.tutorial.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @deprecated 暂时有问题，没写注册
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD})
@Deprecated
public @interface ModFluid {
    String registerName();
    String unlocalizedName();
    String blockClass();
    String creativeTabClass() default "com.elementtimes.tutorial.common.creativetabs.ElementTimesTabs$Main";
}
