package com.Jfpicker.wheelpicker.picker_calendar;


import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.Jfpicker.wheelpicker.R;
import com.Jfpicker.wheelpicker.picker_calendar.custom.DefaultMultiMonthView;
import com.Jfpicker.wheelpicker.picker_calendar.custom.DefaultRangeMonthView;
import com.Jfpicker.wheelpicker.picker_calendar.custom.DefaultSingleMonthView;
import com.Jfpicker.wheelpicker.picker_calendar.listener.OnCalendarMultiPickedListener;
import com.Jfpicker.wheelpicker.picker_calendar.listener.OnCalendarPickedListener;
import com.Jfpicker.wheelpicker.picker_calendar.listener.OnCalendarRangePickedListener;
import com.Jfpicker.wheelpicker.picker_calendar.listener.OnCalendarTitleListener;
import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarView;


import java.util.List;

/**
 * @author Created by linyincongxingkeji on  2021/11/24
 * @description 公司样式的日历选择器, 日历组件的使用方法具体参考大神的git
 * 使用了CalendarView日历：https://github.com/huanghaibin-dev/CalendarView
 */

public class CalendarLayout extends LinearLayout {

    protected RelativeLayout rlTitlebar;
    protected LinearLayout llCancel;
    protected ImageView ivCancel;
    protected TextView tvCancel;
    protected LinearLayout llPre;
    protected ImageView ivPre;
    protected LinearLayout llNext;
    protected ImageView ivNext;
    protected TextView tvTitle;
    protected LinearLayout llSure;
    protected ImageView ivSure;
    protected TextView tvSure;
    protected CalendarView mCalendarView;
    protected View viewBottom;


    private int selectMode = 1;
    private OnCalendarPickedListener onCalendarPickedListener;
    private OnCalendarMultiPickedListener onCalendarMultiPickedListener;
    private OnCalendarRangePickedListener onCalendarRangePickedListener;
    private OnCalendarTitleListener onCalendarTitleListener;

    public CalendarLayout(Context context) {
        super(context);
        init(context, null);
    }

    public CalendarLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CalendarLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public CalendarLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    protected void init(Context context, AttributeSet attrs) {
        setOrientation(VERTICAL);
        inflate(context, R.layout.picker_calendar_layout, this);
        rlTitlebar = findViewById(R.id.rlTitlebar);
        llCancel = findViewById(R.id.llCancel);
        ivCancel = findViewById(R.id.ivCancel);
        tvCancel = findViewById(R.id.tvCancel);
        llPre = findViewById(R.id.llPre);
        ivPre = findViewById(R.id.ivPre);
        llNext = findViewById(R.id.llNext);
        ivNext = findViewById(R.id.ivNext);
        tvTitle = findViewById(R.id.tvTitle);
        llSure = findViewById(R.id.llSure);
        ivSure = findViewById(R.id.ivSure);
        tvSure = findViewById(R.id.tvSure);
        mCalendarView = findViewById(R.id.my_calendar);
        viewBottom = findViewById(R.id.viewBottom);
        mCalendarView.setOnMonthChangeListener(new CalendarView.OnMonthChangeListener() {
            @Override
            public void onMonthChange(int year, int month) {
                setTitleText(year, month);
            }
        });

        llPre.setOnClickListener(v -> {
            mCalendarView.scrollToPre();
        });
        llNext.setOnClickListener(v -> {
            mCalendarView.scrollToNext();
        });

        llCancel.setOnClickListener(v -> {
            if (onCalendarTitleListener != null) {
                onCalendarTitleListener.onClose();
            }
        });
        llSure.setOnClickListener(v -> {
            if (onCalendarTitleListener != null) {
                onCalendarTitleListener.onSure();
            }
            if (selectMode == 1) {
                //单选
                if (onCalendarPickedListener != null) {
                    Calendar calendar = mCalendarView.getSelectedCalendar();
                    onCalendarPickedListener.onCalendarPicked(calendar.getYear(), calendar.getMonth(), calendar.getDay());
                }
            } else if (selectMode == 3) {
                //多选
                if (onCalendarMultiPickedListener != null) {
                    List<Calendar> list = mCalendarView.getMultiSelectCalendars();
                    onCalendarMultiPickedListener.onCalendarMultiPicked(list);
                }
            } else if (selectMode == 2) {
                //范围选择
                if (onCalendarRangePickedListener != null) {
                    List<Calendar> list = mCalendarView.getSelectCalendarRange();
                    onCalendarRangePickedListener.onCalendarRangePicked(list);
                }
            }


        });
        setTitleText();
        setSelectSingleMode();
    }

