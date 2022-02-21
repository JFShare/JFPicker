package com.Jfpicker.wheelpicker.picker_calendar.listener;

import com.haibin.calendarview.Calendar;

import java.util.List;

/**
 * @author Created by linyincongxingkeji on  2021/11/25
 * @description 范围选择日历回调
 */

public interface OnCalendarRangePickedListener {
    void onCalendarRangePicked(List<Calendar> list);
}
