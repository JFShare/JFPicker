package com.Jfpicker.wheelpicker.picker_date.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.Jfpicker.wheelpicker.R;
import com.Jfpicker.wheelpicker.picker_date.formatter.FillZeroFormatter;
import com.Jfpicker.wheelpicker.wheelview.WheelDataAdapter;
import com.Jfpicker.wheelpicker.wheelview.format.WheelFormatListener;
import com.Jfpicker.wheelpicker.picker_date.annotation.TimeMode;


import com.Jfpicker.wheelpicker.wheelview.WheelAttrs;
import com.Jfpicker.wheelpicker.wheelview.WheelView;
import com.Jfpicker.wheelpicker.wheelview.listener.OnWheelScrollListener;


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

    private int timeMode = TimeMode.HOUR_24;
    public static final int default_idle_time = 0;

    private OnWheelScrollListener onWheelScrollListenerHour;
    private OnWheelScrollListener onWheelScrollListenerMinute;
    private OnWheelScrollListener onWheelScrollListenerSecond;

    public HourMinuteSecondWheelLayout(Context context) {
        super(context);
        init(context);
    }

    public HourMinuteSecondWheelLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public HourMinuteSecondWheelLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public HourMinuteSecondWheelLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        setOrientation(VERTICAL);
        inflate(context, R.layout.jf_wheel_picker_time, this);
        onInit();
    }

    private void onInit() {

        wheelViewHour = findViewById(R.id.wheelViewHour);
        wheelViewMinute = findViewById(R.id.wheelViewMinute);
        wheelViewSecond = findViewById(R.id.wheelViewSecond);

        tvHourLabel = findViewById(R.id.tvHourLabel);
        tvMinuteLabel = findViewById(R.id.tvMinuteLabel);
        tvSecondLabel = findViewById(R.id.tvSecondLabel);

        setLabelText("时", "分", "秒");

        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        int currentMinute = calendar.get(Calendar.MINUTE);
        int currentSecond = calendar.get(Calendar.SECOND);
        setTimeMode(timeMode);
        setFormatter(new FillZeroFormatter());
        initWheelView(currentHour, currentMinute, currentSecond);

    }

    public void initWheelView(int currentHour, int currentMiute, int currentSecond) {

        //初始化时滚轮
        adapterHour = new WheelDataAdapter();
        adapterHour.objects.clear();
        int indexHourChoose = 0;
        for (int i = 0; i <= 23; i++) {
            adapterHour.objects.add(i);
            if (i == currentHour) {
                indexHourChoose = i;
            }
        }
        wheelViewHour.setAdapter(adapterHour);
        wheelViewHour.setOnWheelScrollListener(new OnWheelScrollListener() {
            @Override
            public void onItemChecked(WheelView wheelView, int index) {
                if (onWheelScrollListenerHour != null) {
                    onWheelScrollListenerHour.onItemChecked(wheelView, index);
                }
            }

            @Override
            public void onScrollStatusChange(boolean scrolling) {
                if (onWheelScrollListenerHour != null) {
                    onWheelScrollListenerHour.onScrollStatusChange(scrolling);
                }
            }
        });
        wheelViewHour.setCurrentItem(indexHourChoose);

        //初始化分滚轮
        adapterMinute = new WheelDataAdapter();
        adapterMinute.objects.clear();
        int indexMinuteChoose = 0;
        for (int i = 0; i <= 59; i++) {
            adapterMinute.objects.add(i);
            if (i == currentMiute) {
                indexMinuteChoose = i;
            }
        }

        wheelViewMinute.setAdapter(adapterMinute);
        wheelViewMinute.setOnWheelScrollListener(new OnWheelScrollListener() {
            @Override
            public void onItemChecked(WheelView wheelView, int index) {
                if (onWheelScrollListenerMinute != null) {
                    onWheelScrollListenerMinute.onItemChecked(wheelView, index);
                }
            }

            @Override
            public void onScrollStatusChange(boolean scrolling) {
                if (onWheelScrollListenerMinute != null) {
                    onWheelScrollListenerMinute.onScrollStatusChange(scrolling);
                }
            }
        });
        wheelViewMinute.setCurrentItem(indexMinuteChoose);

        //初始化秒滚轮
        adapterSecond = new WheelDataAdapter();
        adapterSecond.objects.clear();
        int indexSecondChoose = 0;
        for (int i = 0; i <= 59; i++) {
            adapterSecond.objects.add(i);
            if (i == currentSecond) {
                indexSecondChoose = i;
            }
        }
        wheelViewSecond.setAdapter(adapterSecond);
        wheelViewSecond.setOnWheelScrollListener(new OnWheelScrollListener() {
            @Override
            public void onItemChecked(WheelView wheelView, int index) {
                if (onWheelScrollListenerSecond != null) {
                    onWheelScrollListenerSecond.onItemChecked(wheelView, index);
                }
            }

            @Override
            public void onScrollStatusChange(boolean scrolling) {
                if (onWheelScrollListenerSecond != null) {
                    onWheelScrollListenerSecond.onScrollStatusChange(scrolling);
                }
            }
        });
        wheelViewSecond.setCurrentItem(indexSecondChoose);


    }

    //设置显示格式化
    public void setFormatter(WheelFormatListener formatter) {
        wheelViewHour.setFormatter(formatter);
        wheelViewMinute.setFormatter(formatter);
        wheelViewSecond.setFormatter(formatter);
    }

    //设置显示格式化
    public void setFormatter(WheelFormatListener formatterHour, WheelFormatListener formatterMinute,
                             WheelFormatListener formatterSecond) {
        wheelViewHour.setFormatter(formatterHour);
        wheelViewMinute.setFormatter(formatterMinute);
        wheelViewSecond.setFormatter(formatterSecond);
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
        if (adapterHour.objects.size() == 0) {
            return default_idle_time;
        }
        if (timeMode == TimeMode.HOUR_24 || timeMode == TimeMode.HOUR_24_NO_SECOND) {
            return (int) adapterHour.objects.get(wheelViewHour.getCurrentItem());
        } else {
            return default_idle_time;
        }
    }


    public WheelView getWheelViewMinute() {
        return wheelViewMinute;
    }

    public int getSelectMinute() {
        if (adapterMinute.objects.size() == 0) {
            return default_idle_time;
        }
        if (timeMode == TimeMode.HOUR_24 || timeMode == TimeMode.HOUR_24_NO_SECOND
                || timeMode == TimeMode.HOUR_24_NO_HOUR) {
            return (int) adapterMinute.objects.get(wheelViewMinute.getCurrentItem());
        } else {
            return default_idle_time;
        }

    }

    public WheelView getWheelViewSecond() {
        return wheelViewSecond;
    }

    public int getSelectSecond() {
        if (adapterSecond.objects.size() == 0) {
            return default_idle_time;
        }
        if (timeMode == TimeMode.HOUR_24 || timeMode == TimeMode.HOUR_24_NO_HOUR) {
            return (int) adapterSecond.objects.get(wheelViewSecond.getCurrentItem());
        } else {
            return default_idle_time;
        }
    }

    public void setAllWheelViewAttrs(WheelAttrs attrs) {
        wheelViewHour.setAttrs(attrs);
        wheelViewMinute.setAttrs(attrs);
        wheelViewSecond.setAttrs(attrs);
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

    public void setOnWheelScrollListener(OnWheelScrollListener listener) {
        onWheelScrollListenerHour = listener;
        onWheelScrollListenerMinute = listener;
        onWheelScrollListenerSecond = listener;
    }

    public void setOnWheelScrollListener(OnWheelScrollListener onWheelScrollListenerHour, OnWheelScrollListener onWheelScrollListenerMinute,
                                         OnWheelScrollListener onWheelScrollListenerSecond) {
        this.onWheelScrollListenerHour = onWheelScrollListenerHour;
        this.onWheelScrollListenerMinute = onWheelScrollListenerMinute;
        this.onWheelScrollListenerSecond = onWheelScrollListenerSecond;
    }
}
