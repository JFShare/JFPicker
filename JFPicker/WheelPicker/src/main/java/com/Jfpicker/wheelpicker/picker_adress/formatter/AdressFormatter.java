package com.Jfpicker.wheelpicker.picker_adress.formatter;

import androidx.annotation.NonNull;

import com.Jfpicker.wheelpicker.picker_adress.entity.AddressEntity;
import com.Jfpicker.wheelpicker.picker_base.WheelFormatter;

/**
 * @author Created by JF on  2021/11/13
 * @description
 */

public class AdressFormatter implements WheelFormatter {
    @Override
    public String formatItem(@NonNull Object item) {
        if (item != null && item instanceof AddressEntity) {
            return ((AddressEntity) item).getName();
        }
        return "";
    }
}
