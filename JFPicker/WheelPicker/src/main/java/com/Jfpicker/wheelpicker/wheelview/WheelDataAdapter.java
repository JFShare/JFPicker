package com.Jfpicker.wheelpicker.wheelview;

import com.Jfpicker.wheelpicker.wheelview.WheelView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Created by JF on  2021/11/9
 * @description WheelView.WheelDataAbstractAdapter的实现类，objects是列表数据
 */

public class WheelDataAdapter extends WheelDataAbstractAdapter {

    public List<Object> objects;

    public WheelDataAdapter() {
        objects = new ArrayList<>();
    }
    @Override
    protected int getItemCount() {
        return objects.size();
    }

    @Override
    protected Object getItem(int index) {
        return objects.get(index);
    }

}

