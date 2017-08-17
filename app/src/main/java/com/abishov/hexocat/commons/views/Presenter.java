package com.abishov.hexocat.commons.views;

import android.support.annotation.NonNull;

public interface Presenter<T extends View> {

    void onAttach(@NonNull T view);

    void onDetach();
}
