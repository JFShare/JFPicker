package com.Jfpicker.wheelpicker.picker_option.entity;



/**
 * @author Created by JF on  2021/11/13 9:50
 * @description
 */

public class OptionEntity implements IOptionEntity {

    String id;
    String name;

    public OptionEntity(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getWheelItem() {
        return name;
    }


    public String getIdentifiy() {
        return id;
    }
}
