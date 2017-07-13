package com.abishov.hexocat;

import android.support.annotation.NonNull;

import com.abishov.hexocat.commons.network.HexocatAdapterFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Dependency provider for unit tests.
 */
public final class Inject {
    private Inject() {
        // no instances
    }

    @NonNull
    public static Gson gson() {
        return new GsonBuilder()
                .registerTypeAdapterFactory(HexocatAdapterFactory.create())
                .create();
    }
}
