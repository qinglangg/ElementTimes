package com.elementtimes.tutorial.interfaces.tileentity;

/**
 * @author luqin2007
 */
public interface IConfigApply {

    /**
     * 覆写此方法，用于应用配置文件的某些参数
     */
    default void applyConfig() {};
}
