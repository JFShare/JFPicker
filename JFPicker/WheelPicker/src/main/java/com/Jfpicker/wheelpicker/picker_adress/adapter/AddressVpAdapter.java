package com.Jfpicker.wheelpicker.picker_adress.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.Jfpicker.wheelpicker.R;
import com.Jfpicker.wheelpicker.rv_listener.OnSecondItemClickListener;
import com.Jfpicker.wheelpicker.picker_adress.entity.AddressItemEntity;
import com.Jfpicker.wheelpicker.rv_listener.OnRecyclerviewStyleListener;

import java.util.List;

/**
 * @author Created by JF on  2022/6/6 13:54
 * @description
 */

public class AddressVpAdapter extends RecyclerView.Adapter<AddressVpAdapter.AddressVpViewHolder> {
    Context context;

    List<List<AddressItemEntity>> dataList;
    private OnSecondItemClickListener onSecondItemClickListener;

    OnRecyclerviewStyleListener onRecyclerviewStyleListener;

    public AddressVpAdapter(Context context, @Nullable List<List<AddressItemEntity>> dataList,
                            OnRecyclerviewStyleListener onRecyclerviewStyleListener,
                            OnSecondItemClickListener onSecondItemClickListener) {
        this.context = context;
        this.onSecondItemClickListener = onSecondItemClickListener;
        this.dataList = dataList;
        this.onRecyclerviewStyleListener = onRecyclerviewStyleListener;
    }


    @NonNull
    @Override
    public AddressVpAdapter.AddressVpViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.jf_item_vp_recyclerview, parent, false);
        return new AddressVpAdapter.AddressVpViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddressVpAdapter.AddressVpViewHolder holder, int position) {
        AddressItemAdapter adapter = new AddressItemAdapter(context,
                dataList.get(position), position,
                onRecyclerviewStyleListener);
        adapter.setOnItemClickListener((view, position1) -> {
            if (onSecondItemClickListener != null) {
                onSecondItemClickListener.onClick(position, position1, true);
            }
        });
        holder.rvPageItem.setLayoutManager(new LinearLayoutManager(context));
        holder.rvPageItem.setAdapter(adapter);
    }

    @Override
    public int getItemCount() {
        return dataList != null ? dataList.size() : 0;
    }

    public List<List<AddressItemEntity>> getDataList() {
        return dataList;
    }

    public AddressItemEntity getAddressItem(int vpPosition, int clickItemPosition) {
        return dataList.get(vpPosition).get(clickItemPosition);
    }

    public class AddressVpViewHolder extends RecyclerView.ViewHolder {
        public RecyclerView rvPageItem;

        public AddressVpViewHolder(@NonNull View itemView) {
            super(itemView);
            rvPageItem = itemView.findViewById(R.id.rvPageItem);
        }
    }


    public void addItem(@IntRange(from = 0) int position, @NonNull List<AddressItemEntity> item) {
        if (getDataList() == null) {
            return;
        }

        if (position < getDataList().size()) {
            getDataList().add(position, item);
        } else {
            getDataList().add(item);
            position = getDataList().size() - 1;
        }
        notifyItemInserted(position);
    }

    public void removeItem(@IntRange(from = 0) int position) {
        getDataList().remove(position);
        notifyItemRemoved(position);
    }
}
