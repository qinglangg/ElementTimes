package com.elementtimes.tutorial.annotation;

import net.minecraftforge.fml.relauncher.Side;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// 客户端与服务端通信 用于 IMessage 接口实现类
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ModNetwork {

    // 频道 ID
    int id();
    // 数据包的 Handler 类, 实现 IMessageHandler 接口
    String handlerClass();
    // 数据包接收端
    Side[] side();
}
