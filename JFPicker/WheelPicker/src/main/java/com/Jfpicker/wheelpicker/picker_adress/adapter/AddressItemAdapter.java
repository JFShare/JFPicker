package com.Jfpicker.wheelpicker.picker_adress.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.Jfpicker.wheelpicker.R;
import com.Jfpicker.wheelpicker.picker_adress.entity.AddressItemEntity;
import com.Jfpicker.wheelpicker.rv_listener.OnRecyclerviewStyleListener;
import com.Jfpicker.wheelpicker.rv_listener.OnItemClickListener;

import java.util.List;

/**
 * @author Created by JF on  2022/6/6 14:02
 * @description
 */

public class AddressItemAdapter extends RecyclerView.Adapter<AddressItemAdapter.AddressItemViewHolder> {

    Context context;
    List<AddressItemEntity> dataList;
    int parentPosition;
    AddressItemEntity currentProvince;
    AddressItemEntity currentCity;
    AddressItemEntity currentArea;
    OnRecyclerviewStyleListener onRecyclerviewStyleListener;

    OnItemClickListener onItemClickListener;

    public AddressItemAdapter(Context context, @Nullable List<AddressItemEntity> dataList, int parentPosition,
                              OnRecyclerviewStyleListener onRecyclerviewStyleListener) {
        this.context = context;
        this.dataList = dataList;
        this.parentPosition = parentPosition;
        this.onRecyclerviewStyleListener = onRecyclerviewStyleListener;
    }

    public void notifyCurrentAddressChanged(AddressItemEntity currentProvince, AddressItemEntity currentCity,
                                            AddressItemEntity currentArea) {
        this.currentProvince = currentProvince;
        this.currentCity = currentCity;
        this.currentArea = currentArea;
        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public AddressItemAdapter.AddressItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.jf_item_option, parent, false);
        return new AddressItemAdapter.AddressItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddressItemAdapter.AddressItemViewHolder holder, int position) {
        holder.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(holder.itemView, position);
            }
        });
        holder.tvRecyclerviewContent.setText(dataList.get(position).getName());
        boolean isSelect = false;
        switch (parentPosition) {
            case 0:
                if (currentProvince != null && currentProvince.getCode().equals(dataList.get(position).getCode())) {
                    isSelect = true;
                }
                break;
            case 1:
                if (currentCity != null && currentCity.getCode().equals(dataList.get(position).getCode())) {
                    isSelect = true;
                }
                break;
            case 2:
                if (currentArea != null && currentArea.getCode().equals(dataList.get(position).getCode())) {
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

    public List<AddressItemEntity> getDataList() {
        return dataList;
    }

    public class AddressItemViewHolder extends RecyclerView.ViewHolder {
        TextView tvRecyclerviewContent;

        public AddressItemViewHolder(@NonNull View itemView) {
            super(itemView);
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