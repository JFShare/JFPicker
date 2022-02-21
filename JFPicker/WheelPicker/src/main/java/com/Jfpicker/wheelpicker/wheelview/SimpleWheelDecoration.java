package com.Jfpicker.wheelpicker.wheelview;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.Log;

import com.Jfpicker.wheelpicker.picker_base.WheelFormatter;


/**
 * @author Created by JF on  2021/11/10
 * @description 继承自WheelDecoration，主要实现绘制文字效果。
 */

class SimpleWheelDecoration extends WheelDecoration {
    /**
     * 文字颜色，选中文字颜色
     */
    private int textColor, textColorCenter;
    /**
     * 文字是否加粗，选中文字是否加粗
     */
    private boolean isTextBlod, isCenterTextBlod;

    /**
     * 文字大小
     */
    private float textSize;
    /**
     * 是否文字大小渐变
     */
    private boolean textSizeGradient;
    /**
     * 支持文字渐变时最小的文字大小
     */
    private float minGradientTextSize;
    /**
     * 文字位置
     */
    private int gravity_text;

    /**
     * 选中区域大小
     */
    private int dividerSize;

    private float textHeight;
    /**
     * 文字画笔
     */
    private Paint paint, dividerPaint;

    /**
     * Adapter
     */
    private WheelViewAdapter adapter;

    /**
     * 显示文字格式化
     */
    private WheelFormatter formatter;

    public static String ELLIPSISTEXT = "...";


    public void setFormatter(WheelFormatter formatter) {
        this.formatter = formatter;
    }

    SimpleWheelDecoration(WheelViewAdapter adapter, WheelFormatter formatter, int gravity, float gravityCoefficient, boolean isWheel,
                          int gravity_text, int textColor, int textColorCenter, float textSize, int dividerColor, int dividerSize,
                          float itemDegreeTotal, boolean alphaGradient, boolean isTextBlod, boolean isCenterTextBlod,
                          boolean textSizeGradient, float minGradientTextSize) {
        super(adapter.itemCount, adapter.itemSize, gravity, gravityCoefficient, isWheel, itemDegreeTotal, alphaGradient);

        this.formatter = formatter;
        this.gravity_text = gravity_text;
        this.textColor = textColor;
        this.textColorCenter = textColorCenter;
        this.dividerSize = dividerSize;
        this.adapter = adapter;
        this.isTextBlod = isTextBlod;
        this.isCenterTextBlod = isCenterTextBlod;
        this.textSize = textSize;
        this.textSizeGradient = textSizeGradient;
        this.minGradientTextSize = minGradientTextSize;

        this.paint = new Paint();
        paint.setAntiAlias(true);
        paint.setTextSize(textSize);
        paint.setTextAlign(Paint.Align.CENTER);

        dividerPaint = new Paint();
        dividerPaint.setAntiAlias(true);
        dividerPaint.setColor(dividerColor);

    }


    @Override
    void drawItem(Canvas c, Rect rect, int position, int alpha, boolean isCenterItem, boolean isVertical, float textSizeP) {

        paint.setColor(isCenterItem ? textColorCenter : textColor);
        if (isCenterItem) {
            paint.setTypeface(isCenterTextBlod ? Typeface.DEFAULT_BOLD : Typeface.DEFAULT);
        } else {
            paint.setTypeface(isTextBlod ? Typeface.DEFAULT_BOLD : Typeface.DEFAULT);
        }

        paint.setAlpha(alpha);
        float realTextSize = textSize;
        if (textSizeGradient) {
            realTextSize = minGradientTextSize + ((textSize - minGradientTextSize) * textSizeP);
        }
        paint.setTextSize(realTextSize);
        Paint.FontMetrics fm = paint.getFontMetrics();
        textHeight = (fm.bottom + fm.top) / 2.0f;


        String s = adapter.getItemString(position).toString();
        if (formatter != null) {
            s = formatter.formatItem(adapter.getItemString(position));
        }
        String ellipsisText = getEllipsisText(s, rect);
        if (!s.equals(ellipsisText)) {
            s = getEllipsisAppendText(ellipsisText, rect) + ELLIPSISTEXT;
        }

        if (gravity_text == GRAVITY_LEFT) {
            //在rect区域内画左边居中文字
            c.drawText(s, 0, rect.exactCenterY() - textHeight, paint);
        } else if (gravity_text == GRAVITY_RIGHT) {
            Rect rect_text = new Rect();
            paint.getTextBounds(s, 0, s.length(), rect_text);
            int width = rect.width();//文字宽
            //在rect区域内画右边居中文字
            c.drawText(s, rect.width() - width, rect.exactCenterY() - textHeight, paint);
        } else {
            //在rect区域内画居中文字
            c.drawText(s, rect.exactCenterX(), rect.exactCenterY() - textHeight, paint);
        }

    }

    @Override
    void drawDivider(Canvas c, Rect rect, boolean isVertical) {
        if (isVertical) {
            float dividerOff = (rect.height() - dividerSize) / 2.0f;
            float firstY = rect.top + dividerOff;
            c.drawLine(rect.left, firstY, rect.right, firstY, dividerPaint);
            float secondY = rect.bottom - dividerOff;
            c.drawLine(rect.left, secondY, rect.right, secondY, dividerPaint);
        } else {
            float dividerOff = (rect.width() - dividerSize) / 2.0f;
            float firstX = rect.left + dividerOff;
            c.drawLine(firstX, rect.top, firstX, rect.bottom, dividerPaint);
            float secondX = rect.right - dividerOff;
            c.drawLine(secondX, rect.top, secondX, rect.bottom, dividerPaint);
        }
    }

    //非常简单的计算了省略号的显示
    public String getEllipsisText(String text, Rect rect) {
        if (paint.measureText(text) - (rect.right - rect.left) > 0) {
            int length = text.length();
            if (length > 3) {
                String subText = text.substring(0, length - 2);
                return getEllipsisText(subText, rect);
            } else {
                return text;
            }
        } else {
            return text;
        }
    }

    public String getEllipsisAppendText(String text, Rect rect) {
        if (paint.measureText(text + ELLIPSISTEXT) - (rect.right - rect.left) > 0) {
            int length = text.length();
            if (length > 3) {
                String subText = text.substring(0, length - 2);
                return getEllipsisText(subText, rect);
            } else {
                return text;
            }
        } else {
            return text;
        }
    }
}
