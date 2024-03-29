package com.Jfpicker.wheelpicker.wheelview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.Jfpicker.wheelpicker.R;
import com.Jfpicker.wheelpicker.wheelview.format.WheelFormatListener;
import com.Jfpicker.wheelpicker.wheelview.layoutmanager.LayoutManagerScrollEngine;
import com.Jfpicker.wheelpicker.wheelview.layoutmanager.VerticalLooperLayoutManager;
import com.Jfpicker.wheelpicker.wheelview.layoutmanager.WheelLinearLayoutManager;
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

    private CScrollRecyclerView mRecyclerView;
    //LayoutManager
    private LayoutManagerScrollEngine scrollEngine;
    private WheelLinearLayoutManager linearLayoutManager;
    private VerticalLooperLayoutManager looperLayoutManager;
    //装饰器
    private WheelDecoration wheelDecoration;
    //数据适配器
    private WheelViewAdapter wheelAdapter;
    private WheelDataAbstractAdapter abstractAdapter;
    private WheelViewObserver observer;

    //滚动与选中
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
            wheelAttrs.setLoop(a.getBoolean(R.styleable.WheelView_isLoop, false));

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

    //初始化列表
    private void initRecyclerView(Context context) {
        removeAllViews();
        mRecyclerView = new CScrollRecyclerView(context);
        mRecyclerView.setOverScrollMode(OVER_SCROLL_NEVER);
        if (wheelAttrs.isLoop()) {
            looperLayoutManager = new VerticalLooperLayoutManager(mRecyclerView, wheelAttrs.getItemSize());
            scrollEngine = looperLayoutManager;
            mRecyclerView.setLayoutManager(looperLayoutManager);
        } else {
            linearLayoutManager = new WheelLinearLayoutManager(context);
            scrollEngine = linearLayoutManager;
            mRecyclerView.setLayoutManager(linearLayoutManager);
        }
        int totalItemSize = (wheelAttrs.getHalfItemCount() * 2 + 1) * wheelAttrs.getItemSize();
        //让滑动结束时都能定到中心位置
        new LinearSnapHelper().attachToRecyclerView(mRecyclerView);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                totalItemSize);
        this.addView(mRecyclerView, params);

        wheelAdapter = new WheelViewAdapter(wheelAttrs);
        wheelDecoration = new WheelDecoration(wheelAdapter, wheelAttrs);
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

    //设置数据源
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

    //数据源发生变化
    @SuppressLint("NotifyDataSetChanged")
    private void dataSetChanged() {
        if (wheelAttrs.isLoop()) {
            looperLayoutManager.setDataCount(wheelAdapter.adapter.getItemCount());
        }
        wheelAdapter.notifyDataSetChanged();
    }

    //设置选中项
    public void setCurrentItem(int position) {
        scrollEngine.scrollToTargetPosition(position);
        dataPosition = position;
    }

    //获取当前的选中项
    public int getCurrentItem() {
        if (wheelAttrs.isLoop()) {
            int adapterCount = looperLayoutManager.getDataCount();
            if (wheelDecoration.centerRealPosition >= adapterCount) {
                return adapterCount - 1;
            }
            return wheelDecoration.centerRealPosition;
        } else {
            int adapterCount = linearLayoutManager.getItemCount();
            if (wheelDecoration.centerRealPosition >= adapterCount)
                return 0;
            int wheelCount = adapterCount - wheelAttrs.getHalfItemCount() * 2;
            if (wheelDecoration.centerRealPosition >= wheelCount) {
                return wheelCount - 1;
            }
            return wheelDecoration.centerRealPosition;
        }
    }


    //获取RecyclerView
    private CScrollRecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    //获取当前的滚轮样式
    public WheelAttrs getAttrs() {
        return wheelAttrs;
    }

    //当前的滚轮样式发生变化后，更新视图
    public void updateAttrs() {
        setAttrs(wheelAttrs);
    }

    //动态 重新设置滚轮样式
    public void setAttrs(WheelAttrs attrs) {
        if (mState != RecyclerView.SCROLL_STATE_IDLE) {
            return;
        }
        if (attrs != null) {
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

    //设置滚动状态和选中监听
    public void setOnWheelScrollListener(OnWheelScrollListener listener) {
        this.listener = listener;
    }

    //设置数据源格式化
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
