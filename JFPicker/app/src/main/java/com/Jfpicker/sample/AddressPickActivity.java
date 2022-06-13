package com.Jfpicker.sample;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.Jfpicker.wheelpicker.picker_adress.AddressPicker;
import com.Jfpicker.wheelpicker.picker_adress.AddressViewPagerPicker;
import com.Jfpicker.wheelpicker.picker_adress.annotation.AddressMode;
import com.Jfpicker.wheelpicker.picker_adress.entity.AddressItemEntity;
import com.Jfpicker.wheelpicker.picker_adress.utility.AddressJsonParser;
import com.Jfpicker.wheelpicker.picker_net.NetRequestPicker;
import com.Jfpicker.wheelpicker.picker_option.entity.OptionEntity;

import java.util.ArrayList;
import java.util.List;


public class AddressPickActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adress_pick);
        findViewById(R.id.btnAdressPicker).setOnClickListener(v -> {
            AddressPicker picker = new AddressPicker(AddressPickActivity.this);
            picker.setOnAddressPickedListener((province, city, county)
                    -> toastAddress(province, city, county));
            picker.show();
        });

        findViewById(R.id.btnDefault).setOnClickListener(v -> {
            AddressPicker picker = new AddressPicker(AddressPickActivity.this);
            picker.setDefaultValue("山东省", "临沂市", "河东区");
            picker.setOnAddressPickedListener((province, city, county) ->
                    toastAddress(province, city, county));
            picker.show();
        });

        findViewById(R.id.btnAdressPickerProvinceCity).setOnClickListener(v -> {
            AddressPicker picker = new AddressPicker(AddressPickActivity.this);
            picker.setAddressMode(AddressMode.PROVINCE_CITY);
            picker.setOnAddressPickedListener((province, city, county) ->
                    toastAddress(province, city, county));
            picker.show();
        });
        findViewById(R.id.btnAdressPickerCityCounty).setOnClickListener(v -> {
            AddressPicker picker = new AddressPicker(AddressPickActivity.this);
            picker.setAddressMode(AddressMode.CITY_COUNTY);
            picker.setDefaultValue("山东省", "临沂市", "河东区");
            picker.setOnAddressPickedListener((province, city, county) ->
                    toastAddress(province, city, county));
            picker.show();
        });

        findViewById(R.id.btnAdressPickerCustom).setOnClickListener(v -> {
            AddressPicker picker = new AddressPicker(AddressPickActivity.this);
            picker.setAddressMode("china_adress_cgat.json", AddressMode.PROVINCE_CITY_COUNTY,
                    new AddressJsonParser.Builder()
                            .provinceCodeField("code")
                            .provinceNameField("name")
                            .provinceChildField("city")
                            .cityCodeField("code")
                            .cityNameField("name")
                            .cityChildField("area")
                            .countyCodeField("code")
                            .countyNameField("name")
                            .build());
            picker.setDefaultValue("山东省", "临沂市", "河东区");
            picker.setOnAddressPickedListener((province, city, county) ->
                    toastAddress(province, city, county));

            picker.show();
        });
        findViewById(R.id.btnViewpagerPicker).setOnClickListener(v -> {
            AddressViewPagerPicker picker = new AddressViewPagerPicker(AddressPickActivity.this);
            picker.setOnAddressPickedListener((province, city, county) ->
                    toastAddress(province, city, county));
            picker.show();
        });
        findViewById(R.id.btnViewpagerPickerDefault).setOnClickListener(v -> {
            AddressViewPagerPicker picker = new AddressViewPagerPicker(AddressPickActivity.this);
            picker.setDefaultValue("山东省", "临沂市", "河东区");
            picker.setOnAddressPickedListener((province, city, county) ->
                    toastAddress(province, city, county));
            picker.show();
        });

        findViewById(R.id.btnViewpagerPickerMyStyle).setOnClickListener(v -> {
            AddressViewPagerPicker picker = new AddressViewPagerPicker(AddressPickActivity.this);
            picker.getTabLayout().setSelectedTabIndicatorColor(getColor(R.color.teal_200));
            picker.getTabLayout().setTabTextColors(Color.parseColor("#333333"),
                    getColor(R.color.teal_200));
            picker.getConfirmTextView().setTextColor(getColor(R.color.teal_200));
            picker.setOnRecyclerviewStyleListener((itemView, itemTextView, entity, isSelect) -> {
                if (isSelect) {
                    itemTextView.setTextColor(getColor(R.color.teal_200));
                    itemTextView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                } else {
                    itemTextView.setTextColor(Color.parseColor("#999999"));
                    itemTextView.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                }
            });
            ArrayList<String> tableNameList = new ArrayList<>();
            tableNameList.add("请选择省");
            tableNameList.add("请选择市");
            tableNameList.add("请选择区");
            picker.setTabNameList(tableNameList);
            picker.setOnAddressPickedListener((province, city, county) ->
                    toastAddress(province, city, county));
            picker.show();
        });
        findViewById(R.id.btnNetRequestPicker).setOnClickListener(v -> {
            final int[] errorNum = {0};
            NetRequestPicker picker = new NetRequestPicker(AddressPickActivity.this);
            List<OptionEntity> firstData = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                firstData.add(new OptionEntity("0" + i, "第0组第" + i));
            }
            picker.setFirstData(firstData);
            picker.setMaxIndex(5);
            picker.setOnNetRequestStartListener((parentPosition, entity) -> {
                //模拟网络请求
                new Handler().postDelayed(() -> {
                    if (parentPosition == 2 && errorNum[0] == 0) {
                        picker.setNextData();
                        errorNum[0]++;
                    } else {
                        List<OptionEntity> nextData = new ArrayList<>();
                        for (int i = 0; i < 10; i++) {
                            nextData.add(new OptionEntity((parentPosition + 1) + "-" + i, "第" + (parentPosition + 1) + "组第" + i));
                        }
                        picker.setNextData(nextData);
                    }
                }, 500);
            });
            picker.setOnNetRequestPickedListener((firstData1, secondData, thirdDate, fourthData, fifthData) ->
                    toastOptions(firstData1, secondData, thirdDate, fourthData, fifthData));

            picker.show();
        });

        findViewById(R.id.btnNetRequestPickerMyStyle).setOnClickListener(v -> {
            final int[] errorNum = {0};
            NetRequestPicker picker = new NetRequestPicker(AddressPickActivity.this);
            picker.getTabLayout().setSelectedTabIndicatorColor(getColor(R.color.teal_200));
            picker.getTabLayout().setTabTextColors(Color.parseColor("#333333"), getColor(R.color.teal_200));
            picker.getConfirmTextView().setTextColor(getColor(R.color.teal_200));
            picker.setOnRecyclerviewStyleListener((itemView, itemTextView, entity, isSelect) -> {
                if (isSelect) {
                    itemTextView.setTextColor(getColor(R.color.teal_200));
                    itemTextView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                } else {
                    itemTextView.setTextColor(Color.parseColor("#999999"));
                    itemTextView.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                }
            });

            List<OptionEntity> firstData = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                firstData.add(new OptionEntity("0" + i, "第0组第" + i));
            }
            picker.setFirstData(firstData);
            picker.setMaxIndex(4);
            ArrayList<String> tableNameList = new ArrayList<>();
            tableNameList.add("请选择第一级");
            tableNameList.add("请选择第二级");
            tableNameList.add("请选择第三级");
            tableNameList.add("请选择第四级");
            picker.setTabNameList(tableNameList);

            picker.setOnNetRequestStartListener((parentPosition, entity) -> {
                //模拟网络请求
                new Handler().postDelayed(() -> {
                    if (parentPosition == 2 && errorNum[0] == 0) {
                        picker.setNextData();
                        errorNum[0]++;
                    } else {
                        List<OptionEntity> nextData = new ArrayList<>();
                        for (int i = 0; i < 10; i++) {
                            nextData.add(new OptionEntity((parentPosition + 1) + "-" + i, "第" + (parentPosition + 1) + "组第" + i));
                        }
                        picker.setNextData(nextData);
                    }
                }, 500);
            });
            picker.setOnNetRequestPickedListener((firstData12, secondData, thirdDate, fourthData, fifthData) ->
                    toastOptions(firstData12, secondData, thirdDate, fourthData, fifthData));

            picker.show();
        });

        findViewById(R.id.btnNetRequestPickerMyState).setOnClickListener(v -> {
            final int[] errorNum = {0};
            NetRequestPicker picker = new NetRequestPicker(AddressPickActivity.this);
            picker.getTabLayout().setSelectedTabIndicatorColor(getColor(R.color.teal_200));
            picker.getTabLayout().setTabTextColors(Color.parseColor("#333333"), getColor(R.color.teal_200));
            picker.getConfirmTextView().setTextColor(getColor(R.color.teal_200));
            picker.setOnRecyclerviewStyleListener((itemView, itemTextView, entity, isSelect) -> {
                if (isSelect) {
                    itemTextView.setTextColor(getColor(R.color.teal_200));
                    itemTextView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                } else {
                    itemTextView.setTextColor(Color.parseColor("#999999"));
                    itemTextView.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                }
            });

            List<OptionEntity> firstData = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                firstData.add(new OptionEntity("0" + i, "第0组第" + i));
            }
            picker.setFirstData(firstData);
            picker.setMaxIndex(4);
            ArrayList<String> tableNameList = new ArrayList<>();
            tableNameList.add("请选择第一级");
            tableNameList.add("请选择第二级");
            tableNameList.add("请选择第三级");
            tableNameList.add("请选择第四级");
            picker.setTabNameList(tableNameList);

            picker.setOnNetRequestStartListener((parentPosition, entity) -> {
                //模拟网络请求
                new Handler().postDelayed(() -> {
                    if (parentPosition == 2 && errorNum[0] == 0) {
                        picker.setNextData();
                        errorNum[0]++;
                    } else {
                        List<OptionEntity> nextData = new ArrayList<>();
                        for (int i = 0; i < 10; i++) {
                            nextData.add(new OptionEntity((parentPosition + 1) + "-" + i, "第" + (parentPosition + 1) + "组第" + i));
                        }
                        picker.setNextData(nextData);
                    }
                }, 500);
            });
            picker.setOnNetRequestPickedListener((firstData12, secondData, thirdDate, fourthData, fifthData) ->
                    toastOptions(firstData12, secondData, thirdDate, fourthData, fifthData));
            picker.setStateLayout(R.layout.layout_my_loading, R.layout.layout_my_error,
                    R.id.btnReLoad);
            picker.show();
        });
    }

    public void toastAddress(AddressItemEntity province, AddressItemEntity city, AddressItemEntity county) {
        String provinceText = "";
        String cityText = "";
        String countyText = "";
        if (province != null) {
            provinceText = province.getName();
        }
        if (city != null) {
            cityText = city.getName();
        }
        if (county != null) {
            countyText = county.getName();
        }
        Toast.makeText(AddressPickActivity.this,
                "选择了" + provinceText + "-" + cityText + "-" + countyText, Toast.LENGTH_SHORT).show();
    }

    public void toastOptions(OptionEntity firstData, OptionEntity secondData, OptionEntity thirdDate,
                             OptionEntity fourthData, OptionEntity fifthData) {
        String fist = firstData != null ? firstData.getName() : "";
        String second = secondData != null ? secondData.getName() : "";
        String third = thirdDate != null ? thirdDate.getName() : "";
        String fourth = fourthData != null ? fourthData.getName() : "";
        String fifth = fifthData != null ? fifthData.getName() : "";

        Toast.makeText(AddressPickActivity.this,
                "选择了" + fist + "-" + second + "-" + third + "-" + fourth + "-" + fifth, Toast.LENGTH_SHORT).show();
    }
}