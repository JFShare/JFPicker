

package com.Jfpicker.wheelpicker.picker_adress.contract;


import com.Jfpicker.wheelpicker.picker_adress.entity.AddressItemEntity;


public interface OnAddressPickedListener {

    void onAddressPicked(AddressItemEntity province, AddressItemEntity city, AddressItemEntity county);

}
