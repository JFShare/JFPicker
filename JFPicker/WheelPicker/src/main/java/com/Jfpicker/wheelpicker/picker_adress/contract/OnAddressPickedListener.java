

package com.Jfpicker.wheelpicker.picker_adress.contract;

import com.Jfpicker.wheelpicker.picker_adress.entity.CityEntity;
import com.Jfpicker.wheelpicker.picker_adress.entity.CountyEntity;
import com.Jfpicker.wheelpicker.picker_adress.entity.ProvinceEntity;

public interface OnAddressPickedListener {

    /**
     * 联动选择回调
     *
     * @param province 选中的省/直辖市/自治区
     * @param city     选中的地级市/自治州
     * @param county   选中的区/县/县级市
     */
    void onAddressPicked(ProvinceEntity province, CityEntity city, CountyEntity county);

}
