package com.elementtimes.elementtimes.interfaces;

import com.elementtimes.elementcore.api.utils.MathUtils;

/**
 * 任何存在以一个 int 存储多个值，以 byte 为单位读写的类的接口

 */
public interface IByteData {

    /**
     * 存储的 int
     * @return int 值
     */
    int getByteData();

    /**
     * 设置 int 值
     * @param data 值
     */
    void setByteData(int data);

    /**
     * 保存数据时是否总是调用 setByteData 方法
     * 若、旧数据与新数据相同时，若此方法返回 false，则不会调用 set 方法。
     * @return 是否总是调用 set 方法
     */
    default boolean shouldAlwaysSet() {
        return false;
    }

    /**
     * 设置 data，包含检查新旧值是否相同的逻辑
     * @param newData 新值
     * @return 是否改变，若新旧值相同则返回 false
     */
    default boolean setByteDataInternal(int newData) {
        int data = getByteData();
        if (newData == data) {
            if (shouldAlwaysSet()) {
                setByteData(newData);
            }
            return false;
        } else {
            setByteData(newData);
            return true;
        }
    }

    /**
     * 读取某一位，取出 boolean 值
     * @param pos 位置
     * @return 值
     */
    default boolean readByteValue(int pos) {
        return MathUtils.fromByte(getByteData(), pos);
    }

    /**
     * 读取某几位，取出 int 值
     * @param offsetRight 位置偏移值，从该位开始读取
     * @param valueLength 值的长度（二进制位数）
     * @return 值
     */
    default int readByteValue(int offsetRight, int valueLength) {
        int mask = 0;
        for (int i = 0; i < valueLength; i++) {
            mask = (mask << 1) | 1;
        }
        return (getByteData() >> offsetRight) & mask;
    }

    /**
     * 写入某几位的 int 值
     * @param offsetRight 位置偏移，从该位开始写入
     * @param valueLength 写入值长度（二进制位数）
     * @param newValue 新值
     * @return 值是否被改变
     */
    default boolean writeByteValue(int offsetRight, int valueLength, int newValue) {
        int data = getByteData();
        int right = 0;
        for (int i = 0; i < offsetRight; i++) {
            right = ((right << 1) | 1);
        }
        right = data & right;
        int left = data >> (offsetRight + valueLength);
        left = ((left << valueLength) | newValue);
        int newData = (left << offsetRight) | right;
        return setByteDataInternal(newData);
    }

    /**
     * 写入某一位，相当于设置某一位为 true（1）
     * @param pos 位置
     * @return 值是否被改变
     */
    default boolean writeByteValue(int pos) {
        int data = getByteData();
        int newData = MathUtils.setByte(data, pos, true);
        return setByteDataInternal(newData);
    }

    /**
     * 清除某一位，相当于设置某一位为 false（0）
     * @param pos 位置
     * @return 值是否被改变
     */
    default boolean clearByteValue(int pos) {
        int data = getByteData();
        int newData = MathUtils.setByte(data, pos, false);
        return setByteDataInternal(newData);
    }
}
