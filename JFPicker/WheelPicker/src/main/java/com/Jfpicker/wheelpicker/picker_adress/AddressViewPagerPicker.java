package com.Jfpicker.wheelpicker.picker_adress;

import android.app.Activity;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.StyleRes;

import com.Jfpicker.wheelpicker.picker_adress.annotation.AddressMode;
import com.Jfpicker.wheelpicker.picker_adress.contract.AddressLoader;
import com.Jfpicker.wheelpicker.picker_adress.contract.AddressParser;
import com.Jfpicker.wheelpicker.picker_adress.contract.AddressReceiver;
import com.Jfpicker.wheelpicker.picker_adress.contract.OnAddressPickedListener;
import com.Jfpicker.wheelpicker.picker_adress.entity.AddressItemEntity;
import com.Jfpicker.wheelpicker.picker_adress.impl.AddressProvider;
import com.Jfpicker.wheelpicker.picker_adress.impl.AssetAddressLoader;
import com.Jfpicker.wheelpicker.picker_adress.utility.AddressJsonParser;
import com.Jfpicker.wheelpicker.picker_adress.widget.AddressViewPagerLayout;
import com.Jfpicker.wheelpicker.rv_listener.OnRecyclerviewStyleListener;
import com.Jfpicker.wheelpicker.dialog.config.DialogConfig;
import com.Jfpicker.wheelpicker.utils.WheelLogUtils;
import com.Jfpicker.wheelpicker.dialog.ModalDialog;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Created by JF on  2021/11/15
 * @description ViewPager样式的地址选择器, 默认底部，15圆角样式
 */

public class AddressViewPagerPicker extends ModalDialog implements AddressReceiver {
    private AddressLoader addressLoader;
    private AddressParser addressParser;
    AddressViewPagerLayout addressLayout;
    private OnAddressPickedListener onAddressPickedListener;

    public AddressViewPagerPicker(@NonNull Activity activity) {

        super(activity, DialogConfig.getBottomConfig(15));
    }

    public AddressViewPagerPicker(@NonNull Activity activity, DialogConfig dialogConfig) {
        super(activity, (dialogConfig != null) ? dialogConfig : DialogConfig.getBottomConfig(15));
    }

    public AddressViewPagerPicker(@NonNull Activity activity, int themeResId) {
        super(activity, DialogConfig.getBottomConfig(15), themeResId);
    }

    public AddressViewPagerPicker(@NonNull Activity activity, DialogConfig dialogConfig, @StyleRes int themeResId) {
        super(activity, (dialogConfig != null) ? dialogConfig : DialogConfig.getBottomConfig(15), themeResId);
    }

    @Override
    protected void initView() {
        super.initView();
        if (titleTextView != null) {
            titleTextView.setText("地址选择");
        }

    }

    @NonNull
    @Override
    protected View createBodyView() {
        addressLayout = new AddressViewPagerLayout(getContext());
        return addressLayout;
    }

    @Override
    protected void initData() {
        super.initData();
        if (addressLoader == null || addressParser == null) {
            setDefaultAddress();
        }
        WheelLogUtils.print("Address data loading");
        addressLoader.loadJson(this, addressParser);
    }

    @Override
    protected void onCancel() {

    }

    @Override
    protected void onConfirm() {
        if (onAddressPickedListener != null) {
            AddressItemEntity province = addressLayout.getProvince();
            AddressItemEntity city = addressLayout.getCity();
            AddressItemEntity county = addressLayout.getArea();
            onAddressPickedListener.onAddressPicked(province, city, county);
        }
    }

    @Override
    public void onAddressReceived(@NonNull List<AddressItemEntity> data) {
        addressLayout.setData(new AddressProvider(data, AddressMode.PROVINCE_CITY_COUNTY));
    }

    public void setAddressLoader(@NonNull AddressLoader loader, @NonNull AddressParser parser) {
        this.addressLoader = loader;
        this.addressParser = parser;
    }

    public void setDefaultValue(String province, String city, String county) {
        addressLayout.setDefaultValue(province, city, county);
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
        return addressLayout;
    }

    public TabLayout getTabLayout() {
        return addressLayout.getTabLayout();
    }

    public void setTabNameList(ArrayList<String> tabNameList) {
        addressLayout.setTabNameList(tabNameList);

    }

    public void setOnRecyclerviewStyleListener(OnRecyclerviewStyleListener onRecyclerviewStyleListener) {
        addressLayout.setOnRecyclerviewStyleListener(onRecyclerviewStyleListener);
    }

}
