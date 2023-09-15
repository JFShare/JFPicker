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
import com.Jfpicker.wheelpicker.picker_option.adapter.CheckBoxAdapter;
import com.Jfpicker.wheelpicker.picker_option.entity.OptionEntity;
import com.Jfpicker.wheelpicker.picker_option.listener.OnOptionMultiPickedListener;
import com.Jfpicker.wheelpicker.rv_listener.OnRecyclerviewStyleListener;
import com.Jfpicker.wheelpicker.utils.WheelDensityUtils;
import com.Jfpicker.wheelpicker.dialog.ModalDialog;
import com.Jfpicker.wheelpicker.dialog.config.DialogConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Created by JF on  2022/6/6 10:51
 * @description 多选弹窗，默认居中，列表项包含一个id是tvRecyclerviewContent的TextView 一个id是ivCheck的ImageView
 * 可以设置布局id，选中未选中图片id实现自定义样式
 * 也可以通过设置OnRecyclerviewStyleListener实现自定义样式
 */

public class CheckBoxPicker extends ModalDialog {

    private RecyclerView recyclerView;
    protected View viewBottomSplitLine;
    protected View viewBtnSplitLine;
    private OnOptionMultiPickedListener onOptionMultiPickedListener;
    CheckBoxAdapter mAdapter;

    public CheckBoxPicker(@NonNull Activity activity) {
        super(activity, DialogConfig.getCenterConfig(10));
    }

    public CheckBoxPicker(@NonNull Activity activity, DialogConfig dialogConfig) {
        super(activity, (dialogConfig == null) ? DialogConfig.getCenterConfig(10) : dialogConfig);
    }

    public CheckBoxPicker(@NonNull Activity activity, int themeResId) {
        super(activity, DialogConfig.getCenterConfig(10), themeResId);
    }

    public CheckBoxPicker(@NonNull Activity activity, DialogConfig dialogConfig, @StyleRes int themeResId) {
        super(activity, (dialogConfig == null) ? DialogConfig.getCenterConfig(10) : dialogConfig, themeResId);
    }

    @Nullable
    @Override
    public View createTitleView() {
        return View.inflate(activity, R.layout.jf_titlebar_only_title, null);
    }


    @NonNull
    @Override
    protected View createBodyView() {
        View view = getLayoutInflater().inflate(R.layout.jf_picker_recyclerview_nobar, null, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        setRecyclerViewFixedHeight(250);
        return view;
    }

    @Nullable
    @Override
    protected View createFooterView() {
        View view = View.inflate(activity, R.layout.jf_footer_default, null);
        viewBottomSplitLine = view.findViewById(R.id.viewBottomSplitLine);
        viewBtnSplitLine = view.findViewById(R.id.viewBtnSplitLine);

        viewBottomSplitLine.setBackgroundColor((dialogConfig != null) ?
                dialogConfig.getBottomSplitLineColor() : DialogConfig.getDefault().getBottomSplitLineColor());
        viewBtnSplitLine.setBackgroundColor((dialogConfig != null) ?
                dialogConfig.getBtnSplitLineColor() : DialogConfig.getDefault().getBtnSplitLineColor());

        return view;
    }

    @Override
    protected void initView() {
        super.initView();
        if (titleTextView != null) {
            titleTextView.setText("请选择");
        }
    }

    @Override
    protected void onCancel() {

    }

    @Override
    protected void onConfirm() {
        if (onOptionMultiPickedListener != null) {
            List<OptionEntity> checkedList = new ArrayList<>();
            for (int i = 0; i < mAdapter.getDataList().size(); i++) {
                if (mAdapter.getDataList().get(i).isChecked()) {
                    checkedList.add(mAdapter.getDataList().get(i));
                }
            }
            onOptionMultiPickedListener.onOption(checkedList);
        }
    }

    public void setOptionsData(List<OptionEntity> list) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new CheckBoxAdapter(getContext(), list);
        recyclerView.setAdapter(mAdapter);
    }

    public void setOptionsData(List<OptionEntity> list,
                               OnRecyclerviewStyleListener onRecyclerviewStyleListener) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new CheckBoxAdapter(getContext(), list, onRecyclerviewStyleListener);
        recyclerView.setAdapter(mAdapter);
    }

    public void setOptionsData(int layoutResId, List<OptionEntity> list) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new CheckBoxAdapter(getContext(), layoutResId, list);
        recyclerView.setAdapter(mAdapter);
    }

    public void setOptionsData(int uncheckedIcon, int checkedIcon, List<OptionEntity> list) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new CheckBoxAdapter(getContext(), uncheckedIcon, checkedIcon, list);
        recyclerView.setAdapter(mAdapter);
    }

    public void setOptionsData(int layoutResId, int uncheckedIcon, int checkedIcon, List<OptionEntity> list) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new CheckBoxAdapter(getContext(), layoutResId, uncheckedIcon, checkedIcon, list);
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

    public void setOnOptionMultiPickedListener(OnOptionMultiPickedListener onOptionMultiPickedListener) {
        this.onOptionMultiPickedListener = onOptionMultiPickedListener;
    }
}

