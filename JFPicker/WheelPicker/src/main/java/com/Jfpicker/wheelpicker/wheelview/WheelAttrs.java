package com.Jfpicker.wheelpicker.wheelview;

import android.graphics.Color;

import com.Jfpicker.wheelpicker.wheelview.format.WheelFormatListener;
import com.Jfpicker.wheelpicker.wheelview.format.AlphaGradientListener;
import com.Jfpicker.wheelpicker.wheelview.format.TextSizeGradientListener;

/**
 * @author Created by JF on  2021/11/10
 * @description WheelView所有的属性类，提供默认样式
 */

public class WheelAttrs {


    /*
     * 间隔区域背景：间隔线、矩形
     */
    public static final int DIVIDER_LINE = 1;
    public static final int DIVIDER_RECTANGLE = 2;

    public static final int DEFAULT_SIZE = 100;
    public static final float DEFAULT_TEXT_SIZE = 50;
    public static final int DEFAULT_TEXT_COLOR = Color.parseColor("#333333");
    public static final int DEFAULT_DIVIDER_BG_COLOR = Color.parseColor("#00000000");
    public static final int DEFAULT_DIVIDER_COLOR = Color.parseColor("#dedede");

    /*
     * 滚轮样式相关==========================
     */

    //是否滚轮样式，等于false即列表平铺
    private boolean isWheel = true;
    //是否使用画布偏移，取消画布Y轴Z轴偏移会对滚轮的立体视觉效果产生影响
    private boolean translateYZ = true;
    // 滚轮总角度，决定滚轮的弯曲程度，0 < itemDegreeTotal <= 180
    private float itemDegreeTotal = 180.f;
    // 除了选中项以外，上下各有几项，总数量为 wheelItemCount * 2 + 1
    private int halfItemCount = 3;
    /*
         每一项的高度，px,如果使用滚轮效果，因为画布偏移，会造成上下区域的一定留白，
         如果不想留白，
         需要设置 isWheel=false取消滚轮效果，
         或者 设置 translateYZ=false 取消画布Y轴Z轴偏移，取消画布Y轴Z轴偏移会对滚轮的立体视觉效果产生影响
     */
    private int itemSize = DEFAULT_SIZE;
    //是否开启数据无限循环
    private boolean isLoop;

    /*
     * 滚轮字体相关==========================
     */

    //字体颜色
    private int textColor = DEFAULT_TEXT_COLOR;
    //选中项字体颜色
    private int checkedTextColor = DEFAULT_TEXT_COLOR;
    //字体大小,px
    private float textSize = DEFAULT_TEXT_SIZE;
    //选中项字体大小，px,字体大小格式化接口标准大小,
    private float checkedTextSize = DEFAULT_TEXT_SIZE;
    //字体是否加粗
    private boolean isTextBold = false;
    //选中项字体是否加粗
    private boolean isCheckedTextBold = false;


    /*
     * 滚轮间隔线相关==========================
     */

    //间隔背景样式：间隔线、矩形
    private int dividerType = DIVIDER_LINE;
    //间隔之间的宽度 px
    private float dividerSize = DEFAULT_SIZE;
    //间隔背景色
    private int dividerBgColor = DEFAULT_DIVIDER_BG_COLOR;
    //间隔线颜色
    private int dividerColor = DEFAULT_DIVIDER_COLOR;
    //间隔线宽度，px,默认1px宽
    private int dividerStrokeWidth = 1;
    //间隔线、间隔矩形 圆角大小 px
    private int dividerCorner = 0;

    /*
     * 滚轮接口相关==========================
     */

    /*
        字体透明度渐变格式化接口, int onGradient(int position),
        position是位置，选中项是0，选中项上方为+1 +2 +3 ...，下方为 -1 -2 -3 ...
     */
    private AlphaGradientListener alphaFormat;
    /*
        字体大小渐变格式化接口,float onGradient(float StandardSize, int position)
        StandardSize即checkedTextSize，sp,
        position是位置，选中项是0，选中项上方为+1 +2 +3 ...，下方为 -1 -2 -3 ...
     */
    private TextSizeGradientListener textSizeFormat;

    /*
        显示内容格式化接口 String formatItem(@NonNull Object item)
        item是设置的滚轮数据
     */
    private WheelFormatListener formatter;