    //设置单选
    public void setSelectSingleMode() {
        selectMode = 1;
        mCalendarView.setSelectSingleMode();
        mCalendarView.setMonthView(DefaultSingleMonthView.class);
        mCalendarView.setOnCalendarSelectListener(new CalendarView.OnCalendarSelectListener() {
            @Override
            public void onCalendarOutOfRange(Calendar calendar) {

            }

            @Override
            public void onCalendarSelect(Calendar calendar, boolean isClick) {

            }
        });
        mCalendarView.scrollToCurrent();
    }

    //设置多选
    public void setSelectMultiMode(int size) {
        selectMode = 3;
        mCalendarView.setMaxMultiSelectSize(size);
        mCalendarView.setSelectMultiMode();
        mCalendarView.setMonthView(DefaultMultiMonthView.class);
        mCalendarView.setOnCalendarMultiSelectListener(new CalendarView.OnCalendarMultiSelectListener() {
            @Override
            public void onCalendarMultiSelectOutOfRange(Calendar calendar) {

            }

            @Override
            public void onMultiSelectOutOfSize(Calendar calendar, int maxSize) {
                Toast.makeText(getContext(), "最多选择" + maxSize + "个日期", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCalendarMultiSelect(Calendar calendar, int curSize, int maxSize) {

            }
        });

    }

    //设置范围选择
    public void setSelectRangeMode() {
        selectMode = 2;
        mCalendarView.setSelectRangeMode();
        mCalendarView.setMonthView(DefaultRangeMonthView.class);
        mCalendarView.setOnCalendarRangeSelectListener(new CalendarView.OnCalendarRangeSelectListener() {
            @Override
            public void onCalendarSelectOutOfRange(Calendar calendar) {

            }

            @Override
            public void onSelectOutOfRange(Calendar calendar, boolean isOutOfMinRange) {

            }

            @Override
            public void onCalendarRangeSelect(Calendar calendar, boolean isEnd) {

            }
        });

    }

    private void setTitleText() {
        int year = mCalendarView.getCurYear();
        int month = mCalendarView.getCurMonth();
        setTitleText(year, month);
    }

    private void setTitleText(int year, int month) {
        String mon = month + "";
        if (month < 10) {
            mon = "0" + month;
        }
        tvTitle.setText(year + "." + mon);
    }


    public void setOnCalendarPickedListener(OnCalendarPickedListener onCalendarPickedListener) {
        this.onCalendarPickedListener = onCalendarPickedListener;
    }

    public void setOnCalendarMultiPickedListener(OnCalendarMultiPickedListener onCalendarMultiPickedListener) {
        this.onCalendarMultiPickedListener = onCalendarMultiPickedListener;
    }


    public void setOnCalendarTitleListener(OnCalendarTitleListener onCloseCalendarListener) {
        this.onCalendarTitleListener = onCloseCalendarListener;
    }

    public void setOnCalendarRangePickedListener(OnCalendarRangePickedListener onCalendarRangePickedListener) {
        this.onCalendarRangePickedListener = onCalendarRangePickedListener;
    }

    public int getSelectMode() {
        return selectMode;
    }

    public void setSelectMode(int selectMode) {
        this.selectMode = selectMode;
    }

    public CalendarView getCalendarView() {
        return mCalendarView;
    }

    public RelativeLayout getRlTitlebar() {
        return rlTitlebar;
    }

    public LinearLayout getLlCancel() {
        return llCancel;
    }

    public ImageView getIvCancel() {
        return ivCancel;
    }

    public LinearLayout getLlPre() {
        return llPre;
    }

    public ImageView getIvPre() {
        return ivPre;
    }

    public LinearLayout getLlNext() {
        return llNext;
    }

    public ImageView getIvNext() {
        return ivNext;
    }

    public TextView getTvTitle() {
        return tvTitle;
    }

    public LinearLayout getLlSure() {
        return llSure;
    }

    public ImageView getIvSure() {
        return ivSure;
    }

    public View getViewBottom() {
        return viewBottom;
    }

    public TextView getTvCancel() {
        return tvCancel;
    }

    public TextView getTvSure() {
        return tvSure;
    }

}
