package com.elementtimes.tutorial.annotation;

import net.minecraft.entity.EnumCreatureType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ModEntity {

    int network() default 1;
    String id();
    String name();
    /**
     * 实体跟踪
     * trackerRange 半径, 即超过该半径, 实体就不更新了 一般生物 64 比较合适
     * updateFrequency 更新频率 单位 gametick 一般生物 3 比较合适, MAX_VALUE 为 不更新
     * sendVelocityUpdate 是否同步实体的速度更新, 静止实体和手动更新位置的实体为 false
     */
    int trackerRange() default 64;
    int updateFrequency() default 3;
    boolean sendVelocityUpdate() default true;
    // 是否添加 怪物蛋
    boolean hasEgg() default false;
    int eggColorPrimary() default 0x000000;
    int eggColorSecondary() default 0x000000;
    // 是否在世界生成
    boolean canSpawn() default false;
    EnumCreatureType spawnType() default EnumCreatureType.CREATURE;
    int spawnWeight() default 0; // weight 权重越高越可能优先生成
    int spawnMin() default 0;
    int spawnMax() default 0;
    String[] biomeIds() default { "plains" };
    // 类
    String renderClass();
}
