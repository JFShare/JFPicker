
package com.Jfpicker.wheelpicker.wheel_dialog;

import android.util.Log;

import androidx.annotation.NonNull;

/**
 * 使用了AndroidPicker的Dialog代码，根据自身的需求做了相应的修改
 * 主要修改：DefaultDialogConfig定义全局的弹窗样式。通过构造方法传入 DialogConfig，定义私有的弹窗样式。
 * 源码地址：https://github.com/gzu-liyujiang/AndroidPicker
 */
public final class DialogLog {
    private static final String TAG = "AndroidPicker";
    private static boolean enable = false;

    private DialogLog() {
        super();
    }

    /**
     * 启用调试日志
     */
    public static void enable() {
        enable = true;
    }

    /**
     * 打印调试日志
     *
     * @param log 日志信息
     */
    public static void print(@NonNull Object log) {
        if (!enable) {
            return;
        }
        Log.d(TAG, log.toString());
    }

}
