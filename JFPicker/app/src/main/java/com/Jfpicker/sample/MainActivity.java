package com.Jfpicker.sample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.Jfpicker.wheelpicker.dialog.annotation.CornerRound;
import com.Jfpicker.wheelpicker.dialog.annotation.DialogStyle;
import com.Jfpicker.wheelpicker.dialog.config.DialogConfig;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btnWheelView).setOnClickListener(v -> {
            WheelViewActivity.start(this);
        });
        findViewById(R.id.btnDate).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, DatePickActivity.class);
            startActivity(intent);
        });
        findViewById(R.id.btnCalendar).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CalendarPickActivity.class);
            startActivity(intent);
        });
        findViewById(R.id.btnOption).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, OptionPickActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.btnAdress).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddressPickActivity.class);
            startActivity(intent);
        });
        RadioGroup rgDialogStyle = findViewById(R.id.rgDialogStyle);
        rgDialogStyle.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (R.id.rbDialogStyleDefault == checkedId) {
                    DialogConfig.defaultDialogConfig = new DialogConfig();
                    Toast.makeText(MainActivity.this, "样式已修改！", Toast.LENGTH_SHORT).show();
                }
                if (R.id.rbDialogStyleCustom == checkedId) {
                    DialogConfig.getDefault().setDialogStyle(DialogStyle.center);
                    DialogConfig.getDefault().setSplitLineColor(getColor(R.color.teal_700))
                            .setConfirmTextColor(getColor(R.color.teal_700))
                            .setSplitLineColor(getColor(R.color.teal_700))
                            .setCancelTextColor(Color.RED);
                    DialogConfig.getDefault().setDialogCornerRound(CornerRound.All)
                            .setCornerRadius(10);
                    Toast.makeText(MainActivity.this, "样式已修改！", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}