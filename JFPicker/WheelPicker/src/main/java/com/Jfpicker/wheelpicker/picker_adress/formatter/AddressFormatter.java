package com.Jfpicker.wheelpicker.picker_adress.formatter;

import androidx.annotation.NonNull;

import com.Jfpicker.wheelpicker.picker_adress.entity.AddressEntity;
import com.Jfpicker.wheelpicker.wheelview.format.WheelFormatListener;

/**
 * @author Created by JF on  2021/11/13
 * @description
 */

public class AddressFormatter implements WheelFormatListener {
    @Override
    public String formatItem(@NonNull Object item) {
        if (item instanceof AddressEntity) {
            return ((AddressEntity) item).getName();
        }
        return item.toString();
    }
}
