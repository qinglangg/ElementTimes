package com.elementtimes.tutorial.annotation.util;

import com.elementtimes.tutorial.Elementtimes;

/**
 * 处理 log，消息等
 *
 * @author luqin2007
 */
public class MessageUtil {

    /**
     * 发送 Warn 等级的 Log
     * @param message Log 格式化信息 {}
     * @param params Log 信息格式化成分
     */
    public static void warn(String message, Object... params) {
        Elementtimes.getLogger().warn("[Elementtimes] " + message, params);
    }
}
