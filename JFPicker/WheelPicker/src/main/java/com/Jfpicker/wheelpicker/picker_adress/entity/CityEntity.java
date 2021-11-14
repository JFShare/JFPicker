

package com.Jfpicker.wheelpicker.picker_adress.entity;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class CityEntity extends AddressEntity {
    private List<CountyEntity> countyList;

    @NonNull
    public List<CountyEntity> getCountyList() {
        if (countyList == null) {
            countyList = new ArrayList<>();
        }
        return countyList;
    }

    public void setCountyList(List<CountyEntity> countyList) {
        this.countyList = countyList;
    }

}
