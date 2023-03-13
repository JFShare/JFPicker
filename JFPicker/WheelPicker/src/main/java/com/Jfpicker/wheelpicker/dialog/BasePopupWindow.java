package com.Jfpicker.wheelpicker.dialog;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Outline;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.PopupWindow;

import com.Jfpicker.wheelpicker.R;

/**
 * *********************
 *
 * @Author JF
 * 创建日期 ：2023/3/13
 * 代码描述 ：
 * *********************
 */
public class BasePopupWindow extends PopupWindow {

    private Context mContext;

    public BasePopupWindow(Context context, int contentLayoutId) {
        super(context);
        mContext = context;

        // 设置PopupWindow的布局

        View contentView = LayoutInflater.from(mContext).inflate(contentLayoutId, null);
        setContentView(contentView);

        // 设置PopupWindow的宽和高
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);

        // 设置PopupWindow的背景
        setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        // 设置PopupWindow是否可以获取焦点和响应事件
        setFocusable(true);
        setTouchable(true);
        setOutsideTouchable(true);

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            setElevation(10f);
//        }

    }


    @Override
    public void showAsDropDown(View anchor) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            showAtLocation(anchor, Gravity.CENTER_HORIZONTAL, 0, 0);
        } else {
            showAsDropDown(anchor);
        }

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            getContentView().setOutlineProvider(new ViewOutlineProvider() {
//                @Override
//                public void getOutline(View view, Outline outline) {
//                    outline.setRect(0, 0, view.getWidth(), view.getHeight());
//                }
//            });
//            getContentView().setClipToOutline(true); // Enable clipping
//        }

    }


}
