package com.Jfpicker.wheelpicker.wheelview;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;


import com.Jfpicker.wheelpicker.picker_base.WheelFormatter;

class SimpleWheelDecoration extends WheelDecoration {
    /**
     * wheel item颜色与中心选中时的颜色
     */
    private int textColor, textColorCenter;

    private int dividerSize;
    /**
     * 画文本居中时文本画笔的高度
     */
    private float textHeight;
    /**
     * wheel paint, dividerPaint
     */
    private Paint paint, dividerPaint;

    private WheelViewAdapter adapter;

    private int gravity_text;

    private float itemDegreeTotal;

    private WheelFormatter formatter;
    public static String ELLIPSISTEXT = "...";
    private float ellipsisLength = 0;

    public void setFormatter(WheelFormatter formatter) {
        this.formatter = formatter;
    }

    SimpleWheelDecoration(WheelViewAdapter adapter, WheelFormatter formatter, int gravity, float gravityCoefficient, boolean isWheel,
                          int gravity_text, int textColor,
                          int textColorCenter, float textSize, int dividerColor, int dividerSize, float itemDegreeTotal) {
        super(adapter.itemCount, adapter.itemSize, gravity, gravityCoefficient, isWheel, itemDegreeTotal);

        this.formatter = formatter;
        this.gravity_text = gravity_text;
        this.textColor = textColor;
        this.textColorCenter = textColorCenter;
        this.dividerSize = dividerSize;
        this.adapter = adapter;

        this.paint = new Paint();
        paint.setAntiAlias(true);
        paint.setTextSize(textSize);
        paint.setTextAlign(Paint.Align.CENTER);
        Paint.FontMetrics fm = paint.getFontMetrics();
        textHeight = (fm.bottom + fm.top) / 2.0f;

        dividerPaint = new Paint();
        dividerPaint.setAntiAlias(true);
        dividerPaint.setColor(dividerColor);

        ellipsisLength = paint.measureText(ELLIPSISTEXT);
    }


    @Override
    void drawItem(Canvas c, Rect rect, int position, int alpha, boolean isCenterItem, boolean isVertical) {


        String s = adapter.getItemString(position).toString();
        if (formatter != null) {
            s = formatter.formatItem(adapter.getItemString(position));
        }

        String ellipsisText = getEllipsisText(s, rect);
        if (!s.equals(ellipsisText)) {
            s = getEllipsisAppendText(ellipsisText, rect) + ELLIPSISTEXT;
        }

        paint.setColor(isCenterItem ? textColorCenter : textColor);
        paint.setAlpha(alpha);
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
