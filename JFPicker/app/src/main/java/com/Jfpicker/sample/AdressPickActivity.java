package com.Jfpicker.sample;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.Jfpicker.wheelpicker.picker_adress.AddressPicker;
import com.Jfpicker.wheelpicker.picker_adress.AddressViewPagerPicker;
import com.Jfpicker.wheelpicker.picker_adress.annotation.AddressMode;
import com.Jfpicker.wheelpicker.picker_adress.contract.OnAddressPickedListener;
import com.Jfpicker.wheelpicker.picker_adress.contract.OnNetRequestPickedListener;
import com.Jfpicker.wheelpicker.picker_adress.entity.AddressItemEntity;
import com.Jfpicker.wheelpicker.picker_adress.utility.AddressJsonParser;
import com.Jfpicker.wheelpicker.picker_base.OnRecyclerviewStyleListener;
import com.Jfpicker.wheelpicker.picker_netrequest.NetRequestLayout;
import com.Jfpicker.wheelpicker.picker_netrequest.NetRequestPicker;
import com.Jfpicker.wheelpicker.picker_option.entity.OptionEntity;
import com.Jfpicker.wheelpicker.wheel_dialog.CornerRound;
import com.Jfpicker.wheelpicker.wheel_dialog.DialogConfig;

import java.util.ArrayList;
import java.util.List;


