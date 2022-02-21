package com.Jfpicker.wheelpicker.picker_netrequest;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;

import androidx.annotation.NonNull;

import com.Jfpicker.wheelpicker.picker_adress.contract.OnNetRequestPickedListener;
import com.Jfpicker.wheelpicker.picker_base.OnRecyclerviewStyleListener;
import com.Jfpicker.wheelpicker.picker_option.entity.OptionEntity;
import com.Jfpicker.wheelpicker.wheel_dialog.DialogConfig;
import com.Jfpicker.wheelpicker.wheel_dialog.ModalDialog;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Created by JF on  2021/11/15
 * @description Viewpager样式的五级选择器
 */

public class NetRequestPicker extends ModalDialog {


    private NetRequestLayout netRequestLayout;

    public OnNetRequestPickedListener onNetRequestPickedListener;

    public NetRequestPicker(@NonNull Activity activity) {
        super(activity);
    }

    public NetRequestPicker(@NonNull Activity activity, DialogConfig dialogConfig) {
        super(activity, dialogConfig);

    }

    public NetRequestPicker(@NonNull Activity activity, int themeResId) {
        super(activity, themeResId);
    }

    @Override
    protected void initView() {
        super.initView();
        titleView.setText("请选择");
        headerView.setBackgroundColor(Color.TRANSPARENT);

    }

    @NonNull
    @Override
    protected View createBodyView() {
        netRequestLayout = new NetRequestLayout(getContext());
        return netRequestLayout;
    }

    @Override
    protected void onCancel() {

    }

    @Override
    protected void onOk() {
        if (onNetRequestPickedListener != null) {
            onNetRequestPickedListener.onPicked(netRequestLayout.getFirstData(), netRequestLayout.getSecondData(), netRequestLayout.getThirdDate(),
                    netRequestLayout.getFourthData(), netRequestLayout.getFifthData());
        }

    }

    public void setTabNameList(ArrayList<String> tabNameList) {
        netRequestLayout.setTabNameList(tabNameList);
    }

    public void setMaxIndex(int maxIndex) {
        netRequestLayout.setMaxIndex(maxIndex);
    }

    public void setFirstData(List<OptionEntity> firstData) {
        netRequestLayout.setFirstData(firstData);
    }

    public void setNextData(List<OptionEntity> nextData) {
        netRequestLayout.setNextData(nextData);
    }


    public void setOnNetRequestStartListener(NetRequestLayout.OnNetRequestStartListener listener) {
        netRequestLayout.setOnNetRequestStartListener(listener);
    }

    public void setOnNetRequestPickedListener(OnNetRequestPickedListener onNetRequestPickedListener) {
        this.onNetRequestPickedListener = onNetRequestPickedListener;
    }

    public void setOnRecyclerviewStyleListener(OnRecyclerviewStyleListener onRecyclerviewStyleListener) {
        netRequestLayout.setOnRecyclerviewStyleListener(onRecyclerviewStyleListener);
    }

    public NetRequestLayout getNetRequestLayout() {
        return netRequestLayout;
    }

    public TabLayout getTabLayout() {
        return netRequestLayout.getTabLayout();
    }

}
