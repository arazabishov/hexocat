package com.abishov.hexocat.common.views;

public interface Presenter<T extends View> {
    void onAttach(T view);

    void onDetach();
}
