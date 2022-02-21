package com.Jfpicker.sample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.Jfpicker.sample.data.AntFortuneLikeProvider;
import com.Jfpicker.wheelpicker.picker_option.LinkagePicker;
import com.Jfpicker.wheelpicker.picker_option.OptionPicker;
import com.Jfpicker.wheelpicker.picker_option.OptionRecyclerViewPicker;
import com.Jfpicker.wheelpicker.picker_option.entity.OptionEntity;
import com.Jfpicker.wheelpicker.picker_option.listener.OnLinkagePickedListener;
import com.Jfpicker.wheelpicker.picker_option.listener.OnOptionPickedListener;
import com.Jfpicker.wheelpicker.picker_sure.SurePicker;
import com.Jfpicker.wheelpicker.utils.DensityUtils;
import com.Jfpicker.wheelpicker.wheel_dialog.CornerRound;
import com.Jfpicker.wheelpicker.wheel_dialog.DialogConfig;
import com.Jfpicker.wheelpicker.wheel_dialog.DialogStyle;

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

        findViewById(R.id.btnList).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OptionRecyclerViewPicker picker = new OptionRecyclerViewPicker(OptionPickActivity.this, DialogConfig.getDefaultConfig());
                List<OptionEntity> list = new ArrayList<>();
                for (int i = 0; i < 10; i++) {
                    list.add(new OptionEntity("" + i, "第" + i + "条数据"));
                }
                picker.setOptionsData(list);
                picker.setOnOptionPickedListener(new OnOptionPickedListener() {
                    @Override
                    public void onOption(String id, String name) {
                        Toast.makeText(OptionPickActivity.this, "选择了id=" + id + "name=" + name, Toast.LENGTH_SHORT).show();
                    }
                });
                picker.show();
            }
        });
        findViewById(R.id.btnListManyData).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OptionRecyclerViewPicker picker = new OptionRecyclerViewPicker(OptionPickActivity.this, DialogConfig.getDefaultConfig());
                picker.setRecyclerViewFixedHeight(300);
                List<OptionEntity> list = new ArrayList<>();
                for (int i = 0; i < 100; i++) {
                    list.add(new OptionEntity("" + i, "第" + i + "条数据"));
                }
                picker.setOptionsData(list);
                picker.setOnOptionPickedListener(new OnOptionPickedListener() {
                    @Override
                    public void onOption(String id, String name) {
                        Toast.makeText(OptionPickActivity.this, "选择了id=" + id + "name=" + name, Toast.LENGTH_SHORT).show();
                    }
                });
                picker.show();
            }
        });
        findViewById(R.id.btnOptionListMyStyle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OptionRecyclerViewPicker picker = new OptionRecyclerViewPicker(OptionPickActivity.this);
                List<OptionEntity> list = new ArrayList<>();
                for (int i = 0; i < 10; i++) {
                    list.add(new OptionEntity("" + i, "第" + i + "条数据"));
                }
                picker.setOptionsData(R.layout.item_my_option_recyclerview, list);
                picker.setOnOptionPickedListener(new OnOptionPickedListener() {
                    @Override
                    public void onOption(String id, String name) {
                        Toast.makeText(OptionPickActivity.this, "选择了id=" + id + "name=" + name, Toast.LENGTH_SHORT).show();
                    }
                });
                picker.show();
            }
        });

        findViewById(R.id.btnSure).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogConfig config = DialogConfig.getDefaultConfig();
                config.getDialogBackground().setCornerRadius(15).setDialogCornerRound(CornerRound.All);
                config.setDialogStyle(DialogStyle.center);
                SurePicker picker = new SurePicker(OptionPickActivity.this, config);
                picker.setContent("这是内容");
                picker.show();
            }
        });

    }
}