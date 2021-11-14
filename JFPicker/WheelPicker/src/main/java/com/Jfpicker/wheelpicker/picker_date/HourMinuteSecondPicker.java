package com.Jfpicker.wheelpicker.picker_date;

import android.app.Activity;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.StyleRes;

import com.Jfpicker.wheelpicker.picker_date.listener.OnTimePickedListener;
import com.Jfpicker.wheelpicker.picker_date.widget.HourMinuteSecondWheelLayout;
import com.Jfpicker.wheelpicker.wheel_dialog.ModalDialog;

/**
 * @author Created by JF on  2021/11/12 8:58
 * @description
 */

public class HourMinuteSecondPicker extends ModalDialog {

    HourMinuteSecondWheelLayout wheelLayout;
    OnTimePickedListener onTimePickedListener;
    public HourMinuteSecondPicker(@NonNull Activity activity) {
        super(activity);
    }

    public HourMinuteSecondPicker(@NonNull Activity activity, @StyleRes int themeResId) {
        super(activity, themeResId);
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
        titleView.setText("时间选择");
    }

    @Override
    protected void onCancel() {

    }

    @Override
    protected void onOk() {
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