    public boolean isWheel() {
        return isWheel;
    }

    public void setWheel(boolean wheel) {
        isWheel = wheel;
    }

    public boolean isTranslateYZ() {
        return translateYZ;
    }

    public void setTranslateYZ(boolean translateYZ) {
        this.translateYZ = translateYZ;
    }

    public float getItemDegreeTotal() {
        return itemDegreeTotal;
    }

    public void setItemDegreeTotal(float itemDegreeTotal) {
        if (itemDegreeTotal <= 0) {
            this.itemDegreeTotal = 1;
            return;
        }
        if (itemDegreeTotal > 180) {
            this.itemDegreeTotal = 180;
            return;
        }
        this.itemDegreeTotal = itemDegreeTotal;
    }

    public int getHalfItemCount() {
        return halfItemCount;
    }

    public void setHalfItemCount(int halfItemCount) {
        this.halfItemCount = halfItemCount;
    }

    public int getItemSize() {
        return itemSize;
    }

    public void setItemSize(int itemSize) {
        this.itemSize = itemSize;
    }

    public boolean isLoop() {
        return isLoop;
    }

    public void setLoop(boolean loop) {
        isLoop = loop;
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public int getCheckedTextColor() {
        return checkedTextColor;
    }

    public void setCheckedTextColor(int checkedTextColor) {
        this.checkedTextColor = checkedTextColor;
    }

    public float getTextSize() {
        return textSize;
    }

    public void setTextSize(float textSize) {
        this.textSize = textSize;
    }

    public float getCheckedTextSize() {
        return checkedTextSize;
    }

    public void setCheckedTextSize(float checkedTextSize) {
        this.checkedTextSize = checkedTextSize;
    }

    public boolean isTextBold() {
        return isTextBold;
    }

    public void setTextBold(boolean textBold) {
        isTextBold = textBold;
    }

    public boolean isCheckedTextBold() {
        return isCheckedTextBold;
    }

    public void setCheckedTextBold(boolean checkedTextBold) {
        isCheckedTextBold = checkedTextBold;
    }


    public int getDividerType() {
        return dividerType;
    }

    public void setDividerType(int dividerType) {
        this.dividerType = dividerType;
    }

    public float getDividerSize() {
        return dividerSize;
    }

    public void setDividerSize(float dividerSize) {
        this.dividerSize = dividerSize;
    }

    public int getDividerBgColor() {
        return dividerBgColor;
    }

    public void setDividerBgColor(int dividerBgColor) {
        this.dividerBgColor = dividerBgColor;
    }

    public int getDividerColor() {
        return dividerColor;
    }

    public void setDividerColor(int dividerColor) {
        this.dividerColor = dividerColor;
    }

    public int getDividerStrokeWidth() {
        return dividerStrokeWidth;
    }

    public void setDividerStrokeWidth(int dividerStrokeWidth) {
        this.dividerStrokeWidth = dividerStrokeWidth;
    }

    public int getDividerCorner() {
        return dividerCorner;
    }

    public void setDividerCorner(int dividerCorner) {
        this.dividerCorner = dividerCorner;
    }


    public AlphaGradientListener getAlphaFormat() {
        return alphaFormat;
    }

    public void setAlphaFormat(AlphaGradientListener alphaFormat) {
        this.alphaFormat = alphaFormat;
    }

    public TextSizeGradientListener getTextSizeFormat() {
        return textSizeFormat;
    }

    public void setTextSizeFormat(TextSizeGradientListener textSizeFormat) {
        this.textSizeFormat = textSizeFormat;
    }

    public WheelFormatListener getFormatter() {
        return formatter;
    }

    public void setFormatter(WheelFormatListener formatter) {
        this.formatter = formatter;
    }

    //滚轮默认样式
    public static WheelAttrs getDefault() {
        return new WheelAttrs();
    }

    //非滚轮的默认样式
    public static WheelAttrs getDefaultNoWheel() {
        WheelAttrs wheelAttrs = new WheelAttrs();
        wheelAttrs.setWheel(false);
        wheelAttrs.setDividerSize(130);
        wheelAttrs.setItemSize(130);
        wheelAttrs.setHalfItemCount(2);
        return wheelAttrs;
    }

}
