package com.elementtimes.tutorial.annotation.util;

import com.elementtimes.tutorial.Elementtimes;

public class MessageUtil {

    /**
     * 发送 Warn 级 Log
     * @param message Log 格式化信息 {}
     * @param params Log 信息格式化成分
     */
    public static void warn(String message, Object... params) {
        Elementtimes.getLogger().warn("[Elementtimes] " + message, params);
    }
}
