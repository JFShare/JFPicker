package com.Jfpicker.wheelpicker.picker_date;

import android.app.Activity;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.StyleRes;

import com.Jfpicker.wheelpicker.picker_date.listener.OnDatePickedListener;
import com.Jfpicker.wheelpicker.picker_date.widget.YearMonthDayWheelLayout;
import com.Jfpicker.wheelpicker.wheel_dialog.DialogConfig;
import com.Jfpicker.wheelpicker.wheel_dialog.ModalDialog;

/**
 * @author Created by JF on  2021/11/11
 * @description 年月日选择器
 */

public class YearMonthDayPicker extends ModalDialog {

    protected YearMonthDayWheelLayout wheelLayout;
    private OnDatePickedListener onDatePickedListener;

    public YearMonthDayPicker(@NonNull Activity activity) {
        super(activity);
    }

    public YearMonthDayPicker(@NonNull Activity activity, DialogConfig dialogConfig) {
        super(activity, dialogConfig);
    }

    public YearMonthDayPicker(@NonNull Activity activity, @StyleRes int themeResId) {
        super(activity, themeResId);
    }

    @NonNull
    @Override
    protected View createBodyView() {
        wheelLayout = new YearMonthDayWheelLayout(activity);
        return wheelLayout;
    }

    @Override
    protected void initView() {
        super.initView();
        if (titleView != null) {
            titleView.setText("日期选择");
        }
    }

    @Override
    protected void onCancel() {

    }

    @Override
    protected void onOk() {
        if (onDatePickedListener != null) {
            int year = wheelLayout.getSelectYear();
            int month = wheelLayout.getSelectMonth();
            int day = wheelLayout.getSelectDay();
            onDatePickedListener.onDatePicked(year, month, day);
        }
    }

    public void setOnDatePickedListener(OnDatePickedListener onDatePickedListener) {
        this.onDatePickedListener = onDatePickedListener;
    }

    public final YearMonthDayWheelLayout getWheelLayout() {
        return wheelLayout;
    }
}