package com.Jfpicker.wheelpicker.wheelview;

import android.database.DataSetObserver;

/**
 * @author Created by JF on  2022/6/7 10:58
 * @description WheelView 抽象的数据管理类
 */

public abstract class WheelDataAbstractAdapter {

    private DataSetObserver wheelObserver;

    void setWheelViewObserver(DataSetObserver observer) {
        synchronized (this) {
            wheelObserver = observer;
        }
    }

    protected abstract int getItemCount();

    protected abstract Object getItem(int index);

    public final void notifyDataSetChanged() {
        synchronized (this) {
            if (wheelObserver != null) {
                wheelObserver.onChanged();
            }
        }
    }
}