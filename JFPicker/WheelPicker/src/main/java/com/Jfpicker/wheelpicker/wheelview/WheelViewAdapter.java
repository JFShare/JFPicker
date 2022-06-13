package com.Jfpicker.wheelpicker.wheelview;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


class WheelViewAdapter extends RecyclerView.Adapter<WheelViewAdapter.WheelViewHolder> {

    WheelDataAbstractAdapter adapter;
    private WheelAttrs attrs;

    WheelViewAdapter(WheelAttrs attrs) {
        this.attrs = attrs;
    }

    @Override
    public void onBindViewHolder(@NonNull WheelViewHolder holder, int position) {
    }

    @Override
    public int getItemCount() {
        return attrs.getHalfItemCount() * 2 + (adapter == null ? 0 : adapter.getItemCount());
    }

    @NonNull
    @Override
    public WheelViewAdapter.WheelViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = new View(parent.getContext());
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                attrs.getItemSize());
        view.setLayoutParams(params);
        return new WheelViewHolder(view);
    }

    Object getItemString(int index) {
        return adapter == null ? "" : adapter.getItem(index);
    }


    class WheelViewHolder extends RecyclerView.ViewHolder {
        WheelViewHolder(View itemView) {
            super(itemView);
        }
    }

}
