package com.abishov.hexocat.commons.views;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public interface Presenter<T extends View, K extends ViewState> {
    void onAttach(@NonNull T view, @Nullable K state);

    void onDetach();
}
