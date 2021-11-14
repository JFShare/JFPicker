

package com.Jfpicker.wheelpicker.picker_adress.contract;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;

public interface AddressLoader {

    @MainThread
    void loadJson(@NonNull AddressReceiver receiver, @NonNull AddressParser parser);

}
