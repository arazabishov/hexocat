package com.abishov.hexocat.commons.views;

public interface Presenter<T extends View> {
    void onAttach(T view);

    void onDetach();
}
