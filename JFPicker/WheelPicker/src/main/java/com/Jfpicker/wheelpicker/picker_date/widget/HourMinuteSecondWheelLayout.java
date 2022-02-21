package com.Jfpicker.wheelpicker.picker_date.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.Jfpicker.wheelpicker.R;
import com.Jfpicker.wheelpicker.picker_base.WheelDataAdapter;
import com.Jfpicker.wheelpicker.picker_base.WheelFormatter;
import com.Jfpicker.wheelpicker.picker_date.annotation.TimeMode;

import com.Jfpicker.wheelpicker.picker_date.formatter.TimeFillZeroFormatter;
import com.Jfpicker.wheelpicker.picker_date.formatter.TimeFormatter;
import com.Jfpicker.wheelpicker.wheelview.WheelAttrs;
import com.Jfpicker.wheelpicker.wheelview.WheelView;

import java.util.Calendar;
import java.util.Locale;

/**
 * @author Created by JF on  2021/11/12 8:26
 * @description 时分秒选择布局
 */

public class HourMinuteSecondWheelLayout extends LinearLayout {


    private WheelView wheelViewHour;
    private WheelView wheelViewMinute;
    private WheelView wheelViewSecond;

    private TextView tvHourLabel;
    private TextView tvMinuteLabel;
    private TextView tvSecondLabel;


    private WheelDataAdapter adapterHour;
    private WheelDataAdapter adapterMinute;
    private WheelDataAdapter adapterSecond;

    private int indexHourChoose, indexMinuteChoose, indexSecondChoose;

    private int timeMode = TimeMode.HOUR_24;

    private TimeFormatter formatter = new TimeFillZeroFormatter();

    //设置显示格式化
    public void setFormatter(TimeFormatter formatter) {
        this.formatter = formatter;
        wheelViewHour.setFormatter(new WheelFormatter() {
            @Override
            public String formatItem(@NonNull Object item) {
                return formatter.formatHour((int) item);
            }
        });
        wheelViewMinute.setFormatter(new WheelFormatter() {
            @Override
            public String formatItem(@NonNull Object item) {
                return formatter.formatMinute((int) item);
            }
        });
        wheelViewSecond.setFormatter(new WheelFormatter() {
            @Override
            public String formatItem(@NonNull Object item) {
                return formatter.formatSecond((int) item);
            }
        });
    }


    public HourMinuteSecondWheelLayout(Context context) {
        super(context);
        init(context, null);
    }

    public HourMinuteSecondWheelLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public HourMinuteSecondWheelLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public HourMinuteSecondWheelLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        setOrientation(VERTICAL);
        inflate(context, R.layout.wheel_picker_time, this);
        onInit(context);
    }

    private void onInit(@NonNull Context context) {

        wheelViewHour = findViewById(R.id.wheelViewHour);
        wheelViewMinute = findViewById(R.id.wheelViewMinute);
        wheelViewSecond = findViewById(R.id.wheelViewSecond);

        tvHourLabel = findViewById(R.id.tvHourLabel);
        tvMinuteLabel = findViewById(R.id.tvMinuteLabel);
        tvSecondLabel = findViewById(R.id.tvSecondLabel);

        setLabelText("时", "分", "秒");

        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        int currentMiute = calendar.get(Calendar.MINUTE);
        int currentSecond = calendar.get(Calendar.SECOND);
        setTimeMode(timeMode);
        setFormatter(formatter);
        initWheelView(currentHour, currentMiute, currentSecond);

    }

    public void initWheelView(int currentHour, int currentMiute, int currentSecond) {

        //初始化时滚轮
        adapterHour = new WheelDataAdapter();
        adapterHour.strs.clear();
        for (int i = 0; i <= 23; i++) {
            adapterHour.strs.add(i);
            if (i == currentHour) {
                indexHourChoose = i;
            }
        }
        wheelViewHour.setOnItemSelectedListener(new WheelView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(WheelView wv, int index) {
                indexHourChoose = index;
            }

            @Override
            public void onScrollStatusChange(boolean scrolling) {
//                wheelViewMinute.setEnabled(!scrolling);
//                wheelViewSecond.setEnabled(!scrolling);
            }

        });
        wheelViewHour.setAdapter(adapterHour);
        wheelViewHour.setCurrentItem(indexHourChoose);

        //初始化分滚轮
        adapterMinute = new WheelDataAdapter();
        adapterMinute.strs.clear();
        for (int i = 0; i <= 59; i++) {
            adapterMinute.strs.add(i);
            if (i == currentMiute) {
                indexMinuteChoose = i;
            }
        }

        wheelViewMinute.setOnItemSelectedListener(new WheelView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(WheelView wv, int index) {
                indexMinuteChoose = index;
            }

            @Override
            public void onScrollStatusChange(boolean scrolling) {
//                wheelViewHour.setEnabled(!scrolling);
//                wheelViewSecond.setEnabled(!scrolling);
            }
        });
        wheelViewMinute.setAdapter(adapterMinute);
        wheelViewMinute.setCurrentItem(indexMinuteChoose);

        //初始化秒滚轮
        adapterSecond = new WheelDataAdapter();
        adapterSecond.strs.clear();

        for (int i = 1; i <= 59; i++) {
            adapterSecond.strs.add(i);
            if (i == currentSecond) {
                indexSecondChoose = i;
            }
        }
        wheelViewSecond.setOnItemSelectedListener(new WheelView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(WheelView wv, int index) {
                indexSecondChoose = index;
            }

            @Override
            public void onScrollStatusChange(boolean scrolling) {
                wheelViewHour.setEnabled(!scrolling);
                wheelViewMinute.setEnabled(!scrolling);
            }

        });
        wheelViewSecond.setAdapter(adapterSecond);
        wheelViewSecond.setCurrentItem(indexSecondChoose);


    }

    //设置显示模式
    public void setTimeMode(@TimeMode int timeMode) {

        this.timeMode = timeMode;
        wheelViewHour.setVisibility(VISIBLE);
        tvHourLabel.setVisibility(VISIBLE);
        wheelViewMinute.setVisibility(VISIBLE);
        tvMinuteLabel.setVisibility(VISIBLE);
        wheelViewSecond.setVisibility(VISIBLE);
        tvSecondLabel.setVisibility(VISIBLE);

        if (timeMode == TimeMode.NONE) {
            wheelViewHour.setVisibility(GONE);
            tvHourLabel.setVisibility(GONE);
            wheelViewMinute.setVisibility(GONE);
            tvMinuteLabel.setVisibility(GONE);
            wheelViewSecond.setVisibility(GONE);
            tvSecondLabel.setVisibility(GONE);
            return;
        }
        if (timeMode == TimeMode.HOUR_24_NO_SECOND) {
            wheelViewSecond.setVisibility(GONE);
            tvSecondLabel.setVisibility(GONE);
            return;
        }
        if (timeMode == TimeMode.HOUR_24_NO_HOUR) {
            wheelViewHour.setVisibility(GONE);
            tvHourLabel.setVisibility(GONE);
            return;
        }

    }

    public WheelView getWheelViewHour() {
        return wheelViewHour;
    }

    public int getSelectHour() {
        if (timeMode == TimeMode.HOUR_24 || timeMode == TimeMode.HOUR_24_NO_SECOND) {
            return (int) adapterHour.strs.get(wheelViewHour.getCurrentItem());
        } else {
            return -1;
        }
    }


    public WheelView getWheelViewMinute() {
        return wheelViewMinute;
    }

    public int getSelectMinute() {
        if (timeMode == TimeMode.HOUR_24 || timeMode == TimeMode.HOUR_24_NO_SECOND
                || timeMode == TimeMode.HOUR_24_NO_HOUR) {
            return (int) adapterMinute.strs.get(wheelViewMinute.getCurrentItem());
        } else {
            return -1;
        }

    }

    public WheelView getWheelViewSecond() {
        return wheelViewSecond;
    }

    public int getSelectSecond() {
        if (timeMode == TimeMode.HOUR_24 || timeMode == TimeMode.HOUR_24_NO_HOUR) {
            return (int) adapterSecond.strs.get(wheelViewSecond.getCurrentItem());
        } else {
            return -1;
        }
    }

    public void setAllWheelViewAttrs(WheelAttrs attrs) {
        wheelViewHour.setWheelAttrs(attrs);
        wheelViewMinute.setWheelAttrs(attrs);
        wheelViewSecond.setWheelAttrs(attrs);
    }


    public void setLabelTextColor(int labelTextColor) {
        tvHourLabel.setTextColor(labelTextColor);
        tvMinuteLabel.setTextColor(labelTextColor);
        tvSecondLabel.setTextColor(labelTextColor);
    }

    public void setLabelTextColor(int hourLabelTextColor, int minuteLabelTextColor, int secondLabelTextColor) {
        tvHourLabel.setTextColor(hourLabelTextColor);
        tvMinuteLabel.setTextColor(minuteLabelTextColor);
        tvSecondLabel.setTextColor(secondLabelTextColor);
    }

    public void setLabelVisibility(int visibility) {
        tvHourLabel.setVisibility(visibility);
        tvMinuteLabel.setVisibility(visibility);
        tvSecondLabel.setVisibility(visibility);
    }

    public void setLabelVisibility(int yearLabelVisibility, int monthLabelVisibility, int dayLabelVisibility) {
        tvHourLabel.setVisibility(yearLabelVisibility);
        tvMinuteLabel.setVisibility(monthLabelVisibility);
        tvSecondLabel.setVisibility(dayLabelVisibility);
    }

    public void setLabelText(String hourLabel, String minuteLabel, String secondLabel) {
        tvHourLabel.setText(hourLabel);
        tvMinuteLabel.setText(minuteLabel);
        tvSecondLabel.setText(secondLabel);
    }

    public TextView getTvHourLabel() {
        return tvHourLabel;
    }

    public TextView getTvMinuteLabel() {
        return tvMinuteLabel;
    }

    public TextView getTvSecondLabel() {
        return tvSecondLabel;
    }


}
