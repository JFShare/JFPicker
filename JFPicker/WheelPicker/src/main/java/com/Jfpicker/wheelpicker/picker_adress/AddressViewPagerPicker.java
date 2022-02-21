package com.Jfpicker.wheelpicker.picker_adress;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;

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
import com.Jfpicker.wheelpicker.picker_base.OnRecyclerviewStyleListener;
import com.Jfpicker.wheelpicker.wheel_dialog.CornerRound;
import com.Jfpicker.wheelpicker.wheel_dialog.DialogConfig;
import com.Jfpicker.wheelpicker.wheel_dialog.DialogLog;
import com.Jfpicker.wheelpicker.wheel_dialog.ModalDialog;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Created by JF on  2021/11/15
 * @description ViewPager样式的地址选择器
 */

public class AddressViewPagerPicker extends ModalDialog implements AddressReceiver {
    private AddressLoader addressLoader;
    private AddressParser addressParser;
    AddressViewPagerLayout adressLayout;
    private OnAddressPickedListener onAddressPickedListener;

    public AddressViewPagerPicker(@NonNull Activity activity) {
        super(activity);
    }

    public AddressViewPagerPicker(@NonNull Activity activity, DialogConfig dialogConfig) {
        super(activity, dialogConfig);
    }

    public AddressViewPagerPicker(@NonNull Activity activity, int themeResId) {
        super(activity, themeResId);
    }

    @Override
    protected void initView() {
        super.initView();
        titleView.setText("地址选择");
        headerView.setBackgroundColor(Color.TRANSPARENT);
    }

    @NonNull
    @Override
    protected View createBodyView() {
        adressLayout = new AddressViewPagerLayout(getContext());
        return adressLayout;
    }

    @Override
    protected void initData() {
        super.initData();

        if (addressLoader == null || addressParser == null) {
            setDefaultAddress();
        }
        DialogLog.print("Address data loading");
        addressLoader.loadJson(this, addressParser);
    }

    @Override
    protected void onCancel() {

    }

    @Override
    protected void onOk() {
        if (onAddressPickedListener != null) {
            AddressItemEntity province = adressLayout.getProvince();
            AddressItemEntity city = adressLayout.getCity();
            AddressItemEntity county = adressLayout.getArea();
            onAddressPickedListener.onAddressPicked(province, city, county);
        }
    }

    @Override
    public void onAddressReceived(@NonNull List<AddressItemEntity> data) {
        adressLayout.setData(new AddressProvider(data, AddressMode.PROVINCE_CITY_COUNTY));
    }

    public void setAddressLoader(@NonNull AddressLoader loader, @NonNull AddressParser parser) {
        this.addressLoader = loader;
        this.addressParser = parser;
    }

    public void setDefaultValue(String province, String city, String county) {
        adressLayout.setDefaultValue(province, city, county);
    }

    public void setDefaultAddress() {
        setAddressLoader(new AssetAddressLoader(getContext(), "china_address.json"), new AddressJsonParser());
    }

    public void setAddress(@NonNull String assetPath, @AddressMode @NonNull AddressJsonParser jsonParser) {
        setAddressLoader(new AssetAddressLoader(getContext(), assetPath), jsonParser);
    }

    public void setOnAddressPickedListener(@NonNull OnAddressPickedListener onAddressPickedListener) {
        this.onAddressPickedListener = onAddressPickedListener;
    }

    public AddressViewPagerLayout getAddressViewPagerLayout() {
        return adressLayout;
    }

    public TabLayout getTabLayout() {
        return adressLayout.getTabLayout();
    }

    public void setTabNameList(ArrayList<String> tabNameList) {
        adressLayout.setTabNameList(tabNameList);

    }

    public void setOnRecyclerviewStyleListener(OnRecyclerviewStyleListener onRecyclerviewStyleListener) {
        adressLayout.setOnRecyclerviewStyleListener(onRecyclerviewStyleListener);
    }

}
