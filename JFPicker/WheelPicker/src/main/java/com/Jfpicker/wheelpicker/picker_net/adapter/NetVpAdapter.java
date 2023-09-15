package com.Jfpicker.wheelpicker.picker_net.adapter;

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
import com.Jfpicker.wheelpicker.picker_option.entity.OptionEntity;
import com.Jfpicker.wheelpicker.rv_listener.OnRecyclerviewStyleListener;
import com.Jfpicker.wheelpicker.rv_listener.OnSecondItemClickListener;

import java.util.List;

/**
 * @author Created by JF on  2022/6/7 10:04
 * @description
 */

public class NetVpAdapter extends RecyclerView.Adapter<NetVpAdapter.NetVpViewHolder> {

    Context context;
    List<List<OptionEntity>> dataList;
    OnSecondItemClickListener onSecondItemClickListener;
    OnRecyclerviewStyleListener onRecyclerviewStyleListener;

    public NetVpAdapter(Context context, @Nullable List<List<OptionEntity>> dataList,
                        OnRecyclerviewStyleListener onRecyclerviewStyleListener,
                        OnSecondItemClickListener onSecondItemClickListener) {
        this.context = context;
        this.dataList = dataList;
        this.onSecondItemClickListener = onSecondItemClickListener;
        this.onRecyclerviewStyleListener = onRecyclerviewStyleListener;

    }


    @NonNull
    @Override
    public NetVpAdapter.NetVpViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.jf_item_vp_recyclerview, parent, false);
        return new NetVpAdapter.NetVpViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NetVpAdapter.NetVpViewHolder holder, int position) {
        NetRequestOptionAdapter adapter = new NetRequestOptionAdapter(context,
                position, dataList.get(position),
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

    public List<List<OptionEntity>> getDataList() {
        return dataList;
    }

    public OptionEntity getOptionItem(int vpPosition, int clickItemPosition) {
        return dataList.get(vpPosition).get(clickItemPosition);
    }

    public class NetVpViewHolder extends RecyclerView.ViewHolder {
        public RecyclerView rvPageItem;

        public NetVpViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setTag(this);
            rvPageItem = itemView.findViewById(R.id.rvPageItem);
        }
    }


    public void addItem(@IntRange(from = 0) int position, @NonNull List<OptionEntity> item) {
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
