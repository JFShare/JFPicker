package com.Jfpicker.wheelpicker.dialog;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.CallSuper;
import androidx.annotation.Dimension;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StyleRes;

import com.Jfpicker.wheelpicker.R;
import com.Jfpicker.wheelpicker.utils.WheelLogUtils;
import com.Jfpicker.wheelpicker.dialog.annotation.CornerRound;
import com.Jfpicker.wheelpicker.dialog.annotation.DialogStyle;

import com.Jfpicker.wheelpicker.dialog.config.DialogConfig;

/**
 * 使用了AndroidPicker的Dialog代码，根据自身的需求做了相应的修改
 * 主要修改：DefaultDialogConfig定义全局的弹窗样式。通过构造方法传入 DialogConfig，定义私有的弹窗样式。
 * 源码地址：https://github.com/gzu-liyujiang/AndroidPicker
 */
@SuppressWarnings("unused")
public abstract class ModalDialog extends BottomDialog implements View.OnClickListener {

    protected View titleView;
    protected View headerView;
    protected TextView cancelTextView;
    protected TextView titleTextView;
    protected TextView confirmTextView;
    protected View topLineView;
    protected View bodyView;
    protected View footerView;


    public ModalDialog(@NonNull Activity activity) {
        super(activity);
    }

    public ModalDialog(@NonNull Activity activity, DialogConfig dialogConfig) {
        super(activity, dialogConfig, ((dialogConfig != null && dialogConfig.getDialogStyle() == DialogStyle.center) ||
                (dialogConfig == null && DialogConfig.getDefault().getDialogStyle() == DialogStyle.center))
                ? R.style.DialogTheme_Fade : R.style.DialogTheme_Sheet);
    }

    public ModalDialog(@NonNull Activity activity, @StyleRes int themeResId) {
        super(activity, themeResId);
    }

    public ModalDialog(@NonNull Activity activity, DialogConfig dialogConfig, @StyleRes int themeResId) {
        super(activity, dialogConfig, themeResId);
    }

    @Override
    public void onInit(@Nullable Bundle savedInstanceState) {
        super.onInit(savedInstanceState);
        if (dialogConfig != null && dialogConfig.getDialogStyle() == DialogStyle.center) {
            setGravity(Gravity.CENTER);
            if (dialogConfig != null) {
                setWidth((int) (activity.getResources().getDisplayMetrics().widthPixels * dialogConfig.getDialogWidthP()));
            } else {
                setWidth((int) (activity.getResources().getDisplayMetrics().widthPixels * DialogConfig.getDefault().getDialogWidthP()));
            }
        } else if ((dialogConfig == null && DialogConfig.getDefault().getDialogStyle() == DialogStyle.center)) {
            setWidth((int) (activity.getResources().getDisplayMetrics().widthPixels * DialogConfig.getDefault().getDialogWidthP()));
            setGravity(Gravity.CENTER);
        }

    }

