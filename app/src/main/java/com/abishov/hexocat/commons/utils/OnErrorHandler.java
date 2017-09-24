package com.abishov.hexocat.commons.utils;

import io.reactivex.exceptions.OnErrorNotImplementedException;
import io.reactivex.functions.Consumer;

public final class OnErrorHandler implements Consumer<Throwable> {

    public static Consumer<Throwable> create() {
        return new OnErrorHandler();
    }

    private OnErrorHandler() {
        // use factory method
    }

    @Override
    public void accept(Throwable throwable) {
        throw new OnErrorNotImplementedException(throwable);
    }
}