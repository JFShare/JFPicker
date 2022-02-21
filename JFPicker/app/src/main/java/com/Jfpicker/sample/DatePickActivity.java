package com.Jfpicker.sample;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.Jfpicker.sample.custom.LovelyBirthdayPicker;
import com.Jfpicker.wheelpicker.picker_date.BirthdayPicker;
import com.Jfpicker.wheelpicker.picker_date.BirthdayWithTimePicker;
import com.Jfpicker.wheelpicker.picker_date.DateTimePicker;
import com.Jfpicker.wheelpicker.picker_date.HourMinuteSecondPicker;
import com.Jfpicker.wheelpicker.picker_date.annotation.TimeMode;
import com.Jfpicker.wheelpicker.picker_date.listener.OnDateTimePickedListener;
import com.Jfpicker.wheelpicker.picker_date.listener.OnTimePickedListener;

import com.Jfpicker.wheelpicker.picker_date.YearMonthDayPicker;
import com.Jfpicker.wheelpicker.picker_date.annotation.DateMode;
import com.Jfpicker.wheelpicker.picker_date.listener.OnDatePickedListener;
import com.Jfpicker.wheelpicker.picker_date.widget.YearMonthDayWheelLayout;
import com.Jfpicker.wheelpicker.wheel_dialog.CornerRound;
import com.Jfpicker.wheelpicker.wheel_dialog.DialogBackground;
import com.Jfpicker.wheelpicker.wheel_dialog.DialogConfig;
import com.Jfpicker.wheelpicker.wheel_dialog.DialogStyle;
import com.Jfpicker.wheelpicker.wheelview.WheelAttrs;

