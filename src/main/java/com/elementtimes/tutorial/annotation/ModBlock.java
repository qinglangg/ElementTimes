package com.elementtimes.tutorial.annotation;

import com.elementtimes.tutorial.common.creativetabs.ModCreativeTabs;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标记注册 Block
 * 可将其注解到类或静态变量中。
 *  注解类则会使用无参构造实例化被标记类，并注册
 *  注解成员变量则会尝试使用无参构造实例化成员变量类型，并注册
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD})
public @interface ModBlock {
    String registerName();
    String unlocalizedName();
    ModCreativeTabs creativeTab() default ModCreativeTabs.Main;

    /**
     * 注册该类绑定的 TileEntity
     *  name：对应 TileEntity 注册名
     *  clazz: 对应 TileEntity 类全类名
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.TYPE, ElementType.FIELD})
    @interface TileEntity {
        String name();
        String clazz();
    }

    /**
     * 对 ModMultiBlock 的注解
     * 使用 ModStateMapper 简化
     * 指定材质应用于方块本体
     * 若无 StateMap 注解，将注册所有状态对应的物品材质
     * @see StateMapperCustom
     * @see StateMap
     */
    @Target({ElementType.TYPE, ElementType.FIELD})
    @Retention(RetentionPolicy.RUNTIME)
    @interface StateMapper {
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

    /**
     * 使用自定义 IStateMap
     * 该注解与 ModStateMapper 注解冲突且优先级高于 ModStateMapper 注解
     * 默认留空 则加载 DefaultStateMapper
     * 若无 StateMap 注解，将注册所有状态对应的物品材质
     * 指定材质应用于方块本体
     * @see StateMapper
     * @see StateMap
     */
    @Target({ElementType.TYPE, ElementType.FIELD})
    @Retention(RetentionPolicy.RUNTIME)
    @interface StateMapperCustom {
        String value() default "";
    }

    /**
     * 标记为包含 BlockState
     * 作用有二，一是控制开启 OBJ/B3D 渲染，二是将不同 metadata 值的方块注册为不同物品
     * 指定材质应用于方块物品
     * @see StateMapper
     * @see StateMapperCustom
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.TYPE, ElementType.FIELD})
    @interface StateMap {
        // 使用三方渲染
        /**
         * 启用 OBJ 渲染
         */
        boolean useOBJ() default false;

        /**
         * 启用 U3D 渲染
         */
        boolean useB3D() default false;

        /**
         * metadata 对应物品
         * 若留空，则只注册 0b0000 和 DefaultState 对应 metadata 的物品
         * models 或 properties 数组长度必须不短于该数组长度
         */
        int[] metadatas() default {};

        /**
         * 对应 metadata 物品使用的材质 id，即 # 前的内容
         * 有 StateMapperCustom 或 StateMapper 注解时，该属性若为空，则对应 metadata 的 材质由 IStateMapper 生成
         * 否则，为该方块 RegistryName
         */
        String[] models() default {};

        /**
         * 对应材质 id 的属性，即 # 后的内容。
         * 有 StateMapperCustom 或 StateMapper 注解时，该属性若为空，则对应 metadata 的 材质由 IStateMapper 生成
         * 否则，为 inventory
         */
        String[] properties() default {};
    }
}