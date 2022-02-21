package com.Jfpicker.sample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.Jfpicker.wheelpicker.wheel_dialog.CornerRound;
import com.Jfpicker.wheelpicker.wheel_dialog.DefaultDialogConfig;
import com.Jfpicker.wheelpicker.wheel_dialog.DialogStyle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btnDate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DatePickActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.btnCalendar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CalendarPickActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.btnOption).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, OptionPickActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.btnAdress).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AdressPickActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.btnUpdateAllStyle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DefaultDialogConfig.setDialogStyle(DialogStyle.center);
                DefaultDialogConfig.getDialogColor().topLineColor(getResources().getColor(R.color.teal_700))
                        .okTextColor(getResources().getColor(R.color.teal_700))
                        .topLineColor(getResources().getColor(R.color.teal_700))
                        .cancelTextColor(Color.RED);
                DefaultDialogConfig.getDialogBackground().setDialogCornerRound(CornerRound.All)
                        .setCornerRadius(10);
                Toast.makeText(MainActivity.this, "样式已修改！", Toast.LENGTH_SHORT).show();
            }
        });
    }
}