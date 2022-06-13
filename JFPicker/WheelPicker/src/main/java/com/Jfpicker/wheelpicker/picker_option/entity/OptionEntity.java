package com.Jfpicker.wheelpicker.picker_option.entity;


/**
 * @author Created by JF on  2021/11/13
 * @description 选项实体类
 */

public class OptionEntity implements IOptionEntity {

    String id;
    String name;
    boolean checked;

    public OptionEntity(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public OptionEntity(String id, String name, boolean checked) {
        this.id = id;
        this.name = name;
        this.checked = checked;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getWheelItem() {
        return name;
    }


    public String getUniqueId() {
        return id;
    }


}
