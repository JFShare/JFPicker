package com.Jfpicker.wheelpicker.picker_date;

import android.app.Activity;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.StyleRes;

import com.Jfpicker.wheelpicker.utils.DensityUtils;
import com.Jfpicker.wheelpicker.picker_date.listener.OnDateTimePickedListener;
import com.Jfpicker.wheelpicker.picker_date.widget.HourMinuteSecondWheelLayout;
import com.Jfpicker.wheelpicker.picker_date.widget.YearMonthDayWheelLayout;
import com.Jfpicker.wheelpicker.wheel_dialog.DialogConfig;
import com.Jfpicker.wheelpicker.wheel_dialog.ModalDialog;

/**
 * @author Created by JF on  2021/11/12
 * @description 年月日时分秒选择器
 */

public class DateTimePicker extends ModalDialog {

    LinearLayout dateTimeLayout;
    YearMonthDayWheelLayout dateLayout;
    HourMinuteSecondWheelLayout timeLayout;

    OnDateTimePickedListener onDateTimePickedListener;

    public DateTimePicker(@NonNull Activity activity) {
        super(activity);
    }

    public DateTimePicker(@NonNull Activity activity, DialogConfig dialogConfig) {
        super(activity, dialogConfig);
    }

    public DateTimePicker(@NonNull Activity activity, @StyleRes int themeResId) {
        super(activity, themeResId);
    }

    @NonNull
    @Override
    protected View createBodyView() {

        dateTimeLayout = new LinearLayout(activity);
        dateTimeLayout.setOrientation(LinearLayout.HORIZONTAL);

        dateLayout = new YearMonthDayWheelLayout(activity);
        LinearLayout.LayoutParams paramsYear = (LinearLayout.LayoutParams) dateLayout.getWheelViewYear().getLayoutParams();
        paramsYear.weight = 0;
        paramsYear.height = WRAP_CONTENT;
        paramsYear.weight = 1.3F;
        dateLayout.getWheelViewYear().setLayoutParams(paramsYear);

        LinearLayout.LayoutParams paramsMonth = (LinearLayout.LayoutParams) dateLayout.getWheelViewMonth().getLayoutParams();
        paramsMonth.weight = 0;
        paramsMonth.height = WRAP_CONTENT;
        paramsMonth.weight = 1F;
        dateLayout.getWheelViewMonth().setLayoutParams(paramsMonth);

        LinearLayout.LayoutParams paramsDay = (LinearLayout.LayoutParams) dateLayout.getWheelViewDay().getLayoutParams();
        paramsDay.weight = 0;
        paramsDay.height = WRAP_CONTENT;
        paramsDay.weight = 1F;
        dateLayout.getWheelViewDay().setLayoutParams(paramsDay);

        LinearLayout.LayoutParams paramsDate = new LinearLayout.LayoutParams(0, WRAP_CONTENT, 1.3F);
        paramsDate.rightMargin = DensityUtils.dip2px(activity, 5);
        paramsDate.leftMargin = DensityUtils.dip2px(activity, 5);
        dateTimeLayout.addView(dateLayout, paramsDate);


        timeLayout = new HourMinuteSecondWheelLayout(activity);
        LinearLayout.LayoutParams paramsHour = (LinearLayout.LayoutParams) timeLayout.getWheelViewHour().getLayoutParams();
        paramsHour.weight = 0;
        paramsHour.height = WRAP_CONTENT;
        paramsHour.weight = 1F;
        timeLayout.getWheelViewHour().setLayoutParams(paramsHour);

        LinearLayout.LayoutParams paramsMinute = (LinearLayout.LayoutParams) timeLayout.getWheelViewMinute().getLayoutParams();
        paramsMinute.weight = 0;
        paramsMinute.height = WRAP_CONTENT;
        paramsMinute.weight = 1F;
        timeLayout.getWheelViewMinute().setLayoutParams(paramsMinute);

        LinearLayout.LayoutParams paramsSecond = (LinearLayout.LayoutParams) timeLayout.getWheelViewSecond().getLayoutParams();
        paramsSecond.weight = 0;
        paramsSecond.height = WRAP_CONTENT;
        paramsSecond.weight = 1F;
        timeLayout.getWheelViewSecond().setLayoutParams(paramsSecond);

        LinearLayout.LayoutParams paramsTime = new LinearLayout.LayoutParams(0, WRAP_CONTENT, 1F);
        paramsTime.leftMargin = DensityUtils.dip2px(activity, 5);
        paramsTime.rightMargin = DensityUtils.dip2px(activity, 5);
        dateTimeLayout.addView(timeLayout, paramsTime);

        return dateTimeLayout;
    }

    @Override
    protected void initView() {
        super.initView();
        titleView.setText("日期选择");
    }

    @Override
    protected void onCancel() {

    }

    @Override
    protected void onOk() {
        if (onDateTimePickedListener != null) {
            int year = dateLayout.getSelectYear();
            int month = dateLayout.getSelectMonth();
            int day = dateLayout.getSelectDay();

            int hour = timeLayout.getSelectHour();
            int minute = timeLayout.getSelectMinute();
            int second = timeLayout.getSelectSecond();
            onDateTimePickedListener.onDateTimePicked(year, month, day, hour, minute, second);
        }
    }

    public void setOnDateTimePickedListener(OnDateTimePickedListener onDateTimePickedListener) {
        this.onDateTimePickedListener = onDateTimePickedListener;
    }


    public LinearLayout getDateTimeLayout() {
        return dateTimeLayout;
    }

    public YearMonthDayWheelLayout getDateLayout() {
        return dateLayout;
    }

    public HourMinuteSecondWheelLayout getTimeLayout() {
        return timeLayout;
    }


}