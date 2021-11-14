
package com.Jfpicker.wheelpicker.picker_adress.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


@Retention(RetentionPolicy.SOURCE)
public @interface AddressMode {
    /**
     * 省级、市级、县级
     */
    int PROVINCE_CITY_COUNTY = 0;
    /**
     * 省级、市级
     */
    int PROVINCE_CITY = 1;
    /**
     * 市级、县级
     */
    int CITY_COUNTY = 2;
}
