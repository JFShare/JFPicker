
package com.Jfpicker.wheelpicker.picker_adress.contract;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;

import com.Jfpicker.wheelpicker.picker_adress.entity.ProvinceEntity;

import java.util.List;



public interface AddressReceiver {

    @MainThread
    void onAddressReceived(@NonNull List<ProvinceEntity> data);

}
