package com.abishov.hexocat.commons.views;

public interface ViewState {
    Boolean isIdle();

    Boolean isInProgress();

    Boolean isSuccess();

    Boolean isFailure();

    String error();
}
