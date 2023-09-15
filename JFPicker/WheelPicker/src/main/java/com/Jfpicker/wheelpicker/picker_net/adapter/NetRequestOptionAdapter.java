package com.Jfpicker.wheelpicker.picker_net.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.Jfpicker.wheelpicker.R;
import com.Jfpicker.wheelpicker.picker_option.entity.OptionEntity;
import com.Jfpicker.wheelpicker.rv_listener.OnItemClickListener;
import com.Jfpicker.wheelpicker.rv_listener.OnRecyclerviewStyleListener;

import java.util.List;

/**
 * @author Created by JF on  2022/6/7 10:07
 * @description
 */

public class NetRequestOptionAdapter extends RecyclerView.Adapter<NetRequestOptionAdapter.NetRequestOptionViewHolder> {

    Context context;
    int parentPosition;
    List<OptionEntity> dataList;

    OnRecyclerviewStyleListener onRecyclerviewStyleListener;
    private OptionEntity firstData;
    private OptionEntity secondData;
    private OptionEntity thirdDate;
    private OptionEntity fourthData;
    private OptionEntity fifthData;
    OnItemClickListener onItemClickListener;


    public NetRequestOptionAdapter(Context context, int parentPosition, @Nullable List<OptionEntity> dataList,
                                   OnRecyclerviewStyleListener onRecyclerviewStyleListener) {
        this.context = context;
        this.parentPosition = parentPosition;
        this.dataList = dataList;
        this.onRecyclerviewStyleListener = onRecyclerviewStyleListener;
    }

    public void notifyCurrentOptionChanged(OptionEntity firstData, OptionEntity secondData, OptionEntity thirdDate,
                                           OptionEntity fourthData, OptionEntity fifthData) {
        this.firstData = firstData;
        this.secondData = secondData;
        this.thirdDate = thirdDate;
        this.fourthData = fourthData;
        this.fifthData = fifthData;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NetRequestOptionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.jf_item_option, parent, false);
        return new NetRequestOptionAdapter.NetRequestOptionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NetRequestOptionViewHolder holder, int position) {
        holder.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(holder.itemView, position);
            }
        });

        holder.tvRecyclerviewContent.setText(dataList.get(position).getName());
        boolean isSelect = false;
        switch (parentPosition) {
            case 0:
                if (firstData != null && firstData.getId().equals(dataList.get(position).getId())) {
                    isSelect = true;
                }
                break;
            case 1:
                if (secondData != null && secondData.getId().equals(dataList.get(position).getId())) {
                    isSelect = true;
                }
                break;
            case 2:
                if (thirdDate != null && thirdDate.getId().equals(dataList.get(position).getId())) {
                    isSelect = true;
                }
                break;
            case 3:
                if (fourthData != null && fourthData.getId().equals(dataList.get(position).getId())) {
                    isSelect = true;
                }
                break;
            case 4:
                if (fifthData != null && fifthData.getId().equals(dataList.get(position).getId())) {
                    isSelect = true;
                }
                break;
            default:
                isSelect = false;
                break;
        }
        if (isSelect) {
            holder.tvRecyclerviewContent.setTextColor(context.getColor(R.color.colorPrimary));
        } else {
            holder.tvRecyclerviewContent.setTextColor(context.getColor(R.color.jf_text_color));
        }
        if (onRecyclerviewStyleListener != null) {
            onRecyclerviewStyleListener.onStyle(holder.itemView,
                    holder.tvRecyclerviewContent,
                    dataList.get(position), isSelect);
        }
    }


    @Override
    public int getItemCount() {
        return dataList != null ? dataList.size() : 0;
    }

    public List<OptionEntity> getDataList() {
        return dataList;
    }

    public class NetRequestOptionViewHolder extends RecyclerView.ViewHolder {
        public TextView tvRecyclerviewContent;

        public NetRequestOptionViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setTag(this);
            tvRecyclerviewContent = itemView.findViewById(R.id.tvRecyclerviewContent);
        }
    }

    public OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
