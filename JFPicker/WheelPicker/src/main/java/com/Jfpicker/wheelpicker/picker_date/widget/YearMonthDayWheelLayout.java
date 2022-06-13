package com.Jfpicker.wheelpicker.picker_date.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.Jfpicker.wheelpicker.picker_date.formatter.FillZeroFormatter;
import com.Jfpicker.wheelpicker.utils.WheelDateUtils;
import com.Jfpicker.wheelpicker.R;
import com.Jfpicker.wheelpicker.wheelview.WheelDataAdapter;
import com.Jfpicker.wheelpicker.wheelview.format.WheelFormatListener;
import com.Jfpicker.wheelpicker.picker_date.annotation.DateMode;

import com.Jfpicker.wheelpicker.wheelview.WheelAttrs;
import com.Jfpicker.wheelpicker.wheelview.WheelView;
import com.Jfpicker.wheelpicker.wheelview.listener.OnWheelScrollListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * @author Created by JF on  2021/11/11
 * @description 年月日选择布局
 */

public class YearMonthDayWheelLayout extends LinearLayout {
    public static final int default_start_year = 1900;
    public static final int default_idle_date = -1;
    private WheelView wheelViewYear;
    private WheelView wheelViewMonth;
    private WheelView wheelViewDay;

    private TextView tvYearLabel;
    private TextView tvMonthLabel;
    private TextView tvDayLabel;

    private WheelDataAdapter adapterYear;
    private WheelDataAdapter adapterMonth;
    private WheelDataAdapter adapterDay;

    private int indexYearChoose, indexMonthChoose, indexDayChoose;

    private int dateMode = DateMode.YEAR_MONTH_DAY;
    private boolean hasInitView = false;

    private OnWheelScrollListener onWheelScrollListenerYear;
    private OnWheelScrollListener onWheelScrollListenerMonth;
    private OnWheelScrollListener onWheelScrollListenerDay;

    public YearMonthDayWheelLayout(Context context) {
        super(context);
        init(context);
    }

