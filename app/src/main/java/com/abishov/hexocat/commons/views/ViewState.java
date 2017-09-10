package com.abishov.hexocat.commons.views;

import android.support.annotation.NonNull;

public interface ViewState {

    @NonNull
    Boolean isIdle();

    @NonNull
    Boolean isInProgress();

    @NonNull
    Boolean isSuccess();

    @NonNull
    Boolean isFailure();

    @NonNull
    String error();
}
