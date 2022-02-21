package com.Jfpicker.sample.custom;

import com.Jfpicker.wheelpicker.picker_date.formatter.DateFillZeroFormatter;


/**
 * @author Created by JF on  2021/11/17
 * @description
 */

public class LovelyBirthdayFormatter extends DateFillZeroFormatter {
    @Override
    public String formatYear(int year) {
        return super.formatYear(year) + " 年";
    }

    @Override
    public String formatMonth(int month) {
        return super.formatMonth(month) + " 月";
    }

    @Override
    public String formatDay(int day) {
        return super.formatDay(day) + " 日";
    }
}
