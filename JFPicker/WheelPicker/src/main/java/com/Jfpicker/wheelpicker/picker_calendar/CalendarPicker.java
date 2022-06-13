package com.Jfpicker.wheelpicker.picker_calendar;

import android.app.Activity;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StyleRes;

import com.Jfpicker.wheelpicker.picker_calendar.listener.OnCalendarMultiPickedListener;
import com.Jfpicker.wheelpicker.picker_calendar.listener.OnCalendarPickedListener;
import com.Jfpicker.wheelpicker.picker_calendar.listener.OnCalendarRangePickedListener;
import com.Jfpicker.wheelpicker.picker_calendar.listener.OnCalendarTitleListener;
import com.Jfpicker.wheelpicker.dialog.config.DialogConfig;
import com.Jfpicker.wheelpicker.dialog.ModalDialog;

/**
 * @author Created by JF on  2021/11/24
 * @description 公司样式的日历选择弹窗
 */

public class CalendarPicker extends ModalDialog {


    protected CalendarLayout calendarLayout;

    public CalendarPicker(@NonNull Activity activity) {
        super(activity);
    }

    public CalendarPicker(@NonNull Activity activity, DialogConfig dialogConfig) {
        super(activity, dialogConfig);
    }

    public CalendarPicker(@NonNull Activity activity, int themeResId) {
        super(activity, themeResId);
    }

    public CalendarPicker(@NonNull Activity activity, DialogConfig dialogConfig, @StyleRes int themeResId) {
        super(activity, dialogConfig, themeResId);
    }

    @Nullable
    @Override
    protected View createTopLineView() {
        return null;
    }

    @Nullable
    @Override
    public View createTitleView() {
        return null;
    }

    @Nullable
    @Override
    protected View createHeaderView() {
        return null;
    }

    @Nullable
    @Override
    protected View createFooterView() {
        return null;
    }

    @NonNull
    @Override
    protected View createBodyView() {
        calendarLayout = new CalendarLayout(getContext());
        calendarLayout.setOnCalendarTitleListener(new OnCalendarTitleListener() {
            @Override
            public void onClose() {
                dismiss();
            }

            @Override
            public void onSure() {
                dismiss();
            }
        });

        return calendarLayout;
    }

    @Override
    protected void onCancel() {

    }

    @Override
    protected void onConfirm() {

    }

    public CalendarLayout getCalendarLayout() {
        return calendarLayout;
    }

    public void setSelectSingleMode() {
        calendarLayout.setSelectSingleMode();
    }

    public void setSelectMultiMode(int size) {
        calendarLayout.setSelectMultiMode(size);
    }

    public void setSelectRangeMode() {
        calendarLayout.setSelectRangeMode();
    }

    public void setOnCalendarPickedListener(OnCalendarPickedListener onCalendarPickedListener) {
        calendarLayout.setOnCalendarPickedListener(onCalendarPickedListener);
    }

    public void setOnCalendarMultiPickedListener(OnCalendarMultiPickedListener onCalendarMultiPickedListener) {
        calendarLayout.setOnCalendarMultiPickedListener(onCalendarMultiPickedListener);
    }

    public void setOnCalendarRangePickedListener(OnCalendarRangePickedListener onCalendarRangePickedListener) {
        calendarLayout.setOnCalendarRangePickedListener(onCalendarRangePickedListener);
    }
}
