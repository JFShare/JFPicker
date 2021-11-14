package com.Jfpicker.sample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.Jfpicker.sample.data.AntFortuneLikeProvider;
import com.Jfpicker.wheelpicker.picker_option.LinkagePicker;
import com.Jfpicker.wheelpicker.picker_option.OptionPicker;
import com.Jfpicker.wheelpicker.picker_option.entity.OptionEntity;
import com.Jfpicker.wheelpicker.picker_option.listener.OnLinkagePickedListener;
import com.Jfpicker.wheelpicker.picker_option.listener.OnOptionPickedListener;

import java.util.ArrayList;
import java.util.List;

public class OptionPickActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option_pick);

        findViewById(R.id.btnOption).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<OptionEntity> list = new ArrayList<>();
                for (int i = 0; i < 20; i++) {
                    if (i % 2 == 1) {
                        list.add(new OptionEntity(i + "", "选项" + i + "是一个非常非常非常非常非常非常非常非常长的文字"));
                    } else {
                        list.add(new OptionEntity(i + "", "选项" + i));
                    }

                }
                OptionPicker picker = new OptionPicker(OptionPickActivity.this, "选项选择", list);
                picker.setOnOptionPickedListener(new OnOptionPickedListener() {
                    @Override
                    public void onOption(String id, String name) {
                        Toast.makeText(OptionPickActivity.this, "选择了id=" + id + "name=" + name, Toast.LENGTH_SHORT).show();
                    }
                });
                picker.show();
            }
        });
        findViewById(R.id.btnTwoLinkageOption).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinkagePicker picker = new LinkagePicker(OptionPickActivity.this);
                picker.setData(new AntFortuneLikeProvider());
                picker.setOnLinkagePickedListener(new OnLinkagePickedListener() {
                    @Override
                    public void onLinkagePicked(Object first, Object second, Object third) {
                        if (first != null && second != null) {
                            Toast.makeText(OptionPickActivity.this,
                                    "选择了" + first.toString() + " - " + second.toString(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                picker.show();
            }
        });
    }
}