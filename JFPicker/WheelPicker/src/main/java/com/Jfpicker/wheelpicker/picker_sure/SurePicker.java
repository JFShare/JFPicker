package com.Jfpicker.wheelpicker.picker_sure;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.Jfpicker.wheelpicker.R;
import com.Jfpicker.wheelpicker.picker_sure.listener.SureCancelListener;
import com.Jfpicker.wheelpicker.utils.DensityUtils;
import com.Jfpicker.wheelpicker.wheel_dialog.DefaultDialogConfig;
import com.Jfpicker.wheelpicker.wheel_dialog.DialogConfig;
import com.Jfpicker.wheelpicker.wheel_dialog.ModalDialog;

/**
 * @author Created by linyincongxingkeji on  2021/12/3 14:30
 * @description
 */

public class SurePicker extends ModalDialog {


    protected TextView tvTitle;
    protected TextView tvContent;
    protected View viewBottomLine;
    protected View viewDeviderLine;
    protected TextView tvCancel;
    protected TextView tvSure;

    protected SureCancelListener sureCancelListener;

    public SurePicker(@NonNull Activity activity) {
        super(activity);
    }

    public SurePicker(@NonNull Activity activity, DialogConfig dialogConfig) {
        super(activity, dialogConfig);
    }

    public SurePicker(@NonNull Activity activity, int themeResId) {
        super(activity, themeResId);
    }

    @Override
    protected void initView() {
        super.initView();
    }

    @Nullable
    @Override
    protected View createHeaderView() {
        View view = View.inflate(activity, R.layout.picker_sure_header_layout, null);
        tvTitle = view.findViewById(R.id.tvTitle);
        return view;
    }

    @NonNull
    @Override
    protected View createBodyView() {
        View view = View.inflate(activity, R.layout.picker_sure_content_layout, null);
        tvContent = view.findViewById(R.id.tvContent);
        return view;
    }


    @Nullable
    @Override
    protected View createFooterView() {
        View view = View.inflate(activity, R.layout.picker_sure_footer_layout, null);
        viewBottomLine = view.findViewById(R.id.viewBottomLine);
        viewDeviderLine = view.findViewById(R.id.viewDeviderLine);
        tvCancel = view.findViewById(R.id.tvCancel);
        tvSure = view.findViewById(R.id.tvSure);
        tvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (sureCancelListener != null) {
                    sureCancelListener.onSure();
                }
            }
        });

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (sureCancelListener != null) {
                    sureCancelListener.onCancel();
                }
            }
        });
        int lineColor = (dialogConfig != null && dialogConfig.getDialogColor() != null) ?
                dialogConfig.getDialogColor().topLineColor() : DefaultDialogConfig.getDialogColor().topLineColor();
        viewBottomLine.setBackgroundColor(lineColor);
        viewDeviderLine.setBackgroundColor(lineColor);

        return view;
    }

    @Override
    protected void onCancel() {

    }

    @Override
    protected void onOk() {

    }

    public void setSureCancelListener(SureCancelListener sureCancelListener) {
        this.sureCancelListener = sureCancelListener;
    }

    public void setTips(String title, String cancelText, String sureText) {
        tvTitle.setText(title);
        tvCancel.setText(cancelText);
        tvSure.setText(sureText);
    }

    public void setContent(String content) {
        tvContent.setText(content);
    }

    public TextView getTvTitle() {
        return tvTitle;
    }

    public TextView getTvContent() {
        return tvContent;
    }

    public View getViewBottomLine() {
        return viewBottomLine;
    }

    public View getViewDeviderLine() {
        return viewDeviderLine;
    }

    public TextView getTvCancel() {
        return tvCancel;
    }

    public TextView getTvSure() {
        return tvSure;
    }
}
