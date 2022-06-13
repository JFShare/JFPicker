package com.Jfpicker.wheelpicker.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.CallSuper;
import androidx.annotation.ColorInt;
import androidx.annotation.Dimension;
import androidx.annotation.DrawableRes;
import androidx.annotation.FloatRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StyleRes;

import com.Jfpicker.wheelpicker.R;
import com.Jfpicker.wheelpicker.utils.WheelLogUtils;
import com.Jfpicker.wheelpicker.dialog.annotation.CornerRound;
import com.Jfpicker.wheelpicker.dialog.config.DialogConfig;

/**
 * 使用了AndroidPicker的Dialog代码，根据自身的需求做了相应的修改
 * 主要修改：DefaultDialogConfig定义全局的弹窗样式。通过构造方法传入 DialogConfig，定义私有的弹窗样式。
 * 源码地址：https://github.com/gzu-liyujiang/AndroidPicker
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public abstract class BaseDialog extends Dialog implements
        DialogInterface.OnShowListener, DialogInterface.OnDismissListener {
    public static final int MATCH_PARENT = ViewGroup.LayoutParams.MATCH_PARENT;
    public static final int WRAP_CONTENT = ViewGroup.LayoutParams.WRAP_CONTENT;
    protected Activity activity;
    protected View contentView;
    protected DialogConfig dialogConfig;

    public BaseDialog(@NonNull Activity activity) {
        this(activity, null, R.style.DialogTheme_Base);
    }

    public BaseDialog(@NonNull Activity activity, DialogConfig dialogConfig) {
        this(activity, dialogConfig, R.style.DialogTheme_Base);
    }

    public BaseDialog(@NonNull Activity activity, @StyleRes int themeResId) {
        this(activity, null, themeResId);
    }

    public BaseDialog(@NonNull Activity activity, DialogConfig dialogConfig, @StyleRes int themeResId) {
        super(activity, themeResId);
        this.dialogConfig = dialogConfig;
        init(activity);
    }

    private void init(Activity activity) {
        this.activity = activity;
        setOwnerActivity(activity);
        setCanceledOnTouchOutside(false);
        setCancelable(false);
        super.setOnShowListener(this);
        super.setOnDismissListener(this);
        Window window = super.getWindow();
        if (window != null) {
            window.requestFeature(Window.FEATURE_NO_TITLE);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            window.setLayout(activity.getResources().getDisplayMetrics().widthPixels, WindowManager.LayoutParams.WRAP_CONTENT);
            window.setGravity(Gravity.CENTER);
            window.getDecorView().setPadding(0, 0, 0, 0);
        }
        onInit(null);
        // 调用create或show才能触发onCreate
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            super.create();
        } else {
            readyView();
        }
    }

    @Deprecated
    @CallSuper
    protected void onInit(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
        WheelLogUtils.print("dialog onInit");
    }

    @CallSuper
    protected void onInit(@Nullable Bundle savedInstanceState) {
        onInit(activity, savedInstanceState);
    }

    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WheelLogUtils.print("dialog onCreate");
        if (contentView == null) {
            readyView();
        }
    }

    private void readyView() {
        contentView = createContentView();
        contentView.setFocusable(true);
        contentView.setFocusableInTouchMode(true);
        setContentView(contentView);
        initView();
    }

    @NonNull
    protected abstract View createContentView();
    public final View getContentView() {
        return contentView;
    }


    @SuppressWarnings("DeprecatedIsStillUsed")
    @Deprecated
    @CallSuper
    protected void initView(View contentView) {
        WheelLogUtils.print("dialog initView");
    }

    @CallSuper
    protected void initView() {
        initView(contentView);
    }

    public final void disableCancel() {
        setCancelable(false);
        setCanceledOnTouchOutside(false);
    }

    public final void setBackgroundColor(@ColorInt int color) {
        setBackgroundColor(CornerRound.No, color);
    }

    public final void setBackgroundColor(@CornerRound int cornerRound, @ColorInt int color) {
        setBackgroundColor(cornerRound, 20, color);
    }

    public final void setBackgroundColor(@CornerRound int cornerRound,
                                         @Dimension(unit = Dimension.DP) int radius, @ColorInt int color) {
        if (contentView == null) {
            return;
        }
        float radiusInPX = contentView.getResources().getDisplayMetrics().density * radius;
        contentView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        Drawable drawable;
        switch (cornerRound) {
            case CornerRound.Top:
                float[] outerRadii = new float[]{radiusInPX, radiusInPX, radiusInPX, radiusInPX, 0, 0, 0, 0};
                ShapeDrawable shapeDrawable = new ShapeDrawable(new RoundRectShape(outerRadii, null, null));
                shapeDrawable.setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN));
                drawable = shapeDrawable;
                break;
            case CornerRound.All:
                GradientDrawable gradientDrawable = new GradientDrawable();
                gradientDrawable.setCornerRadius(radiusInPX);
                gradientDrawable.setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN));
                drawable = gradientDrawable;
                break;
            default:
                drawable = new ColorDrawable(color);
                break;
        }
        contentView.setBackground(drawable);
    }

    public final void setBackgroundResource(@DrawableRes int resId) {
        if (contentView == null) {
            return;
        }
        contentView.setBackgroundResource(resId);
    }

    public final void setBackgroundDrawable(Drawable drawable) {
        if (contentView == null) {
            return;
        }
        contentView.setBackground(drawable);
    }

    public final void setLayout(int width, int height) {
        getWindow().setLayout(width, height);
    }

    public final void setWidth(int width) {
        getWindow().setLayout(width, getWindow().getAttributes().height);
    }

    public final void setHeight(int height) {
        getWindow().setLayout(getWindow().getAttributes().width, height);
    }

    public final void setGravity(int gravity) {
        getWindow().setGravity(gravity);
    }

    public final void setDimAmount(@FloatRange(from = 0, to = 1) float amount) {
        getWindow().setDimAmount(amount);
    }

    public final void setAnimationStyle(@StyleRes int animRes) {
        getWindow().setWindowAnimations(animRes);
    }

    @Override
    public void setOnShowListener(@Nullable OnShowListener listener) {
        if (listener == null) {
            return;
        }
        final OnShowListener current = this;
        super.setOnShowListener(dialog -> {
            current.onShow(dialog);
            listener.onShow(dialog);
        });
    }

    @Override
    public void setOnDismissListener(@Nullable OnDismissListener listener) {
        if (listener == null) {
            return;
        }
        final OnDismissListener current = this;
        super.setOnDismissListener(dialog -> {
            current.onDismiss(dialog);
            listener.onDismiss(dialog);
        });
    }

    @CallSuper
    @Override
    public void show() {
        if (isShowing()) {
            return;
        }
        try {
            super.show();
            WheelLogUtils.print("dialog show");
        } catch (Exception e) {
            WheelLogUtils.print(e);
        }
    }

    @CallSuper
    @Override
    public void dismiss() {
        if (!isShowing()) {
            return;
        }
        try {
            super.dismiss();
            WheelLogUtils.print("dialog dismiss");
        } catch (Exception e) {
            WheelLogUtils.print(e);
        }
    }

    @CallSuper
    @Override
    public void onAttachedToWindow() {
        WheelLogUtils.print("dialog attached to window");
        super.onAttachedToWindow();
        initData();
    }

    @CallSuper
    protected void initData() {
        WheelLogUtils.print("dialog initData");
    }

    @CallSuper
    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        WheelLogUtils.print("dialog detached from window");
    }

    @CallSuper
    @Override
    public void onShow(DialogInterface dialog) {
        WheelLogUtils.print("dialog onShow");
    }


    @CallSuper
    @Override
    public void onDismiss(DialogInterface dialog) {
        WheelLogUtils.print("dialog onDismiss");
    }

}