    public YearMonthDayWheelLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public YearMonthDayWheelLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public YearMonthDayWheelLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        setOrientation(VERTICAL);
        inflate(context, R.layout.wheel_picker_date, this);
        onInit();
    }

    private void onInit() {

        wheelViewYear = findViewById(R.id.wheelViewYear);
        wheelViewMonth = findViewById(R.id.wheelViewMonth);
        wheelViewDay = findViewById(R.id.wheelViewDay);

        tvYearLabel = findViewById(R.id.tvYearLabel);
        tvMonthLabel = findViewById(R.id.tvMonthLabel);
        tvDayLabel = findViewById(R.id.tvDayLabel);

        setLabelText("年", "月", "日");

        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH) + 1;
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
        setDateMode(dateMode);
        initWheelView(default_start_year, WheelDateUtils.getFutureYear(100),
                currentYear, currentMonth, currentDay);

    }

    public void initWheelView(int startYear, int endYear) {
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH) + 1;
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
        this.initWheelView(startYear, endYear, currentYear, currentMonth, currentDay);
    }

    /**
     * @param startYear    最小年份
     * @param endYear      最大年份
     * @param currentYear  选中年
     * @param currentMonth 选中月
     * @param currentDay   选中日
     */
    public void initWheelView(int startYear, int endYear,
                              int currentYear, int currentMonth,
                              int currentDay) {
        setFormatter(new FillZeroFormatter());

        //初始化年滚轮
        adapterYear = new WheelDataAdapter();
        adapterYear.objects.clear();
        for (int i = startYear; i <= endYear; i++) {
            adapterYear.objects.add(i);
            if (i == currentYear) {
                indexYearChoose = i - startYear;
            }
        }
        wheelViewYear.setOnWheelScrollListener(new OnWheelScrollListener() {
            @Override
            public void onItemChecked(WheelView wv, int index) {
                indexYearChoose = index;
                reloadDayData();
                if (onWheelScrollListenerYear != null) {
                    onWheelScrollListenerYear.onItemChecked(wv, index);
                }
            }

            @Override
            public void onScrollStatusChange(boolean scrolling) {
                wheelViewMonth.setEnabled(!scrolling);
                wheelViewDay.setEnabled(!scrolling);
                if (onWheelScrollListenerYear != null) {
                    onWheelScrollListenerYear.onScrollStatusChange(scrolling);
                }
            }

        });
        wheelViewYear.setAdapter(adapterYear);
        wheelViewYear.setCurrentItem(indexYearChoose);

        //初始化月滚轮
        adapterMonth = new WheelDataAdapter();
        adapterMonth.objects.clear();
        for (int i = 1; i <= 12; i++) {
            adapterMonth.objects.add(i);
            if (i == currentMonth) {
                indexMonthChoose = i - 1;
            }
        }
        wheelViewMonth.setOnWheelScrollListener(new OnWheelScrollListener() {
            @Override
            public void onItemChecked(WheelView wv, int index) {
                indexMonthChoose = index;
                reloadDayData();
                if (onWheelScrollListenerMonth != null) {
                    onWheelScrollListenerMonth.onItemChecked(wv, index);
                }
            }

            @Override
            public void onScrollStatusChange(boolean scrolling) {
                wheelViewYear.setEnabled(!scrolling);
                wheelViewDay.setEnabled(!scrolling);
                if (onWheelScrollListenerMonth != null) {
                    onWheelScrollListenerMonth.onScrollStatusChange(scrolling);
                }
            }
        });
        wheelViewMonth.setAdapter(adapterMonth);
        wheelViewMonth.setCurrentItem(indexMonthChoose);

        //初始化日滚轮
        adapterDay = new WheelDataAdapter();
        adapterDay.objects.clear();
        int CurrentDays = WheelDateUtils.calculateDaysInMonth(currentYear, currentMonth);
        for (int i = 1; i <= CurrentDays; i++) {
            adapterDay.objects.add(i);
            if (i == currentDay) {
                indexDayChoose = i - 1;
            }
        }
        wheelViewDay.setOnWheelScrollListener(new OnWheelScrollListener() {
            @Override
            public void onItemChecked(WheelView wv, int index) {
                indexDayChoose = index;
                if (onWheelScrollListenerDay != null) {
                    onWheelScrollListenerDay.onItemChecked(wv, index);
                }
            }

            @Override
            public void onScrollStatusChange(boolean scrolling) {
                wheelViewYear.setEnabled(!scrolling);
                wheelViewMonth.setEnabled(!scrolling);
                if (onWheelScrollListenerDay != null) {
                    onWheelScrollListenerDay.onScrollStatusChange(scrolling);
                }
            }
        });
        wheelViewDay.setAdapter(adapterDay);
        wheelViewDay.setCurrentItem(indexDayChoose);

        hasInitView = true;
    }

    //设置显示格式化
    public void setFormatter(WheelFormatListener formatter) {
        wheelViewYear.setFormatter(formatter);
        wheelViewMonth.setFormatter(formatter);
        wheelViewDay.setFormatter(formatter);
    }

    public void setFormatter(WheelFormatListener formatterYear, WheelFormatListener formatterMonth,
                             WheelFormatListener formatterDay) {
        wheelViewYear.setFormatter(formatterYear);
        wheelViewMonth.setFormatter(formatterMonth);
        wheelViewDay.setFormatter(formatterDay);
    }

    //设置显示模式
    public void setDateMode(@DateMode int dateMode) {

        this.dateMode = dateMode;
        wheelViewYear.setVisibility(VISIBLE);
        tvYearLabel.setVisibility(VISIBLE);
        wheelViewMonth.setVisibility(VISIBLE);
        tvMonthLabel.setVisibility(VISIBLE);
        wheelViewDay.setVisibility(VISIBLE);
        tvDayLabel.setVisibility(VISIBLE);

        if (dateMode == DateMode.NONE) {
            wheelViewYear.setVisibility(GONE);
            tvYearLabel.setVisibility(GONE);
            wheelViewMonth.setVisibility(GONE);
            tvMonthLabel.setVisibility(GONE);
            wheelViewDay.setVisibility(GONE);
            tvDayLabel.setVisibility(GONE);
            return;
        }
        if (dateMode == DateMode.MONTH_DAY) {
            wheelViewYear.setVisibility(GONE);
            tvYearLabel.setVisibility(GONE);
            return;
        }
        if (dateMode == DateMode.YEAR_MONTH) {
            wheelViewDay.setVisibility(GONE);
            tvDayLabel.setVisibility(GONE);
        }

        if (hasInitView) {
            reloadDayData();
        }

    }

    //年月改变、显示模式改变以后，需要重新计算天数
    public void reloadDayData() {
        if (dateMode == DateMode.MONTH_DAY) {
            //需要根据年份及月份动态计算天数，这里因为是月日显示模式，2月是29天
            List<Integer> dayList = new ArrayList<>();
            int maxDays = WheelDateUtils.getMaxDaysInMonth(
                    (Integer) adapterMonth.objects.get(wheelViewMonth.getCurrentItem()));
            for (int i = 1; i <= maxDays; i++) {
                dayList.add(i);
            }
            if (indexDayChoose >= maxDays - 1) {
                //年或月变动时，保持之前选择的日不动：如果之前选择的日是之前年月的最大日，则日自动为该年月的最大日
                indexDayChoose = maxDays - 1;
            }
            adapterDay.objects.clear();
            adapterDay.objects.addAll(dayList);
            adapterDay.notifyDataSetChanged();
            wheelViewDay.setCurrentItem(indexDayChoose);
        } else if (dateMode == DateMode.YEAR_MONTH_DAY) {
            //需要根据年份及月份动态计算天数
            List<Integer> dayList = new ArrayList<>();
            int maxDays = WheelDateUtils.calculateDaysInMonth((Integer) adapterYear.objects.get(wheelViewYear.getCurrentItem()),
                    (Integer) adapterMonth.objects.get(wheelViewMonth.getCurrentItem()));
            for (int i = 1; i <= maxDays; i++) {
                dayList.add(i);
            }
            if (indexDayChoose >= maxDays - 1) {
                //年或月变动时，保持之前选择的日不动：如果之前选择的日是之前年月的最大日，则日自动为该年月的最大日
                indexDayChoose = maxDays - 1;
            }
            adapterDay.objects.clear();
            adapterDay.objects.addAll(dayList);
            adapterDay.notifyDataSetChanged();
            wheelViewDay.setCurrentItem(indexDayChoose);
        }
    }

    public void setAllWheelViewAttrs(WheelAttrs attrs) {
        wheelViewYear.setAttrs(attrs);
        wheelViewMonth.setAttrs(attrs);
        wheelViewDay.setAttrs(attrs);
    }

    public WheelView getWheelViewYear() {
        return wheelViewYear;
    }

    public int getSelectYear() {
        if (adapterYear.objects.size() == 0) {
            return default_idle_date;
        }
        if (dateMode == DateMode.YEAR_MONTH_DAY || dateMode == DateMode.YEAR_MONTH) {
            return (Integer) adapterYear.objects.get(wheelViewYear.getCurrentItem());
        } else {
            return default_idle_date;
        }
    }

    public WheelView getWheelViewMonth() {
        return wheelViewMonth;
    }

    public int getSelectMonth() {
        if (adapterMonth.objects.size() == 0) {
            return default_idle_date;
        }
        if (dateMode == DateMode.NONE) {
            return default_idle_date;
        } else {
            return (Integer) adapterMonth.objects.get(wheelViewMonth.getCurrentItem());
        }

    }


    public WheelView getWheelViewDay() {
        return wheelViewDay;
    }


    public int getSelectDay() {
        if (adapterDay.objects.size() == 0) {
            return default_idle_date;
        }
        if (dateMode == DateMode.YEAR_MONTH_DAY || dateMode == DateMode.MONTH_DAY) {
            return (Integer) adapterDay.objects.get(wheelViewDay.getCurrentItem());
        } else {
            return default_idle_date;
        }
    }

    public void setLabelVisibility(int visibility) {
        tvYearLabel.setVisibility(visibility);
        tvMonthLabel.setVisibility(visibility);
        tvDayLabel.setVisibility(visibility);
    }

    public void setLabelVisibility(int yearLabelVisibility, int monthLabelVisibility, int dayLabelVisibility) {
        tvYearLabel.setVisibility(yearLabelVisibility);
        tvMonthLabel.setVisibility(monthLabelVisibility);
        tvDayLabel.setVisibility(dayLabelVisibility);
    }

    public void setLabelText(String yearLabel, String monthLabel, String dayLabel) {
        tvYearLabel.setText(yearLabel);
        tvMonthLabel.setText(monthLabel);
        tvDayLabel.setText(dayLabel);
    }

    public void setLabelTextColor(int labelTextColor) {
        tvYearLabel.setTextColor(labelTextColor);
        tvMonthLabel.setTextColor(labelTextColor);
        tvDayLabel.setTextColor(labelTextColor);
    }

    public void setLabelTextColor(int yearLabelTextColor, int monthLabelTextColor, int dayLabelTextColor) {
        tvYearLabel.setTextColor(yearLabelTextColor);
        tvMonthLabel.setTextColor(monthLabelTextColor);
        tvDayLabel.setTextColor(dayLabelTextColor);
    }

    public TextView getTvYearLabel() {
        return tvYearLabel;
    }

    public TextView getTvMonthLabel() {
        return tvMonthLabel;
    }

    public TextView getTvDayLabel() {
        return tvDayLabel;
    }

    public void setOnWheelScrollListener(OnWheelScrollListener listener) {
        onWheelScrollListenerYear = listener;
        onWheelScrollListenerMonth = listener;
        onWheelScrollListenerDay = listener;
    }

    public void setOnWheelScrollListener(OnWheelScrollListener onWheelScrollListenerYear, OnWheelScrollListener onWheelScrollListenerMonth,
                                         OnWheelScrollListener onWheelScrollListenerDay) {
        this.onWheelScrollListenerYear = onWheelScrollListenerYear;
        this.onWheelScrollListenerMonth = onWheelScrollListenerMonth;
        this.onWheelScrollListenerDay = onWheelScrollListenerDay;
    }
}
