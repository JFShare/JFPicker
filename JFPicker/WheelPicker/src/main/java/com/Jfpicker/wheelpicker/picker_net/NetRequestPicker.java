package com.Jfpicker.wheelpicker.picker_net;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.StyleRes;

import com.Jfpicker.wheelpicker.picker_net.listener.OnNetRequestPickedListener;
import com.Jfpicker.wheelpicker.picker_net.listener.OnNetRequestStartListener;
import com.Jfpicker.wheelpicker.picker_net.widget.NetRequestLayout;
import com.Jfpicker.wheelpicker.rv_listener.OnRecyclerviewStyleListener;
import com.Jfpicker.wheelpicker.picker_option.entity.OptionEntity;
import com.Jfpicker.wheelpicker.dialog.config.DialogConfig;
import com.Jfpicker.wheelpicker.dialog.ModalDialog;
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
        super(activity, DialogConfig.getBottomConfig(15));
    }

    public NetRequestPicker(@NonNull Activity activity, DialogConfig dialogConfig) {
        super(activity, (dialogConfig != null) ? dialogConfig : DialogConfig.getBottomConfig(15));

    }

    public NetRequestPicker(@NonNull Activity activity, int themeResId) {
        super(activity, DialogConfig.getBottomConfig(15), themeResId);
    }

    public NetRequestPicker(@NonNull Activity activity, DialogConfig dialogConfig, @StyleRes int themeResId) {
        super(activity, (dialogConfig != null) ? dialogConfig : DialogConfig.getBottomConfig(15), themeResId);
    }

    @Override
    protected void initView() {
        super.initView();
        titleTextView.setText("请选择");
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
    protected void onConfirm() {
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

    public void setNextData() {
        netRequestLayout.setNextData();
    }

    public void setNextData(List<OptionEntity> nextData) {
        netRequestLayout.setNextData(nextData);
    }


    public void setOnNetRequestStartListener(OnNetRequestStartListener listener) {
        netRequestLayout.setOnNetRequestStartListener(listener);
    }

    public void setOnNetRequestPickedListener(OnNetRequestPickedListener onNetRequestPickedListener) {
        this.onNetRequestPickedListener = onNetRequestPickedListener;
    }

    public void setOnRecyclerviewStyleListener(OnRecyclerviewStyleListener onRecyclerviewStyleListener) {
        netRequestLayout.setOnRecyclerviewStyleListener(onRecyclerviewStyleListener);
    }

    public void setStateLayout(int loadingLayoutId, int errorLayoutId, int reRequestClickId) {
        netRequestLayout.setStateLayout(loadingLayoutId, errorLayoutId, reRequestClickId);
    }

    public NetRequestLayout getNetRequestLayout() {
        return netRequestLayout;
    }

    public TabLayout getTabLayout() {
        return netRequestLayout.getTabLayout();
    }

}
