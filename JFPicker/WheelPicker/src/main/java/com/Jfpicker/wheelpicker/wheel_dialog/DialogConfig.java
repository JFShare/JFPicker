

package com.Jfpicker.wheelpicker.wheel_dialog;

/**
 * @author Created by JF on  2021/11/16
 * @description 单独的Dialog样式，通过构造参数传入
 */

public class DialogConfig {

    private int dialogStyle = DialogStyle.bottom;
    private DialogColor dialogColor;
    private DialogBackground dialogBackground;

    public DialogConfig() {
        super();
    }

    public DialogConfig setDialogStyle(@DialogStyle int style) {
        dialogStyle = style;
        return this;
    }

    @DialogStyle
    public int getDialogStyle() {
        return dialogStyle;
    }

    public DialogBackground getDialogBackground() {
        return dialogBackground;
    }

    public DialogConfig setDialogBackground(DialogBackground dialogBackground) {
        this.dialogBackground = dialogBackground;
        return this;
    }

    public void setDialogColor(DialogColor color) {
        dialogColor = color;
    }

    public DialogColor getDialogColor() {
        return dialogColor;
    }


    public static DialogConfig getDefaultConfig() {
        DialogConfig config = new DialogConfig();
        config.setDialogStyle(DialogStyle.bottom);
        config.setDialogColor(new DialogColor());
        config.setDialogBackground(new DialogBackground());
        return config;
    }
}
