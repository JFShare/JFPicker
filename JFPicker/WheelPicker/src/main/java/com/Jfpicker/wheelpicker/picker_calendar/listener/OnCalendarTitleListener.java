package com.Jfpicker.wheelpicker.picker_calendar.listener;

import android.widget.TextView;

/**
 * @author Created by JF on  2021/11/25
 * @description 日历标题点击事件回调
 */

public interface OnCalendarTitleListener {
    void onClose();

    void onSure();

    void onTitleClick(TextView tvTitle);
}
