package com.Jfpicker.wheelpicker.picker_base;

import com.Jfpicker.wheelpicker.wheelview.WheelView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Created by JF on  2021/11/9 14:53
 * @description
 */

public class WheelDataAdapter extends WheelView.WheelDataAbstractAdapter {

    public List<Object> strs;

    public WheelDataAdapter() {
        strs = new ArrayList<>();
    }

    @Override
    protected int getItemCount() {
        return strs.size();
    }

    @Override
    protected Object getItem(int index) {
        return strs.get(index);
    }

}

