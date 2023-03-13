package com.Jfpicker.sample;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.Jfpicker.sample.custom.LovelyBirthdayPicker;
import com.Jfpicker.wheelpicker.picker_date.BirthdayPicker;
import com.Jfpicker.wheelpicker.picker_date.BirthdayWithTimePicker;
import com.Jfpicker.wheelpicker.picker_date.DateTimePicker;
import com.Jfpicker.wheelpicker.picker_date.HourMinuteSecondPicker;
import com.Jfpicker.wheelpicker.picker_date.annotation.TimeMode;

import com.Jfpicker.wheelpicker.picker_date.YearMonthDayPicker;
import com.Jfpicker.wheelpicker.picker_date.annotation.DateMode;

import com.Jfpicker.wheelpicker.picker_date.widget.HourMinuteSecondWheelLayout;
import com.Jfpicker.wheelpicker.picker_date.widget.YearMonthDayWheelLayout;
import com.Jfpicker.wheelpicker.dialog.annotation.CornerRound;

import com.Jfpicker.wheelpicker.dialog.config.DialogConfig;
import com.Jfpicker.wheelpicker.dialog.annotation.DialogStyle;
import com.Jfpicker.wheelpicker.wheelview.WheelAttrs;
import com.Jfpicker.wheelpicker.wheelview.WheelView;
import com.Jfpicker.wheelpicker.wheelview.listener.OnWheelScrollListener;

