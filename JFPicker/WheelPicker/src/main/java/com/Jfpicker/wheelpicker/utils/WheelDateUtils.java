package com.Jfpicker.wheelpicker.utils;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * @author Created by JF on  2021/11/9
 * @description
 */

public class WheelDateUtils {

    //根据年月计算 本月有多少天
    public static int calculateDaysInMonth(int year, int month) {
        // 添加大小月月份并将其转换为list,方便之后的判断
        String[] bigMonths = {"1", "3", "5", "7", "8", "10", "12"};
        String[] littleMonths = {"4", "6", "9", "11"};
        List<String> bigList = Arrays.asList(bigMonths);
        List<String> littleList = Arrays.asList(littleMonths);
        // 判断大小月及是否闰年,用来确定"日"的数据
        if (bigList.contains(String.valueOf(month))) {
            return 31;
        } else if (littleList.contains(String.valueOf(month))) {
            return 30;
        } else {
            if (year <= 0) {
                return 29;
            }
            // 是否闰年
            if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
                return 29;
            } else {
                return 28;
            }
        }
    }

    //获取某一月的最大天数
    public static int getMaxDaysInMonth(int month) {
        int maxDays = 31;
        switch (month) {
            case 2:
                maxDays = 29;
                break;
            case 4:
            case 6:
            case 9:
            case 11:
                maxDays = 30;
                break;
        }
        return maxDays;

    }

    //获取往后多少年
    public static int getFutureYear(int future) {
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        int currentYear = calendar.get(Calendar.YEAR);
        return currentYear + future;
    }
}
