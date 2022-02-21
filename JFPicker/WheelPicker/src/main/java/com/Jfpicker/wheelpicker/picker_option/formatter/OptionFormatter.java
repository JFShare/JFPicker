package com.Jfpicker.wheelpicker.picker_option.formatter;

import com.Jfpicker.wheelpicker.picker_option.entity.OptionEntity;

/**
 * @author Created by JF on  2021/11/13
 * @description 格式化选项
 */

public class OptionFormatter {
    public String formatOption(OptionEntity entity) {
        return entity.getWheelItem();
    }
}
