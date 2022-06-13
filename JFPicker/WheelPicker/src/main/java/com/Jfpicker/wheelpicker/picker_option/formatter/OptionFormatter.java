package com.Jfpicker.wheelpicker.picker_option.formatter;

import androidx.annotation.NonNull;

import com.Jfpicker.wheelpicker.picker_option.entity.OptionEntity;
import com.Jfpicker.wheelpicker.wheelview.format.WheelFormatListener;

/**
 * @author Created by JF on  2021/11/13
 * @description 格式化选项
 */

public class OptionFormatter implements WheelFormatListener {

    @Override
    public String formatItem(@NonNull Object item) {
        if (item instanceof OptionEntity){
            return ((OptionEntity) item).getWheelItem();
        }
        return item.toString();
    }
}
