package com.Jfpicker.wheelpicker.picker_option.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.Jfpicker.wheelpicker.R;
import com.Jfpicker.wheelpicker.picker_base.WheelDataAdapter;
import com.Jfpicker.wheelpicker.picker_base.WheelFormatter;
import com.Jfpicker.wheelpicker.picker_option.entity.LinkageProvider;
import com.Jfpicker.wheelpicker.wheelview.WheelView;

/**
 * 三级联动的滚动选择布局
 * 参考了AndroidPicker的LinkageWheelLayout代码
 * 源码地址：https://github.com/gzu-liyujiang/AndroidPicker
 */
@SuppressWarnings("unused")
public class LinkageWheelLayout extends LinearLayout {
    private WheelView wheelViewFirst, wheelViewSecond, wheelViewThird;
    private WheelDataAdapter adapterFirst, adapterSecond, adapterThird;
    private TextView tvFirstLabel, tvSecondLabel, tvThirdLabel;
    private ProgressBar pbLoading;
    private Object firstValue, secondValue, thirdValue;
    private int firstIndex, secondIndex, thirdIndex;
    private LinkageProvider dataProvider;


    public LinkageWheelLayout(Context context) {
        super(context);
        init(context, null);
    }

    public LinkageWheelLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public LinkageWheelLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public LinkageWheelLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        setOrientation(VERTICAL);
        inflate(context, R.layout.wheel_picker_linkage, this);
        onInit(context);
    }

    protected void onInit(@NonNull Context context) {
        wheelViewFirst = findViewById(R.id.wheelViewFirst);
        wheelViewSecond = findViewById(R.id.wheelViewSecond);
        wheelViewThird = findViewById(R.id.wheelViewThird);
        tvFirstLabel = findViewById(R.id.tvFirstLabel);
        tvSecondLabel = findViewById(R.id.tvSecondLabel);
        tvThirdLabel = findViewById(R.id.tvThirdLabel);
        pbLoading = findViewById(R.id.pbLoading);
        wheelViewFirst.setOnItemSelectedListener(new WheelView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(WheelView wheelView, int index) {
                firstIndex = index;
                secondIndex = 0;
                thirdIndex = 0;
                changeSecondData();
                changeThirdData();
            }

            @Override
            public void onScrollStatusChange(boolean scrolling) {
                wheelViewSecond.setEnabled(!scrolling);
                wheelViewThird.setEnabled(!scrolling);
            }
        });
        wheelViewSecond.setOnItemSelectedListener(new WheelView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(WheelView wheelView, int index) {
                secondIndex = index;
                thirdIndex = 0;
                changeThirdData();
            }

            @Override
            public void onScrollStatusChange(boolean scrolling) {
                wheelViewFirst.setEnabled(!scrolling);
                wheelViewThird.setEnabled(!scrolling);
            }
        });
        wheelViewThird.setOnItemSelectedListener(new WheelView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(WheelView wheelView, int index) {
                thirdIndex = index;
            }

            @Override
            public void onScrollStatusChange(boolean scrolling) {
                wheelViewFirst.setEnabled(!scrolling);
                wheelViewSecond.setEnabled(!scrolling);
            }
        });
    }


    //设置数据源
    public void setData(@NonNull LinkageProvider provider) {
        setFirstVisible(provider.firstLevelVisible());
        setThirdVisible(provider.thirdLevelVisible());
        if (firstValue != null) {
            firstIndex = provider.findFirstIndex(firstValue);
        }
        if (secondValue != null) {
            secondIndex = provider.findSecondIndex(firstIndex, secondValue);
        }
        if (thirdValue != null) {
            thirdIndex = provider.findThirdIndex(firstIndex, secondIndex, thirdValue);
        }
        dataProvider = provider;
        changeFirstData();
        changeSecondData();
        changeThirdData();
    }

    //设置默认数据
    public void setDefaultValue(Object first, Object second, Object third) {
        if (dataProvider != null) {
            firstIndex = dataProvider.findFirstIndex(first);
            secondIndex = dataProvider.findSecondIndex(firstIndex, second);
            thirdIndex = dataProvider.findThirdIndex(firstIndex, secondIndex, third);
            changeFirstData();
            changeSecondData();
            changeThirdData();
        } else {
            this.firstValue = first;
            this.secondValue = second;
            this.thirdValue = third;
        }
    }

    public void setFormatter(WheelFormatter formatter) {
        wheelViewFirst.setFormatter(formatter);
        wheelViewSecond.setFormatter(formatter);
        wheelViewThird.setFormatter(formatter);
    }

    public void setFormatter(WheelFormatter first, WheelFormatter second, WheelFormatter third) {
        wheelViewFirst.setFormatter(first);
        wheelViewSecond.setFormatter(second);
        wheelViewThird.setFormatter(third);
    }

    public void setLabel(CharSequence first, CharSequence second, CharSequence third) {
        tvFirstLabel.setText(first);
        tvSecondLabel.setText(second);
        tvThirdLabel.setText(third);
    }

    public void showLoading() {
        pbLoading.setVisibility(VISIBLE);
    }

    public void hideLoading() {
        pbLoading.setVisibility(GONE);
    }


    public void setFirstVisible(boolean visible) {
        if (visible) {
            wheelViewFirst.setVisibility(VISIBLE);
            tvFirstLabel.setVisibility(VISIBLE);
        } else {
            wheelViewFirst.setVisibility(GONE);
            tvFirstLabel.setVisibility(GONE);
        }
    }

    public void setThirdVisible(boolean visible) {
        if (visible) {
            wheelViewThird.setVisibility(VISIBLE);
            tvThirdLabel.setVisibility(VISIBLE);
        } else {
            wheelViewThird.setVisibility(GONE);
            tvThirdLabel.setVisibility(GONE);
        }
    }


    //修改第一级数据
    private void changeFirstData() {
        adapterFirst = new WheelDataAdapter();
        adapterFirst.strs.clear();
        adapterFirst.strs.addAll(dataProvider.provideFirstData());
        wheelViewFirst.setAdapter(adapterFirst);
        wheelViewFirst.setCurrentItem(firstIndex);
    }

    //修改第二级数据
    private void changeSecondData() {
        adapterSecond = new WheelDataAdapter();
        adapterSecond.strs.clear();
        adapterSecond.strs.addAll(dataProvider.linkageSecondData(firstIndex));
        wheelViewSecond.setAdapter(adapterSecond);

        wheelViewSecond.setCurrentItem(secondIndex);
    }

    //修改第三级数据
    private void changeThirdData() {
        if (!dataProvider.thirdLevelVisible()) {
            return;
        }
        adapterThird = new WheelDataAdapter();
        adapterThird.strs.clear();
        adapterThird.strs.addAll(dataProvider.linkageThirdData(firstIndex, secondIndex));
        wheelViewThird.setAdapter(adapterThird);

        wheelViewThird.setCurrentItem(thirdIndex);
    }

    public final WheelView getFirstWheelView() {
        return wheelViewFirst;
    }

    public Object getSelectFirst() {
        if (wheelViewFirst.getCurrentItem() > -1) {
            return adapterFirst.strs.get(wheelViewFirst.getCurrentItem());
        } else {
            return null;
        }
    }

    public Object getSelectSecond() {
        if (wheelViewSecond.getCurrentItem() > -1) {
            return adapterSecond.strs.get(wheelViewSecond.getCurrentItem());
        } else {
            return null;
        }
    }

    public Object getSelectThird() {
        if (wheelViewThird.getCurrentItem() > -1) {
            return adapterThird.strs.get(wheelViewThird.getCurrentItem());
        } else {
            return null;
        }
    }


    public final WheelView getSecondWheelView() {
        return wheelViewSecond;
    }

    public final WheelView getThirdWheelView() {
        return wheelViewThird;
    }

    public final TextView getFirstLabelView() {
        return tvFirstLabel;
    }

    public final TextView getSecondLabelView() {
        return tvSecondLabel;
    }

    public final TextView getThirdLabelView() {
        return tvThirdLabel;
    }

    public final ProgressBar getLoadingView() {
        return pbLoading;
    }

}
