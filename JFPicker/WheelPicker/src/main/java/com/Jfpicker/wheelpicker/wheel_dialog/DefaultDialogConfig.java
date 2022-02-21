package com.Jfpicker.wheelpicker.wheel_dialog;

/**
 * @author Created by JF on  2021/11/16
 * @description 全局的默认Dialog样式
 */

public class DefaultDialogConfig {

    private static int dialogStyle = DialogStyle.bottom;
    private static DialogBackground dialogBackground;
    private static DialogColor dialogColor = new DialogColor();

    private DefaultDialogConfig() {
        super();
    }

    public static void setDialogStyle(@DialogStyle int style) {
        dialogStyle = style;
    }

    @DialogStyle
    public static int getDialogStyle() {
        return dialogStyle;
    }

    public static DialogBackground getDialogBackground() {
        if (dialogBackground == null) {
            dialogBackground = new DialogBackground();
        }
        return dialogBackground;
    }

    public static void setDialogBackground(DialogBackground background) {
        dialogBackground = background;
    }

    public static void setDialogColor(DialogColor color) {
        dialogColor = color;
    }

    public static DialogColor getDialogColor() {
        if (dialogColor == null) {
            dialogColor = new DialogColor();
        }
        return dialogColor;
    }
}
