package com.Jfpicker.wheelpicker.picker_calendar.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextUtils;

import com.Jfpicker.wheelpicker.utils.DensityUtils;
import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.MonthView;

/**
 * 自定义单选的月视图
 */

public class DefaultSingleMonthView extends MonthView {

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

    public DefaultSingleMonthView(Context context) {
        super(context);

        mTextPaint.setTextSize(DensityUtils.dip2px(context, 8));
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

        mCircleRadius = DensityUtils.dip2px(getContext(), 7);

        mPadding = DensityUtils.dip2px(getContext(), 3);

        mPointRadius = DensityUtils.dip2px(context, 2);

        Paint.FontMetrics metrics = mSchemeBasicPaint.getFontMetrics();
        mSchemeBaseLine = mCircleRadius - metrics.descent + (metrics.bottom - metrics.top) / 2 + DensityUtils.dip2px(getContext(), 1);


    }

    @Override
    protected void onPreviewHook() {
        mSolarTermTextPaint.setTextSize(mCurMonthLunarTextPaint.getTextSize());
        mRadius = Math.min(mItemWidth, mItemHeight) / 11 * 5;
    }


    @Override
    protected boolean onDrawSelected(Canvas canvas, Calendar calendar, int x, int y, boolean hasScheme) {
        int cx = x + mItemWidth / 2;
        int cy = y + mItemHeight / 2;
        mSelectedPaint.setColor(0xFF3f8aef);
        canvas.drawCircle(cx, cy, mRadius, mSelectedPaint);
        return true;
    }

    @SuppressWarnings("IntegerDivisionInFloatingPointContext")
    @Override
    protected void onDrawScheme(Canvas canvas, Calendar calendar, int x, int y) {

//        boolean isSelected = isSelected(calendar);
//        if (isSelected) {
//            mPointPaint.setColor(Color.WHITE);
//        } else {
//        mPointPaint.setColor(Color.parseColor("#7049B9"));
//        }

//        canvas.drawCircle(x + mItemWidth / 2, y + mItemHeight - 3 * mPadding, mPointRadius, mPointPaint);

    }

    @SuppressWarnings("IntegerDivisionInFloatingPointContext")
    @Override
    protected void onDrawText(Canvas canvas, Calendar calendar, int x, int y, boolean hasScheme, boolean isSelected) {
        int cx = x + mItemWidth / 2;
        int cy = y + mItemHeight / 2;
        int top = y - mItemHeight / 6;

        if (calendar.isCurrentDay() && !isSelected) {
            canvas.drawCircle(cx, cy, mRadius, mCurrentDayPaint);
        }

        if (hasScheme) {
//            canvas.drawCircle(x + mItemWidth - mPadding - mCircleRadius / 2, y + mPadding + mCircleRadius, mCircleRadius, mSchemeBasicPaint);
            mTextPaint.setColor(calendar.getSchemeColor());
//            Rect rect = new Rect();
//            mTextPaint.getTextBounds(text, 0, test.length(), rect);
//            int width = rect.width();//文字宽
//            int height = rect.height();//文字高
//            canvas.drawText(calendar.getScheme(), x + mItemWidth - mPadding - mCircleRadius, y + mPadding + mSchemeBaseLine, mTextPaint);
            canvas.drawCircle(x + mItemWidth - mPadding - mCircleRadius - mPointRadius * 2, y + mPadding + mSchemeBaseLine, mPointRadius, mPointPaint);
        }

        //当然可以换成其它对应的画笔就不麻烦，
        if (calendar.isWeekend() && calendar.isCurrentMonth()) {
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
        } else {
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
        }

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
