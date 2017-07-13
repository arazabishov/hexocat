package com.abishov.hexocat.commons.network;

import android.support.annotation.NonNull;

import com.google.gson.TypeAdapterFactory;
import com.ryanharter.auto.value.gson.GsonTypeAdapterFactory;

@GsonTypeAdapterFactory
public abstract class HexocatAdapterFactory implements TypeAdapterFactory {

    // Static factory method to access the package
    // private generated implementation
    @NonNull
    public static TypeAdapterFactory create() {
        return new AutoValueGson_HexocatAdapterFactory();
    }
}
