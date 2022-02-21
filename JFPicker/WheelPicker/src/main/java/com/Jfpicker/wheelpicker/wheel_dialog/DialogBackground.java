package com.Jfpicker.wheelpicker.wheel_dialog;

import android.graphics.Color;

import androidx.annotation.ColorInt;

/**
 * @author Created by JF on  2021/11/16
 * @description Dialog的背景样式
 */

public class DialogBackground {
    private int contentBackgroundColor = Color.WHITE;
    private int dialogCornerRound = CornerRound.No;
    private int cornerRadius = 20;
    private float dialogWidthP = 0.8f;

    public int getDialogCornerRound() {
        return dialogCornerRound;
    }

    public DialogBackground setDialogCornerRound(@CornerRound int dialogCornerRound) {
        this.dialogCornerRound = dialogCornerRound;
        return this;
    }

    @CornerRound
    public int getCornerRadius() {
        return cornerRadius;
    }

    public DialogBackground setCornerRadius(int cornerRadius) {
        this.cornerRadius = cornerRadius;
        return this;
    }


    public DialogBackground setContentBackgroundColor(@ColorInt int color) {
        this.contentBackgroundColor = color;
        return this;
    }

    @ColorInt
    public int getContentBackgroundColor() {
        return contentBackgroundColor;
    }

    public float getDialogWidthP() {
        return dialogWidthP;
    }

    public DialogBackground setDialogWidthP(float dialogWidthP) {
        this.dialogWidthP = dialogWidthP;
        return this;
    }

}
