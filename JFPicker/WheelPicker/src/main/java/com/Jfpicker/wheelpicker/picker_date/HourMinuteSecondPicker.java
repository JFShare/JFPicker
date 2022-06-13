package com.Jfpicker.wheelpicker.picker_date;

import android.app.Activity;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.StyleRes;

import com.Jfpicker.wheelpicker.picker_date.listener.OnTimePickedListener;
import com.Jfpicker.wheelpicker.picker_date.widget.HourMinuteSecondWheelLayout;
import com.Jfpicker.wheelpicker.dialog.config.DialogConfig;
import com.Jfpicker.wheelpicker.dialog.ModalDialog;

/**
 * @author Created by JF on  2021/11/12
 * @description 时分秒选择器
 */

public class HourMinuteSecondPicker extends ModalDialog {

    protected HourMinuteSecondWheelLayout wheelLayout;
    private OnTimePickedListener onTimePickedListener;

    public HourMinuteSecondPicker(@NonNull Activity activity) {
        super(activity);
    }

    public HourMinuteSecondPicker(@NonNull Activity activity, DialogConfig dialogConfig) {
        super(activity, dialogConfig);
    }

    public HourMinuteSecondPicker(@NonNull Activity activity, @StyleRes int themeResId) {
        super(activity, themeResId);
    }

    public HourMinuteSecondPicker(@NonNull Activity activity, DialogConfig dialogConfig, @StyleRes int themeResId) {
        super(activity, dialogConfig, themeResId);
    }

    @NonNull
    @Override
    protected View createBodyView() {
        wheelLayout = new HourMinuteSecondWheelLayout(activity);
        return wheelLayout;
    }

    @Override
    protected void initView() {
        super.initView();
        titleTextView.setText("时间选择");
    }

    @Override
    protected void onCancel() {

    }

    @Override
    protected void onConfirm() {
        if (onTimePickedListener != null) {
            int hour = wheelLayout.getSelectHour();
            int minute = wheelLayout.getSelectMinute();
            int second = wheelLayout.getSelectSecond();
            onTimePickedListener.onTimePicked(hour, minute, second);
        }
    }

    public void setOnTimePickedListener(OnTimePickedListener onTimePickedListener) {
        this.onTimePickedListener = onTimePickedListener;
    }

    public final HourMinuteSecondWheelLayout getWheelLayout() {
        return wheelLayout;
    }
}