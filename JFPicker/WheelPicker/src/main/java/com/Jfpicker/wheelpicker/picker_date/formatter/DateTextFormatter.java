package com.Jfpicker.wheelpicker.picker_date.formatter;

/**
 * @author Created by JF on  2021/11/12
 * @description 年月日增加后缀文字
 */

public class DateTextFormatter extends DateFillZeroFormatter {


    @Override
    public String formatYear(int year) {
        return super.formatYear(year) + "年";
    }

    @Override
    public String formatMonth(int month) {
        return super.formatMonth(month) + "月";
    }

    @Override
    public String formatDay(int day) {
        return super.formatDay(day) + "日";
    }
}
