
package com.Jfpicker.wheelpicker.picker_adress.entity;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * 省级数据实体
 *
 * @author 贵州山野羡民（1032694760@qq.com）
 * @since 2019/6/17 11:47
 */
public class ProvinceEntity extends AddressEntity {
    private List<CityEntity> cityList;

    @NonNull
    public List<CityEntity> getCityList() {
        if (cityList == null) {
            cityList = new ArrayList<>();
        }
        return cityList;
    }

    public void setCityList(List<CityEntity> cityList) {
        this.cityList = cityList;
    }

}
