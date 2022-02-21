package com.Jfpicker.wheelpicker.wheelview;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.Jfpicker.wheelpicker.utils.DensityUtils;
import com.Jfpicker.wheelpicker.R;
import com.Jfpicker.wheelpicker.picker_base.WheelFormatter;

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
    /**
     * 垂直与水平布局两种状态
     */
    public static final int WHEEL_VERTICAL = 1;
    public static final int WHEEL_HORIZONTAL = 2;


    /**
     * item color
     */
    private int textColor = Color.BLACK;
    /**
     * 中心item颜色
     */
    private int textColorCenter = Color.parseColor("#333333");
    /**
     * 分割线颜色
     */
    private int dividerColor = Color.BLACK;
    /**
     * 文本大小
     */
    private float textSize;
    /**
     * item数量
     */
    private int itemCount = 3;
    /**
     * item大小
     */
    private int itemSize;
    /**
     * 分割线之间距离
     */
    private int dividerSize;
    /**
     * 布局方向
     */
    private int orientation = WHEEL_VERTICAL;
    /**
     * 对齐方式
     */
    private int gravity = WheelDecoration.GRAVITY_CENTER;
    /**
     * 文字对齐方式
     */
    private int gravity_text = WheelDecoration.GRAVITY_CENTER;

    /**
     * 左右倾斜的幅度
     */
    private float gravityCoefficient = 0.75F;

    /**
     * 是否仿Ios样式的滚轮
     */
    private boolean isWheel = true;

    /**
     * 3D滚轮效果的总角度
     */
    private float itemDegreeTotal = 180.f;

    /**
     * 文字渐变
     */
    private boolean alphaGradient = true;

    /**
     * 文字是否加粗
     */
    private boolean isTextBlod = false;

    /**
     * 选中文字是否加粗
     */
    private boolean isCenterTextBlod = false;

    /**
     * 文字大小是否渐变
     */
    private boolean textSizeGradient = false;

    /**
     * 文字大小渐变 的最小文字尺寸
     */
    private float minGradientTextSize;

    /**
     * recyclerView
     */
    private CScrollRecyclerView mRecyclerView;
    private LinearLayoutManager layoutManager;

    private SimpleWheelDecoration wheelDecoration;
    private WheelViewAdapter wheelAdapter;

    private WheelDataAbstractAdapter mDataAdapter;
    private WheelViewObserver observer;


    private int mState = RecyclerView.SCROLL_STATE_IDLE;

    private int dataPosition = IDLE_POSITION;

    private WheelFormatter formatter;

    public void setFormatter(WheelFormatter formatter) {
        this.formatter = formatter;
        if (wheelDecoration != null) {
            wheelDecoration.setFormatter(formatter);
        }
    }


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
            itemCount = a.getInt(R.styleable.WheelView_wheelItemCount, itemCount);
            textColor = a.getColor(R.styleable.WheelView_wheelTextColor, textColor);
            textColorCenter = a.getColor(R.styleable.WheelView_wheelTextColorCenter, textColorCenter);
            dividerColor = a.getColor(R.styleable.WheelView_wheelDividerColor, dividerColor);
            textSize = a.getDimension(R.styleable.WheelView_wheelTextSize, DensityUtils.sp2px(context, 18));
            itemSize = a.getDimensionPixelOffset(R.styleable.WheelView_wheelItemSize, DensityUtils.dip2px(context, 35));
            dividerSize = a.getDimensionPixelOffset(R.styleable.WheelView_wheelDividerSize, DensityUtils.dip2px(context, 35));
            orientation = a.getInt(R.styleable.WheelView_wheelOrientation, orientation);
            gravity = a.getInt(R.styleable.WheelView_wheelGravity, gravity);
            gravity_text = a.getInt(R.styleable.WheelView_wheelTextGravity, gravity);
            gravityCoefficient = a.getFloat(R.styleable.WheelView_gravityCoefficient, gravityCoefficient);
            isWheel = a.getBoolean(R.styleable.WheelView_isWheel, true);
            itemDegreeTotal = a.getFloat(R.styleable.WheelView_itemDegreeTotal, itemDegreeTotal);
            alphaGradient = a.getBoolean(R.styleable.WheelView_alphaGradient, alphaGradient);
            isTextBlod = a.getBoolean(R.styleable.WheelView_isTextBlod, isTextBlod);
            isCenterTextBlod = a.getBoolean(R.styleable.WheelView_isCenterTextBlod, isCenterTextBlod);
            textSizeGradient = a.getBoolean(R.styleable.WheelView_textSizeGradient, textSizeGradient);
            minGradientTextSize = a.getDimension(R.styleable.WheelView_minGradientTextSize, DensityUtils.sp2px(context, 10));
            a.recycle();
        }
        initRecyclerView(context);
    }

    private void initRecyclerView(Context context) {
        removeAllViews();
        mRecyclerView = new CScrollRecyclerView(context);
        mRecyclerView.setOverScrollMode(OVER_SCROLL_NEVER);
        int totolItemSize = (itemCount * 2 + 1) * itemSize;
        layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(orientation == WHEEL_VERTICAL ?
                LinearLayoutManager.VERTICAL : LinearLayoutManager.HORIZONTAL);
        mRecyclerView.setLayoutManager(layoutManager);
        //让滑动结束时都能定到中心位置
        new LinearSnapHelper().attachToRecyclerView(mRecyclerView);
        this.addView(mRecyclerView, WheelUtils.createLayoutParams(orientation, totolItemSize));

        wheelAdapter = new WheelViewAdapter(orientation, itemSize, itemCount);
        wheelDecoration = new SimpleWheelDecoration(wheelAdapter, formatter, gravity, gravityCoefficient, isWheel,
                gravity_text, textColor, textColorCenter, textSize, dividerColor, dividerSize, itemDegreeTotal,
                alphaGradient, isTextBlod, isCenterTextBlod, textSizeGradient, minGradientTextSize);
        mRecyclerView.addItemDecoration(wheelDecoration);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

                mState = newState;
                if (listener == null || wheelDecoration == null) return;
                if (newState == RecyclerView.SCROLL_STATE_DRAGGING || newState == RecyclerView.SCROLL_STATE_SETTLING) {
                    listener.onScrollStatusChange(true);
                } else {
                    listener.onScrollStatusChange(false);
                }
                if (wheelDecoration.centerItemPosition == IDLE_POSITION || newState != RecyclerView.SCROLL_STATE_IDLE)
                    return;

                dataPosition = getCurrentItem();
                listener.onItemSelected(WheelView.this, dataPosition);

            }
        });
        mRecyclerView.setAdapter(wheelAdapter);
    }

    public void setAdapter(WheelDataAbstractAdapter adapter) {
        if (mDataAdapter != null) {
            mDataAdapter.setWheelViewObserver(null);
        }
        mDataAdapter = adapter;
        if (mDataAdapter != null) {
            if (observer == null) {
                observer = new WheelViewObserver();
            }
            mDataAdapter.setWheelViewObserver(observer);
            dataPosition = IDLE_POSITION;
            this.wheelAdapter.adapter = adapter;
            this.wheelAdapter.notifyDataSetChanged();
        }
    }

    public WheelDataAbstractAdapter getAdapter() {
        return mDataAdapter;
    }

    private void dataSetChanged() {
        this.wheelAdapter.notifyDataSetChanged();
    }

    public void setCurrentItem(int position) {
        layoutManager.scrollToPositionWithOffset(position, 0);
        dataPosition = position;
    }

    public int getCurrentItem() {
        int adapterCount = layoutManager.getItemCount();
        if (wheelDecoration.centerItemPosition >= adapterCount)
            return 0; //如果当前位置大于整个适配器大小,刷新时RecyclerView会回到第0个位置
        int wheelCount = adapterCount - itemCount * 2;
        if (wheelDecoration.centerItemPosition >= wheelCount) {
            return wheelCount - 1;
        }
        return wheelDecoration.centerItemPosition;
    }

    private OnItemSelectedListener listener;


    public void setOnItemSelectedListener(OnItemSelectedListener listener) {
        this.listener = listener;
    }


    public interface OnItemSelectedListener {
        void onItemSelected(WheelView wheelView, int index);

        void onScrollStatusChange(boolean scrolling);

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


    public static abstract class WheelDataAbstractAdapter {

        private DataSetObserver wheelObserver;

        void setWheelViewObserver(DataSetObserver observer) {
            synchronized (this) {
                wheelObserver = observer;
            }
        }

        protected abstract int getItemCount();

        protected abstract Object getItem(int index);

        public final void notifyDataSetChanged() {
            synchronized (this) {
                if (wheelObserver != null) {
                    wheelObserver.onChanged();
                }
            }
        }
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        if (mRecyclerView != null) {
            mRecyclerView.setCanScroll(enabled);
        }
    }

    //动态 重新设置滚轮样式
    public void setWheelAttrs(WheelAttrs attrs) {

        if (mState != RecyclerView.SCROLL_STATE_IDLE) {
            return;
        }
        if (attrs != null) {

            if (mRecyclerView != null && layoutManager != null
                    && wheelAdapter != null && wheelDecoration != null) {

                itemCount = attrs.getItemCount();
                textColor = attrs.getTextColor();
                textColorCenter = attrs.getTextColorCenter();
                dividerColor = attrs.getDividerColor();
                textSize = DensityUtils.sp2px(getContext(), attrs.getTextSize());
                itemSize = DensityUtils.dip2px(getContext(), attrs.getItemSize());
                dividerSize = DensityUtils.dip2px(getContext(), attrs.getDividerSize());
                gravity = attrs.getWheelGravity();
                gravity_text = attrs.getTextGravity();
                gravityCoefficient = attrs.getGravityCoefficient();
                isWheel = attrs.isWheel();
                itemDegreeTotal = attrs.getItemDegreeTotal();
                alphaGradient = attrs.isAlphaGradient();
                isTextBlod = attrs.isTextBlod();
                isCenterTextBlod = attrs.isCenterTextBlod();
                textSizeGradient = attrs.isTextSizeGradient();
                minGradientTextSize = DensityUtils.sp2px(getContext(), attrs.getMinGradientTextSize());

                initRecyclerView(getContext());
                if (mDataAdapter != null) {
                    this.wheelAdapter.adapter = mDataAdapter;
                    this.wheelAdapter.notifyDataSetChanged();
                    if (mDataAdapter.getItemCount() <= 0) {
                        return;
                    }
                    if (dataPosition > IDLE_POSITION && dataPosition < mDataAdapter.getItemCount()) {
                        setCurrentItem(dataPosition);
                    }
                }
            }

        }
    }

    //获取当前的滚轮样式
    public WheelAttrs.Builder getAttrsBuilder() {
        WheelAttrs.Builder builder = new WheelAttrs.Builder();
        return builder.setItemCount(itemCount)
                .setTextColor(textColor)
                .setTextColorCenter(textColorCenter)
                .setDividerColor(dividerColor)
                .setTextSize(DensityUtils.px2sp(getContext(), textSize))
                .setItemSize(DensityUtils.px2dip(getContext(), itemSize))
                .setDividerSize(DensityUtils.px2dip(getContext(), dividerSize))
                .setWheelGravity(gravity)
                .setTextGravity(gravity_text)
                .setGravityCoefficient(gravityCoefficient)
                .setIsWheel(isWheel)
                .setItemDegreeTotal(itemDegreeTotal)
                .setAlphaGradient(alphaGradient)
                .setTextBlod(isTextBlod)
                .setCenterTextBlod(isCenterTextBlod)
                .setTextSizeGradient(textSizeGradient)
                .setMinGradientTextSize(DensityUtils.px2sp(getContext(), minGradientTextSize));
    }

}
