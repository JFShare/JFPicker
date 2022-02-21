package com.Jfpicker.sample.custom;

import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.Jfpicker.sample.R;
import com.Jfpicker.wheelpicker.picker_date.BirthdayPicker;
import com.Jfpicker.wheelpicker.picker_date.listener.OnDatePickedListener;
import com.Jfpicker.wheelpicker.wheel_dialog.DialogConfig;
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
    protected View createTopLineView() {
        return null;
    }

    @Nullable
    @Override
    protected View createHeaderView() {
        return LayoutInflater.from(getContext()).inflate(R.layout.lovely_birthday_picker_header_layout, null, false);
    }

    @Nullable
    @Override
    protected View createFooterView() {
        View footerView = LayoutInflater.from(getContext())
                .inflate(R.layout.lovely_birthday_picker_footer_layout, null, false);
        TextView tvCancel = footerView.findViewById(R.id.tvCancel);
        TextView tvSure = footerView.findViewById(R.id.tvSure);
        tvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (onDatePickedListener != null) {
                    int year = wheelLayout.getSelectYear();
                    int month = wheelLayout.getSelectMonth();
                    int day = wheelLayout.getSelectDay();
                    onDatePickedListener.onDatePicked(year, month, day);
                }
            }
        });
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return footerView;
    }

    @Override
    protected void initView() {
        super.initView();
        WheelAttrs attrs = new WheelAttrs();
        attrs.setDividerColor(Color.TRANSPARENT);
        attrs.setItemCount(1);
        attrs.setTextColorCenter(Color.BLACK);
        attrs.setTextColor(Color.parseColor("#333333"));
        attrs.setTextSize(18);
        attrs.setItemSize(44);
        attrs.setDividerSize(50);
        attrs.setItemDegreeTotal(60);
        attrs.setCenterTextBlod(true);
        attrs.setTextBlod(false);
        attrs.setAlphaGradient(false);
        attrs.setTextSizeGradient(true);
        attrs.setMinGradientTextSize(12);
        wheelLayout.setAllWheelViewAttrs(attrs);
        wheelLayout.setFormatter(new LovelyBirthdayFormatter());
    }

    public void setOnDatePickedListener(OnDatePickedListener onDatePickedListener) {
        this.onDatePickedListener = onDatePickedListener;
    }

}