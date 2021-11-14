package com.Jfpicker.sample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class PickerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picker);

        findViewById(R.id.btnDate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PickerActivity.this, DatePickActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.btnOption).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PickerActivity.this, OptionPickActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.btnAdress).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PickerActivity.this, AdressPickActivity.class);
                startActivity(intent);
            }
        });
    }
}