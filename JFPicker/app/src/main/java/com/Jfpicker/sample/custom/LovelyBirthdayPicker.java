package com.Jfpicker.sample.custom;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.Jfpicker.sample.R;
import com.Jfpicker.wheelpicker.picker_date.BirthdayPicker;
import com.Jfpicker.wheelpicker.picker_date.listener.OnDatePickedListener;
import com.Jfpicker.wheelpicker.dialog.config.DialogConfig;
import com.Jfpicker.wheelpicker.wheelview.WheelAttrs;

/**
 * @author Created by JF on  2021/11/17
 * @description
 */

public class LovelyBirthdayPicker extends BirthdayPicker {
    private OnDatePickedListener onDatePickedListener;

    public LovelyBirthdayPicker(@NonNull Activity activity) {
        super(activity);
    }

    public LovelyBirthdayPicker(@NonNull Activity activity, DialogConfig dialogConfig) {
        super(activity, dialogConfig);
    }

    public LovelyBirthdayPicker(@NonNull Activity activity, int themeResId) {
        super(activity, themeResId);
    }

    @Nullable
    @Override
    public View createTitleView() {
        return LayoutInflater.from(getContext()).inflate(R.layout.lovely_birthday_picker_header_layout, null, false);
    }

    @Nullable
    @Override
    protected View createTopLineView() {
        return null;
    }


    @Nullable
    @Override
    protected View createFooterView() {
        View footerView = LayoutInflater.from(getContext())
                .inflate(R.layout.lovely_birthday_picker_footer_layout, null, false);
        TextView tvCancel = footerView.findViewById(R.id.tvCancel);
        TextView tvSure = footerView.findViewById(R.id.tvSure);
        tvSure.setOnClickListener(v -> {
            dismiss();
            if (onDatePickedListener != null) {
                int year = wheelLayout.getSelectYear();
                int month = wheelLayout.getSelectMonth();
                int day = wheelLayout.getSelectDay();
                onDatePickedListener.onDatePicked(year, month, day);
            }
        });
        tvCancel.setOnClickListener(v -> dismiss());
        return footerView;
    }

    @Override
    protected void initView() {
        super.initView();
        WheelAttrs attrs = new WheelAttrs();
        attrs.setDividerColor(Color.TRANSPARENT);
        attrs.setCheckedTextColor(Color.BLACK);
        attrs.setTextColor(Color.parseColor("#333333"));
        attrs.setTextSize(18);
        attrs.setItemSize(100);
        attrs.setDividerSize(100);
        attrs.setItemDegreeTotal(60);
        attrs.setCheckedTextBold(true);
        attrs.setTextBold(false);
        attrs.setHalfItemCount(1);
        attrs.setTextSizeFormat((standardSize, position) ->
                standardSize - Math.abs(position));
        wheelLayout.setAllWheelViewAttrs(attrs);

    }

    public void setOnDatePickedListener(OnDatePickedListener onDatePickedListener) {
        this.onDatePickedListener = onDatePickedListener;
    }

}