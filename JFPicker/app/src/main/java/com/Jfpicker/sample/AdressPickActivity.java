package com.Jfpicker.sample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.Jfpicker.wheelpicker.picker_adress.AddressPicker;
import com.Jfpicker.wheelpicker.picker_adress.annotation.AddressMode;
import com.Jfpicker.wheelpicker.picker_adress.contract.OnAddressPickedListener;
import com.Jfpicker.wheelpicker.picker_adress.entity.CityEntity;
import com.Jfpicker.wheelpicker.picker_adress.entity.CountyEntity;
import com.Jfpicker.wheelpicker.picker_adress.entity.ProvinceEntity;

public class AdressPickActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adress_pick);

        findViewById(R.id.btnAdressPicker).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddressPicker picker = new AddressPicker(AdressPickActivity.this);
                picker.setAddressMode(AddressMode.PROVINCE_CITY_COUNTY);
                picker.setOnAddressPickedListener(new OnAddressPickedListener() {
                    @Override
                    public void onAddressPicked(ProvinceEntity province, CityEntity city, CountyEntity county) {
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
                });

                picker.show();
            }
        });
    }
}