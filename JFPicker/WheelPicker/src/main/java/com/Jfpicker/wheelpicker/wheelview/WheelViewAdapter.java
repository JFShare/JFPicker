package com.Jfpicker.wheelpicker.wheelview;


import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;



class WheelViewAdapter extends RecyclerView.Adapter<WheelViewHolder> {

    /**
     * recyclerview 布局方向
     */
    final int orientation;

    /**
     * 每个item大小
     */
    final int itemSize;
    /**
     * wheelview 滑动时上或下的空白数量
     */
    final int itemCount;
    /**
     * wheelview 滑动时上或下空白总数量
     */
    private int totalItemCount;


    WheelView.WheelDataAbstractAdapter adapter;

    WheelViewAdapter(int orientation, int itemSize, int itemCount) {
        this.orientation = orientation;
        this.itemSize = itemSize;
        this.itemCount = itemCount;
        this.totalItemCount = itemCount * 2;
    }

    @Override
    public void onBindViewHolder(WheelViewHolder holder, int position) {
    }

    @Override
    public int getItemCount() {
        return totalItemCount + (adapter == null ? 0 : adapter.getItemCount());
    }

    @Override
    public WheelViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = new View(parent.getContext());
        view.setLayoutParams(WheelUtils.createLayoutParams(orientation, itemSize));
        return new WheelViewHolder(view);
    }

    Object getItemString(int index) {
        return adapter == null ? "" : adapter.getItem(index);
    }

}
