package com.Jfpicker.wheelpicker.picker_option;

import android.app.Activity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.Jfpicker.wheelpicker.R;
import com.Jfpicker.wheelpicker.picker_option.entity.OptionEntity;
import com.Jfpicker.wheelpicker.picker_option.listener.OnOptionPickedListener;
import com.Jfpicker.wheelpicker.utils.DensityUtils;
import com.Jfpicker.wheelpicker.wheel_dialog.DialogConfig;
import com.Jfpicker.wheelpicker.wheel_dialog.ModalDialog;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import java.util.List;

/**
 * @author Created by JF on  2021/11/15
 * @description 单选RecyclerView列表样式的弹窗，可以传入列表项布局，要求有一个id是recyclerview_content_text的TextView
 */

public class OptionRecyclerViewPicker extends ModalDialog {

    private RecyclerView recyclerView;
    private TextView tvCancel;
    private OnOptionPickedListener onOptionPickedListener;

    public OptionRecyclerViewPicker(@NonNull Activity activity) {
        super(activity);
    }

    public OptionRecyclerViewPicker(@NonNull Activity activity, DialogConfig dialogConfig) {
        super(activity, dialogConfig);
    }

    public OptionRecyclerViewPicker(@NonNull Activity activity, int themeResId) {
        super(activity, themeResId);
    }

    @Nullable
    @Override
    protected View createTopLineView() {
        return null;
    }

    @NonNull
    @Override
    protected View createBodyView() {
        View view = getLayoutInflater().inflate(R.layout.dialog_option_recyclerview, null, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        tvCancel = view.findViewById(R.id.tvCancel);
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return view;
    }

    @Override
    protected void initView() {
        super.initView();
        headerView.setVisibility(View.GONE);
    }

    @Override
    protected void onCancel() {

    }

    @Override
    protected void onOk() {

    }

    public void setOptionsData(List<OptionEntity> list) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        OptionAdapter mAdapter = new OptionAdapter(list);
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                if (onOptionPickedListener != null) {
                    onOptionPickedListener.onOption(mAdapter.getData().get(position).getId(),
                            mAdapter.getData().get(position).getName());
                    dismiss();
                }
            }
        });
        recyclerView.setAdapter(mAdapter);
    }

    public void setOptionsData(int layoutResId, List<OptionEntity> list) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        OptionAdapter mAdapter = new OptionAdapter(layoutResId, list);
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                if (onOptionPickedListener != null) {
                    onOptionPickedListener.onOption(mAdapter.getData().get(position).getId(),
                            mAdapter.getData().get(position).getName());
                    dismiss();
                }
            }
        });
        recyclerView.setAdapter(mAdapter);
    }

    public class OptionAdapter extends BaseQuickAdapter<OptionEntity, BaseViewHolder> {

        public OptionAdapter(int layoutResId, @Nullable List<OptionEntity> data) {
            super((layoutResId > 0) ? layoutResId : R.layout.item_content, data);
        }

        public OptionAdapter(@Nullable List<OptionEntity> data) {
            super(R.layout.item_content, data);
        }

        @Override
        protected void convert(@NonNull BaseViewHolder holder, OptionEntity entity) {
            View view = holder.findView(R.id.recyclerview_content_text);
            if (view instanceof TextView) {
                holder.setText(R.id.recyclerview_content_text, entity.getName());
            }
        }
    }

    public void setRecyclerViewFixedHeight(int dpHeight) {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) recyclerView.getLayoutParams();
        params.height = DensityUtils.dip2px(getContext(), dpHeight);
        recyclerView.setLayoutParams(params);
    }

    public void setRecyclerViewWrapContent() {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) recyclerView.getLayoutParams();
        params.height = WRAP_CONTENT;
        recyclerView.setLayoutParams(params);
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public TextView getTvCancel() {
        return tvCancel;
    }

    public void setOnOptionPickedListener(OnOptionPickedListener onOptionPickedListener) {
        this.onOptionPickedListener = onOptionPickedListener;
    }
}
