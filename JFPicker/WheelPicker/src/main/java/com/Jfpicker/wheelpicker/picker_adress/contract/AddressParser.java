

package com.Jfpicker.wheelpicker.picker_adress.contract;

import androidx.annotation.NonNull;
import androidx.annotation.WorkerThread;

import com.Jfpicker.wheelpicker.picker_adress.entity.ProvinceEntity;

import java.util.List;

public interface AddressParser {

    @WorkerThread
    @NonNull
    List<ProvinceEntity> parseData(@NonNull String text);

}
