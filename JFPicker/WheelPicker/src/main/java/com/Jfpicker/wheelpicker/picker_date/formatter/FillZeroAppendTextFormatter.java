package com.Jfpicker.wheelpicker.picker_date.formatter;

import androidx.annotation.NonNull;

/**
 * @author Created by JF on  2022/6/7 11:21
 * @description
 */

public class FillZeroAppendTextFormatter extends FillZeroFormatter {

    private String appendText;

    public FillZeroAppendTextFormatter(String appendText) {
        this.appendText = appendText;
    }

    @Override
    public String formatItem(@NonNull Object item) {
        return super.formatItem(item) + appendText;
    }
}
