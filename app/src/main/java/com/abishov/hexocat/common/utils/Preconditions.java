package com.abishov.hexocat.common.utils;

import android.support.annotation.Nullable;

public final class Preconditions {
    private Preconditions() {
        // no instances
    }

    public static <T> T isNull(@Nullable T object, String message) {
        if (object == null) {
            throw new IllegalArgumentException(message);
        }

        return object;
    }
}
