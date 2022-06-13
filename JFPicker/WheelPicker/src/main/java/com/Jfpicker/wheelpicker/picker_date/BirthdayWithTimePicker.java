package com.Jfpicker.wheelpicker.picker_date;

import android.app.Activity;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.StyleRes;

import com.Jfpicker.wheelpicker.picker_date.formatter.FillZeroAppendTextFormatter;

import com.Jfpicker.wheelpicker.dialog.config.DialogConfig;

/**
 * @author Created by JF on  2021/11/12
 * @description 带后缀文字的年月日时分秒选择器
 */

public class BirthdayWithTimePicker extends DateTimePicker {

    public BirthdayWithTimePicker(@NonNull Activity activity) {
        super(activity);
    }

    public BirthdayWithTimePicker(@NonNull Activity activity, DialogConfig dialogConfig) {
        super(activity, dialogConfig);
    }

    public BirthdayWithTimePicker(@NonNull Activity activity, int themeResId) {
        super(activity, themeResId);
    }
    public BirthdayWithTimePicker(@NonNull Activity activity, DialogConfig dialogConfig, @StyleRes int themeResId) {
        super(activity, dialogConfig, themeResId);
    }
    @Override
    protected void initView() {
        super.initView();
        getDateLayout().getWheelViewYear().setFormatter(new FillZeroAppendTextFormatter("年"));
        getDateLayout().getWheelViewMonth().setFormatter(new FillZeroAppendTextFormatter("月"));
        getDateLayout().getWheelViewDay().setFormatter(new FillZeroAppendTextFormatter("日"));
        getDateLayout().setLabelVisibility(View.GONE);

        getTimeLayout().getWheelViewHour().setFormatter(new FillZeroAppendTextFormatter("时"));
        getTimeLayout().getWheelViewMinute().setFormatter(new FillZeroAppendTextFormatter("分"));
        getTimeLayout().getWheelViewSecond().setFormatter(new FillZeroAppendTextFormatter("秒"));
        getTimeLayout().setLabelVisibility(View.GONE);
    }
}
