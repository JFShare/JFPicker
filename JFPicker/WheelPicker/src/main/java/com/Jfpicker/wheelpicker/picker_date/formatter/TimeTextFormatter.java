package com.Jfpicker.wheelpicker.picker_date.formatter;

/**
 * @author Created by JF on  2021/11/12 15:15
 * @description
 */

public class TimeTextFormatter extends TimeFillZeroFormatter {
    @Override
    public String formatHour(int hour) {
        return super.formatHour(hour) + "时";
    }

    @Override
    public String formatMinute(int minute) {
        return super.formatMinute(minute) + "分";
    }

    @Override
    public String formatSecond(int second) {
        return super.formatSecond(second) + "秒";
    }

}
