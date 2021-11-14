
package com.Jfpicker.wheelpicker.picker_date.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


@Retention(RetentionPolicy.SOURCE)
public @interface DateMode {
    /**
     * 不显示
     */
    int NONE = -1;
    /**
     * 年月日
     */
    int YEAR_MONTH_DAY = 0;
    /**
     * 年月
     */
    int YEAR_MONTH = 1;
    /**
     * 月日
     */
    int MONTH_DAY = 2;
}
