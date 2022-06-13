package com.Jfpicker.wheelpicker.wheelview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.Jfpicker.wheelpicker.R;
import com.Jfpicker.wheelpicker.wheelview.format.WheelFormatListener;
import com.Jfpicker.wheelpicker.wheelview.listener.OnWheelScrollListener;

/**
 * @author Created by JF on  2021/11/10
 * @description 核心的WheelView滚轮控件，样式由WheelDecoration绘制，数据由WheelViewAdapter控制，代码来自同事分享
 * 如果有知道来自哪位大神，请留言。
 */
public class WheelView extends FrameLayout {
    /**
     * 无效的位置
     */
    public static final int IDLE_POSITION = -1;

    private WheelAttrs wheelAttrs = WheelAttrs.getDefault();

    /**
     * recyclerView
     */
    private CScrollRecyclerView mRecyclerView;
    private LinearLayoutManager layoutManager;
    private SimpleWheelDecoration wheelDecoration; //RecyclerView.ItemDecoration
    private WheelViewAdapter wheelAdapter; // RecyclerView.Adapter
    private WheelDataAbstractAdapter abstractAdapter;
    private WheelViewObserver observer;

    private int mState = RecyclerView.SCROLL_STATE_IDLE;
    private int dataPosition = IDLE_POSITION;
    private OnWheelScrollListener listener;

    public WheelView(Context context) {
        super(context);
        init(context, null);
    }

    public WheelView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public WheelView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {

        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.WheelView);
            wheelAttrs.setWheel(a.getBoolean(R.styleable.WheelView_isWheel, true));
            wheelAttrs.setTranslateYZ(a.getBoolean(R.styleable.WheelView_translateYZ, true));
            wheelAttrs.setItemDegreeTotal(a.getFloat(R.styleable.WheelView_itemDegreeTotal, 180.f));
            wheelAttrs.setHalfItemCount(a.getInt(R.styleable.WheelView_halfItemCount, 3));
            wheelAttrs.setItemSize(a.getDimensionPixelOffset(R.styleable.WheelView_wheelItemSize,
                    WheelAttrs.DEFAULT_SIZE));


            wheelAttrs.setTextColor(a.getColor(R.styleable.WheelView_wheelTextColor,
                    WheelAttrs.DEFAULT_TEXT_COLOR));
            wheelAttrs.setCheckedTextColor(a.getColor(R.styleable.WheelView_wheelCheckedTextColor,
                    WheelAttrs.DEFAULT_TEXT_COLOR));
            wheelAttrs.setTextSize(a.getDimension(R.styleable.WheelView_wheelTextSize,
                    WheelAttrs.DEFAULT_TEXT_SIZE));
            wheelAttrs.setCheckedTextSize(a.getDimension(R.styleable.WheelView_wheelCheckedTextSize,
                    WheelAttrs.DEFAULT_TEXT_SIZE));
            wheelAttrs.setTextBold(a.getBoolean(R.styleable.WheelView_isTextBold, false));
            wheelAttrs.setCheckedTextBold(a.getBoolean(R.styleable.WheelView_isCheckedTextBold, false));