    @NonNull
    @Override
    protected View createContentView() {
        LinearLayout rootLayout = new LinearLayout(activity);
        rootLayout.setLayoutParams(new LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT));
        rootLayout.setOrientation(LinearLayout.VERTICAL);
        rootLayout.setGravity(Gravity.CENTER);
        rootLayout.setPadding(0, 0, 0, 0);
        rootLayout.setBackgroundColor(Color.WHITE);
        titleView = createTitleView();
        if (titleView == null) {
            titleView = new View(activity);
            titleView.setLayoutParams(new LinearLayout.LayoutParams(0, 0));
        }
        rootLayout.addView(titleView);
        topLineView = createTopLineView();
        if (topLineView == null) {
            topLineView = new View(activity);
            topLineView.setLayoutParams(new LinearLayout.LayoutParams(0, 0));
        }
        rootLayout.addView(topLineView);
        headerView = createHeaderView();
        if (headerView == null) {
            headerView = new View(activity);
            headerView.setLayoutParams(new LinearLayout.LayoutParams(0, 0));
        }
        rootLayout.addView(headerView);
        bodyView = createBodyView();
        rootLayout.addView(bodyView, new LinearLayout.LayoutParams(MATCH_PARENT, 0, 1.0f));
        footerView = createFooterView();
        if (footerView == null) {
            footerView = new View(activity);
            footerView.setLayoutParams(new LinearLayout.LayoutParams(0, 0));
        }
        rootLayout.addView(footerView);
        return rootLayout;
    }

    @Nullable
    public View createTitleView() {
        return View.inflate(activity, R.layout.titlebar_default, null);
    }

    @Nullable
    protected View createHeaderView() {
        return null;
    }

    @Nullable
    protected View createTopLineView() {
        View view = new View(activity);
        view.setLayoutParams(new LinearLayout.LayoutParams(MATCH_PARENT, 1));
        int topLineColor = (dialogConfig != null) ?
                dialogConfig.getSplitLineColor() : DialogConfig.getDefault().getSplitLineColor();
        view.setBackgroundColor(topLineColor);
        return view;
    }

    @NonNull
    protected abstract View createBodyView();

    @Nullable
    protected View createFooterView() {
        return null;
    }

    @CallSuper
    @Override
    protected void initView() {
        super.initView();
        int contentBackgroundColor = (dialogConfig != null) ? dialogConfig.getContentBackgroundColor() :
                DialogConfig.getDefault().getContentBackgroundColor();
        int connerStyle = (dialogConfig != null) ? dialogConfig.getDialogCornerRound() :
                DialogConfig.getDefault().getDialogCornerRound();
        int connerRadius = (dialogConfig != null) ? dialogConfig.getCornerRadius() :
                DialogConfig.getDefault().getCornerRadius();
        switch (connerStyle) {
            case CornerRound.No:
            case CornerRound.Top:
            case CornerRound.All:
                setBackgroundColor(connerStyle, connerRadius, contentBackgroundColor);
                break;
            default:
                setBackgroundColor(CornerRound.No, connerRadius, contentBackgroundColor);
                break;
        }

        cancelTextView = contentView.findViewById(R.id.tvPickerCancel);
        if (cancelTextView != null) {
            int cancelTextColor;
            if (dialogConfig != null) {
                cancelTextView.setTextColor(dialogConfig.getCancelTextColor());
            } else {
                cancelTextView.setTextColor(DialogConfig.getDefault().getCancelTextColor());
            }
            cancelTextView.setOnClickListener(this);
        }

        titleTextView = contentView.findViewById(R.id.tvPickerTitle);
        if (titleTextView != null) {
            if (dialogConfig != null) {
                titleTextView.setTextColor(dialogConfig.getTitleTextColor());
                if (dialogConfig.isTitleBold()) {
                    Typeface boldTypeface = Typeface.defaultFromStyle(Typeface.BOLD);
                    titleTextView.setTypeface(boldTypeface);
                }
            } else {
                titleTextView.setTextColor(DialogConfig.getDefault().getTitleTextColor());
                if (DialogConfig.getDefault().isTitleBold()) {
                    Typeface boldTypeface = Typeface.defaultFromStyle(Typeface.BOLD);
                    titleTextView.setTypeface(boldTypeface);
                }
            }
        }

        confirmTextView = contentView.findViewById(R.id.tvPickerConfirm);
        if (confirmTextView != null) {
            if (dialogConfig != null) {
                confirmTextView.setTextColor(dialogConfig.getConfirmTextColor());
            } else {
                confirmTextView.setTextColor(DialogConfig.getDefault().getConfirmTextColor());
                Log.e("设置了颜色", DialogConfig.getDefault().getConfirmTextColor() + "");
            }
            confirmTextView.setOnClickListener(this);
        }

    }


    @Override
    public void setTitle(@Nullable CharSequence title) {
        if (titleTextView != null) {
            titleTextView.setText(title);
        } else {
            super.setTitle(title);
        }
    }

    @Override
    public void setTitle(int titleId) {
        if (titleTextView != null) {
            titleTextView.setText(titleId);
        } else {
            super.setTitle(titleId);
        }
    }

    @CallSuper
    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.tvPickerCancel) {
            WheelLogUtils.print("cancel clicked");
            onCancel();
            dismiss();
        } else if (id == R.id.tvPickerConfirm) {
            WheelLogUtils.print("confirm clicked");
            onConfirm();
            dismiss();
        }
    }

    protected abstract void onCancel();

    protected abstract void onConfirm();

    public final void setBodyWidth(@Dimension(unit = Dimension.DP) @IntRange(from = 50) int bodyWidth) {
        ViewGroup.LayoutParams layoutParams = bodyView.getLayoutParams();
        int width = WRAP_CONTENT;
        if (bodyWidth != WRAP_CONTENT && bodyWidth != MATCH_PARENT) {
            width = (int) (bodyView.getResources().getDisplayMetrics().density * bodyWidth);
        }
        layoutParams.width = width;
        bodyView.setLayoutParams(layoutParams);
    }

    public final void setBodyHeight(@Dimension(unit = Dimension.DP) @IntRange(from = 50) int bodyHeight) {
        ViewGroup.LayoutParams layoutParams = bodyView.getLayoutParams();
        int height = WRAP_CONTENT;
        if (bodyHeight != WRAP_CONTENT && bodyHeight != MATCH_PARENT) {
            height = (int) (bodyView.getResources().getDisplayMetrics().density * bodyHeight);
        }
        layoutParams.height = height;
        bodyView.setLayoutParams(layoutParams);
    }


    public final View getTitleView() {
        return titleView;
    }

    public final TextView getCancelTextView() {
        return cancelTextView;
    }

    public final TextView getTitleTextView() {
        return titleTextView;
    }

    public final TextView getConfirmTextView() {
        return confirmTextView;
    }

    public final View getHeaderView() {
        return headerView;
    }

    public final View getTopLineView() {
        return topLineView;
    }

    public final View getBodyView() {
        return bodyView;
    }

    public final View getFooterView() {
        return footerView;
    }


}
