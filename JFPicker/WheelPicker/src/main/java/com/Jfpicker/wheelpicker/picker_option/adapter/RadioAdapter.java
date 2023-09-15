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
 * @author Created by JF on  2022/6/5 16:01
 * @description
 */

public class RadioAdapter extends RecyclerView.Adapter<RadioAdapter.RadioViewHolder> {
    Context context;
    int layoutResId = -1;
    int uncheckedIcon = -1;
    int checkedIcon = -1;
    List<OptionEntity> dataList;
    OnItemClickListener listener;

    OnRecyclerviewStyleListener onRecyclerviewStyleListener;

    public RadioAdapter(Context context, List<OptionEntity> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    public RadioAdapter(Context context, List<OptionEntity> dataList,
                        OnRecyclerviewStyleListener onRecyclerviewStyleListener) {
        this.context = context;
        this.dataList = dataList;
        this.onRecyclerviewStyleListener = onRecyclerviewStyleListener;
    }

    public RadioAdapter(Context context, int layoutResId, List<OptionEntity> dataList) {
        this.context = context;
        this.layoutResId = layoutResId;
        this.dataList = dataList;
    }

    public RadioAdapter(Context context, int uncheckedIcon, int checkedIcon,
                        List<OptionEntity> dataList) {
        this.context = context;
        this.uncheckedIcon = uncheckedIcon;
        this.checkedIcon = checkedIcon;
        this.dataList = dataList;
    }

    public RadioAdapter(Context context, int layoutResId, int uncheckedIcon, int checkedIcon,
                        List<OptionEntity> dataList) {
        this.context = context;
        this.layoutResId = layoutResId;
        this.uncheckedIcon = uncheckedIcon;
        this.checkedIcon = checkedIcon;
        this.dataList = dataList;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public RadioAdapter.RadioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate((layoutResId > 0) ? layoutResId :
                R.layout.jf_item_radio, parent, false);
        return new RadioAdapter.RadioViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RadioAdapter.RadioViewHolder holder, int position) {
        holder.itemView.setOnClickListener(v -> {
            for (int i = 0; i < dataList.size(); i++) {
                dataList.get(i).setChecked(i == position);
            }
            notifyDataSetChanged();
        });
        if (holder.tvRecyclerviewContent != null) {
            holder.tvRecyclerviewContent.setText(dataList.get(position).getName());
        }
        if (holder.ivCheck != null) {
            if (dataList.get(position).isChecked()) {
                holder.ivCheck.setImageResource(uncheckedIcon > 0 ? checkedIcon : R.drawable.ic_radio_btn_checked);
            } else {
                holder.ivCheck.setImageResource(uncheckedIcon > 0 ? uncheckedIcon : R.drawable.ic_radio_btn_unchecked);
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

    public class RadioViewHolder extends RecyclerView.ViewHolder {

        TextView tvRecyclerviewContent;
        ImageView ivCheck;

        public RadioViewHolder(@NonNull View itemView) {
            super(itemView);
            tvRecyclerviewContent = itemView.findViewById(R.id.tvRecyclerviewContent);
            ivCheck = itemView.findViewById(R.id.ivCheck);
        }
    }

}

