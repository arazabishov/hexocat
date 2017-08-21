package com.abishov.hexocat.commons.views;

import android.support.annotation.NonNull;

import com.google.auto.value.AutoValue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@AutoValue
public abstract class ViewState<T> {

    @NonNull
    public abstract Boolean isIdle();

    @NonNull
    public abstract Boolean isInProgress();

    @NonNull
    public abstract Boolean isSuccess();

    @NonNull
    public abstract Boolean isFailure();

    @NonNull
    public abstract String error();

    @NonNull
    public abstract List<T> items();

    @NonNull
    public static <T> ViewState<T> idle() {
        return new AutoValue_ViewState<>(true, false, false, false, "",
                Collections.unmodifiableList(Collections.emptyList()));
    }

    @NonNull
    public static <T> ViewState<T> progress() {
        return new AutoValue_ViewState<>(false, true, false, false, "",
                Collections.unmodifiableList(Collections.emptyList()));
    }

    @NonNull
    public static <T> ViewState<T> success(@NonNull List<T> items) {
        List<T> deepCopy = Collections.unmodifiableList(new ArrayList<>(items));
        return new AutoValue_ViewState<>(false, false, true, false, "", deepCopy);
    }

    @NonNull
    public static <T> ViewState<T> failure(@NonNull Throwable throwable) {
        return new AutoValue_ViewState<>(false, false, false, true, throwable.getMessage(),
                Collections.unmodifiableList(Collections.emptyList()));
    }
}
