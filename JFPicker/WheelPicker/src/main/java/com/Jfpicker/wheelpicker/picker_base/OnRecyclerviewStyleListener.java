package com.Jfpicker.wheelpicker.picker_base;

import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * @author Created by JF on  2021/11/16
 * @description 选项RecyclerView类弹窗的样式修改回调
 */

public interface OnRecyclerviewStyleListener {

    void onStyle(FrameLayout itemView, TextView itemTextView, Object entity, boolean isSelect);
}
