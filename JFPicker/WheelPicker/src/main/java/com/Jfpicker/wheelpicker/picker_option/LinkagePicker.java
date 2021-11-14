package com.Jfpicker.wheelpicker.picker_option;

import android.app.Activity;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.StyleRes;

import com.Jfpicker.wheelpicker.picker_option.entity.LinkageProvider;
import com.Jfpicker.wheelpicker.picker_option.listener.OnLinkagePickedListener;
import com.Jfpicker.wheelpicker.picker_option.widget.LinkageWheelLayout;
import com.Jfpicker.wheelpicker.wheel_dialog.ModalDialog;


@SuppressWarnings({"WeakerAccess", "unused"})
public class LinkagePicker extends ModalDialog {
    protected LinkageWheelLayout wheelLayout;
    private OnLinkagePickedListener onLinkagePickedListener;

    public LinkagePicker(@NonNull Activity activity) {
        super(activity);
    }

    public LinkagePicker(@NonNull Activity activity, @StyleRes int themeResId) {
        super(activity, themeResId);
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
        titleView.setText("请选择");
    }

    @Override
    protected void onCancel() {

    }

    @Override
    protected void onOk() {
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
