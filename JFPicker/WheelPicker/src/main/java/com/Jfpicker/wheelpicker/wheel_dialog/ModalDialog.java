package com.Jfpicker.wheelpicker.wheel_dialog;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
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
import com.Jfpicker.wheelpicker.utils.DensityUtils;

/**
 * 使用了AndroidPicker的Dialog代码，根据自身的需求做了相应的修改
 * 主要修改：DefaultDialogConfig定义全局的弹窗样式。通过构造方法传入 DialogConfig，定义私有的弹窗样式。
 * 源码地址：https://github.com/gzu-liyujiang/AndroidPicker
 */
@SuppressWarnings("unused")
public abstract class ModalDialog extends BottomDialog implements View.OnClickListener {
    protected View headerView;
    protected TextView cancelView;
    protected TextView titleView;
    protected TextView okView;
    protected View topLineView;
    protected View bodyView;
    protected View footerView;


    public ModalDialog(@NonNull Activity activity) {
        super(activity);
    }

    public ModalDialog(@NonNull Activity activity, DialogConfig dialogConfig) {
        super(activity, dialogConfig, ((dialogConfig != null && dialogConfig.getDialogStyle() == DialogStyle.center) ||
                (dialogConfig == null && DefaultDialogConfig.getDialogStyle() == DialogStyle.center))
                ? R.style.DialogTheme_Fade : R.style.DialogTheme_Sheet);
    }

    public ModalDialog(@NonNull Activity activity, @StyleRes int themeResId) {
        super(activity, themeResId);
    }

    @Override
    public void onInit(@Nullable Bundle savedInstanceState) {
        super.onInit(savedInstanceState);
        if (dialogConfig != null && dialogConfig.getDialogStyle() == DialogStyle.center) {
            setGravity(Gravity.CENTER);
            if (dialogConfig.getDialogBackground() != null) {
                setWidth((int) (activity.getResources().getDisplayMetrics().widthPixels * dialogConfig.getDialogBackground().getDialogWidthP()));
            } else {
                setWidth((int) (activity.getResources().getDisplayMetrics().widthPixels * DefaultDialogConfig.getDialogBackground().getDialogWidthP()));
            }
        } else if ((dialogConfig == null && DefaultDialogConfig.getDialogStyle() == DialogStyle.center)) {
            setWidth((int) (activity.getResources().getDisplayMetrics().widthPixels * DefaultDialogConfig.getDialogBackground().getDialogWidthP()));
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
        headerView = createHeaderView();
        if (headerView == null) {
            headerView = new View(activity);
            headerView.setLayoutParams(new LinearLayout.LayoutParams(0, 0));
        }
        rootLayout.addView(headerView);
        topLineView = createTopLineView();
        if (topLineView == null) {
            topLineView = new View(activity);
            topLineView.setLayoutParams(new LinearLayout.LayoutParams(0, 0));
        }
        rootLayout.addView(topLineView);
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
    protected View createHeaderView() {

        return View.inflate(activity, R.layout.dialog_header_style_default, null);

    }

    @Nullable
    protected View createTopLineView() {
        View view = new View(activity);
        view.setLayoutParams(new LinearLayout.LayoutParams(MATCH_PARENT, 1));
        int topLineColor = (dialogConfig != null && dialogConfig.getDialogColor() != null) ?
                dialogConfig.getDialogColor().topLineColor() : DefaultDialogConfig.getDialogColor().topLineColor();
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

        int contentBackgroundColor;
        int connerStyle;
        int connerRadius;
        if (dialogConfig != null && dialogConfig.getDialogBackground() != null) {
            contentBackgroundColor = dialogConfig.getDialogBackground().getContentBackgroundColor();
        } else {
            contentBackgroundColor = DefaultDialogConfig.getDialogBackground().getContentBackgroundColor();
        }
        if (dialogConfig != null && dialogConfig.getDialogBackground() != null) {
            connerStyle = dialogConfig.getDialogBackground().getDialogCornerRound();
        } else {
            connerStyle = DefaultDialogConfig.getDialogBackground().getDialogCornerRound();
        }
        if (dialogConfig != null && dialogConfig.getDialogBackground() != null) {
            connerRadius = dialogConfig.getDialogBackground().getCornerRadius();
        } else {
            connerRadius = DefaultDialogConfig.getDialogBackground().getCornerRadius();
        }
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

        cancelView = contentView.findViewById(R.id.dialog_modal_cancel);

        if (cancelView != null) {
            int cancelTextColor;
            if (dialogConfig != null && dialogConfig.getDialogColor() != null) {
                cancelTextColor = dialogConfig.getDialogColor().cancelTextColor();
            } else {
                cancelTextColor = DefaultDialogConfig.getDialogColor().cancelTextColor();
            }
            cancelView.setTextColor(cancelTextColor);
            cancelView.setOnClickListener(this);
        }
        titleView = contentView.findViewById(R.id.dialog_modal_title);

        if (titleView != null) {
            int titleTextColor;
            if (dialogConfig != null && dialogConfig.getDialogColor() != null) {
                titleTextColor = dialogConfig.getDialogColor().titleTextColor();
            } else {
                titleTextColor = DefaultDialogConfig.getDialogColor().titleTextColor();
            }
            titleView.setTextColor(titleTextColor);
        }
        okView = contentView.findViewById(R.id.dialog_modal_ok);
        if (okView != null) {
            int okTextColor;
            if (dialogConfig != null && dialogConfig.getDialogColor() != null) {
                okTextColor = dialogConfig.getDialogColor().okTextColor();
            } else {
                okTextColor = DefaultDialogConfig.getDialogColor().okTextColor();
            }
            okView.setTextColor(okTextColor);

            okView.setOnClickListener(this);
        }

    }


    @Override
    public void setTitle(@Nullable CharSequence title) {
        if (titleView != null) {
            titleView.setText(title);
        } else {
            super.setTitle(title);
        }
    }

    @Override
    public void setTitle(int titleId) {
        if (titleView != null) {
            titleView.setText(titleId);
        } else {
            super.setTitle(titleId);
        }
    }

    @CallSuper
    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.dialog_modal_cancel) {
            DialogLog.print("cancel clicked");
            onCancel();
            dismiss();
        } else if (id == R.id.dialog_modal_ok) {
            DialogLog.print("ok clicked");
            onOk();
            dismiss();
        }
    }

    protected abstract void onCancel();

    protected abstract void onOk();

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

    public final View getHeaderView() {
        if (headerView == null) {
            headerView = new View(activity);
        }
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

    public final TextView getCancelView() {
        return cancelView;
    }

    public final TextView getTitleView() {
        return titleView;
    }

    public final TextView getOkView() {
        return okView;
    }

}
