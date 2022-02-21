package com.Jfpicker.wheelpicker.picker_date.formatter;

/**
 * @author Created by JF on  2021/11/12
 * @description 年月日格式化
 */

public interface DateFormatter {

    String formatYear(int year);

    String formatMonth(int month);

    String formatDay(int day);
}
