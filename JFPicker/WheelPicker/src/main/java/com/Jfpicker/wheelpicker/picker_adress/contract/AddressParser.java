

package com.Jfpicker.wheelpicker.picker_adress.contract;

import androidx.annotation.NonNull;
import androidx.annotation.WorkerThread;

import com.Jfpicker.wheelpicker.picker_adress.entity.AddressItemEntity;

import java.util.List;
/**
 * 使用了AndroidPicker的地址加载代码
 * 源码地址：https://github.com/gzu-liyujiang/AndroidPicker
 */
public interface AddressParser {

    @WorkerThread
    @NonNull
    List<AddressItemEntity> parseData(@NonNull String text);

}
