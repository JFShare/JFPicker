package com.Jfpicker.wheelpicker.picker_date.formatter;

/**
 * @author Created by JF on  2021/11/12 15:13
 * @description
 */

public class TimeFillZeroFormatter implements TimeFormatter {
    @Override
    public String formatHour(int hour) {
        return hour < 10 ? "0" + hour : hour + "";
    }

    @Override
    public String formatMinute(int minute) {
        return minute < 10 ? "0" + minute : minute + "";
    }

    @Override
    public String formatSecond(int second) {
        return second < 10 ? "0" + second : second + "";
    }
}