public class AdressPickActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adress_pick);

        findViewById(R.id.btnAdressPicker).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddressPicker picker = new AddressPicker(AdressPickActivity.this);
                picker.setOnAddressPickedListener(new OnAddressPickedListener() {
                    @Override
                    public void onAddressPicked(AddressItemEntity province, AddressItemEntity city, AddressItemEntity county) {
                        toastAdress(province, city, county);
                    }
                });

                picker.show();
            }
        });

        findViewById(R.id.btnDefault).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddressPicker picker = new AddressPicker(AdressPickActivity.this);
                picker.setDefaultValue("山东省", "临沂市", "河东区");
                picker.setOnAddressPickedListener(new OnAddressPickedListener() {
                    @Override
                    public void onAddressPicked(AddressItemEntity province, AddressItemEntity city, AddressItemEntity county) {
                        toastAdress(province, city, county);
                    }
                });

                picker.show();
            }
        });

        findViewById(R.id.btnAdressPickerProvinceCity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddressPicker picker = new AddressPicker(AdressPickActivity.this);
                picker.setAddressMode(AddressMode.PROVINCE_CITY);
                picker.setOnAddressPickedListener(new OnAddressPickedListener() {
                    @Override
                    public void onAddressPicked(AddressItemEntity province, AddressItemEntity city, AddressItemEntity county) {
                        toastAdress(province, city, county);
                    }
                });

                picker.show();
            }
        });
        findViewById(R.id.btnAdressPickerCityCounty).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddressPicker picker = new AddressPicker(AdressPickActivity.this);
                picker.setAddressMode(AddressMode.CITY_COUNTY);
                picker.setDefaultValue("山东省", "临沂市", "河东区");
                picker.setOnAddressPickedListener(new OnAddressPickedListener() {
                    @Override
                    public void onAddressPicked(AddressItemEntity province, AddressItemEntity city, AddressItemEntity county) {
                        toastAdress(province, city, county);
                    }
                });

                picker.show();
            }
        });

        findViewById(R.id.btnAdressPickerCustom).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddressPicker picker = new AddressPicker(AdressPickActivity.this);
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
                picker.setOnAddressPickedListener(new OnAddressPickedListener() {
                    @Override
                    public void onAddressPicked(AddressItemEntity province, AddressItemEntity city, AddressItemEntity county) {
                        toastAdress(province, city, county);
                    }
                });

                picker.show();
            }
        });

        findViewById(R.id.btnViewpagerPickerDefault).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogConfig config = DialogConfig.getDefaultConfig();
                config.getDialogBackground().setDialogCornerRound(CornerRound.Top)
                        .setCornerRadius(15);
                AddressViewPagerPicker picker = new AddressViewPagerPicker(AdressPickActivity.this, config);
                picker.setDefaultValue("山东省", "临沂市", "河东区");
                picker.setOnAddressPickedListener(new OnAddressPickedListener() {
                    @Override
                    public void onAddressPicked(AddressItemEntity province, AddressItemEntity city, AddressItemEntity county) {
                        toastAdress(province, city, county);
                    }
                });
                picker.show();
            }
        });
        findViewById(R.id.btnViewpagerPicker).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogConfig config = DialogConfig.getDefaultConfig();
                config.getDialogBackground().setDialogCornerRound(CornerRound.Top)
                        .setCornerRadius(15);
                AddressViewPagerPicker picker = new AddressViewPagerPicker(AdressPickActivity.this, config);

                picker.setOnAddressPickedListener(new OnAddressPickedListener() {
                    @Override
                    public void onAddressPicked(AddressItemEntity province, AddressItemEntity city, AddressItemEntity county) {
                        toastAdress(province, city, county);
                    }
                });
                picker.show();
            }
        });
        findViewById(R.id.btnViewpagerPickerMyStyle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogConfig config = DialogConfig.getDefaultConfig();
                config.getDialogBackground().setDialogCornerRound(CornerRound.Top)
                        .setCornerRadius(15);
                AddressViewPagerPicker picker = new AddressViewPagerPicker(AdressPickActivity.this, config);
                picker.getTabLayout().setSelectedTabIndicatorColor(getResources().getColor(R.color.colorPrimary));
                picker.getTabLayout().setTabTextColors(Color.parseColor("#333333"), getResources().getColor(R.color.colorPrimary));
                ArrayList<String> tableNameList = new ArrayList<>();
                tableNameList.add("请选择省");
                tableNameList.add("请选择市");
                tableNameList.add("请选择区");
                picker.setTabNameList(tableNameList);
                picker.setOnRecyclerviewStyleListener(new OnRecyclerviewStyleListener() {
                    @Override
                    public void onStyle(FrameLayout itemView, TextView itemTextView, Object entity, boolean isSelect) {
                        if (isSelect) {
                            itemTextView.setTextColor(getResources().getColor(R.color.colorPrimary));
                            itemTextView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                        } else {
                            itemTextView.setTextColor(Color.parseColor("#999999"));
                            itemTextView.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                        }
                    }
                });
                picker.setOnAddressPickedListener(new OnAddressPickedListener() {
                    @Override
                    public void onAddressPicked(AddressItemEntity province, AddressItemEntity city, AddressItemEntity county) {
                        toastAdress(province, city, county);
                    }
                });
                picker.show();
            }
        });
        findViewById(R.id.btnNetRequestPicker).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogConfig config = DialogConfig.getDefaultConfig();
                config.getDialogBackground().setCornerRadius(15).setDialogCornerRound(CornerRound.Top);

                NetRequestPicker picker = new NetRequestPicker(AdressPickActivity.this, config);
                List<OptionEntity> firstData = new ArrayList<>();
                for (int i = 0; i < 10; i++) {
                    firstData.add(new OptionEntity("0" + i, "第0组第" + i));
                }
                picker.setFirstData(firstData);
                picker.setMaxIndex(5);

                picker.setOnNetRequestStartListener(new NetRequestLayout.OnNetRequestStartListener() {
                    @Override
                    public void onRequest(int parentPosition, OptionEntity entity) {
                        //模拟网络请求
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                List<OptionEntity> nextData = new ArrayList<>();
                                for (int i = 0; i < 10; i++) {
                                    nextData.add(new OptionEntity((parentPosition + 1) + "-" + i, "第" + (parentPosition + 1) + "组第" + i));
                                }
                                picker.setNextData(nextData);
                            }
                        }, 500);
                    }
                });
                picker.setOnNetRequestPickedListener(new OnNetRequestPickedListener() {
                    @Override
                    public void onPicked(OptionEntity firstData, OptionEntity secondData, OptionEntity thirdDate,
                                         OptionEntity fourthData, OptionEntity fifthData) {
                        toastOptions(firstData, secondData, thirdDate, fourthData, fifthData);
                    }
                });

                picker.show();
            }
        });
        findViewById(R.id.btnNetRequestPickerMyStyle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogConfig config = DialogConfig.getDefaultConfig();
                config.getDialogBackground().setCornerRadius(15).setDialogCornerRound(CornerRound.Top);

                NetRequestPicker picker = new NetRequestPicker(AdressPickActivity.this, config);
                picker.getTabLayout().setSelectedTabIndicatorColor(getResources().getColor(R.color.colorPrimary));
                picker.getTabLayout().setTabTextColors(Color.parseColor("#333333"), getResources().getColor(R.color.colorPrimary));
                List<OptionEntity> firstData = new ArrayList<>();
                for (int i = 0; i < 10; i++) {
                    firstData.add(new OptionEntity("0" + i, "第0组第" + i ));
                }
                picker.setFirstData(firstData);
                picker.setMaxIndex(4);
                ArrayList<String> tableNameList = new ArrayList<>();
                tableNameList.add("请选择第一级");
                tableNameList.add("请选择第二级");
                tableNameList.add("请选择第三级");
                tableNameList.add("请选择第四级");
                picker.setTabNameList(tableNameList);
                picker.setOnRecyclerviewStyleListener(new OnRecyclerviewStyleListener() {
                    @Override
                    public void onStyle(FrameLayout itemView, TextView itemTextView, Object entity, boolean isSelect) {
                        if (isSelect) {
                            itemTextView.setTextColor(getResources().getColor(R.color.colorPrimary));
                            itemTextView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                        } else {
                            itemTextView.setTextColor(Color.parseColor("#999999"));
                            itemTextView.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                        }
                    }
                });
                picker.setOnNetRequestStartListener(new NetRequestLayout.OnNetRequestStartListener() {
                    @Override
                    public void onRequest(int parentPosition, OptionEntity entity) {
                        //模拟网络请求
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                List<OptionEntity> nextData = new ArrayList<>();
                                for (int i = 0; i < 10; i++) {
                                    nextData.add(new OptionEntity((parentPosition + 1) + "-" + i, "第" + (parentPosition + 1) + "组第" + i ));
                                }
                                picker.setNextData(nextData);
                            }
                        }, 500);
                    }
                });
                picker.setOnNetRequestPickedListener(new OnNetRequestPickedListener() {
                    @Override
                    public void onPicked(OptionEntity firstData, OptionEntity secondData, OptionEntity thirdDate,
                                         OptionEntity fourthData, OptionEntity fifthData) {
                        toastOptions(firstData, secondData, thirdDate, fourthData, fifthData);
                    }
                });

                picker.show();
            }
        });
    }

    public void toastAdress(AddressItemEntity province, AddressItemEntity city, AddressItemEntity county) {
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
        Toast.makeText(AdressPickActivity.this,
                "选择了" + provinceText + "-" + cityText + "-" + countyText, Toast.LENGTH_SHORT).show();
    }

    public void toastOptions(OptionEntity firstData, OptionEntity secondData, OptionEntity thirdDate,
                             OptionEntity fourthData, OptionEntity fifthData) {
        String fist = firstData != null ? firstData.getName() : "";
        String second = secondData != null ? secondData.getName() : "";
        String third = thirdDate != null ? thirdDate.getName() : "";
        String fourth = fourthData != null ? fourthData.getName() : "";
        String fifth = fifthData != null ? fifthData.getName() : "";

        Toast.makeText(AdressPickActivity.this,
                "选择了" + fist + "-" + second + "-" + third + "-" + fourth + "-" + fifth, Toast.LENGTH_SHORT).show();
    }
}