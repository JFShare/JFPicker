package com.Jfpicker.wheelpicker.picker_date;

import android.app.Activity;
import android.view.View;

import androidx.annotation.NonNull;

import com.Jfpicker.wheelpicker.picker_date.formatter.DateTextFormatter;
import com.Jfpicker.wheelpicker.picker_date.formatter.TimeTextFormatter;
import com.Jfpicker.wheelpicker.wheel_dialog.DialogConfig;

/**
 * @author Created by JF on  2021/11/12
 * @description 带后缀文字的年月日时分秒选择器
 */

public class BirthdayWithTimePicker extends DateTimePicker {
    public BirthdayWithTimePicker(@NonNull Activity activity) {
        super(activity);
    }
    public BirthdayWithTimePicker(@NonNull Activity activity, DialogConfig dialogConfig) {
        super(activity,dialogConfig);
    }

    public BirthdayWithTimePicker(@NonNull Activity activity, int themeResId) {
        super(activity, themeResId);
    }

    @Override
    protected void initView() {
        super.initView();

        getDateLayout().setFormatter(new DateTextFormatter());
        getDateLayout().setLabelVisibility(View.GONE);

        getTimeLayout().setFormatter(new TimeTextFormatter());
        getTimeLayout().setLabelVisibility(View.GONE);

    }
}
