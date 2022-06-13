package com.Jfpicker.wheelpicker.picker_option.widget;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.Jfpicker.wheelpicker.R;

import com.Jfpicker.wheelpicker.wheelview.WheelDataAdapter;
import com.Jfpicker.wheelpicker.wheelview.format.WheelFormatListener;
import com.Jfpicker.wheelpicker.picker_option.entity.OptionEntity;
import com.Jfpicker.wheelpicker.picker_option.formatter.OptionFormatter;
import com.Jfpicker.wheelpicker.wheelview.WheelView;
import com.Jfpicker.wheelpicker.wheelview.listener.OnWheelScrollListener;

import java.util.List;

/**
 * @author Created by JF on  2021/11/13
 * @description 单选布局，数据是OptionEntity的列表
 */

public class OptionWheelLayout extends LinearLayout {

    private WheelView wheelOption;

    private WheelDataAdapter adapterOption;
    private OnWheelScrollListener onWheelScrollListener;

    public OptionWheelLayout(Context context) {
        super(context);
        init(context);
    }

    public OptionWheelLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public OptionWheelLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public OptionWheelLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        setOrientation(VERTICAL);
        inflate(context, R.layout.wheel_picker_option, this);
        onInit();
    }

    private void onInit() {
        wheelOption = findViewById(R.id.wheelOption);
        setFormatter(new OptionFormatter());
    }

    //设置数据
    public void setWheelData(List<OptionEntity> list) {

        adapterOption = new WheelDataAdapter();
        adapterOption.objects.clear();
        adapterOption.objects.addAll(list);
        wheelOption.setOnWheelScrollListener(new OnWheelScrollListener() {
            @Override
            public void onItemChecked(WheelView wv, int index) {
                if (onWheelScrollListener != null) {
                    onWheelScrollListener.onItemChecked(wv, index);
                }
            }

            @Override
            public void onScrollStatusChange(boolean scrolling) {
                if (onWheelScrollListener != null) {
                    onWheelScrollListener.onScrollStatusChange(scrolling);
                }
            }

        });
        wheelOption.setAdapter(adapterOption);
    }

    //根据id设置选中的数据
    public void setChooseId(String chooseId) {
        if (!TextUtils.isEmpty(chooseId)) {
            if (adapterOption != null) {
                for (int i = 0; i < adapterOption.objects.size(); i++) {
                    if (chooseId.equals(((OptionEntity) adapterOption.objects.get(i)).getUniqueId())) {
                        wheelOption.setCurrentItem(i);
                        break;
                    }
                }
            }
        }
    }

    public void setWheelData(List<OptionEntity> list, String chooseId) {
        int indexOptionChoose = WheelView.IDLE_POSITION;
        adapterOption = new WheelDataAdapter();
        adapterOption.objects.clear();
        adapterOption.objects.addAll(list);
        if (!TextUtils.isEmpty(chooseId)) {
            for (int i = 0; i < list.size(); i++) {
                if (chooseId.equals(list.get(i).getUniqueId())) {
                    indexOptionChoose = i;
                    break;
                }
            }
        }

        wheelOption.setOnWheelScrollListener(new OnWheelScrollListener() {
            @Override
            public void onItemChecked(WheelView wv, int index) {

            }

            @Override
            public void onScrollStatusChange(boolean scrolling) {

            }

        });
        wheelOption.setAdapter(adapterOption);

        if (indexOptionChoose > WheelView.IDLE_POSITION) {
            wheelOption.setCurrentItem(indexOptionChoose);
        }

    }

    public void setFormatter(WheelFormatListener formatter) {
        wheelOption.setFormatter(formatter);
    }

    public String getSelectId() {
        if (wheelOption.getCurrentItem() > WheelView.IDLE_POSITION
                && adapterOption.objects.size() > 0) {
            return ((OptionEntity) adapterOption.objects.get(wheelOption.getCurrentItem())).getUniqueId();
        } else {
            return "";
        }
    }

    public String getSelectName() {
        if (wheelOption.getCurrentItem() > WheelView.IDLE_POSITION
                && adapterOption.objects.size() > 0) {
            return ((OptionEntity) adapterOption.objects.get(wheelOption.getCurrentItem())).getWheelItem();
        } else {
            return "";
        }
    }

    public void setOnWheelScrollListener(OnWheelScrollListener onWheelScrollListener) {
        this.onWheelScrollListener = onWheelScrollListener;
    }

    public WheelView getWheelOption() {
        return wheelOption;
    }


}