public class DatePickActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_pick);

        YearMonthDayWheelLayout datelayout = findViewById(R.id.datelayout);
        findViewById(R.id.btnChangeStyle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WheelAttrs attrs = datelayout.getWheelViewYear().getAttrsBuilder()
                        .setTextColorCenter(getResources().getColor(R.color.colorPrimary))
                        .setItemSize(50)
                        .setItemCount(2)
                        .setItemDegreeTotal(120f)
                        .setDividerSize(50)
                        .setTextSize(20)
                        .setAlphaGradient(false)
                        .setTextBlod(true)
                        .setCenterTextBlod(true)
                        .setTextSizeGradient(true)
                        .setMinGradientTextSize(14)
                        .build();
                datelayout.setAllWheelViewAttrs(attrs);
                datelayout.setLabelTextColor(getResources().getColor(R.color.colorPrimary));
            }
        });

        findViewById(R.id.btnYearMonthDay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                YearMonthDayPicker picker = new YearMonthDayPicker(DatePickActivity.this);
                picker.setOnDatePickedListener(new OnDatePickedListener() {
                    @Override
                    public void onDatePicked(int year, int month, int day) {
                        Toast.makeText(DatePickActivity.this,
                                year + "-" + month + "-" + day, Toast.LENGTH_SHORT).show();
                    }
                });
                picker.show();
            }
        });
        findViewById(R.id.btnYearMonth).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                YearMonthDayPicker picker = new YearMonthDayPicker(DatePickActivity.this);
                picker.getWheelLayout().setDateMode(DateMode.YEAR_MONTH);
                picker.setOnDatePickedListener(new OnDatePickedListener() {
                    @Override
                    public void onDatePicked(int year, int month, int day) {
                        Toast.makeText(DatePickActivity.this,
                                year + "-" + month, Toast.LENGTH_SHORT).show();
                    }
                });
                picker.show();
            }
        });
        findViewById(R.id.btnMonthDay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                YearMonthDayPicker picker = new YearMonthDayPicker(DatePickActivity.this);
                picker.getWheelLayout().setDateMode(DateMode.MONTH_DAY);
                picker.setOnDatePickedListener(new OnDatePickedListener() {
                    @Override
                    public void onDatePicked(int year, int month, int day) {
                        Toast.makeText(DatePickActivity.this,
                                month + "-" + day, Toast.LENGTH_SHORT).show();
                    }
                });
                picker.show();
            }
        });
        findViewById(R.id.btnCustomYearMonthDay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                YearMonthDayPicker picker = new YearMonthDayPicker(DatePickActivity.this);
                WheelAttrs attrs = picker.getWheelLayout().getWheelViewYear().getAttrsBuilder()
                        .setTextColorCenter(getResources().getColor(R.color.colorPrimary))
                        .setItemSize(50)
                        .setIsWheel(false)
                        .setItemCount(2)
                        .setDividerSize(50).build();

                picker.getWheelLayout().setAllWheelViewAttrs(attrs);

                picker.getOkView().setTextColor(getResources().getColor(R.color.colorPrimary));
                picker.getWheelLayout().setLabelTextColor(getResources().getColor(R.color.colorPrimary));

                picker.setOnDatePickedListener(new OnDatePickedListener() {
                    @Override
                    public void onDatePicked(int year, int month, int day) {
                        Toast.makeText(DatePickActivity.this,
                                year + "-" + month + "-" + day, Toast.LENGTH_SHORT).show();
                    }
                });
                picker.show();
            }
        });


        findViewById(R.id.btnBirthday).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BirthdayPicker picker = new BirthdayPicker(DatePickActivity.this);

                picker.setOnDatePickedListener(new OnDatePickedListener() {
                    @Override
                    public void onDatePicked(int year, int month, int day) {
                        Toast.makeText(DatePickActivity.this,
                                year + "-" + month + "-" + day, Toast.LENGTH_SHORT).show();
                    }
                });
                picker.show();
            }
        });

        findViewById(R.id.btnYearMonthDayCustom).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogConfig config = new DialogConfig();
                config.setDialogStyle(DialogStyle.center);
                config.setDialogBackground(new DialogBackground()
                        .setDialogWidthP(0.7F)
                        .setDialogCornerRound(CornerRound.All)
                        .setCornerRadius(15)
                        .setContentBackgroundColor(Color.WHITE));
                LovelyBirthdayPicker picker = new LovelyBirthdayPicker(DatePickActivity.this, config);
                picker.setOnDatePickedListener(new OnDatePickedListener() {
                    @Override
                    public void onDatePicked(int year, int month, int day) {
                        Toast.makeText(DatePickActivity.this,
                                year + "-" + month + "-" + day, Toast.LENGTH_SHORT).show();
                    }
                });
                picker.show();

            }
        });

        findViewById(R.id.btnHourMinuteSecond).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HourMinuteSecondPicker picker = new HourMinuteSecondPicker(DatePickActivity.this);
                picker.setOnTimePickedListener(new OnTimePickedListener() {
                    @Override
                    public void onTimePicked(int hour, int minute, int second) {
                        Toast.makeText(DatePickActivity.this,
                                hour + "-" + minute + "-" + second, Toast.LENGTH_SHORT).show();
                    }
                });
                picker.show();
            }
        });
        findViewById(R.id.btnhourMinute).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HourMinuteSecondPicker picker = new HourMinuteSecondPicker(DatePickActivity.this);
                picker.getWheelLayout().setTimeMode(TimeMode.HOUR_24_NO_SECOND);
                picker.setOnTimePickedListener(new OnTimePickedListener() {
                    @Override
                    public void onTimePicked(int hour, int minute, int second) {
                        Toast.makeText(DatePickActivity.this,
                                hour + "-" + minute, Toast.LENGTH_SHORT).show();
                    }
                });
                picker.show();
            }
        });
        findViewById(R.id.btnMinuteSecond).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HourMinuteSecondPicker picker = new HourMinuteSecondPicker(DatePickActivity.this);
                picker.getWheelLayout().setTimeMode(TimeMode.HOUR_24_NO_HOUR);
                picker.setOnTimePickedListener(new OnTimePickedListener() {
                    @Override
                    public void onTimePicked(int hour, int minute, int second) {
                        Toast.makeText(DatePickActivity.this,
                                minute + "-" + second, Toast.LENGTH_SHORT).show();
                    }
                });
                picker.show();
            }
        });
        findViewById(R.id.btnCustomHourMinuteSecond).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HourMinuteSecondPicker picker = new HourMinuteSecondPicker(DatePickActivity.this);

                WheelAttrs attrs = picker.getWheelLayout().getWheelViewHour().getAttrsBuilder()
                        .setTextColorCenter(getResources().getColor(R.color.colorPrimary))
                        .setItemSize(50)
                        .setIsWheel(false)
                        .setItemCount(2)
                        .setDividerSize(50).build();
                picker.getWheelLayout().setAllWheelViewAttrs(attrs);

                picker.getOkView().setTextColor(getResources().getColor(R.color.colorPrimary));
                picker.getWheelLayout().setLabelTextColor(getResources().getColor(R.color.colorPrimary));


                picker.setOnTimePickedListener(new OnTimePickedListener() {
                    @Override
                    public void onTimePicked(int hour, int minute, int second) {
                        Toast.makeText(DatePickActivity.this,
                                hour + "-" + minute + "-" + second, Toast.LENGTH_SHORT).show();
                    }
                });
                picker.show();
            }
        });

        findViewById(R.id.btnDateTime).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateTimePicker picker = new DateTimePicker(DatePickActivity.this);
                picker.setOnDateTimePickedListener(new OnDateTimePickedListener() {
                    @Override
                    public void onDateTimePicked(int year, int month, int day, int hour, int minute, int second) {
                        Toast.makeText(DatePickActivity.this,
                                year + "-" + month + "-" + day + "    " + hour + "-" + minute + "-" + second, Toast.LENGTH_SHORT).show();
                    }
                });
                picker.show();
            }
        });
        findViewById(R.id.btnDateTimeBirthday).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BirthdayWithTimePicker picker = new BirthdayWithTimePicker(DatePickActivity.this);
                picker.setOnDateTimePickedListener(new OnDateTimePickedListener() {
                    @Override
                    public void onDateTimePicked(int year, int month, int day, int hour, int minute, int second) {
                        Toast.makeText(DatePickActivity.this,
                                year + "-" + month + "-" + day + "    " + hour + "-" + minute + "-" + second, Toast.LENGTH_SHORT).show();
                    }
                });
                picker.show();
            }
        });

    }
}