package com.Jfpicker.wheelpicker.picker_tips;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StyleRes;

import com.Jfpicker.wheelpicker.R;
import com.Jfpicker.wheelpicker.picker_tips.listener.OnConfirmCancelListener;

import com.Jfpicker.wheelpicker.dialog.config.DialogConfig;
import com.Jfpicker.wheelpicker.dialog.ModalDialog;

/**
 * @author Created by JF on  2021/12/3 14:30
 * @description 提示弹窗
 */

public class TipsPicker extends ModalDialog {

    protected TextView tvContent;

    protected OnConfirmCancelListener confirmCancelListener;


    public TipsPicker(@NonNull Activity activity) {
        super(activity, DialogConfig.getCenterConfig(10)
                .setSplitLineColor(0x80999999)
                .setBottomSplitLineColor(0x80999999)
                .setBtnSplitLineColor(0x80999999)
        );
    }

    public TipsPicker(@NonNull Activity activity, DialogConfig dialogConfig) {
        super(activity, (dialogConfig == null) ? DialogConfig.getCenterConfig(10)
                .setSplitLineColor(0x80999999)
                .setBottomSplitLineColor(0x80999999)
                .setBtnSplitLineColor(0x80999999) : dialogConfig);
    }

    public TipsPicker(@NonNull Activity activity, int themeResId) {
        super(activity, DialogConfig.getCenterConfig(10)
                .setSplitLineColor(0x80999999)
                .setBottomSplitLineColor(0x80999999)
                .setBtnSplitLineColor(0x80999999), themeResId);
    }

    public TipsPicker(@NonNull Activity activity, DialogConfig dialogConfig, @StyleRes int themeResId) {
        super(activity, (dialogConfig == null) ? DialogConfig.getCenterConfig(10)
                .setSplitLineColor(0x80999999)
                .setBottomSplitLineColor(0x80999999)
                .setBtnSplitLineColor(0x80999999) : dialogConfig, themeResId);
    }


    @Nullable
    @Override
    public View createTitleView() {
        return View.inflate(activity, R.layout.titlebar_only_title, null);
    }


    @NonNull
    @Override
    protected View createBodyView() {
        View view = View.inflate(activity, R.layout.picker_tips, null);
        tvContent = view.findViewById(R.id.tvContent);
        return view;
    }


    @Nullable
    @Override
    protected View createFooterView() {
        View view = View.inflate(activity, R.layout.footer_default, null);
        View viewBottomSplitLine = view.findViewById(R.id.viewBottomSplitLine);
        View viewBtnSplitLine = view.findViewById(R.id.viewBtnSplitLine);
        viewBottomSplitLine.setBackgroundColor((dialogConfig != null) ?
                dialogConfig.getBottomSplitLineColor() : DialogConfig.getDefault().getBottomSplitLineColor());
        viewBtnSplitLine.setBackgroundColor((dialogConfig != null) ?
                dialogConfig.getBtnSplitLineColor() : DialogConfig.getDefault().getBtnSplitLineColor());
        return view;
    }

    @Override
    protected void onCancel() {
        dismiss();
        if (confirmCancelListener != null) {
            confirmCancelListener.onCancel();
        }
    }


    @Override
    protected void onConfirm() {
        dismiss();
        if (confirmCancelListener != null) {
            confirmCancelListener.onSure();
        }
    }

    public void setOnConfirmCancelListener(OnConfirmCancelListener confirmCancelListener) {
        this.confirmCancelListener = confirmCancelListener;
    }

    public void setTips(String title, String cancelText, String sureText) {
        titleTextView.setText(title);
        cancelTextView.setText(cancelText);
        confirmTextView.setText(sureText);
    }

    public void setContent(String content) {
        tvContent.setText(content);
    }

    public TextView getTvContent() {
        return tvContent;
    }

}
