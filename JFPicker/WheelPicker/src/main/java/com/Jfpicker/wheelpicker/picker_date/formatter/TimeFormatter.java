package com.Jfpicker.wheelpicker.picker_date.formatter;

/**
 * @author Created by JF on  2021/11/12
 * @description 时分秒格式化
 */

public interface TimeFormatter {

    String formatHour(int hour);

    String formatMinute(int minute);

    String formatSecond(int second);

}