public class DatePickActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_pick);

        YearMonthDayWheelLayout yearMonthDay = findViewById(R.id.wheelViewYearMonthDay);
        TextView tvYearMonthDay = findViewById(R.id.tvYearMonthDay);
        yearMonthDay.post(() -> tvYearMonthDay.setText("当前选中：" + yearMonthDay.getSelectYear() + "-" +
                yearMonthDay.getSelectMonth() + "-" + yearMonthDay.getSelectDay()));

        yearMonthDay.setOnWheelScrollListener(new OnWheelScrollListener() {
            @Override
            public void onItemChecked(WheelView wheelView, int index) {
                tvYearMonthDay.setText("当前选中：" + yearMonthDay.getSelectYear() + "-" +
                        yearMonthDay.getSelectMonth() + "-" + yearMonthDay.getSelectDay());
            }

            @Override
            public void onScrollStatusChange(boolean scrolling) {

            }
        });
        HourMinuteSecondWheelLayout hourMinuteSecond = findViewById(R.id.wheelViewHourMinuteSecond);
        TextView tvHourMinuteSecond = findViewById(R.id.tvHourMinuteSecond);
        hourMinuteSecond.post(new Runnable() {
            @Override
            public void run() {
                tvHourMinuteSecond.setText("当前选中：" + hourMinuteSecond.getSelectHour() + ":" +
                        hourMinuteSecond.getSelectMinute() + ":" + hourMinuteSecond.getSelectSecond());
            }
        });
        hourMinuteSecond.setOnWheelScrollListener(new OnWheelScrollListener() {
            @Override
            public void onItemChecked(WheelView wheelView, int index) {
                tvHourMinuteSecond.setText("当前选中：" + hourMinuteSecond.getSelectHour() + ":" +
                        hourMinuteSecond.getSelectMinute() + ":" + hourMinuteSecond.getSelectSecond());
            }

            @Override
            public void onScrollStatusChange(boolean scrolling) {

            }
        });


        findViewById(R.id.btnYearMonthDay).setOnClickListener(v -> {
            YearMonthDayPicker picker = new YearMonthDayPicker(DatePickActivity.this);
            //设置数据无限循环效果
//            picker.getWheelLayout().getWheelViewDay().getAttrs().setLoop(true);
//            picker.getWheelLayout().getWheelViewDay().updateAttrs();
            picker.setOnDatePickedListener((year, month, day) ->
                    Toast.makeText(DatePickActivity.this,
                            year + "-" + month + "-" + day, Toast.LENGTH_SHORT).show());
            picker.show();
        });
        findViewById(R.id.btnYearMonth).setOnClickListener(v -> {
            YearMonthDayPicker picker = new YearMonthDayPicker(DatePickActivity.this);
            picker.getWheelLayout().setDateMode(DateMode.YEAR_MONTH);
            picker.setOnDatePickedListener((year, month, day) ->
                    Toast.makeText(DatePickActivity.this,
                            year + "-" + month, Toast.LENGTH_SHORT).show());
            picker.show();
        });
        findViewById(R.id.btnMonthDay).setOnClickListener(v -> {
            YearMonthDayPicker picker = new YearMonthDayPicker(DatePickActivity.this);
            picker.getWheelLayout().setDateMode(DateMode.MONTH_DAY);
            picker.setOnDatePickedListener((year, month, day) ->
                    Toast.makeText(DatePickActivity.this,
                            month + "-" + day, Toast.LENGTH_SHORT).show());
            picker.show();
        });
        findViewById(R.id.btnCustomYearMonthDay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                YearMonthDayPicker picker = new YearMonthDayPicker(DatePickActivity.this);
                WheelAttrs attrs = new WheelAttrs();
                attrs.setCheckedTextColor(getColor(R.color.colorPrimary));
                attrs.setItemSize(120);
                attrs.setDividerSize(120);
                attrs.setWheel(false);
                attrs.setHalfItemCount(2);
                attrs.setDividerSize(120);
                picker.getWheelLayout().setAllWheelViewAttrs(attrs);
                picker.getConfirmTextView().setTextColor(getColor(R.color.colorPrimary));
                picker.getWheelLayout().setLabelTextColor(getColor(R.color.colorPrimary));
                picker.setOnDatePickedListener((year, month, day) ->
                        Toast.makeText(DatePickActivity.this,
                                year + "-" + month + "-" + day, Toast.LENGTH_SHORT).show());
                picker.show();
            }
        });

        findViewById(R.id.btnBirthday).setOnClickListener(v -> {
            BirthdayPicker picker = new BirthdayPicker(DatePickActivity.this);
            picker.setOnDatePickedListener((year, month, day) ->
                    Toast.makeText(DatePickActivity.this,
                            year + "-" + month + "-" + day, Toast.LENGTH_SHORT).show());
            picker.show();
        });

        findViewById(R.id.btnYearMonthDayCustom).setOnClickListener(v -> {
            DialogConfig config = new DialogConfig();
            config.setDialogStyle(DialogStyle.center);
            config.setDialogWidthP(0.7F)
                    .setDialogCornerRound(CornerRound.All)
                    .setCornerRadius(15)
                    .setContentBackgroundColor(Color.WHITE);
            LovelyBirthdayPicker picker = new LovelyBirthdayPicker(DatePickActivity.this, config);
            picker.setOnDatePickedListener((year, month, day) ->
                    Toast.makeText(DatePickActivity.this,
                            year + "-" + month + "-" + day, Toast.LENGTH_SHORT).show());
            picker.show();

        });

        findViewById(R.id.btnHourMinuteSecond).setOnClickListener(v -> {
            HourMinuteSecondPicker picker = new HourMinuteSecondPicker(DatePickActivity.this);
            picker.setOnTimePickedListener((hour, minute, second)
                    -> Toast.makeText(DatePickActivity.this,
                    hour + "-" + minute + "-" + second, Toast.LENGTH_SHORT).show());
            picker.show();
        });
        findViewById(R.id.btnhourMinute).setOnClickListener(v -> {
            HourMinuteSecondPicker picker = new HourMinuteSecondPicker(DatePickActivity.this);
            picker.getWheelLayout().setTimeMode(TimeMode.HOUR_24_NO_SECOND);
            picker.setOnTimePickedListener((hour, minute, second) ->
                    Toast.makeText(DatePickActivity.this,
                            hour + "-" + minute, Toast.LENGTH_SHORT).show());
            picker.show();
        });
        findViewById(R.id.btnMinuteSecond).setOnClickListener(v -> {
            HourMinuteSecondPicker picker = new HourMinuteSecondPicker(DatePickActivity.this);
            picker.getWheelLayout().setTimeMode(TimeMode.HOUR_24_NO_HOUR);
            picker.setOnTimePickedListener((hour, minute, second) ->
                    Toast.makeText(DatePickActivity.this,
                            minute + "-" + second, Toast.LENGTH_SHORT).show());
            picker.show();
        });
        findViewById(R.id.btnCustomHourMinuteSecond).setOnClickListener(v -> {
            HourMinuteSecondPicker picker = new HourMinuteSecondPicker(DatePickActivity.this);
            WheelAttrs attrs = new WheelAttrs();
            attrs.setCheckedTextColor(getColor(R.color.colorPrimary));
            attrs.setItemSize(120);
            attrs.setDividerSize(120);
            attrs.setWheel(false);
            attrs.setHalfItemCount(2);
            picker.getWheelLayout().setAllWheelViewAttrs(attrs);
            picker.getConfirmTextView().setTextColor(getColor(R.color.colorPrimary));
            picker.getWheelLayout().setLabelTextColor(getColor(R.color.colorPrimary));
            picker.setOnTimePickedListener((hour, minute, second) ->
                    Toast.makeText(DatePickActivity.this,
                            hour + "-" + minute + "-" + second, Toast.LENGTH_SHORT).show());
            picker.show();
        });

        findViewById(R.id.btnDateTime).setOnClickListener(v -> {
            DateTimePicker picker = new DateTimePicker(DatePickActivity.this);
            picker.setOnDateTimePickedListener((year, month, day, hour, minute, second) ->
                    Toast.makeText(DatePickActivity.this,
                            year + "-" + month + "-" + day + "    " + hour + "-" + minute + "-" + second, Toast.LENGTH_SHORT).show());
            picker.show();
        });
        findViewById(R.id.btnDateTimeBirthday).setOnClickListener(v -> {
            BirthdayWithTimePicker picker = new BirthdayWithTimePicker(DatePickActivity.this);
            picker.setOnDateTimePickedListener((year, month, day, hour, minute, second) ->
                    Toast.makeText(DatePickActivity.this,
                            year + "-" + month + "-" + day + "    " + hour + "-" + minute + "-" + second, Toast.LENGTH_SHORT).show());
            picker.show();
        });

    }
}