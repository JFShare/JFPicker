package com.Jfpicker.wheelpicker.picker_date.listener;

/**
 * @author Created by JF on  2021/11/12
 * @description 年月日时分秒选择回调
 */

public interface OnDateTimePickedListener {
    void onDateTimePicked(int year, int month, int day,int hour, int minute, int second);
}
