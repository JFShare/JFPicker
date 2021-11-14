package com.Jfpicker.wheelpicker.wheelview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.recyclerview.widget.RecyclerView;

/**
 * @author Created by JF on  2021/11/10 10:20
 * @description 可以禁用触摸事件的 RecyclerView
 */

public class CScrollRecyclerView extends RecyclerView {

    private boolean canScroll = true;

    public CScrollRecyclerView(Context context) {
        super(context);
    }

    public CScrollRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CScrollRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (canScroll) {
            return super.onTouchEvent(ev);
        } else {
            return true;
        }

    }


    public boolean isCanScroll() {
        return canScroll;
    }

    public void setCanScroll(boolean canScroll) {
        this.canScroll = canScroll;
    }
}
