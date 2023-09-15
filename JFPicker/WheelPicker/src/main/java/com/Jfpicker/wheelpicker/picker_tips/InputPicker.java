package com.Jfpicker.wheelpicker.picker_tips;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StyleRes;

import com.Jfpicker.wheelpicker.R;
import com.Jfpicker.wheelpicker.dialog.ModalDialog;
import com.Jfpicker.wheelpicker.dialog.config.DialogConfig;
import com.Jfpicker.wheelpicker.picker_tips.listener.OnInputListener;

/**
 * @author Created by JF on  2022/6/9 14:26
 * @description
 */

public class InputPicker extends ModalDialog {


    protected EditText etContent;


    protected OnInputListener onInputListener;


    public InputPicker(@NonNull Activity activity) {
        super(activity, DialogConfig.getCenterConfig(10)
                .setSplitLineColor(0x80999999)
                .setBottomSplitLineColor(0x80999999)
                .setBtnSplitLineColor(0x80999999));
    }

    public InputPicker(@NonNull Activity activity, DialogConfig dialogConfig) {
        super(activity, (dialogConfig == null) ?
                DialogConfig.getCenterConfig(10)
                        .setSplitLineColor(0x80999999)
                        .setBottomSplitLineColor(0x80999999)
                        .setBtnSplitLineColor(0x80999999) : dialogConfig);
    }

    public InputPicker(@NonNull Activity activity, int themeResId) {
        super(activity, DialogConfig.getCenterConfig(10)
                .setSplitLineColor(0x80999999)
                .setBottomSplitLineColor(0x80999999)
                .setBtnSplitLineColor(0x80999999), themeResId);
    }

    public InputPicker(@NonNull Activity activity, DialogConfig dialogConfig, @StyleRes int themeResId) {
        super(activity, (dialogConfig == null) ?
                DialogConfig.getCenterConfig(10)
                        .setSplitLineColor(0x80999999)
                        .setBottomSplitLineColor(0x80999999)
                        .setBtnSplitLineColor(0x80999999) : dialogConfig, themeResId);
    }


    @Nullable
    @Override
    public View createTitleView() {
        return View.inflate(activity, R.layout.jf_titlebar_only_title, null);
    }

    @NonNull
    @Override
    protected View createBodyView() {
        View view = View.inflate(activity, R.layout.jf_picker_edit_layout, null);
        etContent = view.findViewById(R.id.etContent);
        return view;
    }


    @Nullable
    @Override
    protected View createFooterView() {
        View view = View.inflate(activity, R.layout.jf_footer_default, null);
        View viewBottomSplitLine = view.findViewById(R.id.viewBottomSplitLine);
        View viewBtnSplitLine = view.findViewById(R.id.viewBtnSplitLine);
        viewBottomSplitLine.setBackgroundColor((dialogConfig != null) ?
                dialogConfig.getBottomSplitLineColor() : DialogConfig.getDefault().getBottomSplitLineColor());
        viewBtnSplitLine.setBackgroundColor((dialogConfig != null) ?
                dialogConfig.getBtnSplitLineColor() : DialogConfig.getDefault().getBtnSplitLineColor());
        return view;
    }

    @Override
    protected void initView() {
        super.initView();
        if (topLineView!=null){
            topLineView.setBackgroundColor(Color.TRANSPARENT);
        }
    }

    @Override
    protected void onCancel() {
        dismiss();
    }


    @Override
    protected void onConfirm() {
        dismiss();
        if (onInputListener != null) {
            onInputListener.onInput(etContent.getText().toString());
        }
    }

    public void setOnInputListener(OnInputListener onInputListener) {
        this.onInputListener = onInputListener;
    }

    public void setTips(String title, String cancelText, String sureText) {
        titleTextView.setText(title);
        cancelTextView.setText(cancelText);
        confirmTextView.setText(sureText);
    }

    public void setContent(String content) {
        etContent.setText(content);
    }

    public EditText getEtContent() {
        return etContent;
    }

}
