package com.Jfpicker.wheelpicker.picker_calendar.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextUtils;

import com.Jfpicker.wheelpicker.utils.WheelDensityUtils;
import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.RangeMonthView;

/**
 * 自定义范围选择的月视图
 */

public class DefaultRangeMonthView extends RangeMonthView {
    private int mRadius;

    /**
     * 文本画笔
     */
    private Paint mTextPaint = new Paint();


    /**
     * 24节气画笔
     */
    private Paint mSolarTermTextPaint = new Paint();

    /**
     * 标记圆点画笔
     */
    private Paint mPointPaint = new Paint();

    /**
     * 今天的背景圆圈画笔
     */
    private Paint mCurrentDayPaint = new Paint();

    /**
     * 圆点半径
     */
    private float mPointRadius;

    private int mPadding;

    private float mCircleRadius;
    /**
     * 标记画笔
     */
    private Paint mSchemeBasicPaint = new Paint();

    private float mSchemeBaseLine;

    public DefaultRangeMonthView(Context context) {
        super(context);
        mTextPaint.setTextSize(WheelDensityUtils.dip2px(context, 8));
        mTextPaint.setColor(0xffffffff);
        mTextPaint.setAntiAlias(true);
        mTextPaint.setFakeBoldText(true);

        mSolarTermTextPaint.setColor(0xff000000);
        mSolarTermTextPaint.setAntiAlias(true);
        mSolarTermTextPaint.setTextAlign(Paint.Align.CENTER);

        mSchemeBasicPaint.setAntiAlias(true);
        mSchemeBasicPaint.setStyle(Paint.Style.FILL);
        mSchemeBasicPaint.setTextAlign(Paint.Align.CENTER);
        mSchemeBasicPaint.setFakeBoldText(true);
        mSchemeBasicPaint.setColor(Color.WHITE);


        mCurrentDayPaint.setAntiAlias(true);
        mCurrentDayPaint.setStyle(Paint.Style.FILL);
        mCurrentDayPaint.setColor(0x33999999);

        mPointPaint.setAntiAlias(true);
        mPointPaint.setStyle(Paint.Style.FILL);
        mPointPaint.setTextAlign(Paint.Align.CENTER);
        mPointPaint.setColor(Color.RED);

        mCircleRadius = WheelDensityUtils.dip2px(getContext(), 7);

        mPadding = WheelDensityUtils.dip2px(getContext(), 3);

        mPointRadius = WheelDensityUtils.dip2px(context, 2);

        Paint.FontMetrics metrics = mSchemeBasicPaint.getFontMetrics();
        mSchemeBaseLine = mCircleRadius - metrics.descent + (metrics.bottom - metrics.top) / 2 + WheelDensityUtils.dip2px(getContext(), 1);
    }


    @Override
    protected void onPreviewHook() {
        mRadius = Math.min(mItemWidth, mItemHeight) / 5 * 2;
        mSchemePaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected boolean onDrawSelected(Canvas canvas, Calendar calendar, int x, int y, boolean hasScheme,
                                     boolean isSelectedPre, boolean isSelectedNext) {
        int cx = x + mItemWidth / 2;
        int cy = y + mItemHeight / 2;
        mSelectedPaint.setColor(0xFF3f8aef);
        if (isSelectedPre) {
            if (isSelectedNext) {
                canvas.drawRect(x, cy - mRadius, x + mItemWidth, cy + mRadius, mSelectedPaint);
            } else {//最后一个，the last
                canvas.drawRect(x, cy - mRadius, cx, cy + mRadius, mSelectedPaint);
                canvas.drawCircle(cx, cy, mRadius, mSelectedPaint);
            }
        } else {
            if(isSelectedNext){
                canvas.drawRect(cx, cy - mRadius, x + mItemWidth, cy + mRadius, mSelectedPaint);
            }
            canvas.drawCircle(cx, cy, mRadius, mSelectedPaint);

        }

        return false;
    }

    @Override
    protected void onDrawScheme(Canvas canvas, Calendar calendar, int x, int y, boolean isSelected) {

    }

    @Override
    protected void onDrawText(Canvas canvas, Calendar calendar, int x, int y, boolean hasScheme, boolean isSelected) {
        int cx = x + mItemWidth / 2;
        int cy = y + mItemHeight / 2;
        int top = y - mItemHeight / 6;

        if (calendar.isCurrentDay() && !isSelected) {
            canvas.drawCircle(cx, cy, mRadius, mCurrentDayPaint);
        }

        if (hasScheme) {
            mTextPaint.setColor(calendar.getSchemeColor());
            canvas.drawCircle(x + mItemWidth - mPadding - mCircleRadius - mPointRadius * 2, y + mPadding + mSchemeBaseLine, mPointRadius, mPointPaint);
        }

        mCurMonthTextPaint.setColor(0xFF000000);//当前月份的日期颜色
        mCurMonthLunarTextPaint.setColor(0xFFbdbdbd);//当前月份的农历颜色
        mSchemeTextPaint.setColor(0xFF000000);//标记的文本颜色
        mSchemeLunarTextPaint.setColor(0xFFbdbdbd);//其他月份的农历颜色
        mOtherMonthLunarTextPaint.setColor(0xFFbdbdbd);//其他月份的农历颜色
        mOtherMonthTextPaint.setColor(0xFF000000);//其他月份的日期颜色
        mSelectTextPaint.setColor(0xFFFFFFFF);//选中的文本颜色
        mCurDayTextPaint.setColor(0xFF000000);//当前日期文本颜色画笔
        mCurDayLunarTextPaint.setColor(0xFF000000);//当天日期的农历文本颜色
        mSelectedLunarTextPaint.setColor(0xFFFFFFFF);//选中的日期的农历颜色

        if (isSelected) {
            canvas.drawText(String.valueOf(calendar.getDay()), cx, mTextBaseLine + top,
                    mSelectTextPaint);
            canvas.drawText(calendar.getLunar(), cx, mTextBaseLine + y + mItemHeight / 10, mSelectedLunarTextPaint);
        } else if (hasScheme) {

            canvas.drawText(String.valueOf(calendar.getDay()), cx, mTextBaseLine + top,
                    calendar.isCurrentMonth() ? mSchemeTextPaint : mOtherMonthTextPaint);

            canvas.drawText(calendar.getLunar(), cx, mTextBaseLine + y + mItemHeight / 10,
                    !TextUtils.isEmpty(calendar.getSolarTerm()) ? mSolarTermTextPaint : mSchemeLunarTextPaint);
        } else {
            canvas.drawText(String.valueOf(calendar.getDay()), cx, mTextBaseLine + top,
                    calendar.isCurrentDay() ? mCurDayTextPaint :
                            calendar.isCurrentMonth() ? mCurMonthTextPaint : mOtherMonthTextPaint);

            canvas.drawText(calendar.getLunar(), cx, mTextBaseLine + y + mItemHeight / 10,
                    calendar.isCurrentDay() ? mCurDayLunarTextPaint :
                            calendar.isCurrentMonth() ? !TextUtils.isEmpty(calendar.getSolarTerm()) ? mSolarTermTextPaint :
                                    mCurMonthLunarTextPaint : mOtherMonthLunarTextPaint);
        }
    }
}
