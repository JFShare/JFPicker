package com.Jfpicker.sample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.Jfpicker.sample.data.AntFortuneLikeProvider;
import com.Jfpicker.wheelpicker.picker_option.CheckBoxPicker;
import com.Jfpicker.wheelpicker.picker_option.LinkagePicker;
import com.Jfpicker.wheelpicker.picker_option.OptionPicker;
import com.Jfpicker.wheelpicker.picker_option.OptionRecyclerViewPicker;
import com.Jfpicker.wheelpicker.picker_option.RadioPicker;
import com.Jfpicker.wheelpicker.picker_option.entity.OptionEntity;
import com.Jfpicker.wheelpicker.picker_option.listener.OnOptionMultiPickedListener;
import com.Jfpicker.wheelpicker.picker_tips.InputPicker;
import com.Jfpicker.wheelpicker.picker_tips.TipsPicker;
import com.Jfpicker.wheelpicker.dialog.config.DialogConfig;
import com.Jfpicker.wheelpicker.picker_tips.listener.OnInputListener;

import java.util.ArrayList;
import java.util.List;

public class OptionPickActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option_pick);

        findViewById(R.id.btnOption).setOnClickListener(v -> {
            List<OptionEntity> list = new ArrayList<>();
            for (int i = 0; i < 20; i++) {
                if (i % 2 == 1) {
                    list.add(new OptionEntity(i + "", "选项" + i + "是一个非常非常非常非常非常非常非常非常长的文字"));
                } else {
                    list.add(new OptionEntity(i + "", "选项" + i));
                }
            }
            OptionPicker picker = new OptionPicker(OptionPickActivity.this, "选项选择", list);
            picker.setOnOptionPickedListener((id, name) ->
                    Toast.makeText(OptionPickActivity.this, "选择了id=" + id + "name=" + name, Toast.LENGTH_SHORT).show());
            picker.show();
        });
        findViewById(R.id.btnTwoLinkageOption).setOnClickListener(v -> {
            LinkagePicker picker = new LinkagePicker(OptionPickActivity.this);
            picker.setData(new AntFortuneLikeProvider());
            picker.setOnLinkagePickedListener((first, second, third) -> {
                if (first != null && second != null) {
                    Toast.makeText(OptionPickActivity.this,
                            "选择了" + first.toString() + " - " + second.toString(),
                            Toast.LENGTH_SHORT).show();
                }
            });
            picker.show();
        });

        findViewById(R.id.btnList).setOnClickListener(v -> {
            OptionRecyclerViewPicker picker = new OptionRecyclerViewPicker(OptionPickActivity.this, DialogConfig.getDefault());
            List<OptionEntity> list = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                list.add(new OptionEntity("" + i, "第" + i + "条数据"));
            }
            picker.setOptionsData(list);
            picker.setOnOptionPickedListener((id, name) ->
                    Toast.makeText(OptionPickActivity.this, "选择了id=" + id + "name=" + name, Toast.LENGTH_SHORT).show());
            picker.show();
        });
        findViewById(R.id.btnListManyData).setOnClickListener(v -> {
            OptionRecyclerViewPicker picker = new OptionRecyclerViewPicker(OptionPickActivity.this, DialogConfig.getDefault());
            picker.setRecyclerViewFixedHeight(300);
            List<OptionEntity> list = new ArrayList<>();
            for (int i = 0; i < 100; i++) {
                list.add(new OptionEntity("" + i, "第" + i + "条数据"));
            }
            picker.setOptionsData(list);
            picker.setOnOptionPickedListener((id, name) ->
                    Toast.makeText(OptionPickActivity.this, "选择了id=" + id + "name=" + name, Toast.LENGTH_SHORT).show());
            picker.show();
        });
        findViewById(R.id.btnOptionListMyStyle).setOnClickListener(v -> {
            OptionRecyclerViewPicker picker = new OptionRecyclerViewPicker(OptionPickActivity.this);
            List<OptionEntity> list = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                list.add(new OptionEntity("" + i, "第" + i + "条数据"));
            }
            picker.setOptionsData(R.layout.item_my_option_recyclerview, list);
            picker.setOnOptionPickedListener((id, name) ->
                    Toast.makeText(OptionPickActivity.this,
                            "选择了id=" + id + "name=" + name, Toast.LENGTH_SHORT).show());
            picker.show();
        });

        findViewById(R.id.btnRadio).setOnClickListener(v -> {
            RadioPicker picker = new RadioPicker(OptionPickActivity.this);
            List<OptionEntity> list = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                list.add(new OptionEntity("" + i, "第" + i + "条数据"));
            }
            picker.setOptionsData(list);
            picker.setOnOptionPickedListener((id, name) ->
                    Toast.makeText(OptionPickActivity.this,
                            "选择了id=" + id + "name=" + name, Toast.LENGTH_SHORT).show());
            picker.show();
        });
        findViewById(R.id.btnRadioMyStyle).setOnClickListener(v -> {
                    RadioPicker picker = new RadioPicker(OptionPickActivity.this);
                    List<OptionEntity> list = new ArrayList<>();
                    for (int i = 0; i < 10; i++) {
                        list.add(new OptionEntity("" + i, "第" + i + "条数据"));
                    }
                    picker.setOptionsData(R.layout.item_my_radio, list);
                    picker.setOnOptionPickedListener((id, name) ->
                            Toast.makeText(OptionPickActivity.this,
                                    "选择了id=" + id + "name=" + name, Toast.LENGTH_SHORT).show());
                    picker.show();
                }
        );
        findViewById(R.id.btnCheckBox).setOnClickListener(v -> {
            CheckBoxPicker picker = new CheckBoxPicker(OptionPickActivity.this);
            List<OptionEntity> list = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                list.add(new OptionEntity("" + i, "第" + i + "条数据"));
            }
            picker.setOptionsData(list);
            picker.setOnOptionMultiPickedListener(new OnOptionMultiPickedListener() {
                @Override
                public void onOption(List<OptionEntity> checkedList) {
                    StringBuilder str = new StringBuilder();
                    for (int i = 0; i < checkedList.size(); i++) {
                        str.append("id=").append(checkedList.get(i).getId()).append("name=")
                                .append(checkedList.get(i).getName()).append("\n");
                    }
                    Toast.makeText(OptionPickActivity.this,
                            str.toString(), Toast.LENGTH_SHORT).show();
                }
            });
            picker.show();
        });
        findViewById(R.id.btnCheckBoxMyStyle).setOnClickListener(v -> {
                    CheckBoxPicker picker = new CheckBoxPicker(OptionPickActivity.this);
                    List<OptionEntity> list = new ArrayList<>();
                    for (int i = 0; i < 10; i++) {
                        list.add(new OptionEntity("" + i, "第" + i + "条数据"));
                    }
                    picker.setOptionsData(R.layout.item_my_checkbox, list);
                    picker.setOnOptionMultiPickedListener(new OnOptionMultiPickedListener() {
                        @Override
                        public void onOption(List<OptionEntity> checkedList) {
                            StringBuilder str = new StringBuilder();
                            for (int i = 0; i < checkedList.size(); i++) {
                                str.append("id=").append(checkedList.get(i).getId()).append("name=")
                                        .append(checkedList.get(i).getName()).append("\n");
                            }
                            Toast.makeText(OptionPickActivity.this,
                                    str.toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
                    picker.show();
                }
        );
        findViewById(R.id.btnSure).setOnClickListener(v -> {
            TipsPicker picker = new TipsPicker(OptionPickActivity.this);
            picker.setContent("这是内容这是内容这是内容这是内容这是内容这是内容这是内容这是内容这是内容这是内容这是内容这是内容这是内容这是内容这是内容这是内容这是内容这是内容这是内容这是内容");
            picker.show();
        });
        findViewById(R.id.btnInput).setOnClickListener(v -> {
            InputPicker picker = new InputPicker(OptionPickActivity.this);
            picker.setOnInputListener(text ->
                    Toast.makeText(OptionPickActivity.this,
                            text, Toast.LENGTH_SHORT).show());
            picker.show();
        });
    }
}