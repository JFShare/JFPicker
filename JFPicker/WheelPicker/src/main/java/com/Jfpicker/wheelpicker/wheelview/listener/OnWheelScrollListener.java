package com.Jfpicker.wheelpicker.wheelview.listener;

import com.Jfpicker.wheelpicker.wheelview.WheelView;

/**
 * @author Created by JF on  2022/6/2 17:07
 * @description
 */

public interface OnWheelScrollListener {
    void onItemChecked(WheelView wheelView, int index);

    void onScrollStatusChange(boolean scrolling);
}