package com.Jfpicker.wheelpicker.picker_date.formatter;

/**
 * @author Created by JF on  2021/11/12 15:01
 * @description
 */

public class DateFillZeroFormatter implements DateFormatter{
    @Override
    public String formatYear(int year) {
        return year + "";
    }

    @Override
    public String formatMonth(int month) {
        return month < 10 ? "0" + month  :  month + "";
    }

    @Override
    public String formatDay(int day) {
        return day < 10 ? "0" + day  : day + "";
    }
}
