package com.Jfpicker.wheelpicker.wheelview;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.Log;

import com.Jfpicker.wheelpicker.wheelview.format.WheelFormatListener;


/**
 * @author Created by JF on  2021/11/10
 * @description 继承自WheelDecoration，主要实现绘制文字效果和间隔样式
 */

class WheelDecoration extends AbstractWheelDecoration {


    private WheelAttrs attrs;

    /**
     * 文字画笔
     */
    private Paint paint, dividerPaint, dividerBgPaint;

    /**
     * Adapter
     */
    private WheelViewAdapter adapter;


    public static String ELLIPSIS_TEXT = "...";


    public void setFormatter(WheelFormatListener formatter) {
        attrs.setFormatter(formatter);
    }

    WheelDecoration(WheelViewAdapter adapter, WheelAttrs attrs) {
        super(attrs);
        this.attrs = attrs;
        this.adapter = adapter;
        initPaint();
    }

    private void initPaint() {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setTextSize(attrs.getTextSize());
        paint.setTextAlign(Paint.Align.CENTER);

        dividerPaint = new Paint();
        dividerPaint.setAntiAlias(true);
        dividerPaint.setStrokeWidth(attrs.getDividerStrokeWidth());
        dividerPaint.setStyle(Paint.Style.STROKE);
        dividerPaint.setColor(attrs.getDividerColor());

        dividerBgPaint = new Paint();
        dividerBgPaint.setAntiAlias(true);
        dividerBgPaint.setStyle(Paint.Style.FILL);
        dividerBgPaint.setColor(attrs.getDividerBgColor());
    }


    @Override
    void drawItem(Canvas c, Rect rect,  int adapterPosition,int centerOffset) {
        boolean isCenterItem = centerOffset==0;
        paint.setColor(isCenterItem ? attrs.getCheckedTextColor() : attrs.getTextColor());
        if (isCenterItem) {
            paint.setTypeface(attrs.isCheckedTextBold() ? Typeface.DEFAULT_BOLD : Typeface.DEFAULT);
        } else {
            paint.setTypeface(attrs.isTextBold() ? Typeface.DEFAULT_BOLD : Typeface.DEFAULT);
        }
        if (attrs.getAlphaFormat() != null) {
            paint.setAlpha(attrs.getAlphaFormat().onGradient(centerOffset));
        }
        if (attrs.getTextSizeFormat() != null) {
            paint.setTextSize(attrs.getTextSizeFormat().onFormat(attrs.getCheckedTextSize(), centerOffset));
        } else {
            paint.setTextSize(isCenterItem ? attrs.getCheckedTextSize() : attrs.getTextSize());
        }
        Paint.FontMetrics fm = paint.getFontMetrics();
        float textHeight = (fm.bottom + fm.top) / 2.0f;

        String itemContentStr = adapter.getItemString(adapterPosition).toString();
        if (attrs.getFormatter() != null) {
            itemContentStr = attrs.getFormatter().formatItem(adapter.getItemString(adapterPosition));
        }
        String ellipsisText = getEllipsisText(itemContentStr, rect);
        if (!itemContentStr.equals(ellipsisText)) {
            itemContentStr = getEllipsisAppendText(ellipsisText, rect) + ELLIPSIS_TEXT;
        }
        c.drawText(itemContentStr, rect.exactCenterX(), rect.exactCenterY() - textHeight, paint);
    }

    @Override
    void drawDividerBg(Canvas c, Rect rect) {
        float dividerOff = (rect.height() - attrs.getDividerSize()) / 2.0f;
        float firstY = rect.top + dividerOff;
        float secondY = rect.bottom - dividerOff;
        RectF rectF;
        if (WheelAttrs.DIVIDER_RECTANGLE == attrs.getDividerType()) {
            rectF = new RectF(rect.left + attrs.getDividerStrokeWidth(),
                    firstY + attrs.getDividerStrokeWidth(),
                    rect.right - attrs.getDividerStrokeWidth(),
                    secondY - attrs.getDividerStrokeWidth());
        } else {
            rectF = new RectF(rect.left, firstY, rect.right, secondY);
        }

        c.drawRoundRect(rectF, attrs.getDividerCorner(), attrs.getDividerCorner(), dividerBgPaint);
    }

    @Override
    void drawDivider(Canvas c, Rect rect) {
        float dividerOff = (rect.height() - attrs.getDividerSize()) / 2.0f;
        float firstY = rect.top + dividerOff;
        float secondY = rect.bottom - dividerOff;
        if (WheelAttrs.DIVIDER_RECTANGLE == attrs.getDividerType()) {
            RectF rectF = new RectF(rect.left + attrs.getDividerStrokeWidth(),
                    firstY + attrs.getDividerStrokeWidth(),
                    rect.right - attrs.getDividerStrokeWidth(),
                    secondY - attrs.getDividerStrokeWidth());
            c.drawRoundRect(rectF, attrs.getDividerCorner(), attrs.getDividerCorner(), dividerPaint);
        } else {
            c.drawLine(rect.left, firstY, rect.right, firstY, dividerPaint);
            c.drawLine(rect.left, secondY, rect.right, secondY, dividerPaint);
        }
    }

    // 每次减少两个字符，简单的计算一下是否需要显示省略号
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
        if (paint.measureText(text + ELLIPSIS_TEXT) - (rect.right - rect.left) > 0) {
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
