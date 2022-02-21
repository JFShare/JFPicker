package com.Jfpicker.wheelpicker.wheel_dialog;

import androidx.annotation.ColorInt;

import java.io.Serializable;

/**
 * 使用了AndroidPicker的Dialog代码，根据自身的需求做了相应的修改
 * 主要修改：DefaultDialogConfig定义全局的弹窗样式。通过构造方法传入 DialogConfig，定义私有的弹窗样式。
 * 源码地址：https://github.com/gzu-liyujiang/AndroidPicker
 */
public class DialogColor implements Serializable {

    private int topLineColor = 0xFFEDEDED;
    private int titleTextColor = 0xFF000000;
    private int cancelTextColor = 0xFF999999;
    private int okTextColor = 0xFF333333;

    public DialogColor topLineColor(@ColorInt int color) {
        this.topLineColor = color;
        return this;
    }

    @ColorInt
    public int topLineColor() {
        return topLineColor;
    }

    public DialogColor titleTextColor(@ColorInt int color) {
        this.titleTextColor = color;
        return this;
    }

    @ColorInt
    public int titleTextColor() {
        return titleTextColor;
    }

    public DialogColor cancelTextColor(@ColorInt int color) {
        this.cancelTextColor = color;
        return this;
    }

    @ColorInt
    public int cancelTextColor() {
        return cancelTextColor;
    }

    public DialogColor okTextColor(@ColorInt int color) {
        this.okTextColor = color;
        return this;
    }

    @ColorInt
    public int okTextColor() {
        return okTextColor;
    }


}
