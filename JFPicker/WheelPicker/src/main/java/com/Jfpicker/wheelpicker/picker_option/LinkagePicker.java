package com.Jfpicker.wheelpicker.picker_option;

import android.app.Activity;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.StyleRes;

import com.Jfpicker.wheelpicker.picker_option.entity.LinkageProvider;
import com.Jfpicker.wheelpicker.picker_option.listener.OnLinkagePickedListener;
import com.Jfpicker.wheelpicker.picker_option.widget.LinkageWheelLayout;
import com.Jfpicker.wheelpicker.dialog.config.DialogConfig;
import com.Jfpicker.wheelpicker.dialog.ModalDialog;

/**
 * 三级联动的选择弹窗
 * 参考了AndroidPicker的LinkagePicker代码
 * 源码地址：https://github.com/gzu-liyujiang/AndroidPicker
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public class LinkagePicker extends ModalDialog {
    protected LinkageWheelLayout wheelLayout;
    private OnLinkagePickedListener onLinkagePickedListener;

    public LinkagePicker(@NonNull Activity activity) {
        super(activity);
    }

    public LinkagePicker(@NonNull Activity activity, DialogConfig dialogConfig) {
        super(activity, dialogConfig);
    }

    public LinkagePicker(@NonNull Activity activity, @StyleRes int themeResId) {
        super(activity, themeResId);
    }

    public LinkagePicker(@NonNull Activity activity, DialogConfig dialogConfig, @StyleRes int themeResId) {
        super(activity, dialogConfig, themeResId);
    }

    @NonNull
    @Override
    protected View createBodyView() {
        wheelLayout = new LinkageWheelLayout(activity);
        return wheelLayout;
    }

    @Override
    protected void initView() {
        super.initView();
        titleTextView.setText("请选择");
    }

    @Override
    protected void onCancel() {

    }

    @Override
    protected void onConfirm() {
        if (onLinkagePickedListener != null) {
            Object first = wheelLayout.getSelectFirst();
            Object second = wheelLayout.getSelectSecond();
            Object third = wheelLayout.getSelectThird();
            onLinkagePickedListener.onLinkagePicked(first, second, third);
        }
    }

    public void setData(@NonNull LinkageProvider data) {
        wheelLayout.setData(data);
    }

    public void setDefaultValue(Object first, Object second, Object third) {
        wheelLayout.setDefaultValue(first, second, third);
    }

    public void setOnLinkagePickedListener(OnLinkagePickedListener onLinkagePickedListener) {
        this.onLinkagePickedListener = onLinkagePickedListener;
    }

    public final LinkageWheelLayout getWheelLayout() {
        return wheelLayout;
    }


}
