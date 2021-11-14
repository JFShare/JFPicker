
package com.Jfpicker.wheelpicker.picker_date.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
public @interface TimeMode {
    /**
     * 不显示
     */
    int NONE = -1;
    /**
     * 24小时制
     */
    int HOUR_24 = 0;

    /**
     * 24小时制（不含秒）
     */
    int HOUR_24_NO_SECOND = 1;

    /**
     * 24小时制（不包括时）
     */
    int HOUR_24_NO_HOUR = 2;
}
