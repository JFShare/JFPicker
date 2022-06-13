package com.Jfpicker.wheelpicker.picker_option.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.Jfpicker.wheelpicker.R;
import com.Jfpicker.wheelpicker.picker_option.entity.OptionEntity;
import com.Jfpicker.wheelpicker.rv_listener.OnItemClickListener;
import com.Jfpicker.wheelpicker.rv_listener.OnRecyclerviewStyleListener;

import java.util.List;

/**
 * @author Created by JF on  2022/6/5 15:46
 * @description
 */

public class OptionAdapter extends RecyclerView.Adapter<OptionAdapter.OptionViewHolder> {
    Context context;
    int layoutResId = -1;
    List<OptionEntity> dataList;
    OnItemClickListener listener;
    OnRecyclerviewStyleListener onRecyclerviewStyleListener;

    public OptionAdapter(Context context, List<OptionEntity> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    public OptionAdapter(Context context, List<OptionEntity> dataList,
                         OnRecyclerviewStyleListener onRecyclerviewStyleListener) {
        this.context = context;
        this.dataList = dataList;
        this.onRecyclerviewStyleListener = onRecyclerviewStyleListener;
    }

    public OptionAdapter(Context context, int layoutResId, List<OptionEntity> dataList) {
        this.context = context;
        this.layoutResId = layoutResId;
        this.dataList = dataList;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public OptionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate((layoutResId > 0) ? layoutResId :
                R.layout.item_option, parent, false);
        return new OptionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OptionViewHolder holder, int position) {
        if (holder.tvRecyclerviewContent != null) {
            holder.tvRecyclerviewContent.setText(dataList.get(position).getName());
        }
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(holder.itemView, position);
            }
        });
        if (onRecyclerviewStyleListener != null) {
            onRecyclerviewStyleListener.onStyle(holder.itemView, holder.tvRecyclerviewContent,
                    getDataList().get(position), false);
        }
    }

    @Override
    public int getItemCount() {
        return dataList != null ? dataList.size() : 0;
    }

    public List<OptionEntity> getDataList() {
        return dataList;
    }

    public class OptionViewHolder extends RecyclerView.ViewHolder {

        TextView tvRecyclerviewContent;

        public OptionViewHolder(@NonNull View itemView) {
            super(itemView);
            tvRecyclerviewContent = itemView.findViewById(R.id.tvRecyclerviewContent);
        }
    }

}