            wheelAttrs.setDividerType(a.getInt(R.styleable.WheelView_wheelDividerType, WheelAttrs.DIVIDER_LINE));
            wheelAttrs.setDividerSize(a.getDimensionPixelOffset(R.styleable.WheelView_wheelDividerSize,
                    WheelAttrs.DEFAULT_SIZE));
            wheelAttrs.setDividerBgColor(a.getColor(R.styleable.WheelView_wheelDividerBgColor,
                    WheelAttrs.DEFAULT_DIVIDER_BG_COLOR));
            wheelAttrs.setDividerColor(a.getColor(R.styleable.WheelView_wheelDividerColor,
                    WheelAttrs.DEFAULT_DIVIDER_COLOR));
            wheelAttrs.setDividerStrokeWidth(a.getDimensionPixelOffset(R.styleable.WheelView_wheelDividerStrokeWidth, 1));
            wheelAttrs.setDividerCorner(a.getDimensionPixelOffset(R.styleable.WheelView_wheelDividerCorner, 0));
            a.recycle();
        }
        initRecyclerView(context);
    }

    private void initRecyclerView(Context context) {
        removeAllViews();
        mRecyclerView = new CScrollRecyclerView(context);
        mRecyclerView.setOverScrollMode(OVER_SCROLL_NEVER);
        int totalItemSize = (wheelAttrs.getHalfItemCount() * 2 + 1) * wheelAttrs.getItemSize();
        layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        //让滑动结束时都能定到中心位置
        new LinearSnapHelper().attachToRecyclerView(mRecyclerView);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                totalItemSize);
        this.addView(mRecyclerView, params);

        wheelAdapter = new WheelViewAdapter(wheelAttrs);
        wheelDecoration = new SimpleWheelDecoration(wheelAdapter, wheelAttrs);
        mRecyclerView.addItemDecoration(wheelDecoration);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                mState = newState;
                if (wheelDecoration == null) return;
                dataPosition = getCurrentItem();
                if (listener == null) return;
                listener.onScrollStatusChange(newState == RecyclerView.SCROLL_STATE_DRAGGING || newState == RecyclerView.SCROLL_STATE_SETTLING);
                if (wheelDecoration.centerRealPosition == IDLE_POSITION || newState != RecyclerView.SCROLL_STATE_IDLE)
                    return;
                listener.onItemChecked(WheelView.this, dataPosition);
            }
        });
        mRecyclerView.setAdapter(wheelAdapter);
    }

    public void setAdapter(WheelDataAbstractAdapter adapter) {
        if (abstractAdapter != null) {
            abstractAdapter.setWheelViewObserver(null);
        }
        abstractAdapter = adapter;
        if (abstractAdapter != null) {
            if (observer == null) {
                observer = new WheelViewObserver();
            }
            abstractAdapter.setWheelViewObserver(observer);
            dataPosition = IDLE_POSITION;
            wheelAdapter.adapter = adapter;
            dataSetChanged();
        }
    }


    @SuppressLint("NotifyDataSetChanged")
    private void dataSetChanged() {
        wheelAdapter.notifyDataSetChanged();
    }

    public void setCurrentItem(int position) {
        layoutManager.scrollToPositionWithOffset(position, 0);
        dataPosition = position;
    }

    public int getCurrentItem() {
        int adapterCount = layoutManager.getItemCount();
        if (wheelDecoration.centerRealPosition >= adapterCount)
            return 0;
        int wheelCount = adapterCount - wheelAttrs.getHalfItemCount() * 2;
        if (wheelDecoration.centerRealPosition >= wheelCount) {
            return wheelCount - 1;
        }
        return wheelDecoration.centerRealPosition;
    }


    //获取当前的滚轮样式
    public WheelAttrs getAttrs() {
        return wheelAttrs;
    }

    public void updateAttrs() {
        setAttrs(wheelAttrs);
    }

    //动态 重新设置滚轮样式
    public void setAttrs(WheelAttrs attrs) {
        if (mState != RecyclerView.SCROLL_STATE_IDLE) {
            return;
        }
        if (attrs != null) {
            if (mRecyclerView != null && layoutManager != null
                    && wheelAdapter != null && wheelDecoration != null) {
                wheelAttrs = attrs;
                initRecyclerView(getContext());
                if (abstractAdapter != null) {
                    wheelAdapter.adapter = abstractAdapter;
                    dataSetChanged();
                    if (abstractAdapter.getItemCount() <= 0) {
                        return;
                    }
                    if (dataPosition > IDLE_POSITION && dataPosition < abstractAdapter.getItemCount()) {
                        setCurrentItem(dataPosition);
                    }
                }
            }
        }
    }

    public void setOnWheelScrollListener(OnWheelScrollListener listener) {
        this.listener = listener;
    }

    public void setFormatter(WheelFormatListener formatter) {
        if (wheelAttrs != null) {
            wheelAttrs.setFormatter(formatter);
            updateAttrs();
        }
    }


    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        if (mRecyclerView != null) {
            mRecyclerView.setCanScroll(enabled);
        }
    }

    private class WheelViewObserver extends DataSetObserver {
        @Override
        public void onChanged() {
            dataSetChanged();
        }

        @Override
        public void onInvalidated() {
            dataSetChanged();
        }
    }


}
