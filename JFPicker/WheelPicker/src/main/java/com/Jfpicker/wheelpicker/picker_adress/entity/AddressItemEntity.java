package com.Jfpicker.wheelpicker.picker_adress.entity;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Created by JF on  2021/11/15
 * @description
 */

public class AddressItemEntity extends AddressEntity {

    private List<AddressItemEntity> nextList;

    @NonNull
    public List<AddressItemEntity> getNextList() {
        if (nextList == null) {
            nextList = new ArrayList<>();
        }
        return nextList;
    }

    public void setNextList(List<AddressItemEntity> nextList) {
        this.nextList = nextList;
    }
}
