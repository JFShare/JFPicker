package com.Jfpicker.wheelpicker.picker_calendar.listener;

import com.haibin.calendarview.Calendar;

import java.util.List;

/**
 * @author Created by linyincongxingkeji on  2021/11/24
 * @description 多选日历回调
 */

public interface OnCalendarMultiPickedListener {
    void onCalendarMultiPicked(List<Calendar> list);
}
