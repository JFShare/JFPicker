package com.Jfpicker.wheelpicker.picker_option.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.Jfpicker.wheelpicker.R;
import com.Jfpicker.wheelpicker.picker_option.entity.OptionEntity;
import com.Jfpicker.wheelpicker.rv_listener.OnItemClickListener;
import com.Jfpicker.wheelpicker.rv_listener.OnRecyclerviewStyleListener;

import java.util.List;

/**
 * @author Created by JF on  2022/6/6 10:52
 * @description
 */

public class CheckBoxAdapter extends RecyclerView.Adapter<CheckBoxAdapter.CheckBoxViewHolder> {
    Context context;
    int layoutResId = -1;
    int uncheckedIcon = -1;
    int checkedIcon = -1;
    List<OptionEntity> dataList;
    OnRecyclerviewStyleListener onRecyclerviewStyleListener;

    public CheckBoxAdapter(Context context, List<OptionEntity> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    public CheckBoxAdapter(Context context, List<OptionEntity> dataList,
                           OnRecyclerviewStyleListener onRecyclerviewStyleListener) {
        this.context = context;
        this.dataList = dataList;
        this.onRecyclerviewStyleListener = onRecyclerviewStyleListener;
    }

    public CheckBoxAdapter(Context context, int layoutResId, List<OptionEntity> dataList) {
        this.context = context;
        this.layoutResId = layoutResId;
        this.dataList = dataList;
    }

    public CheckBoxAdapter(Context context, int uncheckedIcon, int checkedIcon,
                           List<OptionEntity> dataList) {
        this.context = context;
        this.uncheckedIcon = uncheckedIcon;
        this.checkedIcon = checkedIcon;
        this.dataList = dataList;
    }

    public CheckBoxAdapter(Context context, int layoutResId, int uncheckedIcon, int checkedIcon,
                           List<OptionEntity> dataList) {
        this.context = context;
        this.layoutResId = layoutResId;
        this.uncheckedIcon = uncheckedIcon;
        this.checkedIcon = checkedIcon;
        this.dataList = dataList;
    }


    @NonNull
    @Override
    public CheckBoxAdapter.CheckBoxViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate((layoutResId > 0) ? layoutResId :
                R.layout.item_checkbox, parent, false);
        return new CheckBoxAdapter.CheckBoxViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CheckBoxAdapter.CheckBoxViewHolder holder, int position) {
        holder.itemView.setOnClickListener(v -> {
            dataList.get(position).setChecked(!dataList.get(position).isChecked());
            notifyItemChanged(position);
        });
        if (holder.tvRecyclerviewContent != null) {
            holder.tvRecyclerviewContent.setText(dataList.get(position).getName());
        }
        if (holder.ivCheck != null) {
            if (dataList.get(position).isChecked()) {
                holder.ivCheck.setImageResource(uncheckedIcon > 0 ? checkedIcon : R.drawable.ic_checkbox_checked);
            } else {
                holder.ivCheck.setImageResource(uncheckedIcon > 0 ? uncheckedIcon : R.drawable.ic_checkbox_unchecked);
            }
        }
        if (onRecyclerviewStyleListener != null) {
            onRecyclerviewStyleListener.onStyle(holder.itemView, holder.tvRecyclerviewContent,
                    getDataList().get(position), dataList.get(position).isChecked());
        }
    }

    @Override
    public int getItemCount() {
        return dataList != null ? dataList.size() : 0;
    }

    public List<OptionEntity> getDataList() {
        return dataList;
    }

    public class CheckBoxViewHolder extends RecyclerView.ViewHolder {

        TextView tvRecyclerviewContent;
        ImageView ivCheck;

        public CheckBoxViewHolder(@NonNull View itemView) {
            super(itemView);
            tvRecyclerviewContent = itemView.findViewById(R.id.tvRecyclerviewContent);
            ivCheck = itemView.findViewById(R.id.ivCheck);
        }
    }

}

