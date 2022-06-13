package com.Jfpicker.wheelpicker.picker_date.formatter;

import androidx.annotation.NonNull;

import com.Jfpicker.wheelpicker.wheelview.format.WheelFormatListener;

/**
 * @author Created by JF on  2022/6/7 11:18
 * @description
 */

public class FillZeroFormatter implements WheelFormatListener {
    @Override
    public String formatItem(@NonNull Object item) {
        if (item instanceof Integer) {
            return (Integer) item < 10 ? "0" + item : item + "";
        }
        return item.toString();
    }
}
