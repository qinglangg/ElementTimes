package com.elementtimes.tutorial.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ModMultiBlock {
    String registerName();
    String unlocalizedName();

    // 用于 nameByMeta 函数
    String[] nameByMetadata();
    String creativeTab() default "com.lq2007.mymod.creativetab.MyCreativeTab";
    int[] metadataToGetName();

    // 用于区分 Model
    String[] modelByMetadata();
    int[] metadataToGetModel();

    // 使用三方渲染
    boolean useOBJ() default false;
    boolean useB3D() default false;
}
