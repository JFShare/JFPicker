
package com.Jfpicker.wheelpicker.picker_adress.utility;

import android.text.TextUtils;

import androidx.annotation.NonNull;


import com.Jfpicker.wheelpicker.picker_adress.contract.AddressParser;

import com.Jfpicker.wheelpicker.picker_adress.entity.AddressItemEntity;
import com.Jfpicker.wheelpicker.utils.WheelLogUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
/**
 * 使用了AndroidPicker的地址加载代码
 * 源码地址：https://github.com/gzu-liyujiang/AndroidPicker
 */
public class AddressJsonParser implements AddressParser {
    private final Builder builder;

    public AddressJsonParser() {
        this(new Builder());
    }

    public AddressJsonParser(Builder builder) {
        this.builder = builder;
    }

    @NonNull
    @Override
    public List<AddressItemEntity> parseData(@NonNull String text) {
        try {
            JSONArray provinceArray = new JSONArray(text);
            return parseProvince(provinceArray);
        } catch (JSONException e) {
            WheelLogUtils.print(e);
        }
        return new ArrayList<>();
    }

    private List<AddressItemEntity> parseProvince(JSONArray provinceArray) {
        List<AddressItemEntity> data = new ArrayList<>();
        for (int i = 0, x = provinceArray.length(); i < x; i++) {
            AddressItemEntity provinceEntity = new AddressItemEntity();
            JSONObject provinceObject = provinceArray.optJSONObject(i);
            provinceEntity.setCode(provinceObject.optString(builder.provinceCodeField));
            provinceEntity.setName(provinceObject.optString(builder.provinceNameField));
            provinceEntity.setNextList(new ArrayList<>());
            JSONArray cityArray = provinceObject.optJSONArray(builder.provinceChildField);
            parseCity(provinceEntity, cityArray);
            data.add(provinceEntity);
        }
        return data;
    }

    private void parseCity(AddressItemEntity provinceEntity, JSONArray cityArray) {
        //台湾的第二级可能没数据
        if (cityArray == null || cityArray.length() == 0) {
            return;
        }
        for (int j = 0, y = cityArray.length(); j < y; j++) {
            AddressItemEntity cityEntity = new AddressItemEntity();
            JSONObject cityObject = cityArray.optJSONObject(j);
            cityEntity.setCode(cityObject.optString(builder.cityCodeField));
            cityEntity.setName(cityObject.optString(builder.cityNameField));
            cityEntity.setNextList(new ArrayList<>());
            provinceEntity.getNextList().add(cityEntity);
            JSONArray countyArray = cityObject.optJSONArray(builder.cityChildField);
            parseCounty(cityEntity, countyArray);
        }
    }

    private void parseCounty(AddressItemEntity cityEntity, JSONArray countyArray) {
        //港澳台的第三级可能没数据
        if (countyArray == null || countyArray.length() == 0) {
            return;
        }
        for (int k = 0, z = countyArray.length(); k < z; k++) {
            AddressItemEntity countyEntity = new AddressItemEntity();
            JSONObject countyObject = countyArray.optJSONObject(k);
            countyEntity.setCode(countyObject.optString(builder.countyCodeField));
            countyEntity.setName(countyObject.optString(builder.countyNameField));
            cityEntity.getNextList().add(countyEntity);
        }
    }

    @SuppressWarnings("unused")
    public static class Builder {
        private String provinceCodeField = "code";
        private String provinceNameField = "name";
        private String provinceChildField = "cityList";
        private String cityCodeField = "code";
        private String cityNameField = "name";
        private String cityChildField = "areaList";
        private String countyCodeField = "code";
        private String countyNameField = "name";

        public Builder provinceCodeField(String provinceCodeField) {
            if (TextUtils.isEmpty(provinceCodeField)) {
                return this;
            }
            this.provinceCodeField = provinceCodeField;
            return this;
        }

        public Builder provinceNameField(String provinceNameField) {
            if (TextUtils.isEmpty(provinceNameField)) {
                return this;
            }
            this.provinceNameField = provinceNameField;
            return this;
        }

        public Builder provinceChildField(String provinceChildField) {
            if (TextUtils.isEmpty(provinceChildField)) {
                return this;
            }
            this.provinceChildField = provinceChildField;
            return this;
        }

        public Builder cityCodeField(String cityCodeField) {
            if (TextUtils.isEmpty(cityCodeField)) {
                return this;
            }
            this.cityCodeField = cityCodeField;
            return this;
        }

        public Builder cityNameField(String cityNameField) {
            if (TextUtils.isEmpty(cityNameField)) {
                return this;
            }
            this.cityNameField = cityNameField;
            return this;
        }

        public Builder cityChildField(String cityChildField) {
            if (TextUtils.isEmpty(cityChildField)) {
                return this;
            }
            this.cityChildField = cityChildField;
            return this;
        }

        public Builder countyCodeField(String countyCodeField) {
            if (TextUtils.isEmpty(countyCodeField)) {
                return this;
            }
            this.countyCodeField = countyCodeField;
            return this;
        }

        public Builder countyNameField(String countyNameField) {
            if (TextUtils.isEmpty(countyNameField)) {
                return this;
            }
            this.countyNameField = countyNameField;
            return this;
        }

        public AddressJsonParser build() {
            return new AddressJsonParser(this);
        }

    }

}
