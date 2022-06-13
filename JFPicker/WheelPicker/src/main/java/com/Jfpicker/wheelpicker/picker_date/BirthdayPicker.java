package com.Jfpicker.wheelpicker.picker_date;

import android.app.Activity;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.StyleRes;

import com.Jfpicker.wheelpicker.picker_date.formatter.FillZeroAppendTextFormatter;
import com.Jfpicker.wheelpicker.dialog.config.DialogConfig;

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

    public BirthdayPicker(@NonNull Activity activity, DialogConfig dialogConfig, @StyleRes int themeResId) {
        super(activity, dialogConfig, themeResId);
    }

    @Override
    protected void initView() {
        super.initView();
        getWheelLayout().getWheelViewYear().setFormatter(new FillZeroAppendTextFormatter("年"));
        getWheelLayout().getWheelViewMonth().setFormatter(new FillZeroAppendTextFormatter("月"));
        getWheelLayout().getWheelViewDay().setFormatter(new FillZeroAppendTextFormatter("日"));
        getWheelLayout().setLabelVisibility(View.GONE);
    }
}
