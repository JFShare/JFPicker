package com.Jfpicker.wheelpicker.picker_option;

import android.app.Activity;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Dimension;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StyleRes;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.Jfpicker.wheelpicker.R;
import com.Jfpicker.wheelpicker.picker_option.adapter.OptionAdapter;
import com.Jfpicker.wheelpicker.picker_option.entity.OptionEntity;
import com.Jfpicker.wheelpicker.picker_option.listener.OnOptionPickedListener;
import com.Jfpicker.wheelpicker.rv_listener.OnRecyclerviewStyleListener;
import com.Jfpicker.wheelpicker.utils.WheelDensityUtils;
import com.Jfpicker.wheelpicker.dialog.config.DialogConfig;
import com.Jfpicker.wheelpicker.dialog.ModalDialog;

import java.util.List;

/**
 * @author Created by JF on  2021/11/15
 * @description 列表样式的选项弹窗，列表项包含一个id是tvRecyclerviewContent的TextView
 * 可以设置布局id实现自定义样式
 * 也可以通过设置OnRecyclerviewStyleListener实现自定义样式
 */

public class OptionRecyclerViewPicker extends ModalDialog {

    private RecyclerView recyclerView;

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

    public OptionRecyclerViewPicker(@NonNull Activity activity, DialogConfig dialogConfig, @StyleRes int themeResId) {
        super(activity, dialogConfig, themeResId);
    }

    @Nullable
    @Override
    public View createTitleView() {
        return null;
    }

    @Nullable
    @Override
    protected View createTopLineView() {
        return null;
    }

    @NonNull
    @Override
    protected View createBodyView() {
        View view = getLayoutInflater().inflate(R.layout.jf_picker_recyclerview, null, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        return view;
    }

    @Nullable
    @Override
    protected View createFooterView() {
        return getLayoutInflater().inflate(R.layout.jf_footer_bottom_cancel, null, false);
    }

    @Override
    protected void onCancel() {

    }

    @Override
    protected void onConfirm() {

    }

    public void setOptionsData(List<OptionEntity> list) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        OptionAdapter mAdapter = new OptionAdapter(getContext(), list);
        mAdapter.setOnItemClickListener((view, position) -> {
            if (onOptionPickedListener != null) {
                onOptionPickedListener.onOption(mAdapter.getDataList().get(position).getId(),
                        mAdapter.getDataList().get(position).getName());
            }
            dismiss();
        });
        recyclerView.setAdapter(mAdapter);
    }

    public void setOptionsData(List<OptionEntity> list,
                               OnRecyclerviewStyleListener onRecyclerviewStyleListener) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        OptionAdapter mAdapter = new OptionAdapter(getContext(), list, onRecyclerviewStyleListener);
        mAdapter.setOnItemClickListener((view, position) -> {
            if (onOptionPickedListener != null) {
                onOptionPickedListener.onOption(mAdapter.getDataList().get(position).getId(),
                        mAdapter.getDataList().get(position).getName());
            }
            dismiss();
        });
        recyclerView.setAdapter(mAdapter);
    }

    public void setOptionsData(int layoutResId, List<OptionEntity> list) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        OptionAdapter mAdapter = new OptionAdapter(getContext(), layoutResId, list);
        mAdapter.setOnItemClickListener((view, position) -> {
            if (onOptionPickedListener != null) {
                onOptionPickedListener.onOption(mAdapter.getDataList().get(position).getId(),
                        mAdapter.getDataList().get(position).getName());
            }
            dismiss();
        });

        recyclerView.setAdapter(mAdapter);
    }

    public void setRecyclerViewFixedHeight(@Dimension(unit = Dimension.DP) int dpHeight) {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) recyclerView.getLayoutParams();
        params.height = WheelDensityUtils.dip2px(getContext(), dpHeight);
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


    public void setOnOptionPickedListener(OnOptionPickedListener onOptionPickedListener) {
        this.onOptionPickedListener = onOptionPickedListener;
    }
}
