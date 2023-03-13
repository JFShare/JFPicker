package com.Jfpicker.wheelpicker.wheelview.listener;

import com.Jfpicker.wheelpicker.wheelview.WheelView;

/**
 * @author Created by JF on  2022/6/2 17:07
 * @description WheelView 滚动回调接口
 */

public interface OnWheelScrollListener {

    /**
     * 停止滚动后选中哪一项
     * @param wheelView 滚轮组件
     * @param index adapterPosition
     */
    void onItemChecked(WheelView wheelView, int index);

    /**
     * @param scrolling 是否滚动中
     */
    void onScrollStatusChange(boolean scrolling);

}