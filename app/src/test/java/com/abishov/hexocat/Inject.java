package com.abishov.hexocat;

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

    public static Gson gson() {
        return new GsonBuilder()
                .registerTypeAdapterFactory(HexocatAdapterFactory.create())
                .create();
    }
}
