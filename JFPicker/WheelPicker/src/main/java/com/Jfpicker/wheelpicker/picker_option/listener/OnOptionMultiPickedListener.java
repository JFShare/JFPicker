package com.Jfpicker.wheelpicker.picker_option.listener;

import com.Jfpicker.wheelpicker.picker_option.entity.OptionEntity;

import java.util.List;

/**
 * @author Created by JF on  2022/6/6 10:57
 * @description
 */

public interface OnOptionMultiPickedListener {
    void onOption(List<OptionEntity> checkedList);
}
