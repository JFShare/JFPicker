
package com.Jfpicker.wheelpicker.picker_calendar.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


@Retention(RetentionPolicy.SOURCE)
public @interface CalendarMode {
    /**
     * 单选
     */
    int SINGLE = 0;
    /**
     * 多选
     */
    int MULTI = 1;
    /**
     * 范围
     */
    int RANGE = 2;
}
