

package com.Jfpicker.wheelpicker.dialog.config;

import android.graphics.Color;

import androidx.annotation.ColorInt;
import androidx.annotation.Dimension;

import com.Jfpicker.wheelpicker.dialog.annotation.CornerRound;
import com.Jfpicker.wheelpicker.dialog.annotation.DialogStyle;

/**
 * @author Created by JF on  2021/11/16
 * @description 单独的Dialog样式，通过构造参数传入
 */

public class DialogConfig {

    public static DialogConfig defaultDialogConfig = new DialogConfig();

    public static DialogConfig getDefault() {
        return defaultDialogConfig;
    }

    private int dialogStyle = DialogStyle.bottom; // 居中还是底部
    private int titleTextColor = 0xFF333333; //标题文本颜色
    private boolean titleBold = false; //标题是否加粗
    private int confirmTextColor = 0xFF333333; // 确认文本颜色
    private int cancelTextColor = 0xFF999999; // 取消文本颜色
    private int splitLineColor = 0xFFEDEDED; // 顶部分割线颜色
    private int maskColor = 0x7F000000; //遮挡层颜色
    private int bottomSplitLineColor = 0xFFEDEDED;//底部间隔线颜色
    private int btnSplitLineColor = 0xFFEDEDED;//底部间隔线颜色
    private int contentBackgroundColor = Color.WHITE; //弹窗整体背景色
    private int dialogCornerRound = CornerRound.No; //弹窗圆角样式
    private int cornerRadius = 10; //弹窗圆角大小 dp
    private float dialogWidthP = 0.8f; //弹窗宽度占屏幕宽度的百分比
    private int calendarSingleColor = 0xFF3E7EEA; //日历弹窗 单选背景色
    private int calendarMultiColor = 0xFF3E7EEA;//日历弹窗 多选背景色
    private int calendarRangeColor = 0xFF3E7EEA;//日历弹窗 范围选择背景色

    public DialogConfig() {

    }

    public static DialogConfig getCenterConfig(int cornerRadius) {
        DialogConfig config = new DialogConfig();
        config.setCornerRadius(cornerRadius);
        config.setDialogCornerRound(CornerRound.All);
        config.setDialogStyle(DialogStyle.center);
        return config;
    }

    public static DialogConfig getBottomConfig(int cornerRadius) {
        DialogConfig config = new DialogConfig();
        config.setCornerRadius(cornerRadius);
        config.setDialogCornerRound(CornerRound.Top);
        config.setDialogStyle(DialogStyle.bottom);
        return config;
    }

    public DialogConfig setDialogStyle(@DialogStyle int style) {
        dialogStyle = style;
        return this;
    }

    @DialogStyle
    public int getDialogStyle() {
        return dialogStyle;
    }

    public DialogConfig setTitleTextColor(@ColorInt int color) {
        this.titleTextColor = color;
        return this;
    }

    @ColorInt
    public int getTitleTextColor() {
        return titleTextColor;
    }

    public boolean isTitleBold() {
        return titleBold;
    }

    public DialogConfig setTitleBold(boolean titleBold) {
        this.titleBold = titleBold;
        return this;
    }

    public DialogConfig setConfirmTextColor(@ColorInt int color) {
        this.confirmTextColor = color;
        return this;
    }

    @ColorInt
    public int getConfirmTextColor() {
        return confirmTextColor;
    }


    public DialogConfig setCancelTextColor(@ColorInt int color) {
        this.cancelTextColor = color;
        return this;
    }

    @ColorInt
    public int getCancelTextColor() {
        return cancelTextColor;
    }

    @ColorInt
    public int getSplitLineColor() {
        return splitLineColor;
    }

    public DialogConfig setSplitLineColor(@ColorInt int color) {
        this.splitLineColor = color;
        return this;
    }

    @ColorInt
    public int getMaskColor() {
        return maskColor;
    }

    public DialogConfig setMaskColor(@ColorInt int maskColor) {
        this.maskColor = maskColor;
        return this;
    }

    @ColorInt
    public int getBottomSplitLineColor() {
        return bottomSplitLineColor;
    }

    public DialogConfig setBottomSplitLineColor(@ColorInt int bottomSplitLineColor) {
        this.bottomSplitLineColor = bottomSplitLineColor;
        return this;
    }

    @ColorInt
    public int getBtnSplitLineColor() {
        return btnSplitLineColor;
    }

    public DialogConfig setBtnSplitLineColor(@ColorInt int btnSplitLineColor) {
        this.btnSplitLineColor = btnSplitLineColor;
        return this;
    }

    public DialogConfig setContentBackgroundColor(@ColorInt int color) {
        this.contentBackgroundColor = color;
        return this;
    }

    @ColorInt
    public int getContentBackgroundColor() {
        return contentBackgroundColor;
    }

    @CornerRound
    public int getDialogCornerRound() {
        return dialogCornerRound;
    }

    public DialogConfig setDialogCornerRound(@CornerRound int dialogCornerRound) {
        this.dialogCornerRound = dialogCornerRound;
        return this;
    }

    @Dimension(unit = Dimension.DP)
    public int getCornerRadius() {
        return cornerRadius;
    }

    public DialogConfig setCornerRadius(@Dimension(unit = Dimension.DP) int cornerRadius) {
        this.cornerRadius = cornerRadius;
        return this;
    }


    public float getDialogWidthP() {
        return dialogWidthP;
    }

    public DialogConfig setDialogWidthP(float dialogWidthP) {
        this.dialogWidthP = dialogWidthP;
        return this;
    }

    @ColorInt
    public int getCalendarSingleColor() {
        return calendarSingleColor;
    }

    public DialogConfig setCalendarSingleColor(@ColorInt int calendarSingleColor) {
        this.calendarSingleColor = calendarSingleColor;
        return this;
    }

    @ColorInt
    public int getCalendarMultiColor() {
        return calendarMultiColor;
    }

    public DialogConfig setCalendarMultiColor(@ColorInt int calendarMultiColor) {
        this.calendarMultiColor = calendarMultiColor;
        return this;
    }

    @ColorInt
    public int getCalendarRangeColor() {
        return calendarRangeColor;
    }

    public DialogConfig setCalendarRangeColor(@ColorInt int calendarRangeColor) {
        this.calendarRangeColor = calendarRangeColor;
        return this;
    }

}
