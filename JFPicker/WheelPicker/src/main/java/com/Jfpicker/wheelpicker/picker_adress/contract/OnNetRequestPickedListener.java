package com.Jfpicker.wheelpicker.picker_adress.contract;

import com.Jfpicker.wheelpicker.picker_option.entity.OptionEntity;

/**
 * @author Created by JF on  2021/11/15
 * @description
 */

public interface OnNetRequestPickedListener {
    void onPicked(OptionEntity firstData, OptionEntity secondData,
                  OptionEntity thirdDate, OptionEntity fourthData, OptionEntity fifthData);
}
