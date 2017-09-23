package com.abishov.hexocat.home.trending;

import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.abishov.hexocat.commons.views.ViewState;
import com.google.auto.value.AutoValue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@AutoValue
abstract class TrendingViewState implements ViewState, Parcelable {

    @NonNull
    abstract List<TrendingViewModel> items();

    @NonNull
    static TrendingViewState idle() {
        return new AutoValue_TrendingViewState(true, false, false, false, "",
                Collections.unmodifiableList(Collections.emptyList()));
    }

    @NonNull
    static TrendingViewState progress() {
        return new AutoValue_TrendingViewState(false, true, false, false, "",
                Collections.unmodifiableList(Collections.emptyList()));
    }

    @NonNull
    static TrendingViewState success(@NonNull List<TrendingViewModel> items) {
        List<TrendingViewModel> deepCopy = Collections.unmodifiableList(new ArrayList<>(items));
        return new AutoValue_TrendingViewState(false, false, true, false, "", deepCopy);
    }

    @NonNull
    static TrendingViewState failure(@NonNull Throwable throwable) {
        return new AutoValue_TrendingViewState(false, false, false, true, throwable.getMessage(),
                Collections.unmodifiableList(Collections.emptyList()));
    }
}
