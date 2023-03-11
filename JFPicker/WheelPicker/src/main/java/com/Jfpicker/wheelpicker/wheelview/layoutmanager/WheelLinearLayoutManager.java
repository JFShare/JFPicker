package com.Jfpicker.wheelpicker.wheelview.layoutmanager;

import android.content.Context;
import android.util.AttributeSet;

import androidx.recyclerview.widget.LinearLayoutManager;

/**
 * *********************
 *
 * @Author JF
 * 创建日期 ：2023/3/11
 * 代码描述 ：LinearLayoutManager
 * *********************
 */
public class WheelLinearLayoutManager extends LinearLayoutManager implements LayoutManagerScrollEngine {
    public WheelLinearLayoutManager(Context context) {
        super(context);
        setOrientation(LinearLayoutManager.VERTICAL);
    }

    public WheelLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public WheelLinearLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void scrollToTargetPosition(int position) {
        scrollToPositionWithOffset(position, 0);
    }
}
