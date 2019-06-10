package com.elementtimes.tutorial.annotation;

import com.elementtimes.tutorial.annotation.enums.FluidBlockType;
import com.elementtimes.tutorial.common.creativetabs.ModCreativeTabs;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 流体注册
 * 通常，应当忽略 name/stillResource/flowingResource/overlayResource/color 参数
 * 以上参数用于当对应注解成员不存在时，用于新建 Fluid 对象。这种用法是不推荐的。
 * 因此 实际有用的 仅为 bucket，creativeTabClass 参数
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD})
public @interface ModFluid {
    /**
     * 代表流体注册名
     * 当该注解注解 Field 且流体 name 与属性名相同（忽略大小写，使用 toLowerCase 处理）时，可省略
     * 当该注解注解 Class 且流体 name 与类名相同（忽略大小写，使用 toLowerCase 处理）时，可省略
     * @return name
     */
    String name() default "";

    /**
     * 使用该参数请务必确保有 mod 开启万能桶
     * @return 是否添加对应的桶
     */
    boolean bucket() default true;

    /**
     * @return 静止材质
     */
    String stillResource() default "";

    /**
     * @return 流动材质
     */
    String flowingResource() default "";

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
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.TYPE, ElementType.FIELD})
    @interface FluidBlock {
        /**
         * RegisterName，代表方块注册名
         * 当该注解注解 Field 且方块 registerName 与属性名相同（忽略大小写，使用 toLowerCase 处理）时，可省略
         * 当该注解注解 Class 且方块 registerName 与类名相同（忽略大小写，使用 toLowerCase 处理）时，可省略
         * @return registerName
         */
        String registerName() default "";

        /**
         * UnlocalizedName，用于获取方块显示名
         * 当 unlocalizedName 与 registerName 相同时，可省略
         * @return unlocalizedName
         */
        String unlocalizedName() default "";

        /**
         * @return 创造物品栏
         */
        ModCreativeTabs creativeTab() default ModCreativeTabs.None;

        /**
         * 创建默认流体方块
         * 当 className/id 参数非空时忽略
         * XXXLava 表示使用 Lava 材质，默认 Water
         * @return 方块类型
         */
        FluidBlockType type() default FluidBlockType.Classic;

        /**
         * 自定义流体方块全类名
         * 使用此方法指定流体方块，会优先使用带有一个 Fluid 类型参数的构造
         * 有限度最高
         * 使用该参数获取的流体方块仍会在 registerName/unlocalizedName 为 null 时设置其值
         * 仍会设置 creativeTab
         * @return 自定义流体方块全类名
         */
        String className() default "";

        /**
         * 由于 Forge 读取流体方块材质根据流体 name 属性区分，因此可以多个流体公用一个 blockstate json 文件
         * 此属性可设置使用的 blockstate 文件名（不包括扩展名）。
         * 留空则仍使用方块的 RegistryName
         * @return blockstate 资源名
         */
        String resource() default "fluids";

        /**
         * 使用 FluidBlock 时，默认不会再加载 Fluid 的材质。当此参数为 true 时，仍会加载对应材质
         * 此时 加载的材质通常用于绘制其他 gui 界面
         * @return 是否仍要加载流体材质
         */
        boolean loadTexture() default true;
    }
}
