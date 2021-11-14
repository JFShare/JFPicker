package com.Jfpicker.wheelpicker.picker_date;

import android.app.Activity;
import android.view.View;

import androidx.annotation.NonNull;

import com.Jfpicker.wheelpicker.picker_date.formatter.DateTextFormatter;
import com.Jfpicker.wheelpicker.picker_date.formatter.TimeTextFormatter;

/**
 * @author Created by JF on  2021/11/12 15:56
 * @description
 */

public class BirthdayWithTimePicker extends DateTimePicker {
    public BirthdayWithTimePicker(@NonNull Activity activity) {
        super(activity);
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
