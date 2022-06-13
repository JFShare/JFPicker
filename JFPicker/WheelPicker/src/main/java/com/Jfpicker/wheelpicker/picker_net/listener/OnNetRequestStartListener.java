package com.Jfpicker.wheelpicker.picker_net.listener;

import com.Jfpicker.wheelpicker.picker_option.entity.OptionEntity;

/**
 * @author Created by JF on  2022/6/7 10:02
 * @description
 */

public interface OnNetRequestStartListener {
    void onRequest(int parentPosition, OptionEntity entity);
}