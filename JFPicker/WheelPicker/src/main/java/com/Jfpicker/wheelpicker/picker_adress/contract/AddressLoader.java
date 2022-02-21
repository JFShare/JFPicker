package com.Jfpicker.wheelpicker.picker_adress.contract;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;

/**
 * 使用了AndroidPicker的地址加载代码
 * 源码地址：https://github.com/gzu-liyujiang/AndroidPicker
 */
public interface AddressLoader {

    @MainThread
    void loadJson(@NonNull AddressReceiver receiver, @NonNull AddressParser parser);

}
