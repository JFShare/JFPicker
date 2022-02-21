package com.Jfpicker.wheelpicker.picker_date;

import android.app.Activity;
import android.view.View;

import androidx.annotation.NonNull;

import com.Jfpicker.wheelpicker.picker_date.formatter.DateTextFormatter;
import com.Jfpicker.wheelpicker.wheel_dialog.DialogConfig;

/**
 * @author Created by JF on  2021/11/12
 * @description 带后缀文字的年月日选择器
 */

public class BirthdayPicker extends YearMonthDayPicker {

    public BirthdayPicker(@NonNull Activity activity) {
        super(activity);
    }

    public BirthdayPicker(@NonNull Activity activity, DialogConfig dialogConfig) {
        super(activity, dialogConfig);
    }

    public BirthdayPicker(@NonNull Activity activity, int themeResId) {
        super(activity, themeResId);
    }

    @Override
    protected void initView() {
        super.initView();
        getWheelLayout().setFormatter(new DateTextFormatter());
        getWheelLayout().setLabelVisibility(View.GONE);
    }
}
