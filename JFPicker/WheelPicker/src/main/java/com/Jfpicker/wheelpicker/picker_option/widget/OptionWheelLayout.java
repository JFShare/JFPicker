package com.Jfpicker.wheelpicker.picker_option.widget;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.Jfpicker.wheelpicker.R;

import com.Jfpicker.wheelpicker.picker_base.WheelDataAdapter;
import com.Jfpicker.wheelpicker.picker_base.WheelFormatter;
import com.Jfpicker.wheelpicker.picker_option.entity.OptionEntity;
import com.Jfpicker.wheelpicker.picker_option.formatter.OptionFormatter;
import com.Jfpicker.wheelpicker.wheelview.WheelView;

import java.util.List;

/**
 * @author Created by JF on  2021/11/13 8:32
 * @description
 */

public class OptionWheelLayout extends LinearLayout {

    private WheelView wheelOption;
    private TextView tvOptionLabel;
    private WheelDataAdapter adapterOption;

    private int indexOptionChooose = -1;

    private OptionFormatter formatter = new OptionFormatter();

    public OptionWheelLayout(Context context) {
        super(context);
        init(context, null);
    }

    public OptionWheelLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public OptionWheelLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public OptionWheelLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        setOrientation(VERTICAL);
        inflate(context, R.layout.wheel_picker_option, this);
        onInit(context);
    }

    private void onInit(@NonNull Context context) {
        wheelOption = findViewById(R.id.wheelOption);
        tvOptionLabel = findViewById(R.id.tvOptionLabel);
        setFormatter(formatter);
    }

    public void setWheelData(List<OptionEntity> list) {

        indexOptionChooose = -1;
        adapterOption = new WheelDataAdapter();
        adapterOption.strs.clear();
        adapterOption.strs.addAll(list);
        wheelOption.setOnItemSelectedListener(new WheelView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(WheelView wv, int index) {

            }

            @Override
            public void onScrollStatusChange(boolean scrolling) {

            }

        });
        wheelOption.setAdapter(adapterOption);
    }

    public void setChooseId(String chooseId) {
        if (!TextUtils.isEmpty(chooseId)) {
            if (adapterOption != null) {
                for (int i = 0; i < adapterOption.strs.size(); i++) {
                    if (chooseId.equals(((OptionEntity) adapterOption.strs.get(i)).getIdentifiy())) {
                        wheelOption.setCurrentItem(i);
                        break;
                    }
                }
            }
        }
    }

    public void setWheelData(List<OptionEntity> list, String chooseId) {
        indexOptionChooose = -1;
        adapterOption = new WheelDataAdapter();
        adapterOption.strs.clear();
        adapterOption.strs.addAll(list);
        if (!TextUtils.isEmpty(chooseId)) {
            for (int i = 0; i < list.size(); i++) {
                if (chooseId.equals(list.get(i).getIdentifiy())) {
                    indexOptionChooose = i;
                    break;
                }
            }
        }

        wheelOption.setOnItemSelectedListener(new WheelView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(WheelView wv, int index) {

            }

            @Override
            public void onScrollStatusChange(boolean scrolling) {

            }

        });
        wheelOption.setAdapter(adapterOption);

        if (indexOptionChooose > 0) {
            wheelOption.setCurrentItem(indexOptionChooose);
        }

    }

    public void setFormatter(OptionFormatter formatter) {
        this.formatter = formatter;
        wheelOption.setFormatter(new WheelFormatter() {
            @Override
            public String formatItem(@NonNull Object item) {
                return formatter.formatOption((OptionEntity) item);
            }
        });
    }

    public String getSelectId() {
        if (wheelOption.getCurrentItem() > -1) {
            return ((OptionEntity) adapterOption.strs.get(wheelOption.getCurrentItem())).getIdentifiy();
        } else {
            return "";
        }
    }

    public String getSelectItemName() {
        if (wheelOption.getCurrentItem() > -1) {
            return ((OptionEntity) adapterOption.strs.get(wheelOption.getCurrentItem())).getWheelItem();
        } else {
            return "";
        }
    }

    public WheelView getWheelOption() {
        return wheelOption;
    }

    public TextView getTvOptionLabel() {
        return tvOptionLabel;
    }

}
