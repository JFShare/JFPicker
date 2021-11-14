

package com.Jfpicker.wheelpicker.picker_adress.impl;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.WorkerThread;

import com.Jfpicker.wheelpicker.picker_adress.contract.AddressLoader;
import com.Jfpicker.wheelpicker.picker_adress.contract.AddressParser;
import com.Jfpicker.wheelpicker.picker_adress.contract.AddressReceiver;
import com.Jfpicker.wheelpicker.picker_adress.entity.ProvinceEntity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

@SuppressWarnings("unused")
public class AssetAddressLoader implements AddressLoader {
    private final Context context;
    private final String path;

    public AssetAddressLoader(@NonNull Context context, @NonNull String path) {
        this.context = context;
        this.path = path;
    }

    @Override
    public void loadJson(@NonNull final AddressReceiver receiver, @NonNull final AddressParser parser) {
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                String text = loadFromAssets();
                final List<ProvinceEntity> data;
                if (TextUtils.isEmpty(text)) {
                    data = new ArrayList<>();
                } else {
                    data = parser.parseData(text);
                }
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        receiver.onAddressReceived(data);
                    }
                });
            }
        });
    }

    @WorkerThread
    private String loadFromAssets() {
        StringBuilder stringBuilder = new StringBuilder();
        AssetManager am = context.getAssets();
        try (BufferedReader bf = new BufferedReader(new InputStreamReader(am.open(path)))) {
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            return "";
        }
        return stringBuilder.toString();
    }

}
