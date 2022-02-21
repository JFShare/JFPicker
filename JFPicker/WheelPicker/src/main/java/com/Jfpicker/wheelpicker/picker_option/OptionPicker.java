package com.Jfpicker.wheelpicker.picker_option;

import android.app.Activity;
import android.view.View;

import androidx.annotation.NonNull;

import com.Jfpicker.wheelpicker.picker_option.entity.OptionEntity;
import com.Jfpicker.wheelpicker.picker_option.listener.OnOptionPickedListener;
import com.Jfpicker.wheelpicker.picker_option.widget.OptionWheelLayout;
import com.Jfpicker.wheelpicker.wheel_dialog.DialogConfig;
import com.Jfpicker.wheelpicker.wheel_dialog.ModalDialog;

import java.util.List;

/**
 * @author Created by JF on  2021/11/13
 * @description 单选滚轮弹窗
 */

public class OptionPicker extends ModalDialog {

    OptionWheelLayout wheelLayout;
    private boolean initialized = false;
    private List<OptionEntity> dataList;
    private String title;
    private OnOptionPickedListener onOptionPickedListener;

    public OptionPicker(@NonNull Activity activity) {
        super(activity);
    }

    public OptionPicker(@NonNull Activity activity, DialogConfig dialogConfig) {
        super(activity, dialogConfig);
    }

    public OptionPicker(@NonNull Activity activity, String title, List<OptionEntity> dataList) {
        super(activity);
        this.title = title;
        this.dataList = dataList;
    }

    public OptionPicker(@NonNull Activity activity, DialogConfig dialogConfig, String title, List<OptionEntity> dataList) {
        super(activity, dialogConfig);
        this.title = title;
        this.dataList = dataList;
    }

    public OptionPicker(@NonNull Activity activity, int themeResId) {
        super(activity, themeResId);
    }


    @NonNull
    @Override
    protected View createBodyView() {
        wheelLayout = new OptionWheelLayout(activity);
        return wheelLayout;
    }

    @Override
    protected void initView() {
        super.initView();
        titleView.setText("请选择");
    }

    @Override
    protected void initData() {
        super.initData();
        initialized = true;
        if (dataList != null) {
            wheelLayout.setWheelData(dataList);
        }
        titleView.setText(title);
    }

    @Override
    protected void onCancel() {

    }

    @Override
    protected void onOk() {
        if (onOptionPickedListener != null) {
            String id = wheelLayout.getSelectId();
            String name = wheelLayout.getSelectItemName();
            onOptionPickedListener.onOption(id, name);
        }
    }

    public void setDataList(List<OptionEntity> dataList) {
        this.dataList = dataList;
        if (initialized) {
            if (dataList != null) {
                wheelLayout.setWheelData(dataList);
            }
        }
    }

    public void setTitle(String title) {
        this.title = title;
        if (initialized) {
            titleView.setText(title);
        }
    }


    public final OptionWheelLayout getWheelLayout() {
        return wheelLayout;
    }

    public void setOnOptionPickedListener(OnOptionPickedListener onOptionPickedListener) {
        this.onOptionPickedListener = onOptionPickedListener;
    }
}
