
package com.Jfpicker.wheelpicker.picker_adress;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.StyleRes;

import com.Jfpicker.wheelpicker.picker_adress.annotation.AddressMode;
import com.Jfpicker.wheelpicker.picker_adress.contract.AddressLoader;
import com.Jfpicker.wheelpicker.picker_adress.contract.AddressParser;
import com.Jfpicker.wheelpicker.picker_adress.contract.AddressReceiver;

import com.Jfpicker.wheelpicker.picker_adress.contract.OnAddressPickedListener;
import com.Jfpicker.wheelpicker.picker_adress.entity.AddressItemEntity;

import com.Jfpicker.wheelpicker.picker_adress.formatter.AdressFormatter;
import com.Jfpicker.wheelpicker.picker_adress.impl.AddressProvider;
import com.Jfpicker.wheelpicker.picker_adress.impl.AssetAddressLoader;
import com.Jfpicker.wheelpicker.picker_adress.utility.AddressJsonParser;
import com.Jfpicker.wheelpicker.picker_option.LinkagePicker;
import com.Jfpicker.wheelpicker.picker_option.entity.LinkageProvider;
import com.Jfpicker.wheelpicker.picker_option.listener.OnLinkagePickedListener;
import com.Jfpicker.wheelpicker.wheel_dialog.DialogConfig;
import com.Jfpicker.wheelpicker.wheel_dialog.DialogLog;


import java.util.List;

/**
 * 地址滚轮选择器
 * 使用了AndroidPicker的地址加载代码
 * 源码地址：https://github.com/gzu-liyujiang/AndroidPicker
 */
@SuppressWarnings({"unused"})
public class AddressPicker extends LinkagePicker implements AddressReceiver {
    private AddressLoader addressLoader;
    private AddressParser addressParser;
    private int addressMode;
    private OnAddressPickedListener onAddressPickedListener;

    public AddressPicker(@NonNull Activity activity) {
        super(activity);
    }

    public AddressPicker(@NonNull Activity activity, DialogConfig dialogConfig) {
        super(activity, dialogConfig);
    }

    public AddressPicker(@NonNull Activity activity, @StyleRes int themeResId) {
        super(activity, themeResId);
    }

    @Override
    protected void initView() {
        super.initView();
        titleView.setText("地址选择");
        wheelLayout.setFormatter(new AdressFormatter());
    }

    @Override
    protected void initData() {
        super.initData();
        if (addressLoader == null || addressParser == null) {
            setAddressMode(AddressMode.PROVINCE_CITY_COUNTY);
        }
        wheelLayout.showLoading();

        DialogLog.print("Address data loading");
        addressLoader.loadJson(this, addressParser);
    }

    @Override
    public void onAddressReceived(@NonNull List<AddressItemEntity> data) {
        DialogLog.print("Address data received");
        wheelLayout.hideLoading();

        wheelLayout.setData(new AddressProvider(data, addressMode));
    }

    @Deprecated
    @Override
    public void setData(@NonNull LinkageProvider data) {
        throw new UnsupportedOperationException("Use setAddressMode or setAddressLoader instead");
    }

    @Deprecated
    @Override
    public void setOnLinkagePickedListener(OnLinkagePickedListener onLinkagePickedListener) {
        throw new UnsupportedOperationException("Use setOnAddressPickedListener instead");
    }

    @Override
    protected void onOk() {
        if (onAddressPickedListener != null) {
            AddressItemEntity province = (AddressItemEntity) wheelLayout.getSelectFirst();
            AddressItemEntity city = (AddressItemEntity) wheelLayout.getSelectSecond();
            AddressItemEntity county = (AddressItemEntity) wheelLayout.getSelectThird();
            onAddressPickedListener.onAddressPicked(province, city, county);
        }
    }


    public void setOnAddressPickedListener(@NonNull OnAddressPickedListener onAddressPickedListener) {
        this.onAddressPickedListener = onAddressPickedListener;
    }


    public void setAddressLoader(@NonNull AddressLoader loader, @NonNull AddressParser parser) {
        this.addressLoader = loader;
        this.addressParser = parser;
    }

    public void setAddressMode(@AddressMode int addressMode) {
        setAddressMode("china_address.json", addressMode);
    }

    public void setAddressMode(@NonNull String assetPath, @AddressMode int addressMode) {
        setAddressMode(assetPath, addressMode, new AddressJsonParser());
    }

    public void setAddressMode(@NonNull String assetPath, @AddressMode int addressMode,
                               @NonNull AddressJsonParser jsonParser) {
        this.addressMode = addressMode;
        setAddressLoader(new AssetAddressLoader(getContext(), assetPath), jsonParser);
    }

    public void setDefaultValue(String province, String city, String county) {
        wheelLayout.setDefaultValue(province, city, county);
    }
}
