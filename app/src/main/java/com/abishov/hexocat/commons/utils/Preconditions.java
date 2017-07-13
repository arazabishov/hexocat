package com.abishov.hexocat.commons.utils;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public final class Preconditions {
    private Preconditions() {
        // no instances
    }

    @NonNull
    public static <T> T isNull(@Nullable T object, @NonNull String message) {
        if (object == null) {
            throw new IllegalArgumentException(message);
        }

        return object;
    }
}
