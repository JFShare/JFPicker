package com.Jfpicker.wheelpicker.picker_calendar;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StyleRes;

import com.Jfpicker.wheelpicker.R;
import com.Jfpicker.wheelpicker.dialog.BasePopupWindow;
import com.Jfpicker.wheelpicker.picker_calendar.listener.OnCalendarMultiPickedListener;
import com.Jfpicker.wheelpicker.picker_calendar.listener.OnCalendarPickedListener;
import com.Jfpicker.wheelpicker.picker_calendar.listener.OnCalendarRangePickedListener;
import com.Jfpicker.wheelpicker.picker_calendar.listener.OnCalendarTitleListener;
import com.Jfpicker.wheelpicker.dialog.config.DialogConfig;
import com.Jfpicker.wheelpicker.dialog.ModalDialog;
import com.Jfpicker.wheelpicker.picker_date.YearMonthDayPicker;
import com.Jfpicker.wheelpicker.picker_date.annotation.DateMode;
import com.Jfpicker.wheelpicker.picker_date.widget.YearMonthDayWheelLayout;

/**
 * @author Created by JF on  2021/11/24
 * @description 公司样式的日历选择弹窗
 */

public class CalendarPicker extends ModalDialog {


    protected CalendarLayout calendarLayout;
    private BasePopupWindow yearMonthPopup;
    private OnCalendarTitleListener onCalendarTitleListener;

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
                if (onCalendarTitleListener != null) {
                    onCalendarTitleListener.onClose();
                }else {
                    dismiss();
                }
            }

            @Override
            public void onSure() {
                if (onCalendarTitleListener != null) {
                    onCalendarTitleListener.onSure();
                }else {
                    dismiss();
                }
            }

            @Override
            public void onTitleClick(TextView tvTitle) {
                if (onCalendarTitleListener != null) {
                    onCalendarTitleListener.onTitleClick(tvTitle);
                }else {
                    initYearMonthPopup();
                    if (!yearMonthPopup.isShowing()) {
                        yearMonthPopup.showAsDropDown(tvTitle);
                    }
                }

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

    public BasePopupWindow getYearMonthPopup() {
        return yearMonthPopup;
    }

    private void initYearMonthPopup() {
        if (yearMonthPopup == null) {
            yearMonthPopup = new BasePopupWindow(activity, R.layout.popup_down_select_calendar);
            YearMonthDayWheelLayout wheelViewCalendar = yearMonthPopup.getContentView().findViewById(R.id.wheelViewCalendar);
            wheelViewCalendar.setDateMode(DateMode.YEAR_MONTH);
            TextView tvPickerCancel = yearMonthPopup.getContentView().findViewById(R.id.tvPickerCancel);
            TextView tvPickerTitle = yearMonthPopup.getContentView().findViewById(R.id.tvPickerTitle);
            tvPickerTitle.setText("选择年月");
            TextView tvPickerConfirm = yearMonthPopup.getContentView().findViewById(R.id.tvPickerConfirm);
            tvPickerCancel.setOnClickListener(v -> yearMonthPopup.dismiss());
            tvPickerConfirm.setOnClickListener(v -> {
                yearMonthPopup.dismiss();
                calendarLayout.setCalendar(wheelViewCalendar.getSelectYear(), wheelViewCalendar.getSelectMonth(), 1);
            });
        }
    }

    public void setOnCalendarTitleListener(OnCalendarTitleListener listener) {
        onCalendarTitleListener = listener;
    }

}
