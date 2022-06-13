package com.Jfpicker.sample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.Jfpicker.wheelpicker.picker_calendar.CalendarPicker;
import com.Jfpicker.wheelpicker.picker_calendar.listener.OnCalendarRangePickedListener;
import com.Jfpicker.wheelpicker.dialog.annotation.CornerRound;
import com.Jfpicker.wheelpicker.dialog.config.DialogConfig;
import com.haibin.calendarview.Calendar;

import java.util.List;

public class CalendarPickActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_calendar_pick);

        findViewById(R.id.btnCalendarPickerSingle).setOnClickListener(v -> {
            DialogConfig config = new DialogConfig();
            config.setDialogCornerRound(CornerRound.Top)
                    .setCornerRadius(15);
            CalendarPicker picker = new CalendarPicker(CalendarPickActivity.this, config);
            picker.setOnCalendarPickedListener((year, month, day) ->
                    Toast.makeText(CalendarPickActivity.this, "选择了" + year + "-" + month + "-" + day, Toast.LENGTH_SHORT).show());
            picker.show();
        });
        findViewById(R.id.btnCalendarPickerMulti).setOnClickListener(v -> {
            DialogConfig config =new  DialogConfig();
            config.setDialogCornerRound(CornerRound.Top)
                    .setCornerRadius(15);
            CalendarPicker picker = new CalendarPicker(CalendarPickActivity.this, config);
            picker.setSelectMultiMode(5);
            picker.setOnCalendarMultiPickedListener(list -> {
                StringBuilder str = new StringBuilder("选择了\n");
                for (int i = 0; i < list.size(); i++) {
                    str.append(list.get(i).getYear()).append("-").append(list.get(i).getMonth()).append("-").append(list.get(i).getDay()).append("\n");
                }
                Toast.makeText(CalendarPickActivity.this, str.toString(), Toast.LENGTH_SHORT).show();
            });
            picker.show();
        });

        findViewById(R.id.btnCalendarPickerRange).setOnClickListener(v -> {
            DialogConfig config = new DialogConfig();
            config.setDialogCornerRound(CornerRound.Top)
                    .setCornerRadius(15);
            CalendarPicker picker = new CalendarPicker(CalendarPickActivity.this, config);
            picker.setSelectRangeMode();
            picker.setOnCalendarRangePickedListener(new OnCalendarRangePickedListener() {
                @Override
                public void onCalendarRangePicked(List<Calendar> list) {
                    StringBuilder str = new StringBuilder("选择了\n");
                    for (int i = 0; i < list.size(); i++) {
                        str.append(list.get(i).getYear()).append("-").append(list.get(i).getMonth()).append("-").append(list.get(i).getDay()).append("\n");
                    }
                    Toast.makeText(CalendarPickActivity.this, str.toString(), Toast.LENGTH_SHORT).show();
                }
            });
            picker.show();
        });

    }
}