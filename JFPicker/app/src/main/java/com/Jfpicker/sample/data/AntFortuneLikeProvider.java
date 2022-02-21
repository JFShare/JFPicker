

package com.Jfpicker.sample.data;

import androidx.annotation.NonNull;


import com.Jfpicker.wheelpicker.picker_option.entity.LinkageProvider;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 使用了AndroidPicker的演示代码
 * 源码地址：https://github.com/gzu-liyujiang/AndroidPicker
 */

public class AntFortuneLikeProvider implements LinkageProvider {

    @Override
    public boolean firstLevelVisible() {
        return true;
    }

    @Override
    public boolean thirdLevelVisible() {
        return false;
    }

    @NonNull
    @Override
    public List<String> provideFirstData() {
        return Arrays.asList("每周", "每两周", "每月", "每日");
    }

    @NonNull
    @Override
    public List<String> linkageSecondData(int firstIndex) {
        switch (firstIndex) {
            case 0:
            case 1:
                return Arrays.asList("周一", "周二", "周三", "周四", "周五");
            case 2:
                List<String> data = new ArrayList<>();
                for (int i = 1; i <= 28; i++) {
                    data.add(i + "日");
                }
                return data;
        }
        return new ArrayList<>();
    }

    @NonNull
    @Override
    public List<String> linkageThirdData(int firstIndex, int secondIndex) {
        return new ArrayList<>();
    }

    @Override
    public int findFirstIndex(Object firstValue) {
        return 0;
    }

    @Override
    public int findSecondIndex(int firstIndex, Object secondValue) {
        return 0;
    }

    @Override
    public int findThirdIndex(int firstIndex, int secondIndex, Object thirdValue) {
        return 0;
    }

}
