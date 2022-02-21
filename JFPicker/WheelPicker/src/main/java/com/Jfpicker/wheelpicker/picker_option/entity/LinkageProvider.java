
package com.Jfpicker.wheelpicker.picker_option.entity;

import androidx.annotation.NonNull;

import java.util.List;

/**
 * 使用了AndroidPicker的LinkageProvider代码
 * 源码地址：https://github.com/gzu-liyujiang/AndroidPicker
 */
public interface LinkageProvider {
    int INDEX_NO_FOUND = -1;

    /**
     * 是否展示第一级
     *
     * @return 返回true表示展示第一级
     */
    boolean firstLevelVisible();

    /**
     * 是否展示第三级
     *
     * @return 返回true表示展示第三级
     */
    boolean thirdLevelVisible();

    /**
     * 提供第一级数据
     *
     * @return 第一级数据
     */
    @NonNull
    List<?> provideFirstData();

    /**
     * 根据第一级数据联动第二级数据
     *
     * @param firstIndex 第一级数据索引
     * @return 第二级数据
     */
    @NonNull
    List<?> linkageSecondData(int firstIndex);

    /**
     * 根据第一二级数据联动第三级数据
     *
     * @param firstIndex  第一级数据索引
     * @param secondIndex 第二级数据索引
     * @return 第三级数据
     */
    @NonNull
    List<?> linkageThirdData(int firstIndex, int secondIndex);

    /**
     * 根据第一数据值查找其索引
     *
     * @param firstValue 第一级数据值
     * @return 第一级数据索引
     */
    int findFirstIndex(Object firstValue);

    /**
     * 根据第二数据值查找其索引
     *
     * @param firstIndex  第一级数据索引
     * @param secondValue 第二级数据值
     * @return 第二级数据索引
     */
    int findSecondIndex(int firstIndex, Object secondValue);

    /**
     * 根据第三数据值查找其索引
     *
     * @param firstIndex  第一级数据索引
     * @param secondIndex 第二级数据索引
     * @param thirdValue  第三级数据值
     * @return 第三级数据索引
     */
    int findThirdIndex(int firstIndex, int secondIndex, Object thirdValue);

}