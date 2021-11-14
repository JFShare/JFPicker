
package com.Jfpicker.wheelpicker.wheel_dialog;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StyleRes;
import com.Jfpicker.wheelpicker.R;

public abstract class BottomDialog extends BaseDialog {
    protected View maskView;

    public BottomDialog(@NonNull Activity activity) {
        super(activity, R.style.DialogTheme_Sheet);
    }

    public BottomDialog(@NonNull Activity activity, @StyleRes int themeResId) {
        super(activity, themeResId);
    }

    @Override
    public void onInit(@Nullable Bundle savedInstanceState) {
        super.onInit(savedInstanceState);
        setCancelable(true);
        setCanceledOnTouchOutside(true);
        setWidth(activity.getResources().getDisplayMetrics().widthPixels);
        setGravity(Gravity.BOTTOM);
    }

    @Override
    public void onShow(DialogInterface dialog) {
        super.onShow(dialog);
        if (enableMaskView()) {
            addMaskView();
        }
    }

    protected boolean enableMaskView() {
        return true;
    }

    protected void addMaskView() {
        // 通过自定义遮罩层视图解决自带弹窗遮罩致使系统导航栏背景过暗不一体问题
        try {
            // 取消弹窗遮罩效果 android:backgroundDimEnabled=false
            getWindow().setDimAmount(0);
            // 自定义遮罩层视图
            WindowManager.LayoutParams params = new WindowManager.LayoutParams();
            params.width = WindowManager.LayoutParams.MATCH_PARENT;
            Point screenRealSize = new Point();
            activity.getWindowManager().getDefaultDisplay().getRealSize(screenRealSize);
            int navBarIdentifier = activity.getResources().getIdentifier("navigation_bar_height", "dimen", "android");
            params.height = screenRealSize.y - activity.getResources().getDimensionPixelSize(navBarIdentifier);
            params.gravity = Gravity.TOP;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                // 取消弹窗遮罩效果后，异形屏的状态栏没法被自定义的遮罩试图挡住，需结合systemUiVisibility
                params.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
            }
            params.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            params.type = WindowManager.LayoutParams.TYPE_APPLICATION_PANEL;
            params.format = PixelFormat.TRANSLUCENT;
            params.token = activity.getWindow().getDecorView().getWindowToken();
            params.softInputMode = WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN;
            maskView = new View(activity);
            maskView.setBackgroundColor(0x7F000000);
            maskView.setFitsSystemWindows(false);
            maskView.setOnKeyListener((view, keyCode, event) -> {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    dismiss();
                    return true;
                }
                return false;
            });
            activity.getWindowManager().addView(maskView, params);
            DialogLog.print("dialog add mask view");
        } catch (Exception e) {
            DialogLog.print(e);
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        removeMaskView();
        super.onDismiss(dialog);
    }

    protected void removeMaskView() {
        if (maskView == null) {
            DialogLog.print("mask view is null");
            return;
        }
        try {
            activity.getWindowManager().removeViewImmediate(maskView);
            DialogLog.print("dialog remove mask view");
        } catch (Exception e) {
            DialogLog.print(e);
        }
    }

}
