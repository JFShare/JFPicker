
package com.Jfpicker.wheelpicker.picker_adress.impl;

import androidx.annotation.NonNull;


import com.Jfpicker.wheelpicker.picker_adress.annotation.AddressMode;
import com.Jfpicker.wheelpicker.picker_adress.entity.AddressItemEntity;

import com.Jfpicker.wheelpicker.picker_option.entity.LinkageProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * 使用了AndroidPicker的地址加载代码
 * 源码地址：https://github.com/gzu-liyujiang/AndroidPicker
 */
public class AddressProvider implements LinkageProvider {
    private final List<AddressItemEntity> data;
    private final int mode;

    public AddressProvider(@NonNull List<AddressItemEntity> data, int mode) {
        this.data = data;
        this.mode = mode;
    }

    @Override
    public boolean firstLevelVisible() {
        return mode == AddressMode.PROVINCE_CITY_COUNTY || mode == AddressMode.PROVINCE_CITY;
    }

    @Override
    public boolean thirdLevelVisible() {
        return mode == AddressMode.PROVINCE_CITY_COUNTY || mode == AddressMode.CITY_COUNTY;
    }

    @NonNull
    @Override
    public List<AddressItemEntity> provideFirstData() {
        return data;
    }

    @NonNull
    @Override
    public List<AddressItemEntity> linkageSecondData(int firstIndex) {
        if (data.size() == 0) {
            return new ArrayList<>();
        }
        if (firstIndex == INDEX_NO_FOUND) {
            firstIndex = 0;
        }
        return data.get(firstIndex).getNextList();
    }

    @NonNull
    @Override
    public List<AddressItemEntity> linkageThirdData(int firstIndex, int secondIndex) {
        List<AddressItemEntity> data = linkageSecondData(firstIndex);
        if (data.size() == 0) {
            return new ArrayList<>();
        }
        if (secondIndex == INDEX_NO_FOUND) {
            secondIndex = 0;
        }
        return data.get(secondIndex).getNextList();
    }

    @Override
    public int findFirstIndex(Object firstValue) {
        if (firstValue == null) {
            return INDEX_NO_FOUND;
        }
        if (firstValue instanceof AddressItemEntity) {
            return data.indexOf(firstValue);
        }
        for (int i = 0, n = data.size(); i < n; i++) {
            AddressItemEntity entity = data.get(i);
            if (entity.getCode().equals(firstValue.toString()) ||
                    entity.getName().contains(firstValue.toString())) {
                return i;
            }
        }
        return INDEX_NO_FOUND;
    }

    @Override
    public int findSecondIndex(int firstIndex, Object secondValue) {
        if (secondValue == null) {
            return INDEX_NO_FOUND;
        }
        List<AddressItemEntity> cityList = linkageSecondData(firstIndex);
        if (secondValue instanceof AddressItemEntity) {
            return cityList.indexOf(secondValue);
        }
        for (int i = 0, n = cityList.size(); i < n; i++) {
            AddressItemEntity entity = cityList.get(i);
            if (entity.getCode().equals(secondValue.toString()) ||
                    entity.getName().contains(secondValue.toString())) {
                return i;
            }
        }
        return INDEX_NO_FOUND;
    }

    @Override
    public int findThirdIndex(int firstIndex, int secondIndex, Object thirdValue) {
        if (thirdValue == null) {
            return INDEX_NO_FOUND;
        }
        List<AddressItemEntity> countyList = linkageThirdData(firstIndex, secondIndex);
        if (thirdValue instanceof AddressItemEntity) {
            return countyList.indexOf(thirdValue);
        }
        for (int i = 0, n = countyList.size(); i < n; i++) {
            AddressItemEntity entity = countyList.get(i);
            if (entity.getCode().equals(thirdValue.toString()) ||
                    entity.getName().contains(thirdValue.toString())) {
                return i;
            }
        }
        return INDEX_NO_FOUND;
    }

}
