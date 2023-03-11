package com.Jfpicker.sample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.Jfpicker.wheelpicker.wheelview.WheelDataAdapter;
import com.Jfpicker.wheelpicker.wheelview.WheelAttrs;
import com.Jfpicker.wheelpicker.wheelview.WheelView;
import com.Jfpicker.wheelpicker.wheelview.format.AlphaGradientListener;
import com.Jfpicker.wheelpicker.wheelview.format.TextSizeGradientListener;
import com.Jfpicker.wheelpicker.wheelview.format.WheelFormatListener;
import com.Jfpicker.wheelpicker.wheelview.listener.OnWheelScrollListener;

import java.util.ArrayList;

public class WheelViewActivity extends AppCompatActivity {

    public static void start(Context context) {
        context.startActivity(new Intent(context, WheelViewActivity.class));
    }

    private WheelView wheelView;
    private boolean isCityData = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wheel_view);
        wheelView = findViewById(R.id.wheelView);
        initWheelView();
        initOp();
    }

    private void initWheelView() {
        WheelDataAdapter adapter = new WheelDataAdapter();
        adapter.objects.clear();
        adapter.objects.addAll(getCityData());
        wheelView.setAdapter(adapter);
        wheelView.setCurrentItem(5);
        TextView tvCurrent = findViewById(R.id.tvCurrent);
        wheelView.setOnWheelScrollListener(new OnWheelScrollListener() {
            @Override
            public void onItemChecked(WheelView wheelView, int index) {
                tvCurrent.setText("当前选中：" + adapter.objects.get(index).toString());
            }

            @Override
            public void onScrollStatusChange(boolean scrolling) {

            }
        });
        wheelView.post(new Runnable() {
            @Override
            public void run() {
                tvCurrent.setText("当前选中：" + adapter.objects.get(wheelView.getCurrentItem()).toString());
            }
        });

        Button btnChangeDataSource = findViewById(R.id.btnChangeDataSource);
        btnChangeDataSource.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.objects.clear();
                if (isCityData){
                    adapter.objects.addAll(getNumberData());
                }else {
                    adapter.objects.addAll(getCityData());
                }
                adapter.notifyDataSetChanged();
                isCityData=!isCityData;
            }
        });
    }

    private void initOp() {
        RadioGroup rgIsWheel = findViewById(R.id.rgIsWheel);
        rgIsWheel.setOnCheckedChangeListener((group, checkedId) -> {
            if (R.id.rbIsWheel == checkedId) {
                wheelView.getAttrs().setWheel(true);
                wheelView.updateAttrs();
            }
            if (R.id.rbIsNotWheel == checkedId) {
                wheelView.getAttrs().setWheel(false);
                wheelView.updateAttrs();
            }
        });
        RadioGroup rgIsLoop = findViewById(R.id.rgIsLoop);
        rgIsLoop.setOnCheckedChangeListener((group, checkedId) -> {
            if (R.id.rbIsLoop == checkedId) {
                wheelView.getAttrs().setLoop(true);
                wheelView.updateAttrs();
            }
            if (R.id.rbIsNotLoop == checkedId) {
                wheelView.getAttrs().setLoop(false);
                wheelView.updateAttrs();
            }
        });
        RadioGroup rgTranslateYZ = findViewById(R.id.rgTranslateYZ);
        rgTranslateYZ.setOnCheckedChangeListener((group, checkedId) -> {
            if (R.id.rbIsTranslateYZ == checkedId) {
                wheelView.getAttrs().setTranslateYZ(true);
                wheelView.updateAttrs();
            }
            if (R.id.rbIsNotTranslateYZ == checkedId) {
                wheelView.getAttrs().setTranslateYZ(false);
                wheelView.updateAttrs();
            }
        });
        EditText etItemDegreeTotal = findViewById(R.id.etItemDegreeTotal);
        etItemDegreeTotal.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s != null && !TextUtils.isEmpty(s.toString())) {
                    wheelView.getAttrs().setItemDegreeTotal(Float.parseFloat(s.toString()));
                    wheelView.updateAttrs();
                }
            }
        });
        EditText etHalfItemCount = findViewById(R.id.etHalfItemCount);
        etHalfItemCount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s != null && !TextUtils.isEmpty(s.toString())) {
                    wheelView.getAttrs().setHalfItemCount(Integer.parseInt(s.toString()));
                    wheelView.updateAttrs();
                }
            }
        });

        EditText etItemSize = findViewById(R.id.etItemSize);
        etItemSize.setText(wheelView.getAttrs().getItemSize() + "");
        etItemSize.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s != null && !TextUtils.isEmpty(s.toString())) {
                    wheelView.getAttrs().setItemSize(Integer.parseInt(s.toString()));
                    wheelView.updateAttrs();
                }
            }
        });

        RadioGroup rgTextColor = findViewById(R.id.rgTextColor);
        rgTextColor.setOnCheckedChangeListener((group, checkedId) -> {
            if (R.id.rbTextColorBlack == checkedId) {
                wheelView.getAttrs().setTextColor(Color.parseColor("#333333"));
                wheelView.updateAttrs();
            }
            if (R.id.rbTextColorRed == checkedId) {
                wheelView.getAttrs().setTextColor(Color.parseColor("#FF0000"));
                wheelView.updateAttrs();
            }
            if (R.id.rbTextColorGreen == checkedId) {
                wheelView.getAttrs().setTextColor(Color.parseColor("#00FF00"));
                wheelView.updateAttrs();
            }
        });

        RadioGroup rgCheckedTextColor = findViewById(R.id.rgCheckedTextColor);
        rgCheckedTextColor.setOnCheckedChangeListener((group, checkedId) -> {
            if (R.id.rbCheckedTextColorBlack == checkedId) {
                wheelView.getAttrs().setCheckedTextColor(Color.parseColor("#333333"));
                wheelView.updateAttrs();
            }
            if (R.id.rbCheckedTextColorRed == checkedId) {
                wheelView.getAttrs().setCheckedTextColor(Color.parseColor("#FF0000"));
                wheelView.updateAttrs();
            }
            if (R.id.rbCheckedTextColorGreen == checkedId) {
                wheelView.getAttrs().setCheckedTextColor(Color.parseColor("#00FF00"));
                wheelView.updateAttrs();
            }
        });
        EditText etTextSize = findViewById(R.id.etTextSize);
        etTextSize.setText(wheelView.getAttrs().getTextSize() + "");
        etTextSize.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    wheelView.getAttrs().setTextSize(Float.parseFloat(s.toString()));
                    wheelView.updateAttrs();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        EditText etCheckedTextSize = findViewById(R.id.etCheckedTextSize);
        etCheckedTextSize.setText(wheelView.getAttrs().getCheckedTextSize() + "");
        etCheckedTextSize.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    wheelView.getAttrs().setCheckedTextSize(Float.parseFloat(s.toString()));
                    wheelView.updateAttrs();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        RadioGroup rgBold = findViewById(R.id.rgBold);
        rgBold.setOnCheckedChangeListener((group, checkedId) -> {
            if (R.id.rbBold == checkedId) {
                wheelView.getAttrs().setTextBold(true);
                wheelView.updateAttrs();
            }
            if (R.id.rbNotBold == checkedId) {
                wheelView.getAttrs().setTextBold(false);
                wheelView.updateAttrs();
            }
        });

        RadioGroup rgCheckedBold = findViewById(R.id.rgCheckedBold);
        rgCheckedBold.setOnCheckedChangeListener((group, checkedId) -> {
            if (R.id.rbCheckedBold == checkedId) {
                wheelView.getAttrs().setCheckedTextBold(true);
                wheelView.updateAttrs();
            }
            if (R.id.rbNotCheckedBold == checkedId) {
                wheelView.getAttrs().setCheckedTextBold(false);
                wheelView.updateAttrs();
            }
        });

        RadioGroup rgDividerType = findViewById(R.id.rgDividerType);
        rgDividerType.setOnCheckedChangeListener((group, checkedId) -> {
            if (R.id.rbDividerTypeLine == checkedId) {
                wheelView.getAttrs().setDividerType(WheelAttrs.DIVIDER_LINE);
                wheelView.updateAttrs();
            }
            if (R.id.rbDividerTypeRectangle == checkedId) {
                wheelView.getAttrs().setDividerType(WheelAttrs.DIVIDER_RECTANGLE);
                wheelView.updateAttrs();
            }
        });

        EditText etDividerSize = findViewById(R.id.etDividerSize);
        etDividerSize.setText(wheelView.getAttrs().getDividerSize() + "");
        etDividerSize.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    wheelView.getAttrs().setDividerSize(Float.parseFloat(s.toString()));
                    wheelView.updateAttrs();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


        RadioGroup rgDividerBgColor = findViewById(R.id.rgDividerBgColor);
        rgDividerBgColor.setOnCheckedChangeListener((group, checkedId) -> {
            if (R.id.rbDividerBgColorBlack == checkedId) {
                wheelView.getAttrs().setDividerBgColor(Color.parseColor("#00000000"));
                wheelView.updateAttrs();
            }
            if (R.id.rbDividerBgColorRed == checkedId) {
                wheelView.getAttrs().setDividerBgColor(Color.parseColor("#FF0000"));
                wheelView.updateAttrs();
            }
            if (R.id.rbDividerBgColorGreen == checkedId) {
                wheelView.getAttrs().setDividerBgColor(Color.parseColor("#00FF00"));
                wheelView.updateAttrs();
            }
        });
        RadioGroup rgDividerColor = findViewById(R.id.rgDividerColor);
        rgDividerColor.setOnCheckedChangeListener((group, checkedId) -> {
            if (R.id.rbDividerColorBlack == checkedId) {
                wheelView.getAttrs().setDividerColor(Color.parseColor("#dedede"));
                wheelView.updateAttrs();
            }
            if (R.id.rbDividerColorRed == checkedId) {
                wheelView.getAttrs().setDividerColor(Color.parseColor("#FF0000"));
                wheelView.updateAttrs();
            }
            if (R.id.rbDividerColorGreen == checkedId) {
                wheelView.getAttrs().setDividerColor(Color.parseColor("#00FF00"));
                wheelView.updateAttrs();
            }
        });

        EditText etDividerStrokeWidth = findViewById(R.id.etDividerStrokeWidth);
        etDividerStrokeWidth.setText(wheelView.getAttrs().getDividerStrokeWidth() + "");
        etDividerStrokeWidth.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    wheelView.getAttrs().setDividerStrokeWidth(Integer.parseInt(s.toString()));
                    wheelView.updateAttrs();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        EditText etDividerCorner = findViewById(R.id.etDividerCorner);
        etDividerCorner.setText(wheelView.getAttrs().getDividerCorner() + "");
        etDividerCorner.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    wheelView.getAttrs().setDividerCorner(Integer.parseInt(s.toString()));
                    wheelView.updateAttrs();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        RadioGroup rgAlphaGradient = findViewById(R.id.rgAlphaGradient);
        rgAlphaGradient.setOnCheckedChangeListener((group, checkedId) -> {
            if (R.id.rbAlphaGradient == checkedId) {
                wheelView.getAttrs().setAlphaFormat(new MyAlphaFormatter());
                wheelView.updateAttrs();
            }
            if (R.id.rbNotAlphaGradient == checkedId) {
                wheelView.getAttrs().setAlphaFormat(null);
                wheelView.updateAttrs();
            }
        });

        RadioGroup rgTextSizeGradient = findViewById(R.id.rgTextSizeGradient);
        rgTextSizeGradient.setOnCheckedChangeListener((group, checkedId) -> {
            if (R.id.rbTextSizeGradient == checkedId) {
                wheelView.getAttrs().setTextSizeFormat(new MyTextSizeFormatter());
                wheelView.updateAttrs();
            }
            if (R.id.rbNotTextSizeGradient == checkedId) {
                wheelView.getAttrs().setTextSizeFormat(null);
                wheelView.updateAttrs();
            }
        });

        RadioGroup rgTextFormat = findViewById(R.id.rgTextFormat);
        rgTextFormat.setOnCheckedChangeListener((group, checkedId) -> {
            if (R.id.rbTextFormat == checkedId) {
                wheelView.getAttrs().setFormatter(new MyTextFormatter());
                wheelView.updateAttrs();
            }
            if (R.id.rbNotTextFormat == checkedId) {
                wheelView.getAttrs().setFormatter(null);
                wheelView.updateAttrs();
            }
        });
    }

    class MyAlphaFormatter implements AlphaGradientListener {

        @Override
        public int onGradient(int position) {
            return 255 - Math.abs(position) * 60;
        }
    }

    class MyTextSizeFormatter implements TextSizeGradientListener {

        @Override
        public float onFormat(float standardSize, int position) {
            return standardSize - Math.abs(position) * 4;
        }
    }

    class MyTextFormatter implements WheelFormatListener {

        @Override
        public String formatItem(@NonNull Object item) {
            return item + "自定义后缀";
        }
    }

    private ArrayList<String> getCityData(){
        ArrayList<String> strList = new ArrayList<>();
        strList.add("北京市");
        strList.add("天津市");
        strList.add("河北省");
        strList.add("山西省");
        strList.add("内蒙古自治区");
        strList.add("辽宁省");
        strList.add("吉林省");
        strList.add("黑龙江省");
        strList.add("上海市");
        strList.add("江苏省");
        return strList;
    }
    private ArrayList<String> getNumberData(){
        ArrayList<String> strList = new ArrayList<>();
        strList.add("11");
        strList.add("22");
        strList.add("33");
        strList.add("44");
        return strList;
    }
}